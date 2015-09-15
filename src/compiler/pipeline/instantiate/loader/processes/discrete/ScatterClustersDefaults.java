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

package compiler.pipeline.instantiate.loader.processes.discrete;

import compiler.pipeline.instantiate.loader.agent.AgentDescriptorLoader;
import compiler.pipeline.instantiate.loader.processes.discrete.cluster.StrictSeparationClusterHelperLoader;
import control.GeneralParameters;
import control.arguments.CellDescriptor;
import control.arguments.ConstantInteger;
import control.arguments.IntegerArgument;
import layers.LayerManager;
import processes.discrete.cluster.ScatterClustersHelper;

/**
 * Created by dbborens on 8/27/2015.
 */
public class ScatterClustersDefaults {
    public CellDescriptor description(LayerManager lm, GeneralParameters p) {
        AgentDescriptorLoader loader = new AgentDescriptorLoader();
        return loader.instantiate(lm, p);
    }

    public ScatterClustersHelper helper(LayerManager lm, GeneralParameters p) {
        StrictSeparationClusterHelperLoader loader = new StrictSeparationClusterHelperLoader();
        return loader.instantiate(lm, p);
    }

    public IntegerArgument neighbors() {
        return new ConstantInteger(2);
    }
}
