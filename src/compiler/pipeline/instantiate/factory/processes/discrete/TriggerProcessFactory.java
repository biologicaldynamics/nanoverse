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
package compiler.pipeline.instantiate.factory.processes.discrete;

import processes.discrete.TriggerProcess;
import processes.discrete.CellProcessArguments;
import processes.discrete.filter.Filter;
import processes.BaseProcessArguments;
import compiler.pipeline.instantiate.factory.Factory;

public class TriggerProcessFactory implements Factory<TriggerProcess> {

    private final TriggerProcessFactoryHelper helper;

    private BaseProcessArguments arguments;
    private CellProcessArguments cpArguments;
    private String behaviorName;
    private Filter filter;
    private boolean skipVacant;
    private boolean requireNeighbors;

    public TriggerProcessFactory() {
        helper = new TriggerProcessFactoryHelper();
    }

    public TriggerProcessFactory(TriggerProcessFactoryHelper helper) {
        this.helper = helper;
    }

    public void setArguments(BaseProcessArguments arguments) {
        this.arguments = arguments;
    }

    public void setCpArguments(CellProcessArguments cpArguments) {
        this.cpArguments = cpArguments;
    }

    public void setBehaviorName(String behaviorName) {
        this.behaviorName = behaviorName;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    public void setSkipVacant(boolean skipVacant) {
        this.skipVacant = skipVacant;
    }

    public void setRequireNeighbors(boolean requireNeighbors) {
        this.requireNeighbors = requireNeighbors;
    }

    @Override
    public TriggerProcess build() {
        return helper.build(arguments, cpArguments, behaviorName, filter, skipVacant, requireNeighbors);
    }
}