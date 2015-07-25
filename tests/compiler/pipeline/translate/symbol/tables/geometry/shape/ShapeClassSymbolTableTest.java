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

package compiler.pipeline.translate.symbol.tables.geometry.shape;

import compiler.pipeline.translate.symbol.tables.*;
import geometry.shape.*;
import org.junit.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ShapeClassSymbolTableTest extends ClassSymbolTableTest {

    @Override
    protected ClassSymbolTable getQuery() {
        return new ShapeClassSymbolTable();
    }

    @Override
    protected Class getExpectedClass() {
        return Shape.class;
    }

    @Test
    public void line() throws Exception {
        verifyReturnSymbol("Line", Line.class);
    }

    @Test
    public void rectangle() throws Exception {
        verifyReturnSymbol("Rectangle", Rectangle.class);
    }

    @Test
    public void hexagon() throws Exception {
        verifyReturnSymbol("Hexagon", Hexagon.class);
    }

    @Test
    public void cuboid() throws Exception {
        verifyReturnSymbol("Cuboid", Cuboid.class);
    }
}