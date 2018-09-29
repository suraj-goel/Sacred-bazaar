package sample;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CategoryRemove {
    private CategoryRemoveRequest categoryRemoveRequest;

    public CategoryRemove(CategoryRemoveRequest categoryRemoveRequest){
        this.categoryRemoveRequest=categoryRemoveRequest;
    }

    public String remove(){
        String query="Delete from category where Category=?;";
        try {
            PreparedStatement preparedStatement=Main.connection.prepareStatement(query);
            preparedStatement.setString(1,categoryRemoveRequest.getCategory());
            preparedStatement.executeUpdate();
            return String.valueOf(CategoryRemoveStatus.SUCCESS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return String.valueOf(CategoryRemoveStatus.FAILED);
    }
}
