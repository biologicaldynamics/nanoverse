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

package compiler.pipeline.instantiate.loader.agent;

import agent.targets.TargetDescriptor;
import compiler.pipeline.instantiate.loader.InterpolatorTest;
import compiler.pipeline.instantiate.loader.agent.targets.TargetLoader;
import compiler.pipeline.instantiate.loader.layers.continuum.ReactionStreamLoader;
import compiler.pipeline.translate.nodes.*;
import control.arguments.*;
import org.junit.*;

import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class AgentDescriptorInterpolatorTest extends InterpolatorTest {

    private AgentDescriptorDefaults defaults;
    private AgentDescriptorInterpolator query;

    @Before @Override
    public void before() throws Exception {
        super.before();
        defaults = mock(AgentDescriptorDefaults.class);
        query = new AgentDescriptorInterpolator(load, defaults);
    }

    @Test
    public void behaviors() throws Exception {
        DictionaryObjectNode cNode = mock(DictionaryObjectNode.class);
        when(node.getMember("behaviors")).thenReturn(cNode);

        BehaviorMapLoader loader = mock(BehaviorMapLoader.class);
        when(load.getLoader(eq(node), eq("behaviors"), anyBoolean())).thenReturn(loader);

        Map expected = mock(Map.class);
        when(loader.instantiate(cNode, lm, p)).thenReturn(expected);

        Map actual = query.behaviors(node, lm, p);
        assertSame(expected, actual);
    }

    @Test
    public void behaviorsDefault() throws Exception {
        Map expected = mock(Map.class);
        when(defaults.behaviors()).thenReturn(expected);
        Map actual = query.behaviors(node, lm, p);
        assertSame(expected, actual);
    }

    @Test
    public void clazz() throws Exception {
        Supplier<IntegerArgument> trigger = () -> query.clazz(node, random);
        verifyIntegerArgument("class", trigger);
    }

    @Test
    public void clazzDefault() throws Exception {
        IntegerArgument expected = mock(IntegerArgument.class);
        when(defaults.clazz()).thenReturn(expected);
        Runnable trigger = () -> query.clazz(node, random);
        verifyIntegerArgumentDefault("class", expected, trigger);
    }

    @Test
    public void initialHealth() throws Exception {
        Supplier<DoubleArgument> trigger = () -> query.initialHealth(node, random);
        verifyDoubleArgument("initialHealth", trigger);
    }

    @Test
    public void initialHealthDefault() throws Exception {
        DoubleArgument expected = mock(DoubleArgument.class);
        when(defaults.initialHealth()).thenReturn(expected);
        Runnable trigger = () -> query.initialHealth(node, random);
        verifyDoubleArgumentDefault("initialHealth", expected, trigger);
    }

    @Test
    public void threshold() throws Exception {
        Supplier<DoubleArgument> trigger = () -> query.threshold(node, random);
        verifyDoubleArgument("threshold", trigger);
    }

    @Test
    public void thresholdDefault() throws Exception {
        DoubleArgument expected = mock(DoubleArgument.class);
        when(defaults.threshold()).thenReturn(expected);
        Runnable trigger = () -> query.threshold(node, random);
        verifyDoubleArgumentDefault("threshold", expected, trigger);
    }

    @Test
    public void reactions() throws Exception {
        ListObjectNode cNode = mock(ListObjectNode.class);
        when(node.getMember("reactions")).thenReturn(cNode);

        ReactionStreamLoader loader = mock(ReactionStreamLoader.class);
        when(load.getLoader(eq(node), eq("reactions"), anyBoolean())).thenReturn(loader);

        Stream expected = mock(Stream.class);
        when(loader.instantiate(cNode, random)).thenReturn(expected);

        Stream actual = query.reactions(node, random);
        assertSame(expected, actual);
    }

    @Test
    public void reactionsDefault() throws Exception {
        Stream expected = mock(Stream.class);
        when(defaults.reactions()).thenReturn(expected);
        Stream actual = query.reactions(node, random);
        assertSame(expected, actual);
    }
}