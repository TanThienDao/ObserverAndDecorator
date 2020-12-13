package com.company;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame implements Listener, ActionListener {
    private JButton runB = new JButton("run");
    private Source source = new Source();

    public static void main(String[] args) {
	// write your code here

        Main main = new Main();
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main.setSize(500,800);
        main.setVisible(true);




    }

    public Main()
    {
        Container mainContainer = getContentPane();
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Stock Market");
        menuBar.add(menu);

        setJMenuBar(menuBar);
        runB = new JButton("run");

        source.create();

        BarPlot barPlot = new BarPlot();
        SimplePlot simplePlot = new SimplePlot();
        MarkedPLot markedPLot = new MarkedPLot();


        barPlot.add(markedPLot);
        markedPLot.add(simplePlot);




        PlatPannel platPannelB2 = new PlatPannel(barPlot);
        PlatPannel platPannelM2 = new PlatPannel(markedPLot);
        PlatPannel platPannelS2 = new PlatPannel(simplePlot);



        platPannelB2.add(platPannelM2);
        platPannelB2.add(platPannelS2);
        platPannelM2.add(platPannelS2);

        source.add(platPannelS2);
        source.add(platPannelM2);
        source.add(platPannelB2);




        mainContainer.setLayout(new GridLayout(4,1));
        platPannelM2.setBackground(Color.lightGray);
        platPannelS2.setBackground(Color.gray);

        mainContainer.add(platPannelB2);
        mainContainer.add(platPannelM2);
        mainContainer.add(platPannelS2);

        mainContainer.add(runB);
        runB.addActionListener( this);




    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == runB)
        {

            source.create();

        }

    }

    //+Main

    //+actionPerform() -> call the work methif in the Class Source -> new ramdom number Source is observable !
    // in side work after create ramdom number -> noityfy observer.
}
