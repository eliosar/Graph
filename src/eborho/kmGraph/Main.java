package eborho.kmGraph;

import org.jfree.data.xy.XYDataItem;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Main{

    private static generalActionListener al;

    public static void main(String args[]) {
        new Main().start();
    }

    private void start() {
        Person newPerson = new Person();
        newPerson.setColor(Color.CYAN);
        newPerson.setLineNumber(0);
        newPerson.addStDatawithoutGraph(new XYDataItem(0, 0));
        GraphGui graphGui = new GraphGui();
        graphGui.setLocation(null);

        al = new generalActionListener(graphGui, newPerson);

        graphGui.setActionListener(al);
        graphGui.show();
    }

    //TODO maybe extract to separate class
    private static class generalActionListener implements ActionListener {

        private InputGui inputGui = null;
        private NewPersonGui newPersonGui = null;
        private ChoosePersonGui choosePersonGui = null;
        private Person currentPerson;
        private final GraphGui stGraph;
        private final ArrayList<Person> allpersons = new ArrayList<>();

        public generalActionListener(GraphGui stGraph, Person person) {
            this.stGraph = stGraph;
            this.allpersons.add(person);
            currentPerson = person;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (inputGui != null && inputGui.isAddAction(e)) {
                if (inputGui.hasValidDataset(currentPerson.getStData().get(currentPerson.getStData().size() - 1).getX().intValue())) {
                    XYDataItem dataItem = inputGui.getDataItem();
                    currentPerson.addStData(dataItem, stGraph);
                    inputGui.Exit();
                    stGraph.enableButtons();

                    int lastValue = currentPerson.getStData().size() - 2;
                    float time;
                    float distance;

                    if (currentPerson.getStData().size() == 2) {
                        lastValue += 1;
                    }

                    XYDataItem laststdata = currentPerson.getStData().get(lastValue);

                    if (currentPerson.getStData().size() == 2) {
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
                    currentPerson.getVtData().add(new XYDataItem(dataItem.getX(), distance / time));
                } else {
                    inputGui.markInputInvalid();
                }
            }

            if (newPersonGui != null && newPersonGui.isAddAction(e)) {
                Person newperson = new Person();
                newperson.addStDatawithoutGraph(new XYDataItem(0, 0));
                newperson.setLineNumber(allpersons.size());
                newperson.setColor(newPersonGui.getColor());
                currentPerson = newperson;
                allpersons.add(newperson);
                newPersonGui.close();
                stGraph.addLine(newperson);
                stGraph.enableButtons();
            }

            if(choosePersonGui != null && choosePersonGui.ischoosePerson(e)){
                currentPerson = choosePersonGui.getchoosedPerson();
                choosePersonGui.close();
                stGraph.enableButtons();
            }

            if(stGraph.ischoosePersonAction(e)){
                choosePersonGui = new ChoosePersonGui(allpersons);
                choosePersonGui.setActionListener(al);
                choosePersonGui.show();
                stGraph.disableButtons();
            }

            if (stGraph.isAddPersonAction(e)) {
                newPersonGui = new NewPersonGui();
                newPersonGui.setActionListener(al);
                newPersonGui.show();
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

                VtGraph vtGraph = new VtGraph(allpersons);
                vtGraph.setLocation(new Point(333, 280));
                vtGraph.show();
            }
        }
    }
}