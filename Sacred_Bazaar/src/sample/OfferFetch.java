package sample;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OfferFetch implements Serializable {

    public Offer fetch(String id){
        Offer offer=new Offer();
        offer.setOfferID(id);
        String query="Select * from offers where offerID='"+offer.getOfferID()+"';";
        try {
            PreparedStatement preparedStatement=Main.connection.prepareStatement(query);
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                offer.setType(resultSet.getString(3));
                offer.setX(resultSet.getDouble(4));
                offer.setY(resultSet.getDouble(5));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return offer;

    }
}
