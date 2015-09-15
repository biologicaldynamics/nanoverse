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

package compiler.pipeline.instantiate.loader.processes;

import compiler.pipeline.instantiate.helpers.LoadHelper;
import compiler.pipeline.translate.nodes.MapObjectNode;
import control.arguments.IntegerArgument;

import java.util.Random;

/**
 * Created by dbborens on 8/26/2015.
 */
public class BaseProcessArgumentsInterpolator {

    private final LoadHelper load;
    private final BaseProcessArgumentsDefaults defaults;

    public BaseProcessArgumentsInterpolator() {
        load = new LoadHelper();
        defaults = new BaseProcessArgumentsDefaults();
    }

    public BaseProcessArgumentsInterpolator(LoadHelper load,
                                            BaseProcessArgumentsDefaults defaults) {

        this.load = load;
        this.defaults = defaults;
    }

    public IntegerArgument period(MapObjectNode node, Random random) {
        return load.anIntegerArgument(node, "period", random, defaults::period);
    }

    public IntegerArgument start(MapObjectNode node, Random random) {
        return load.anIntegerArgument(node, "start", random, defaults::start);
    }
}
