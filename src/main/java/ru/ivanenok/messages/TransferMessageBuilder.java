package ru.ivanenok.messages;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by ivanenok on 2/10/16.
 */
public class TransferMessageBuilder {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static String buildRequest(TransferMessage data) throws JsonProcessingException {
        return objectMapper.writeValueAsString(data);
    }

}
