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

package compiler.pipeline.translate.visitors;

import compiler.pipeline.interpret.nodes.ASTNode;
import compiler.pipeline.translate.helpers.TranslationCallback;
import compiler.pipeline.translate.nodes.ObjectNode;
import compiler.pipeline.translate.symbol.*;
import org.junit.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class MapChildTranslatorTest {

    private TranslationCallback walker;
    private MapGrandchildVisitor gcResolver;
    private MapChildTranslator query;

    private ASTNode child;
    private ObjectNode expected;
    @Before
    public void before() throws Exception {
        walker = mock(TranslationCallback.class);
        gcResolver = mock(MapGrandchildVisitor.class);
        query = new MapChildTranslator(walker, gcResolver);

        child = mock(ASTNode.class);
        expected = mock(ObjectNode.class);
    }

    @Test
    public void simpleCase() throws Exception {
        ResolvingSymbolTable rst = mock(ClassSymbolTable.class);
        when(gcResolver.walk(child, rst)).thenReturn(expected);

        ObjectNode actual = query.translate(child, rst);
        assertSame(expected, actual);
    }

    @Test
    public void listCase() throws Exception {
        ResolvingSymbolTable rst = mock(ListSymbolTable.class);
        when(walker.walk(child, rst)).thenReturn(expected);

        ObjectNode actual = query.translate(child, rst);
        assertSame(expected, actual);
    }

    @Test
    public void dictCase() throws Exception {
        ResolvingSymbolTable rst = mock(DictionarySymbolTable.class);
        when(walker.walk(child, rst)).thenReturn(expected);

        ObjectNode actual = query.translate(child, rst);
        assertSame(expected, actual);
    }
}