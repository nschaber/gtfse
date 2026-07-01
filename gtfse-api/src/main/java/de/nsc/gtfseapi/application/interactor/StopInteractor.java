package de.nsc.gtfseapi.application.interactor;

import de.nsc.gtfseapi.application.repository.IStopRepository;
import de.nsc.gtfseapi.application.repository.IStopTimeRepository;
import de.nsc.gtfseapi.application.repository.ITileRepository;
import de.nsc.gtfseapi.application.repository.ITripRepository;
import de.nsc.gtfseapi.model.StopEntity;
import de.nsc.gtfseapi.model.StopTimeEntity;
import de.nsc.gtfseapi.model.TripEntity;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StopInteractor {

    private final IStopRepository stopRepository;
    private final IStopTimeRepository stopTimeRepository;
    private final ITripRepository tripRepository;
    private final ITileRepository tileRepository;

    public StopInteractor(@NonNull final IStopRepository stopRepository, @NonNull final IStopTimeRepository stopTimeRepository, @NonNull final ITripRepository tripRepository, @NonNull final ITileRepository tileRepository) {
        this.stopRepository = stopRepository;
        this.stopTimeRepository = stopTimeRepository;
        this.tripRepository = tripRepository;
        this.tileRepository = tileRepository;
    }

    public List<StopEntity> getAll() {
        return this.stopRepository.findAll();
    }

    public StopEntity getOne(@NonNull final String id) {
        final var optional = this.stopRepository.findById(id);
        return optional.orElse(null);
    }

    public List<StopTimeEntity> getStopTimes(@NonNull final String id) {
        return this.stopTimeRepository.findByStopId(id);
    }

    public List<TripEntity> getTrips(@NonNull final String id) {
        return this.tripRepository.findByStopId(id);
    }

    public byte[] getStop(@NonNull final String stopId, @NonNull final Integer z, @NonNull final Integer x, @NonNull final Integer y) {
        return this.tileRepository.getStop(stopId, z, x, y);
    }

    public byte[] getStops(@NonNull final Integer z, @NonNull final Integer x, @NonNull final Integer y) {
        return this.tileRepository.getStops(z, x, y);
    }

}
