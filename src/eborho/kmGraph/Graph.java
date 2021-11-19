package eborho.kmGraph;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Graph{
    private ArrayList<Float> Allkm;
    private ArrayList<Float> Alltimes;

    private int width = 640;
    private int height = 480;

    private JFreeChart chart;
    public JFrame frame;

    public Graph(ArrayList<Float> allkm, ArrayList<Float> alltimes, Point location){
        Allkm = allkm;
        Alltimes = alltimes;

        frame = new JFrame();
        JPanel chartPanel = createChartPanel();
        frame.add(chartPanel, BorderLayout.CENTER);

        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(location);
        frame.setVisible(true);
    }

    private JPanel createChartPanel(){
        XYDataset dataset = createDataset();

        chart = ChartFactory.createXYLineChart("st eborho.kmGraph.Graph", "distance [km]", "time [h]", dataset, PlotOrientation.HORIZONTAL, false, true, true);
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

    private XYDataset createDataset(){
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries Line = new XYSeries("line-chart", false);

        Line.add(0, 0);

        for (int i = 0; i < Allkm.size(); i++) {
            Line.add(Allkm.get(i), Alltimes.get((i)));
        }

        dataset.addSeries(Line);

        return dataset;
    }
}