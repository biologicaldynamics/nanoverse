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

package processes.discrete;

import cells.BehaviorCell;
import control.arguments.*;
import control.halt.DominationEvent;
import control.identifiers.*;
import geometry.Geometry;
import geometry.boundaries.*;
import geometry.lattice.*;
import geometry.shape.*;
import layers.MockLayerManager;
import layers.cell.CellLayer;
import org.junit.*;
import processes.*;
import processes.discrete.check.CheckForDomination;
import structural.MockGeneralParameters;
import test.EslimeTestCase;

import static org.junit.Assert.assertEquals;
public class CheckForDominationTest extends EslimeTestCase {
    private MockLayerManager layerManager;
    private CellLayer layer;
    private CheckForDomination query;

    @Before
    public void setUp() throws Exception {
        Lattice lattice = new RectangularLattice();
        layerManager = new MockLayerManager();
        Shape shape = new Rectangle(lattice, 11, 1);
        Boundary boundary = new Periodic(shape, lattice);
        Geometry geom = new Geometry(lattice, shape, boundary);
        layer = new CellLayer(geom);
        layerManager.setCellLayer(layer);
        MockGeneralParameters p = makeMockGeneralParameters();
        DoubleArgument thresholdArg = new ConstantDouble(0.2);
        IntegerArgument stateArg = new ConstantInteger(1);

        // Create a 1D lattice of length 10.
        // Create an occupancy test that checks for 30% occupancy.
        BaseProcessArguments arguments = makeBaseProcessArguments(layerManager, p);
        CellProcessArguments cpArguments = makeCellProcessArguments(geom);
        query = new CheckForDomination(arguments, cpArguments, stateArg, thresholdArg);
        query.init();
    }

    @Test
    public void testAboveThreshold() throws Exception {
        for (int i = 1; i < 4; i++) {
            populate(i, 1);
        }

        for (int i = 4; i < 11; i++) {
            populate(i, i);
        }

        doTest(true);
    }

    private void doTest(boolean expectThrow) throws Exception {
        boolean thrown = false;

        try {
            query.fire(new StepState(0.0, 0));
        } catch (DominationEvent ex) {
            thrown = true;
        }

        assertEquals(expectThrow, thrown);
    }

    private void populate(int x, int state) throws Exception {
        BehaviorCell cell = new BehaviorCell(layerManager, state, state, state, null);
        Coordinate coord = new Coordinate2D(x, 0, 0);
        layer.getUpdateManager().place(cell, coord);
    }

    @Test
    public void testAtThreshold() throws Exception {
        for (int i = 1; i < 3; i++) {
            populate(i, 1);
        }

        for (int i = 3; i < 11; i++) {
            populate(i, i);
        }

        doTest(true);
    }

    @Test
    public void testBelowThreshold() throws Exception {
        for (int i = 1; i < 2; i++) {
            populate(i, 1);
        }

        for (int i = 2; i < 11; i++) {
            populate(i, i);
        }

        doTest(false);
    }
}