package sample;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductFetch implements Serializable {
    private String category;
    public ProductFetch(String Category){
        this.category=Category;
    }
    public ProductFetch(){}
    public List<Product> fetch(){
        List<Product> productList=new ArrayList<Product>();
        Product tempProduct;
        String query="Select * from products where category='"+this.category+"' order by ProductName;";
        try {
            PreparedStatement stmt=Main.connection.prepareStatement(query);
            ResultSet rs=stmt.executeQuery();
            while (rs.next()){
                tempProduct=new Product();
                tempProduct.setRetailer(new RetailerFetch().fetch(rs.getString(2)));
                tempProduct.setName(rs.getString(3));
                tempProduct.setPrice(rs.getString(4));
                tempProduct.setMfd(rs.getDate(5));
                tempProduct.setCategory(rs.getString(6));
                tempProduct.setExpiry(rs.getDate(7));
                tempProduct.setRetailerid(rs.getString(2));
                tempProduct.setProductid(rs.getString(1));
                tempProduct.setQuantityavailable(rs.getInt(8));
                System.out.println(rs.getString(9));
                if (rs.getString(9)!=null)
                tempProduct.setCurrentOffer(new OfferFetch().fetch(rs.getString(9)));
                else tempProduct.setCurrentOffer(null);
                productList.add(tempProduct);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productList;
    }
    public List<Product> fetch(int price){
        if(price!=0){
        List<Product> productList=new ArrayList<Product>();
        Product tempProduct;
        String query="Select * from products where category='"+this.category+"' order by Price,ProductName;";
        try {
            PreparedStatement stmt=Main.connection.prepareStatement(query);
            ResultSet rs=stmt.executeQuery();
            while (rs.next()){
                tempProduct=new Product();
                tempProduct.setRetailer(new RetailerFetch().fetch(rs.getString(2)));
                tempProduct.setName(rs.getString(3));
                tempProduct.setPrice(rs.getString(4));
                tempProduct.setMfd(rs.getDate(5));
                tempProduct.setCategory(rs.getString(6));
                tempProduct.setExpiry(rs.getDate(7));
                tempProduct.setRetailerid(rs.getString(2));
                tempProduct.setProductid(rs.getString(1));
                tempProduct.setQuantityavailable(rs.getInt(8));
                if (rs.getString(9)!=null)
                    tempProduct.setCurrentOffer(new OfferFetch().fetch(rs.getString(9)));
                else tempProduct.setCurrentOffer(null);
                productList.add(tempProduct);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productList;}
        return null;
    }
    public List<Product> fetch(String search){
        List<Product> productList=new ArrayList<Product>();
        Product tempProduct;
        String query="Select * from products where ProductName LIKE '%"+search+"%' ORDER BY Price,ProductName;";
        try {
            PreparedStatement stmt=Main.connection.prepareStatement(query);
            ResultSet rs=stmt.executeQuery();
            while (rs.next()){
                tempProduct=new Product();
                tempProduct.setRetailer(new RetailerFetch().fetch(rs.getString(2)));
                tempProduct.setName(rs.getString(3));
                tempProduct.setPrice(rs.getString(4));
                tempProduct.setMfd(rs.getDate(5));
                tempProduct.setCategory(rs.getString(6));
                tempProduct.setExpiry(rs.getDate(7));
                tempProduct.setRetailerid(rs.getString(2));
                tempProduct.setProductid(rs.getString(1));
                tempProduct.setQuantityavailable(rs.getInt(8));
                if (rs.getString(9)!=null)
                    tempProduct.setCurrentOffer(new OfferFetch().fetch(rs.getString(9)));
                else tempProduct.setCurrentOffer(null);
                productList.add(tempProduct);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productList;
    }

}
