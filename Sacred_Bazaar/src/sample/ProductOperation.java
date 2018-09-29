package sample;

import java.io.Serializable;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProductOperation implements Serializable {
    private Product product;
    java.util.Date date;
    public ProductOperation(Product product){
        this.product=product;
    }
    public ProductOperation(ProductAddRequest productAddRequest){
        this.product=productAddRequest.getProduct();
        this.date=productAddRequest.getDate();
    }
    public ProductOperation(ProductEditRequest productEditRequest){
        this.product=productEditRequest.getProduct();
        this.date=productEditRequest.getDate();
    }
    public String add(){
        String query="Insert into products values(?,?,?,?,?,?,?,?,?,?);";
        String query2="CREATE EVENT offerEvent " +
                "ON SCHEDULE AT ? " +
                "ON COMPLETION PRESERVE " +
                "DO "+
                "UPDATE products SET currentOffer=NULL where ProductID=? ;"
                ;
        Date d;
        try {
            PreparedStatement statement=Main.connection.prepareStatement(query);
            PreparedStatement statement1=Main.connection.prepareStatement(query2);
            statement1.setString(2,product.getProductid());
            Date date=new Date(this.date.getTime());
            statement.setDate(10,date);
            statement1.setDate(1,date);
            statement.setString(1,product.getProductid());
            statement.setString(2,product.getRetailerid());
            statement.setString(3,product.getName());
            statement.setString(4,""+product.getPrice());
            d=new Date(product.getMfd().getTime());
            System.out.println(d);
            statement.setDate(5, d);
            statement.setString(6,product.getCategory());
            d=new Date(product.getExpiry().getTime());
            System.out.println(d);
            statement.setDate(7,d);
            statement.setInt(8,product.getQuantityavailable());
            statement.setString(9,product.getCurrentOffer().getOfferID());
            statement.executeUpdate();
            return String.valueOf(ProductAStatus.SUCCESS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return String.valueOf(ProductAStatus.FAILED);
    }
    public String delete(){
        String query="DELETE from products where ProductID=?;";
        try {
            PreparedStatement statement=Main.connection.prepareStatement(query);
            statement.setString(1,product.getProductid());
            statement.executeUpdate();
            return String.valueOf(ProductAStatus.SUCCESS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return String.valueOf(ProductAStatus.FAILED);
    }
    public String edit(){
        Date d;
        String query="UPDATE products SET ProductName=?,Price=?,ManufacturingDate=?,Category=?,ExpiryDate=?,QuantityAvailable=?,currentOffer=?,currentOfferEnd=? where ProductID=?;";
        String query2="CREATE EVENT offerEvent " +
                "ON SCHEDULE AT ? " +
                "ON COMPLETION PRESERVE " +
                "DO "+
                "UPDATE products SET currentOffer=NULL where ProductID=? ;"
                ;
        try {
            PreparedStatement statement=Main.connection.prepareStatement(query);
            PreparedStatement statement1=Main.connection.prepareStatement(query2);
            statement1.setString(2,product.getProductid());
            Date date=new Date(this.date.getTime());
            statement.setDate(8,date);
            statement1.setDate(1,date);
            statement.setString(1,product.getName());
            statement.setString(2,""+product.getPrice());
            d=new Date(product.getMfd().getTime());
            statement.setDate(3,d);
            statement.setString(4,product.getCategory());
            d=new Date(product.getExpiry().getTime());
            statement.setDate(5,d);
            statement.setInt(6,product.getQuantityavailable());
            statement.setString(7,product.getCurrentOffer().getOfferID());
            statement.setString(9,product.getProductid());
            statement.executeUpdate();
            return String.valueOf(ProductAStatus.SUCCESS);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e);
        }
        return String.valueOf(ProductAStatus.FAILED);
    }
}
