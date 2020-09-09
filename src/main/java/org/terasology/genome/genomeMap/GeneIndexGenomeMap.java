// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.genome.genomeMap;

import com.google.common.base.Function;
import org.terasology.genome.breed.BreedingAlgorithm;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * An implementation of GenomeMap.
 */
public class GeneIndexGenomeMap implements GenomeMap {
    private final Map<String, GenePropertyDefinition> propertyDefinitionMap = new LinkedHashMap<>();

    /**
     * Add a new named property to the genome map.
     *
     * @param propertyName The name of the property to be added
     * @param geneIndices The indices of the genes which the property depends
     * @param type The type of the property
     * @param breedingAlgorithm The breeding algorithm of the property
     * @param geneStringTransformation A function to transform the gene into a property value
     * @param <T> The class used for the property value
     */
    public <T> void addProperty(String propertyName, int[] geneIndices, Class<T> type,
                                BreedingAlgorithm breedingAlgorithm,
                                Function<String, T> geneStringTransformation) {
        propertyDefinitionMap.put(propertyName, new GenePropertyDefinition<T>(geneIndices, type, breedingAlgorithm,
                geneStringTransformation));
    }

    public <T> void addProperty(String propertyName, int[] geneIndices, Class<T> type,
                                Function<String, T> geneStringTransformation) {
        addProperty(propertyName, geneIndices, type, null, geneStringTransformation);
    }

    /**
     * Get the value of a named property for the specified genes of an organism.
     *
     * @param property Name of the property
     * @param genes Genes of the organism
     * @param type Type of the property
     * @param <T> The class used for the property value
     * @return The value of the property
     */
    @Override
    public <T> T getProperty(String property, String genes, Class<T> type) {
        GenePropertyDefinition<T> definition = propertyDefinitionMap.get(property);
        if (definition == null) {
            return null;
        }
        if (definition.type != type) {
            throw new IllegalArgumentException("Invalid type for property requested");
        }
        StringBuilder genesForProperty = new StringBuilder(definition.geneIndices.length);
        for (int i = 0; i < definition.geneIndices.length; i++) {
            genesForProperty.append(genes.charAt(definition.geneIndices[i]));
        }
        return definition.transformation.apply(genesForProperty.toString());
    }

    /**
     * Get the Property Definition Map for a Genome Map
     *
     * @return The property definition map
     */
    public final Map<String, GenePropertyDefinition> getPropertyDefinitionMap() {
        return propertyDefinitionMap;
    }

    public static final class GenePropertyDefinition<T> {
        public int[] geneIndices;
        public BreedingAlgorithm breedingAlgorithm;
        public Class<T> type;
        public Function<String, T> transformation;

        public GenePropertyDefinition(int[] geneIndices, Class<T> type, BreedingAlgorithm breedingAlgorithm,
                                      Function<String, T> transformation) {
            this.geneIndices = geneIndices;
            this.type = type;
            this.breedingAlgorithm = breedingAlgorithm;
            this.transformation = transformation;
        }
    }
}
