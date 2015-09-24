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

package processes.discrete;

import cells.MockCell;
import control.identifiers.Coordinate;
import geometry.MockGeometry;
import layers.MockLayerManager;
import layers.cell.CellLayer;
import org.junit.*;
import processes.BaseProcessArguments;
import processes.discrete.filter.NullFilter;
import processes.gillespie.GillespieState;
import structural.MockGeneralParameters;
import test.EslimeTestCase;

import static org.junit.Assert.*;
/**
 * This is a test for the TriggerProcess object, which is a discrete process
 * that triggers behaviors in cells. It should not be confused with the Trigger
 * action, which is an action that individual cells can execute as part of a
 * behavior.
 * <p>
 * Created by David B Borenstein on 2/18/14.
 */
public class TriggerProcessTest extends EslimeTestCase {

    private TriggerProcess trigger;
    private CellLayer layer;
    private MockLayerManager layerManager;
    private MockGeneralParameters p;
    private MockGeometry geom;

    @Before
    public void setUp() throws Exception {
        geom = buildMockGeometry();

        p = new MockGeneralParameters();
        p.initializeRandom(0);
        layer = new CellLayer(geom);
        layerManager = new MockLayerManager();
        layerManager.setCellLayer(layer);

//        trigger = new TriggerProcess(layerManager, 0, "test", p, true, false, -1, false);
        BaseProcessArguments arguments = makeBaseProcessArguments(layerManager, p);
        CellProcessArguments cpArguments = makeCellProcessArguments(geom);

        trigger = new TriggerProcess(arguments, cpArguments, "test", new NullFilter(), true, false);
    }

    /**
     * Make sure that the "target" method works as expected when
     * using Gillespie mode.
     */
    @Test
    public void testTargetGillespie() throws Exception {
        Integer[] keys = new Integer[]{0};
        GillespieState state = new GillespieState(keys);

        // Gillespie state should be updated by target

        trigger.target(state);
        state.close();
        assertEquals(1, state.getTotalCount());
        assertEquals(1.0, state.getTotalWeight(), epsilon);
    }

    @Test
    public void testLifeCycle() throws Exception {
        MockCell cell = new MockCell();
        Coordinate c = layer.getGeometry().getCanonicalSites()[0];
        layer.getUpdateManager().place(cell, c);
        assertTrue(layer.getViewer().isOccupied(c));
        trigger.target(null);
        trigger.fire(null);
        assertEquals("test", cell.getLastTriggeredBehaviorName());
        assertNull(cell.getLastTriggeredCaller());
    }

    /**
     * Make sure that, if cells are required to have occupied neigbhors in order
     * to be triggered, that the requirement is honored.
     *
     * @throws Exception
     */
    @Test
    public void testRequireNeighborsFlag() throws Exception {
        // Unlike the other tests, we want a trigger process that requires
        // cells to have at least one occupied neighbor in order to be
        // triggered.
//        trigger = new TriggerProcess(layerManager, 0, "test", p, true, true, -1, false);
        BaseProcessArguments arguments = makeBaseProcessArguments(layerManager, p);
        CellProcessArguments cpArguments = makeCellProcessArguments(geom);

        trigger = new TriggerProcess(arguments, cpArguments, "test", new NullFilter(), true, true);

        // Set up two neighboring cells and one isolated cell.
        MockCell neighbor1 = new MockCell();
        MockCell neighbor2 = new MockCell();
        MockCell isolated = new MockCell();
        setUpNeighborhoodTestCase(neighbor1, neighbor2, isolated);


        // Execute the trigger event.
        trigger.target(null);
        trigger.fire(null);

        // Only the neighboring cells should be triggered.
        assertEquals(1, neighbor1.getTriggerCount());
        assertEquals(1, neighbor2.getTriggerCount());
        assertEquals(0, isolated.getTriggerCount());
    }

    //    public void testRecordAfterTargeting() throws Exception {
//        fail("Not yet implemented");
//    }
    private void setUpNeighborhoodTestCase(MockCell neighbor1, MockCell neighbor2, MockCell isolated) throws Exception {
        MockGeometry geom = (MockGeometry) layer.getGeometry();
        // 0, 0, 0
        Coordinate nc1 = geom.getCanonicalSites()[0];
        layer.getUpdateManager().place(neighbor1, nc1);

        // 0, 0, 1
        Coordinate nc2 = geom.getCanonicalSites()[1];
        layer.getUpdateManager().place(neighbor2, nc2);

        // 0, 1, 1
        Coordinate ni = geom.getCanonicalSites()[3];
        layer.getUpdateManager().place(isolated, ni);

        // Since we're using a mock geometry, we have to manually define
        // the neighborhoods.
        geom.setCellNeighbors(nc1, new Coordinate[]{nc2});
        geom.setCellNeighbors(nc2, new Coordinate[]{nc1});
        geom.setCellNeighbors(ni, new Coordinate[]{});

    }

}