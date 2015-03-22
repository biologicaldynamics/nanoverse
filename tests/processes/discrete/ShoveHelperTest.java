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

import cells.Cell;
import cells.MockCell;
import control.identifiers.Coordinate;
import geometry.Geometry;
import geometry.boundaries.Absorbing;
import geometry.boundaries.Arena;
import geometry.boundaries.Boundary;
import geometry.boundaries.Periodic;
import geometry.lattice.CubicLattice;
import geometry.lattice.Lattice;
import geometry.lattice.RectangularLattice;
import geometry.shape.Cuboid;
import geometry.shape.Rectangle;
import geometry.shape.Shape;
import layers.MockLayerManager;
import layers.cell.CellLayer;
import structural.MockRandom;
import test.EslimeTestCase;

import java.util.HashSet;
import java.util.Random;
import java.util.Arrays;

public class ShoveHelperTest extends EslimeTestCase {

    private CellLayer layer;
    private ShoveHelper query;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        // Create a 10x1 rectangular, 2D geometry
        Lattice lattice = new RectangularLattice();
        Shape shape = new Rectangle(lattice, 10, 1);
        Boundary boundary = new Periodic(shape, lattice);
        Geometry geom = new Geometry(lattice, shape, boundary);
        MockLayerManager lm = new MockLayerManager();
        layer = new CellLayer(geom);
        lm.setCellLayer(layer);
        placeCells();

        Random random = new Random(RANDOM_SEED);

