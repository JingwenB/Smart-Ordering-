package gui;

import dbsrc.DBM;
import dbsrc.Order;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DeleteOrderPanel extends JPanel {

    public DeleteOrderPanel() {
        //setPreferredSize(new Dimension(100, 190));

        JLabel label1=new JLabel("Enter Order's Confirmation Number: ");
        this.add(label1);
        
        //JPanel textPanel = new JPanel();
        JTextField textField = new JTextField(20);
        this.add(textField);

        // Create the button and add the above actionlistener
        //JPanel bPanel = new JPanel();
        JButton button = new JButton("Delete");

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                Order order=new Order();
                try{
             int confNum = Integer.parseInt(textField.getText());

             if (ae.getActionCommand().equals("Delete")) {
               
                System.out.println("Button delete has been clicked" );

               if(order.deleteOrder(confNum)){
                popUp("Your record has been deleted!");
               } else {
                   popUp("Order with confNum: " + confNum + " does not exist!");
               }
            }
                }
                catch(Exception ex){
                    popUp("please insert test boxes!");
                }
            }
        });


        JButton button2 = new JButton("Show Table Example: Order 1");
        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                new Table(DBM.getTable("Order1"));
            }
        });

        JButton button3 = new JButton("Show Table Example: Order 2");
        button3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                new Table(DBM.getTable("Order2"));
            }
        });

   
        this.add(button);
        this.add(button2);
        this.add(button3);


    }
    
	private static void popUp(String message)
	{
		JOptionPane.showMessageDialog(null, message);
	}
}
