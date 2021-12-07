// Copyright 2021 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.genome.breed;

/**
 * Defines how organisms of a type breed.
 */
public interface BreedingAlgorithm {
    /**
     * Check whether two organisms with the given genes can breed.
     *
     * @param genes1 The genes of the first organism
     * @param genes2 The genes of the second organism
     * @return       Whether the two organisms can breed
     */
    boolean canCross(String genes1, String genes2);

    /**
     * Produces the genes of an offspring from parent organisms with the genes specified.
     *
     * @param genes1 The genes of the first parent organism
     * @param genes2 The genes of the second parent organism
     * @return       The genes of an offspring from the two parent organisms
     */
    String produceCross(String genes1, String genes2);
}
