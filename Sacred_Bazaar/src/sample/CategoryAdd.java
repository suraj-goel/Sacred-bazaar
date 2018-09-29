package sample;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CategoryAdd {
    private CategoryAddRequest categoryAddRequest;

    public CategoryAdd(CategoryAddRequest categoryAddRequest){
        this.categoryAddRequest=categoryAddRequest;
    }

    public String add(){
        String query="Insert into category values(?);";
        try {
            PreparedStatement preparedStatement=Main.connection.prepareStatement(query);
            preparedStatement.setString(1,categoryAddRequest.getCategory());
            preparedStatement.executeUpdate();
            return String.valueOf(CategoryAddStatus.SUCCESS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return String.valueOf(CategoryAddStatus.FAILED);
    }
}
