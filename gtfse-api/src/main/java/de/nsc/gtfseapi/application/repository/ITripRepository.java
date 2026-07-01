package de.nsc.gtfseapi.application.repository;

import de.nsc.gtfseapi.model.TripEntity;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ITripRepository extends JpaRepository<TripEntity, String>, JpaSpecificationExecutor<TripEntity> {

    @NonNull
    @Override
    <S extends TripEntity> List<S> saveAll(@NonNull final Iterable<S> entities);

    @NonNull
    @Override
    Optional<TripEntity> findById(@NonNull final String id);

    @Query(value = "SELECT st.trip FROM StopTimeEntity st WHERE st.stop.id = :stopId")
    List<TripEntity> findByStopId(@NonNull @Param("stopId") String stopId);

    @Query(value = "SELECT tr FROM TripEntity tr WHERE tr.route.id = :routeId")
    List<TripEntity> findByRouteId(@NonNull @Param("routeId") String routeId);

}
