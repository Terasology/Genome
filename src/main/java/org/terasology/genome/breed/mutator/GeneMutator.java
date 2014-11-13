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
package org.terasology.genome.breed.mutator;

/**
 * Class providing mechanism for mutating a gene.
 *
 * @author Marcin Sciesinski <marcins78@gmail.com>
 */
public interface GeneMutator {
    /**
     * Returns a gene to replace the gene specified after mutation.
     *
     * @param input     Input into the mutator algorithm, value in [0, 1) range.
     * @param geneIndex Index of the gene being mutated.
     * @param geneValue Gene value before mutation.
     * @return New gene to replaced the mutated gene.
     */
    char mutateGene(float input, int geneIndex, char geneValue);
}
