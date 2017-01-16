/*
 * Copyright 2014 MovingBlocks
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.terasology.genome.system;

import org.terasology.entitySystem.entity.EntityRef;

/**
 * Provides a set of utility methods used for both: breeding the organisms, as well as extracting property of an organism.
 */
public interface GenomeManager {
    /**
     * Returns whether the specified two organisms can breed.
     *
     * @param organism1 The first organism
     * @param organism2 The second organism
     * @return          Whether the two organisms can breed
     */
    boolean canBreed(EntityRef organism1, EntityRef organism2);

    /**
     * Applies genetic information to the offspring by breeding the two given organisms. If the two organisms cannot
     * breed, <code>false</code> will be returned, and no changes will be made to the offspring.
     *
     * @param organism1 The first organism
     * @param organism2 The second organism
     * @param offspring The offspring
     * @return          Whether the two organisms were bred successfully and the resulting information was applied to the offspring
     */
    boolean applyBreeding(EntityRef organism1, EntityRef organism2, EntityRef offspring);

    /**
     * Returns the named property for that organism, as defined by GenomeMap its genome was registered with.
     *
     * @param organism     The organism whose property is to be fetched
     * @param propertyName The name of the property to be fetched
     * @return             The property of the organism with the given name
     */
    <T> T getGenomeProperty(EntityRef organism, String propertyName, Class<T> type);
}
