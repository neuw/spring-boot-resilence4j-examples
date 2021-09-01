package in.n2w.web.config;

import in.n2w.web.models.MockServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.ReactiveHealthIndicator;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component("mockServiceHealth")
public class ServiceHealth implements ReactiveHealthIndicator {

    @Autowired
    private WebClient mockServiceWebClient;

    @Value("${downstream.mock.url}")
    private String DOWNSTREAM_MOCK_URL;
 
    @Override
    public Mono<Health> health() {
        return mockServiceWebClient.get()
                .uri(DOWNSTREAM_MOCK_URL).retrieve().bodyToMono(MockServiceResponse.class)
                .map(s -> new Health.Builder().up().build())
                .onErrorResume(ex -> Mono.just(new Health.Builder().down(ex).build()))
                .log();
    }
}