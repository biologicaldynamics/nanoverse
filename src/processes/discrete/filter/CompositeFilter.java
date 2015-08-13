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

package processes.discrete.filter;

import control.identifiers.Coordinate;
import structural.annotations.FactoryTarget;

import java.util.Arrays;
import java.util.List;

/**
 * Created by dbborens on 5/5/14.
 */
public class CompositeFilter extends Filter {
    private Filter[] children;

    @FactoryTarget
    public CompositeFilter(Filter[] children) {
        this.children = children;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CompositeFilter that = (CompositeFilter) o;

        if (!Arrays.equals(children, that.children)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return children != null ? Arrays.hashCode(children) : 0;
    }

    @Override
    public List<Coordinate> apply(List<Coordinate> toFilter) {
        for (Filter child : children) {
            toFilter = child.apply(toFilter);
        }

        return toFilter;
    }
}
