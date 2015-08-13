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

package compiler.pipeline.instantiate.loader.control;

import compiler.pipeline.instantiate.factory.control.run.ProjectFactory;
import compiler.pipeline.translate.nodes.MapObjectNode;
import control.GeneralParameters;
import control.Integrator;
import control.ProcessManager;
import control.arguments.GeometryDescriptor;
import control.run.Runner;
import io.serialize.SerializationManager;
import layers.LayerManager;
import org.junit.*;
import org.mockito.InOrder;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ProjectLoaderTest {

    private ProjectFactory factory;
    private ProjectInterpolator interpolator;
    private IntegratorLoader integratorLoader;
    private MapObjectNode node;
    private ProjectLoader query;

    @Before
    public void before() throws Exception {
        factory = mock(ProjectFactory.class);
        interpolator = mock(ProjectInterpolator.class);
        node = mock(MapObjectNode.class);
        integratorLoader = mock(IntegratorLoader.class);
        query = new ProjectLoader(factory, interpolator, integratorLoader);
    }

    @Test
    public void versionIsChecked() {
        query.instantiate(node);
        verify(interpolator).version(node);
    }

    @Test
    public void testInstantiate() throws Exception {
        GeneralParameters p = mock(GeneralParameters.class);
        when(interpolator.generalParameters(node)).thenReturn(p);

        GeometryDescriptor gd = mock(GeometryDescriptor.class);
        when(interpolator.geometry(node)).thenReturn(gd);

        LayerManager lm = mock(LayerManager.class);
        when(interpolator.layers(node, gd)).thenReturn(lm);

        ProcessManager pm = mock(ProcessManager.class);
        when(interpolator.processes(node, p, lm)).thenReturn(pm);

        SerializationManager sm = mock(SerializationManager.class);
        when(interpolator.output(node, p, lm)).thenReturn(sm);

        Integrator integrator = mock(Integrator.class);
        when(integratorLoader.instantiate(p, pm, sm)).thenReturn(integrator);

        Runner expected = mock(Runner.class);
        when(factory.build()).thenReturn(expected);

        Runner actual = query.instantiate(node);

        assertSame(expected, actual);

        verify(factory).setIntegrator(integrator);
        verify(factory).setP(p);
    }
}