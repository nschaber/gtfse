package de.nsc.gtfseapi.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "gtfs_trip")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TripEntity {

    @Id
    private String id;
    private String serviceId;
    private String blockId;
    private String shapeId;
    private String tripHeadsign;
    private String tripShortName;
    private int directionId;
    private int wheelchairAccessible;
    private int bikesAllowed;

    @ManyToOne
    @JoinColumn(name = "route_id")
    private RouteEntity route;

}