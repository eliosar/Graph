package eborho.kmGraph;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GraphGui {

    private static final int WIDTH = 640;
    private static final int HEIGHT = 480;

    private XYSeriesCollection dataset = new XYSeriesCollection();

    private ArrayList<XYSeries> Lines = new ArrayList<>();

    private ArrayList<Person> allpersons = new ArrayList<>();

    private final JFrame frame = new JFrame();
    private final JButton addButton = new JButton("add");
    private final JButton finishButton = new JButton("finish");
    private final JButton addPersonButton = new JButton("add Person");
    private final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

    public GraphGui() {
        Lines.add(new XYSeries("line", false));
        addButton.setBounds(10, 415, 90, 20);
        finishButton.setBounds(110, 415, 90, 20);
        addPersonButton.setBounds( 210, 415, 100, 20);
        JPanel chartPanel = createChartPanel();
        frame.add(addButton);
        frame.add(finishButton);
        frame.add(addPersonButton);
        frame.add(chartPanel, BorderLayout.CENTER);

        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private JPanel createChartPanel() {
        XYDataset dataset = createDataset();

        JFreeChart chart = ChartFactory.createXYLineChart("stGraph", "time [h]", "distance [km]", dataset, PlotOrientation.VERTICAL, false, true, true);
        chart.setBackgroundPaint(Color.BLACK);
        chart.getTitle().setPaint(Color.CYAN);

        XYPlot plot = chart.getXYPlot();

        renderer.setSeriesPaint(0, Color.CYAN);

        plot.setRenderer(renderer);
        plot.setOutlinePaint(Color.BLACK);
        plot.setOutlineStroke(new BasicStroke(2.0f));
        plot.setBackgroundPaint(Color.DARK_GRAY);
        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.BLACK);

        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.BLACK);

        return new ChartPanel(chart);
    }

    private XYDataset createDataset() {
        Lines.get(Lines.size() - 1).add(0,0);
        dataset.addSeries(Lines.get(Lines.size() - 1));
        return dataset;
    }

    public void show() {
        frame.setVisible(true);
    }

    public void close() {
        frame.dispose();
    }

    public void closeButtons(){
        addButton.setVisible(false);
        finishButton.setVisible(false);
        addPersonButton.setVisible(false);
    }

    public void addDataset(XYDataItem dataItem, int LineNumber) {
        Lines.get(LineNumber).add(dataItem);
    }

    public void addLine(Person newPerson){
        Lines.add(new XYSeries(newPerson.getLineNumber(), false));
        Lines.get(Lines.size() - 1).add(0,0);
        dataset.addSeries(Lines.get(Lines.size() - 1));
        renderer.setSeriesPaint(newPerson.getLineNumber(), newPerson.getColor());
    }

    void setActionListener(ActionListener al) {
        addButton.addActionListener(al);
        finishButton.addActionListener(al);
        addPersonButton.addActionListener(al);
    }

    void setLocation(Point location){
        if(location == null){
            frame.setLocationRelativeTo(null);
        }else {
            frame.setLocation(location);
        }
    }

    public boolean isAddAction(ActionEvent e) {
        return e.getSource().equals(addButton);
    }
    public boolean isfinishAction(ActionEvent e) {
        return e.getSource().equals(finishButton);
    }
    public boolean isAddPersonAction(ActionEvent e) {
        return e.getSource().equals(addPersonButton);
    }

    public void setPersons(ArrayList<Person> persons){
        allpersons = persons;
    }
}