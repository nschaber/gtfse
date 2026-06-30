package de.nsc.gtfseapi.adapter.config.json;

import org.geolatte.geom.json.jackson3.GeolatteGeomModule;
import org.springframework.boot.jackson.autoconfigure.JsonMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Configuration
public class JacksonConfig {

    @Bean
    public JsonMapperBuilderCustomizer jsonCustomizer() {
        return builder -> builder
                .changeDefaultPropertyInclusion(include -> include.withValueInclusion(NON_NULL))
                .addModules(new GeolatteGeomModule());
    }

}