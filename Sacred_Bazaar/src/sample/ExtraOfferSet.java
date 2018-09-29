package sample;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

public class ExtraOfferSet {
    private Double pricex;
    private Date endDate;
    private Date endDateOffer;

    public Double getPricex() {
        return pricex;
    }

    public void setPricex(Double pricex) {
        this.pricex = pricex;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getEndDateOffer() {
        return endDateOffer;
    }

    public void setEndDateOffer(Date endDateOffer) {
        this.endDateOffer = endDateOffer;
    }


    public String setBonusOffer(){
        String query="Update AmountSpendUser set BonusEndDate=? where UserID='admin';";
        try {
            PreparedStatement preparedStatement=Main.connection.prepareStatement(query);
            preparedStatement.setDate(1,new java.sql.Date(getEndDateOffer().getTime()));
            preparedStatement.executeUpdate();
            return String.valueOf(ExtraOfferSetStatus.SUCCESS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return String.valueOf(ExtraOfferSetStatus.FAILED);
    }

    public String setOffer(){
        String query="Update AmountSpendUser set AmountSpend=? , DiscountEndDate=? where UserID='admin';";
        try {
            PreparedStatement preparedStatement=Main.connection.prepareStatement(query);
            preparedStatement.setDouble(1,getPricex());
            preparedStatement.setDate(2,new java.sql.Date(getEndDate().getTime()));
            preparedStatement.executeUpdate();
            return String.valueOf(ExtraOfferSetStatus.SUCCESS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return String.valueOf(ExtraOfferSetStatus.FAILED);
    }
}
