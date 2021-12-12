package eborho.kmGraph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;

public class NewLineGui {

    private static Color color;
    private static String Name;

    private final JButton finishButton = new JButton("finish");
    private final JFrame frame = new JFrame();

    public NewLineGui(){
        JButton colorButton = new JButton("choose Color");
        JButton nameButton = new JButton("choose Name");
        generalActionListener action = new generalActionListener(colorButton, nameButton);

        colorButton.setBounds(10, 20, 122, 25);
        colorButton.addActionListener(action);

        nameButton.setBounds(10, 20, 122, 25);
        nameButton.addActionListener(action);

        finishButton.setBounds(10, 50, 122, 25);

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.add(finishButton);
        panel.add(colorButton);
        panel.add(nameButton);
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

    public String getName(){
        return Name;
    }

    public void show(){
        frame.setVisible(true);
    }

    public void close(){
        frame.dispose();
    }

    private static class generalActionListener implements ActionListener {

        private final JButton ColorButton;
        private final JButton NameButton;
        private final JButton NameAddButton = new JButton();
        private final JTextField NameField = new JTextField();
        private final JFrame Nameframe = new JFrame();

        generalActionListener(JButton ColorButton, JButton NameButton){
            this.ColorButton = ColorButton;
            this.NameButton = NameButton;
            NameAddButton.setText("add");
            NameAddButton.addActionListener(this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource().equals(ColorButton)){
                color = JColorChooser.showDialog(null, "Color choose", null);
            }

            if(e.getSource().equals(NameButton)){
                JLabel NameText = new JLabel("Name");

                NameText.setBounds(10, 20, 160, 25);
                NameField.setBounds(110, 20, 80, 25);
                NameAddButton.setBounds(30, 60, 122, 25);

                JPanel panel = new JPanel();
                panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
                panel.setLayout(null);
                panel.add(NameField);
                panel.add(NameText);
                panel.setBackground(Color.GRAY);

                Nameframe.setSize(350, 200);
                Nameframe.setLocationRelativeTo(null);
                Nameframe.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                Nameframe.add(NameAddButton);
                Nameframe.add(panel);
                Nameframe.setVisible(true);
            }

            if(e.getSource().equals(NameAddButton)){
                Name = NameField.getText();
                Nameframe.setVisible(false);
            }
        }
    }
}