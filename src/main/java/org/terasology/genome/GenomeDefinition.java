// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.genome;

import org.terasology.genome.breed.BreedingAlgorithm;
import org.terasology.genome.genomeMap.GenomeMap;

/**
 * This class defines common properties of a certain organism type, namely the default breeding algorithm and the genome
 * map.
 */
public class GenomeDefinition {
    private final BreedingAlgorithm defaultBreedingAlgorithm;
    private final GenomeMap genomeMap;

    public GenomeDefinition(BreedingAlgorithm defaultBreedingAlgorithm, GenomeMap genomeMap) {
        this.defaultBreedingAlgorithm = defaultBreedingAlgorithm;
        this.genomeMap = genomeMap;
    }

    /**
     * Get the default breeding algorithm used by this organism type.
     *
     * @return The default breeding algorithm used by this organism type
     */
    public BreedingAlgorithm getDefaultBreedingAlgorithm() {
        return defaultBreedingAlgorithm;
    }

    /**
     * Get the genome map of this organism type.
     *
     * @return The genome map of this organism type
     */
    public GenomeMap getGenomeMap() {
        return genomeMap;
    }
}
