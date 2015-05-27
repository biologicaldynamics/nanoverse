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

package layers.continuum;

import no.uib.cipr.matrix.DenseVector;
import org.junit.Before;
import org.junit.Test;
import test.LinearMocks;

import java.util.stream.Stream;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;

public class ContinuumLayerContentTest extends LinearMocks {

    private ContinuumLayerContent query;
    private DenseVector vector;
    @Before
    public void init() {
        query = new ContinuumLayerContent(indexer, 3);
        vector = vector(1.0, 2.0, 3.0);
        query.setState(vector);
    }

    @Test
    public void get() {
        assertEquals(1.0, query.get(a), epsilon);
    }

    @Test
    public void reset() {
        query.reset();
        DenseVector expected = new DenseVector(3);
        assertVectorsEqual(expected, query.getState(), epsilon);
    }

    @Test
    public void getStateStream() {
        Stream<Double> expected = Stream.of(1.0, 2.0, 3.0);
        Stream<Double> actual = query.getStateStream();
        assertStreamsEqual(expected, actual);
    }
}