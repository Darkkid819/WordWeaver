package com.wordweaver.util;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public final class TextToSpeech {
    // default voice
    private static final String VOICENAME = "kevin";

    public static void speak(String text) {
        // Create a new thread to handle the text-to-speech processing
        Thread speechThread = new Thread(() -> {
            System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
            VoiceManager voiceManager = VoiceManager.getInstance();
            Voice voice = voiceManager.getVoice(VOICENAME);
            if (voice == null) {
                System.err.println("Voice not found.");
                return;
            }

            voice.allocate();
            voice.speak(text);
            voice.deallocate();
        });

        // closes thread when javafx application is closed
        speechThread.setDaemon(true);
        speechThread.start();
    }
}
