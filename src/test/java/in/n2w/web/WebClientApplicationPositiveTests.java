package in.n2w.web;

import in.n2w.mock.server.MockServer;
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
public class WebClientApplicationPositiveTests {

    @Autowired
    private WebTestClient webTestClient;

    @Nested
    @DisplayName("Positive Tests")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class PositiveTestCases {

        @BeforeAll
        public void init() {
            log.info("downstream mock-server starting");
            MockServer.start();
            log.info("downstream mock-server started");
        }

        @Test
        public void testUpstream() {
            log.info("upstream test with mock-server started");
            webTestClient
                    .get().uri("/v1/mock/upstream")
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isOk().expectBody()
                    .jsonPath("$.isError").doesNotExist();
            log.info("upstream test with mock-server started done");
        }

        @Test
        public void testHealth() {
            log.info("upstream health test with mock-server started");
            webTestClient
                    .get().uri("/actuator/health")
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isOk();
            log.info("upstream health test with mock-server started done");
        }

        @AfterAll()
        public void stopMockDownstream() {
            log.info("downstream mock-server stopping");
            MockServer.stop();
            log.info("downstream mock-server stopped");
        }
    }

}
