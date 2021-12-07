// Copyright 2021 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.genome.breed;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.terasology.engine.utilities.random.FastRandom;
import org.terasology.genome.breed.mutator.GeneMutator;

import java.util.Arrays;

/**
 * A breeding algorithm that produces a diploid cross for Discrete traits with a Punnet Square.
 */
public class DiscreteBreedingAlgorithm implements BreedingAlgorithm {
    private static final Logger LOGGER = LoggerFactory.getLogger(DiscreteBreedingAlgorithm.class);

    private float mutationChance;
    private GeneMutator geneMutator;

    public DiscreteBreedingAlgorithm(float mutationChance, GeneMutator geneMutator) {
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

        return (genes1.length() == genes2.length());
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
        int punnetCounter = 0;

        FastRandom rand = new FastRandom();

        StringBuilder result = new StringBuilder(genomeLength);

        char[] chars1 = genes1.toCharArray();
        char[] chars2 = genes2.toCharArray();
        char[] resultGenes;

        String[] punnetSquare = new String[4];
        String alleles = "";
        String resultant;

        for (int i = 0; i < genomeLength; i++) {
            alleles = "" + chars1[i];
            for (int j = 0; j < genomeLength; j++) {
                alleles = alleles + "" + chars2[j];
                punnetSquare[punnetCounter++] = alleles;
                alleles = "" + alleles.charAt(0);
            }
        }

        resultGenes = punnetSquare[rand.nextInt(3)].toCharArray();
        Arrays.sort(resultGenes);
        resultant = new String(resultGenes);

        result.append(resultant);

        if (mutationChance >= rand.nextFloat()) {
            int geneIndex = rand.nextInt(genomeLength);
            char gene = geneMutator.mutateGene(rand.nextFloat(), geneIndex, result.charAt(geneIndex));
            result.replace(geneIndex, geneIndex + 1, "" + gene);
        }

        resultGenes = result.toString().toCharArray();
        Arrays.sort(resultGenes);
        result = new StringBuilder(new String(resultGenes));

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
