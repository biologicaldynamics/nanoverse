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

package compiler.pipeline.instantiate.loader.io.serialize.binary;

import compiler.pipeline.instantiate.loader.io.visual.*;
import control.GeneralParameters;
import geometry.Geometry;
import io.visual.Visualization;
import layers.LayerManager;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.function.Supplier;

/**
 * Created by dbborens on 8/21/2015.
 */
public class VisualizationSerializerDefaults {
    public String prefix() {
        return "img";
    }

    public Visualization visualization(LayerManager layerManager, GeneralParameters p) {
        Geometry geometry = layerManager.getCellLayer().getGeometry();

        if (geometry.getDimensionality() == 1) {
            KymographLoader loader = new KymographLoader();
            return loader.instantiate(p);
        } else if (geometry.getDimensionality() == 2) {
            MapVisualizationLoader loader = new MapVisualizationLoader();
            return loader.instantiate(p);
        } else {
            throw new NotImplementedException();
        }
    }
}
