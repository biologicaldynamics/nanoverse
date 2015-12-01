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
import nanoverse.compiler.pipeline.translate.nodes.DictionaryObjectNode;
import nanoverse.compiler.pipeline.translate.nodes.ObjectNode;
import nanoverse.compiler.pipeline.translate.symbol.DictionarySymbolTable;
import org.junit.*;
import org.mockito.*;

import java.util.List;
import java.util.stream.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class DictionaryContainerVisitorTest {

    private DictionaryChildLoader loader;
    private DictionaryContainerVisitor query;
    private List<ASTNode> children;
    private static final int lineNumber = 1;

    @Before
    public void before() throws Exception {
        loader = mock(DictionaryChildLoader.class);
        query = new DictionaryContainerVisitor(loader);
        children = makeChildren();
    }

    private List<ASTNode> makeChildren() throws Exception {
        return IntStream.range(0, 4)
            .mapToObj(i -> mock(ASTNode.class))
            .collect(Collectors.toList());
    }

    @Test
    public void lineNumberToTranslate() throws Exception {
        ASTNode toTranslate = mock(ASTNode.class);
        String id = "test";
        when(toTranslate.getIdentifier()).thenReturn(id);
        when(toTranslate.getChildren()).thenReturn(children.stream());
        when(toTranslate.getLineNumber()).thenReturn(lineNumber);

        DictionarySymbolTable dst = mock(DictionarySymbolTable.class);
        when(dst.getBroadClass()).thenReturn(Object.class);

        ObjectNode node = query.translate(toTranslate, dst);
        assertEquals(lineNumber, node.getLineNumber());
    }

    @Test
    public void translate() throws Exception {
        ASTNode toTranslate = mock(ASTNode.class);
        String id = "test";
        when(toTranslate.getIdentifier()).thenReturn(id);
        when(toTranslate.getChildren()).thenReturn(children.stream());
        DictionarySymbolTable dst = mock(DictionarySymbolTable.class);
        when(dst.getBroadClass()).thenReturn(Object.class);

        query.translate(toTranslate, dst);

        verifyAll(dst);
    }

    private void verifyAll(DictionarySymbolTable dst) {
        ArgumentCaptor<DictionaryObjectNode> captor = ArgumentCaptor.forClass(DictionaryObjectNode.class);

        InOrder inOrder = inOrder(loader);

        for (int i = 0; i < 4; i++) {
            ASTNode child = children.get(i);
            inOrder.verify(loader)
                .loadChild(eq(child), eq(dst), captor.capture());

            DictionaryObjectNode dNode = captor.getValue();
            assertSame(dst, dNode.getSymbolTable());
        }
    }
}