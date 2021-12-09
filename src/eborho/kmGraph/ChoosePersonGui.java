package eborho.kmGraph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;

public class ChoosePersonGui {
    private final JFrame frame = new JFrame();
    private Person choosedPerson;

    private final ArrayList<JButton> allpersonsButtons = new ArrayList<>();
    private final ArrayList<Person> allpersons;

    public ChoosePersonGui(ArrayList<Person> allpersons){
        this.allpersons = allpersons;

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setBackground(Color.GRAY);

        for (Person currentperson : allpersons){
            JButton newPersonButton = new JButton((currentperson.getLineNumber() + 1) + "");
            allpersonsButtons.add(newPersonButton);
            panel.add(newPersonButton);
        }

        frame.setSize(350, 200);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        frame.add(panel);
    }

    public void setActionListener(ActionListener al){
        for(JButton currentperson : allpersonsButtons){
            currentperson.addActionListener(al);
        }
    }

    public boolean ischoosePerson(ActionEvent e){
        boolean is = false;
        for (JButton currentPersonButton : allpersonsButtons){
            if(e.getSource().equals(currentPersonButton)){
                is = true;

                for (Person currentPerson : allpersons) {
                    int currentPersonNumber = Integer.parseInt(currentPersonButton.getText());

                    if(currentPersonNumber - 1 == currentPerson.getLineNumber()) {
                        this.choosedPerson = currentPerson;
                        break;
                    }
                }
                break;
            }
        }
        return is;
    }

    public Person getchoosedPerson(){
        return choosedPerson;
    }

    public void show(){
        frame.setVisible(true);
    }
    public void close(){
        frame.dispose();
    }
}