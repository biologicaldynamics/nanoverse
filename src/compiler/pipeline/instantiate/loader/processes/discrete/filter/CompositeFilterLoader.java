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

package compiler.pipeline.instantiate.loader.processes.discrete.filter;

import compiler.pipeline.instantiate.factory.processes.discrete.filter.CompositeFilterFactory;
import compiler.pipeline.translate.nodes.*;
import control.GeneralParameters;
import layers.LayerManager;
import layers.cell.CellLayer;
import processes.discrete.filter.*;

import java.util.stream.Stream;

/**
 * Created by dbborens on 8/24/2015.
 */
public class CompositeFilterLoader extends FilterLoader<CompositeFilter> {

    private final CompositeFilterFactory factory;
    private final CompositeFilterInterpolator interpolator;

    public CompositeFilterLoader() {
        factory = new CompositeFilterFactory();
        interpolator = new CompositeFilterInterpolator();
    }

    public CompositeFilterLoader(CompositeFilterFactory factory,
                                 CompositeFilterInterpolator interpolator) {

        this.factory = factory;
        this.interpolator = interpolator;
    }

    @Override
    public CompositeFilter instantiate(MapObjectNode node,
                                       LayerManager lm,
                                       GeneralParameters p) {

        Stream<Filter> children = interpolator.including(node, lm, p);
        factory.setChildren(children);

        return factory.build();
    }

}
