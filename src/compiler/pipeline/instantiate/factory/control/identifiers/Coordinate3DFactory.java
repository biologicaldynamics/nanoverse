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
package compiler.pipeline.instantiate.factory.control.identifiers;

import control.identifiers.Coordinate3D;
import compiler.pipeline.instantiate.factory.Factory;

public class Coordinate3DFactory implements Factory<Coordinate3D> {

    private final Coordinate3DFactoryHelper helper;

    private int x;
    private int y;
    private int z;
    private int flags;

    public Coordinate3DFactory() {
        helper = new Coordinate3DFactoryHelper();
    }

    public Coordinate3DFactory(Coordinate3DFactoryHelper helper) {
        this.helper = helper;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    @Override
    public Coordinate3D build() {
        return helper.build(x, y, z, flags);
    }
}