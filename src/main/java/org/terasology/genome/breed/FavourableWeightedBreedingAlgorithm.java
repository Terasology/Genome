/*
 * Copyright 2017 MovingBlocks
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

public class FavourableWeightedBreedingAlgorithm implements BreedingAlgorithm {
    private int minimumCrossSimilarity;
    private float mutationChance;
    private float weight;
    private GeneMutator geneMutator;

    public FavourableWeightedBreedingAlgorithm(int minimumCrossSimilarity, float mutationChance, float weight, GeneMutator geneMutator) {
        this.minimumCrossSimilarity = minimumCrossSimilarity;
        this.mutationChance = mutationChance;
        this.weight = weight;
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
     * @return       The genes of an offspring from the two parent organisms
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
            if (rand.nextInt(100) < (int) weight * 100) {
                // Choose the character with the greater ASCII value.
                result.append((int)chars1[i] >= (int)chars2[i] ? chars1[i] : chars2[i]);
            } else {
                // Choose the character with the lower ASCII value.
                result.append((int)chars1[i] >= (int)chars2[i] ? chars2[i] : chars1[i]);
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
