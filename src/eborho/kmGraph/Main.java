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
        private boolean ischangingLine = false;
        private Line currentLine;
        private Line choosedLine;
        private ChooseOrChangeGui chooseOrChangeGui = null;
        private final GraphGui stGraph;
        private final ArrayList<Line> allLines = new ArrayList<>();

        public generalActionListener(GraphGui stGraph, Line line) {
            this.stGraph = stGraph;
            allLines.add(line);
            currentLine = line;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // new Data
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

            // new Line or changing Line finished
            if (newLineGui != null && newLineGui.isAddAction(e)) {
                choosedLine.setColor(newLineGui.getColor());
                choosedLine.setName(newLineGui.getName());

                if(ischangingLine){
                    for(int i = 0; i < allLines.size(); i++){
                        if(allLines.get(i).getNumber() == choosedLine.getNumber()){
                            allLines.set(i, choosedLine);
                            break;
                        }
                    }
                    ischangingLine = false;
                }else{
                    choosedLine.addStDatawithoutGraph(new XYDataItem(0, 0));
                    choosedLine.setNumber(allLines.size());
                    allLines.add(choosedLine);
                    stGraph.addLine(choosedLine);
                }
                setcurrentLine(choosedLine);

                newLineGui.close();
                stGraph.enableButtons();
            }

            // selected Line = current Line
            if(chooseOrChangeGui != null && chooseOrChangeGui.isChooseAction(e)){
                stGraph.enableButtons();
                setcurrentLine(choosedLine);
                chooseOrChangeGui.close();
            }

            // selected Line changing
            if(chooseOrChangeGui != null && chooseOrChangeGui.isChangeAction(e)){
                ischangingLine = true;
                newLineGui = new NewLineGui();
                newLineGui.setActionListener(al);
                newLineGui.show();
                chooseOrChangeGui.close();
            }

            // new Line
            if(chooseLineGui != null && chooseLineGui.isAddAction(e)){
                choosedLine = new Line();
                chooseLineGui.close();

                newLineGui = new NewLineGui();
                newLineGui.setActionListener(al);
                newLineGui.show();
            }

            // selecting Line
            if(chooseLineGui != null && chooseLineGui.ischooseLine(e)){
                choosedLine = chooseLineGui.getchoosedLine();
                chooseLineGui.close();

                chooseOrChangeGui = new ChooseOrChangeGui();
                chooseOrChangeGui.setActionListener(al);
                chooseOrChangeGui.show();
            }

            // choose Line Gui
            if(stGraph.ischooseLineAction(e)){
                chooseLineGui = new ChooseLineGui(allLines);
                chooseLineGui.setActionListener(al);
                chooseLineGui.show();
                stGraph.disableButtons();
            }

            // Data gui
            if (stGraph.isAddAction(e)) {
                inputGui = new InputGui();
                inputGui.setActionListener(al);
                inputGui.show();
                stGraph.disableButtons();
            }

            // finished
            if (stGraph.isfinishAction(e)) {
                stGraph.setLocation(new Point(999, 280));
                stGraph.closeButtons();

                VtGraph vtGraph = new VtGraph(allLines);
                vtGraph.setLocation(new Point(333, 280));
                vtGraph.show();
            }
        }

        private void setcurrentLine(Line currentLine){
            this.currentLine = currentLine;
            stGraph.setCurrentLine(currentLine);
        }
    }
}