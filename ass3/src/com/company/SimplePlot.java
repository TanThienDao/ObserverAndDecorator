package com.company;

import com.sun.jdi.Value;

import java.awt.*;

public class SimplePlot extends Drawable {
    private int width =500;

    public void draw(Graphics g)
    {
        int Width = (width/20);


        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(Color.blue);
        int x=0;
        int oldX =20,oldY =0, newX=0,newY=0;

        Printdata();



        if(stackValue.size()>1)
        {

            for(int i=1;i<stackValue.size();i++)
            {
                x+=20;
                g.drawLine((x+1)+5,(200+1-stackValue.get(i))+5,(x-20+1)+5,(200+1-stackValue.get(i-1))+5);


            }
        }

    }
    public void Printdata()
    {
        System.out.println("size in simple: "+stackValue.size());
        for(int i=0;i<stackValue.size();i++)
        {
            System.out.print(stackValue.get(i)+" ");

        }
        System.out.println("");
    }

}
