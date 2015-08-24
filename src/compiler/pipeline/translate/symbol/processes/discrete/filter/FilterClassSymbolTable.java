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

package compiler.pipeline.translate.symbol.processes.discrete.filter;

import compiler.pipeline.translate.symbol.*;
import processes.discrete.filter.Filter;

import java.util.HashMap;
import java.util.function.Supplier;

/**
 * Created by dbborens on 7/23/2015.
 */
public class FilterClassSymbolTable extends ClassSymbolTable<Filter> {
    @Override
    public String getDescription() {
        return "Filters restrict the targets for processes (system-wide, top-" +
            "down events) according to a specific rule.";
    }

    @Override
    protected HashMap<String, Supplier<InstantiableSymbolTable>> resolveSubclasses() {
        HashMap<String, Supplier<InstantiableSymbolTable>> ret = new HashMap<>();
        cellState(ret);
        composite(ret);
        depth(ret);
        nullFilter(ret);
        return ret;
    }

    private void cellState(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> st = CellStateFilterInstSymbolTable::new;
        ret.put("CellState", st);
    }

    private void composite(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> st = CompositeFilterInstSymbolTable::new;
        ret.put("Composite", st);
    }

    private void depth(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> st = DepthFilterInstSymbolTable::new;
        ret.put("Depth", st);
    }

    private void nullFilter(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> st = NullFilterInstSymbolTable::new;
        ret.put("Null", st);
    }

}
