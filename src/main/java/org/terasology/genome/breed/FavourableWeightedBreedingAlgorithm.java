// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.genome.breed;

import org.terasology.engine.utilities.random.FastRandom;

public class FavourableWeightedBreedingAlgorithm implements BreedingAlgorithm {
    private final int minimumCrossSimilarity;
    private final float weight;
    private float mutationChance;

    public FavourableWeightedBreedingAlgorithm(int minimumCrossSimilarity, float weight) {
        this.minimumCrossSimilarity = minimumCrossSimilarity;
        this.mutationChance = mutationChance;
        this.weight = weight;
    }

    /**
     * Check whether two organisms with the given genes can breed.
     *
     * @param genes1 The genes of the first organism
     * @param genes2 T  he genes of the second organism
     * @return Whether the two organisms can breed
     */
    @Override
    public boolean canCross(String genes1, String genes2) {
        validateGenes(genes1, genes2);

        int genomeLength = genes1.length();

        int sameGenesCount = 0;
        char[] chars1 = genes1.toCharArray();
        char[] chars2 = genes2.toCharArray();

        for (int i = 0; i < genomeLength; i++) {
            if (chars1[i] == chars2[i]) {
                sameGenesCount++;
            }
        }

        return sameGenesCount >= minimumCrossSimilarity;
    }

    /**
     * Produces the genes of an offspring from parent organisms with the genes specified.
     *
     * @param genes1 The genes of the first parent organism
     * @param genes2 The genes of the second parent organism
     * @return The genes of an offspring from the two parent organisms
     */
    @Override
    public String produceCross(String genes1, String genes2) {
        if (!canCross(genes1, genes2)) {
            return null;
        }

        int genomeLength = genes1.length();

        FastRandom rand = new FastRandom();

        StringBuilder result = new StringBuilder(genomeLength);

        char[] chars1 = genes1.toCharArray();
        char[] chars2 = genes2.toCharArray();

        for (int i = 0; i < genomeLength; i++) {
            if (rand.nextInt(100) < (int) (weight * 100)) {
                // Choose the character with the greater ASCII value.
                result.append((int) chars1[i] >= (int) chars2[i] ? chars1[i] : chars2[i]);
            } else {
                // Choose the character with the lower ASCII value.
                result.append((int) chars1[i] >= (int) chars2[i] ? chars2[i] : chars1[i]);
            }
        }
        // TODO: Implement mutation?
        return result.toString();
    }

    private void validateGenes(String genes1, String genes2) {
        if (genes1 == null || genes2 == null || genes1.length() != genes2.length()) {
            throw new IllegalArgumentException("Genomes not defined or of incorrect length");
        }
    }
}
