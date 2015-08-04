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

package agent.targets;

import cells.BehaviorCell;
import layers.LayerManager;
import processes.discrete.filter.Filter;
import structural.annotations.FactoryTarget;

import java.util.Random;
import java.util.function.Function;

/**
 * Created by dbborens on 8/4/2015.
 */
public class TargetOccupiedNeighborsDescriptor extends TargetDescriptor<TargetOccupiedNeighbors> {
    private final Function<BehaviorCell, TargetOccupiedNeighbors> constructor;

    @FactoryTarget(displayName = "TargetOccupiedNeighbors")
    public TargetOccupiedNeighborsDescriptor(LayerManager layerManager, Filter filter, int maximum, Random random) {
        constructor = cell -> new TargetOccupiedNeighbors(cell, layerManager, filter, maximum, random);
    }

    @Override
    protected Function<BehaviorCell, TargetOccupiedNeighbors> getConstructor() {
        return constructor;
    }
}
