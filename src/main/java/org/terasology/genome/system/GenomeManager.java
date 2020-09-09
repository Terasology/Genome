// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.genome.system;

import org.terasology.engine.entitySystem.entity.EntityRef;

/**
 * Provides a set of utility methods used for both: breeding the organisms, as well as extracting property of an
 * organism.
 */
public interface GenomeManager {
    /**
     * Returns whether the specified two organisms can breed.
     *
     * @param organism1 The first organism
     * @param organism2 The second organism
     * @return Whether the two organisms can breed
     */
    boolean canBreed(EntityRef organism1, EntityRef organism2);

    /**
     * Applies genetic information to the offspring by breeding the two given organisms. If the two organisms cannot
     * breed, <code>false</code> will be returned, and no changes will be made to the offspring.
     *
     * @param organism1 The first organism
     * @param organism2 The second organism
     * @param offspring The offspring
     * @return Whether the two organisms were bred successfully and the resulting information was applied to the
     *         offspring
     */
    boolean applyBreeding(EntityRef organism1, EntityRef organism2, EntityRef offspring);

    /**
     * Returns the named property for that organism, as defined by GenomeMap its genome was registered with.
     *
     * @param organism The organism whose property is to be fetched
     * @param propertyName The name of the property to be fetched
     * @return The property of the organism with the given name
     */
    <T> T getGenomeProperty(EntityRef organism, String propertyName, Class<T> type);
}
