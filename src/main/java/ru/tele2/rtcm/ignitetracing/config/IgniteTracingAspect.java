package ru.tele2.rtcm.ignitetracing.config;

import io.opentracing.Span;
import io.opentracing.Tracer;
import org.apache.ignite.IgniteCache;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.util.List;

@Aspect
public class IgniteTracingAspect {

    private static final String OPERATION_NAME = "cache-operation-%s-%s";

    private final Tracer tracer;
    private final List<String> excludedMethods;

    public IgniteTracingAspect(Tracer tracer, List<String> excludedMethods) {

        this.tracer = tracer;
        this.excludedMethods = excludedMethods;
    }

    @Around("execution(* org.apache.ignite.IgniteCache.*(..))")
    public Object traceCache(ProceedingJoinPoint pjp) throws Throwable {

        String method = pjp.getSignature().getName();
        if (excludedMethods.contains(method)) {
            return pjp.proceed();
        }
        String cacheName = ((IgniteCache) pjp.getTarget()).getName();

        Span span = tracer.buildSpan(String.format(OPERATION_NAME, cacheName, method)).start();
        span.setTag("cache.method", method);
        span.setTag("cache.name", cacheName);
        try {
            return pjp.proceed();
        } finally {
            span.finish();
        }
    }
}
