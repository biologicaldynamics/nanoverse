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
import nanoverse.compiler.pipeline.translate.helpers.TranslationCallback;
import nanoverse.compiler.pipeline.translate.nodes.ObjectNode;
import nanoverse.compiler.pipeline.translate.symbol.*;
import org.junit.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class MapGrandchildVisitorTest {

    private TranslationCallback walker;
    private GrandchildResolver gcResolver;
    private MapGrandchildVisitor query;

    private static final int lineNumber = 1;

    @Before
    public void before() throws Exception {
        walker = mock(TranslationCallback.class);
        gcResolver = mock(GrandchildResolver.class);
        query = new MapGrandchildVisitor(walker, gcResolver);
    }

    @Test
    public void resolve() throws Exception {
        ASTNode child = mock(ASTNode.class);
        ASTNode grandchild = mock(ASTNode.class);
        when(gcResolver.getChildValue(child)).thenReturn(grandchild);
        when(child.getLineNumber()).thenReturn(lineNumber);

        String id = "test";
        when(grandchild.getIdentifier()).thenReturn(id);

        ResolvingSymbolTable rst = mock(ResolvingSymbolTable.class);
        InstantiableSymbolTable ist = mock(InstantiableSymbolTable.class);
        when(rst.getSymbolTable(id, lineNumber)).thenReturn(ist);

        ObjectNode expected = mock(ObjectNode.class);

        when(walker.walk(grandchild, ist)).thenReturn(expected);
        ObjectNode actual = query.walk(child, rst);

        assertSame(expected, actual);
    }
}