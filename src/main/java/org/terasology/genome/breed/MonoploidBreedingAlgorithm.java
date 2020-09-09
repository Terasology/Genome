// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.genome.breed;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.terasology.engine.utilities.random.FastRandom;
import org.terasology.genome.breed.mutator.GeneMutator;

/**
 * A breeding algorithm that produces a monoploid (haploid) cross.
 */
public class MonoploidBreedingAlgorithm implements BreedingAlgorithm {
    private static final Logger LOGGER = LoggerFactory.getLogger(MonoploidBreedingAlgorithm.class);
    private final int minimumCrossSimilarity;
    private final float mutationChance;
    private final GeneMutator geneMutator;

    public MonoploidBreedingAlgorithm(int minimumCrossSimilarity, float mutationChance, GeneMutator geneMutator) {
        this.minimumCrossSimilarity = minimumCrossSimilarity;
        this.mutationChance = mutationChance;
        this.geneMutator = geneMutator;
    }

    /**
     * Check whether two organisms with the given genes can breed.
     *
     * @param genes1 The genes of the first organism
     * @param genes2 The genes of the second organism
     * @return Whether the two organisms can breed
     */
    @Override
    public boolean canCross(String genes1, String genes2) {
        if (!(validateGenes(genes1, genes2))) {
            return false;
        }

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
            result.append(rand.nextBoolean() ? chars1[i] : chars2[i]);
        }

        if (mutationChance >= rand.nextFloat()) {
            int geneIndex = rand.nextInt(genomeLength);
            char gene = geneMutator.mutateGene(rand.nextFloat(), geneIndex, result.charAt(geneIndex));
            result.replace(geneIndex, geneIndex + 1, "" + gene);
        }

        return result.toString();
    }

    private boolean validateGenes(String genes1, String genes2) {
        if (genes1 == null || genes2 == null || genes1.length() != genes2.length()) {
            LOGGER.error("Genomes not defined or of incorrect length");
            return false;
        }
        return true;
    }
}
