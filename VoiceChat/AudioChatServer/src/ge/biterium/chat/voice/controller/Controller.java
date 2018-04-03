package ge.biterium.chat.voice.controller;

import ge.biterium.chat.voice.Main;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import java.net.DatagramSocket;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public Button btnStartServer;

    public int port = 8888;
    public SourceDataLine outputAudio;
    PlayerThread playerThread;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void startServer(ActionEvent actionEvent) {
        init_audio();
    }

    public void init_audio() {
        try {
            AudioFormat format = VoiceFormat.getAudioFormat();
            DataLine.Info info_out = new DataLine.Info(SourceDataLine.class, format);
            if (!AudioSystem.isLineSupported((info_out))) {
                System.out.println("მიკროფონი ვერ იპოვა ");
                System.exit(0);
            }
            outputAudio = (SourceDataLine) AudioSystem.getLine(info_out);
            outputAudio.open(format);
            outputAudio.start();
            playerThread = new PlayerThread(new DatagramSocket(port), outputAudio);
            Main.calling = true;
            playerThread.start();
            btnStartServer.setDisable(true);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
