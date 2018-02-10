package htlklu.at.we_car;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import java.util.prefs.Preferences;
import java.util.prefs.PreferencesFactory;

public class MainActivity extends AppCompatActivity {

    private UDPClient m_Client;

    private SeekBar m_Seek_Schub;
    private SeekBar m_Seek_Steer;

    private TextView m_DebugText;

    private float m_Torque = 1;
    private float m_Steering = 1;

    private Timer m_Timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        m_Client = new UDPClient("10.68.1.52", 4242);

        m_Timer = new Timer();

        m_Seek_Schub = (SeekBar) findViewById(R.id.ss_schub);
        m_Seek_Steer = (SeekBar) findViewById(R.id.ss_lenkung);
        m_DebugText = (TextView) findViewById(R.id.text_debug);

        m_Timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                //doesn't work because the timer runs a new thread and it is not allowed to change the text from a different thread. isdn.
                m_DebugText.setText("");

                float t_Steer;
                if(m_Steering == 0)
                {
                    t_Steer = 0;
                }
                else
                {
                    t_Steer = m_Steering / 255.0f;
                }

                m_DebugText.setText(String.format("Steering Value: %.3f", t_Steer));

                float t_Left = (float) (m_Torque * t_Steer);
                float t_Right = (float) (m_Torque * (1 - t_Steer));

                float normref = t_Left < t_Right ? t_Right : t_Left;

                float n_Left = t_Left / normref;
                float n_Right = t_Right / normref;



                byte[] data = { (byte) (n_Right * m_Torque), (byte) (n_Left * m_Torque)};
                m_Client.send(data);

                m_DebugText.setText(m_DebugText.getText() + "\nSent Network Data: \n" + Arrays.toString(data));
                //log data to activity
            }
        }, 100, 100);

        m_Seek_Schub.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                m_Torque = progress;
            }

        });

        m_Seek_Steer.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                m_Steering = progress;
            }
        });

    }
}
