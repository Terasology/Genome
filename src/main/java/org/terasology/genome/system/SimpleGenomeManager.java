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
import org.terasology.entitySystem.event.EventPriority;
import org.terasology.entitySystem.event.ReceiveEvent;
import org.terasology.entitySystem.systems.BaseComponentSystem;
import org.terasology.entitySystem.systems.RegisterSystem;
import org.terasology.genome.GenomeDefinition;
import org.terasology.genome.GenomeRegistry;
import org.terasology.genome.component.GenomeComponent;
import org.terasology.genome.events.OnBreed;
import org.terasology.registry.In;
import org.terasology.registry.Share;

/**
 * A simple genome manager implementation.
 */
@RegisterSystem
@Share(GenomeManager.class)
public class SimpleGenomeManager extends BaseComponentSystem implements GenomeManager {
    @In
    private GenomeRegistry genomeRegistry;

    @ReceiveEvent(priority = EventPriority.PRIORITY_CRITICAL)
    public void Breed(OnBreed event, EntityRef entity, GenomeComponent genomeComponent) {
        EntityRef organism1 = event.getOrganism1();
        EntityRef organism2 = event.getOrganism2();
        EntityRef offspring = event.getOffspring();

        if (organism1.hasComponent(GenomeComponent.class) && organism2.hasComponent(GenomeComponent.class)) {
            applyBreeding(organism1, organism2, offspring);
        }
    }

    /**
     * Returns whether the specified two organisms can breed.
     *
     * @param organism1 The first organism
     * @param organism2 The second organism
     * @return          Whether the two organisms can breed
     */
    @Override
    public boolean canBreed(EntityRef organism1, EntityRef organism2) {
        GenomeComponent genome1 = organism1.getComponent(GenomeComponent.class);
        GenomeComponent genome2 = organism2.getComponent(GenomeComponent.class);

        return canBreedInternal(genome1, genome2);
    }

    /**
     * Applies genetic information to the offspring by breeding the two given organisms. If the two organisms cannot
     * breed, <code>false</code> will be returned, and no changes will be made to the offspring.
     *
     * @param organism1 The first organism
     * @param organism2 The second organism
     * @param offspring The offspring
     * @return          Whether the two organisms were bred successfully and the resulting information was applied to the offspring
     */

    //this method will need to be changed so traits are bred according to their own breeding algorithms, if nothing is specified then default can be used
    @Override
    public boolean applyBreeding(EntityRef organism1, EntityRef organism2, EntityRef offspring) {
        GenomeComponent genome1 = organism1.getComponent(GenomeComponent.class);
        GenomeComponent genome2 = organism2.getComponent(GenomeComponent.class);

        if (!canBreedInternal(genome1, genome2)) {
            return false;
        }

        GenomeDefinition genomeDefinition = genomeRegistry.getGenomeDefinition(genome1.genomeId);

        //here breed by getting breeding algorithm per gene, otherwise use defaultBreedingAlgorithm
        String resultGenes = genomeDefinition.getDefaultBreedingAlgorithm().produceCross(genome1.genes, genome2.genes);
        if (resultGenes == null) {
            return false;
        }

        GenomeComponent resultGenome = new GenomeComponent();
        resultGenome.genomeId = genome1.genomeId;
        resultGenome.genes = resultGenes;

        offspring.addOrSaveComponent(resultGenome);

        return true;
    }

    private boolean canBreedInternal(GenomeComponent genome1, GenomeComponent genome2) {
        if (genome1 == null || genome2 == null
                || !genome1.genomeId.equals(genome2.genomeId)) {
            return false;
        }

        GenomeDefinition genomeDefinition = genomeRegistry.getGenomeDefinition(genome1.genomeId);
        if (genomeDefinition == null) {
            return false;
        }

        return genomeDefinition.getDefaultBreedingAlgorithm().canCross(genome1.genes, genome2.genes);
    }

    /**
     * Returns the named property for that organism, as defined by GenomeMap its genome was registered with.
     *
     * @param organism     The organism whose property is to be fetched
     * @param propertyName The name of the property to be fetched
     * @return             The property of the organism with the given name
     */
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

    //add a method here getGenomeBreedingAlgorithm. Add in the interface as well
    //add getPropertyType method too
}
