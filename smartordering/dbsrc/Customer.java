package dbsrc;

import java.sql.*;

public class Customer{
	private String email;
	private String name;
	private long phone;
	private Address addr;

	private Connection con;

	public Customer(String email, Address addr){
		this.email = email;
		this.name = null;
		this.phone = -1;
		this.addr = addr;

		this.con = DBM.getConnection();

		String insert = "INSERT INTO Customer values('"+ email + "',null,null,'" + addr.postal +
						"'," +  addr.house_num + ",'" + addr.street + "'')";

		try{
			Statement stmt = con.createStatement();
			stmt.executeUpdate(insert);
			con.commit();
			stmt.close();
		}
		catch(Exception e){}
	}

	public boolean updateNP(String cName, String phone){
		if(this.email == null){
			return false;
		}

		try {
			PreparedStatement ps = con.prepareStatement("SELECT cName, phone FROM Customer WHERE email = '" + this.email+ "'",
        	ResultSet.TYPE_SCROLL_INSENSITIVE,
			ResultSet.CONCUR_UPDATABLE);
		
			ps.setString(1, this.email);
			ResultSet rs = ps.executeQuery();
		
			   // the query returns only 1 row
			   if (rs.next()) {
				   rs.updateString(1, cName);
				   rs.updateString(2, phone);
				   rs.updateRow();
				}
				else{
					System.out.println("\nCustomer with email: " + this.email + " does not exist!");
					return false;
				}
				  
		    con.commit();
			ps.close();
			return true;
		} 
		catch (SQLException ex){
	    	return false;
		}
	}

	public boolean updateAddr(String postalCode, int houseNum, String street){
		/*
		if(this.email == null){
			return false;
		}*/

		// first update addr object
		if(this.addr.update(addr.province, addr.city, postalCode, houseNum, street) == false){
			return false;
		}

		try {
			PreparedStatement ps = con.prepareStatement("SELECT postalCode, houseNum, street FROM Customer WHERE email = '" + this.email+ "'",
        	ResultSet.TYPE_SCROLL_INSENSITIVE,
        	ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = ps.executeQuery();
			  
			// the query returns only 1 row
			if (rs.next()) {
				rs.updateString(1, postalCode);
				rs.updateInt(2, houseNum);
				rs.updateString(3, street);
				rs.updateRow();
			}
			else{
				System.out.println("\nCustomer with email: " + this.email + " does not exist!");
			}
				  
		    con.commit();
			ps.close();

		} catch (SQLException ex) {
			return false;
		}

		return true;
	}
}

/*
	public boolean delete(){

		if(this.email == null){
			return false;
		}

		String insert = "DELETE FROM Customer WHERE email='" + this.email + "'";

		try{
			Statement stmt = con.createStatement();
			stmt.executeUpdate(insert);
			con.commit();
			stmt.close();		
		} catch(Exception e){
			return false;
		}

		this.email = NULL;
		this.name = NULL;
		this.phone = -1;

		// delete the address
		if(!addr.delete(con)){
			return false;
		}

		this.addr = NULL;

		return true;
	}
}*/