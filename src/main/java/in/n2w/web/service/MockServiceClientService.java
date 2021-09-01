package in.n2w.web.service;

import in.n2w.web.models.MockServiceResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;

/**
 * @author Karanbir Singh on 08/19/2021
 */
@Service
public class MockServiceClientService {

    @Autowired
    private WebClient mockServiceWebClient;

    @Value("${downstream.mock.path}")
    private String DOWNSTREAM_PATH;

    @CircuitBreaker(name = "mockService", fallbackMethod = "fallback")
    public Mono<MockServiceResponse> getMockServiceResponse() {
        return mockServiceWebClient.get()
                .uri(DOWNSTREAM_PATH)
                .retrieve()
                .bodyToMono(MockServiceResponse.class)
                .doOnError(ex -> {
                    throw new RuntimeException("the exception message is - "+ex.getMessage());
                });
    }

    public Mono<MockServiceResponse> fallback(Throwable ex) {
        //Arrays.stream(ex.getStackTrace()).forEach(System.out::println);
        System.out.println("---> "+ex.getMessage());
        MockServiceResponse mockServiceResponse = new MockServiceResponse();
        mockServiceResponse.setError(true);
        return Mono.just(mockServiceResponse);
    }

}
