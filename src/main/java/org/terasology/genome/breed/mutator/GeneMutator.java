// Copyright 2021 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.genome.breed.mutator;

/**
 * A class that provides mechanism for mutating a gene.
 */
public interface GeneMutator {
    /**
     * Returns a gene to replace the gene specified after mutation.
     *
     * @param input     Input into the mutator algorithm, value in [0, 1) range.
     * @param geneIndex Index of the gene being mutated.
     * @param geneValue Gene value before mutation.
     * @return          New gene to replaced the mutated gene.
     */
    char mutateGene(float input, int geneIndex, char geneValue);
}
