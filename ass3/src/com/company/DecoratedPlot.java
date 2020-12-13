package com.company;

import java.awt.*;

public class DecoratedPlot extends Drawable {
    protected static Drawable drawable;
    protected static SimplePlot simplePlot;

    public void add(Drawable d)
    {

        drawable=d;
    }

    @Override
    public void draw(Graphics g) {
        drawable.draw(g);
        System.out.println("here is the decorator graphic !");
       // simplePlot.draw(g);


    }

    @Override
    public void setValue(int value) {
        this.Value=value;
        super.setValue(value);
    }

    @Override
    public void SetX(int x) {
        this.X=x;
        super.SetX(x);
    }

    @Override
    public void SetY(int y) {
        this.Y=y;
        super.SetY(y);
    }





}
