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
package org.terasology.genome.genomeMap;

import com.google.common.base.Function;
import org.terasology.genome.breed.BreedingAlgorithm;
import org.terasology.genome.util.CombinatoricsUtils;
import org.terasology.registry.CoreRegistry;
import org.terasology.utilities.random.FastRandom;
import org.terasology.world.WorldProvider;

/**
 * An extension of the GeneIndexGenomeMap class which adds seed based properties. Seed based properties are properties
 * which are calculated using a pseudo-random generator which uses the game's seed.
 */


public class SeedBasedGenomeMap extends GeneIndexGenomeMap {
    private int mapSeed;

    public SeedBasedGenomeMap(int mapSeed) {
        this.mapSeed = mapSeed;
    }

    /**
     * Add a new seed based (pseudo-random) property.
     *
     * @param propertyName The name of the property
     * @param minGeneIndex The minimum index of a which can affect the property
     * @param maxGeneIndex The maximum index of a gene that can affect the property
     * @param codeLength The maximum length of a gene sequence that can affect the property
     * @param type The type of the property
     * @param breedingAlgorithm The breeding algorithm of the property
     * @param geneStringTransformation A function that transforms a gene into a property value
     * @param <T> The class used for the property
     */
    public <T> void addSeedBasedProperty(String propertyName, int minGeneIndex, int maxGeneIndex, int codeLength,
                                         Class<T> type, BreedingAlgorithm breedingAlgorithm,
                                         Function<String, T> geneStringTransformation) {
        if (maxGeneIndex < minGeneIndex || maxGeneIndex - minGeneIndex + 1 < codeLength) {
            throw new IllegalArgumentException("Incorrectly configured gene indices");
        }
        String seed = CoreRegistry.get(WorldProvider.class).getSeed();
        FastRandom rand = new FastRandom(seed.hashCode() + propertyName.hashCode() + mapSeed);
        int[] selectedGeneIndices = CombinatoricsUtils.getRandomPermutationIndicesWithoutRepetition(codeLength,
                maxGeneIndex - minGeneIndex, rand);
        for (int i = 0; i < codeLength; i++) {
            selectedGeneIndices[i] += minGeneIndex;
        }
        addProperty(propertyName, selectedGeneIndices, type, breedingAlgorithm, geneStringTransformation);
    }

    /**
     * Add a new seed based (pseudo-random) property.
     *
     * @param propertyName The name of the property
     * @param codeLength The maximum length of a gene sequence that can affect the property
     * @param type The type of the property
     * @param breedingAlgorithm The breeding algorithm of the property
     * @param geneStringTransformation A function that transforms a gene into a property value
     * @param <T> The class used for the property
     */
    public <T> void addSeedBasedProperty(String propertyName, int genomeLength, int codeLength, Class<T> type,
                                         BreedingAlgorithm breedingAlgorithm,
                                         Function<String, T> geneStringTransformation) {
        addSeedBasedProperty(propertyName, 0, genomeLength, codeLength, type, breedingAlgorithm,
                geneStringTransformation);
    }
}
