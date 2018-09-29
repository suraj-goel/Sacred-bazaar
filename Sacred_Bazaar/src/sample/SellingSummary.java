package sample;

import javafx.util.Pair;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SellingSummary {
    public List<CartProduct> fetch(String category){
        String query1="Select * from ProductSold where products.ProductID=ProductSold.ProductID && products.Category=?;";
        String query2="Select * from products where ProductID=?;";
        List<CartProduct> cartProducts=new ArrayList<>();
        try {
            PreparedStatement preparedStatement=Main.connection.prepareStatement(query1);
            preparedStatement.setString(1,category);
            ResultSet resultSet=preparedStatement.executeQuery();
            extract(query2, cartProducts, resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cartProducts;
    }
    public List<CartProduct> fetch(String category, Date start){
        String query1="Select * from ProductSold where products.ProductID=ProductSold.ProductID && products.Category=? && DateOfOrder>=? ;";
        String query2="Select * from products where ProductID=?;";
        List<CartProduct> cartProducts=new ArrayList<>();
        try {
            PreparedStatement preparedStatement=Main.connection.prepareStatement(query1);
            preparedStatement.setString(1,category);
            java.sql.Date date=new java.sql.Date(start.getTime());
            preparedStatement.setDate(2,date);
            ResultSet resultSet=preparedStatement.executeQuery();
            extract(query2, cartProducts, resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cartProducts;
    }
    public List<CartProduct> fetch(String category, Date start, Date end){
        String query1="Select * from ProductSold where products.ProductID=ProductSold.ProductID && products.Category=? && DateOfOrder>=? && DateOfOrder<=?;";
        String query2="Select * from products where ProductID=?;";
        List<CartProduct> cartProducts=new ArrayList<>();
        try {
            PreparedStatement preparedStatement=Main.connection.prepareStatement(query1);
            preparedStatement.setString(1,category);
            java.sql.Date date=new java.sql.Date(start.getTime());
            preparedStatement.setDate(2,date);
            date=new java.sql.Date(end.getTime());
            preparedStatement.setDate(3,date);
            ResultSet resultSet=preparedStatement.executeQuery();
            extract(query2, cartProducts, resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cartProducts;
    }
    public List<LineGraphDate> fetchUsersByDate(String retailerID){
        String query="Select DateOfOrder,count(CustomerID) from ProductSold,products where products.ProductID=ProductSold.ProductID && RetailerID=? Group By DateOfOrder;";
        List<LineGraphDate> lineGraphDates = new ArrayList<>();
        try {
            PreparedStatement preparedStatement=Main.connection.prepareStatement(query);
            preparedStatement.setString(1,retailerID);
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                LineGraphDate lineGraphDate=new LineGraphDate(resultSet.getDate(1));
                lineGraphDate.setUsers(resultSet.getInt(2));
                lineGraphDates.add(lineGraphDate);
            }
            return lineGraphDates;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public List<GraphCategory> fetchUsersByCategory(String retailerID,Date startDate,Date endDate){
        java.sql.Date datestart,dateend;
        String query;
        PreparedStatement preparedStatement=null;
        try {
            datestart=new java.sql.Date(startDate.getTime());
            dateend=new java.sql.Date(endDate.getTime());
            query="Select Category,count(CustomerID) from products,ProductSold where products.ProductID=ProductSold.ProductID && RetailerID='"+retailerID+"' && DateOfOrder>=? && DateOfOrder<=? GROUP BY Category;";
            preparedStatement=Main.connection.prepareStatement(query);
            preparedStatement.setDate(1,datestart);
            preparedStatement.setDate(2,dateend);
        }catch (NullPointerException e){
            query="Select Category,count(CustomerID) from products,ProductSold where products.ProductID=ProductSold.ProductID && RetailerID='"+retailerID+"' GROUP BY Category;";
            try {
                preparedStatement=Main.connection.prepareStatement(query);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        List<GraphCategory> graphCategories=new ArrayList<>();
        try {
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                GraphCategory graphCategory=new GraphCategory(resultSet.getString(1));
                graphCategory.setNumber(resultSet.getInt(2));
                graphCategories.add(graphCategory);
            }
            return graphCategories;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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