        query = new ShoveHelper(lm, random);
    }

    /**
     * This test will create a linear geometry that has one vacancy. A line
     * of cells will be pushed toward this vacancy, moving the vacancy to the
     * origin of the shove. This does not test all of the behavior of this
     * process, but it's a start.
     * <p>
     * So here is what is supposed to happen:
     * <p>
     * 0123456_89  Initial condition
     * ^       (Cell to be shoved)
     * <p>
     * 0123_45689  Result
     */
    public void test1Dshove() throws Exception {
        Coordinate target = new Coordinate(7, 0, 0);
        Coordinate origin = new Coordinate(4, 0, 0);
        query.shove(origin, target);

        int[] leftSeq = new int[]{0, 1, 2, 3};
        int[] rightSeq = new int[]{4, 5, 6, 8, 9};

        for (int x = 0; x < 4; x++) {
            Coordinate c = new Coordinate(x, 0, 0);
            Cell observed = layer.getViewer().getCell(c);
            int expected = leftSeq[x];
            int actual = observed.getState();
            assertEquals(expected, actual);
        }

        for (int x = 0; x < 5; x++) {
            Coordinate c = new Coordinate(x + 5, 0, 0);
            Cell observed = layer.getViewer().getCell(c);
            int expected = rightSeq[x];
            int actual = observed.getState();
            assertEquals(expected, actual);
        }
    }

    public void test3Dshove() throws Exception {
        // Create a 10x1 rectangular, 2D geometry
        Lattice lattice = new CubicLattice();
        Shape shape = new Cuboid(lattice, 5, 5, 5);
        Boundary boundary = new Absorbing(shape, lattice);
        Geometry geom = new Geometry(lattice, shape, boundary);
        MockLayerManager lm = new MockLayerManager();
        layer = new CellLayer(geom);
        lm.setCellLayer(layer);
        Random random = new MockRandom();
        query = new ShoveHelper(lm, random);

        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                for (int z = 0; z < 4; z++) {
                    Cell cell = new MockCell(1);
                    Coordinate coord = new Coordinate(x, y, z, 0);
                    layer.getUpdateManager().place(cell, coord);
                }
            }
        }

        Coordinate origin = new Coordinate(1, 2, 3, 0);
        Coordinate target = new Coordinate(4, 4, 4, 0);

        HashSet<Coordinate> affected = query.shove(origin, target);

        // Algorithm will prefer z, then y, then x. So the shoving should
        // progress like this:
        assertTrue(affected.contains(new Coordinate(1, 2, 4, 0)));
        assertTrue(affected.contains(new Coordinate(1, 3, 4, 0)));
        assertTrue(affected.contains(new Coordinate(1, 4, 4, 0)));
        assertTrue(affected.contains(new Coordinate(2, 4, 4, 0)));
        assertTrue(affected.contains(new Coordinate(3, 4, 4, 0)));
        assertTrue(affected.contains(new Coordinate(4, 4, 4, 0)));

        // Having shoved, the origin should now be vacant.
        assertFalse(layer.getViewer().isOccupied(origin));
    }

    public void testGetTarget() throws Exception {
        Coordinate origin = new Coordinate(4, 0, 0);

        Coordinate expected = new Coordinate(7, 0, 0);
        Coordinate actual = query.chooseVacancy(origin);
        assertEquals(expected, actual);
    }

    public void testRemoveImaginary() throws Exception {
        Lattice lattice = new RectangularLattice();
        Shape shape = new Rectangle(lattice, 10, 1);
        Boundary boundary = new Arena(shape, lattice);
        Geometry geom = new Geometry(lattice, shape, boundary);
        MockLayerManager lm = new MockLayerManager();
        layer = new CellLayer(geom);
        lm.setCellLayer(layer);
        placeCells();
        Random random = new Random(RANDOM_SEED);
        query = new ShoveHelper(lm, random);
        MockCell cell = new MockCell(1);
        layer.getUpdateManager().place(cell, new Coordinate(-1, 0, 0));
        assertEquals(1, layer.getViewer().getImaginarySites().size());
        query.removeImaginary();
        assertEquals(0, layer.getViewer().getImaginarySites().size());
    }

    /**
     * ensure that the displacement vector between each cell in the shoving
     * path is the same for random shoving.
     * @throws Exception
     */
    public void test1DShoveRandom() throws Exception {
        Lattice lattice = new RectangularLattice();
        Shape shape = new Rectangle(lattice, 10, 1);
        Boundary boundary = new Periodic(shape, lattice);
        Geometry geom = new Geometry(lattice, shape, boundary);
        MockLayerManager lm = new MockLayerManager();
        layer = new CellLayer(geom);
        lm.setCellLayer(layer);
        Random random = new MockRandom();
        query = new ShoveHelper(lm, random);

        // initial state: _1234567__
        for (int x = 1; x < 8; x++) {
            Cell cell = new MockCell(1);
            Coordinate coord = new Coordinate(x, 0, 0);
            layer.getUpdateManager().place(cell, coord);
        }

        Coordinate origin = new Coordinate(4, 0, 0);
        HashSet<Coordinate> affectedSites = query.shoveRandom(origin);

        // make sure displacement vector between each site is the same
        Coordinate[] affectedArray = affectedSites.toArray(new Coordinate[0]);
        Arrays.sort(affectedArray);
        Coordinate[] displacements = new Coordinate[affectedArray.length -1];
        for (int i=0; i < affectedArray.length - 1; i++) {
            displacements[i] = lm.getCellLayer().getGeometry().
                    getDisplacement(affectedArray[i],
                            affectedArray[i+1], Geometry.APPLY_BOUNDARIES);
        }
        Coordinate displacement = displacements[0];
        for (int j=0; j < displacements.length; j++) {
            assertEquals(displacement, displacements[j]);
        }

        // Having shoved, the origin should now be vacant.
        assertFalse(layer.getViewer().isOccupied(origin));
    }

    private void placeCells() throws Exception {
        for (int x = 0; x < 7; x++) {
            placeNumberedCell(x);
        }

        for (int x = 8; x <= 9; x++) {
            placeNumberedCell(x);
        }
    }

    private void placeNumberedCell(int x) throws Exception {
        MockCell cell = new MockCell(x);
        Coordinate coord = new Coordinate(x, 0, 0);
        layer.getUpdateManager().place(cell, coord);
    }

}