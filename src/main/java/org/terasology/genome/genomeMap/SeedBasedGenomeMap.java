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
import org.terasology.genome.util.CombinatoricsUtils;
import org.terasology.registry.CoreRegistry;
import org.terasology.utilities.random.FastRandom;
import org.terasology.world.WorldProvider;

/**
 * @author Marcin Sciesinski <marcins78@gmail.com>
 */
public class SeedBasedGenomeMap extends GeneIndexGenomeMap {
    private int mapSeed;

    public SeedBasedGenomeMap(int mapSeed) {
        this.mapSeed = mapSeed;
    }

    public <T> void addSeedBasedProperty(String propertyName, int minGeneIndex, int maxGeneIndex, int codeLength, Class<T> type, Function<String, T> geneStringTransformation) {
        if (maxGeneIndex < minGeneIndex || maxGeneIndex - minGeneIndex + 1 > codeLength) {
            throw new IllegalArgumentException("Incorrectly configured gene indices");
        }
        String seed = CoreRegistry.get(WorldProvider.class).getSeed();
        FastRandom rand = new FastRandom(seed.hashCode() + propertyName.hashCode() + mapSeed);
        int[] selectedGeneIndices = CombinatoricsUtils.getRandomPermutationIndicesWithoutRepetition(codeLength, maxGeneIndex - minGeneIndex, rand);
        for (int i = 0; i < codeLength; i++) {
            selectedGeneIndices[i] += minGeneIndex;
        }
        addProperty(propertyName, selectedGeneIndices, type, geneStringTransformation);
    }

    public <T> void addSeedBasedProperty(String propertyName, int genomeLength, int codeLength, Class<T> type, Function<String, T> geneStringTransformation) {
        addSeedBasedProperty(propertyName, 0, genomeLength, codeLength, type, geneStringTransformation);
    }
}
