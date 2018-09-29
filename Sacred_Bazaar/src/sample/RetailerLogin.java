package sample;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RetailerLogin implements Serializable {
    private String userID,retailerID,address,name;
    private User user;
    private Retailer retailer;
    public RetailerLogin(RetailerLoginRequest retailerLoginRequest){
        this.user=retailerLoginRequest.getUser();
    }
    public Retailer login(){
        String query="Select * from retailers where UserID = ?;";
        try {
            retailer=new Retailer(user);
            PreparedStatement statement=Main.connection.prepareStatement(query);
            statement.setString(1,user.getId());
            ResultSet resultSet=statement.executeQuery();
            System.out.println(statement);
            while (resultSet.next()){
                retailer.setRetailerID(resultSet.getString(2));
                retailer.setAddress(resultSet.getString(4));
                retailer.setRetailName(resultSet.getString(3));
                System.out.println(retailer.getRetailerID());

            }

            return retailer;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return retailer;
    }
}
