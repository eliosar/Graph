package eborho.kmGraph;

import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Main{

    private static InputGuiActionListener al;
    private static ArrayList<XYDataItem> vtdatas = new ArrayList<>();
    private static ArrayList<XYDataItem> stdatas = new ArrayList<>();

    public static void main() {
        new Main().start();
    }

    private void start() {
        GraphGui graphGui = new GraphGui();
        graphGui.setLocation(null);

        al = new InputGuiActionListener(graphGui);

        graphGui.setActionListener(al);
        graphGui.show();
    }

    //TODO maybe extract to separate class
    private static class InputGuiActionListener implements ActionListener {

        private InputGui inputGui = null;
        private final GraphGui stGraph;

        public InputGuiActionListener(GraphGui stGraph) {
            this.stGraph = stGraph;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            if(stGraph.isAddAction(e)){
                this.inputGui = new InputGui();
                inputGui.setActionListener(al);
                inputGui.show();
            }else if(inputGui.isAddAction(e)){
                if(inputGui.hasValidDataset()) {
                    inputGui.markInputValid();
                    XYDataItem dataItem = inputGui.getDataItem();
                    inputGui.Exit();
                    stGraph.addDataset(dataItem);
                    stdatas.add(dataItem);

                    int lastValue = stdatas.size() - 2;
                    float time;
                    float distance;

                    if (stdatas.size() == 1) {
                        lastValue += 1;
                    }

                    if (stdatas.size() == 1) {
                        distance = dataItem.getY().floatValue();
                        time = dataItem.getX().floatValue();
                    } else {
                        time = dataItem.getY().floatValue() - stdatas.get(lastValue).getY().floatValue();

                        if (dataItem.getX().floatValue() > stdatas.get(lastValue).getX().floatValue()) {
                            distance = dataItem.getX().floatValue() - stdatas.get(lastValue).getX().floatValue();
                        } else {
                            distance = -(stdatas.get(lastValue).getX().floatValue() - dataItem.getX().floatValue());
                        }
                    }

                    vtdatas.add(new XYDataItem(distance / time, dataItem.getY()));
                }else{
                    inputGui.markInputInvalid();
                }
            }else if(stGraph.isfinishAction(e)){
                stGraph.setLocation(new Point(999, 280));
                stGraph.closeButtons();
                stGraph.show();

                VtGraph vtGraph = new VtGraph(vtdatas);
                vtGraph.setLocation(new Point(333, 280));
                vtGraph.show();
            }
        }
    }
}