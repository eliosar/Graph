package eborho.kmGraph;

import org.jfree.data.xy.XYDataItem;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends InputGui {

    public static void main(String args[]) {
        new Main().start();
    }

    private void start() {
        InputGui inputGui = new InputGui();
        GraphGui graphGui = new GraphGui( new Point(999, 280));

        InputGuiActionListener al = new InputGuiActionListener(inputGui, graphGui);
        inputGui.setActionListener(al);

        inputGui.show();
        graphGui.show();
    }

    //TODO maybe extract to separate class
    private static class InputGuiActionListener implements ActionListener {

        private final InputGui inputGui;
        private final GraphGui stGraph;

        public InputGuiActionListener(InputGui inputGui, GraphGui stGraph) {
            this.inputGui = inputGui;
            this.stGraph = stGraph;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            if(inputGui.isExitAction(e)){
                inputGui.close();
                stGraph.close();
            }
            if(inputGui.isAddAction(e)){

                if(inputGui.hasValidDataset()){
                    inputGui.markInputValid();
                    XYDataItem dataItem = inputGui.getDataItem();
                    stGraph.addDataset(dataItem);
                }else{
                    inputGui.markInputInvalid();
                }

            }

        }
    }
}