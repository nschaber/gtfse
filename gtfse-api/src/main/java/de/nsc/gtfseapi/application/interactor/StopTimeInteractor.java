package de.nsc.gtfseapi.application.interactor;

import de.nsc.gtfseapi.application.repository.IStopTimeRepository;
import de.nsc.gtfseapi.model.StopTimeEntity;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StopTimeInteractor {

    private final IStopTimeRepository stopTimeRepository;

    public StopTimeInteractor(@NonNull final IStopTimeRepository stopTimeRepository) {
        this.stopTimeRepository = stopTimeRepository;
    }

    public List<StopTimeEntity> getAll() {
        return this.stopTimeRepository.findAll();
    }

    public StopTimeEntity getOne(@NonNull final String id) {
        final var optional = this.stopTimeRepository.findById(id);
        return optional.orElse(null);
    }

}
