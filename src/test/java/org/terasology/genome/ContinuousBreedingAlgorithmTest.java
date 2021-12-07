// Copyright 2021 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0

package org.terasology.genome;

import org.junit.jupiter.api.Test;
import org.terasology.genome.breed.BreedingAlgorithm;
import org.terasology.genome.breed.ContinuousBreedingAlgorithm;
import org.terasology.genome.breed.mutator.GeneMutator;
import org.terasology.genome.breed.mutator.VocabularyGeneMutator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


class ContinuousBreedingAlgorithmTest {
    @Test
    void testMethod() {
        String geneVocabulary = "TAZEtz";
        GeneMutator geneMutator = new VocabularyGeneMutator(geneVocabulary);
        BreedingAlgorithm breedingAlgorithm = new ContinuousBreedingAlgorithm(0.3f, geneMutator);
        String genes1 = "TTAZZ";
        String genes2 = "TtAZz";
        String resultGenes = breedingAlgorithm.produceCross(genes1, genes2);

        assertEquals(resultGenes.length(), genes1.length());

        for (int i = 0; i < resultGenes.length(); i++) {
            assertNotEquals(geneVocabulary.indexOf(resultGenes.charAt(i)), -1);
        }
    }
}
