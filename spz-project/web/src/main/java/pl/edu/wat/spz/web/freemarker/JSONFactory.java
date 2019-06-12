package pl.edu.wat.spz.web.freemarker;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONFactory {

    private static final ObjectMapper mapper = new ObjectMapper();

    public String stringify(Object object) throws JsonProcessingException {
        return mapper.writeValueAsString(object);
    }
}