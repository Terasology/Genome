// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.genome.util;

import org.terasology.engine.utilities.random.FastRandom;

import java.util.ArrayList;
import java.util.List;

/**
 * Class containing utility functions pertaining to combinatorics.
 */
public final class CombinatoricsUtils {
    private CombinatoricsUtils() {
    }

    /**
     * Get random permutation indices without any repetition.
     *
     * @param count The number of indices desired
     * @param max The maximum value of the indices
     * @param rand A FastRandom generator
     * @return An array of length <code>count</code> containing random indices with maximum value <code>max</code>
     */
    public static int[] getRandomPermutationIndicesWithoutRepetition(int count, int max, FastRandom rand) {
        if (count > max + 1) {
            throw new IllegalArgumentException("Count cannot be larger than max");
        }

        // The slow way, but good enough for what we need for now
        List<Integer> lookup = new ArrayList<>();
        for (int i = 0; i <= max; i++) {
            lookup.add(i);
        }

        int[] result = new int[count];
        for (int i = 0; i < count; i++) {
            result[i] = lookup.remove(rand.nextInt(count - i));
        }
        return result;
    }
}
