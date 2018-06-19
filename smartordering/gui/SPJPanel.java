package gui;

import dbsrc.DBM;
import javax.swing.*;
import java.awt.event.*;

public class SPJPanel extends JPanel {

    public SPJPanel() {

    	//this.getContentPane().setLayout(null);	// no layout manager
    	// Container c = frame.getContentPane();


    	JButton button1 = new JButton("Show Table Example: Food");
    	//button1.seBounds(100, 200, 50, 40);
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                new Table(DBM.getTable("Food"));
            }
        });

        JButton button2 = new JButton("Show Table Example: Customer");
        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                new Table(DBM.getTable("Customer"));
            }
        });

        this.add(button1);
        this.add(button2);
    }
}
