// Copyright 2021 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0

package org.terasology.genome;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.base.Function;
import org.terasology.genome.breed.BreedingAlgorithm;
import org.terasology.genome.breed.MonoploidBreedingAlgorithm;
import org.terasology.genome.breed.mutator.GeneMutator;
import org.terasology.genome.breed.mutator.VocabularyGeneMutator;
import org.terasology.genome.genomeMap.GeneIndexGenomeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A test to check working of multiple traits with different breeding algorithms. Addition of multiple breeding
 * algorithms was based on the structure of these tests.
 */
class GenomeNewTest {

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
        assertEquals(genomeMap1.getPropertyDefinitionMap().toString(), genomeMap.getPropertyDefinitionMap().toString());
        assertEquals(genomeMap1.getProperty("filling", "Aa", Integer.class), 5);
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
        assertEquals(genomeMap1.getPropertyDefinitionMap().toString(), genomeMap.getPropertyDefinitionMap().toString());
        assertEquals(genomeMap1.getProperty("height", "TT", Integer.class), 1);
        assertEquals(genomeMap1.getProperty("height", "tt", Integer.class), 0);
        assertEquals(genomeMap1.getProperty("height", "Tt", Integer.class), 1);
    }

    @Test
    void testBreeding() {
        int counter = 0;
        String resultGenes = "";
        int geneIndex = 0;
        BreedingAlgorithm breedingAlgorithm = defaultBreedingAlgorithm;

        String g1 = "TTAF";
        String g2 = "ttKK";
        GeneIndexGenomeMap genomeMap = new GeneIndexGenomeMap();
        int geneDepends[] = new int[]{0, 1};
        int geneIndices[];

        String propertyType = "discrete";
        genomeMap.addProperty("height", geneDepends, Integer.class, defaultBreedingAlgorithm,
                new Function<String, Integer>() {
                    @Nullable
                    @Override
                    public Integer apply(@Nullable String input) {
                        return (Character.isUpperCase(input.charAt(0)) ? 1 : 0);
                    }
                });

        geneDepends = new int[]{2, 3};
        genomeMap.addProperty("filling", geneDepends, Integer.class, defaultBreedingAlgorithm,
                new Function<String, Integer>() {
                    @Nullable
                    @Override
                    public Integer apply(@Nullable String input) {
                        return (input.charAt(0) - 'A' + 5);
                    }
                });

        GenomeDefinition genomeDefinition = new GenomeDefinition(defaultBreedingAlgorithm, genomeMap);
        GeneIndexGenomeMap genomeMap1 = (GeneIndexGenomeMap) genomeDefinition.getGenomeMap();

        Map propertyDefinitionMap1 = new LinkedHashMap(genomeMap1.getPropertyDefinitionMap());
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
                resultGenes += "" + breedingAlgorithm.produceCross(g1.substring(geneIndex), g2.substring(geneIndex));
            }
            geneIndex += geneIndices.length;
        }
        assertNotEquals(resultGenes, "");
    }
}
