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

package nanoverse.compiler.pipeline.instantiate.loader.primitive.strings;

import nanoverse.compiler.pipeline.instantiate.loader.Loader;
import nanoverse.compiler.pipeline.translate.nodes.*;
import nanoverse.runtime.control.arguments.*;

/**
 * Created by dbborens on 8/1/2015.
 */
public class StringArgumentLoader extends Loader<StringArgument> {

    public ConstantString instantiate(ObjectNode node) {
        String value = getValue(node);
        return new ConstantString(value);
    }

    private String getValue(ObjectNode node) {
        PrimitiveObjectNode<String> pNode = (PrimitiveObjectNode) node;
        String value = pNode.getValue();
        return value;
    }

    public String instantiateToFirst(ObjectNode node) {
        String value = getValue(node);
        return value;
    }
}
