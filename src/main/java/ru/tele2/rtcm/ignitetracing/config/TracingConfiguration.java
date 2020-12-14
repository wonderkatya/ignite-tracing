package ru.tele2.rtcm.ignitetracing.config;

import io.opentracing.Tracer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.jaegertracing.Configuration.*;

import java.util.Collections;

import static io.jaegertracing.Configuration.fromEnv;

@Configuration
public class TracingConfiguration {

    @Bean
    public Tracer tracer() {
        var samplerConfig = new SamplerConfiguration()
                .withType("const")
                .withParam(1);
        return fromEnv("ignite-tracing")
                .withSampler(samplerConfig)
                .withReporter(ReporterConfiguration.fromEnv().withLogSpans(true))
                .getTracer();
    }

    @Bean
    public IgniteTracingAspect tracedAspect(Tracer tracer) {
        return new IgniteTracingAspect(tracer, Collections.emptyList());
    }
}
