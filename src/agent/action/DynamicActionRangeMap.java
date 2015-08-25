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

import agent.action.stochastic.ProbabilitySupplier;
import cells.BehaviorCell;
import layers.LayerManager;
import structural.annotations.FactoryTarget;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dbborens on 4/27/14.
 */
public class DynamicActionRangeMap {

    private final Map<Action, ProbabilitySupplier> functionMap;
    private final LayerManager layerManager;

    private ActionRangeMap valueMap;

    public DynamicActionRangeMap(Map<Action, ProbabilitySupplier> functionMap,
                                 LayerManager layerManager) {

        this.functionMap = functionMap;
        this.layerManager = layerManager;
    }

    public DynamicActionRangeMap(LayerManager layerManager) {
        functionMap = new HashMap<>();
        this.layerManager = layerManager;
    }

    public void add(Action action, ProbabilitySupplier supplier) {
        functionMap.put(action, supplier);
    }

    public void refresh() {
        valueMap = new ActionRangeMap(functionMap.size());
        functionMap.forEach((action, supplier) -> {
            double value = supplier.get();
            valueMap.add(action, value);
        });
    }

    public Action selectTarget(double x) {
        return valueMap.selectTarget(x);
    }

    public double getTotalWeight() {
        return valueMap.getTotalWeight();
    }

    public DynamicActionRangeMap clone(BehaviorCell child) {
        DynamicActionRangeMap cloned = new DynamicActionRangeMap(layerManager);

        functionMap.forEach((action, supplier) -> {
            Action clonedKey = action.clone(child);
            ProbabilitySupplier clonedValue = supplier.clone(child);
            cloned.add(clonedKey, clonedValue);
        });

        return cloned;
    }
}
