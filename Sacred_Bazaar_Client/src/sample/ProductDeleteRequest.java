package sample;

import java.io.Serializable;

public class ProductDeleteRequest implements Serializable {
    Product product;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ProductDeleteRequest(Product product){
        this.product=product;
    }
    @Override
    public String toString(){
        return String.valueOf(ServerRequest.PRODUCT_DELETE_REQUEST);
    }
}
