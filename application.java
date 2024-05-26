import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class application extends JFrame {

    public application() {
        // Set the title of the frame
        setTitle("Swing Application with Menu, Toolbar, and Table");
        
        // Set the default close operation
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Set the size of the frame
        setSize(600, 400);
        
        // Set the location of the frame to the center of the screen
        setLocationRelativeTo(null);

        // Create and set up the menu bar
        JMenuBar menuBar = new JMenuBar();
        
        // Create the File menu
        JMenu fileMenu = new JMenu("File");
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitMenuItem);
        
        // Add File menu to the menu bar
        menuBar.add(fileMenu);

        // Create the Help menu
        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutMenuItem = new JMenuItem("About");
        aboutMenuItem.addActionListener(e -> JOptionPane.showMessageDialog(this, "Swing Application Example\nVersion 1.0"));
        helpMenu.add(aboutMenuItem);
        
        // Add Help menu to the menu bar
        menuBar.add(helpMenu);

        // Set the menu bar for the frame
        setJMenuBar(menuBar);

        // Create and set up the toolbar
        JToolBar toolBar = new JToolBar();
        JButton addButton = new JButton("Add Row");
        JButton removeButton = new JButton("Remove Row");
        
        toolBar.add(addButton);
        toolBar.add(removeButton);
        
        // Add the toolbar to the frame
        add(toolBar, BorderLayout.NORTH);

        // Create and set up the table
        String[] columnNames = {"Name", "Age", "Occupation"};
        Object[][] data = {
            {"John Doe", 25, "Developer"},
            {"Jane Smith", 30, "Designer"},
            {"Mike Johnson", 35, "Manager"}
        };

        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(tableModel);
        
        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Add action listeners for the toolbar buttons
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableModel.addRow(new Object[]{"New Name", 0, "New Occupation"});
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    tableModel.removeRow(selectedRow);
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a row to remove.");
                }
            }
        });
    }

    public static void main(String[] args) {
        // Use the event dispatch thread to create and show the GUI
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                application example = new application();
                example.setVisible(true);
            }
        });
    }
}
