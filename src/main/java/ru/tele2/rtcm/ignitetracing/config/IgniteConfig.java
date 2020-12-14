package ru.tele2.rtcm.ignitetracing.config;

import io.opentracing.Tracer;
import lombok.RequiredArgsConstructor;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.multicast.TcpDiscoveryMulticastIpFinder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tele2.rtcm.ignitetracing.dto.Content;

import java.math.BigDecimal;
import java.util.Collections;

import static ru.tele2.rtcm.ignitetracing.config.CacheNames.CONTENT_CACHE;

@Configuration
@RequiredArgsConstructor
public class IgniteConfig {

    private final CacheConfig cacheConfig;
    private final Tracer tracer;

    @Bean
    public Ignite ignite() {

        var config = new IgniteConfiguration();
        config.setClientMode(true);

        TcpDiscoverySpi tcpDiscoverySpi = new TcpDiscoverySpi();
        tcpDiscoverySpi.setLocalPort(47501);

        TcpDiscoveryMulticastIpFinder ipFinder = new TcpDiscoveryMulticastIpFinder();
        ipFinder.setAddresses(Collections.singletonList("127.0.0.1:47500..47509"));

        tcpDiscoverySpi.setIpFinder(ipFinder);
        config.setDiscoverySpi(tcpDiscoverySpi);

        return Ignition.start(config);

    }

    @Bean(CONTENT_CACHE)
    public IgniteCache<BigDecimal, Content> contactCache(Ignite ignite) {
        return ignite.getOrCreateCache(cacheConfig.contentCacheConfiguration());
    }
}
