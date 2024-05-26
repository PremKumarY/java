import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class databaseoperation1 extends JFrame implements ActionListener {

    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String DATABASE_NAME = "College";
    private static final String USER = "root";
    private static final String PASSWORD = "123Cold@1234";

    private JTextField idField, nameField, courseField, branchField, emailField;
    private JButton insertButton, modifyButton, deleteButton, showButton;
    private JTable table;
    private DefaultTableModel tableModel;

    public databaseoperation1() {
        setTitle("Student Database");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        JLabel idLabel = new JLabel("ID:");
        idLabel.setBounds(100,30,100,20);
        idField = new JTextField();
        idField.setBounds(200,30,100,20);

        JLabel nameLabel = new JLabel("Student Name:");
        nameLabel.setBounds(100,60,100,20);
        nameField = new JTextField();
        nameField.setBounds(200,60,100,20);

        JLabel courseLabel = new JLabel("Course:");
        courseLabel.setBounds(100,90,100,20);
        courseField = new JTextField();
        courseField.setBounds(200,90,100,20);

        JLabel branchLabel = new JLabel("Branch:");
        branchLabel.setBounds(100,120,100,20);
        branchField = new JTextField();
        branchField.setBounds(200,120,100,20);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(100,150,100,20);
        emailField = new JTextField();
        emailField.setBounds(200,150,100,20);

        insertButton = new JButton("Insert Record");
        insertButton.setBounds(100,180,200,20);

        modifyButton = new JButton("Modify Record");
        modifyButton.setBounds(100,210,200,20);

        deleteButton = new JButton("Delete Record");
        deleteButton.setBounds(100,240,200,20);

        showButton = new JButton("Show Records");
        showButton.setBounds(100,270,200,20);

        insertButton.addActionListener(this);
        modifyButton.addActionListener(this);
        deleteButton.addActionListener(this);
        showButton.addActionListener(this);

        inputPanel.add(new JLabel("ID:"));
        inputPanel.add(idField);
        inputPanel.add(new JLabel("Student Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Course:"));
        inputPanel.add(courseField);
        inputPanel.add(new JLabel("Branch:"));
        inputPanel.add(branchField);
        inputPanel.add(new JLabel("Email:"));
        inputPanel.add(emailField);
        inputPanel.add(insertButton);
        inputPanel.add(modifyButton);
        inputPanel.add(deleteButton);
        inputPanel.add(showButton);

        tableModel = new DefaultTableModel(new String[]{"ID", "Student Name", "Course", "Branch", "Email"}, 0);
        table = new JTable(tableModel);

        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            databaseoperation1 frame = new databaseoperation1();
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
            } else if (e.getSource() == showButton) {
                showRecords(connection);
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

    private void showRecords(Connection connection) throws SQLException {
        String selectSQL = "SELECT * FROM Students";
        PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
        ResultSet resultSet = preparedStatement.executeQuery();

        tableModel.setRowCount(0); // Clear existing data

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String studentName = resultSet.getString("Student_name");
            String course = resultSet.getString("Course");
            String branch = resultSet.getString("Branch");
            String email = resultSet.getString("email");
            tableModel.addRow(new Object[]{id, studentName, course, branch, email});
        }

        preparedStatement.close();
    }
}
