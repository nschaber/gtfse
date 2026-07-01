package de.nsc.gtfseapi.application.repository;

import de.nsc.gtfseapi.model.StopTimeEntity;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IStopTimeRepository extends JpaRepository<StopTimeEntity, String>, JpaSpecificationExecutor<StopTimeEntity> {

    @NonNull
    @Override
    <S extends StopTimeEntity> List<S> saveAll(@NonNull final Iterable<S> entities);

    @Query(value = "SELECT st FROM StopTimeEntity st WHERE st.trip.id = :tripId ORDER BY st.stopSequence ASC")
    List<StopTimeEntity> findByTripId(@NonNull @Param("tripId") String tripId);

    @Query(value = "SELECT st FROM StopTimeEntity st WHERE st.stop.id = :stopId ORDER BY st.stopSequence ASC")
    List<StopTimeEntity> findByStopId(@NonNull @Param("stopId") String stopId);

}
