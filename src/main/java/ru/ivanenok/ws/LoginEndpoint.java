package ru.ivanenok.ws;

import ru.ivanenok.AuthTokenManager;
import ru.ivanenok.CustomerNotFound;
import ru.ivanenok.domain.AuthToken;
import ru.ivanenok.messages.request.RequestDecoder;
import ru.ivanenok.messages.response.ResponseBuilder;
import ru.ivanenok.messages.response.ResponseEncoder;
import ru.ivanenok.messages.TransferMessage;

import javax.inject.Inject;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;

/**
 * Created by ivanenok on 2/10/16.
 */

@ServerEndpoint(value = "/login", decoders = RequestDecoder.class, encoders = ResponseEncoder.class)
public class LoginEndpoint {

    @Inject
    private AuthTokenManager authTokenManager;

    @OnOpen
    public void onOpenSession(Session session) {

    }

    @OnMessage
    public void handleRequest(Session session, final TransferMessage request) throws IOException, EncodeException {
        Object response;

        Map<String, String> data = getData(request);
        String email = data.get("email");
        String password = data.get("password");

        try {
            AuthToken authToken = authTokenManager.authenticate(email, password);
            response = ResponseBuilder.buildSuccessAuth(request, authToken.getToken(), authToken.getExpired());
        } catch (CustomerNotFound customerNotFound) {
            response = ResponseBuilder.buildError(request, "customer.notFound", "Customer not found");
        }

        session.getBasicRemote().sendObject(response);
    }

    @SuppressWarnings("unchecked")
    private Map<String, String> getData(TransferMessage request) {
        return (Map<String, String>) request.getData();
    }

    @OnError
    public void onError(Throwable t) {
        t.printStackTrace();
    }

    @OnClose
    public void onClose(CloseReason reason) {

    }
}
