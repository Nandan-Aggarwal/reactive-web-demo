package com.example.reactivewebdemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@WebFluxTest(controllers = FooController.class)
class FooControllerTest {

    @Autowired
    private WebTestClient webClient;

    @Test
    void testEmitFoo() {
        Flux<Foo> fooStream = webClient.get().uri("/foo")
                                       .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_STREAM_JSON_VALUE)
                                       .exchange()
                                       .expectStatus().isOk()
                                       .returnResult(Foo.class).getResponseBody();

        StepVerifier.create(fooStream).
                expectNextMatches(foo -> foo.getName().equalsIgnoreCase("foo 0"))
                    .expectNextMatches(foo -> foo.getName().equalsIgnoreCase("foo 1"))
                    .expectNextMatches(foo -> foo.getName().equalsIgnoreCase("foo 2"))
                    .thenCancel()
                    .verify();
    }
}