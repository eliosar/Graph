package eborho.kmGraph;

import org.jfree.data.xy.XYDataItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;

public class InputGui {

    private static final int X_AMOUNT = 110;
    private static final int X_TEXT = 10;
    private static final int Y_KM = 20;
    private static final int Y_TIME = 50;

    private final JTextField kmAmount = new JTextField();
    private final JTextField timeAmount = new JTextField();

    public final JFrame frame = new JFrame();

    public final JButton addButton = new JButton("add");

    public InputGui() {
        kmAmount.setBounds(X_AMOUNT, Y_KM, 80, 25);
        JLabel kmText = new JLabel("km");
        kmText.setBounds(X_TEXT, Y_KM, 160, 25);

        timeAmount.setBounds(X_AMOUNT, Y_TIME, 80, 25);
        JLabel timeText = new JLabel("time spend (h)");
        timeText.setBounds(X_TEXT, Y_TIME, 160, 25);

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setLayout(null);
        panel.add(kmAmount);
        panel.add(kmText);
        panel.add(timeAmount);
        panel.add(timeText);
        panel.setBackground(Color.GRAY);

        addButton.setBounds(30, 80, 122, 25);

        frame.setSize(350, 200);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        frame.add(addButton);
        frame.add(panel);
    }

    void setActionListener(ActionListener al) {
        addButton.addActionListener(al);
    }

    protected void show() {
        frame.setVisible(true);
    }

    public boolean isAddAction(ActionEvent e) {
        return e.getSource().equals(addButton);
    }

    public void close() {
        frame.dispose();
    }

    public boolean hasValidDataset() {
        if (!kmAmount.getText().isEmpty() && !timeAmount.getText().isEmpty()) {
            try {
                float km = Float.parseFloat(kmAmount.getText());
                float time = Float.parseFloat(timeAmount.getText());

                //valid input if
                return km >= 0 && time > 0;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
    }

    public XYDataItem getDataItem() {
        float km = Float.parseFloat(kmAmount.getText());
        float time = Float.parseFloat(timeAmount.getText());
        return new XYDataItem(km, time);
    }

    public void markInputInvalid() {
        kmAmount.setBackground(Color.RED);
        timeAmount.setBackground(Color.RED);
    }

    public void markInputValid() {
        kmAmount.setBackground(Color.WHITE);
        timeAmount.setBackground(Color.WHITE);
    }

    public void Exit(){
        frame.dispose();
    }
}