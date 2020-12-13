package com.company;

import com.sun.jdi.Value;

import java.awt.*;
import java.util.Vector;

public class Drawable {
    Vector<Integer> stackValue = new Vector<Integer>(20);
    protected int X,Y;
    protected int Value;
    private int width =500;

    public void setValue(int value)
    {
        stackValue.add(value);
        if (stackValue.size()>20)
        {
            stackValue.remove(0);
            Value=value;

        }
        if(stackValue.size()==0)
        {
            stackValue.add(value);
        }
        else if(stackValue.size()!=0 && stackValue.get(stackValue.size()-1)!=value)
        {
            stackValue.add(value);
        }



    }
    public Vector<Integer> getStackValue()
    {
        return stackValue;
    }

    public void SetX(int x)
    {
        this.X=x;
    }
    public void SetY(int y)
    {
        this.Y=y;
    }
    public void draw(Graphics g)
    {

    }



}
