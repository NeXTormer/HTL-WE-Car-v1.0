package holz.htl_klu.at.schubauto_controller;

/**
 * Created by Felix on 02/02/2018.
 */

import java.io.IOException;
import java.net.*;

public class UDPClient {

    private int m_Port;

    private InetAddress m_Serveraddress;
    private DatagramSocket m_Socket;

    public UDPClient(String address, int port)
    {
        this.m_Port = port;
        try {
            m_Serveraddress = InetAddress.getByName(address);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        try {
            m_Socket = new DatagramSocket(port);
        } catch (SocketException e) {
            System.err.println("Socket Exception");
            e.printStackTrace();
        }

    }

    public void send(byte[] data)
    {
        DatagramPacket packet = new DatagramPacket(data, data.length, m_Serveraddress, m_Port);

        try {
            m_Socket.send(packet);
        } catch (IOException e) {
            System.err.println("Sending Failed");
            e.printStackTrace();
        }
    }
}