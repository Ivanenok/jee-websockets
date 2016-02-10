package ru.ivanenok;

import javax.websocket.*;
import java.util.concurrent.CountDownLatch;

/**
 * Created by ivanenok on 2/10/16.
 */
@ClientEndpoint
public class TestClientEndpoint {

    public static CountDownLatch barrier = new CountDownLatch(1);
    public static String response;


    @OnOpen
    public void onOpenSession(Session session){

    }

    @OnMessage
    public void handleMessage(Session session, String text) {
        response = text;
        barrier.countDown();
    }

    @OnClose
    public void onClose(Session session){

    }

}
