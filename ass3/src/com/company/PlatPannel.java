package com.company;
import com.sun.source.tree.Scope;

import javax.swing.*;
import java.awt.*;
//import java.util.Observable;
//import  java.util.Observer;
import  java.awt.Dimension;

// 3 plot Pannel for the main
// observer is here !
//((Graphics) void).drawLine(x, y, x2, y2)

public class PlatPannel extends JPanel implements Observer {

    private Drawable plot;
    private Evaluator e;
    int value=0;

    PlatPannel(Drawable d)
    {
        plot = d;
        e =e.getInstance();
        //this.setPreferredSize(new Dimension(400,200));
        //this.plot=d;


    }
    public void  paintComponent(Graphics g)
    {
        super.paintComponent(g);
        plot.draw(g);

        Evaluator.getInstance().draw(g);



    }

//    @Override
//    public void update(Observable o, Object arg)
//    {
//        plot.setValue(((Source)o).getValue());
//        plot.SetX(((Source)o).getX());
//        Grader.getInstance().doSomething(((Source)o).getX(),((Source)o).getValue());
//        repaint();
//
//
//    }
    public int getValue()
    {
        return value;
    }

    @Override
    public void update(int value) {
        Source s = new Source();
        plot.setValue(value);
        Evaluator.getInstance().doSomething(plot.stackValue);
        this.repaint();



    }
}
