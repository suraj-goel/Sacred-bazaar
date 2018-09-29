package sample;

import java.io.Serializable;

public class CategoryRemoveRequest implements Serializable {
    private String category;
    public CategoryRemoveRequest(String category){
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
        return String.valueOf(ServerRequest.CATEGORY_REMOVE_REQUEST);
    }
}