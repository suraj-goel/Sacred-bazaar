package sample;
//importing appropriate classes
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Class for awarding Extra offer
 * to a User on a basis of spending amount monthly
 */
public class BonusOffersReset implements Runnable{
    static Timer timer = new Timer();
    static Date dateTimer;
    private static class TimerTask extends java.util.TimerTask{

        @Override
        public void run() {
            Double x=0.0;
            Date date=new Date();
            Date dateBonus=new Date();
            List<String> userList=new ArrayList<>();
            String query1="Select * from AmountSpendUser where UserID='admin';";
            String query2="Select * from AmountSpendUser where UserID!='admin' order by AmountSpend desc;";
            try {
                PreparedStatement preparedStatement=Main.connection.prepareStatement(query1);
                PreparedStatement preparedStatement1=Main.connection.prepareStatement(query2);
                ResultSet resultSet=preparedStatement.executeQuery();
                while (resultSet.next()){
                    x=resultSet.getDouble(2);
                    date=resultSet.getDate(3);
                    dateBonus=resultSet.getDate(4);
                }
                ResultSet resultSet1=preparedStatement1.executeQuery();
                int i=0;
                while (resultSet1.next()){
                    userList.add(resultSet1.getString(1));
                    i++;
                    if (i==5)break;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            String query4="UPDATE AmountSpendUser Set DiscountEndDate=NULL && BonusEndDate=NULL where UserID!='admin';";
            try {
                PreparedStatement preparedStatement=Main.connection.prepareStatement(query4);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            String query="UPDATE AmountSpendUser Set DiscountEndDate=? where AmountSpend>=?;";
            try {
                PreparedStatement preparedStatement=Main.connection.prepareStatement(query);
                preparedStatement.setDouble(2,x);
                java.sql.Date date1=new java.sql.Date(date.getTime());
                preparedStatement.setDate(1,date1);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            for (String s : userList){
                String query3="UPDATE AmountSpendUser Set BonusEndDate=? where UserID=?;";
                try {
                    PreparedStatement preparedStatement=Main.connection.prepareStatement(query3);
                    java.sql.Date date4=new java.sql.Date(dateBonus.getTime());
                    preparedStatement.setDate(1,date4);
                    preparedStatement.setString(2,s);
                    preparedStatement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void run() {
        int period = 86400000;
        Timer timer1=new Timer();
        timer1.scheduleAtFixedRate(new java.util.TimerTask() {
            @Override
            public void run() {
                Calendar calendar=Calendar.getInstance();
                calendar.set(Calendar.DATE,1);
                calendar.add(Calendar.MONTH,1);
                dateTimer=calendar.getTime();
                timer.schedule(new TimerTask(),dateTimer);
            }
        },0,period);
    }
}
