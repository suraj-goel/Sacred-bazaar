package sample;

import java.io.Serializable;

public class User implements Serializable {
    protected String firstName,lastName,email,phone,id;
    private int retailerStatus;
    private String verificationStatus;
    private boolean extraOffer,bonusOffer;

    public boolean isExtraOffer() {
        return extraOffer;
    }

    public void setExtraOffer(boolean extraOffer) {
        this.extraOffer = extraOffer;
    }

    public boolean isBonusOffer() {
        return bonusOffer;
    }

    public void setBonusOffer(boolean bonusOffer) {
        this.bonusOffer = bonusOffer;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setRetailerStatus(int retailerStatus) {
        this.retailerStatus = retailerStatus;
    }

    public void setVerificationStatus(String verificationStatus) {
        this.verificationStatus = verificationStatus;
    }

    public User(){

    }
    public String getId() {
        return id;
    }

    public String getVerificationStatus() {
        return verificationStatus;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public int getRetailerStatus() {
        return retailerStatus;
    }

    @Override
    public String toString(){
        return this.getFirstName();
    }
}