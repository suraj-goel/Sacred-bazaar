package sample;

import java.io.Serializable;

public class CategoryAddRequest implements Serializable {
    private String category;
    public CategoryAddRequest(String category){
        this.category=category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString(){
        return String.valueOf(ServerRequest.CATEGORY_ADD_REQUEST);
    }
}
