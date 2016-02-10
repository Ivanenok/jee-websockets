package ru.ivanenok.messages.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import ru.ivanenok.messages.TransferMessage;
import ru.ivanenok.messages.TransferMessageBuilder;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 * Created by ivanenok on 2/10/16.
 */
public class ResponseEncoder implements Encoder.Text<TransferMessage> {
    @Override
    public String encode(TransferMessage object) throws EncodeException {
        try {
            return TransferMessageBuilder.buildRequest(object);
        } catch (JsonProcessingException e) {
            throw new EncodeException(object, "Promlem on message encoding", e);
        }
    }

    @Override
    public void init(EndpointConfig config) {

    }

    @Override
    public void destroy() {

    }
}
