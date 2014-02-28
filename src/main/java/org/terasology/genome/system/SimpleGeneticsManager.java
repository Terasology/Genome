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
package org.terasology.genome.system;

import org.terasology.entitySystem.entity.EntityRef;
import org.terasology.entitySystem.systems.BaseComponentSystem;
import org.terasology.entitySystem.systems.RegisterSystem;
import org.terasology.genome.GenomeDefinition;
import org.terasology.genome.GenomeRegistry;
import org.terasology.genome.component.GenomeComponent;
import org.terasology.registry.In;
import org.terasology.registry.Share;

/**
 * @author Marcin Sciesinski <marcins78@gmail.com>
 */
@RegisterSystem
@Share(value = {GeneticsManager.class})
public class SimpleGeneticsManager extends BaseComponentSystem implements GeneticsManager {
    @In
    private GenomeRegistry genomeRegistry;

    @Override
    public boolean canBreed(EntityRef organism1, EntityRef organism2) {
        GenomeComponent genome1 = organism1.getComponent(GenomeComponent.class);
        GenomeComponent genome2 = organism2.getComponent(GenomeComponent.class);

        if (genome1 == null || genome2 == null
                || !genome1.genomeId.equals(genome2.genomeId)) {
            return false;
        }

        GenomeDefinition genomeDefinition = genomeRegistry.getGenomeDefinition(genome1.genomeId);
        if (genomeDefinition == null) {
            return false;
        }

        return genomeDefinition.getBreedingAlgorithm().canCross(genome1.genes, genome2.genes);
    }

    @Override
    public boolean applyBreeding(EntityRef organism1, EntityRef organism2, EntityRef offspring) {
        if (!canBreed(organism1, organism2)) {
            return false;
        }

        GenomeComponent genome1 = organism1.getComponent(GenomeComponent.class);
        GenomeComponent genome2 = organism2.getComponent(GenomeComponent.class);
        GenomeDefinition genomeDefinition = genomeRegistry.getGenomeDefinition(genome1.genomeId);

        String resultGenes = genomeDefinition.getBreedingAlgorithm().produceCross(genome1.genes, genome2.genes);
        if (resultGenes == null) {
            return false;
        }

        GenomeComponent resultGenome = new GenomeComponent();
        resultGenome.genomeId = genome1.genomeId;
        resultGenome.genes = resultGenes;

        offspring.addComponent(resultGenome);

        return true;
    }

    @Override
    public <T> T getGenomeProperty(EntityRef organism, String propertyName, Class<T> type) {
        GenomeComponent genome = organism.getComponent(GenomeComponent.class);
        if (genome == null) {
            return null;
        }

        GenomeDefinition genomeDefinition = genomeRegistry.getGenomeDefinition(genome.genomeId);
        if (genomeDefinition == null) {
            return null;
        }

        return genomeDefinition.getGenomeMap().getProperty(propertyName, genome.genes, type);
    }
}
