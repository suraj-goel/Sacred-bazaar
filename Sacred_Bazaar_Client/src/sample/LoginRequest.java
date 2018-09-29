package sample;

import java.io.Serializable;

public class LoginRequest implements Serializable {

    private String email,password;

    public LoginRequest(String email,String password) {
        this.email=email;
        this.password=password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return String.valueOf(ServerRequest.LOGIN_REQUEST);
    }
}
