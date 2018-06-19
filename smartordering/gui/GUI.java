package gui;

import javax.swing.*;
import java.awt.*;

public class GUI {

    public void createAndShowGUI() {

        //Create and set up the window.
        JFrame frame = new JFrame("Smart Ordering");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // create tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Slection, Projection, Join", new SPJPanel());
        tabbedPane.addTab("Division", new DivisionPanel());
        tabbedPane.addTab("Aggregation", new AggregationPanel());
        tabbedPane.addTab("Nested Aggregation", new NesAggregationPanel());
        tabbedPane.addTab("Update Review", new UpdateReviewPanel());
        tabbedPane.addTab("Delete Order", new DeleteOrderPanel());
        
		  //tabbedPane.addTab("Create Customer", new CreateCustomerPanel());
        //tabbedPane.addTab("Division Query", new DivisionQueryPanel());
        

        frame.add(tabbedPane, BorderLayout.NORTH);

        //Display the window.
        frame.setPreferredSize(new Dimension(700, 600));
        frame.pack();
        frame.setVisible(true);
    }
}
