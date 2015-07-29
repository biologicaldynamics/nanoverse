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

package compiler.pipeline.translate.symbol.io.visual.highlight;

import compiler.pipeline.instantiate.Loader;
import compiler.pipeline.translate.nodes.ObjectNode;
import compiler.pipeline.translate.symbol.*;
import compiler.pipeline.translate.symbol.primitive.doubles.DoubleClassSymbolTable;
import compiler.pipeline.translate.symbol.primitive.strings.StringClassSymbolTable;
import io.visual.highlight.CrosshairsGlyph;

import java.util.HashMap;

/**
 * Created by dbborens on 7/28/2015.
 */
public class CrosshairsGlyphInstSymbolTable extends MapSymbolTable<CrosshairsGlyph> {
    @Override
    public String getDescription() {
        return "A crosshairs glyph appears as a circle overlaid with a symmetrical cross.";
    }

    @Override
    protected HashMap<String, MemberSymbol> resolveMembers() {
        HashMap<String, MemberSymbol> ret = super.resolveMembers();
        circle(ret);
        cross(ret);
        color(ret);
        return ret;
    }

    private void color(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new StringClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "Hexadecimal representation of the glyph's color (RRGGBB).");
        ret.put("color", ms);
    }

    private void cross(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new DoubleClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "Size of the cross of the " +
                "crosshairs, specified as a multiple of the size of the " +
                "circle component of the crosshairs.");
        ret.put("cross", ms);
    }

    private void circle(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new DoubleClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "Size of the circle of the " +
                "crosshairs, specified as a multiple of the size of the " +
                "lattice site in the visualization.");
        ret.put("circle", ms);
    }

    @Override
    public Loader getLoader(ObjectNode node) {
        return null;
    }
}
