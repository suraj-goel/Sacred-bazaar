package sample;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

public class ProductBuy implements Serializable {
    private List<CartProduct> cartProducts;
    private User user;
    private Date dateofOrder;
    public ProductBuy(ProductBuyRequest productBuyRequest){
        this.cartProducts=productBuyRequest.getProducts();
        this.user=productBuyRequest.getUser();
        this.dateofOrder=new Date(productBuyRequest.getDateofOrder().getTime());
    }
    public String buy(){
        String s;
        for (CartProduct producttemp : this.cartProducts){

            System.out.println(producttemp.getProduct().getName());
        String query1="UPDATE products SET QuantityAvailable=QuantityAvailable-? WHERE ProductID=?; ";
        String query2="INSERT into ProductSold values (?,?,?,?,?,?,?);";
        String query3="UPDATE AmountSpendUser Set AmountSpend=AmountSpend+? Where UserID=?;";
        try {
            PreparedStatement preparedStatement1=Main.connection.prepareStatement(query1);
            PreparedStatement preparedStatement2=Main.connection.prepareStatement(query2);
            PreparedStatement preparedStatement=Main.connection.prepareStatement(query3);
            preparedStatement.setString(2,user.getId());
            preparedStatement.setDouble(1,producttemp.getTransactionCost());
            preparedStatement1.setString(2,producttemp.getProduct().getProductid());
            preparedStatement1.setInt(1,producttemp.getQuantity());
            preparedStatement2.setString(1,producttemp.getProduct().getProductid());
            preparedStatement2.setString(2,user.getId());
            preparedStatement2.setDate(3, dateofOrder);
            preparedStatement2.setString(4,producttemp.getProduct().getCurrentOffer().getOfferID());
            preparedStatement2.setDouble(5,producttemp.getTransactionCost());
            preparedStatement2.setString(6,producttemp.getOrderID());
            preparedStatement2.setInt(7,producttemp.getQuantity());
            preparedStatement1.executeUpdate();
            preparedStatement.executeUpdate();
            preparedStatement2.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return String.valueOf(ProductBuyStatus.ORDER_FAILED);
        }
        }
        Bill bill=new Bill(cartProducts,user);
        return bill.generate();
    }
}
