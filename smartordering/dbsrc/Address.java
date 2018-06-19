/* A class to wrap an address. The constructor will insert the newly instantied 
   address into the DB (only inserts primary key values). The update function 
   will take the new paramaters, update the DB, then update the object. 
   On failure, update will return false and not update the struct. 
   A delete operation is also provided. On delete all attributes are set to null (ints set to -1).
*/

package dbsrc;

import java.sql.*;

public class Address{

	public String province;
	public String city;
	public String postal;
	public int house_num;
	public String street;

	private Connection con;

	public Address(String postal, int house_num, String street){
		this.postal = postal;
		this.house_num = house_num;
		this.street = street;
		this.province = null;
		this.city = null;

		this.con = DBM.getConnection();

		// insert into table
		String insert = "INSERT INTO Address values(null,null, '"+ postal 
						+"','" + house_num + "','" + street + "')";

		try{
			Statement stmt = con.createStatement();
			stmt.executeUpdate(insert);
			con.commit();
			stmt.close();		
		}
		catch(Exception e){}
	}

	public boolean update(String province_new, String city_new, String postal_new, 
						  int house_num_new, String street_new){

		if(postal_new == null || house_num_new == -1 || street_new == null){
			return false;
		}

		if(this.postal == null || this.house_num == -1 || this.street == null){
			return false;
		}

		String insert = "UPDATE Address set" +
						"Postal_code=" + "'" + postal_new + "'," + 
						"HouseNum=" + "'" + house_num_new + "'," +
						"Street=" + "'" + street_new + "',";

		if(province_new != null){
			insert += "Province=" + "'" + province_new + "',";
		}
		else{
			insert += "Province=null,";
		}
		if(city_new != null){
			insert += "City=" + "'" + city_new + "'";
		}
		else{
			insert += "City=null ";
		}

		insert += "WHERE" + "Postal_code=" + "'" + this.postal + "'," + 
							"HouseNum=" + "'" + this.house_num + "'," +
							"Street=" + "'" + this.street + "'";

		try{
			Statement stmt = con.createStatement();
			stmt.executeUpdate(insert);
			con.commit();
			stmt.close();		
		}
		catch(Exception e){
			return false;
		}

		this.province = province_new;
		this.city = city_new;
		this.postal = postal_new;
		this.house_num = house_num_new;
		this.street = street_new;

		return true;
	}

	public boolean delete(){

		if(this.postal == null || this.house_num == -1 || this.street == null){
			return false;
		}

		String insert = "DELETE FROM Address WHERE Postal_code='" + this.postal + "', HouseNum=" + 
						this.house_num + ", Street='" + this.street + "'";

		try{
			Statement stmt = con.createStatement();
			stmt.executeUpdate(insert);
			con.commit();
			stmt.close();		
		}
		catch(Exception e){
			return false;
		}

		this.province = null;
		this.city = null;
		this.postal = null;
		this.house_num = -1;
		this.street = null;

		return true;
	}
}