/*
 * Copyright 2017 MovingBlocks
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
package org.terasology.genome.events;

import org.terasology.engine.entitySystem.entity.EntityRef;
import org.terasology.engine.entitySystem.event.Event;

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
