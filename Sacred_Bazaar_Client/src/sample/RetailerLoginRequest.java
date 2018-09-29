package sample;

import java.io.Serializable;

public class RetailerLoginRequest implements Serializable {
    private User user;
    public RetailerLoginRequest(User user){
        this.user=user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return String.valueOf(ServerRequest.RETAILER_LOGIN);
    }
}