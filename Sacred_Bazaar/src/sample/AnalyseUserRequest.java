package sample;
//importing Appropriate classes
import java.io.Serializable;

/**
 * Class of the request send by
 * client for analysing interaction
 * number of users to retailers
 */
public class AnalyseUserRequest implements Serializable {

    private String retailerID;//Retailer Id of the user wants analysis

    /**
     * Constructor for setting retailer ID
     * of the retailer wanting analysis
     * @param retailerID String of the retailer ID of retailer
     */
    public AnalyseUserRequest(String retailerID) {
        this.retailerID = retailerID;
    }

    /**
     * Method for returning retailer ID
     * of the retailer wanting analysis
     * @return String of the retailer ID of retailer
     */
    public String getRetailerID() {
        return retailerID;
    }

    /**
     * Method for setting retailer ID
     * of the retailer wanting analysis
     * @param retailerID String of the retailer ID of retailer
     */
    public void setRetailerID(String retailerID) {
        this.retailerID = retailerID;
    }

    /**
     * Method for returning String showing
     * Appropriate message for Analysis of User Interaction
     * by Retailer
     * @return String returning Appropriate request
     */
    @Override
    public String toString() {
        return String.valueOf(ServerRequest.ANALYZE_USER_REQUEST);
    }
}
