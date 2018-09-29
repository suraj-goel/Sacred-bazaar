package sample;

import java.io.Serializable;

public class TransactionFetchRequest implements Serializable {
    private String Category;

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    @Override
    public String toString(){
        return String.valueOf(ServerRequest.TRANSACTION_FETCH_REQUEST);
    }
}
