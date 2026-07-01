package de.nsc.gtfseapi.application.interactor;

import de.nsc.gtfseapi.application.repository.IRouteRepository;
import de.nsc.gtfseapi.application.repository.ITripRepository;
import de.nsc.gtfseapi.model.RouteEntity;
import de.nsc.gtfseapi.model.TripEntity;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouterInteractor {

    private final IRouteRepository routeRepository;
    private final ITripRepository tripRepository;

    public RouterInteractor(@NonNull final IRouteRepository routeRepository, @NonNull final ITripRepository tripRepository) {
        this.routeRepository = routeRepository;
        this.tripRepository = tripRepository;
    }

    public List<RouteEntity> getAll() {
        return this.routeRepository.findAll();
    }

    public RouteEntity getOne(@NonNull final String id) {
        final var optional = this.routeRepository.findById(id);
        return optional.orElse(null);
    }

    public List<TripEntity> getTrips(@NonNull final String id) {
        return this.tripRepository.findByRouteId(id);
    }

}
