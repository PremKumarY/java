import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseOperations extends JFrame implements ActionListener {

    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String DATABASE_NAME = "College";
    private static final String USER = "root";
    private static final String PASSWORD = "123Cold@1234";

    private JTextField idField, nameField, courseField, branchField, emailField;
    private JButton insertButton, modifyButton, deleteButton;

    public DatabaseOperations() {
        setTitle("Student Database");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // setLayout(new GridLayout(6, 2, 10, 10));
        setLayout(null);

        JLabel idLabel = new JLabel("ID:");
        idField = new JTextField();
        JLabel nameLabel = new JLabel("Student Name:");
        nameField = new JTextField();
        JLabel courseLabel = new JLabel("Course:");
        courseField = new JTextField();
        JLabel branchLabel = new JLabel("Branch:");
        branchField = new JTextField();
        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField();

        idLabel.setBounds(100, 60, 100, 20); 
        nameLabel.setBounds(100, 90, 100, 20); 
        courseLabel.setBounds(100, 120, 100, 20); 
        branchLabel.setBounds(100, 150, 100, 20); 
        emailLabel.setBounds(100, 180, 100, 20); 

        idField.setBounds(180, 60, 100, 20); 
        nameField.setBounds(180, 90, 100, 20); 
        courseField.setBounds(180, 120, 100, 20); 
        branchField.setBounds(180, 150, 100, 20); 
        emailField.setBounds(180, 180, 100, 20); 

        insertButton = new JButton("Insert Record");
        modifyButton = new JButton("Modify Record");
        deleteButton = new JButton("Delete Record");

        insertButton.addActionListener(this);
        modifyButton.addActionListener(this);
        deleteButton.addActionListener(this);

        insertButton.setBounds(180, 370, 80, 30);
        modifyButton.setBounds(180, 400, 80, 30);
        deleteButton.setBounds(180, 430, 80, 30);

        add(idLabel);
        add(idField);
        add(nameLabel);
        add(nameField);
        add(courseLabel);
        add(courseField);
        add(branchLabel);
        add(branchField);
        add(emailLabel);
        add(emailField);
        add(insertButton);
        add(modifyButton);
        add(deleteButton);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DatabaseOperations frame = new DatabaseOperations();
            frame.setVisible(true);
        });

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            createDatabase();

            Connection connection = DriverManager.getConnection(URL + DATABASE_NAME, USER, PASSWORD);

            createTable(connection);

            connection.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(URL + DATABASE_NAME, USER, PASSWORD);

            if (e.getSource() == insertButton) {
                insertRecord(connection, nameField.getText(), courseField.getText(), branchField.getText(), emailField.getText());
            } else if (e.getSource() == modifyButton) {
                modifyRecord(connection, nameField.getText(), courseField.getText(), branchField.getText(), emailField.getText(), Integer.parseInt(idField.getText()));
            } else if (e.getSource() == deleteButton) {
                deleteRecord(connection, Integer.parseInt(idField.getText()));
            }

            connection.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private static void createDatabase() throws SQLException {
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        String sql = "CREATE DATABASE IF NOT EXISTS " + DATABASE_NAME;
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
        statement.close();
        connection.close();
        JOptionPane.showMessageDialog(null, "Database created successfully.", "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void createTable(Connection connection) throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS Students ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "Student_name VARCHAR(50) NOT NULL, "
                + "Course VARCHAR(50) NOT NULL, "
                + "Branch VARCHAR(50) NOT NULL, "
                + "email VARCHAR(50) NOT NULL)";
        Statement statement = connection.createStatement();
        statement.executeUpdate(createTableSQL);
        statement.close();
        JOptionPane.showMessageDialog(null, "Table 'Students' created successfully.", "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void insertRecord(Connection connection, String studentName, String course, String branch, String email) throws SQLException {
        String insertSQL = "INSERT INTO Students (Student_name, Course, Branch, email) VALUES (?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);
        preparedStatement.setString(1, studentName);
        preparedStatement.setString(2, course);
        preparedStatement.setString(3, branch);
        preparedStatement.setString(4, email);
        int rowsInserted = preparedStatement.executeUpdate();
        if (rowsInserted > 0) {
            JOptionPane.showMessageDialog(null, "A new Student was inserted successfully.", "Info", JOptionPane.INFORMATION_MESSAGE);
        }
        preparedStatement.close();
    }

    private static void modifyRecord(Connection connection, String studentName, String course, String branch, String email, int id) throws SQLException {
        String updateSQL = "UPDATE Students SET Student_name = ?, Course = ?, Branch = ?, email = ? WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(updateSQL);
        preparedStatement.setString(1, studentName);
        preparedStatement.setString(2, course);
        preparedStatement.setString(3, branch);
        preparedStatement.setString(4, email);
        preparedStatement.setInt(5, id);
        int rowsUpdated = preparedStatement.executeUpdate();
        if (rowsUpdated > 0) {
            JOptionPane.showMessageDialog(null, "Student record updated successfully.", "Info", JOptionPane.INFORMATION_MESSAGE);
        }
        preparedStatement.close();
    }

    private static void deleteRecord(Connection connection, int id) throws SQLException {
        String deleteSQL = "DELETE FROM Students WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL);
        preparedStatement.setInt(1, id);
        int rowsDeleted = preparedStatement.executeUpdate();
        if (rowsDeleted > 0) {
            JOptionPane.showMessageDialog(null, "Student record deleted successfully.", "Info", JOptionPane.INFORMATION_MESSAGE);
        }
        preparedStatement.close();
    }
}
