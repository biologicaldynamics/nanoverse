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

package compiler.pipeline.translate.symbol.processes.discrete.cluster;

import compiler.pipeline.translate.symbol.ClassSymbolTable;
import compiler.pipeline.translate.symbol.tables.ClassSymbolTableTest;
import org.junit.*;
import processes.discrete.cluster.CompactSeparatedClustersHelper;
import processes.discrete.cluster.ContactClustersHelper;
import processes.discrete.cluster.ScatterClustersHelper;
import processes.discrete.cluster.StrictSeparationClusterHelper;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ScatterClustersHelperClassSymbolTableTest extends ClassSymbolTableTest {

    @Override
    protected ClassSymbolTable getQuery() {
        return new ScatterClustersHelperClassSymbolTable();
    }

    @Override
    protected Class getExpectedClass() {
        return ScatterClustersHelper.class;
    }

    @Test
    public void compact() throws Exception {
        verifyReturnSymbol("compact", CompactSeparatedClustersHelper.class);
    }

    @Test
    public void contact() throws Exception {
        verifyReturnSymbol("contact", ContactClustersHelper.class);
    }

    @Test
    public void strict() throws Exception {
        verifyReturnSymbol("strict", StrictSeparationClusterHelper.class);
    }

}