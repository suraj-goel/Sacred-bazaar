package sample;
//importing Appropriate classes
import java.io.Serializable;
import java.util.Date;

/**
 * Class of the request send
 * by Client for Analytics
 * Products of each Category
 */
public class AnalyseCategoryRequest implements Serializable {

    private String retailerID;//Retailer Id of the Retailer want to analyse
    private Date startDate;//Start Date of Analytics Process
    private Date endDate;//End Date of Analytics Process

    /**
     * Method for returning End date of Analytics Request
     * @return End Date of Analytics
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Method for setting End Date of Analytics Request
     * @param endDate End Date of Analytics Request
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * Method for getting Start date of Analytics Request
     * @return Start Date of Analytics Request
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Method for setting Start Date of Analytics Request
     * @param startDate Start Date of Analytics Request
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Constructor for setting the retailer Id
     * for the retailer who want Analytics
     * @param retailerID Retailer ID of Retailer
     */
    public AnalyseCategoryRequest(String retailerID){
        this.retailerID=retailerID;
    }

    /**
     * Method for getting the retailer ID
     * of the retailer seeking analysis
     * @return Retailer Id of Retailer
     */
    public String getRetailerID() {
        return retailerID;
    }

    /**
     * Method for setting the retailer ID
     * of the Retailer wanting analysis
     * @param retailerID String of Retailer Id of Retailer
     */
    public void setRetailerID(String retailerID) {
        this.retailerID = retailerID;
    }

    /**
     * Method for returning string of Request send by Client
     * @return String showing Appropriate Analyse request
     */
    @Override
    public String toString(){
        return String.valueOf(ServerRequest.ANALYZE_CATEGORY_REQUEST);
    }
}
