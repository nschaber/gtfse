package de.nsc.gtfseapi.adapter.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "gtfs.import")
public class ImportConfig {

    private String zipPath;
    private Boolean enabled;

}