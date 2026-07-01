package de.nsc.gtfseapi.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "gtfs_route")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RouteEntity {

    @Id
    private String id;
    private String shortName;
    private String longName;
    private int type;
    private String url;
    private String color;
    private String textColor;

    @ManyToOne
    @JoinColumn(name = "agency_id")
    private AgencyEntity agency;

}