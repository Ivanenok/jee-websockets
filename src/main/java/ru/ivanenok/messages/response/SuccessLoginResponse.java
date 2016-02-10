package ru.ivanenok.messages.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.ivanenok.messages.TransferMessage;
import ru.ivanenok.messages.response.ResponseType;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by ivanenok on 2/10/16.
 */
public class SuccessLoginResponse extends TransferMessage {
    public SuccessLoginResponse(String sequenceId, String token, LocalDateTime expiration) {
        super(sequenceId, ResponseType.CUSTOMER_API_TOKEN.toString(), new TokenInfo(token, expiration));
    }

    private static class TokenInfo {
        @JsonProperty("api_token")
        private String token;

        @JsonProperty("api_token_expiration_date")
        private LocalDateTime expiration;

        public TokenInfo() {
        }

        public TokenInfo(String token, LocalDateTime expiration) {
            this.token = token;
            this.expiration = expiration;
        }


        public String getToken() {
            return token;
        }

        public String getExpiration() {
            return expiration.toInstant(ZoneOffset.UTC).toString();
        }
    }
}
