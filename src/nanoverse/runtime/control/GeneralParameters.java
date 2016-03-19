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

package nanoverse.runtime.control;

import nanoverse.runtime.structural.annotations.FactoryTarget;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * The parameter object returns general parameters for the simulation.
 *
 * @author dbborens
 */
public class GeneralParameters {

    protected Random random;            // Random number generator
    protected long randomSeed;
    // Dimensions
    private int maxStep;
    private int instances;
    // Path variables
    private String basePath;        // Path as specified
    private String path;            // May contain a time stamp
    // Output flags
    private String instancePath;    // Includes instance number�(if applies)
    // Instantiated members
    // State members
    private int instance;
    private String projectName;
    private boolean isStamp;

    private boolean showUserInterface = true;


    @FactoryTarget(displayName = "Parameters")
    public GeneralParameters(Random random,
                             long randomSeed,
                             int maxStep,
                             int instances,
                             String basePath,
                             String project,
                             boolean isStamp) {

        this.random = random;
        this.randomSeed = randomSeed;
        this.instances = instances;
        this.maxStep = maxStep;
        this.basePath = basePath;
        this.projectName = project;
        this.isStamp = isStamp;

        internalPaths();
        instance = 0;
        updateInstancePath();
    }

    private void updateInstancePath() {
        if (instances == 1) {
            instancePath = path;
        } else {
            instancePath = path + '/' + instance + '/';
        }

    }

    private void internalPaths() {
        if (isStamp) {
            path = basePath + '/' + date() + '/' + projectName + '/' + time() + '/';
        } else {
            path = basePath + projectName;
        }

    }

    private String date() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return sdf.format(date);
    }

    public String time() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH'h'mm'm'ss's'");
        Date date = new Date();
        return sdf.format(date);
    }

    // Minimal constructor for mock testing.
    public GeneralParameters() {
    }

    /**
     * Signals to the parameters object that it should
     * generate a new random number seed and advance the
     * instance counter.
     */
    public void advance() {
        instance++;
        updateInstancePath();
        randomSeed = System.currentTimeMillis();
        random = new Random(randomSeed);
    }

    /**
     * Returns max time step. Due to frequent calls, this getter's
     * name has been shortened.
     *
     * @return
     */
    public int T() {
        return maxStep;
    }

    public int getNumInstances() {
        return instances;
    }

    public String getBasePath() {
        return basePath;
    }

    public String getPath() {
        return path;
    }

    public String getInstancePath() {
        return instancePath;
    }

    public Random getRandom() {
        return random;
    }

    public long getRandomSeed() {
        return randomSeed;
    }

    public int getInstance() {
        return instance;
    }

    public boolean getShowUserInterface() {
        return showUserInterface;
    }
}
