// Copyright 2021 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.genome.breed.mutator;

import com.google.common.base.Predicate;
import org.terasology.math.TeraMath;

/**
 * A simple implementation of the GeneMutator interface.
 */
public class VocabularyGeneMutator implements GeneMutator {
    private char[] vocabulary;
    private Predicate<Integer> mutableGeneIndices;

    public VocabularyGeneMutator(String genesVocabulary) {
        vocabulary = genesVocabulary.toCharArray();
    }

    public VocabularyGeneMutator(String genesVocabulary, Predicate<Integer> mutableGeneIndices) {
        vocabulary = genesVocabulary.toCharArray();
        this.mutableGeneIndices = mutableGeneIndices;
    }

    /**
     * Returns a gene to replace the gene specified after mutation.
     *
     * @param input     Input into the mutator algorithm, value in [0, 1) range.
     * @param geneIndex Index of the gene being mutated.
     * @param geneValue Gene value before mutation.
     * @return          New gene to replaced the mutated gene.
     */
    @Override
    public char mutateGene(float input, int geneIndex, char geneValue) {
        if (mutableGeneIndices == null || mutableGeneIndices.apply(geneIndex)) {
            return vocabulary[TeraMath.floorToInt(input * vocabulary.length)];
        } else {
            return geneValue;
        }
    }
}
