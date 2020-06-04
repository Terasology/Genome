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

import static org.junit.jupiter.api.Assertions.*;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

class GenomeNewTest {
    @In
    WorldProvider worldProvider;

    @In
    GenomeRegistry genomeRegistry;

    @Test
    void testContinuousTrait() {
        int genomeLength = 1;
        float mutationChance = 0.05f;
        //A=5, F=10, K=15
        GeneMutator geneMutator = new VocabularyGeneMutator("ABCDEFGHIJK");
        BreedingAlgorithm breedingAlgorithm = new MonoploidBreedingAlgorithm(0, mutationChance, geneMutator);
        GeneIndexGenomeMap genomeMap = new GeneIndexGenomeMap();
        int geneIndices[] = new int[]{0};
        //change the next line to include the type of trait
        genomeMap.addProperty("filling", geneIndices, Integer.class, breedingAlgorithm,
                new Function<String, Integer>() {
                    @Nullable
                    @Override
                    public Integer apply(@Nullable String input) {
                        return (input.charAt(0) - 'A' + 5);
                    }
                });

        //change this to defaultBreedingAlgorithm
        GenomeDefinition genomeDefinition = new GenomeDefinition(breedingAlgorithm, genomeMap);
        GeneIndexGenomeMap genomeMap1 = (GeneIndexGenomeMap) genomeDefinition.getGenomeMap();
        assertEquals(genomeMap1.propertyDefinitionMap.toString(), genomeMap.propertyDefinitionMap.toString());
        //System.out.println(genomeMap1.propertyDefinitionMap);
        System.out.println(genomeMap1.getProperty("filling", "Aa", Integer.class));
        //also need to change everywhere to default breeding algorithm because the breeding algorithm will be defined
        // per trait
    }

    @Test
    void testDiscreteTrait() {
        int genomeLength = 1;
        String g1 = "AF";
        String g2 = "TA";
        float mutationChance = 0.05f;
        //A=5, F=10, K=15
        GeneMutator geneMutator = new VocabularyGeneMutator("ABCDEFGHIJK");
        BreedingAlgorithm breedingAlgorithm = new MonoploidBreedingAlgorithm(0, mutationChance, geneMutator);
        GeneIndexGenomeMap genomeMap = new GeneIndexGenomeMap();
        int geneIndices[] = new int[]{0, 1};
        String propertyType = "discrete";
        //change the next line to include the type of trait
        genomeMap.addProperty("height", geneIndices, Integer.class, breedingAlgorithm,
                new Function<String, Integer>() {
                    @Nullable
                    @Override
                    public Integer apply(@Nullable String input) {
                        return (Character.isUpperCase(input.charAt(0)) ? 1 : 0);
                    }
                });

        //change this to defaultBreedingAlgorithm
        GenomeDefinition genomeDefinition = new GenomeDefinition(breedingAlgorithm, genomeMap);
        GeneIndexGenomeMap genomeMap1 = (GeneIndexGenomeMap) genomeDefinition.getGenomeMap();
        assertEquals(genomeMap1.propertyDefinitionMap.toString(), genomeMap.propertyDefinitionMap.toString());
        assertEquals(genomeMap1.getProperty("height", "TT", Integer.class), 1);
        assertEquals(genomeMap1.getProperty("height", "tt", Integer.class), 0);
        //System.out.println(genomeMap1.propertyDefinitionMap);
        System.out.println(genomeMap1.getProperty("height", "tt", Integer.class));
        //System.out.println(genomeDefinition.getDefaultBreedingAlgorithm().produceCross(g1, g2));

        int counter = 0;
        String resultGenes = "";
        int geneIndex = 0;

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

        System.out.println(resultGenes);
    }
}