// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.genome.genomeMap;

/**
 * Defines how the genes of an organism translate into properties defined for that organism, and provides access to
 * named property values for specified genes.
 */
public interface GenomeMap {
    /**
     * Get the value of a named property for the specified genes of an organism.
     *
     * @param property Name of the property
     * @param genes Genes of the organism
     * @param type Type of the property
     * @param <T> Class used for the property value
     * @return The value of the property
     */
    <T> T getProperty(String property, String genes, Class<T> type);
}
