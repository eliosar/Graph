package eborho.kmGraph;

import org.jfree.data.xy.XYDataItem;

import java.awt.*;
import java.util.ArrayList;

public class Line {
    private Color color;
    private int Number;
    private String Name;
    private final ArrayList<XYDataItem> stdata = new ArrayList<>();
    private final ArrayList<XYDataItem> vtdata = new ArrayList<>();

    public ArrayList<XYDataItem> getStData(){
        return stdata;
    }
    public void addStData(XYDataItem dataitem, Graph graph){
        stdata.add(dataitem);
        graph.addDataset(dataitem, Number);
    }
    public void addStDatawithoutGraph(XYDataItem dataitem){
        stdata.add(dataitem);
    }

    public ArrayList<XYDataItem> getVtData(){
        return vtdata;
    }
    public XYDataItem getVtData(int which){
        return vtdata.get(which);
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