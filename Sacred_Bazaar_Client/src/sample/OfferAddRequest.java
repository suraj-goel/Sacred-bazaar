package sample;

import java.io.Serializable;

public class OfferAddRequest implements Serializable {
    private Offer offer;
    public OfferAddRequest(Offer offer){
        this.offer=offer;
    }

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    @Override
    public String toString(){
        return String.valueOf(ServerRequest.OFFER_ADD_REQUEST);
    }
}
