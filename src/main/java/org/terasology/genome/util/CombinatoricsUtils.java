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
package org.terasology.genome.util;

import org.terasology.utilities.random.FastRandom;

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
     * @param max   The maximum value of the indices
     * @param rand  A FastRandom generator
     * @return      An array of length <code>count</code> containing random indices with maximum value <code>max</code>
     */
    public static int[] getRandomPermutationIndicesWithoutRepetition(int count, int max, FastRandom rand) {
        if (count > max + 1) {
            throw new IllegalArgumentException("Count cannot be larger than max");
        }

        // The slow way, but good enough for what we need for now
        List<Integer> lookup = new ArrayList<>();
        for (int i = 0; i < max; i++) {
            lookup.add(i);
        }

        int[] result = new int[count];
        for (int i = 0; i < count; i++) {
            result[i] = lookup.remove(rand.nextInt(count - i));
        }
        return result;
    }
}
