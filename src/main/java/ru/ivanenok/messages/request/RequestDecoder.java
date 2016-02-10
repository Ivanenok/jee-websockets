package ru.ivanenok.messages.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.ivanenok.messages.TransferMessage;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import java.io.IOException;

/**
 * Created by ivanenok on 2/10/16.
 */
public class RequestDecoder implements Decoder.Text<TransferMessage> {
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T readTransferMessage(String text, Class<T> clazz) throws DecodeException {
        try {
            return objectMapper.readValue(text, clazz);
        } catch (IOException e) {
            throw new DecodeException(text, "Problem on request encoding", e);
        }
    }

    @Override
    public TransferMessage decode(String s) throws DecodeException {
        return readTransferMessage(s, TransferMessage.class);
    }

    @Override
    public boolean willDecode(String s) {
        return true;
    }

    @Override
    public void init(EndpointConfig config) {

    }

    @Override
    public void destroy() {

    }
}
