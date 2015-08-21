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

package compiler.pipeline.instantiate.loader.io.serialize.text;

import compiler.pipeline.instantiate.loader.InterpolatorTest;
import control.arguments.DoubleArgument;
import org.junit.*;
import org.mockito.ArgumentCaptor;

import java.util.function.Supplier;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CorrelationWriterInterpolatorTest extends InterpolatorTest {

    private CorrelationWriterInterpolator query;
    @Before
    public void before() throws Exception {
        super.before();
        query = new CorrelationWriterInterpolator(load);
    }

    @Test
    public void filename() throws Exception {
        String expected = "test";
        when(load.aString(eq(node), eq("filename"), any()))
                .thenReturn(expected);
        String actual = query.filename(node);
        assertEquals(expected, actual);
    }

    @Test
    public void filenameDefault() throws Exception {
        String expected = CorrelationWriterInterpolator.DEFAULT_FILENAME;
        when(load.aString(eq(node), eq("filename"), any())).thenReturn(expected + "1");
        ArgumentCaptor<Supplier> captor = ArgumentCaptor.forClass(Supplier.class);
        query.filename(node);
        verify(load).aString(any(), any(), captor.capture());
        String actual = (String) captor.getValue().get();
        assertEquals(expected, actual);
    }

    @Test
    public void time() throws Exception {
        DoubleArgument expected = mock(DoubleArgument.class);
        when(load.aDoubleArgument(node, "time", random)).thenReturn(expected);
        DoubleArgument actual = query.time(node, random);
        assertSame(expected, actual);
    }
}