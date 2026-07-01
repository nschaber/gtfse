package de.nsc.gtfseapi.application.repository;

import de.nsc.gtfseapi.model.RouteEntity;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRouteRepository extends JpaRepository<RouteEntity, String>, JpaSpecificationExecutor<RouteEntity> {

    @NonNull
    @Override
    <S extends RouteEntity> List<S> saveAll(@NonNull final Iterable<S> entities);
}
