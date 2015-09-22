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

package compiler.pipeline.interpret.visitors;

import compiler.pipeline.interpret.nanosyntax.NanosyntaxParser.IdContext;
import compiler.pipeline.interpret.nodes.ASTNode;
import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.*;
import test.TestBase;

import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class NanoStandaloneIdVisitorTest extends TestBase {

    private String id = "test";

    private NanoStandaloneIdVisitor query;
    private IdContext ctx;

    @Before
    public void before() throws Exception {
        query = new NanoStandaloneIdVisitor();
        ctx = mock(IdContext.class);
    }

    @Test
    public void visitId() throws Exception {
        ParseTree idTree = mock(ParseTree.class);
        when(ctx.getChild(0)).thenReturn(idTree);

        CommonToken payload = mock(CommonToken.class);
        when(idTree.getPayload()).thenReturn(payload);

        when(idTree.getText()).thenReturn(id);

        ASTNode result = query.visitId(ctx);
        verifyResult(result);
    }

    private void verifyResult(ASTNode result) {
        verifyId(result);
        verifyChildren(result);

    }

    private void verifyId(ASTNode result) {
        String expected = id;
        String actual = result.getIdentifier();

        assertEquals(expected, actual);
    }

    private void verifyChildren(ASTNode result) {
        Stream<ASTNode> expected = Stream.empty();
        Stream<ASTNode> actual = result.getChildren();

        assertStreamsEqual(expected, actual);
    }

}