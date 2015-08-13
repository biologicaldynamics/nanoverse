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
package compiler.pipeline.instantiate.factory.processes.discrete.filter;

import control.arguments.Argument;
import control.arguments.IntegerArgument;
import processes.discrete.filter.DepthFilter;
import layers.cell.CellLayer;
import compiler.pipeline.instantiate.factory.Factory;

public class DepthFilterFactory implements Factory<DepthFilter> {

    private final DepthFilterFactoryHelper helper;

    private CellLayer layer;
    private IntegerArgument maxDepth;

    public DepthFilterFactory() {
        helper = new DepthFilterFactoryHelper();
    }

    public DepthFilterFactory(DepthFilterFactoryHelper helper) {
        this.helper = helper;
    }

    public void setLayer(CellLayer layer) {
        this.layer = layer;
    }

    public void setMaxDepth(IntegerArgument maxDepth) {
        this.maxDepth = maxDepth;
    }

    @Override
    public DepthFilter build() {
        return helper.build(layer, maxDepth);
    }
}