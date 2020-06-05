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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.base.Function;
import org.terasology.entitySystem.systems.BaseComponentSystem;
import org.terasology.entitySystem.systems.RegisterSystem;
import org.terasology.genome.GenomeDefinition;
import org.terasology.genome.GenomeRegistry;
import org.terasology.genome.breed.BreedingAlgorithm;
import org.terasology.genome.breed.MonoploidBreedingAlgorithm;
import org.terasology.genome.breed.mutator.GeneMutator;
import org.terasology.genome.breed.mutator.VocabularyGeneMutator;
import org.terasology.genome.genomeMap.GeneIndexGenomeMap;
import org.terasology.genome.genomeMap.SeedBasedGenomeMap;
import org.terasology.registry.In;
import org.terasology.world.WorldProvider;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A test to check working of multiple traits with different breeding algorithms. Addition of multiple breeding
 * algorithms was based on the structure of these tests.
 */
class GenomeNewTest {
    @In
    WorldProvider worldProvider;

    @In
    GenomeRegistry genomeRegistry;

    GeneMutator geneMutator;
    BreedingAlgorithm defaultBreedingAlgorithm;
    float mutationChance;

    @BeforeEach
    public void setUp() {
        //A=5, F=10, K=15
        geneMutator = new VocabularyGeneMutator("ABCDEFGHIJK");
        mutationChance = 0.05f;
        defaultBreedingAlgorithm = new MonoploidBreedingAlgorithm(0, mutationChance, geneMutator);
    }

    @Test
    void testContinuousTrait() {
        GeneIndexGenomeMap genomeMap = new GeneIndexGenomeMap();
        int geneIndices[] = new int[]{0};
        genomeMap.addProperty("filling", geneIndices, Integer.class, defaultBreedingAlgorithm,
                new Function<String, Integer>() {
                    @Nullable
                    @Override
                    public Integer apply(@Nullable String input) {
                        return (input.charAt(0) - 'A' + 5);
                    }
                });

        GenomeDefinition genomeDefinition = new GenomeDefinition(defaultBreedingAlgorithm, genomeMap);
        GeneIndexGenomeMap genomeMap1 = (GeneIndexGenomeMap) genomeDefinition.getGenomeMap();
        assertEquals(genomeMap1.propertyDefinitionMap.toString(), genomeMap.propertyDefinitionMap.toString());
        assertEquals(genomeMap1.getProperty("filling","Aa",Integer.class),5);
    }

    @Test
    void testDiscreteTrait() {
        GeneIndexGenomeMap genomeMap = new GeneIndexGenomeMap();
        int geneIndices[] = new int[]{0, 1};
        genomeMap.addProperty("height", geneIndices, Integer.class, defaultBreedingAlgorithm,
                new Function<String, Integer>() {
                    @Nullable
                    @Override
                    public Integer apply(@Nullable String input) {
                        return (Character.isUpperCase(input.charAt(0)) ? 1 : 0);
                    }
                });

        GenomeDefinition genomeDefinition = new GenomeDefinition(defaultBreedingAlgorithm, genomeMap);
        GeneIndexGenomeMap genomeMap1 = (GeneIndexGenomeMap) genomeDefinition.getGenomeMap();
        assertEquals(genomeMap1.propertyDefinitionMap.toString(), genomeMap.propertyDefinitionMap.toString());
        assertEquals(genomeMap1.getProperty("height", "TT", Integer.class), 1);
        assertEquals(genomeMap1.getProperty("height", "tt", Integer.class), 0);
        assertEquals(genomeMap1.getProperty("height", "Tt", Integer.class),1);
    }

    @Test
    void testBreeding() {
        int counter = 0;
        String resultGenes = "";
        int geneIndex = 0;
        BreedingAlgorithm breedingAlgorithm = defaultBreedingAlgorithm;

        String g1 = "TT";
        String g2 = "tt";
        GeneIndexGenomeMap genomeMap = new GeneIndexGenomeMap();
        int geneIndices[] = new int[]{0, 1};
        String propertyType = "discrete";
        genomeMap.addProperty("height", geneIndices, Integer.class, defaultBreedingAlgorithm,
                new Function<String, Integer>() {
                    @Nullable
                    @Override
                    public Integer apply(@Nullable String input) {
                        return (Character.isUpperCase(input.charAt(0)) ? 1 : 0);
                    }
                });

        GenomeDefinition genomeDefinition = new GenomeDefinition(defaultBreedingAlgorithm, genomeMap);
        GeneIndexGenomeMap genomeMap1 = (GeneIndexGenomeMap) genomeDefinition.getGenomeMap();

        Map propertyDefinitionMap1 = new LinkedHashMap(genomeMap1.propertyDefinitionMap);
        ArrayList<GeneIndexGenomeMap.GenePropertyDefinition> genePropertyDefinitions =
                new ArrayList(propertyDefinitionMap1.values());
        while (geneIndex != g1.length()) {
            geneIndices = genePropertyDefinitions.get(counter).geneIndices;
            breedingAlgorithm = genePropertyDefinitions.get(counter++).breedingAlgorithm;
            if (geneIndex + geneIndices.length < g1.length()) {
                resultGenes += "" + breedingAlgorithm.produceCross(g1.substring(geneIndex,
                        geneIndex + geneIndices.length), g2.substring(geneIndex,
                        geneIndex + geneIndices.length));
            } else {
                System.out.println(resultGenes);
                resultGenes += "" + breedingAlgorithm.produceCross(g1.substring(geneIndex), g2.substring(geneIndex));
            }
            geneIndex += geneIndices.length;
        }
        assertNotEquals(resultGenes,"");
    }
}
