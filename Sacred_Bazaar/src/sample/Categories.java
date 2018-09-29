package sample;

import java.io.Serializable;
import java.util.List;

public class Categories implements Serializable {
    private List<String> categories=null;

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }
}
