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

import org.terasology.entitySystem.Component;
import org.terasology.logic.inventory.ItemDifferentiating;
import org.terasology.network.Replicate;
import org.terasology.world.block.ForceBlockActive;
import org.terasology.world.block.items.AddToBlockBasedItem;

/**
 * @author Marcin Sciesinski <marcins78@gmail.com>
 */
@ForceBlockActive
@ItemDifferentiating
@AddToBlockBasedItem
@Replicate
public class GenomeComponent implements Component {
    public String genomeId;
    public String genes;

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
}
