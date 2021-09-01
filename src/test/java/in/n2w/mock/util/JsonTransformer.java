package in.n2w.mock.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import spark.ResponseTransformer;

/**
 * @author Karanbir Singh on 08/19/2021
 */
public class JsonTransformer implements ResponseTransformer {

	public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	public JsonTransformer() {
	}

	@Override
	public String render(Object model) {
		try {
			return OBJECT_MAPPER.writeValueAsString(model);
		} catch (JsonProcessingException e) {
			return null;
		}
	}
}