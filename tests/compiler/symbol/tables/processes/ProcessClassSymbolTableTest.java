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

package compiler.symbol.tables.processes;

import compiler.symbol.tables.*;
import org.junit.*;
import processes.*;
import processes.continuum.*;
import processes.discrete.*;
import processes.discrete.check.*;
import processes.temporal.*;

public class ProcessClassSymbolTableTest extends ClassSymbolTableTest {

    @Override
    protected ClassSymbolTable getQuery() {
        return new ProcessClassSymbolTable();
    }

    @Override
    protected Class getExpectedClass() {
        return NanoverseProcess.class;
    }

    @Test
    public void record() {
        verifyReturnSymbol("Record", Record.class);
    }

    @Test
    public void integrate() {
        verifyReturnSymbol("Integrate", Integrate.class);
    }

    @Test
    public void hold() {
        verifyReturnSymbol("Hold", ScheduleHold.class);
    }

    @Test
    public void release() {
        verifyReturnSymbol("Release", ScheduleRelease.class);

    }

    @Test
    public void inject() {
        verifyReturnSymbol("Inject", InjectionProcess.class);
    }

    @Test
    public void diffuse() {
        verifyReturnSymbol("Diffuse", OperatorProcess.class);
    }

    @Test
    public void checkForExtinction() {
        verifyReturnSymbol("CheckForExtinction", CheckForExtinction.class);
    }

    @Test
    public void checkForDomination() {
        verifyReturnSymbol("CheckForDomination", CheckForDomination.class);
    }

    @Test
    public void checkThresholdOccupancy() {
        verifyReturnSymbol("CheckForThresholdOccupancy", CheckForThresholdOccupancy.class);
    }

    @Test
    public void checkForFixation() {
        verifyReturnSymbol("CheckForFixation", CheckForFixation.class);
    }

    @Test
    public void cull() {
        verifyReturnSymbol("Cull", Cull.class);
    }

    @Test
    public void trigger() {
        verifyReturnSymbol("Trigger", TriggerProcess.class);
    }

    @Test
    public void mockProcess() {
        verifyReturnSymbol("Mock", MockProcess.class);
    }

    @Test
    public void fill() {
        verifyReturnSymbol("Fill", Fill.class);
    }

    @Test
    public void scatter() {
        verifyReturnSymbol("Scatter", Scatter.class);
    }

    @Test
    public void powerScatter() {
        verifyReturnSymbol("PowerScatter", PowerScatter.class);
    }

    @Test
    public void scatterClusters() {
        verifyReturnSymbol("ScatterClusters", ScatterClusters.class);
    }

    @Test
    public void generalNeighborSwap() {
        verifyReturnSymbol("GeneralNeighborSwap", GeneralNeighborSwap.class);
    }

    @Test
    public void occupiedNeighborSwap() {
        verifyReturnSymbol("OccupiedNeighborSwap", OccupiedNeighborSwap.class);
    }

    @Test
    public void divide() {
        verifyReturnSymbol("Divide", Divide.class);
    }

    @Test
    public void manualHalt() {
        verifyReturnSymbol("ManualHalt", ManualHalt.class);
    }

    @Test
    public void tick() {
        verifyReturnSymbol("Tick", Tick.class);
    }
}