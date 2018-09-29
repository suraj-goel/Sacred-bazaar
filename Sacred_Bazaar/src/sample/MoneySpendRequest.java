package sample;

import java.io.Serializable;
import java.util.Date;

public class MoneySpendRequest implements Serializable {
    private User user;
    private Date start,end;

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public MoneySpendRequest(User user){
        this.user=user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString(){
        return String.valueOf(ServerRequest.MONEY_SPEND_REQUEST);
    }
}
