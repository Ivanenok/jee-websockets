package ru.ivanenok;

import org.junit.Test;
import ru.ivanenok.messages.request.LoginRequest;
import ru.ivanenok.messages.TransferMessageBuilder;
import ru.ivanenok.util.TimeService;

import java.time.*;
import java.util.TimeZone;

/**
 * Created by ivanenok on 2/10/16.
 */
public class TransferMessageBuilderTest {
    @Test
    public void testBuildRequest() throws Exception {
        String loginRequest = TransferMessageBuilder.buildRequest(new LoginRequest("test@test.com", "password"));
    }
}