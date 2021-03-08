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
package org.terasology.genome.component;

import org.terasology.engine.entitySystem.Component;
import org.terasology.engine.logic.inventory.ItemDifferentiating;
import org.terasology.engine.network.Replicate;
import org.terasology.engine.world.block.ForceBlockActive;
import org.terasology.engine.world.block.items.AddToBlockBasedItem;

/**
 * A component that defines genome-related attributes of an entity.
 */
@ForceBlockActive
@AddToBlockBasedItem
public class GenomeComponent implements Component, ItemDifferentiating {
    /** The type of the genome. */
    @Replicate
    public String genomeId;

    /** The sequence of genes contained by the genome. */
    @Replicate
    public String genes;

    /**
     * Check whether this genome component is equal to a given object.
     *
     * @param o The object with which the genome component is to be compared
     * @return  Whether the genome component is equal to the given object.
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
        if (genomeId != null ? !genomeId.equals(that.genomeId) : that.genomeId != null) {
            return false;
        }

        return true;
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
