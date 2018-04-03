package ge.biterium.chat.voice.controller;

import ge.biterium.chat.voice.Main;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javax.sound.sampled.*;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public Button btnCall;
    public Button btnEndCall;
    public Label lblHeader;

    InetAddress ipAdress;
    TargetDataLine inputAudio;
    public RecorderThread recorderThread;
    public int port = 8888;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void startCall(ActionEvent actionEvent) {
        String txt = lblHeader.textProperty().get();
        lblHeader.textProperty().set("Calling ...");
        audioCall();
    }

    public void endCall(ActionEvent actionEvent) {
        String txt = lblHeader.textProperty().get();
        lblHeader.textProperty().set("Call End !");
        Main.calling = false;
        btnCall.setDisable(false);
        btnEndCall.setDisable(true);
    }

    public void audioCall() {
        AudioFormat format = VoiceFormat.getAudioFormat();
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
        if (!AudioSystem.isLineSupported(info)) {
            System.out.println("not Support - zura");
            System.exit(0);
        }
        try {
            inputAudio = (TargetDataLine) AudioSystem.getLine(info);
            inputAudio.open(format);
            inputAudio.start();
            ipAdress = InetAddress.getByName("localhost");
            recorderThread = new RecorderThread(inputAudio, new DatagramSocket(), ipAdress, port);
            Main.calling = true;
            recorderThread.start();
            btnCall.setDisable(true);
            btnEndCall.setDisable(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
