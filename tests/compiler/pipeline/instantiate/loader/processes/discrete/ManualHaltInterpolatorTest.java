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

package compiler.pipeline.instantiate.loader.processes.discrete;

import compiler.pipeline.instantiate.loader.InterpolatorTest;
import org.junit.*;

import java.util.function.Supplier;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ManualHaltInterpolatorTest extends InterpolatorTest {

    private ManualHaltDefaults defaults;
    private ManualHaltInterpolator query;

    @Before
    public void before() throws Exception {
        super.before();
        defaults = mock(ManualHaltDefaults.class);
        query = new ManualHaltInterpolator(load, null, null, defaults);
    }

    @Test
    public void message() throws Exception {
        Supplier<String> trigger = () -> query.message(node);
        verifyString("message", trigger);
    }

    @Test
    public void messageDefault() throws Exception {
        when(defaults.message()).thenReturn("test");
        Runnable trigger = () -> query.message(node);
        verifyStringDefault("message", "test", trigger);
    }
}