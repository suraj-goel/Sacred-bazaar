package sample;

import java.io.Serializable;

public class RetailerFetchRequest implements Serializable {
    private String search;

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public RetailerFetchRequest(){
        this.search="";
    }
    @Override
    public String toString(){
        return String.valueOf(ServerRequest.RETAILER_FETCH_REQUEST);
    }
}
