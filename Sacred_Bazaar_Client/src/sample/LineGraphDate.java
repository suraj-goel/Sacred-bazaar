package sample;

import java.io.Serializable;
import java.util.Date;

public class LineGraphDate implements Serializable {
    private Date purchaseDate;
    private Integer users;

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Integer getUsers() {
        return users;
    }

    public void setUsers(Integer users) {
        this.users = users;
    }

    public LineGraphDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
        this.users=0;
    }
}