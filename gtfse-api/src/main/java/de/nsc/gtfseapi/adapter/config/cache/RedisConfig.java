package de.nsc.gtfseapi.adapter.config.cache;

import lombok.AllArgsConstructor;
import org.springframework.boot.cache.autoconfigure.RedisCacheManagerBuilderCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import tools.jackson.databind.jsontype.PolymorphicTypeValidator;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static java.time.Duration.ofMinutes;
import static org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair.fromSerializer;
import static org.springframework.data.redis.serializer.RedisSerializer.byteArray;
import static tools.jackson.databind.DefaultTyping.NON_FINAL;
import static tools.jackson.databind.DeserializationFeature.FAIL_ON_INVALID_SUBTYPE;

@EnableCaching
@Configuration
@AllArgsConstructor
public class RedisConfig {

    private ObjectMapper redisObjectMapper() {
        PolymorphicTypeValidator ptv = BasicPolymorphicTypeValidator.builder().allowIfBaseType(Object.class).build();
        return JsonMapper.builder()
                .activateDefaultTyping(ptv, NON_FINAL, PROPERTY)
                .configure(FAIL_ON_INVALID_SUBTYPE, false)
                .build();
    }

    @Bean
    @Profile(value = {"prod", "dev", "debug"})
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        GenericJacksonJsonRedisSerializer jsonSerializer = new GenericJacksonJsonRedisSerializer(redisObjectMapper());
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(jsonSerializer);
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(jsonSerializer);
        return template;
    }

    @Bean
    @Profile(value = {"prod", "dev", "debug"})
    public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
        return (builder) -> builder
                .withCacheConfiguration("trip_tiles",
                        RedisCacheConfiguration.defaultCacheConfig()
                                .entryTtl(ofMinutes(30))
                                .serializeValuesWith(fromSerializer(byteArray()))
                )
                .withCacheConfiguration("trips_tiles",
                        RedisCacheConfiguration.defaultCacheConfig()
                                .entryTtl(ofMinutes(30))
                                .serializeValuesWith(fromSerializer(byteArray()))
                )
                .withCacheConfiguration("trips_stops_tiles",
                        RedisCacheConfiguration.defaultCacheConfig()
                                .entryTtl(ofMinutes(30))
                                .serializeValuesWith(fromSerializer(byteArray()))
                );
    }

    @Bean
    @Profile(value = {"prod", "dev", "debug"})
    public RedisCacheConfiguration cacheConfiguration() {
        return RedisCacheConfiguration
                .defaultCacheConfig(Thread.currentThread().getContextClassLoader())
                .entryTtl(ofMinutes(30))
                .serializeValuesWith(fromSerializer(new GenericJacksonJsonRedisSerializer(redisObjectMapper())));
    }

}
