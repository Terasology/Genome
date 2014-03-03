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

import org.terasology.genome.breed.mutator.GeneMutator;
import org.terasology.math.TeraMath;
import org.terasology.math.Vector2i;
import org.terasology.utilities.procedural.Noise2D;
import org.terasology.utilities.procedural.SimplexNoise;
import org.terasology.utilities.random.FastRandom;

/**
 * Assists in generating organisms that have diverse genes based on their spawn area.
 *
 * @author Marcin Sciesinski <marcins78@gmail.com>
 */
public class BiodiversityGenerator {
    private Noise2D[] mutationPositionNoises;
    private Noise2D[] mutationInputNoises;
    private String baseGenome;
    private GeneMutator geneMutator;
    private float areaDiversity;

    /**
     * @param worldSeed          World seed.
     * @param diversitySeed      Diversity seed - unique value for each instance of this class that is used to populate organisms.
     * @param geneMutator        Mutator used to mutate genes of this one species-group.
     * @param baseGenome         Base genome (genes) for this one species-group.
     * @param diversityMagnitude How diverse the gene pool for this species-group is, the higher value the more diverse
     *                           the organisms are.
     * @param areaDiversity      How frequently the genes change based on the position of the spawn, the higher the value
     *                           the more diverse organisms close to each other will be.
     */
    public BiodiversityGenerator(String worldSeed, int diversitySeed, GeneMutator geneMutator, String baseGenome,
                                 int diversityMagnitude, float areaDiversity) {
        this.geneMutator = geneMutator;
        this.areaDiversity = areaDiversity;

        FastRandom random = new FastRandom(worldSeed.hashCode() + diversitySeed);

        this.baseGenome = baseGenome;
        mutationPositionNoises = new Noise2D[diversityMagnitude];
        mutationInputNoises = new Noise2D[diversityMagnitude];
        for (int i = 0; i < diversityMagnitude; i++) {
            mutationPositionNoises[i] = new SimplexNoise(random.nextInt());
            mutationInputNoises[i] = new SimplexNoise(random.nextInt());
        }
    }

    public String generateGenes(Vector2i worldLocation) {
        char[] result = baseGenome.toCharArray();
        for (int i = 0; i < mutationPositionNoises.length; i++) {
            int mutationPosition = TeraMath.floorToInt(result.length *
                    mutationPositionNoises[i].noise(areaDiversity * worldLocation.x, areaDiversity * worldLocation.y));
            float mutationInput = (float) mutationInputNoises[i].noise(areaDiversity * worldLocation.x, areaDiversity * worldLocation.y);
            result[mutationPosition] = geneMutator.mutateGene(mutationInput, mutationPosition, result[mutationPosition]);
        }
        return new String(result);
    }
}
