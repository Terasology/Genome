// Copyright 2021 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.genome.genomeMap;

import com.google.common.base.Function;
import org.terasology.engine.registry.CoreRegistry;
import org.terasology.engine.utilities.random.FastRandom;
import org.terasology.engine.world.WorldProvider;
import org.terasology.genome.breed.BreedingAlgorithm;
import org.terasology.genome.util.CombinatoricsUtils;

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
     * @param minGeneIndex The minimum index of a gene that can affect the property
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

    public <T> void addSeedBasedProperty(String propertyName, int minGeneIndex, int maxGeneIndex, int codeLength,
                                         Class<T> type, Function<String, T> geneStringTransformation) {
        addSeedBasedProperty(propertyName, minGeneIndex, maxGeneIndex, codeLength, type, null,
                geneStringTransformation);
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

    public <T> void addSeedBasedProperty(String propertyName, int genomeLength, int codeLength, Class<T> type,
                                         Function<String, T> geneStringTransformation) {
        addSeedBasedProperty(propertyName, 0, genomeLength, codeLength, type, null,
                geneStringTransformation);
    }
}
