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
package compiler.pipeline.instantiate.factory.layers.continuum.solvers;

import layers.continuum.ContinuumLayerContent;
import layers.continuum.ScheduledOperations;
import layers.continuum.solvers.EquilibriumSolver;
import layers.continuum.solvers.EquilibriumMatrixSolver;
import compiler.pipeline.instantiate.factory.Factory;

public class EquilibriumSolverFactory implements Factory<EquilibriumSolver> {

    private final EquilibriumSolverFactoryHelper helper;

    private ContinuumLayerContent content;
    private ScheduledOperations so;
    private EquilibriumMatrixSolver steadyState;

    public EquilibriumSolverFactory() {
        helper = new EquilibriumSolverFactoryHelper();
    }

    public EquilibriumSolverFactory(EquilibriumSolverFactoryHelper helper) {
        this.helper = helper;
    }

    public void setContent(ContinuumLayerContent content) {
        this.content = content;
    }

    public void setSo(ScheduledOperations so) {
        this.so = so;
    }

    public void setSteadyState(EquilibriumMatrixSolver steadyState) {
        this.steadyState = steadyState;
    }

    @Override
    public EquilibriumSolver build() {
        return helper.build(content, so, steadyState);
    }
}