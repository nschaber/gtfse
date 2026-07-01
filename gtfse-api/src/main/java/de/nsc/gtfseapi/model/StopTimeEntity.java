package de.nsc.gtfseapi.model;

import jakarta.persistence.*;
import lombok.*;

@Table(
        name = "gtfs_stop_time",
        indexes = {
                @Index(name = "idx_gtfs_stop_time_trip_id", columnList = "trip_id"),
                @Index(name = "idx_gtfs_stop_time_stop_id", columnList = "stop_id"),
                @Index(name = "idx_gtfs_stop_time_stop_sequence", columnList = "stop_sequence")
        }
)
@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StopTimeEntity {

    @Id
    private String id;
    private String stopHeadsign;
    private int arrivalTime;
    private int departureTime;
    private int stopSequence;
    private int pickupType;
    private int dropOffType;
    private double shapeDistTraveled;

    @ManyToOne
    @JoinColumn(name = "trip_id")
    private TripEntity trip;

    @ManyToOne
    @JoinColumn(name = "stop_id")
    private StopEntity stop;

}