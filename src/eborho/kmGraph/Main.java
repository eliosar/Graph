package eborho.kmGraph;

import org.jfree.data.xy.XYDataItem;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Main{

    private static generalActionListener al;

    public static void main(String[] args) {
        new Main().start();
    }

    private void start() {
        Line newLine = new Line();
        newLine.setColor(Color.CYAN);
        newLine.setNumber(0);
        newLine.setName(" ");
        newLine.addStDatawithoutGraph(new XYDataItem(0, 0));
        GraphGui graphGui = new GraphGui();
        graphGui.setLocation(null);

        al = new generalActionListener(graphGui, newLine);

        graphGui.setActionListener(al);
        graphGui.show();
    }

    //TODO maybe extract to separate class
    private static class generalActionListener implements ActionListener {

        private InputGui inputGui = null;
        private NewLineGui newLineGui = null;
        private ChooseLineGui chooseLineGui = null;
        private Line currentLine;
        private final GraphGui stGraph;
        private final ArrayList<Line> allLines = new ArrayList<>();

        public generalActionListener(GraphGui stGraph, Line line) {
            this.stGraph = stGraph;
            allLines.add(line);
            currentLine = line;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (inputGui != null && inputGui.isAddAction(e)) {
                if (inputGui.hasValidDataset(currentLine.getStData().get(currentLine.getStData().size() - 1).getX().intValue())) {
                    XYDataItem dataItem = inputGui.getDataItem();
                    currentLine.addStData(dataItem, stGraph);
                    inputGui.Exit();
                    stGraph.enableButtons();

                    int lastValue = currentLine.getStData().size() - 2;
                    float time;
                    float distance;

                    if (currentLine.getStData().size() == 2) {
                        lastValue += 1;
                    }

                    XYDataItem laststdata = currentLine.getStData().get(lastValue);

                    if (currentLine.getStData().size() == 2) {
                        distance = dataItem.getY().floatValue();
                        time = dataItem.getX().floatValue();
                    } else {
                        time = dataItem.getY().floatValue() - laststdata.getY().floatValue();

                        if (dataItem.getX().floatValue() > laststdata.getX().floatValue()) {
                            distance = dataItem.getX().floatValue() - laststdata.getX().floatValue();
                        } else {
                            distance = -(laststdata.getX().floatValue() - dataItem.getX().floatValue());
                        }
                    }
                    currentLine.getVtData().add(new XYDataItem(dataItem.getX(), distance / time));
                } else {
                    inputGui.markInputInvalid();
                }
            }

            if (newLineGui != null && newLineGui.isAddAction(e)) {
                Line newline = new Line();
                newline.addStDatawithoutGraph(new XYDataItem(0, 0));
                newline.setNumber(allLines.size());
                newline.setColor(newLineGui.getColor());
                newline.setName(newLineGui.getName());
                currentLine = newline;
                allLines.add(newline);
                newLineGui.close();
                stGraph.addLine(newline);
                stGraph.enableButtons();
            }

            if(chooseLineGui != null && chooseLineGui.ischooseLine(e)){
                currentLine = chooseLineGui.getchoosedLine();
                chooseLineGui.close();
                stGraph.enableButtons();
            }

            if(stGraph.ischooseLineAction(e)){
                chooseLineGui = new ChooseLineGui(allLines);
                chooseLineGui.setActionListener(al);
                chooseLineGui.show();
                stGraph.disableButtons();
            }

            if (stGraph.isAddLineAction(e)) {
                newLineGui = new NewLineGui();
                newLineGui.setActionListener(al);
                newLineGui.show();
                stGraph.disableButtons();
            }

            if (stGraph.isAddAction(e)) {
                inputGui = new InputGui();
                inputGui.setActionListener(al);
                inputGui.show();
                stGraph.disableButtons();
            }

            if (stGraph.isfinishAction(e)) {
                stGraph.setLocation(new Point(999, 280));
                stGraph.closeButtons();

                VtGraph vtGraph = new VtGraph(allLines);
                vtGraph.setLocation(new Point(333, 280));
                vtGraph.show();
            }
        }
    }
}