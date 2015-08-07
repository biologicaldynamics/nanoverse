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

package compiler.pipeline.translate.symbol.io.serialize;

import compiler.pipeline.instantiate.loader.Loader;
import compiler.pipeline.translate.nodes.ObjectNode;
import compiler.pipeline.translate.symbol.*;
import compiler.pipeline.translate.symbol.io.visual.VisualizationClassSymbolTable;
import compiler.pipeline.translate.symbol.primitive.strings.StringClassSymbolTable;
import io.serialize.binary.VisualizationSerializer;

import java.util.HashMap;

/**
 * Created by dbborens on 7/26/2015.
 */
public class VisualizationSerializerInstSymbolTable extends MapSymbolTable<VisualizationSerializer> {
    @Override
    public String getDescription() {
        return "Generates the specified visualization for each instance of the simulation.";
    }

    @Override
    protected HashMap<String, MemberSymbol> resolveMembers() {
        HashMap<String, MemberSymbol> ret = super.resolveMembers();
        prefix(ret);
        visualization(ret);
        return ret;
    }

    private void visualization(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new VisualizationClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "The properties of the visualization to be produced.");
        ret.put("visualization", ms);
    }

    private void prefix(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new StringClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "Prefix for the image files to be produced.");
        ret.put("prefix", ms);
    }

    @Override
    public Loader getLoader() {
        return null;
    }
}
