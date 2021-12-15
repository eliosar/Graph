package eborho.kmGraph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;

public class ChooseOrChangeGui {

    private final JButton chooseButton = new JButton("choose");
    private final JButton changeButton = new JButton("change");

    private final JFrame frame = new JFrame();

    public ChooseOrChangeGui(){
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.add(chooseButton);
        panel.add(changeButton);
        panel.setBackground(Color.GRAY);

        frame.setSize(350, 200);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        frame.add(panel);
    }

    public void setActionListener(ActionListener al){
        changeButton.addActionListener(al);
        chooseButton.addActionListener(al);
    }

    public boolean isChooseAction(ActionEvent e){
        return e.getSource().equals(chooseButton);
    }
    public boolean isChangeAction(ActionEvent e){
        return  e.getSource().equals(changeButton);
    }

    public void show(){
        frame.setVisible(true);
    }
    public void close(){
        frame.dispose();
    }
}
