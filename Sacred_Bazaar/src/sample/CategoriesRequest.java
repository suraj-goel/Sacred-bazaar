package sample;

import java.io.Serializable;

public class CategoriesRequest extends Categories implements Serializable {
    @Override
    public String toString() {
        return String.valueOf(ServerRequest.CATEGORIES);
    }
}
