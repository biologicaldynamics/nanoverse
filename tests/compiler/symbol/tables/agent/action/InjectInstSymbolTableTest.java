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

package compiler.symbol.tables.agent.action;

import agent.action.Inject;
import compiler.symbol.tables.MapSymbolTable;
import control.arguments.*;
import org.junit.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class InjectInstSymbolTableTest extends ActionInstSymbolTableTest {

    @Override
    protected Class getExpectedActionClass() {
        return Inject.class;
    }

    @Override
    protected MapSymbolTable getQuery() {
        return new InjectInstSymbolTable();
    }

    @Test
    public void delta() throws Exception {
        verifyReturnSymbol("delta", DoubleArgument.class);
    }

    @Test
    public void layer() throws Exception {
        verifyReturnSymbol("layer", StringArgument.class);

    }
}