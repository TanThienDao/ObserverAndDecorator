package com.company;
import java.util.ArrayList;
import java.util.List;

public class Observable {
    private static List<Observer> observers = new ArrayList<Observer>();

    public static void add(Observer observer)
    {
        observers.add(observer);
    }
    public static  void remove(Observer observer)
    {
        observers.remove(observer);
    }
    public static void notifyObserver(int value)
    {
        for (Observer o : observers)
        {

            o.update(value);
        }
    }






}
