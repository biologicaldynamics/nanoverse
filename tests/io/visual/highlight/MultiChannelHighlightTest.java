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

import control.identifiers.Coordinate;
import geometry.Geometry;
import geometry.boundaries.Absorbing;
import geometry.boundaries.Boundary;
import geometry.lattice.Lattice;
import geometry.lattice.TriangularLattice;
import geometry.shape.Rectangle;
import geometry.shape.Shape;
import io.deserialize.MockCoordinateDeindexer;
import io.visual.VisualizationProperties;
import io.visual.color.ColorManager;
import io.visual.color.DefaultColorManager;
import io.visual.map.MapVisualization;
import layers.LightweightSystemState;
import layers.SystemState;
import test.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * Test to make sure that multi-channel highlighting works as expected.
 * Created by dbborens on 4/3/14.
 */
public class MultiChannelHighlightTest extends EslimeTestCase {

    protected SystemState systemState;
    Geometry geometry;
    private HighlightManager highlightManager;
    private MapVisualization map;

    @Override
    protected void setUp() throws Exception {
        geometry = makeGeometry();

        // Create 10x10 triangular lattice.
        ColorManager colorManager = new DefaultColorManager();

        // Create a 10 x 10 hexagonal map.
        VisualizationProperties mapState = new VisualizationProperties(colorManager, 50, 1);

        // Create highlight manager.
        highlightManager = new HighlightManager();
        mapState.setHighlightManager(highlightManager);

        // Channel 0 has a small glyph.
        Glyph bullseye = new BullseyeGlyph(Color.DARK_GRAY, Color.WHITE, 0.5);
        highlightManager.setGlyph(0, bullseye);

        Glyph crosshairs = new CrosshairsGlyph(Color.PINK, 0.3, 1.2);
        highlightManager.setGlyph(1, crosshairs);

        // Create map visualization.
        map = new MapVisualization(mapState);
        map.init(geometry, null, null);

        // Create system state
        systemState = makeSystemState(geometry);

    }

    protected void populateStateAndHealth(Geometry geom, LightweightSystemState systemState) {
        int n = geom.getCanonicalSites().length;
        double[] health = new double[n];
        int[] state = new int[n];

        for (int i = 0; i < n; i++) {
            health[i] = 0;
            state[i] = 0;
        }
        systemState.initCellLayer(state);

    }

    protected LightweightSystemState makeSystemState(Geometry geom) {
        MockCoordinateDeindexer deindexer = new MockCoordinateDeindexer();
        deindexer.setUnderlying(geom.getCanonicalSites());


        LightweightSystemState ret = new LightweightSystemState(geom);
        populateStateAndHealth(geom, ret);

        Set<Coordinate> highlights0 = new HashSet<>();
        Set<Coordinate> highlights1 = new HashSet<>();

        Coordinate[] cc = geom.getCanonicalSites();
        Coordinate a = cc[0];
        Coordinate b = cc[1];
        Coordinate c = cc[2];

        highlights0.add(a);
        highlights0.add(b);

        highlights1.add(b);
        highlights1.add(c);

        ret.setHighlights(0, highlights0);
        ret.setHighlights(1, highlights1);

        ret.setTime(0.0);
        ret.setFrame(0);

        return ret;
    }

    protected Geometry makeGeometry() {
        Lattice lattice = new TriangularLattice();
        Shape shape = new Rectangle(lattice, 10, 10);
        Boundary boundary = new Absorbing(shape, lattice);
        Geometry geometry = new Geometry(lattice, shape, boundary);
        return geometry;
    }

    public void testOverlay() throws Exception {
        // Render the frame.
        BufferedImage result = map.render(systemState);

        File file = new File(outputPath + getFileName());
        ImageIO.write(result, "png", file);

        FileAssertions.assertOutputMatchesFixture("glyphs/" + getFileName(), getFileName(), false);
//        assertBinaryFilesEqual("glyphs/" + getFileName(), getFileName());
    }


    protected String getFileName() {
        return "multipleGlyphs.png";
    }
}

