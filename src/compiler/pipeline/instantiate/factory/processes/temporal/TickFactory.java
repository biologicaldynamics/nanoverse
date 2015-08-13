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
package compiler.pipeline.instantiate.factory.processes.temporal;

import control.arguments.Argument;
import control.arguments.DoubleArgument;
import processes.temporal.Tick;
import processes.BaseProcessArguments;
import compiler.pipeline.instantiate.factory.Factory;

public class TickFactory implements Factory<Tick> {

    private final TickFactoryHelper helper;

    private BaseProcessArguments arguments;
    private DoubleArgument dt;

    public TickFactory() {
        helper = new TickFactoryHelper();
    }

    public TickFactory(TickFactoryHelper helper) {
        this.helper = helper;
    }

    public void setArguments(BaseProcessArguments arguments) {
        this.arguments = arguments;
    }

    public void setDt(DoubleArgument dt) {
        this.dt = dt;
    }

    @Override
    public Tick build() {
        return helper.build(arguments, dt);
    }
}