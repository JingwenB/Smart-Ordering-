package dbsrc;

import java.sql.*;

public class ClubMember{

	private Connection con;

    public ClubMember(){
    	this.con = DBM.getConnection();
    }

    public void insertClubMember(String customerEmail, int points) {
		
		try {
			PreparedStatement pstmt = con.prepareStatement("INSERT INTO ClubMember VALUES (?,?)");
					
			pstmt.setString(1, customerEmail);
			pstmt.setInt(2, points);
			
			pstmt.executeUpdate();
			con.commit();
			pstmt.close();

		} catch(SQLException ex) {
			System.out.println("Message: " + ex.getMessage());
		}
	}

    public  void updateClubMemPts(String email, int pts){
		try {
			PreparedStatement ps = con.prepareStatement("SELECT CustomerEmail, Points, street FROM Customer WHERE email = '" + email+ "'",
			ResultSet.TYPE_SCROLL_INSENSITIVE,
			ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = ps.executeQuery();
			con.commit();
			ps.close();

			// the query returns only 1 row
			if (rs.next()) {
				if(pts<0)rs.updateNull(2);
				else rs.updateInt(2, pts);
				rs.updateRow();
			} else {
				System.out.println("\nClub member with email: " + email + " does not exist!");
			}

		} catch (SQLException ex){
			
			System.out.println("\nMessage: " + ex.getMessage());
			try  {
				con.rollback();
			} catch (SQLException ex2){
				System.out.println("\nMessage: " + ex2.getMessage());
				System.exit(-1);
			}
		}
	}
}
