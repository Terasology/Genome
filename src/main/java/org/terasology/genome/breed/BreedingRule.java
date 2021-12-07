// Copyright 2021 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.genome.breed;

/**
 * A rule used to check if two genomes can be crossed for breeding.
 */
public interface BreedingRule {
    /**
     * Check whether two given genomes can be crossed for breeding.
     *
     * @param genome1 The first genome
     * @param genome2 The second genome
     * @return        Whether the two genomes can be crossed
     */
    boolean canBreed(String genome1, String genome2);
}
