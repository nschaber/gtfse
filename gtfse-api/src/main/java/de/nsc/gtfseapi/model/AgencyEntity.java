package de.nsc.gtfseapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "gtfs_agency")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgencyEntity {

    @Id
    private String id;
    private String name;
    private String url;
    private String timezone;
    private String language;
    private String phone;
    private String fareUrl;
    private String email;

}