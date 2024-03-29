// Copyright 2021 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.genome.breed;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.terasology.engine.utilities.random.FastRandom;
import org.terasology.genome.breed.mutator.GeneMutator;

/**
 * A breeding algorithm that produces a diploid cross.
 */
public class DiploidBreedingAlgorithm implements BreedingAlgorithm {
    private static final Logger LOGGER = LoggerFactory.getLogger(DiploidBreedingAlgorithm.class);

    private BreedingRule breedingRule;
    private float mutationChance;
    private GeneMutator geneMutator;

    public DiploidBreedingAlgorithm(BreedingRule breedingRule, float mutationChance, GeneMutator geneMutator) {
        this.breedingRule = breedingRule;
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

        return breedingRule.canBreed(genes1, genes2);
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

        int genomeLength = genes1.length() / 2;

        FastRandom rand = new FastRandom();

        StringBuilder result = new StringBuilder(genomeLength);

        char[] chars1 = genes1.toCharArray();
        char[] chars2 = genes2.toCharArray();

        for (int i = 0; i < genomeLength; i++) {
            result.append(rand.nextBoolean() ? chars1[i * 2] : chars1[i * 2 + 1]);
            result.append(rand.nextBoolean() ? chars2[i * 2] : chars2[i * 2 + 1]);
        }

        if (mutationChance >= rand.nextFloat()) {
            int geneIndex = rand.nextInt(genomeLength * 2);

            char gene = geneMutator.mutateGene(rand.nextFloat(), geneIndex, result.charAt(geneIndex));
            result.replace(geneIndex, geneIndex + 1, "" + gene);
        }

        return null;
    }

    private boolean validateGenes(String genes1, String genes2) {
        if (genes1 == null || genes2 == null || genes1.length() != genes2.length()) {
            LOGGER.error("Genomes not defined or of incorrect length");
            return false;
        }
        return true;
    }
}
