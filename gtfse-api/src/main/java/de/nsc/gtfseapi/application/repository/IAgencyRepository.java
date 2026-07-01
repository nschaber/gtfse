package de.nsc.gtfseapi.application.repository;

import de.nsc.gtfseapi.model.AgencyEntity;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAgencyRepository extends JpaRepository<AgencyEntity, String>, JpaSpecificationExecutor<AgencyEntity> {

    @NonNull
    @Override
    <S extends AgencyEntity> List<S> saveAll(@NonNull final Iterable<S> entities);

    @NonNull
    @Override
    Page<AgencyEntity> findAll(@Nullable final Specification<AgencyEntity> spec, @NonNull final Pageable pageable);

}
