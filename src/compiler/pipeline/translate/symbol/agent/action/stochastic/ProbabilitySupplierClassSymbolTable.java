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

package compiler.pipeline.translate.symbol.agent.action.stochastic;

import compiler.pipeline.translate.symbol.*;
import control.arguments.ProbabilitySupplierDescriptor;

import java.time.Instant;
import java.util.HashMap;
import java.util.function.Supplier;

/**
 * Created by dbborens on 8/25/2015.
 */
public class ProbabilitySupplierClassSymbolTable extends ClassSymbolTable<ProbabilitySupplierDescriptor> {

    @Override
    protected HashMap<String, Supplier<InstantiableSymbolTable>> resolveSubclasses() {
        HashMap<String, Supplier<InstantiableSymbolTable>> ret = new HashMap<>();
        constant(ret);
        dependent(ret);
        return ret;
    }

    private void dependent(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> supplier =
            DependentProbabilitySupplierInstSymbolTable::new;

        ret.put("Dependent", supplier);
    }

    private void constant(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> supplier =
            ConstantProbabilitySupplierInstSymbolTable::new;

        ret.put("Constant", supplier);
    }

    @Override
    public String getDescription() {
        return "Represents the probability that a particular stochastic " +
            "option will be chosen. Soon to be replaced with an alternative " +
            "approach based on waiting time.";
    }
}
