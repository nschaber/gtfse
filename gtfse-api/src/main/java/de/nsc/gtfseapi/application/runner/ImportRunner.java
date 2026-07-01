package de.nsc.gtfseapi.application.runner;

import de.nsc.gtfseapi.adapter.config.ImportConfig;
import de.nsc.gtfseapi.application.repository.*;
import de.nsc.gtfseapi.mapper.GtfsMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.onebusaway.gtfs.model.*;
import org.onebusaway.gtfs.serialization.GtfsReader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ImportRunner implements CommandLineRunner {

    private final ImportConfig importConfig;
    private final GtfsMapper gtfsMapper;
    private final IAgencyRepository agencyRepository;
    private final IRouteRepository routeRepository;
    private final IStopRepository stopRepository;
    private final ITripRepository tripRepository;
    private final IStopTimeRepository stopTimeRepository;

    @Override
    public void run(final String @NonNull ... args) throws Exception {
        if (importConfig.getEnabled()) {

            log.info("Importing GTFS data from {}", importConfig.getZipPath());
            final var gtfsZipFile = new File(importConfig.getZipPath());
            if (!gtfsZipFile.exists()) {
                throw new RuntimeException("GTFS zip file does not exist: " + gtfsZipFile.getAbsolutePath());
            }

            final var agencies = new ArrayList<de.nsc.gtfseapi.model.AgencyEntity>();
            final var routes = new ArrayList<de.nsc.gtfseapi.model.RouteEntity>();
            final var stops = new ArrayList<de.nsc.gtfseapi.model.StopEntity>();
            final var trips = new ArrayList<de.nsc.gtfseapi.model.TripEntity>();
            final var stopTimes = new ArrayList<de.nsc.gtfseapi.model.StopTimeEntity>();

            final var reader = new GtfsReader();
            reader.setInputLocation(gtfsZipFile);
            reader.setEntityClasses(List.of(Agency.class, Route.class, Stop.class, Trip.class, StopTime.class));
            reader.addEntityHandler((final var object) -> {
                if (object instanceof Agency gtfsAgency) {
                    final var agency = gtfsMapper.toEntity(gtfsAgency);
                    agencies.add(agency);
                } else if (object instanceof Route gtfsRoute) {
                    final var route = gtfsMapper.toEntity(gtfsRoute);
                    routes.add(route);
                } else if (object instanceof Stop gtfsStop) {
                    final var stop = gtfsMapper.toEntity(gtfsStop);
                    stops.add(stop);
                } else if (object instanceof Trip gtfsTrip) {
                    final var trip = gtfsMapper.toEntity(gtfsTrip);
                    trips.add(trip);
                } else if (object instanceof StopTime gtfsStopTime) {
                    final var stopTime = gtfsMapper.toEntity(gtfsStopTime);
                    stopTimes.add(stopTime);
                }
            });

            reader.run();

            log.info("Agencies: {}", agencies.size());
            if (!agencies.isEmpty()) agencyRepository.saveAllAndFlush(agencies);
            agencyRepository.flush();
            log.info("Agencies saved");

            log.info("Routes: {}", routes.size());
            if (!routes.isEmpty()) routeRepository.saveAllAndFlush(routes);
            routeRepository.flush();
            log.info("Routes saved");

            log.info("Stops: {}", stops.size());
            if (!stops.isEmpty()) stopRepository.saveAllAndFlush(stops);
            stopRepository.flush();
            log.info("Stops saved");

            log.info("Trips: {}", trips.size());
            if (!trips.isEmpty()) tripRepository.saveAllAndFlush(trips);
            tripRepository.flush();
            log.info("Trips saved");

            log.info("StopTimes: {}", stopTimes.size());
            if (!stopTimes.isEmpty()) stopTimeRepository.saveAllAndFlush(stopTimes);
            stopTimeRepository.flush();
            log.info("StopTimes saved");
            log.info("Import finished");

        }
    }


}