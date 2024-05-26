import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class mouseMoment1 extends JFrame implements MouseListener, MouseMotionListener {

    private JLabel statusLabel;

    public mouseMoment1() {
        setTitle("Mouse Movement Example");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create a panel to capture mouse events
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.addMouseListener(this);
        panel.addMouseMotionListener(this);

        // Create a status label to display mouse event information
        statusLabel = new JLabel("Move the mouse or click to see events");
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Add components to the frame
        add(panel, BorderLayout.CENTER);
        add(statusLabel, BorderLayout.SOUTH);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        statusLabel.setText("Mouse Clicked at (" + e.getX() + ", " + e.getY() + ")");
    }

    @Override
    public void mousePressed(MouseEvent e) {
        statusLabel.setText("Mouse Pressed at (" + e.getX() + ", " + e.getY() + ")");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        statusLabel.setText("Mouse Released at (" + e.getX() + ", " + e.getY() + ")");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        statusLabel.setText("Mouse Entered the component");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        statusLabel.setText("Mouse Exited the component");
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        statusLabel.setText("Mouse Dragged to (" + e.getX() + ", " + e.getY() + ")");
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        statusLabel.setText("Mouse Moved to (" + e.getX() + ", " + e.getY() + ")");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            mouseMoment1 example = new mouseMoment1();
            example.setVisible(true);
        });
    }
}
