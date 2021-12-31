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
        newLine.addGeneralDatawithoutGraph(new XYDataItem(0, 0));
        Graph graph = new Graph();
        graph.setLocation(null);

        al = new generalActionListener(graph, newLine);

        graph.setActionListener(al);
        graph.show();
    }

    //TODO maybe extract to separate class
    private static class generalActionListener implements ActionListener {

        private String xUnit = "h";
        private String yUnit = "km";
        private InputGui inputGui = null;
        private NewLineGui newLineGui = null;
        private ChooseLineGui chooseLineGui = null;
        private ChangeUnitGui changeUnitGui = null;
        private boolean ischangingLine = false;
        private Line currentLine;
        private Line choosedLine;
        private ChooseOrChangeGui chooseOrChangeGui = null;
        private final Graph graph;
        private final ArrayList<Line> allLines = new ArrayList<>();

        public generalActionListener(Graph graph, Line line) {
            this.graph = graph;
            allLines.add(line);
            currentLine = line;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // new Data
            if (inputGui != null && inputGui.isAddAction(e)) {
                // if entered Data is correct
                if (inputGui.hasValidDataset(currentLine.getGeneralData().get(currentLine.getGeneralData().size() - 1).getX().intValue())) {
                    XYDataItem dataItem = inputGui.getDataItem();
                    currentLine.addStData(dataItem, graph);
                    inputGui.Exit();
                    graph.enableButtons();

                    int lastValue = currentLine.getGeneralData().size() - 2;
                    float time;
                    float distance;

                    if (currentLine.getGeneralData().size() == 2) {
                        lastValue += 1;
                    }

                    XYDataItem laststdata = currentLine.getGeneralData().get(lastValue);

                    if (currentLine.getGeneralData().size() == 2) {
                        distance = dataItem.getY().floatValue();
                        time = dataItem.getX().floatValue();
                    } else {
                        time = dataItem.getX().floatValue() - laststdata.getX().floatValue();

                        if (dataItem.getY().floatValue() > laststdata.getY().floatValue()) {
                            distance = dataItem.getY().floatValue() - laststdata.getY().floatValue();
                        } else {
                            distance = -(laststdata.getY().floatValue() - dataItem.getY().floatValue());
                        }
                    }
                    currentLine.getAverageData().add(new XYDataItem(dataItem.getX(), distance / time));
                } else { // if entered input is not correct
                    if(inputGui.hasEmptyDataset()){
                        inputGui.close();
                        graph.enableButtons();
                    }else {
                        inputGui.markInputInvalid(currentLine.getGeneralData().get(currentLine.getGeneralData().size() - 1).getX().intValue());
                    }
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
                    choosedLine.addGeneralDatawithoutGraph(new XYDataItem(0, 0));
                    choosedLine.setNumber(allLines.size());
                    allLines.add(choosedLine);
                    graph.addLine(choosedLine);
                }
                setcurrentLine(choosedLine);

                newLineGui.close();
                graph.enableButtons();
            }

            // selected Line = current Line
            if(chooseOrChangeGui != null && chooseOrChangeGui.isChooseAction(e)){
                graph.enableButtons();
                setcurrentLine(choosedLine);
                chooseOrChangeGui.close();
            }

            // selected Line changing
            if(chooseOrChangeGui != null && chooseOrChangeGui.isChangeAction(e)){
                ischangingLine = true;
                newLineGui = new NewLineGui();
                newLineGui.setActionListener(al);
                newLineGui.show();
                newLineGui.setName(choosedLine.getName());
                newLineGui.setColor(choosedLine.getColor());
                chooseOrChangeGui.close();
            }

            // deleting choosed Line
            if(chooseOrChangeGui != null && chooseOrChangeGui.isDeleteAction(e)){
                graph.enableButtons();
                graph.removeLine(currentLine.getNumber());
                allLines.remove(allLines.size() - 1);
                setcurrentLine(allLines.get(allLines.size() - 1));
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
            if(chooseLineGui != null && chooseLineGui.isChooseLineAction(e)){
                choosedLine = chooseLineGui.getchoosedLine();
                chooseLineGui.close();

                chooseOrChangeGui = new ChooseOrChangeGui();
                chooseOrChangeGui.setActionListener(al);
                chooseOrChangeGui.show();
            }

            if(changeUnitGui != null && changeUnitGui.isChangeAction(e)){
                if(changeUnitGui.hasValidInput()) {
                    xUnit = changeUnitGui.getXUnit();
                    yUnit = changeUnitGui.getYUnit();
                    changeUnitGui.close();
                    graph.enableButtons();
                    graph.changeUnit(xUnit, yUnit);
                }else{
                    changeUnitGui.markInputInvalid();
                }
            }

            // changing Units
            if(graph.isChangeUnitsAction(e)){
                changeUnitGui = new ChangeUnitGui(xUnit, yUnit);
                changeUnitGui.setListener(al);
                changeUnitGui.show();
                graph.disableButtons();
            }

            // choose Line Gui
            if(graph.isChooseLineAction(e)){
                chooseLineGui = new ChooseLineGui(allLines);
                chooseLineGui.setActionListener(al);
                chooseLineGui.show();
                graph.disableButtons();
            }

            // Data gui
            if (graph.isAddAction(e)) {
                inputGui = new InputGui(xUnit, yUnit);
                inputGui.setActionListener(al);
                inputGui.show();
                graph.disableButtons();
            }

            // finished
            if (graph.isFinishAction(e)) {
                graph.setLocation(new Point(999, 280));
                graph.closeButtons();

                AverageGraph averageGraph = new AverageGraph(allLines, xUnit, yUnit);
                averageGraph.setLocation(new Point(333, 280));
                averageGraph.show();
            }
        }

        private void setcurrentLine(Line currentLine){
            this.currentLine = currentLine;
            graph.setCurrentLine(currentLine);
        }
    }
}