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

package compiler.pipeline.instantiate.loader.geometry.set;

import compiler.pipeline.instantiate.factory.geometry.set.DiscCoordinateSetFactory;
import compiler.pipeline.translate.nodes.ObjectNode;
import control.GeneralParameters;
import geometry.set.DiscSet;
import layers.LayerManager;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by dbborens on 8/4/2015.
 */
public class DiscCoordinateSetLoader extends CoordinateSetLoader<DiscSet> {
    private final DiscCoordinateSetFactory factory;

    public DiscCoordinateSetLoader() {
        factory = new DiscCoordinateSetFactory();
    }

    public DiscCoordinateSetLoader(DiscCoordinateSetFactory factory) {
        this.factory = factory;
    }

    @Override
    public DiscSet instantiate(ObjectNode o, LayerManager lm, GeneralParameters p) {
        throw new NotImplementedException();
    }
}
