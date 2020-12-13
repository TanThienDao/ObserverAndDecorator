package com.company;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class Grader extends JPanel {
    private static Grader instance;
    protected Vector<Integer> vectorX  = new <Integer>Vector();
    protected  Vector<Integer> vectorY = new <Integer>Vector();

    protected int x,y;

    private Grader()
    {

    }

    public static Grader getInstance()
    {
        if(instance == null)
        {
            instance = new Grader();
        }
        return instance;

    }

    public void doSomething (int a,int b)
    {
        vectorX.add(a);
        vectorY.add(b);
        int sumX=0;
        for (int i=0;i<= vectorX.size()-1;i++)
        {
            sumX += vectorX.get(i);
        }
        x=sumX/vectorX.size();
        int sumY=0;
        for(int i=0;i<vectorY.size()-1;i++)
        {
            sumY +=vectorY.get(i);
        }
        y=sumY/vectorY.size();


    }

    public void draw(Graphics g)
    {
        g.setColor(Color.red);
        g.drawLine(0,100,800,100);
    }



}
