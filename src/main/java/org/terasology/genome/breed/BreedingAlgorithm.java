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
