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

package layers;

import cells.MockCell;
import control.identifiers.Coordinate;
import control.identifiers.Coordinate2D;
import layers.cell.CellLayerIndices;
import structural.CanonicalCellMap;
import test.EslimeTestCase;

public class CellLayerIndicesTest extends EslimeTestCase {

    private Coordinate c;
    private CellLayerIndices query;

    @Override
    protected void setUp() throws Exception {
        c = new Coordinate2D(0, 0, 0);
        query = new CellLayerIndices();
    }

    public void testNulltoNull() {
        query.refresh(c, null, null);

        assertFalse(query.isDivisible(c));
        assertFalse(query.isOccupied(c));
    }

    public void testNonNullToNull() {
        MockCell cell = new MockCell();
        cell.setDivisible(true);
        query.refresh(c, null, cell);

        query.refresh(c, cell, null);
        assertFalse(query.isIndexed(cell));
        assertFalse(query.isDivisible(c));
        assertFalse(query.isOccupied(c));
    }


    public void testNullToNonDivisible() {
        MockCell cell = new MockCell();

        cell.setDivisible(false);
        cell.setState(3);

        query.refresh(c, null, cell);

        assertTrue(query.isIndexed(cell));
        assertEquals(c, query.locate(cell));
        assertFalse(query.isDivisible(c));
        assertTrue(query.isOccupied(c));
        assertEquals((Integer) 1, query.getStateMap().get(3));
    }

    public void testNullToDivisible() {
        MockCell cell = new MockCell();
        cell.setDivisible(true);
        query.refresh(c, null, cell);
        assertTrue(query.isDivisible(c));
    }

    public void testNonNullTransition() {
        MockCell dCell = new MockCell();
        dCell.setState(5);
        dCell.setDivisible(true);

        MockCell nCell = new MockCell();
        nCell.setDivisible(false);
        nCell.setState(2);

        query.refresh(c, null, nCell);
        assertTrue(query.isIndexed(nCell));
        assertFalse(query.isIndexed(dCell));
        assertFalse(query.isDivisible(c));
        assertEquals((Integer) 0, query.getStateMap().get(5));
        assertEquals((Integer) 1, query.getStateMap().get(2));

        query.refresh(c, nCell, dCell);
        assertFalse(query.isIndexed(nCell));
        assertTrue(query.isIndexed(dCell));
        assertTrue(query.isDivisible(c));
        assertEquals((Integer) 1, query.getStateMap().get(5));
        assertEquals((Integer) 0, query.getStateMap().get(2));
    }

    public void testClone() {
        CanonicalCellMap map = new CanonicalCellMap(1);
        map.put(c, null);
        Object clone = query.clone(map);
        assertEquals(query, clone);
        assertFalse(query == clone);
    }
}
