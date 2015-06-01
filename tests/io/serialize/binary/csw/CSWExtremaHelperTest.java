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

package io.serialize.binary.csw;

import control.identifiers.*;
import io.serialize.binary.BinaryExtremaWriter;
import org.junit.*;
import org.mockito.InOrder;
import processes.StepState;

import java.io.*;
import java.util.HashMap;
import java.util.function.Function;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

public class CSWExtremaHelperTest {

    private HashMap<String, Extrema> extremaMap;
    private Function<String, DataOutputStream> fileFunction;
    private DataOutputStream dos;
    private BinaryExtremaWriter writer;
    private CSWConsiderHelper considerHelper;
    private StepState state;
    private Extrema extrema;
    private Stream<Double> values;
    private CSWExtremaHelper query;

    @Before
    public void before() throws Exception {
        writer = mock(BinaryExtremaWriter.class);
        extremaMap = new HashMap<>();
        considerHelper = mock(CSWConsiderHelper.class);
        dos = mock(DataOutputStream.class);
        fileFunction = anything -> dos;
        extrema = mock(Extrema.class);
        extremaMap.put("test", extrema);
        state = mock(StepState.class);
        when(state.getFrame()).thenReturn(7);
        values = mock(Stream.class);
        when(state.getRecordedContinuumValues("test")).thenReturn(values);
        query = new CSWExtremaHelper(considerHelper, extremaMap, fileFunction, writer);
    }

    @Test
    public void push() throws Exception {
        query.push(state);
        verify(considerHelper).consider("test", 7, values);
    }

    @Test
    public void serialize() throws Exception {
        query.serialize();
        verify(writer).write(dos, extrema);
    }
}