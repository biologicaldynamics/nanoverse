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

package compiler.pipeline.instantiate.loader.io.visual;

import compiler.pipeline.instantiate.helpers.LoadHelper;
import compiler.pipeline.instantiate.loader.InterpolatorTest;
import control.GeneralParameters;
import io.visual.VisualizationProperties;
import org.junit.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class KymographInterpolatorTest extends InterpolatorTest {

    private KymographDefaults defaults;
    private VisualizationPropertiesLoader vpLoader;
    private GeneralParameters p;
    private KymographInterpolator query;

    @Before
    public void before() throws Exception {
        super.before();
        defaults = mock(KymographDefaults.class);
        vpLoader = mock(VisualizationPropertiesLoader.class);
        p = mock(GeneralParameters.class);
        query = new KymographInterpolator(defaults, vpLoader);
    }

    @Test
    public void properties() throws Exception {
        VisualizationProperties expected = mock(VisualizationProperties.class);
        when(vpLoader.instantiate(node, p)).thenReturn(expected);
        VisualizationProperties actual = query.properties(node, p);
        assertSame(expected, actual);
    }

    @Test
    public void propertiesDefault() throws Exception {
        VisualizationProperties expected = mock(VisualizationProperties.class);
        when(defaults.properties(p)).thenReturn(expected);
        VisualizationProperties actual = query.properties(null, p);
        assertSame(expected, actual);
    }
}