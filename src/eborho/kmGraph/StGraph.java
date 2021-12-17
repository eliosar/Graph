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

public class StGraph {

    private static final int WIDTH = 640;
    private static final int HEIGHT = 480;

    private final XYSeriesCollection dataset = new XYSeriesCollection();

    private final ArrayList<XYSeries> Lines = new ArrayList<>();
    private final ArrayList<JButton> allButtons = new ArrayList<>();

    private final JFrame frame = new JFrame();

    private final JButton addButton = new JButton("add");
    private final JButton finishButton = new JButton("finish");
    private final JButton chooselineButton = new JButton("all Lines");

    private final JLabel currentLine = new JLabel();

    private final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

    public StGraph() {
        allButtons.add(addButton);
        allButtons.add(finishButton);
        allButtons.add(chooselineButton);

        Lines.add(new XYSeries("line", false));
        addButton.setBounds(10, 415, 90, 20);
        finishButton.setBounds(110, 415, 90, 20);
        chooselineButton.setBounds(10, 10, 100, 20);
        currentLine.setBounds(210, 415, 120, 20);
        currentLine.setText("current Line: ");
        currentLine.setForeground(Color.WHITE);
        JPanel chartPanel = createChartPanel();

        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(addButton);
        frame.add(finishButton);
        frame.add(chooselineButton);
        frame.add(currentLine);
        frame.add(chartPanel, BorderLayout.CENTER);
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
        for(JButton currentButton : allButtons) {
            currentButton.setVisible(false);
        }
    }

    public void disableButtons(){
        for(JButton currentButton : allButtons) {
            currentButton.setEnabled(false);
        }
    }

    public void enableButtons(){
        for(JButton currentButton : allButtons) {
            currentButton.setEnabled(true);
        }
    }

    public void addDataset(XYDataItem dataItem, int LineNumber) {
        Lines.get(LineNumber).add(dataItem);
    }

    public void addLine(Line newLine){
        Lines.add(new XYSeries(newLine.getNumber(), false));
        Lines.get(Lines.size() - 1).add(0,0);
        dataset.addSeries(Lines.get(Lines.size() - 1));
        renderer.setSeriesPaint(newLine.getNumber(), newLine.getColor());
    }

    void setActionListener(ActionListener al) {
        for(JButton currentButton : allButtons) {
            currentButton.addActionListener(al);
        }
    }

    void setLocation(Point location){
        if(location == null){
            frame.setLocationRelativeTo(null);
        }else {
            frame.setLocation(location);
        }
    }

    public void setCurrentLine(Line currentLine){
        this.currentLine.setText("current Line: " + currentLine.getName());
    }

    public boolean isAddAction(ActionEvent e) {
        return e.getSource().equals(addButton);
    }
    public boolean isfinishAction(ActionEvent e) {
        return e.getSource().equals(finishButton);
    }
    public boolean ischooseLineAction(ActionEvent e) {
        return e.getSource().equals(chooselineButton);
    }
}