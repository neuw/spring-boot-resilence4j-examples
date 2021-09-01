package in.n2w.web.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author Karanbir Singh on 08/19/2021
 */
@Getter
@Setter
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MockServiceResponse {

    private String id;

    private String name;

    private boolean isError;

    @Override
    public String toString() {
        return "MockServiceResponse{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
