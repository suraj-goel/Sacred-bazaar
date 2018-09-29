package sample;

import java.io.Serializable;

public enum  ExtraOfferSetStatus implements Serializable {
    SUCCESS("0"),FAILED("1");
    ExtraOfferSetStatus(String s){
        s.toString();
    }
}
