package de.nsc.gtfseapi.application.repository;

import de.nsc.gtfseapi.model.StopEntity;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ITileRepository extends JpaRepository<StopEntity, String> {

    @Query(value = "WITH bounds AS (SELECT ST_TileEnvelope(:z, :x, :y) AS geom), mvtgeom AS (SELECT ST_AsMVTGeom(ST_Transform(st.location, 3857), bounds.geom) AS geom, st.id, st.code, st.url, st.parent_station, st.name FROM gtfs_stop st, bounds WHERE st.location && ST_Transform(bounds.geom, 4326)) SELECT ST_AsMVT(mvtgeom.*, 'stops') FROM mvtgeom", nativeQuery = true)
    byte[] getStops(@NonNull @Param("z") final Integer z, @NonNull @Param("x") final Integer x, @NonNull @Param("y") final Integer y);

    @Query(value = "WITH bounds AS (SELECT ST_TileEnvelope(:z, :x, :y) AS geom), mvtgeom AS (SELECT ST_AsMVTGeom(ST_Transform(st.location, 3857), bounds.geom) AS geom, st.id, st.code, st.url, st.parent_station, st.name FROM gtfs_stop st, bounds WHERE st.id = :stopId AND st.location && ST_Transform(bounds.geom, 4326)) SELECT ST_AsMVT(mvtgeom.*, 'stops') FROM mvtgeom", nativeQuery = true)
    byte[] getStop(@NonNull @Param("stopId") final String stopId, @NonNull @Param("z") final Integer z, @NonNull @Param("x") final Integer x, @NonNull @Param("y") final Integer y);

    @Query(value = "WITH bounds AS (SELECT ST_TileEnvelope(:z, :x, :y) AS geom), trip_lines AS (SELECT st.trip_id, ST_MakeLine(s.location ORDER BY st.stop_sequence) AS geom FROM gtfs_stop s JOIN gtfs_stop_time st ON s.id = st.stop_id GROUP BY st.trip_id), mvtgeom AS (SELECT ST_AsMVTGeom(ST_Transform(tl.geom, 3857), bounds.geom) AS geom, tl.trip_id FROM trip_lines tl, bounds WHERE tl.geom && ST_Transform(bounds.geom, 4326)) SELECT ST_AsMVT(mvtgeom.*, 'trip_polyline') FROM mvtgeom", nativeQuery = true)
    byte[] getTrips(@NonNull @Param("z") final Integer z, @NonNull @Param("x") final Integer x, @NonNull @Param("y") final Integer y);

    @Query(value = "WITH bounds AS (SELECT ST_TileEnvelope(:z, :x, :y) AS geom), trip_line AS (SELECT ST_MakeLine(s.location ORDER BY st.stop_sequence) AS geom FROM gtfs_stop s JOIN gtfs_stop_time st ON s.id = st.stop_id WHERE st.trip_id = :tripId), mvtgeom AS (SELECT ST_AsMVTGeom(ST_Transform(tl.geom, 3857), bounds.geom) AS geom FROM trip_line tl, bounds WHERE tl.geom && ST_Transform(bounds.geom, 4326)) SELECT ST_AsMVT(mvtgeom.*, 'trip_polyline') FROM mvtgeom", nativeQuery = true)
    byte[] getTrip(@NonNull @Param("tripId") final String tripId, @NonNull @Param("z") final Integer z, @NonNull @Param("x") final Integer x, @NonNull @Param("y") final Integer y);

    @Query(value = "WITH bounds AS (SELECT ST_TileEnvelope(:z, :x, :y) AS geom), trip_stops AS (SELECT s.id, s.code, s.url, s.parent_station, s.name, s.location, st.stop_sequence FROM gtfs_stop s JOIN gtfs_stop_time st ON s.id = st.stop_id WHERE st.trip_id = :tripId), trip_line AS (SELECT ST_MakeLine(ts.location ORDER BY ts.stop_sequence) AS geom FROM trip_stops ts), mvtgeom_line AS (SELECT ST_AsMVTGeom(ST_Transform(tl.geom, 3857), bounds.geom) AS geom FROM trip_line tl, bounds WHERE tl.geom && ST_Transform(bounds.geom, 4326)), mvtgeom_stops AS (SELECT ST_AsMVTGeom(ST_Transform(ts.location, 3857), bounds.geom) AS geom, ts.id, ts.code, ts.url, ts.parent_station, ts.name FROM trip_stops ts, bounds WHERE ts.location && ST_Transform(bounds.geom, 4326)) SELECT (SELECT ST_AsMVT(mvtgeom_line.*, 'trip_polyline') FROM mvtgeom_line) || (SELECT ST_AsMVT(mvtgeom_stops.*, 'stops') FROM mvtgeom_stops)", nativeQuery = true)
    byte[] getTripAndStops(@NonNull @Param("tripId") final String tripId, @NonNull @Param("z") final Integer z, @NonNull @Param("x") final Integer x, @NonNull @Param("y") final Integer y);

}
