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

import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;

public class GraphGui {

    private static final int WIDTH = 640;
    private static final int HEIGHT = 480;

    private final JFrame frame = new JFrame();
    private final XYSeries line = new XYSeries("line-chart", false);
    private final JButton addButton = new JButton("add");
    private final JButton finishButton = new JButton("finish");

    public GraphGui() {
        addButton.setBounds(10, 415, 90, 20);
        finishButton.setBounds(110, 415, 90, 20);
        JPanel chartPanel = createChartPanel();
        frame.add(addButton);
        frame.add(finishButton);
        frame.add(chartPanel, BorderLayout.CENTER);

        frame.setSize(WIDTH, HEIGHT);
        //frame will be close ONLY ON "button exit" from inputGui is pressed
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private JPanel createChartPanel() {
        XYDataset dataset = createDataset();

        JFreeChart chart = ChartFactory.createXYLineChart("stGraph", "distance [km]", "time [h]", dataset, PlotOrientation.HORIZONTAL, false, true, true);
        chart.setBackgroundPaint(Color.BLACK);
        chart.getTitle().setPaint(Color.CYAN);

        XYPlot plot = chart.getXYPlot();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

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
        XYSeriesCollection dataset = new XYSeriesCollection();
        line.add(0,0);
        dataset.addSeries(line);
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
    }

    public void addDataset(XYDataItem dataItem) {
        line.add(dataItem);
    }

    void setActionListener(ActionListener al) {
        addButton.addActionListener(al);
        finishButton.addActionListener(al);
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
}