package com.company;
//import java.util.Observable;
//import  java.util.Observer;

public class Source extends Observable{
    private int x ,y;
    private int value;

    public void create ()
    {
        value =(int)(Math.random()*200)+1;
        System.out.println(value);
        notifyObserver(value);
        //setChanged();
        //notifyObservers();

    }

    public int getValue()
    {

        return value;
    }
    public int getX()
    {
        return x;
    }
    public  int getY()
    {
        return y;
    }


}
