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

public class AverageGraph {
    private final ArrayList<Line> alllines;

    int width = 640;
    int height = 480;

    private final JFrame frame = new JFrame();
    private final String xUnit;
    private final String yUnit;

    public AverageGraph(ArrayList<Line> alllines, String xUnit, String yUnit){
        this.alllines = alllines;
        this.xUnit = xUnit;
        this.yUnit = yUnit;

        JPanel chartPanel = createChartPanel();
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(chartPanel, BorderLayout.CENTER);
    }

    private JPanel createChartPanel(){
        XYDataset dataset = createDataset();

        JFreeChart chart = ChartFactory.createXYLineChart("average Graph", xUnit, yUnit + "/" + xUnit, dataset, PlotOrientation.VERTICAL, false, true, true);
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
                    Line.add(0, currentvtdata.getYValue());
                } else {
                    Line.add(alllines.get(i).getVtData(x - 1).getXValue(), currentvtdata.getYValue());
                }

                Line.add(currentvtdata.getXValue(), currentvtdata.getYValue());

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