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

package io.deserialize;

import control.GeneralParameters;
import control.identifiers.*;
import geometry.Geometry;
import io.deserialize.continuum.*;
import io.serialize.binary.ContinuumStateWriter;
import layers.LayerManager;
import layers.cell.CellLayer;
import layers.continuum.ContinuumLayer;
import org.junit.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import processes.StepState;
import test.TestBase;

import java.util.stream.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by dbborens on 5/28/2015.
 */
public class ContinuumStateLifeCycleTest extends TestBase {

    private static final int RANGE = 10;

    @Test
    public void testContinuumIOLifeCycle() throws Exception {
        doOutputStage();
        doInputAndVerificationStage();
    }

    private void doInputAndVerificationStage() {
        ContinuumStateReader reader = new ContinuumStateReader("./output/", RANGE);
        assertTrue(reader.hasNext());


        // Frame 1
        ContinuumLayerViewer clv = reader.next();
        assertEquals(1.0, clv.getTime(), epsilon);
        assertEquals(1, clv.getFrameNumber());
        verifyLayer(clv.getValues("test1"), 1.0);
        verifyLayer(clv.getValues("test2"), -1.0);

        // Frame 2
        assertTrue(reader.hasNext());
        clv = reader.next();
        assertEquals(4.0, clv.getTime(), epsilon);
        assertEquals(2, clv.getFrameNumber());
        verifyLayer(clv.getValues("test1"), 2.0);
        verifyLayer(clv.getValues("test2"), -2.0);

        assertFalse(reader.hasNext());

        verifyExtrema(reader.getExtrema("test1"), 1.0);
        verifyExtrema(reader.getExtrema("test2"), -1.0);
    }

    private void verifyExtrema(Extrema actual, double coefficient) {
        Extrema expected = new Extrema();
        expected.consider(0.0, new Coordinate(0, 0, 0), 1.0);
        expected.consider(coefficient * 18.0, new Coordinate(9, 0, 0), 2.0);
        assertEquals(expected, actual);
    }

    private void verifyLayer(Stream<Double> actual, double coefficient) {
        Stream<Double> expected = makeValue(coefficient);
        assertStreamsEqual(expected, actual);
    }

    private void doOutputStage() throws Exception {
        GeneralParameters p = mock(GeneralParameters.class);
        when (p.getInstancePath()).thenReturn("./output/");
        LayerManager lm = makeLayerManager();
        ContinuumStateWriter writer = new ContinuumStateWriter(p, lm);
        writer.init();

        // Push first frame
        StepState stepState = makeStepState(1.0, 1);
        writer.flush(stepState);

        // Push second frame
        stepState = makeStepState(2.0, 2);
        writer.flush(stepState);

        // Close writer
        writer.dispatchHalt(null);
    }

    private StepState makeStepState(double coefficient, int frame) {
        StepState stepState = mock(StepState.class);
        when(stepState.getTime()).thenReturn(frame * coefficient);
        when(stepState.getFrame()).thenReturn(frame);
        when(stepState.getRecordedContinuumValues("test1"))
                .thenAnswer(invocation -> makeValue(coefficient));

        when(stepState.getRecordedContinuumValues("test2"))
                .thenAnswer(invocation -> makeValue(coefficient * -1.0));

        return stepState;
    }


    private Stream<Double> makeValue(double coefficient) {
        return IntStream
                .range(0, RANGE)
                .mapToDouble(i -> i * coefficient)
                .boxed();
    }

    public LayerManager makeLayerManager() {
        LayerManager lm = mock(LayerManager.class);
        Geometry geom = mock(Geometry.class);
        Coordinate[] cc = makeCoordinateArray();
        CellLayer cellLayer = mock(CellLayer.class);
        when(geom.getCanonicalSites()).thenReturn(cc);
        when(cellLayer.getGeometry()).thenReturn(geom);
        when(lm.getCellLayer()).thenReturn(cellLayer);
        when(lm.getDeindexer()).thenReturn(i -> cc[i]);
        when(lm.getContinuumLayerIds())
                .thenAnswer(invocation -> Stream.of("test1", "test2"));
        return lm;
    }

    private Coordinate[] makeCoordinateArray() {
        return IntStream.range(0, RANGE)
                .mapToObj(i -> new Coordinate(i, 0, 0))
                .collect(Collectors.toList())
                .toArray(new Coordinate[RANGE]);
    }


}
