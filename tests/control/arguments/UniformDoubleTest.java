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

package control.arguments;

import org.junit.*;

import java.util.Random;

import static org.junit.Assert.assertEquals;
/**
 * Created by David B Borenstein on 4/7/14.
 */
public class UniformDoubleTest extends ArgumentTest {

    protected double[] results;
    private double min = 1.0;
    private double max = 10.0;

    @Before
    public void setUp() throws Exception {
        Random random = new Random(super.RANDOM_SEED);
        UniformDouble query = new UniformDouble(min, max, random);

        results = new double[NUM_ITERATES];
        for (int i = 0; i < NUM_ITERATES; i++) {
            results[i] = query.next();
        }
    }

    @Test
    public void testMean() throws Exception {
        double expected = 4.5;
        double actual = mean(results);

        double var = (1.0 / 12.0) * Math.pow(max - min, 2.0);
        assertEquals(expected, actual, var);
    }

}
