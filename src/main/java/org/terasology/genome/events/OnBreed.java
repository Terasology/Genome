// Copyright 2021 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.genome.events;

import org.terasology.engine.entitySystem.entity.EntityRef;
import org.terasology.gestalt.entitysystem.event.Event;

public class OnBreed implements Event {
    private EntityRef organism1;
    private EntityRef organism2;
    private EntityRef offspring;

    public OnBreed(EntityRef organism1, EntityRef organism2, EntityRef offspring) {
        this.organism1 = organism1;
        this.organism2 = organism2;
        this.offspring = offspring;
    }

    public EntityRef getOrganism1() {
        return organism1;
    }

    public EntityRef getOrganism2() {
        return organism2;
    }

    public EntityRef getOffspring() {
        return offspring;
    }
}
