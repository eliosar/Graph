package eborho.kmGraph;

import org.jfree.data.xy.XYDataItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;

public class InputGui {

    private final JTextField kmAmount = new JTextField();
    private final JTextField timeAmount = new JTextField();

    public final JFrame frame = new JFrame();

    public final JButton addButton = new JButton("add");

    public InputGui() {
        kmAmount.setBounds(110, 20, 80, 25);
        JLabel kmText = new JLabel("km");
        kmText.setBounds(10, 20, 160, 25);

        timeAmount.setBounds(110, 50, 80, 25);
        JLabel timeText = new JLabel("time spend (h)");
        timeText.setBounds(10, 50, 160, 25);

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

    public boolean hasValidDataset(int lastX) {
        if (!kmAmount.getText().isEmpty() && !timeAmount.getText().isEmpty()) {
            try {
                float km = Float.parseFloat(kmAmount.getText());
                float time = Float.parseFloat(timeAmount.getText());

                //valid input if
                return km >= 0 && time > 0 && time > lastX;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
    }

    public boolean hasEmptyDataset(){
        return kmAmount.getText().isEmpty() && timeAmount.getText().isEmpty();
    }

    public XYDataItem getDataItem() {
        float km = Float.parseFloat(kmAmount.getText());
        float time = Float.parseFloat(timeAmount.getText());
        return new XYDataItem(time, km);
    }

    public void markInputInvalid() {
        kmAmount.setBackground(Color.RED);
        timeAmount.setBackground(Color.RED);
    }

    public void Exit(){
        frame.dispose();
    }
}