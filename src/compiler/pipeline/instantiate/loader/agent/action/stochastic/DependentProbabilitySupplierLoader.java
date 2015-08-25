/*
 * Copyright (c) 2014, 2015 David Bruce Borenstein and the
 * Trustees of Princeton University.
 *
 * This file is part of the Nanoverse simulation framework
 * (patent pending).
 *
 * This program is free software: you can redistribute it
 * and/or modify it under the terms of the GNU Affero General
 * Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * This program is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE.  See the GNU Affero General Public License for
 * more details.
 *
 * You should have received a copy of the GNU Affero General
 * Public License along with this program.  If not, see
 * <http://www.gnu.org/licenses/>.
 */

package compiler.pipeline.instantiate.loader.agent.action.stochastic;

import agent.action.stochastic.*;
import compiler.pipeline.instantiate.factory.agent.action.stochastic.DependentProbabilitySupplierFactory;
import compiler.pipeline.instantiate.loader.Loader;
import compiler.pipeline.translate.nodes.MapObjectNode;
import control.GeneralParameters;
import control.arguments.ProbabilitySupplierDescriptor;
import layers.LayerManager;

/**
 * Created by dbborens on 8/25/2015.
 */
public class DependentProbabilitySupplierLoader
    extends ProbabilitySupplierLoader<DependentProbabilitySupplierDescriptor> {

    private final DependentProbabilitySupplierFactory factory;
    private final DependentProbabilitySupplierInterpolator interpolator;

    public DependentProbabilitySupplierLoader() {
        factory = new DependentProbabilitySupplierFactory();
        interpolator = new DependentProbabilitySupplierInterpolator();
    }

    public DependentProbabilitySupplierLoader(
        DependentProbabilitySupplierFactory factory,
        DependentProbabilitySupplierInterpolator interpolator) {

        this.factory = factory;
        this.interpolator = interpolator;
    }
    @Override
    public DependentProbabilitySupplierDescriptor instantiate(MapObjectNode node,
                                                              LayerManager lm,
                                                              GeneralParameters p) {

        factory.setLayerManager(lm);

        String layerId = interpolator.layer(node);
        factory.setLayer(layerId);

        double offset = interpolator.offset(node, p.getRandom());
        factory.setOffset(offset);

        double coefficient = interpolator.coefficient(node, p.getRandom());
        factory.setCoefficient(coefficient);

        return factory.build();
    }
}
