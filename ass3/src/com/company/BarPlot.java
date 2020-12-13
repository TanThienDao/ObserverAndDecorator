package com.company;

import java.awt.*;

public class BarPlot extends DecoratedPlot {

    protected int oldX,oldY;

    @Override
    public void SetX(int x) {
        oldX = drawable.X;
        super.SetX(x);
    }

    @Override
    public void SetY(int y) {
        oldY=drawable.Y;
        super.SetY(y);
    }

    public void draw(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);


        int x=0;
        int oldX =20,oldY =0, newX=0,newY=0;



        if(stackValue.size()>1)
        {

            for(int i=1;i<stackValue.size();i++)
            {

                // g.drawLine(x+1,200+1-stackValue.get(i),x-20+1,200+1-stackValue.get(i-1));
                x+=20;
                g.setColor(Color.gray);
                g.fillRect(x+1,200+1-stackValue.get(i)+10,10,200-10);
                g.setColor(Color.blue);
                g.fillRect(x+1,200+1-stackValue.get(i),10,10);

            }
        }
        super.draw(g);



    }
}
