package com.company;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class Evaluator extends JPanel {

    private static Evaluator instance;
    protected Vector<Integer> vectorX  = new <Integer>Vector();
    protected  Vector<Integer> vectorY = new <Integer>Vector();

    protected int x,y,valueX;

    private Evaluator()
    {

    }

    public static Evaluator getInstance()
    {
        if(instance == null)
        {
            instance = new Evaluator();
        }
        return instance;

    }

    public void doSomething (Vector<Integer> value)
    {

        int sumX=0;
        for (int i=0;i<= value.size()-1;i++)
        {
            sumX += value.get(i);
        }
        valueX=sumX/value.size();





    }

    public void draw(Graphics g)
    {
        g.setColor(Color.red);
        g.drawLine(0,valueX,800,valueX);
    }
}
