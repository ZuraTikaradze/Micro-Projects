package ge.biterium.chat.voice.controller;

import javax.sound.sampled.AudioFormat;

public class VoiceFormat {
    public static AudioFormat getAudioFormat() {
        float sampleRate = 16000.0F;
        int sampleSizeInbits = 16;
        int channel = 1;
        boolean signed = true;
        boolean bigEndian = false;
        return new AudioFormat(sampleRate, sampleSizeInbits, channel, signed, bigEndian);
    }
}
