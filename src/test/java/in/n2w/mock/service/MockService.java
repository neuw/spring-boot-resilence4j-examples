package in.n2w.mock.service;

import in.n2w.web.models.MockServiceResponse;
import spark.Request;
import spark.Response;

import java.util.UUID;

import static net.andreinc.mockneat.unit.user.Names.names;

/**
 * @author Karanbir Singh on 08/19/2021
 */
public class MockService {

    public static MockServiceResponse handle(Request request, Response response) throws Exception {
        MockServiceResponse mockServiceResponse = new MockServiceResponse();
        mockServiceResponse.setId(UUID.randomUUID().toString()).setName(names().full().get());

        response.type("application/json");

        System.out.println("Request -> " + request);
        System.out.println("Response Body -> " + mockServiceResponse);
        System.out.println("Response -> " + response);

        return mockServiceResponse;
    }

}
