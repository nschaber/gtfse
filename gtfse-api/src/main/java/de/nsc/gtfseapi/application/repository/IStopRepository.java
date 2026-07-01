package de.nsc.gtfseapi.application.repository;

import de.nsc.gtfseapi.model.StopEntity;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IStopRepository extends JpaRepository<StopEntity, String>, JpaSpecificationExecutor<StopEntity> {

    @NonNull
    @Override
    <S extends StopEntity> List<S> saveAll(@NonNull final Iterable<S> entities);

    @Query(value = "SELECT st.stop FROM StopTimeEntity st WHERE st.trip.id = :tripId ORDER BY st.stopSequence ASC")
    List<StopEntity> findByTripId(@NonNull @Param("tripId") String tripId);
}
