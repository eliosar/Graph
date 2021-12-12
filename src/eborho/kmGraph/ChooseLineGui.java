package eborho.kmGraph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;

public class ChooseLineGui {
    private final JFrame frame = new JFrame();
    private Line choosedLine;
    private final ArrayList<Integer> allNumbers = new ArrayList<>();

    private final ArrayList<JButton> allLinesButtons = new ArrayList<>();
    private final ArrayList<Line> allLines;

    public ChooseLineGui(ArrayList<Line> allLines){
        this.allLines = allLines;

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setBackground(Color.GRAY);

        for (Line currentLine : allLines){
            JButton newLineButton = new JButton(currentLine.getName());
            newLineButton.setBackground(currentLine.getColor());
            allNumbers.add(currentLine.getNumber());
            newLineButton.setSize(30, 40);
            allLinesButtons.add(newLineButton);
            panel.add(newLineButton);
        }

        frame.setSize(350, 200);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        frame.add(panel);
    }

    public void setActionListener(ActionListener al){
        for(JButton currentLine : allLinesButtons){
            currentLine.addActionListener(al);
        }
    }

    public boolean ischooseLine(ActionEvent e){
        boolean is = false;
        for (int i = 0; i < allLinesButtons.size(); i++){
            JButton currentLineButton = allLinesButtons.get(i);
            if(e.getSource().equals(currentLineButton)){
                is = true;

                for (Line currentLine : allLines) {
                    int currentLineNumber = allNumbers.get(i);

                    if(currentLineNumber == currentLine.getNumber()) {
                        this.choosedLine = currentLine;
                        break;
                    }
                }
                break;
            }
        }
        return is;
    }

    public Line getchoosedLine(){
        return choosedLine;
    }

    public void show(){
        frame.setVisible(true);
    }
    public void close(){
        frame.dispose();
    }
}