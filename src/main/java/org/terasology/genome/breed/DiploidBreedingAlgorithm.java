/*
 * Copyright 2014 MovingBlocks
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.terasology.genome.breed;

import org.terasology.genome.breed.mutator.GeneMutator;
import org.terasology.utilities.random.FastRandom;

/**
 * A breeding algorithm that produces a diploid cross.
 */
public class DiploidBreedingAlgorithm implements BreedingAlgorithm {
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
     * @return       Whether the two organisms can breed
     */
    @Override
    public boolean canCross(String genes1, String genes2) {
        validateGenes(genes1, genes2);

        return breedingRule.canBreed(genes1, genes2);
    }

    /**
     * Produces the genes of an offspring from parent organisms with the genes specified.
     *
     * @param genes1 The genes of the first parent organism
     * @param genes2 The genes of the second parent organism
     * @return       The genes of an offspring from the two parent organisms
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

    private void validateGenes(String genes1, String genes2) {
        if (genes1 == null || genes2 == null || genes1.length() != genes2.length() || (genes1.length() % 2) != 0) {
            throw new IllegalArgumentException("Genomes not defined or of incorrect length");
        }
    }
}
