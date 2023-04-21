package com.wordweaver.util;

import java.util.List;
import java.util.Random;

public class RandomSelector {
    private final Random random;

    public RandomSelector() {
        this.random = new Random();
    }

    // Constructor to allow for a seed to be passed in for testing purposes.
    public RandomSelector(long seed) {
        this.random = new Random(seed);
    }

    // Method to select a weighted random word from a list of words and their frequencies.
    public String selectWeightedRandom(List<String> words, List<Integer> frequencies) {
        if (words == null || frequencies == null || words.size() != frequencies.size()) {
            throw new IllegalArgumentException("Words and frequencies must be non-null and of the same size.");
        }

        int totalWeight = frequencies.stream().mapToInt(Integer::intValue).sum();
        int randomValue = random.nextInt(totalWeight);
        int accumulatedWeight = 0;

        for (int i = 0; i < words.size(); i++) {
            accumulatedWeight += frequencies.get(i);
            if (randomValue < accumulatedWeight) {
                return words.get(i);
            }
        }

        // This should never be reached if the input is valid.
        throw new IllegalStateException("Failed to select a weighted random word.");
    }
}
