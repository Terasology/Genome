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
package org.terasology.genome.genomeMap;

import com.google.common.base.Function;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Marcin Sciesinski <marcins78@gmail.com>
 */
public class GeneIndexGenomeMap implements GenomeMap {
    private Map<String, GenePropertyDefinition> propertyDefinitionMap = new HashMap<>();

    public <T> void addProperty(String propertyName, int[] geneIndices, Class<T> type, Function<String, T> geneStringTransformation) {
        propertyDefinitionMap.put(propertyName, new GenePropertyDefinition<T>(geneIndices, type, geneStringTransformation));
    }

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
        return (T) definition.transformation.apply(genesForProperty.toString());
    }

    private static class GenePropertyDefinition<T> {
        private int[] geneIndices;
        private Class<T> type;
        private Function<String, T> transformation;

        private GenePropertyDefinition(int[] geneIndices, Class<T> type, Function<String, T> transformation) {
            this.geneIndices = geneIndices;
            this.type = type;
            this.transformation = transformation;
        }
    }
}
