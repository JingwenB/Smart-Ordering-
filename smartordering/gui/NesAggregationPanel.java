package gui;

import dbsrc.Report;
import javax.swing.*;
import java.awt.event.*;

public class NesAggregationPanel extends JPanel {

    public NesAggregationPanel() {

    	Report r = new Report();

    	String[] ops1 = new String[] {"AVG", "SUM","MAX", "MIN"};
    	String[] ops2 = new String[] {"MAX", "MIN"};

    	JComboBox<String> box1 = new JComboBox<String>(ops1);
    	JComboBox<String> box2 = new JComboBox<String>(ops2);
    	box2.addActionListener(new ActionListener() {
 
    		@Override
   			public void actionPerformed(ActionEvent event) {
        		String op1 = box1.getSelectedItem().toString();
        		String op2 = box2.getSelectedItem().toString();
 
        		new Table(r.nestedAgg(op1, op2));
    		}
		});

    	this.add(box1);
    	this.add(box2);
    	
    }
}