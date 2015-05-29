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

package io.serialize.binary;

import io.serialize.binary.csw.*;
import org.junit.*;
import processes.StepState;

import static org.mockito.Mockito.*;

public class ContinuumStateWriterTest {

    private CSWExtremaHelper extremaHelper;
    private CSWFileHelper fileHelper;
    private StepState stepState;
    private ContinuumStateWriter query;
    @Before
    public void before() throws Exception {
        extremaHelper = mock(CSWExtremaHelper.class);
        fileHelper = mock(CSWFileHelper.class);
        stepState = mock(StepState.class);
        query = new ContinuumStateWriter(null, null);
        query.init(extremaHelper, fileHelper);
    }

    @Test
    public void flushNotifiesExtremaHelper() throws Exception {
        query.flush(stepState);
        verify(extremaHelper).push(stepState);
    }

    @Test
    public void flushNotifiesFileHelper() throws Exception {
        query.flush(stepState);
        verify(fileHelper).push(stepState);
    }

    @Test
    public void dispatchHaltNotifiesExtremaHelper() throws Exception {
        query.dispatchHalt(null);
        verify(extremaHelper).serialize();
    }

    @Test
    public void dispatchHaltNotifiesFileHelper() throws Exception {
        query.dispatchHalt(null);
        verify(fileHelper).close();
    }

    @Test
    public void closeDoesNothing() throws Exception {
        query.close();
        verifyNoMoreInteractions(extremaHelper, fileHelper);
    }
}