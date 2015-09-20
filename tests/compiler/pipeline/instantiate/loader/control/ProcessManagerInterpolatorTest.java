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

import compiler.pipeline.instantiate.loader.InterpolatorTest;
import compiler.pipeline.instantiate.loader.processes.ProcessLoader;
import compiler.pipeline.translate.nodes.*;
import compiler.pipeline.translate.symbol.*;
import org.junit.*;
import processes.NanoverseProcess;

import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ProcessManagerInterpolatorTest extends InterpolatorTest {

    private ProcessManagerDefaults defaults;
    private ProcessManagerInterpolator query;

    @Before
    public void before() throws Exception {
        super.before();
        defaults = mock(ProcessManagerDefaults.class);
        query = new ProcessManagerInterpolator(defaults);
    }

    @Test
    public void processes() throws Exception {
        ListObjectNode node = mock(ListObjectNode.class);
        MapObjectNode cNode = mock(MapObjectNode.class);
        when(node.getMemberStream()).thenReturn(Stream.of(cNode));

        MapSymbolTable ist = mock(MapSymbolTable.class);
        when(cNode.getSymbolTable()).thenReturn(ist);

        ProcessLoader loader = mock(ProcessLoader.class);
        when(ist.getLoader()).thenReturn(loader);

        NanoverseProcess process = mock(NanoverseProcess.class);
        when(loader.instantiate(cNode, lm, p)).thenReturn(process);

        Stream<NanoverseProcess> expected = Stream.of(process);
        Stream<NanoverseProcess> actual = query.processes(node, lm, p);

        assertStreamsEqual(expected, actual);
    }

    @Test
    public void processesDefault() throws Exception {
        Stream<NanoverseProcess> expected = mock(Stream.class);
        when(defaults.processes()).thenReturn(expected);
        Stream<NanoverseProcess> actual = query.processes(null, lm, p);
        assertSame(expected, actual);
    }
}