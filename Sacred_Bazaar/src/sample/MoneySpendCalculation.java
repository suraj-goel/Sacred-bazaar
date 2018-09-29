package sample;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MoneySpendCalculation implements Serializable {
    private User user;
    public MoneySpendCalculation(User user){
        this.user=user;
    }
    public List<CartProduct> fetch(){
        String query1="Select * from ProductSold where CustomerID=? order by DateOfOrder;";
        String query2="Select * from products where ProductID=?;";
        List<CartProduct> cartProducts=new ArrayList<>();
        try {
            PreparedStatement preparedStatement=Main.connection.prepareStatement(query1);
            preparedStatement.setString(1,user.getId());
            ResultSet resultSet=preparedStatement.executeQuery();
            extract(query2, cartProducts, resultSet);
            for (CartProduct cartProduct:cartProducts) System.out.println(cartProduct.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cartProducts;
    }
    public List<CartProduct> fetch(Date start){
        String query1="Select * from ProductSold where CustomerID=? && DateOfOrder>=? order by DateOfOrder;";
        String query2="Select * from products where ProductID=?;";
        List<CartProduct> cartProducts=new ArrayList<>();
        try {
            PreparedStatement preparedStatement=Main.connection.prepareStatement(query1);
            preparedStatement.setString(1,user.getId());
            java.sql.Date date=new java.sql.Date(start.getTime());
            preparedStatement.setDate(2,date);
            ResultSet resultSet=preparedStatement.executeQuery();
            extract(query2, cartProducts, resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cartProducts;
    }
    public List<CartProduct> fetch(Date start,Date end){
        String query1="Select * from ProductSold where CustomerID=? && DateOfOrder>=? && DateOfOrder<=? order by DateOfOrder;";
        String query2="Select * from products where ProductID=?;";
        List<CartProduct> cartProducts=new ArrayList<>();
        try {
            PreparedStatement preparedStatement=Main.connection.prepareStatement(query1);
            preparedStatement.setString(1,user.getId());
            java.sql.Date date=new java.sql.Date(start.getTime());
            preparedStatement.setDate(2,date);
            date = new java.sql.Date(end.getTime());
            preparedStatement.setDate(3,date);
            ResultSet resultSet=preparedStatement.executeQuery();
            extract(query2, cartProducts, resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cartProducts;
    }

    private void extract(String query2, List<CartProduct> cartProducts, ResultSet resultSet) throws SQLException {
        while (resultSet.next()){
            Product product=new Product();
            PreparedStatement preparedStatement1= Main.connection.prepareStatement(query2);
            preparedStatement1.setString(1,resultSet.getString(1));
            ResultSet resultSet1=preparedStatement1.executeQuery();
            while (resultSet1.next()){
                product.setProductid(resultSet1.getString(1));
                product.setRetailer(new RetailerFetch().fetch(resultSet1.getString(2)));
                product.setName(resultSet1.getString(3));
                product.setPrice(resultSet1.getString(4));
                product.setMfd(resultSet1.getDate(5));
                product.setCategory(resultSet1.getString(6));
                product.setExpiry(resultSet1.getDate(7));
                product.setCurrentOffer(new OfferFetch().fetch(resultSet1.getString(9)));
            }
            CartProduct cartProducttemp=new CartProduct(product);
            cartProducttemp.setOrderDate(resultSet.getDate(3));
            cartProducttemp.setOrderID(resultSet.getString(6));
            cartProducttemp.setQuantity(resultSet.getInt(7));
            cartProducttemp.setTransactionCost(resultSet.getDouble(5));
            cartProducttemp.setOffer(new OfferFetch().fetch(resultSet.getString(4)));
            cartProducts.add(cartProducttemp);
        }
    }
}
