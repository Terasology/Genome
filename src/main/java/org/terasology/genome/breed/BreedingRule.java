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
