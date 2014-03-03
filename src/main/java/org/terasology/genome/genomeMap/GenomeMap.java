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
package org.terasology.genome.genomeMap;

/**
 * Defines how the genes of an organism translate into properties defined for that organism.
 * Provides access to named property values for specified genes.
 *
 * @author Marcin Sciesinski <marcins78@gmail.com>
 */
public interface GenomeMap {
    /**
     * Gets the value of the named property for the specified genes of an organism.
     *
     * @param property Name of the property.
     * @param genes    Genes of the organism.
     * @param type     Type of the property.
     * @param <T>      Class used for the property value.
     * @return The value of the property.
     */
    <T> T getProperty(String property, String genes, Class<T> type);
}
