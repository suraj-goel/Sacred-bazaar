package sample;

import java.io.Serializable;
import java.util.Date;

public class ProductEditRequest implements Serializable {
    private Product product;
    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ProductEditRequest(Product product){
        this.product=product;
    }
    @Override
    public String toString(){
        return String.valueOf(ServerRequest.PRODUCT_EDIT_REQUEST);
    }
}
