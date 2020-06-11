/*
 * Copyright 2020 MovingBlocks
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.terasology.genome;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import com.google.common.base.Function;
import org.terasology.genome.breed.BreedingAlgorithm;
import org.terasology.genome.breed.MonoploidBreedingAlgorithm;
import org.terasology.genome.breed.mutator.GeneMutator;
import org.terasology.genome.breed.mutator.VocabularyGeneMutator;
import org.terasology.genome.genomeMap.GeneIndexGenomeMap;
import org.terasology.registry.In;
import org.terasology.world.WorldProvider;

import javax.annotation.Nullable;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Initial test setup based on working of Genomes before trait specific breeding algorithms were introduced
 * this test is now obsolete/serves no purpose
 */
class GeneIndexGenomeMapTest {

    @Test
    void testMethod() {
        int genomeLength = 1;

        //A=5, F=10, K=15
        GeneMutator geneMutator = new VocabularyGeneMutator("ABCDEFGHIJK");
        BreedingAlgorithm breedingAlgorithm = new MonoploidBreedingAlgorithm(0, 0.05f, geneMutator);
        GeneIndexGenomeMap genomeMap = new GeneIndexGenomeMap();
        int geneIndices[] = {0};
        genomeMap.addProperty("filling", geneIndices, Integer.class, breedingAlgorithm,
                new Function<String, Integer>() {
                    @Nullable
                    @Override
                    public Integer apply(@Nullable String input) {
                        return (input.charAt(0) - 'A' + 5);
                    }
                });

        GenomeDefinition genomeDefinition = new GenomeDefinition(breedingAlgorithm, genomeMap);
        GeneIndexGenomeMap genomeMap1 = (GeneIndexGenomeMap) genomeDefinition.getGenomeMap();
        assertEquals(genomeMap1.getPropertyDefinitionMap().toString(), genomeMap.getPropertyDefinitionMap().toString());
        assertEquals(genomeMap1.getProperty("filling", "Aa", Integer.class),5);
    }
}
