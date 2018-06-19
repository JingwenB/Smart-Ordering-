import gui.GUI;
import dbsrc.DBM;
import java.sql.*;

public class Start {

	public static void main(String[] args) {
		
		// connect to Oracle
		DBM dbm = new DBM("ora_q2i1b", "a93956167");

        //start the GUI
        GUI gui = new GUI();
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
        	public void run() {
        		gui.createAndShowGUI();
            }
        });
    }
}