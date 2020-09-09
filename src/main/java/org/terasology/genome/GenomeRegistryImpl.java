// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.genome;

import org.terasology.engine.entitySystem.systems.BaseComponentSystem;
import org.terasology.engine.entitySystem.systems.RegisterSystem;
import org.terasology.engine.registry.Share;

import java.util.HashMap;
import java.util.Map;

/**
 * A basic implementation of the Genome Registry interface.
 */
@RegisterSystem
@Share(GenomeRegistry.class)
public class GenomeRegistryImpl extends BaseComponentSystem implements GenomeRegistry {
    private final Map<String, GenomeDefinition> genomeDefinitionMap = new HashMap<>();

    /**
     * Register a new organism type.
     *
     * @param typeId Unique type of an organism
     * @param genomeDefinition Definition of the genome for that organism
     */
    @Override
    public void registerType(String typeId, GenomeDefinition genomeDefinition) {
        genomeDefinitionMap.put(typeId, genomeDefinition);
    }

    /**
     * Deregister an organism type.
     *
     * @param typeId Unique type of organism
     */
    @Override
    public void deregisterType(String typeId) {
        genomeDefinitionMap.remove(typeId);
    }

    /**
     * Get the genome definition of a given organism type.
     *
     * @param typeId Type of an organism
     * @return The genome definition of the organism type
     */
    @Override
    public GenomeDefinition getGenomeDefinition(String typeId) {
        return genomeDefinitionMap.get(typeId);
    }
}
