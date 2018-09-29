package sample;


import java.io.Serializable;

public class Offer implements Serializable {
    private String offerID,type,retailerID;
    private Retailer retailer;

    public String getRetailerID() {
        return retailerID;
    }

    public void setRetailerID(String retailerID) {
        this.retailerID = retailerID;
    }

    public Retailer getRetailer() {
        return retailer;
    }

    public void setRetailer(Retailer retailer) {
        this.retailer = retailer;
    }

    public Offer(){

    }
    public String getOfferID() {
        return offerID;
    }

    public void setOfferID(String offerID) {
        this.offerID = offerID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    private double x,y;

    @Override
    public String toString(){
//        System.out.println(this.type);

        if (this.type.equals(String.valueOf(OfferType.BUY_X_GET_Y)))
            return "BUY "+getX()+" GET "+getY()+" FREE";
        else return getX()+"% DISCOUNT";
    }
}