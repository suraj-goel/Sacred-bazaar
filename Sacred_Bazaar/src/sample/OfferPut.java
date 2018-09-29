package sample;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OfferPut {
    private String retailerID;

    public String getRetailerID() {
        return retailerID;
    }

    public void setRetailerID(String retailerID) {
        this.retailerID = retailerID;
    }

    public List<Offer> put(){
        Offer offer;
        List<Offer> offerList=new ArrayList<>();
        String query="Select * from offers where retailerID LIKE '%"+this.retailerID+"%';";
        try {
            PreparedStatement preparedStatement=Main.connection.prepareStatement(query);
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                offer=new Offer();
                offer.setOfferID(resultSet.getString(1));
                offer.setType(resultSet.getString(3));
                System.out.println(resultSet.getString(3));
                offer.setX(resultSet.getDouble(4));
                offer.setY(resultSet.getInt(5));
                offer.setRetailer(new RetailerFetch().fetch(resultSet.getString(2)));
                offerList.add(offer);
            }
            return offerList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
