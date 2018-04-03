package ge.biterium.chat.voice.controller;

import ge.biterium.chat.voice.Main;

import javax.sound.sampled.TargetDataLine;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class RecorderThread extends Thread {
    public TargetDataLine audio_in;
    public DatagramSocket datagramSocket;
    public InetAddress ip;
    public int port;
    byte buffer[] = new byte[512];

    RecorderThread(TargetDataLine audio_in, DatagramSocket datagramSocket, InetAddress ip, int port) {
        this.audio_in = audio_in;
        this.datagramSocket = datagramSocket;
        this.ip = ip;
        this.port = port;
    }

    @Override
    public void run() {
        int i = 0;
        while (Main.calling) {
            audio_in.read(buffer, 0, buffer.length);
            DatagramPacket data = new DatagramPacket(buffer, buffer.length, ip, port);
            System.err.println("send : " + i++);
            try {
                datagramSocket.send(data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        audio_in.close();
        audio_in.drain();
        System.out.println("RecorderThread  Stoped ");
    }
}
