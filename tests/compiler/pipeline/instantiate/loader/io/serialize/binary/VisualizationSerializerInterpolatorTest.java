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

package compiler.pipeline.instantiate.loader.io.serialize.binary;

import compiler.pipeline.instantiate.loader.InterpolatorTest;
import compiler.pipeline.instantiate.loader.io.visual.VisualizationLoader;
import compiler.pipeline.translate.nodes.MapObjectNode;
import io.visual.Visualization;
import layers.LayerManager;
import org.junit.*;

import java.util.function.Supplier;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class VisualizationSerializerInterpolatorTest extends InterpolatorTest {

    private VisualizationSerializerDefaults defaults;
    private LayerManager layerManager;
    private VisualizationSerializerInterpolator query;

    @Before
    @Override
    public void before() throws Exception {
        super.before();
        defaults = mock(VisualizationSerializerDefaults.class);
        layerManager = mock(LayerManager.class);
        query = new VisualizationSerializerInterpolator(load, defaults);
    }

    @Test
    public void prefix() throws Exception {
        Supplier<String> trigger = () -> query.prefix(node);
        verifyString("prefix", trigger);
    }

    @Test
    public void prefixDefault() throws Exception {
        String expected = "expected";
        when(defaults.prefix()).thenReturn(expected);
        Runnable trigger = () -> query.prefix(node);
        verifyStringDefault("prefix", expected, trigger);
    }

    @Test
    public void visualization() throws Exception {
        VisualizationLoader loader = mock(VisualizationLoader.class);
        when(load.getLoader(eq(node), eq("visualization"), anyBoolean()))
            .thenReturn(loader);

        MapObjectNode cNode = mock(MapObjectNode.class);
        when(node.getMember("visualization")).thenReturn(cNode);

        Visualization expected = mock(Visualization.class);
        when(loader.instantiate(cNode, p)).thenReturn(expected);

        Visualization actual = query.visualization(node, layerManager, p);

        assertSame(expected, actual);
    }

    @Test
    public void visualizationDefault() throws Exception {
        when(load.getLoader(isNull(MapObjectNode.class), eq("visualization"), anyBoolean()))
            .thenReturn(null);

        Visualization expected = mock(Visualization.class);
        when(defaults.visualization(layerManager, p)).thenReturn(expected);

        Visualization actual = query.visualization(node, layerManager, p);
        assertSame(expected, actual);
    }
}