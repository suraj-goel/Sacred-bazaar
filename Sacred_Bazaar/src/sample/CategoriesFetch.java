package sample;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class CategoriesFetch implements Serializable {


    public Categories fetch(){
        Categories categories=new Categories();
        List<String> tempCategory=new ArrayList<String>() ;
        String query="Select Distinct Category from category;";
        try {
            PreparedStatement stmt=Main.connection.prepareStatement(query);
            ResultSet rs=stmt.executeQuery();
            while(rs.next()){
                tempCategory.add(rs.getString(1));
            }
            categories.setCategories(tempCategory);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }


}
