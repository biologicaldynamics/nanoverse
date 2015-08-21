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
import org.junit.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ContinuumHistoWriterInterpolatorTest extends InterpolatorTest {

    private ContinuumHistoWriterInterpolator query;

    @Before
    public void before() throws Exception {
        super.before();
        query = new ContinuumHistoWriterInterpolator(load);
    }

    @Test
    public void layerId() throws Exception {
        String expected = "test";
        when(load.aString(node, "layerId")).thenReturn(expected);
        String actual = query.layerId(node);
        assertEquals(expected, actual);
    }

    @Test
    public void occupiedOnly() throws Exception {
        boolean expected = true;
        when(load.aBoolean(eq(node), eq("occupiedOnly"), eq(random), any()))
                .thenReturn(expected);
        boolean actual = query.occupiedOnly(node, random);
        assertEquals(expected, actual);
    }

    @Test
    public void occupiedOnlyDefault() throws Exception {
        verifyBooleanDefault("occupiedOnly",
                ContinuumHistoWriterInterpolator.DEFAULT_OCCUPIED_ONLY,
                () -> query.occupiedOnly(node, random));
    }
}