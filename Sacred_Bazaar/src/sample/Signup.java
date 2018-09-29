package sample;

//import databse connection library
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Signup implements Serializable {
    //Instance variable
    //names
    private String email,fname,lname,phone,password;
    private String query;
    private String uid;

    //public constructor
    public Signup(SignupRequest user){
        this.email=user.getEmail();
        this.phone=user.getPhone();
        this.uid=user.getId();
        this.fname=user.getFirstName();
        this.lname=user.getLastName();
        this.password=user.getPassword();
    }
    //method to add instance variable values to database
    public String put(){
        String query1="INSERT INTO AmountSpendUser(UserID) VALUES (?)";
        if(this.phone==null){
            this.query="INSERT INTO users(UserID,Email,First_Name,Last_Name,Password) VALUES (?,?,?,?,?);";
        }
        else {
            this.query="INSERT INTO users VALUES( ? , ? , ? , ? , ? , ? ,0);";
        }
        try {
            PreparedStatement stmt=Main.connection.prepareStatement(this.query);
            PreparedStatement preparedStatement=Main.connection.prepareStatement(query1);
            stmt.setString(1,this.uid);
            preparedStatement.setString(1,this.uid);
            stmt.setString(2,this.email);
            stmt.setString(3,this.fname);
            stmt.setString(4,this.lname);
            if (this.phone==null){
                stmt.setString(5,this.password);
            }
            else {
                stmt.setString(5,this.phone);
                stmt.setString(6,this.password);
            }
            stmt.executeUpdate();
            preparedStatement.executeUpdate();
            return String.valueOf(SignupStatus.SUCCESS);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e);
            return String.valueOf(SignupStatus.FAILED);
        }
    }
}
