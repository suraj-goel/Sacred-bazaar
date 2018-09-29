package sample;

import java.io.Serializable;

public class OfferRequest implements Serializable {
    private String retailerID;

    public String getRetailerID() {
        return retailerID;
    }

    public void setRetailerID(String retailerID) {
        this.retailerID = retailerID;
    }

    @Override
    public String toString() {
        return String.valueOf(ServerRequest.OFFER_REQUEST);
    }
}
