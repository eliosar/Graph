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
import java.util.ArrayList;

public class VtGraph {
    private final ArrayList<Line> alllines;

    int width = 640;
    int height = 480;

    public JFrame frame = new JFrame();

    public VtGraph(ArrayList<Line> alllines){
        this.alllines = alllines;

        JPanel chartPanel = createChartPanel();
        frame.add(chartPanel, BorderLayout.CENTER);
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private JPanel createChartPanel(){
        XYDataset dataset = createDataset();

        JFreeChart chart = ChartFactory.createXYLineChart("vt Graph", "average speed [km/h]", "time [h]", dataset, PlotOrientation.HORIZONTAL, false, true, true);
        chart.setBackgroundPaint(Color.BLACK);
        chart.getTitle().setPaint(Color.CYAN);

        XYPlot plot = chart.getXYPlot();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

        int currentSeries = 0;
        for (Line allline : alllines) {
            for (int x = 0; x < allline.getVtData().size(); x++) {
                renderer.setSeriesPaint(currentSeries, allline.getColor());
                currentSeries += 1;
            }
        }

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

        for(int i = 0; i < alllines.size(); i++) {

            for(int x = 0; x < alllines.get(i).getVtData().size(); x++) {
                XYSeries Line = new XYSeries(i + " " + x, false);
                XYDataItem currentvtdata = alllines.get(i).getVtData(x);

                if (x == 0) {
                    Line.add(currentvtdata.getYValue(), 0);
                } else {
                    Line.add(currentvtdata.getYValue(), alllines.get(i).getVtData(x - 1).getXValue());
                }

                Line.add(currentvtdata.getYValue(), currentvtdata.getXValue());

                dataset.addSeries(Line);
            }
        }

        return dataset;
    }

    public void setLocation(Point location){
        if(location == null){
            frame.setLocationRelativeTo(null);
        }else {
            frame.setLocation(location);
        }
    }

    public void show(){
        frame.setVisible(true);
    }
}