package eborho.kmGraph;

import org.jfree.data.xy.XYDataItem;

import java.awt.*;
import java.util.ArrayList;

public class Person {
    private Color color;
    private int LineNumber = 0;
    private ArrayList<XYDataItem> stdata = new ArrayList<>();
    private ArrayList<XYDataItem> vtdata = new ArrayList<>();

    public ArrayList<XYDataItem> getStData(){
        return stdata;
    }

    public ArrayList<XYDataItem> getVtData(){
        return vtdata;
    }

    public XYDataItem getVtData(int which){
        return vtdata.get(which);
    }

    public void addStData(XYDataItem dataitem, GraphGui graphGui){
        stdata.add(dataitem);
        graphGui.addDataset(dataitem, LineNumber);
    }

    public void addStDatawithoutGraph(XYDataItem dataitem){
        stdata.add(dataitem);
    }

    public void setLineNumber(int Number){
        LineNumber = Number;
    }

    public int getLineNumber(){
        return LineNumber;
    }

    public void setColor(Color newColor){
        color = newColor;
    }

    public Color getColor(){
        return color;
    }
}