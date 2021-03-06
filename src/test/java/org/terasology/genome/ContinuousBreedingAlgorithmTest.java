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
