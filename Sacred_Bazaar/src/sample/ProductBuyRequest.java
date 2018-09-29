package sample;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ProductBuyRequest implements Serializable {
    private List<CartProduct> cartProducts;
    private User user;
    private Date dateofOrder;

    public User getUser() {
        return user;
    }

    public Date getDateofOrder() {
        return dateofOrder;
    }

    public void setDateofOrder(Date dateofOrder) {
        this.dateofOrder = dateofOrder;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<CartProduct> getProducts() {
        return this.cartProducts;
    }

    public void setProducts(List<CartProduct> products) {
        this.cartProducts = products;
    }

    public ProductBuyRequest(List<CartProduct> products,User user) {
        this.cartProducts=products;
        this.user=user;
    }
    @Override
    public String toString() {
        return String.valueOf(ServerRequest.PRODUCT_BUY_REQUEST);
    }
}
