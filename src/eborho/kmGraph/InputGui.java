package eborho.kmGraph;

import org.jfree.data.xy.XYDataItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;

public class InputGui {

    private final JTextField xAmount = new JTextField();
    private final JTextField yAmount = new JTextField();

    public final JFrame frame = new JFrame();

    public final JButton addButton = new JButton("add");

    public InputGui(String xUnit, String yUnit) {
        xAmount.setBounds(110, 20, 80, 25);
        JLabel xText = new JLabel(xUnit);
        xText.setBounds(10, 20, 160, 25);

        yAmount.setBounds(110, 50, 80, 25);
        JLabel yText = new JLabel(yUnit);
        yText.setBounds(10, 50, 160, 25);

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setLayout(null);
        panel.add(yAmount);
        panel.add(yText);
        panel.add(xAmount);
        panel.add(xText);
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
        if (!yAmount.getText().isEmpty() && !xAmount.getText().isEmpty()) {
            try {
                float x = Float.parseFloat(xAmount.getText());
                float y = Float.parseFloat(yAmount.getText());

                //valid input if
                return y >= 0 && x > 0 && x > lastX;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
    }

    public boolean hasEmptyDataset(){
        return yAmount.getText().isEmpty() && xAmount.getText().isEmpty();
    }

    public XYDataItem getDataItem() {
        float x = Float.parseFloat(xAmount.getText());
        float y = Float.parseFloat(yAmount.getText());
        return new XYDataItem(x, y);
    }

    public void markInputInvalid(int lastX) {
        float y;
        float x;
        try{
            x = Float.parseFloat(xAmount.getText());

            if(x <= lastX){
                xAmount.setBackground(Color.RED);
            }else{
                xAmount.setBackground(Color.WHITE);
            }
        }catch (NumberFormatException e){
            xAmount.setBackground(Color.RED);
        }

        try{
            y = Float.parseFloat(yAmount.getText());

            if(y < 0){
                yAmount.setBackground(Color.RED);
            }else{
                yAmount.setBackground(Color.WHITE);
            }
        }catch (NumberFormatException e){
            yAmount.setBackground(Color.RED);
        }
    }

    public void Exit(){
        frame.dispose();
    }
}