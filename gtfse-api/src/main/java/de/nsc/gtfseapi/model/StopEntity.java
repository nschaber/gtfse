package de.nsc.gtfseapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.geolatte.geom.G2D;
import org.geolatte.geom.Point;

@Entity
@Table(name = "gtfs_stop")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StopEntity {

    @Id
    private String id;
    private String code;
    private String name;
    private String zoneId;
    private String url;
    private int locationType;
    private String parentStation;
    private String timezone;
    private int wheelchairBoarding;
    private Point<G2D> location;

}