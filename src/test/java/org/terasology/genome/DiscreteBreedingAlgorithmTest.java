// Copyright 2021 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0

package org.terasology.genome;

import org.junit.jupiter.api.Test;
import org.terasology.genome.breed.BreedingAlgorithm;
import org.terasology.genome.breed.DiscreteBreedingAlgorithm;
import org.terasology.genome.breed.mutator.GeneMutator;
import org.terasology.genome.breed.mutator.VocabularyGeneMutator;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class DiscreteBreedingAlgorithmTest {
    @Test
    void testMethod() {
        GeneMutator geneMutator = new VocabularyGeneMutator("Tt");
        BreedingAlgorithm breedingAlgorithm = new DiscreteBreedingAlgorithm(0, geneMutator);
        String genes1 = "TT";
        String genes2 = "Tt";
        List<String> possibleGenes = new ArrayList<>();
        possibleGenes.add("TT");
        possibleGenes.add("Tt");
        // Make sure to add 'tt' to the possibleGenes list if mutationChance of the breeding algorithm is greater than 0
        String resultGenes = breedingAlgorithm.produceCross(genes1, genes2);
        assertEquals(resultGenes.length(), genes1.length());
        assertTrue(possibleGenes.contains(resultGenes));
    }
}
