package gui;

import javax.swing.*;
import java.sql.*;

public class Table extends JFrame {

    public Table(ResultSet set) {
        
        try {
            ResultSetMetaData metadata = set.getMetaData();
            
            //headers for the table
            int colCount = metadata.getColumnCount();
            String[] columns = new String[colCount];
            for(int i = 0; i < colCount; ++i){
                columns[i] = metadata.getColumnLabel(i+1);
            }

            // Now get the number of rows
            set.last();
            int rowCount = set.getRow();
            set.beforeFirst();
            // System.out.println(rowCount + "," + colCount);
             
            // Create and populate the data object
            String[][] data = new String[rowCount][colCount];
            int i = 0;
            while(set.next()) {
                for(int j = 0; j < colCount; j++) {
                    data[i][j] = set.getString(j+1);
                }
                ++i;
            }

            //create JTable with data
            JTable table = new JTable(data, columns);
             
            //add the table to the frame
            this.add(new JScrollPane(table));
             
            this.setTitle("Show Table");     
            this.pack();
            this.setVisible(true);
        }
        catch(SQLException ex) {
            System.out.println("Message: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}