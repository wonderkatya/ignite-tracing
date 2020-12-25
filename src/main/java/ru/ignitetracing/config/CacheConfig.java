package ru.ignitetracing.config;

import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.affinity.rendezvous.RendezvousAffinityFunction;
import org.apache.ignite.configuration.CacheConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.ignitetracing.dto.Content;

import java.math.BigDecimal;

@Configuration
public class CacheConfig {

    @Bean
    public CacheConfiguration<BigDecimal, Content> contentCacheConfiguration() {
        CacheConfiguration<BigDecimal, Content> cacheConfiguration = new CacheConfiguration<>();
        cacheConfiguration.setCacheMode(CacheMode.PARTITIONED);
        cacheConfiguration.setName(CacheNames.CONTENT_CACHE);
        cacheConfiguration.setAffinity(new RendezvousAffinityFunction(false, 1));
        cacheConfiguration.setBackups(1);
        cacheConfiguration.setAtomicityMode(CacheAtomicityMode.ATOMIC);

        return cacheConfiguration;
    }
}
