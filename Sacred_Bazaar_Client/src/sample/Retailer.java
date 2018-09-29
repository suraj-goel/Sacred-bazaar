package sample;

public class Retailer extends User {
    protected String retailerID,retailName,address;
    protected String retailerRegisterStatus;
    public Retailer(User user) {
        this.email=user.email;
        this.firstName=user.firstName;
        this.lastName=user.lastName;
        this.phone=user.phone;
        this.id=user.getId();
    }

    public String getRetailerID() {
        return retailerID;
    }

    public void setRetailerID(String retailerID) {
        this.retailerID = retailerID;
    }

    public String getRetailName() {
        return retailName;
    }

    public void setRetailName(String retailName) {
        this.retailName = retailName;
    }

    public String getRetailerRegisterStatus() {
        return retailerRegisterStatus;
    }

    public void setRetailerRegisterStatus(String retailerRegisterStatus) {
        this.retailerRegisterStatus = retailerRegisterStatus;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
