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

package compiler.pipeline.instantiate.loader.processes.continuum;

import compiler.pipeline.instantiate.factory.processes.continuum.ScheduleReleaseFactory;
import compiler.pipeline.instantiate.loader.processes.ProcessLoader;
import compiler.pipeline.translate.nodes.MapObjectNode;
import control.GeneralParameters;
import layers.LayerManager;
import layers.continuum.ContinuumLayerScheduler;
import processes.BaseProcessArguments;
import processes.continuum.*;

/**
 * Created by dbborens on 8/3/2015.
 */
public class ScheduleReleaseLoader extends ProcessLoader<ScheduleRelease> {
    private final ScheduleReleaseFactory factory;
    private final ScheduleReleaseInterpolator interpolator;

    public ScheduleReleaseLoader() {
        factory = new ScheduleReleaseFactory();
        interpolator = new ScheduleReleaseInterpolator();
    }

    public ScheduleReleaseLoader(ScheduleReleaseFactory factory,
                              ScheduleReleaseInterpolator interpolator) {
        this.factory = factory;
        this.interpolator = interpolator;
    }

    @Override
    public ScheduleRelease instantiate(MapObjectNode node, LayerManager lm, GeneralParameters p) {
        BaseProcessArguments arguments = interpolator.arguments(node, lm, p);
        factory.setArguments(arguments);

        String layer = interpolator.layer(node);
        ContinuumLayerScheduler scheduler = interpolator.scheduler(lm, layer);
        factory.setScheduler(scheduler);

        return factory.build();
    }
}
