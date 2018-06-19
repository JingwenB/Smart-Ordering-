package dbsrc;

import java.sql.*;

public class Branch{
	private int bid;
	private String owner;
	private String time_open;
	private String time_close;
	private Address addr;       // Branch only stores (postal, house#, street)

	private Connection con;

	public Branch(int bid, Address addr){

		this.bid = bid;
		this.addr = addr;
		this.owner = null;
		this.time_open = null;
		this.time_close = null;

		this.con = DBM.getConnection();

		String insert = "INSERT INTO Branch values("+ bid + ",null,null,null,'" + addr.postal +
						"'," +  addr.house_num + ",'" + addr.street + "'')";

		try{
			Statement stmt = con.createStatement();
			stmt.executeUpdate(insert);
			con.commit();
			stmt.close();
		}
		catch(Exception e){}
	}

	public boolean update(int bid_new, String owner_new, String time_open_new, 
		String time_close_new, String postal_new, int house_num_new, String street_new){

		/*
		if(bid_new == null){
			return false;
		}

		if(this.bid == null){
			return false;
		}*/

		if(this.addr.update(addr.province, addr.city, postal_new, house_num_new, street_new) == false){
			return false;
		}

		try{

			PreparedStatement ps = con.prepareStatement("UPDATE Branch SET BID=?, OWNER=?, time_open=?, time_close=?, Postal_code=?,HouseNum=?, Street=? WHERE BID=?");
			ps.setInt(1, bid_new);
			ps.setString(2, owner_new);
			ps.setString(3, time_open_new);
			ps.setString(4, time_close_new);
			ps.setString(5, postal_new);
			ps.setInt(6, house_num_new);
			ps.setString(7, street_new);
			ps.setInt(8, this.bid);

			ps.executeUpdate();
			con.commit();
			ps.close();
		}
		catch(Exception e){
			return false;
		}

		return true;
	}

	public boolean delete(Connection con){
		//if(this.bid == null){
		//	return false;
		//}

		String insert = "DELETE FROM Branch WHERE BID='" + this.bid + "'";

		try{
			Statement stmt = con.createStatement();
			stmt.executeUpdate(insert);
			con.commit();
			stmt.close();		
		}
		catch(Exception e){
			return false;
		}

		this.bid = -1;
		this.owner = null;
		this.time_open = null;
		this.time_close = null;

		// delete the address
		if(!addr.delete()){
			return false;
		}

		this.addr = null;

		return true;
	}
}