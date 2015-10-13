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

package nanoverse.runtime.agent.action;

import nanoverse.runtime.agent.targets.*;
import nanoverse.runtime.agent.BehaviorCell;
import nanoverse.runtime.control.arguments.IntegerArgument;
import nanoverse.runtime.layers.LayerManager;
import nanoverse.runtime.structural.annotations.FactoryTarget;

import java.util.function.Function;

/**
 * Created by dbborens on 8/3/2015.
 */
public class TriggerDescriptor extends ActionDescriptor<Trigger> {

    private final Function<BehaviorCell, Trigger> constructor;

    @FactoryTarget(displayName = "Trigger")
    public TriggerDescriptor(LayerManager layerManager,
                             String behaviorName,
                             TargetDescriptor ruleDescriptor,
                             IntegerArgument selfChannel,
                             IntegerArgument targetChannel) {

        constructor = cell -> {
            TargetRule targetRule = ruleDescriptor.instantiate(cell);
            return new Trigger(cell, layerManager, behaviorName,
                targetRule, selfChannel, targetChannel);
        };
    }

    @Override
    protected Function<BehaviorCell, Trigger> resolveConstructor() {
        return constructor;
    }
}
