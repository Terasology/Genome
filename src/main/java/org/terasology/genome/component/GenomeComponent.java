// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.genome.component;

import org.terasology.engine.entitySystem.Component;
import org.terasology.engine.network.Replicate;
import org.terasology.engine.world.block.ForceBlockActive;
import org.terasology.engine.world.block.items.AddToBlockBasedItem;
import org.terasology.inventory.logic.ItemDifferentiating;

/**
 * A component that defines genome-related attributes of an entity.
 */
@ForceBlockActive
@AddToBlockBasedItem
public class GenomeComponent implements Component, ItemDifferentiating {
    /**
     * The type of the genome.
     */
    @Replicate
    public String genomeId;

    /**
     * The sequence of genes contained by the genome.
     */
    @Replicate
    public String genes;

    /**
     * Check whether this genome component is equal to a given object.
     *
     * @param o The object with which the genome component is to be compared
     * @return Whether the genome component is equal to the given object.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GenomeComponent that = (GenomeComponent) o;

        if (genes != null ? !genes.equals(that.genes) : that.genes != null) {
            return false;
        }
        return genomeId != null ? genomeId.equals(that.genomeId) : that.genomeId == null;
    }

    /**
     * Get the hash code of the genome component.
     *
     * @return The hash code of the genome component
     */
    @Override
    public int hashCode() {
        int result = genomeId != null ? genomeId.hashCode() : 0;
        result = 31 * result + (genes != null ? genes.hashCode() : 0);
        return result;
    }
}
