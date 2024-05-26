import java.sql.Connection;
import java.sql.DriverManager;  
import java.sql.Statement;

import javax.swing.JOptionPane;
class Server{  
/**
 * @param args
 */
public static void main(String args[]){  
try{ 
    String url = "jdbc:mysql://localhost:3306/";
    String databaseName="Ramu";
    String userName="root";
    String password="123Cold@1234";

    Class.forName("com.mysql.jdbc.Driver");

    Connection con=DriverManager.getConnection(url,userName,password);
    String sql ="CREATE DATABASE IF NOT EXISTS "+ databaseName;
    Statement statement=con.createStatement();
    statement.executeUpdate(sql);
    statement.close();
    JOptionPane.showMessageDialog(null, statement, password, 0);
}catch (Exception e) {
    // Displaying error message
    JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    e.printStackTrace();

}
}  }