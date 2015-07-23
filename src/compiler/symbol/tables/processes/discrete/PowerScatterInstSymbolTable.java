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

package compiler.symbol.tables.processes.discrete;

import compiler.symbol.symbols.MemberSymbol;
import compiler.symbol.tables.ResolvingSymbolTable;
import compiler.symbol.tables.agent.AgentDescriptorClassSymbolTable;
import compiler.symbol.tables.processes.discrete.cluster.ScatterClustersHelperClassSymbolTable;
import processes.discrete.PowerScatter;

import java.util.HashMap;

/**
 * Created by dbborens on 7/21/2015.
 */
public class PowerScatterInstSymbolTable extends DiscreteProcessInstSymbolTable<PowerScatter> {
    @Override
    public String getDescription() {
        return "Scatter groups of cells in a distribution of cluster sizes " +
                "that may or may not follow a power law. (Check this before using)";
    }

    @Override
    protected HashMap<String, MemberSymbol> resolveMembers() {
        HashMap<String, MemberSymbol> ret =  super.resolveMembers();
        description(ret);
        separation(ret);
        return ret;
    }

    private void separation(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new ScatterClustersHelperClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "Separation rule for clusters.");
        ret.put("separation", ms);
    }

    private void description(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new AgentDescriptorClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "A template for the agents to be scattered by this process.");
        ret.put("description", ms);
    }
}
