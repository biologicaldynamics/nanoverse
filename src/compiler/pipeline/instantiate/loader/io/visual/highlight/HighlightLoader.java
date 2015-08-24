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

package compiler.pipeline.instantiate.loader.io.visual.highlight;

import compiler.pipeline.instantiate.factory.io.visual.highlight.HighlightFactory;
import compiler.pipeline.instantiate.loader.Loader;
import compiler.pipeline.translate.nodes.MapObjectNode;
import control.GeneralParameters;
import io.visual.highlight.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by dbborens on 8/4/2015.
 */
public class HighlightLoader extends Loader<Highlight> {
    private final HighlightFactory factory;
    private final HighlightInterpolator interpolator;

    public HighlightLoader() {
        factory = new HighlightFactory();
        interpolator = new HighlightInterpolator();
    }

    public HighlightLoader(HighlightFactory factory,
                           HighlightInterpolator interpolator) {

        this.factory = factory;
        this.interpolator = interpolator;
    }

    public Highlight instantiate(MapObjectNode mNode, GeneralParameters p) {
        Integer channel = interpolator.channel(mNode, p.getRandom());
        factory.setChannel(channel);

        Glyph glyph = interpolator.glyph(mNode, p);
        factory.setGlyph(glyph);

        return factory.build();
    }
}
