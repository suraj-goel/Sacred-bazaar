package sample;

import java.io.Serializable;

public class GraphCategory implements Serializable {
    private String category;
    private Integer number;
    public GraphCategory(String s){
        this.category=s;
        this.number=0;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}