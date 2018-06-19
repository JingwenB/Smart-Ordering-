package gui;

import dbsrc.Review;
import dbsrc.DBM;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.Exchanger;

import javax.swing.SpringLayout;



public class UpdateReviewPanel extends JPanel{
    
    public UpdateReviewPanel() {
        JTextField textField1;
        JTextField textField2;
        JTextField textField3;
        JTextField textField4;  
      
     SpringLayout layout = new SpringLayout();
      setLayout(layout);
//branchID, String email, int rating, String detail
        String[] labels = {"Branch ID: ", "Your Email: ", "Rating (from 0 to 10): ", "Detail: "};
        int numPairs = labels.length;

        JLabel l1 = new JLabel(labels[0], JLabel.LEADING);
        this.add(l1);
       textField1 = new JTextField(10);
        l1.setLabelFor(textField1);
         this.add(textField1);

        JLabel l2 = new JLabel(labels[1], JLabel.LEADING);
        this.add(l2);
        textField2 = new JTextField(10);
        l1.setLabelFor(textField2);
        this.add(textField2);

        JLabel l3 = new JLabel(labels[2], JLabel.LEADING);
        this.add(l3);
        textField3 = new JTextField(10);
        l3.setLabelFor(textField3);
        this.add(textField3);

        JLabel l4 = new JLabel(labels[3], JLabel.LEADING);
        this.add(l4);
        textField4 = new JTextField(10);
        l4.setLabelFor(textField4);
        this.add(textField4);

      Review rv=new Review();

                // Lay out the panel.
        SpringUtilities.makeCompactGrid(this,
                                numPairs, 2, //rows, cols
                                10, 40,        //initX, initY
                                10, 10);       //xPad, yPad

    
        // Create the button and add the above actionlistener
       
        JButton button1 = new JButton("Update");

        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                
                try{
             int branchID = Integer.parseInt(textField1.getText());
             String email=textField2.getText();
             int rating = Integer.parseInt(textField3.getText());
             String detail=textField4.getText();
             if (ae.getActionCommand().equals("Update")) {
               
                System.out.println("Button1 Update has been clicked" );
               
               try{
                if(rv.updateReview(branchID,email,rating, detail)==0){
                    popUp("Review with Customer Email: " + email + ", Branch ID: "+ branchID+" has been updated");
                } else if (rv.updateReview(branchID,email,rating, detail)==1){
                    popUp("Review with Customer Email: " + email + ", Branch ID: "+ branchID+" does not exist!");
                }

               } catch(Exception ex1) {
                   popUp(ex1.getMessage());
               }
            }
                }
                catch(Exception ex2){
                    popUp(ex2.getMessage());
                }

            
            }
        });
    
        //button.setVerticalTextPosition(AbstractButton.BOTTOM);
        //button.setHorizontalTextPosition(AbstractButton.CENTER);
        JButton button2 = new JButton("Show Table Example: Review");
        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                new Table(DBM.getTable("Review"));
            }
        });
        layout.putConstraint(SpringLayout.EAST, button1,
                     100,
                     SpringLayout.EAST, button2);

        this.add(button1);
        this.add(button2);
     }
     private static String inputPopUp(String message)
	{
		return JOptionPane.showInputDialog(null, message);
    }
    

	private static void popUp(String message)
	{
		JOptionPane.showMessageDialog(null, message);
	}
}


