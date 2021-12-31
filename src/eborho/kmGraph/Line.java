package eborho.kmGraph;

import org.jfree.data.xy.XYDataItem;

import java.awt.*;
import java.util.ArrayList;

public class Line {
    private Color color;
    private int Number;
    private String Name;
    private final ArrayList<XYDataItem> generaldata = new ArrayList<>();
    private final ArrayList<XYDataItem> averagedata = new ArrayList<>();

    public ArrayList<XYDataItem> getGeneralData(){
        return generaldata;
    }
    public void addStData(XYDataItem dataitem, Graph graph){
        generaldata.add(dataitem);
        graph.addDataset(dataitem, Number);
    }
    public void addGeneralDatawithoutGraph(XYDataItem dataitem){
        generaldata.add(dataitem);
    }

    public ArrayList<XYDataItem> getAverageData(){
        return averagedata;
    }
    public XYDataItem getAverageData(int which){
        return averagedata.get(which);
    }

    public void setNumber(int Number){
        this.Number = Number;
    }
    public int getNumber(){
        return Number;
    }

    public void setColor(Color newColor){
        color = newColor;
    }
    public Color getColor(){
        return color;
    }

    public void setName(String Name){
        this.Name = Name;
    }
    public String getName(){
        return Name;
    }
}