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

package compiler.pipeline.translate.symbol.io.serialize;

import compiler.pipeline.translate.symbol.ClassSymbolTable;
import compiler.pipeline.translate.symbol.tables.ClassSymbolTableTest;
import io.serialize.Serializer;
import io.serialize.binary.*;
import io.serialize.interactive.ProgressReporter;
import io.serialize.text.*;
import org.junit.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class OutputClassSymbolTableTest extends ClassSymbolTableTest {

    @Override
    protected ClassSymbolTable getQuery() {
        return new OutputClassSymbolTable();
    }

    @Override
    protected Class getExpectedClass() {
        return Serializer.class;
    }

    @Test
    public void cellStateWriter() throws Exception {
        verifyReturnSymbol("CellStateWriter", LegacyCellStateWriter.class);
    }

    @Test
    public void haltTimeWriter() throws Exception {
        verifyReturnSymbol("HaltTimeWriter", HaltTimeWriter.class);
    }

    @Test
    public void progressReporter() throws Exception {
        verifyReturnSymbol("ProgressReporter", ProgressReporter.class);
    }

    @Test
    public void censusWriter() throws Exception {
        verifyReturnSymbol("CensusWriter", CensusWriter.class);
    }

    @Test
    public void continuumHistogramWriter() throws Exception {
        verifyReturnSymbol("ContinuumHistogramWriter", ContinuumHistoWriter.class);
    }

    @Test
    public void surfaceCensusWriter() throws Exception {
        verifyReturnSymbol("SurfaceCensusWriter", SurfaceCensusWriter.class);
    }

    @Test
    public void individualHaltWriter() throws Exception {
        verifyReturnSymbol("IndividualHaltWriter", IndividualHaltWriter.class);
    }

    @Test
    public void interfaceCensusWriter() throws Exception {
        verifyReturnSymbol("InterfaceCensusWriter", InterfaceCensusWriter.class);
    }

    @Test
    public void randomSeedWriter() throws Exception {
        verifyReturnSymbol("RandomSeedWriter", RandomSeedWriter.class);
    }

    @Test
    public void runningTimeWriter() throws Exception {
        verifyReturnSymbol("RunningTimeWriter", RunningTimeWriter.class);
    }

    @Test
    public void coordinateIndexer() throws Exception {
        verifyReturnSymbol("CoordinateIndexer", CoordinateIndexer.class);
    }

    @Test
    public void continuumStateWriter() throws Exception {
        verifyReturnSymbol("ContinuumStateWriter", ContinuumStateWriter.class);
    }

    @Test
    public void timeWriter() throws Exception {
        verifyReturnSymbol("TimeWriter", TimeWriter.class);
    }

    @Test
    public void highlightWriter() throws Exception {
        verifyReturnSymbol("HighlightWriter", HighlightWriter.class);
    }

    @Test
    public void visualizationSerializer() throws Exception {
        verifyReturnSymbol("VisualizationSerializer", VisualizationSerializer.class);
    }

    @Test
    public void correlationWriter() throws Exception {
        verifyReturnSymbol("CorrelationWriter", CorrelationWriter.class);
    }
}