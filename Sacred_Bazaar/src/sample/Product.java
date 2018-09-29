package sample;

import java.io.Serializable;
import java.util.Date;

public class Product implements Serializable {
    private String name,productid,retailerid,category;
    private Retailer retailer;
    private Date mfd,expiry;
    private String offerID;

    public String getOfferID() {
        return offerID;
    }

    public void setOfferID(String offerID) {
        this.offerID = offerID;
    }

    public Retailer getRetailer() {
        return retailer;
    }

    public void setRetailer(Retailer retailer) {
        this.retailer = retailer;
    }

    private int quantityavailable;

    public int getQuantityselected() {
        return quantityselected;
    }

    public void setQuantityselected(int quantityselected) {
        this.quantityselected = quantityselected;
    }

    private int quantityselected;
    private double price;
    private Offer currentOffer;
    private String productStatus;
    public Product(){
        this.currentOffer=new Offer();
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(String productAdditionStatus) {
        this.productStatus = productAdditionStatus;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public void setRetailerid(String retailerid) {
        this.retailerid = retailerid;
    }

    public void setPrice(String price) {
        this.price = Double.parseDouble(price);
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setMfd(Date mfd) {
        this.mfd = mfd;
    }

    public void setExpiry(Date expiry) {
        this.expiry = expiry;
    }

    public void setQuantityavailable(int quantityavailable) {
        this.quantityavailable = quantityavailable;
    }

    public void setCurrentOffer(Offer currentOffer) {
        this.currentOffer = currentOffer;
    }

    public String getName() {
        return name;
    }

    public String getProductid() {
        return productid;
    }

    public String getRetailerid() {
        return retailerid;
    }

    public Double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public String getCategory() {
        return category;
    }

    public Date getMfd() {
        return mfd;
    }

    public Date getExpiry() {
        return expiry;
    }

    public int getQuantityavailable() {
        return quantityavailable;
    }

    public Offer getCurrentOffer() {
        return currentOffer;
    }
}
