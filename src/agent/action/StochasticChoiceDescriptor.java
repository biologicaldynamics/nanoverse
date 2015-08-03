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
import control.arguments.DynamicActionRangeMapDescriptor;
import layers.LayerManager;
import structural.annotations.FactoryTarget;

import java.util.Random;
import java.util.function.Function;

/**
 * Created by dbborens on 8/3/2015.
 */
public class StochasticChoiceDescriptor extends ActionDescriptor<StochasticChoice> {
    private final Function<BehaviorCell, StochasticChoice> constructor;

    @FactoryTarget(displayName = "StochasticChoice")
    public StochasticChoiceDescriptor(LayerManager layerManager,
                                      DynamicActionRangeMapDescriptor chooser,
                                      Random random) {

        constructor = cell -> {
            DynamicActionRangeMap map = chooser.instantiate(cell);
            return new StochasticChoice(cell, layerManager, map, random);
        };
    }

    @Override
    protected Function<BehaviorCell, StochasticChoice> resolveConstructor() {
        return constructor;
    }
}
