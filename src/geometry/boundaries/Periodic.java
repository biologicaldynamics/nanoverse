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
import geometry.boundaries.helpers.WrapHelper;
import geometry.boundaries.helpers.WrapHelper1D;
import geometry.boundaries.helpers.WrapHelper2D;
import geometry.boundaries.helpers.WrapHelper3D;
import geometry.lattice.Lattice;
import geometry.shape.Cuboid;
import geometry.shape.Line;
import geometry.shape.Rectangle;
import geometry.shape.Shape;
import structural.annotations.FactoryTarget;

/**
 * Created by dbborens on 5/7/14.
 */
public class Periodic extends Boundary {
    protected WrapHelper helper;

    @FactoryTarget
    public Periodic(Shape shape, Lattice lattice) {
        super(shape, lattice);
        if (lattice.getDimensionality() == 1) {
            helper = new WrapHelper1D(shape, lattice);
        } else if (lattice.getDimensionality() == 2) {
            helper = new WrapHelper2D(shape, lattice);
        } else {
            helper = new WrapHelper3D(shape, lattice);
        }
    }

    @Override
    protected void verify(Shape shape, Lattice lattice) {
        if (!((shape instanceof Cuboid) || (shape instanceof Rectangle) ||
                (shape instanceof Line))) {
            throw new IllegalArgumentException("Full periodic boundary " +
                    "condition requires a line, rectangle or cuboid arena" +
                    " shape.");
        }
    }

    @Override
    public Coordinate apply(Coordinate c) {
        return helper.wrapAll(c);
    }

    @Override
    public boolean isInfinite() {
        return false;
    }

    @Override
    public Boundary clone(Shape scaledShape, Lattice clonedLattice) {
        return new Periodic(scaledShape, clonedLattice);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Periodic periodic = (Periodic) o;

        if (helper != null ? !helper.equals(periodic.helper) : periodic.helper != null)
            return false;

        return true;
    }
}
