package ru.ignitetracing.controller;

import io.opentracing.Tracer;
import org.apache.ignite.IgniteCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ignitetracing.config.CacheNames;
import ru.ignitetracing.dto.Content;

import java.math.BigDecimal;

@RestController
public class IgniteCallController {

    @Qualifier(CacheNames.CONTENT_CACHE)
    @Autowired
    private IgniteCache<BigDecimal, Content> contentCache;

    @Autowired
    private Tracer tracer;

    @GetMapping("/cache-operation/")
    public ResponseEntity<String> cacheOperation() {

        contentCache.put(BigDecimal.ONE, new Content("keki", 14.88));
        contentCache.clear();
        contentCache.get(BigDecimal.ONE);
        contentCache.put(BigDecimal.ONE, new Content("keki", 14.88));
        contentCache.get(BigDecimal.ONE);

        return new ResponseEntity<>("cache operation performed", HttpStatus.OK);
    }


}
