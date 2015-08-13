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

package processes.discrete;

import control.arguments.*;
import geometry.set.CoordinateSet;
import structural.annotations.FactoryTarget;

/**
 * Created by dbborens on 11/23/14.
 */
public class CellProcessArguments {
    private CoordinateSet activeSites;
    private IntegerArgument maxTargets;

    @FactoryTarget
    public CellProcessArguments(CoordinateSet activeSites, IntegerArgument maxTargets) {
        this.activeSites = activeSites;
        this.maxTargets = maxTargets;
    }

    public CoordinateSet getActiveSites() {
        return activeSites;
    }

    public IntegerArgument getMaxTargets() {
        return maxTargets;
    }


}
