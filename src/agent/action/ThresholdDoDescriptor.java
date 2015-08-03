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

package agent.action;

import cells.BehaviorCell;
import control.arguments.DoubleArgument;
import layers.LayerManager;
import structural.annotations.FactoryTarget;

import java.util.function.Function;

/**
 * Created by dbborens on 8/3/2015.
 */
public class ThresholdDoDescriptor extends ActionDescriptor<ThresholdDo> {

    private final Function<BehaviorCell,ThresholdDo> constructor;

    @FactoryTarget(displayName = "ThresholdDo")
    public ThresholdDoDescriptor(LayerManager layerManager,
                                 String layerId,
                                 DoubleArgument minimumArg,
                                 DoubleArgument maximumArg,
                                 ActionDescriptor childDescriptor) {

        constructor = cell -> {
            Action child = childDescriptor.instantiate(cell);
            return new ThresholdDo(cell, layerManager, layerId, minimumArg,
                    maximumArg, child);
        };
    }

    @Override
    protected Function<BehaviorCell, ThresholdDo> resolveConstructor() {
        return constructor;
    }
}
