package ru.ivanenok.messages.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.ivanenok.messages.TransferMessage;
import ru.ivanenok.messages.response.ResponseType;

/**
 * Created by ivanenok on 2/10/16.
 */
public class ErrorResponse extends TransferMessage {
    public ErrorResponse(String sequenceId, String error, String description) {
        super(sequenceId, ResponseType.CUSTOMER_ERROR.toString(), new ErrorInfo(error, description));
    }

    private static class ErrorInfo {
        @JsonProperty("error_code")
        private String error;

        @JsonProperty("error_description")
        private String description;

        public ErrorInfo() {
        }

        public ErrorInfo(String error, String description) {
            this.error = error;
            this.description = description;
        }


        public String getError() {
            return error;
        }

        public String getDescription() {
            return description;
        }
    }
}
