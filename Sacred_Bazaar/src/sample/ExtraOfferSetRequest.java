package sample;

import java.io.Serializable;
import java.util.Date;

public class ExtraOfferSetRequest implements Serializable {
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

    @Override
    public String toString(){
        return String.valueOf(ServerRequest.EXTRA_OFFER_SET_REQUEST);
    }
}
