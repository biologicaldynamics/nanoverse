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

package compiler.pipeline.instantiate.loader.layers.agent;

import compiler.pipeline.instantiate.loader.InterpolatorTest;
import compiler.pipeline.instantiate.loader.geometry.boundary.BoundaryLoader;
import compiler.pipeline.translate.nodes.*;
import control.arguments.GeometryDescriptor;
import geometry.boundaries.Boundary;
import org.junit.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class AgentLayerInterpolatorTest extends InterpolatorTest {

    private GeometryDescriptor geom;
    private AgentLayerDefaults defaults;
    private AgentLayerInterpolator query;
    @Before
    public void before() throws Exception {
        super.before();
        geom = mock(GeometryDescriptor.class);
        defaults = mock(AgentLayerDefaults.class);
        query = new AgentLayerInterpolator(load, defaults);
    }

    @Test
    public void boundary() throws Exception {
        MapObjectNode cNode = mock(MapObjectNode.class);
        when(node.getMember("boundary")).thenReturn(cNode);

        BoundaryLoader loader = mock(BoundaryLoader.class);
        when(load.getLoader(eq(node), eq("boundary"), anyBoolean())).thenReturn(loader);

        Boundary expected = mock(Boundary.class);
        when(loader.instantiate(cNode, geom)).thenReturn(expected);

        Boundary actual = query.boundary(node, geom);
        assertSame(expected, actual);
    }

    @Test
    public void boundaryDefault() throws Exception {
        Boundary expected = mock(Boundary.class);
        when(defaults.boundary(geom)).thenReturn(expected);
        Boundary actual = query.boundary(node, geom);

        assertSame(expected, actual);

    }
}