package compiler.pipeline.instantiate.loader.geometry.boundary;

import compiler.pipeline.instantiate.factory.geometry.boundaries.AbsorbingFactory;
import compiler.pipeline.instantiate.factory.geometry.boundaries.PlaneRingReflectingFactory;
import compiler.pipeline.translate.nodes.MapObjectNode;
import control.arguments.GeometryDescriptor;
import geometry.boundaries.*;

/**
 * Created by dbborens on 8/4/2015.
 */
public class PlaneRingReflectingBoundaryLoader extends BoundaryLoader<PlaneRingReflecting> {
    private final PlaneRingReflectingFactory factory;

    public PlaneRingReflectingBoundaryLoader() {
        factory = new PlaneRingReflectingFactory();
    }

    public PlaneRingReflectingBoundaryLoader(PlaneRingReflectingFactory factory) {
        this.factory = factory;
    }

    @Override
    public Boundary instantiate(GeometryDescriptor geom) {
        factory.setLattice(geom.getLattice());
        factory.setShape(geom.getShape());
        return factory.build();
    }
}
