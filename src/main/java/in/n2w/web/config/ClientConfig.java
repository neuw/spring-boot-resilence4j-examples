package in.n2w.web.config;

import io.netty.channel.ChannelOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.CompositeReactiveHealthContributor;
import org.springframework.boot.actuate.health.ReactiveHealthContributor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.Map;

/**
 * @author Karanbir Singh on 08/19/2021
 */
@Configuration
public class ClientConfig {

    @Autowired
    private ServiceHealth mockServiceHealth;

    @Bean
    public WebClient mockServiceWebClient(@Value("${downstream.mock.base}") final String DOWNSTREAM_BASE_URL) {
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 500)
                .responseTimeout(Duration.ofMillis(500));
        return WebClient.builder()
                .baseUrl(DOWNSTREAM_BASE_URL)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .defaultHeader("x-client", "web-client-rnd") // just time pass nothing much here
                .build();
    }

    @Bean
    public ReactiveHealthContributor reactiveHealthContributor() {
        return CompositeReactiveHealthContributor.fromMap(Map.of("mockService", mockServiceHealth));
    }

}
