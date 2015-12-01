/*
 * Nanoverse: a declarative agent-based modeling language for natural and
 * social science.
 *
 * Copyright (c) 2015 David Bruce Borenstein and Nanoverse, LLC.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package nanoverse.compiler.pipeline.translate.visitors;

import nanoverse.compiler.pipeline.interpret.nodes.ASTNode;
import nanoverse.compiler.pipeline.translate.nodes.*;
import nanoverse.compiler.pipeline.translate.symbol.primitive.ConstantPrimitiveSymbolTable;
import org.slf4j.*;

/**
 * Takes a primitive AST node and a primitive symbol table,
 * and returns a primitive object node.
 * <p>
 * Created by dbborens on 4/22/15.
 */
public class PrimitiveVisitor {
    private final Logger logger;

    public PrimitiveVisitor() {
        logger = LoggerFactory.getLogger(PrimitiveVisitor.class);
    }

    public ObjectNode translate(ASTNode toTranslate, ConstantPrimitiveSymbolTable symbolTable) {
        logger.debug("Translating primitive.");

        ASTNode valueNode = toTranslate
            .getChildren()
            .findFirst()
            .get();

        int lineNumber = valueNode.getLineNumber();

        String valueStr = valueNode.getIdentifier();

        Object value = symbolTable.getValue(valueStr);
        return new PrimitiveObjectNode<>(symbolTable, value, lineNumber);
    }
}
