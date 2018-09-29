package sample;

import java.io.Serializable;

public class ProductFetchRequest implements Serializable {
    String category;
    int check;

    public int getCheck() {
        return check;
    }

    public void setCheck(int check) {
        this.check = check;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String name;

    public ProductFetchRequest(String category) {
        this.name = "";
        this.category = category;
        this.check = 0;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return String.valueOf(ServerRequest.PRODUCT_FETCH_REQUEST);
    }
}