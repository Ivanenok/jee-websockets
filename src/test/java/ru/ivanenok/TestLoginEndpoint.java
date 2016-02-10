package ru.ivanenok;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import ru.ivanenok.messages.TransferMessage;
import ru.ivanenok.messages.TransferMessageBuilder;
import ru.ivanenok.messages.request.LoginRequest;
import ru.ivanenok.messages.request.RequestDecoder;
import ru.ivanenok.messages.response.ResponseType;

import javax.inject.Inject;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * Created by ivanenok on 2/10/16.
 */

@RunWith(Arquillian.class)
public class TestLoginEndpoint {

    @ArquillianResource
    URI base;

    @Inject
    AuthTokenManager authTokenManager;

    /*
     * Arquillian specific method for creating a file which can be deployed
     * while executing the test.
     */
    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                .addPackage("ru.ivanenok")
                .addPackage("ru.ivanenok.util")
                .addPackage("ru.ivanenok.ws")
                .addPackage("ru.ivanenok.domain")
                .addPackage("ru.ivanenok.messages")
                .addPackage("ru.ivanenok.messages.request")
                .addPackage("ru.ivanenok.messages.response")
                .addAsResource("META-INF/persistence.xml")
                .addAsResource("META-INF/load.sql");
    }

    @Test
    public void executeTests() throws Exception {
        successLogin();
        unsuccessLogin();
        reLogin();
    }

    public void successLogin() throws Exception {
        System.out.println("-----------------------> login success start");

        LoginRequest requestObj = new LoginRequest("fpi@bk.ru", "123123");
        String request = TransferMessageBuilder.buildRequest(requestObj);

        TestClientEndpoint.barrier = new CountDownLatch(1);
        TestClientEndpoint.response = null;

        Session session = connectToServer(TestClientEndpoint.class);
        assertNotNull(session);
        System.out.println("request: " + request);
        session.getBasicRemote().sendText(request);
        TestClientEndpoint.barrier.await(3, TimeUnit.SECONDS);
        System.out.println("response: " + TestClientEndpoint.response);
        TransferMessage responseObj = RequestDecoder.readTransferMessage(TestClientEndpoint.response, TransferMessage.class);
        assertEquals(responseObj.getSequenceId(), requestObj.getSequenceId());
        assertEquals(responseObj.getType(), ResponseType.CUSTOMER_API_TOKEN.toString());
        System.out.println("-----------------------> login success end");

    }

    public void unsuccessLogin() throws Exception {
        System.out.println("-----------------------> login failed start");

        LoginRequest requestObj = new LoginRequest("123@gmail.com", "newPassword");
        String request = TransferMessageBuilder.buildRequest(requestObj);

        TestClientEndpoint.barrier = new CountDownLatch(1);
        TestClientEndpoint.response = null;

        Session session = connectToServer(TestClientEndpoint.class);
        assertNotNull(session);
        System.out.println("request: " + request);
        session.getBasicRemote().sendText(request);
        TestClientEndpoint.barrier.await(3, TimeUnit.SECONDS);
        System.out.println("response: " + TestClientEndpoint.response);
        TransferMessage responseObj = RequestDecoder.readTransferMessage(TestClientEndpoint.response, TransferMessage.class);
        assertEquals(responseObj.getSequenceId(), requestObj.getSequenceId());
        assertEquals(responseObj.getType(), ResponseType.CUSTOMER_ERROR.toString());
        System.out.println("-----------------------> login failed end");
    }


    public void reLogin() throws Exception {
        System.out.println("-----------------------> relogin start");
        LoginRequest requestObj = new LoginRequest("fpi@bk.ru", "123123");
        String request = TransferMessageBuilder.buildRequest(requestObj);

        TestClientEndpoint.barrier = new CountDownLatch(1);
        TestClientEndpoint.response = null;

        Session session = connectToServer(TestClientEndpoint.class);
        assertNotNull(session);

        System.out.println("request: " + request);
        session.getBasicRemote().sendText(request);
        TestClientEndpoint.barrier.await(3, TimeUnit.SECONDS);
        System.out.println("response: " + TestClientEndpoint.response);
        TransferMessage responseObj = RequestDecoder.readTransferMessage(TestClientEndpoint.response, TransferMessage.class);
        assertEquals(responseObj.getSequenceId(), requestObj.getSequenceId());
        assertEquals(responseObj.getType(), ResponseType.CUSTOMER_API_TOKEN.toString());

        TestClientEndpoint.barrier = new CountDownLatch(1);
        TestClientEndpoint.response = null;

        requestObj = new LoginRequest("fpi@bk.ru", "123123");
        request = TransferMessageBuilder.buildRequest(requestObj);
        System.out.println("request: " + request);
        session.getBasicRemote().sendText(request);
        TestClientEndpoint.barrier.await(3, TimeUnit.SECONDS);
        System.out.println("response: " + TestClientEndpoint.response);
        TransferMessage responseObj2 = RequestDecoder.readTransferMessage(TestClientEndpoint.response, TransferMessage.class);
        assertEquals(responseObj2.getSequenceId(), requestObj.getSequenceId());
        assertEquals(responseObj2.getType(), ResponseType.CUSTOMER_API_TOKEN.toString());
        assertNotEquals(responseObj.getDataObject().get("api_token"), responseObj2.getDataObject().get("api_token"));
        System.out.println("-----------------------> relogin end");
    }


    /**
     * Method used to supply connection to the server by passing the naming of
     * the websocket endpoint
     *
     * @param endpoint
     * @return
     * @throws DeploymentException
     * @throws IOException
     * @throws URISyntaxException
     */
    public Session connectToServer(Class<?> endpoint) throws DeploymentException, IOException, URISyntaxException {
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        URI uri = new URI("ws://"
                + base.getHost()
                + ":"
                + base.getPort()
                + base.getPath()
                + "login");
        return container.connectToServer(endpoint, uri);
    }
}