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
    private ArrayList<XYDataItem> Alldatas = new ArrayList<>();
    private int width = 640;
    private int height = 480;
    private JFreeChart chart;

    public JFrame frame = new JFrame();

    public VtGraph(ArrayList<Person> allpersons){

        for(int i = 0; i < allpersons.size(); i++){
            Alldatas.add(allpersons.get(i).getVtData().get(i));
            //Alldatas.add(datas.get(i).getX().floatValue());
            System.out.println(Alldatas.get(i));
        }
        //Alltimes.add(0f);

        JPanel chartPanel = createChartPanel();
        frame.add(chartPanel, BorderLayout.CENTER);

        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private JPanel createChartPanel(){
        XYDataset dataset = createDataset();

        chart = ChartFactory.createXYLineChart("vt Graph", "average speed [km/h]", "time [h]", dataset, PlotOrientation.HORIZONTAL, false, true, true);
        chart.setBackgroundPaint(Color.BLACK);
        chart.getTitle().setPaint(Color.CYAN);

        XYPlot plot = chart.getXYPlot();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

        for(int i = 0; i < dataset.getSeriesCount(); i++) {
            renderer.setSeriesPaint(i, Color.CYAN);
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

        /*for(int i = 0; i < Allspeed.size(); i++) {
            XYSeries Line = new XYSeries(Allspeed.get(i), false);

            if(i == 0) {
                Line.add(Allspeed.get(i), Alltimes.get(Alltimes.size() - 1));
            }else{
                Line.add(Allspeed.get(i), Alltimes.get(i - 1));
            }

            Line.add(Allspeed.get(i), Alltimes.get(i));

            dataset.addSeries(Line);
        }*/

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