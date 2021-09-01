package in.n2w.web.controller;

import in.n2w.web.models.MockServiceResponse;
import in.n2w.web.service.MockServiceClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author Karanbir Singh on 08/19/2021
 */
@RestController
public class DefaultController {

    @Autowired
    private MockServiceClientService service;

    @GetMapping("/v1/mock/upstream")
    public Mono<MockServiceResponse> getResponse() {
        return service.getMockServiceResponse();
    }

}
