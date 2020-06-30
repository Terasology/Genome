// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0

package org.terasology.genome.util;

import org.terasology.entitySystem.entity.EntityRef;
import org.terasology.genome.component.GenomeComponent;
import org.terasology.logic.characters.CharacterHeldItemComponent;
import org.terasology.logic.console.commandSystem.annotations.Command;
import org.terasology.logic.console.commandSystem.annotations.Sender;
import org.terasology.network.ClientComponent;

public class GenomeCommands {
    @Command(shortDescription = "Prints genome of held item if possible.")
    public String heldGenomeCheck(@Sender EntityRef client) {
        EntityRef character = client.getComponent(ClientComponent.class).character;
        if (character.hasComponent(CharacterHeldItemComponent.class)) {
            EntityRef selectedItem = character.getComponent(CharacterHeldItemComponent.class).selectedItem;
            if (selectedItem.hasComponent(GenomeComponent.class)) {
                return selectedItem.getComponent(GenomeComponent.class).genes;
            } else {
                return "Held item does not have a Genome Component";
            }
        } else {
            return "Command not valid for current conditions.";
        }
    }
}
