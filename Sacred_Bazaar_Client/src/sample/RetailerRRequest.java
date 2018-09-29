package sample;

import java.io.Serializable;

public class RetailerRRequest  extends Retailer implements Serializable {
    public RetailerRRequest(Retailer user) {
        super(user);
        this.retailerID=user.retailerID;
        this.address=user.getAddress();
        this.retailName=user.getRetailName();
    }

    @Override
    public String toString() {
        return String.valueOf(ServerRequest.RETAILER_REGISTRATION);
    }
}
