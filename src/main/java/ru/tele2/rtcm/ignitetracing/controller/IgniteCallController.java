package ru.tele2.rtcm.ignitetracing.controller;

import io.opentracing.Tracer;
import org.apache.ignite.IgniteCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.tele2.rtcm.ignitetracing.dto.Content;

import java.math.BigDecimal;

import static ru.tele2.rtcm.ignitetracing.config.CacheNames.CONTENT_CACHE;

@RestController
public class IgniteCallController {

    @Qualifier(CONTENT_CACHE)
    @Autowired
    private IgniteCache<BigDecimal, Content> contentCache;

    @Autowired
    private Tracer tracer;

    @PostMapping("java/put/")
    public ResponseEntity<Void> putToCache(@RequestBody Content content) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/kek/")
    public ResponseEntity<String> kek() {

        contentCache.put(BigDecimal.ONE, new Content("keki", 14.88));
        contentCache.clear();
        contentCache.get(BigDecimal.ONE);
        contentCache.put(BigDecimal.ONE, new Content("keki", 14.88));
        contentCache.get(BigDecimal.ONE);

        return new ResponseEntity<>("kek", HttpStatus.OK);
    }


}
