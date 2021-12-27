package eborho.kmGraph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;

public class ChangeUnitGui {

    private final JFrame frame = new JFrame();
    private final JButton changeButton = new JButton("change");

    private final JTextField xUnitsAmount = new JTextField();
    private final JTextField yUnitsAmount = new JTextField();

    public ChangeUnitGui(String xUnits, String yUnits){
        xUnitsAmount.setBounds(110, 20, 80, 25);
        xUnitsAmount.setText(xUnits);
        JLabel xUnitText = new JLabel("xUnit");
        xUnitText.setBounds(10, 20, 160, 25);
        yUnitsAmount.setBounds(110, 50, 80, 25);
        yUnitsAmount.setText(yUnits);
        JLabel yUnitText = new JLabel("yUnit");
        yUnitText.setBounds(10, 50, 160, 25);

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setLayout(null);
        panel.add(xUnitsAmount);
        panel.add(yUnitsAmount);
        panel.add(xUnitText);
        panel.add(yUnitText);
        panel.setBackground(Color.GRAY);

        changeButton.setBounds(30, 80, 122, 25);

        frame.setSize(350, 200);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        frame.add(changeButton);
        frame.add(panel);
    }

    public String getXUnit(){
        return xUnitsAmount.getText();
    }
    public String getYUnit(){
        return yUnitsAmount.getText();
    }

    public boolean hasValidInput(){
        return !xUnitsAmount.getText().isEmpty() && !yUnitsAmount.getText().isEmpty();
    }

    public void markInputInvalid(){
        if(xUnitsAmount.getText().isEmpty()) {
            xUnitsAmount.setBackground(Color.RED);
        }

        if(yUnitsAmount.getText().isEmpty()) {
            yUnitsAmount.setBackground(Color.RED);
        }
    }

    public boolean isChangeAction(ActionEvent e){
        return e.getSource().equals(changeButton);
    }

    public void setListener(ActionListener al){
        changeButton.addActionListener(al);
    }

    public void show(){
        frame.setVisible(true);
    }
    public void close(){
        frame.dispose();
    }
}
