package de.nsc.gtfseapi.application.interactor;

import de.nsc.gtfseapi.application.repository.IStopRepository;
import de.nsc.gtfseapi.application.repository.IStopTimeRepository;
import de.nsc.gtfseapi.application.repository.ITileRepository;
import de.nsc.gtfseapi.application.repository.ITripRepository;
import de.nsc.gtfseapi.model.StopEntity;
import de.nsc.gtfseapi.model.StopTimeEntity;
import de.nsc.gtfseapi.model.TripEntity;
import org.jspecify.annotations.NonNull;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TripInteractor {

    private final ITripRepository tripRepository;
    private final IStopTimeRepository stopTimeRepository;
    private final IStopRepository stopRepository;
    private final ITileRepository tileRepository;

    public TripInteractor(@NonNull final ITripRepository tripRepository, @NonNull final IStopTimeRepository stopTimeRepository, @NonNull final IStopRepository stopRepository, @NonNull final ITileRepository tileRepository) {
        this.tripRepository = tripRepository;
        this.stopTimeRepository = stopTimeRepository;
        this.stopRepository = stopRepository;
        this.tileRepository = tileRepository;
    }

    public List<TripEntity> getAll() {
        return this.tripRepository.findAll();
    }

    public TripEntity getOne(@NonNull final String id) {
        final var optional = this.tripRepository.findById(id);
        return optional.orElse(null);
    }

    public List<StopTimeEntity> getStopTimes(@NonNull final String id) {
        return this.stopTimeRepository.findByTripId(id);
    }

    public List<StopEntity> getStops(@NonNull final String id) {
        return this.stopRepository.findByTripId(id);
    }

    @Cacheable(cacheNames = "trip_tiles", value = "trip_tiles", unless = "#result == null")
    public byte[] getTrip(@NonNull final String tripId, @NonNull final Integer z, @NonNull final Integer x, @NonNull final Integer y) {
        return this.tileRepository.getTrip(tripId, z, x, y);
    }

    @Cacheable(cacheNames = "trips_tiles", value = "trips_tiles", unless = "#result == null")
    public byte[] getTrips(@NonNull final Integer z, @NonNull final Integer x, @NonNull final Integer y) {
        return this.tileRepository.getTrips(z, x, y);
    }

    @Cacheable(cacheNames = "trips_stops_tiles", value = "trips_stops_tiles", unless = "#result == null")
    public byte[] getTripAndStops(@NonNull final String tripId, @NonNull final Integer z, @NonNull final Integer x, @NonNull final Integer y) {
        return this.tileRepository.getTripAndStops(tripId, z, x, y);
    }

}
