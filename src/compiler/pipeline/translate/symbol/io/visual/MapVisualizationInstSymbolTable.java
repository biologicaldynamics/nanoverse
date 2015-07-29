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

package compiler.pipeline.translate.symbol.io.visual;

import compiler.pipeline.instantiate.Loader;
import compiler.pipeline.translate.nodes.ObjectNode;
import compiler.pipeline.translate.symbol.*;
import compiler.pipeline.translate.symbol.io.visual.color.ColorModelClassSymbolTable;
import compiler.pipeline.translate.symbol.io.visual.highlight.HighlightClassSymbolTable;
import compiler.pipeline.translate.symbol.primitive.integers.IntegerClassSymbolTable;
import io.visual.map.MapVisualization;

import java.util.HashMap;

/**
 * Created by dbborens on 7/27/2015.
 */
public class MapVisualizationInstSymbolTable extends MapSymbolTable<MapVisualization> {
    @Override
    public String getDescription() {
        return "A map is a 2D visualization consisting of one image per time " +
                "point, and with visualization coordinates corresponding " +
                "directly to their simulation coordinate counterparts.";
    }

    @Override
    protected HashMap<String, MemberSymbol> resolveMembers() {
        HashMap<String, MemberSymbol> ret = super.resolveMembers();
        edge(ret);
        outline(ret);
        color(ret);
        highlights(ret);
        return ret;
    }

    public void highlights(HashMap<String, MemberSymbol> ret) {
        ClassSymbolTable cst = new HighlightClassSymbolTable();
        ResolvingSymbolTable rst = new ListSymbolTable<>(cst);
        MemberSymbol ms = new MemberSymbol(rst, "Specifies which highlight " +
                "channels to visualize, if any, and how they should be " +
                "visualized.");
        ret.put("highlights", ms);
    }

    public void color(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new ColorModelClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "Specifies how simulation " +
                "state should be encoded into color, if at all.");
        ret.put("color", ms);
    }

    public void outline(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new IntegerClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "Specifies the thickness of " +
                "the outline around each lattice site.");
        ret.put("outline", ms);
    }

    public void edge(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new IntegerClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "Specifies the size of each " +
                "lattice site edge (and, by extension, the size of the image " +
                "as a whole).");
        ret.put("edge", ms);

    }

    @Override
    public Loader getLoader(ObjectNode node) {
        return null;
    }
}
