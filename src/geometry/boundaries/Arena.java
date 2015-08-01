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

package geometry.boundaries;

import control.identifiers.Coordinate;
import control.identifiers.Flags;
import geometry.lattice.Lattice;
import geometry.shape.Shape;
import structural.annotations.FactoryTarget;

/**
 * All boundaries are treated as infinite. However, going beyond
 * a specified limit results in the END_OF_WORLD flag being set.
 * This flag can then be handled within a specific process. In
 * practice, this amounts to a kind of absorbing boundary.
 *
 * @author David Bruce Borenstein
 * @test ArenaTest.java
 */
public class Arena extends Boundary {

    @FactoryTarget
    public Arena(Shape shape, Lattice lattice) {
        super(shape, lattice);
    }

    @Override
    public Coordinate apply(Coordinate c) {
        Coordinate ob = shape.getOverbounds(c);

        Coordinate ret;
        // Is any component overbound?
        if (ob.x() != 0 || ob.y() != 0 || ob.z() != 0) {
            // If yes, set the appropriate flags
            ret = c.addFlags(Flags.END_OF_WORLD | Flags.BOUNDARY_APPLIED);
        } else {
            ret = c;
        }

        return ret;
    }

    @Override
    public boolean isInfinite() {
        return true;
    }

    @Override
    protected void verify(Shape shape, Lattice lattice) {
        // Arena is compatible with all lattice geometries and
        // shapes, so no verification is needed.
    }

    @Override
    public Boundary clone(Shape scaledShape, Lattice clonedLattice) {
        return new Arena(scaledShape, clonedLattice);
    }
}
