package ru.ivanenok.messages.response;

import ru.ivanenok.messages.TransferMessage;
import ru.ivanenok.messages.response.ErrorResponse;
import ru.ivanenok.messages.response.SuccessLoginResponse;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * Created by ivanenok on 2/10/16.
 */
public class ResponseBuilder {
    public static Object buildError(TransferMessage request, String error, String descripton) {
        return new ErrorResponse(request.getSequenceId(), error, descripton);
    }

    public static Object buildSuccessAuth(TransferMessage request, String token, LocalDateTime expired) {
        return new SuccessLoginResponse(request.getSequenceId(), token, expired);
    }
}
