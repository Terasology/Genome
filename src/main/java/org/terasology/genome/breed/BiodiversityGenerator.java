// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.genome.breed;

import org.joml.Math;
import org.joml.RoundingMode;
import org.joml.Vector2ic;
import org.terasology.engine.utilities.procedural.Noise;
import org.terasology.engine.utilities.procedural.SimplexNoise;
import org.terasology.engine.utilities.random.FastRandom;
import org.terasology.genome.breed.mutator.GeneMutator;

/**
 * Assists in generating organisms that have diverse genes based on their spawn area.
 */
public class BiodiversityGenerator {
    private Noise[] mutationPositionNoises;
    private Noise[] mutationInputNoises;
    private String baseGenome;
    private GeneMutator geneMutator;
    private float areaDiversity;

    /**
     * Constructor preparing a range of noise and probabilities to be used by this generator.
     *
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
        mutationPositionNoises = new Noise[diversityMagnitude];
        mutationInputNoises = new Noise[diversityMagnitude];
        for (int i = 0; i < diversityMagnitude; i++) {
            mutationPositionNoises[i] = new SimplexNoise(random.nextInt());
            mutationInputNoises[i] = new SimplexNoise(random.nextInt());
        }
    }

    private float getValueFromNoise(Noise noise, float x, float y) {
        float val = noise.noise(x, y);
        return (float) (1.0 / (1.0 + Math.exp(-val))); // sigmoid function
    }

    /**
     * Generate the genes of an organism at a given world location which the organism is expected to inhabit.
     *
     * @param worldLocation The location which the organism is expected to inhabit
     * @return              The genes of an organism expected to inhabit the given location
     */
    public String generateGenes(Vector2ic worldLocation) {
        char[] result = baseGenome.toCharArray();
        for (int i = 0; i < mutationPositionNoises.length; i++) {
            int mutationPosition = Math.roundUsing(result.length
                    * getValueFromNoise(mutationPositionNoises[i], areaDiversity * worldLocation.x(), areaDiversity * worldLocation.y()), RoundingMode.FLOOR);
            float mutationInput = getValueFromNoise(mutationInputNoises[i], areaDiversity * worldLocation.x(), areaDiversity * worldLocation.y());
            result[mutationPosition] = geneMutator.mutateGene(mutationInput, mutationPosition, result[mutationPosition]);
        }
        return new String(result);
    }
}
