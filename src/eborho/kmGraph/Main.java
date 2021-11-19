package eborho.kmGraph;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends GUI {

    private static Graph StGraph;

    public static void main(String args[]) {
        GUI gui = new GUI();

        MyActionListener al = new MyActionListener(gui);

        gui.setActionListener(al);
    }

    static class MyActionListener implements ActionListener {
        private final GUI gui;

        public MyActionListener(GUI gui) {
            this.gui = gui;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(StGraph != null) {
                StGraph.frame.dispose();
            }

            if(e.getSource() == gui.addButton){
                StGraph = new Graph(gui.allkm, gui.alltimes, new Point(999, 280));
                gui.frame.setVisible(true);
            }
            if(e.getSource() == gui.finishButton){
                gui.frame.dispose();

                new Graph(gui.allkm, gui.alltimes, new Point(986, 280));
                new VtGraph(gui.allspeed, gui.alltimes, new Point(333, 280));
            }
        }
    }
}