package ru.ivanenok.messages.request;

import ru.ivanenok.messages.TransferMessage;

/**
 * Created by ivanenok on 2/10/16.
 */
public class LoginRequest extends TransferMessage {

    private LoginRequest() {
        super();
    }

    public LoginRequest(String login, String password) {
        super(RequestType.LOGIN_CUSTOMER.toString(), new AuthInfo(login, password));
    }

    private static class AuthInfo {
        private String email;

        // todo: should be reworked for md5 at least
        private String password;

        public AuthInfo() {
        }

        public AuthInfo(String email, String password) {
            this.email = email;
            this.password = password;
        }


        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }
    }
}
