package com.example.reactivewebdemo;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.UUID;

@RestController
@RequestMapping("/foo")
public class FooController {

    @GetMapping(produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Foo> emitRandomFoo() {
        return Flux.interval(Duration.ofSeconds(1)).take(50)
                   .map(index -> new Foo(UUID.randomUUID().toString(), "foo " + index));
    }
}

