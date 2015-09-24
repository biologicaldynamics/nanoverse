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

package io.visual.highlight;

import io.visual.glyph.MockGlyph;
import layers.MockSystemState;
import org.junit.*;
import test.EslimeLatticeTestCase;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.junit.Assert.assertEquals;
/**
 * Created by dbborens on 4/2/14.
 */
public class HighlightManagerTest extends EslimeLatticeTestCase {

    MockGlyph glyph;
    HighlightManager query;

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
        glyph = new MockGlyph();
        query = new HighlightManager();
        query.setGlyph(0, glyph);
    }

    @Test
    public void testSetGraphics() throws Exception {
        BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = (Graphics2D) image.getGraphics();
        query.setGraphics(graphics);
        assertEquals(graphics, glyph.getGraphics());
    }


    @Test
    public void testOverlayGlyphs() throws Exception {
        MockSystemState systemState = new MockSystemState();
        systemState.setHighlighted(true);
        query.render(origin, systemState);
        assertEquals(origin, glyph.getLastOverlaid());
    }

    @Test
    public void testGetHighlightChannels() throws Exception {
        query.setGlyph(2, new MockGlyph());
        int[] expected = new int[]{0, 2};
        int[] actual = query.getHighlightChannels();
        assertArraysEqual(expected, actual, true);
    }
}
