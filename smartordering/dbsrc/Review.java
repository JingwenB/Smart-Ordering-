package dbsrc;

import java.sql.*;
import java.util.GregorianCalendar;

public class Review {

	private Connection con;

	public Review() {
		this.con = DBM.getConnection();
	}

	public void insertReview(int branchID, String custEmail, int rating, String detail){
		//get current time
		java.sql.Timestamp date = new java.sql.Timestamp(GregorianCalendar.getInstance().getTime().getTime());

		if(rating<0 || rating>10) {
			System.out.println("\nplease insert correct rating from 0 to 10");
			return;
		}
		
		try {
			PreparedStatement ps = con.prepareStatement("INSERT INTO Review VALUES(?,?,?,?,?)");
			ps.setInt(1, branchID);
			ps.setString(2, custEmail);
			ps.setTimestamp(3, date);
			ps.setInt(4, rating);
			//ps.setString(5, detail);
			if(detail=="")	ps.setNull(5,Types.VARCHAR);
			else	ps.setString(5, detail);
			ps.executeUpdate();
			con.commit();
			ps.close();
		} catch (SQLException ex) {
			System.out.println("\nMessage: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	// deletes a review by a certain customer 
	private boolean deleteReview(int branchID, String email) {
		try {
			PreparedStatement ps = con.prepareStatement(
				"DELETE from Review WHERE BranchID="+branchID+" AND CustomerEmail= '" + email+ "'");
			int rowCount = ps.executeUpdate();
			con.commit();
			ps.close();

			if (rowCount == 0){
				System.out.println("\nReview with Customer Email: " + email + ", Branch ID: "+ branchID+" does not exist!"); 
				return false;
			} else {
				System.out.println("\nRecord is deleted!");
				return true;
			}

		} catch (SQLException ex) {
			System.out.println("\nMessage: " + ex.getMessage());
            try { 
            	con.rollback();	
            } catch (SQLException ex2) {
            	System.out.println("\nMessage: " + ex2.getMessage());
            	System.exit(-1);
            }
        }

        return false;
    }

    /*
     * update a review with new rating and desc
	 * if a rating <0 || >10 print out fasle statement
	 */ 
    public int updateReview(int branchID, String email, int rating, String detail) throws SQLException {
		
		
		//	try {
				PreparedStatement ps = con.prepareStatement("SELECT Rating, Detail from Review WHERE BranchID="+branchID+" AND CustomerEmail= '" + email+ "'",
				ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
			
				ResultSet rs = ps.executeQuery();
				// the query returns only 1 row
				if (rs.next()) {
					rs.updateInt(1, rating);
					if(detail=="")	rs.updateNull(2);
					else	rs.updateString(2, detail);
					rs.updateRow();
					System.out.println("\nReview with Customer Email: " + email + ", Branch ID: "+ branchID+" has been updated");
					con.commit();
					ps.close();
					return 0;
	
				} else {
					System.out.println("\nReview with Customer Email: " + email + ", Branch ID: "+ branchID+" does not exist!");
					return 1;
				}
		} 

	public void seeBranchReviews(int branchID) {
		try {	
			Statement stmt = con.createStatement();
			ResultSet results = stmt.executeQuery("SELECT BranchID, CustomerEmail, Rating, Detail " + 
				"FROM Review WHERE BranchID='" + branchID + "'");
			// showResultsTable(results);
			stmt.close();

		} catch(SQLException ex) {
			System.out.println("Message: " + ex.getMessage());
		}
	}

	public void seeCustomerReviews(String customerEmail) {
		try {	
			Statement stmt = con.createStatement();
			ResultSet results = stmt.executeQuery("SELECT BranchID, Rating, Detail FROM Review WHERE CustomerEmail='" + customerEmail + "'");
			// showResultsTable(results);
			stmt.close();

		} catch(SQLException ex) {
			System.out.println("Message: " + ex.getMessage());
		}
	}
}