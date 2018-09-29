package sample;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;

public class Main  {
    static String adminuser="admin";//username of admin login
    static String adminpass="admin";//password of admin login
    static String user="root";// user = username of mysql database
    static String password="root";// pass = password of mysql database
    static String host="jdbc:mysql://localhost:3306/Sacred_Bazaar";// host=localhost address of mysql database
    static Connection connection=MysqlConnection.connect();//connection to mysql server

    public static void main(String[] args) {
      //  launch(args);
        ServerSocket serverSocket;//initialising server socket
        Thread t1=new Thread(new BonusOffersReset());
        t1.start();// starting new thread to check offers of a user of a given month

        Socket socket;
        try {
            serverSocket=new ServerSocket(6963);
            System.out.println("server started");
        }
        catch (IOException e){
            e.printStackTrace();
            return;
        }
        while (true){
            try {
                socket=serverSocket.accept();
                System.out.println("client connected");
                Thread t=new Thread(new HandleClientRequest(socket));
                t.start();//starting new thread to handle client request
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

        }
    }
}
