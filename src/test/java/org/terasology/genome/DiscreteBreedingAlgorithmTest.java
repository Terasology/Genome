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
