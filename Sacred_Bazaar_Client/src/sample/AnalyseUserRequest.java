package sample;

import java.io.Serializable;

public class AnalyseUserRequest implements Serializable {
    private String retailerID;

    public AnalyseUserRequest(String retailerID) {
        this.retailerID = retailerID;
    }

    public String getRetailerID() {
        return retailerID;
    }

    public void setRetailerID(String retailerID) {
        this.retailerID = retailerID;
    }

    @Override
    public String toString() {
        return String.valueOf(ServerRequest.ANALYZE_USER_REQUEST);
    }
}