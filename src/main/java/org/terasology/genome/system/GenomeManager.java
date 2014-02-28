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
 * @author Marcin Sciesinski <marcins78@gmail.com>
 */
public interface GenomeManager {
    /**
     * Returns whether the specified two organisms can breed.
     *
     * @return
     */
    boolean canBreed(EntityRef organism1, EntityRef organism2);

    /**
     * Applies genetic information to the offspring by breeding the two specified organisms. If the two organisms cannot
     * breed, <code>false</code> value will be returned and no changes will be applied to offspring.
     *
     * @return
     */
    boolean applyBreeding(EntityRef organism1, EntityRef organism2, EntityRef offspring);

    /**
     * Returns the named property for that organism, as defined by GenomeMap its genome was registered with.
     *
     * @return
     */
    <T> T getGenomeProperty(EntityRef organism, String propertyName, Class<T> type);
}
