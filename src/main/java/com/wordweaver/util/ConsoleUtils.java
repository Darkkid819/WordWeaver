package com.wordweaver.util;


public class ConsoleUtils {

    public static void printLoadingBar(String prefix, int currentProgress, int totalProgress) {
        int totalSteps = 20;
        int currentSteps = (int) ((double) currentProgress / totalProgress * totalSteps);

        StringBuilder bar = new StringBuilder(prefix + " <");
        for (int i = 0; i < totalSteps; i++) {
            if (i < currentSteps) {
                bar.append("#");
            } else {
                bar.append("-");
            }
        }
        bar.append(">");

        System.out.print("\r" + bar.toString());
        System.out.flush();
    }

}