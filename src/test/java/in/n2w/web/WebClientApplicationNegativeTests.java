package in.n2w.web;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

@Slf4j
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebClientApplicationNegativeTests {

    @Autowired
    private WebTestClient webTestClient;

    @Nested
    @DisplayName("Negative Tests")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class NegativeTestCases {

        @Test
        public void testUpstream() {
            webTestClient
                    .get().uri("/v1/mock/upstream")
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isOk();
        }

        @Test
        public void testHealth() {
            webTestClient
                    .get().uri("/actuator/health")
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().is5xxServerError();
        }

    }

    @Nested
    @DisplayName("Circuit Breaker Tests")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class NegativeTestCasesCircuitOpen {

        @Test
        @Order(1)
        public void testUpstreamFailureOne() {
            webTestClient
                    .get().uri("/v1/mock/upstream")
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isOk();
        }

        @Test
        @Order(2)
        public void testUpstreamFailureTwo() {
            webTestClient
                    .get().uri("/v1/mock/upstream")
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isOk();
        }

        @Test
        @Order(3)
        public void testUpstreamFailureThree() {
            webTestClient
                    .get().uri("/v1/mock/upstream")
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isOk();
        }

        @Test
        @Order(4)
        public void testHealthFailureFour() {
            webTestClient
                    .get().uri("/actuator/health")
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().is5xxServerError();
        }

        @Test
        @Order(5)
        public void testHealth() {
            webTestClient
                    .get().uri("/actuator/health")
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus()
                    .is5xxServerError()
                    .expectBody()
                    .jsonPath("$.components.circuitBreakers.status").isEqualTo("DOWN");
        }

    }


}
