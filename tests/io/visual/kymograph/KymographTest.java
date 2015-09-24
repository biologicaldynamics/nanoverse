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

package io.visual.kymograph;

import control.identifiers.Coordinate;
import geometry.Geometry;
import geometry.boundaries.*;
import geometry.lattice.*;
import geometry.shape.*;
import io.deserialize.MockCoordinateDeindexer;
import io.visual.VisualizationProperties;
import io.visual.color.*;
import io.visual.highlight.HighlightManager;
import layers.LightweightSystemState;
import org.junit.Test;
import test.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;

public class KymographTest extends EslimeTestCase {

    @Test
    public void testWithOutline() throws Exception {
        doTest(1, "KymographOutline.png");
    }

    private void doTest(int outline, String filename) throws Exception {
        Lattice lattice = new LinearLattice();
        Shape shape = new Line(lattice, 5);
        Boundary boundary = new Arena(shape, lattice);
        Geometry geom = new Geometry(lattice, shape, boundary);
        ColorManager colorManager = new DefaultColorManager();
        VisualizationProperties mapState = new VisualizationProperties(colorManager, 25, outline);
        HighlightManager highlightManager = new HighlightManager();
        mapState.setHighlightManager(highlightManager);
        Kymograph map = new Kymograph(mapState);

        double[] times = new double[]{0.0};
        int[] frames = new int[]{0};
        map.init(geom, times, frames);
        LightweightSystemState systemState = makeSystemState(geom);

        BufferedImage result = map.render(systemState);
        File file = new File(outputPath + filename);
        ImageIO.write(result, "png", file);

        FileAssertions.assertOutputMatchesFixture("glyphs/" + filename, filename, false);
//        assertBinaryFilesEqual("glyphs/" + filename, filename);

    }

    protected LightweightSystemState makeSystemState(Geometry geom) {
        MockCoordinateDeindexer deindexer = new MockCoordinateDeindexer();
        deindexer.setUnderlying(geom.getCanonicalSites());


        LightweightSystemState ret = new LightweightSystemState(geom);
        populateStateAndHealth(geom, ret);
        Set<Coordinate> highlights = new HashSet<>();
        for (Coordinate c : geom.getCanonicalSites()) {
            highlights.add(c);
        }
        ret.setHighlights(0, highlights);
        ret.setTime(0.0);
        ret.setFrame(0);

        return ret;
    }

    protected void populateStateAndHealth(Geometry geom, LightweightSystemState systemState) {
        int n = geom.getCanonicalSites().length;
        double[] health = new double[n];
        int[] state = new int[n];

        for (int i = 0; i < n; i++) {
            health[i] = 0.0;
            state[i] = i % 3;
        }
        systemState.initCellLayer(state);

    }

    @Test
    public void testNoOutline() throws Exception {
        doTest(0, "KymographNoOutline.png");
    }
}