package gui;

import dbsrc.DBM;
import javax.swing.*;
import java.awt.event.*;

public class CreateOrderPanel extends JPanel {

    public CreateOrderPanel() {

        //JPanel textPanel = new JPanel();
        JTextField textField = new JTextField("This is a text", 20);
        this.add(textField);

        // Create the button and add the above actionlistener
        //JPanel bPanel = new JPanel();
        JButton button = new JButton("Show Table Example: Food");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                new Table(DBM.getTable("Food"));
            }
        });
        
        this.add(button);
    }
}
