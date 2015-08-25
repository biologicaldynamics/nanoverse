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

package compiler.pipeline.instantiate.loader.agent.action.stochastic;

import agent.action.ActionDescriptor;
import agent.targets.TargetDescriptor;
import compiler.pipeline.instantiate.loader.InterpolatorTest;
import compiler.pipeline.instantiate.loader.agent.action.ActionLoader;
import compiler.pipeline.instantiate.loader.agent.targets.TargetLoader;
import compiler.pipeline.translate.nodes.MapObjectNode;
import control.arguments.ProbabilitySupplierDescriptor;
import org.junit.*;

import java.util.function.Supplier;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class WeightedOptionInterpolatorTest extends InterpolatorTest {

    private WeightedOptionInterpolator query;

    @Before
    public void before() throws Exception {
        super.before();
        query = new WeightedOptionInterpolator(load);
    }

    @Test
    public void action() throws Exception {
        MapObjectNode cNode = mock(MapObjectNode.class);
        when(node.getMember("action")).thenReturn(cNode);

        ActionLoader loader = mock(ActionLoader.class);
        when(load.getLoader(eq(node), eq("action"), anyBoolean())).thenReturn(loader);

        ActionDescriptor expected = mock(ActionDescriptor.class);
        when(loader.instantiate(cNode, lm, p)).thenReturn(expected);

        ActionDescriptor actual = query.action(node, lm, p);
        assertSame(expected, actual);
    }

    @Test
    public void weight() throws Exception {
        MapObjectNode cNode = mock(MapObjectNode.class);
        when(node.getMember("weight")).thenReturn(cNode);

        ProbabilitySupplierLoader loader = mock(ProbabilitySupplierLoader.class);
        when(load.getLoader(eq(node), eq("weight"), anyBoolean())).thenReturn(loader);

        ProbabilitySupplierDescriptor expected = mock(ProbabilitySupplierDescriptor.class);
        when(loader.instantiate(cNode, lm, p)).thenReturn(expected);

        ProbabilitySupplierDescriptor actual = query.weight(node, lm, p);
        assertSame(expected, actual);
    }
}