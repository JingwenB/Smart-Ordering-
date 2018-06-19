package gui;

import dbsrc.Report;
import javax.swing.*;
import java.awt.event.*;

public class AggregationPanel extends JPanel {

    public AggregationPanel() {
    	Report r = new Report();

    	String[] ops1 = new String[] {"AVG", "SUM","MAX", "MIN"};

    	JComboBox<String> box1 = new JComboBox<String>(ops1);
    	box1.addActionListener(new ActionListener() {
 
    		@Override
   			public void actionPerformed(ActionEvent event) {
        		String op1 = box1.getSelectedItem().toString();
 
        		new Table(r.agg(op1));
    		}
		});

    	this.add(box1);
    }
}