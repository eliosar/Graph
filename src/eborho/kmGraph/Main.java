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
        private final GraphGui stGraph;
        private ArrayList<Person> allpersons = new ArrayList<>();

        public generalActionListener(GraphGui stGraph, Person person) {
            this.stGraph = stGraph;
            this.allpersons.add(person);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Person currentPerson = allpersons.get(allpersons.size() - 1);

            if(stGraph.isAddAction(e)){
                this.inputGui = new InputGui();
                inputGui.setActionListener(al);
                inputGui.show();
            }else {
                if (inputGui != null && inputGui.isAddAction(e)) {
                    if (inputGui.hasValidDataset()) {
                        XYDataItem dataItem = inputGui.getDataItem();
                        currentPerson.addStData(dataItem, stGraph);
                        inputGui.Exit();

                        int lastValue = currentPerson.getStData().size() - 2;
                        float time;
                        float distance;

                        if (currentPerson.getStData().size() == 1) {
                            lastValue += 1;
                        }

                        XYDataItem laststdata = currentPerson.getStData().get(lastValue);

                        if (currentPerson.getStData().size() == 1) {
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
                        currentPerson.getVtData().add(new XYDataItem(dataItem.getY(), distance / time));
                    } else {
                        inputGui.markInputInvalid();
                    }
                }

                if (newPersonGui != null && newPersonGui.isAddAction(e)) {
                    allpersons.add(new Person());
                    int current = allpersons.size() - 1;
                    allpersons.get(current).setLineNumber(current);
                    allpersons.get(current).setColor(newPersonGui.getColor());
                    newPersonGui.close();
                    stGraph.addLine(allpersons.get(current));
                    stGraph.setPersons(allpersons);
                }

                if (stGraph.isAddPersonAction(e)) {
                    newPersonGui = new NewPersonGui();
                    newPersonGui.show();
                    newPersonGui.setActionListener(al);
                }

                if(stGraph.isfinishAction(e)) {
                    stGraph.setLocation(new Point(999, 280));
                    stGraph.closeButtons();
                    stGraph.show();

                    VtGraph vtGraph = new VtGraph(allpersons);
                    vtGraph.setLocation(new Point(333, 280));
                    vtGraph.show();
                }
            }
        }
    }
}