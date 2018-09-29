package sample;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OfferAdd implements Serializable {
    private Offer offer;
    public OfferAdd(OfferAddRequest offerAddRequest){
        this.offer=offerAddRequest.getOffer();
    }

    public String add(){
        String query="Insert into offers values(?,?,?,?,?);";
        try {
            PreparedStatement preparedStatement=Main.connection.prepareStatement(query);
            preparedStatement.setString(1,offer.getOfferID());
            preparedStatement.setString(2,offer.getRetailerID());
            preparedStatement.setString(3,offer.getType());
            preparedStatement.setDouble(4,offer.getX());
            preparedStatement.setInt(5,(int)offer.getY());
            preparedStatement.executeUpdate();
            return String.valueOf(OfferStatus.SUCCESS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return String.valueOf(OfferStatus.FAILED);
    }
}
