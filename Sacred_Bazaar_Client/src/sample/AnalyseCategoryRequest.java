package sample;

import java.io.Serializable;
import java.util.Date;

public class AnalyseCategoryRequest implements Serializable {
    private String retailerID;
    private Date startDate,endDate;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public AnalyseCategoryRequest(String retailerID){
        this.retailerID=retailerID;
    }

    public String getRetailerID() {
        return retailerID;
    }

    public void setRetailerID(String retailerID) {
        this.retailerID = retailerID;
    }

    @Override
    public String toString(){
        return String.valueOf(ServerRequest.ANALYZE_CATEGORY_REQUEST);
    }
}