// Copyright 2021 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.genome;

/**
 * Registry used for registering organism types.
 */
public interface GenomeRegistry {
    /**
     * Registers the organism type.
     *
     * @param typeId           Unique type of an organism
     * @param genomeDefinition Definition of the genome for that organism
     */
    void registerType(String typeId, GenomeDefinition genomeDefinition);

    /**
     * Deregisters the organism type.
     *
     * @param typeId Unique type of organism
     */
    void deregisterType(String typeId);

    /**
     * Returns a genome definition for the specified organism type.
     *
     * @param typeId Type of an organism
     * @return       GenomeDefinition for that organism type
     */
    GenomeDefinition getGenomeDefinition(String typeId);
}
