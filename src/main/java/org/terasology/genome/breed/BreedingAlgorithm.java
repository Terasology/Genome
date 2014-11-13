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
 * Defines how organisms of that type breed.
 *
 * @author Marcin Sciesinski <marcins78@gmail.com>
 */
public interface BreedingAlgorithm {
    /**
     * Specifies if two organism with these genes can breed.
     *
     * @param genes1
     * @param genes2
     * @return
     */
    boolean canCross(String genes1, String genes2);

    /**
     * Produces genes of an offspring for parent organisms with the genes specified.
     *
     * @param genes1
     * @param genes2
     * @return
     */
    String produceCross(String genes1, String genes2);
}
