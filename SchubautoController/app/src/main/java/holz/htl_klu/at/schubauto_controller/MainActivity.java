package holz.htl_klu.at.schubauto_controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private UDPClient m_Client;

    private SeekBar ss_Left;
    private SeekBar ss_Right;

    private Timer m_Timer;

    private int schubleft;
    private int schubright;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        m_Client = new UDPClient("10.68.1.52", 4242);
        System.out.println("gas gas gas");
        m_Timer = new Timer();

        ss_Left = (SeekBar) findViewById(R.id.ss_left);
        ss_Right = (SeekBar) findViewById(R.id.ss_right);



        m_Timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                //doesn't work because the timer runs a new thread and it is not allowed to change the text from a different thread. isdn.

                byte[] data = { (byte) schubleft, (byte) (schubright)};
                m_Client.send(data);
                System.out.println("Gas: " + Arrays.toString(data));

                //log data to activity
            }
        }, 100, 100);

        ss_Left.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                schubleft = progress;
            }

        });

        ss_Right.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                schubright = progress;
            }
        });

    }
}
