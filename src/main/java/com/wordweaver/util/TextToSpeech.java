package com.wordweaver.util;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public final class TextToSpeech {
    private static final String VOICENAME = "kevin";
    private static Thread speechThread = null;
    private static Voice voice = null;

    public static void speak(String text) {
        // Stop the current thread if it's running
        stop();

        // Create a new Thread to handle the text-to-speech processing
        speechThread = new Thread(() -> {
            System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
            VoiceManager voiceManager = VoiceManager.getInstance();
            voice = voiceManager.getVoice(VOICENAME);

            if (voice == null) {
                System.err.println("Voice not found.");
                return;
            }

            voice.allocate();
            voice.speak(text);
            voice.deallocate();
        });

        // Close the thread when the application is closed
        speechThread.setDaemon(true);
        speechThread.start();
    }

    public static void stop() {
        if (speechThread != null && speechThread.isAlive() && voice != null) {
            voice.deallocate();
            speechThread.interrupt();
        }
    }

    public static boolean isSpeaking() {
        return speechThread != null && speechThread.isAlive();
    }
}