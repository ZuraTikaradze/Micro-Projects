package ge.biterium.chat.voice.controller;

import ge.biterium.chat.voice.Main;

import javax.sound.sampled.SourceDataLine;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class PlayerThread extends Thread {
    public DatagramSocket datagramSocket;
    public SourceDataLine audio_out;
    byte[] buffer = new byte[512];

    PlayerThread(DatagramSocket datagramSocket, SourceDataLine sourceDataLine) {
        this.datagramSocket = datagramSocket;
        this.audio_out = sourceDataLine;
    }

    @Override
    public void run() {
        DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
        while (Main.calling) {
            try {
                datagramSocket.receive(datagramPacket);
                buffer = datagramPacket.getData();
                audio_out.write(buffer, 0, buffer.length);
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        audio_out.close();
        audio_out.drain();
        System.out.println("Player Thread Stop");
    }
}
