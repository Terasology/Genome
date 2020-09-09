// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0

package org.terasology.genome;

import com.google.common.base.Function;
import org.junit.jupiter.api.Test;
import org.terasology.genome.breed.BreedingAlgorithm;
import org.terasology.genome.breed.MonoploidBreedingAlgorithm;
import org.terasology.genome.breed.mutator.GeneMutator;
import org.terasology.genome.breed.mutator.VocabularyGeneMutator;
import org.terasology.genome.genomeMap.GeneIndexGenomeMap;

import javax.annotation.Nullable;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Initial test setup based on working of Genomes before trait specific breeding algorithms were introduced this test is
 * now obsolete/serves no purpose
 */
class GeneIndexGenomeMapTest {

    @Test
    void testMethod() {
        int genomeLength = 1;

        //A=5, F=10, K=15
        GeneMutator geneMutator = new VocabularyGeneMutator("ABCDEFGHIJK");
        BreedingAlgorithm breedingAlgorithm = new MonoploidBreedingAlgorithm(0, 0.05f, geneMutator);
        GeneIndexGenomeMap genomeMap = new GeneIndexGenomeMap();
        int[] geneIndices = {0};
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
        assertEquals(genomeMap1.getProperty("filling", "Aa", Integer.class), 5);
    }
}
