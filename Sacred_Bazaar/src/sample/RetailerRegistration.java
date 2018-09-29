package sample;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RetailerRegistration implements Serializable {
    private String userID,retailerID,address,name;
    private Retailer retailer;
    public RetailerRegistration(RetailerRRequest retailer){
        this.address=retailer.getAddress();
        this.name=retailer.getRetailName();
        this.retailerID=retailer.getRetailerID();
        this.userID=retailer.getId();
        this.retailer=retailer;
    }
    public Retailer register(){
        String query="UPDATE users SET Retailer_Status = 1 where UserID='"+this.userID+"'";
        try {
            PreparedStatement stmt=Main.connection.prepareStatement(query);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            this.retailer.setRetailerRegisterStatus(String.valueOf(RetailerRStatus.NO_USER_EXIST));
            return this.retailer;
        }
        String query2;
        query2="INSERT into retailers values(?,?,?,?);";
        try {
            PreparedStatement stmt=Main.connection.prepareStatement(query2);
            stmt.setString(2,this.retailerID);
            stmt.setString(1,this.userID);
            stmt.setString(3,this.name);
            stmt.setString(4,this.address);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            this.retailer.setRetailerRegisterStatus(String.valueOf(RetailerRStatus.ALREADY_RETAILER));
            return this.retailer;
        }
        this.retailer.setRetailerRegisterStatus(String.valueOf(RetailerRStatus.SUCCESS));
        return this.retailer;
    }
}
