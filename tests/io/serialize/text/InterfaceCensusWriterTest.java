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

package io.serialize.text;

import cells.MockCell;
import control.arguments.ConstantInteger;
import control.halt.HaltCondition;
import control.identifiers.Coordinate;
import geometry.Geometry;
import geometry.boundaries.Absorbing;
import geometry.boundaries.Boundary;
import geometry.lattice.Lattice;
import geometry.lattice.RectangularLattice;
import geometry.shape.Rectangle;
import geometry.shape.Shape;
import layers.MockLayerManager;
import layers.cell.CellLayer;
import layers.cell.CellUpdateManager;
import processes.StepState;
import structural.MockGeneralParameters;
import test.EslimeTestCase;

/**
 * Created by dbborens on 4/28/14.
 */
public class InterfaceCensusWriterTest extends EslimeTestCase {
    private CellLayer cellLayer;
    private MockLayerManager layerManager;

    @Override
    public void setUp() throws Exception {
        Lattice lattice = new RectangularLattice();
        Shape shape = new Rectangle(lattice, 3, 3);
        Boundary boundary = new Absorbing(shape, lattice);
        Geometry geom = new Geometry(lattice, shape, boundary);
        cellLayer = new CellLayer(geom);
        layerManager = new MockLayerManager();
        layerManager.setCellLayer(cellLayer);

        buildInitialCondition();

    }

    /**
     * Initial condition (1 is focal state)
     * 0 1 2
     * _____
     * <p>
     * 2 |  1 2 3
     * 1 |  2 1 0
     * 0 |  0 1 2
     * <p>
     * <p>
     * Upper left "1": 2 of 2 neighbors in state 2
     * <p>
     * Center "1": 1 of 4 neighbors vacant (state 0)
     * 1 of 4 neighbors in state 1
     * 2 of 4 neighbors in state 2
     * <p>
     * Lower "1": 1 of 3 neighbors vacant (state 0)
     * : 1 of 3 neighbors in state 1
     * : 1 of 3 neighbors in state 2
     * <p>
     * Therefore, expected totals:
     * 2 of 9 total neighbors vacant (state 0)
     * 2 of 9 total neighbors in state 1
     * 5 of 9 total neighbors in state 2
     * <p>
     * Note that "3" never appears as an interface state,
     * despite existing in the system, because it does not
     * border a 1.
     */
    private void buildInitialCondition() throws HaltCondition {
        put(new Coordinate(0, 0, 0), 0);
        put(new Coordinate(1, 0, 0), 1);
        put(new Coordinate(2, 0, 0), 2);
        put(new Coordinate(0, 1, 0), 2);
        put(new Coordinate(1, 1, 0), 1);
        put(new Coordinate(2, 1, 0), 0);
        put(new Coordinate(0, 2, 0), 1);
        put(new Coordinate(1, 2, 0), 2);
        put(new Coordinate(2, 2, 0), 3);
    }

    public void testLifeCycle() throws Exception {
        MockGeneralParameters p = makeMockGeneralParameters();
        p.setInstancePath(outputPath);
        InterfaceCensusWriter writer = new InterfaceCensusWriter(p, new ConstantInteger(1), layerManager);
        writer.init();

        // Flush original configuration
        StepState state = new StepState(0.0, 0);
        state.record(layerManager);
        writer.flush(state);

        /*
         * Replace "2" at (1, 2) with "3"
         *
         *      0 1 2
         *      _____
         *
         * 2 |  1 3 3
         * 1 |  2 1 0
         * 0 |  0 1 2
         *
         * Now expected values are:
         *   State 0 --> 2 of 9
         *   State 1 --> 2 of 9
         *   State 2 --> 3 of 9
         *   State 3 --> 2 of 9
         */
        replace(new Coordinate(1, 2, 0), 3);
        state = new StepState(1.0, 1);
        state.record(layerManager);
        writer.flush(state);

        writer.dispatchHalt(null);
        writer.close();
        assertFilesEqual("interface_1.txt");
    }

    private void replace(Coordinate c, int state) throws HaltCondition {
        CellUpdateManager u = cellLayer.getUpdateManager();
        u.banish(c);
        put(c, state);
    }

    private void put(Coordinate c, int state) throws HaltCondition {
        MockCell cell = new MockCell(state);
        CellUpdateManager u = cellLayer.getUpdateManager();
        u.place(cell, c);
    }
}
