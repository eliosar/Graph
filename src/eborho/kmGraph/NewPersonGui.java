package eborho.kmGraph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;

public class NewPersonGui {

    private static Color color;

    private JButton finishButton = new JButton("finish");
    private JButton ColorButton = new JButton("choose Color");
    private JFrame frame = new JFrame();

    private generalActionListener action;

    public NewPersonGui(){
        action = new generalActionListener(ColorButton);

        ColorButton.setBounds(10, 20, 122, 25);
        ColorButton.addActionListener(action);

        finishButton.setBounds(10, 50, 122, 25);

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.add(finishButton);
        panel.add(ColorButton);
        panel.setBackground(Color.GRAY);

        frame.setSize(350, 200);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        frame.add(panel);
    }

    public void setActionListener(ActionListener al){
        finishButton.addActionListener(al);
    }

    public boolean isAddAction(ActionEvent e) {
        return e.getSource().equals(finishButton);
    }

    public Color getColor(){
        return color;
    }

    public void show(){
        frame.setVisible(true);
    }

    public void close(){
        frame.dispose();
    }

    private static class generalActionListener implements ActionListener {

        private JButton colorButton;

        generalActionListener(JButton colorButton){
            this.colorButton = colorButton;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource().equals(colorButton)){
                color = JColorChooser.showDialog(null, "Color choose", null);
            }
        }
    }
}