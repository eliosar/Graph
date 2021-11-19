package eborho.kmGraph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GUI implements ActionListener {
    private int xAmount = 110;
    private int xText = 10;
    private int ykm = 20;
    private int ytime = 50;

    public ArrayList<Float> allspeed = new ArrayList<Float>();
    public ArrayList<Float> allkm = new ArrayList<Float>();
    public ArrayList<Float> alltimes = new ArrayList<Float>();

    private JTextField kmAmount = new JTextField();
    private JLabel kmText = new JLabel("km");

    private JTextField timeAmount = new JTextField();
    private JLabel timeText = new JLabel("time spend (h)");

    public JFrame frame = new JFrame();
    private JPanel panel = new JPanel();

    public JButton addButton = new JButton("new");
    public JButton finishButton = new JButton("finished");
    private ActionListener al;

    public GUI(){
        kmAmount.setBounds(xAmount, ykm, 80, 25);
        kmText.setBounds(xText, ykm, 160, 25);

        timeAmount.setBounds(xAmount, ytime, 80, 25);
        timeText.setBounds(xText, ytime, 160, 25);

        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setLayout(null);
        panel.add(kmAmount);
        panel.add(kmText);
        panel.add(timeAmount);
        panel.add(timeText);
        panel.setBackground(Color.GRAY);

        addButton.addActionListener(this);
        addButton.setBounds(30, 80, 122, 25);

        finishButton.addActionListener(this);
        finishButton.setBounds(30, 110, 122, 25);

        frame.setSize(350, 200);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(addButton);
        frame.add(finishButton);
        frame.add(panel);

        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean anynull = false;
        boolean allnull = false;
        boolean error = false;

        kmAmount.setBackground(Color.WHITE);
        timeAmount.setBackground(Color.WHITE);

        if (!kmAmount.getText().isEmpty() && !timeAmount.getText().isEmpty()) {
            float km = Float.parseFloat(kmAmount.getText());
            float time = Float.parseFloat(timeAmount.getText());

            if(km < 0){
                error = true;
                kmAmount.setBackground(Color.RED);
            }

            if (time <= 0) {
                error = true;
                timeAmount.setBackground(Color.RED);
            } else {
                for (float currenttime : alltimes) {
                    if (time <= currenttime) {
                        error = true;
                        timeAmount.setBackground(Color.RED);
                        break;
                    }
                }
            }

            if(!error) {
                allkm.add(km);
                alltimes.add(time);
            }
        } else {
            if(kmAmount.getText().isEmpty()){
                kmAmount.setBackground(Color.RED);
            }
            if(timeAmount.getText().isEmpty()){
                timeAmount.setBackground(Color.RED);
            }
            if (kmAmount.getText().isEmpty() && timeAmount.getText().isEmpty()) {
                allnull = true;
            }
            if (kmAmount.getText().isEmpty() || timeAmount.getText().isEmpty()) {
                anynull = true;
            }
        }

        if(!error) {
            if (e.getSource().equals(addButton)) {
                if (!anynull) {
                    frame.setVisible(false);

                    frame.remove(panel);

                    kmAmount.setText(null);
                    timeAmount.setText(null);

                    frame.add(panel);

                    frame.setVisible(true);

                    al.actionPerformed(e);
                }
            }

            if (e.getSource().equals(finishButton)) {
                if (((!allnull && !anynull) || allnull) && allkm.size() > 0) {
                    for (int i = 0; i < allkm.size(); i++) {
                        float currentkm = allkm.get(i);
                        float speed;

                        if(i == 0){
                            speed = currentkm / alltimes.get(i);
                        }else {
                            if(currentkm < allkm.get(i - 1)){
                               speed = -((allkm.get(i - 1) - currentkm) / (alltimes.get(i) - alltimes.get(i - 1)));
                            }else{
                                speed = (currentkm - allkm.get(i - 1)) / (alltimes.get(i) - alltimes.get(i - 1));
                            }
                        }
                        allspeed.add(speed);
                    }
                    al.actionPerformed(e);
                }
            }
        }
    }

    void setActionListener(ActionListener al) {
        this.al = al;
    }
}