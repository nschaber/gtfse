package de.nsc.gtfseapi.mapper;

import de.nsc.gtfseapi.model.*;
import org.geolatte.geom.G2D;
import org.geolatte.geom.Point;
import org.geolatte.geom.builder.DSL;
import org.geolatte.geom.crs.CoordinateReferenceSystems;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.onebusaway.gtfs.model.*;

@Mapper(componentModel = "spring")
public interface GtfsMapper {

    default Point<G2D> toPoint(double lon, double lat) {
        return DSL.point(CoordinateReferenceSystems.WGS84, DSL.g(lon, lat));
    }

    @Mapping(source = "lang", target = "language")
    AgencyEntity toEntity(Agency agency);

    @Mapping(source = "id.id", target = "id")
    RouteEntity toEntity(Route route);

    @Mapping(source = "id.id", target = "id")
    @Mapping(target = "location", expression = "java(toPoint(stop.getLon(), stop.getLat()))")
    StopEntity toEntity(Stop stop);

    @Mapping(source = "id.id", target = "id")
    @Mapping(source = "serviceId.id", target = "serviceId")
    @Mapping(source = "shapeId.id", target = "shapeId")
    TripEntity toEntity(Trip trip);

    StopTimeEntity toEntity(StopTime stopTime);

    Stop toStop(StopLocation stopLocation);

}