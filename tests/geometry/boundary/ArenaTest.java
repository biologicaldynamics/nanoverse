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

package geometry.boundary;

import control.identifiers.Coordinate;
import control.identifiers.Coordinate2D;
import control.identifiers.Flags;
import geometry.boundaries.Arena;
import geometry.boundaries.Boundary;
import geometry.lattice.Lattice;
import geometry.lattice.RectangularLattice;
import geometry.lattice.TriangularLattice;
import geometry.shape.Rectangle;
import geometry.shape.Shape;
import test.EslimeTestCase;

public class ArenaTest extends EslimeTestCase {

    private Boundary rect;
    private Boundary tri;

    public void setUp() {
        Lattice rectLattice = new RectangularLattice();
        Lattice triLattice = new TriangularLattice();

        Shape rectShape = new Rectangle(rectLattice, 3, 5);
        Shape triShape = new Rectangle(triLattice, 3, 5);

        rect = makeBoundary(rectShape, rectLattice);
        tri = makeBoundary(triShape, triLattice);
    }

    public void testInfinite() {
        assertTrue(rect.isInfinite());
        assertTrue(tri.isInfinite());
    }

    public void testApplyInBounds() {
        // These are in bounds for both triangular and rectangular
        Coordinate a, b, c;
        a = new Coordinate2D(0, 0, 0);
        b = new Coordinate2D(1, 1, 0);
        c = new Coordinate2D(2, 2, 0);

        // Rectangular
        Coordinate actual, expected;
        expected = new Coordinate2D(0, 0, 0);
        actual = rect.apply(a);
        assertEquals(expected, actual);

        expected = new Coordinate2D(1, 1, 0);
        actual = rect.apply(b);
        assertEquals(expected, actual);

        expected = new Coordinate2D(2, 2, 0);
        actual = rect.apply(c);
        assertEquals(expected, actual);

        // Triangular
        expected = new Coordinate2D(0, 0, 0);
        actual = tri.apply(a);
        assertEquals(expected, actual);

        expected = new Coordinate2D(1, 1, 0);
        actual = tri.apply(b);
        assertEquals(expected, actual);

        expected = new Coordinate2D(2, 2, 0);
        actual = tri.apply(c);
        assertEquals(expected, actual);
    }

    public void testApplyOutsideX() {
        Coordinate p, q;
        p = new Coordinate2D(-1, 1, 0);
        q = new Coordinate2D(5, 2, 0);

        Coordinate actual, expected;

        // p
        actual = rect.apply(p);
        assertFalse(actual.hasFlag(Flags.UNDEFINED));
        expected = p.addFlags(Flags.END_OF_WORLD | Flags.BOUNDARY_APPLIED);
        assertEquals(expected, actual);

        actual = tri.apply(p);
        assertFalse(actual.hasFlag(Flags.UNDEFINED));
        assertEquals(expected, actual);

        // q
        actual = rect.apply(q);
        assertFalse(actual.hasFlag(Flags.UNDEFINED));
        expected = q.addFlags(Flags.END_OF_WORLD | Flags.BOUNDARY_APPLIED);
        assertEquals(expected, actual);

        actual = tri.apply(q);
        assertFalse(actual.hasFlag(Flags.UNDEFINED));
        assertEquals(expected, actual);
    }

    public void testApplyOutsideY() {
        Coordinate p, q;
        p = new Coordinate2D(0, 5, 0);
        q = new Coordinate2D(2, -1, 0);

        Coordinate actual, expected;

        // p
        actual = rect.apply(p);
        assertFalse(actual.hasFlag(Flags.UNDEFINED));
        expected = p.addFlags(Flags.END_OF_WORLD | Flags.BOUNDARY_APPLIED);
        assertEquals(expected, actual);

        actual = tri.apply(p);
        assertFalse(actual.hasFlag(Flags.UNDEFINED));
        assertEquals(expected, actual);

        // q
        actual = rect.apply(q);
        assertFalse(actual.hasFlag(Flags.UNDEFINED));
        expected = q.addFlags(Flags.END_OF_WORLD | Flags.BOUNDARY_APPLIED);
        assertEquals(expected, actual);

        actual = tri.apply(q);
        assertFalse(actual.hasFlag(Flags.UNDEFINED));
        assertEquals(expected, actual);
    }

    public void testApplyOutsideXY() {
        Coordinate p, q;
        p = new Coordinate2D(-1, 4, 0);
        q = new Coordinate2D(5, -5, 0);

        Coordinate actual, expected;

        // p
        actual = rect.apply(p);
        assertFalse(actual.hasFlag(Flags.UNDEFINED));
        expected = p.addFlags(Flags.END_OF_WORLD | Flags.BOUNDARY_APPLIED);
        assertEquals(expected, actual);

        actual = tri.apply(p);
        assertFalse(actual.hasFlag(Flags.UNDEFINED));
        assertEquals(expected, actual);

        // q
        actual = rect.apply(q);
        assertFalse(actual.hasFlag(Flags.UNDEFINED));
        expected = q.addFlags(Flags.END_OF_WORLD | Flags.BOUNDARY_APPLIED);
        assertEquals(expected, actual);

        actual = tri.apply(q);
        assertFalse(actual.hasFlag(Flags.UNDEFINED));
        assertEquals(expected, actual);
    }

    public void testCloneWithArguments() {
        Lattice lattice = new RectangularLattice();
        Shape singleton = new Rectangle(lattice, 1, 1);

        Boundary query = rect.clone(singleton, lattice);

        // Boundaries are equal based on their class, not their dependencies
        assertEquals(rect, query);
        assertFalse(rect == query);
    }

    protected Boundary makeBoundary(Shape shape, Lattice lattice) {
        return new Arena(shape, lattice);
    }
}
