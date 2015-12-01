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

package nanoverse.compiler.pipeline.interpret.nodes;

import com.google.common.base.Strings;

import java.util.List;
import java.util.stream.*;

/**
 * Created by dbborens on 4/21/15.
 */
public class ASTNode {
    private final String identifier;
    private final List<ASTNode> children;
    private final int lineNumber;

    public ASTNode(String identifier, Stream<ASTNode> children, int lineNumber) {

        this.identifier = identifier;
        this.children = children.collect(Collectors.toList());
        this.lineNumber = lineNumber;
    }

    public int size() {
        return children.size();
    }

    public int getLineNumber() { return lineNumber; }

    public Stream<ASTNode> getChildren() {
        return children.stream();
    }

    public String getIdentifier() {
        return identifier;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ASTNode blockNode = (ASTNode) o;

        if (!identifier.equals(blockNode.identifier)) return false;
        if (!children.equals(blockNode.children)) return false;

        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        astReport(sb, 0);
        return sb.toString();
    }

    public void astReport(StringBuilder builder, int indentLevel) {
        builder.append(Strings.repeat(" ", indentLevel));
        builder.append("container: " + identifier + "(" + size() + ")\n");
        if (children == null) {
            throw new IllegalStateException("Container " + identifier + " has null child list");
        }
        children.stream()
            .forEach(child -> {
                if (child == null) {
                    throw new IllegalStateException("Container " + identifier + " has a null child");
                }
                child.astReport(builder, indentLevel + 1);
            });
    }
}
