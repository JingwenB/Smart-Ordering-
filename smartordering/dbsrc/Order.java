package dbsrc;

import dbsrc.DBM;
import java.sql.*;
import java.util.Map;
import java.util.HashMap;
import java.util.GregorianCalendar;

public class Order {
	
	private Connection con;
	
	public Order() {
        this.con = DBM.getConnection();
        
	}
	
	public void insertOrderItem(Map<Integer, Integer> items, int fid, int quantity) {
        if(items.containsKey(fid))
            items.put(fid, items.get(fid) + quantity);
        else
            items.put(fid, quantity);
    }

	public void createOrder(int confNum, String custEmail, double paidPrice, Map<Integer, Integer> items) {

		try {

			PreparedStatement pstmtOr1 = con.prepareStatement("INSERT INTO Order1 VALUES (?,?,?,?)");
				
			pstmtOr1.setInt(1, confNum);
			pstmtOr1.setString(2, custEmail);

			java.sql.Timestamp date = new java.sql.Timestamp(GregorianCalendar.getInstance().getTime().getTime());
			pstmtOr1.setTimestamp(3, date);

			pstmtOr1.setDouble(4, paidPrice);
			pstmtOr1.executeUpdate();

			// insert items to order 2
			PreparedStatement pstmtOr2 = con.prepareStatement("INSERT INTO Order2 VALUES (?,?,?)");
			for(int fid : items.keySet()) {
				pstmtOr2.setInt(1, confNum);
				pstmtOr2.setInt(2, fid);
				pstmtOr2.setInt(3, items.get(fid));
				pstmtOr2.addBatch();
			}

			pstmtOr2.executeBatch();

			pstmtOr1.close();
			pstmtOr2.close();

		} catch(SQLException ex) {
			System.out.println("Message: " + ex.getMessage());
		}
	}
	
	public boolean deleteOrder(int confNum){
	
		try {
			Statement ps2 = con.createStatement();
			// ps2.setInt(1, confNum);
			
			 int rowCount = ps2.executeUpdate("DELETE from Order1 WHERE confNum="+confNum);
			 System.out.println(rowCount);
			 con.commit();
			ps2.close();
			 
			 if (rowCount == 0){
                 System.out.println("\nOrder with confNum: " + confNum + " does not exist!"); 
                 return false;
			} else {
                System.out.println("\nRecord is deleted!");
                return true;
			}

		} catch (Exception e) {
            System.out.println("\nMessage: " + e.getMessage());
            return false;
        }
	}
}
