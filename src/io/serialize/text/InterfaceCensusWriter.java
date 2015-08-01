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

package io.serialize.text;

import cells.Cell;
import control.GeneralParameters;
import control.arguments.*;
import control.halt.HaltCondition;
import control.identifiers.Coordinate;
import io.serialize.Serializer;
import layers.LayerManager;
import layers.cell.CellLayer;
import processes.StepState;
import structural.annotations.FactoryTarget;
import structural.utilities.FileConventions;

import java.io.BufferedWriter;
import java.util.*;

/**
 * @author dbborens
 */
public class InterfaceCensusWriter extends Serializer {

    private Integer focalState;

    private BufferedWriter bw;

    private HashSet<Integer> observedInterfaceStates;

    private HashMap<Integer, Map<Integer, Double>> frameToHistogramMap;

    @FactoryTarget
    public InterfaceCensusWriter(GeneralParameters p, IntegerArgument focalStateArg, LayerManager lm) {
        super(p, lm);

        try {
            focalState = focalStateArg.next();
        } catch (HaltCondition ex) {
            throw new IllegalStateException(ex);
        }
    }

    @Override
    public void init() {
        super.init();
        observedInterfaceStates = new HashSet<>();
        frameToHistogramMap = new HashMap<>();

        String filename = FileConventions.makeInterfaceFilename(focalState);
        mkDir(p.getInstancePath(), true);
        bw = makeBufferedWriter(p.getInstancePath() + filename);
    }

    private ArrayList<Coordinate> getFocalSites(StepState stepState) {
        CellLayer layer = stepState.getRecordedCellLayer();
        ArrayList<Coordinate> focalSites = new ArrayList<>();

        // Find all cells of focal type.
        HashSet<Coordinate> sites = layer.getViewer().getOccupiedSites();

        for (Coordinate site : sites) {
            Cell focalCell = layer.getViewer().getCell(site);
            if (focalCell.getState() == focalState) {
                focalSites.add(site);
            }
        }

        return focalSites;
    }

    private void processNeighbors(Coordinate site, StepState stepState,
                                  Map<Integer, Double> histo) {

        CellLayer layer = stepState.getRecordedCellLayer();

        int[] neighborStates = layer.getLookupManager().getNeighborStates(site, false);

        for (int neighborState : neighborStates) {
            increment(histo, neighborState);
            note(neighborState);
        }
    }

    private void note(int neighborState) {
        observedInterfaceStates.add(neighborState);
    }

    private void increment(Map<Integer, Double> histo, int neighborState) {
        if (!histo.containsKey(neighborState)) {
            histo.put(neighborState, 0.0);
        }

        double current = histo.get(neighborState);
        histo.put(neighborState, current + 1.0);
    }

    @Override
    public void flush(StepState stepState) {
        Map<Integer, Double> histo = new HashMap<>();
        ArrayList<Coordinate> focalSites = getFocalSites(stepState);
        processFocalSites(stepState, histo, focalSites);

        int frame = stepState.getFrame();
        frameToHistogramMap.put(frame, histo);
    }

    private void processFocalSites(StepState stepState, Map<Integer, Double> histo, ArrayList<Coordinate> focalSites) {
        for (Coordinate site : focalSites) {
            processNeighbors(site, stepState, histo);
        }
    }


    public void dispatchHalt(HaltCondition ex) {
        conclude();
        closed = true;
    }

    private void conclude() {
        // Sort the states numerically
        TreeSet<Integer> sortedStates = new TreeSet<>(observedInterfaceStates);
        writeHeader(sortedStates);

        TreeSet<Integer> sortedFrames = new TreeSet<>(frameToHistogramMap.keySet());


        for (Integer frame : sortedFrames) {
            Map<Integer, Double> absHisto = frameToHistogramMap.get(frame);
            Map<Integer, Double> relHisto = calcRelHisto(absHisto);

            StringBuilder line = new StringBuilder();
            line.append(frame);

            for (Integer state : sortedStates) {
                line.append("\t");
                line.append(relHisto.get(state));
            }
            line.append("\n");
            hAppend(bw, line);
        }
        hClose(bw);
    }

    /**
     * Convert absolute histogram of interface types to relative histogram.
     *
     * @param absHisto
     * @return
     */
    private Map<Integer, Double> calcRelHisto(Map<Integer, Double> absHisto) {
        double ttlObs = 0.0;

        // First, get the total number of observations
        for (double obs : absHisto.values()) {
            ttlObs += obs;
        }

        HashMap<Integer, Double> relHisto = new HashMap<>();

        for (int state : observedInterfaceStates) {
            if (!absHisto.containsKey(state)) {
                relHisto.put(state, 0.0);
            } else {
                double obs = absHisto.get(state);
                double relObs = obs / ttlObs;
                relHisto.put(state, relObs);
            }
        }

        return relHisto;
    }

    private void writeHeader(TreeSet<Integer> sortedStates) {
        // Write out the header
        StringBuilder line = new StringBuilder();
        line.append("frame");
        for (Integer state : sortedStates) {
            line.append("\t");
            line.append(state);
        }

        line.append("\n");

        hAppend(bw, line);
    }

    public void close() {
        // Doesn't do anything.
    }
}
