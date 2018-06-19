package dbsrc;

import java.sql.*;

public class Report {

	private Connection con;

	public Report(){
		this.con = DBM.getConnection();
	}

	// DIVISION: Find branches with all food in its Menu
	// TODO: WRITE down relational algebra
	public ResultSet getBranchesAllFood() {
		
		ResultSet results = null;
		String brancesAllFoodSql = "SELECT BID FROM Branch B " +
		                           "WHERE NOT EXISTS " + 
		                              "((SELECT F.FID FROM Food F) MINUS " +
		                                  "(SELECT M.FID FROM Menu M " +
		                                  "WHERE M.BID = B.BID))";
		try {	
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			results = stmt.executeQuery(brancesAllFoodSql);

		} catch(SQLException ex) {
			System.out.println("Message: " + ex.getMessage());
			ex.printStackTrace();
		}

		return results;
	}

	// AGGREGATION: Will return the SUM, AVG, MIN, or MAX of all purchases made by a customer and will return this
	// value for all customers.
	public ResultSet agg(String op){
		ResultSet results = null;
		String sql;
		if(op == "AVG"){
			sql = "select c.email, AVG(o.paidPrice) as Average_Price FROM Customer c, Order1 o WHERE c.email = o.email GROUP BY c.email";
		}
		else if(op == "SUM"){
			sql = "select c.email, SUM(o.paidPrice) as Total_Spending FROM Customer c, Order1 o WHERE c.email = o.email GROUP BY c.email";
		}
		else if(op == "MIN"){
			sql = "select c.email, MIN(o.paidPrice) as Min_Purchase FROM Customer c, Order1 o WHERE c.email = o.email GROUP BY c.email";
		}
		else{
			sql = "select c.email, MAX(o.paidPrice) as Max_Purchase FROM Customer c, Order1 o WHERE c.email = o.email GROUP BY c.email";
		}

		try {	
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			results = stmt.executeQuery(sql);

		} catch(SQLException ex) {
			System.out.println("Message: " + ex.getMessage());
			ex.printStackTrace();
		}

		return results;
	}

	// NESTED AGGREGATION: Will return the MIN or MAX of over all the customers of the above query
	public ResultSet nestedAgg(String op, String op2){
		ResultSet results = null;
		String sql;
		if(op == "AVG"){
			sql = "select c.email, AVG(o.paidPrice) as " + op2 + "_Average_Price FROM Customer c, Order1 o WHERE c.email = o.email GROUP BY c.email ";
		}
		else if(op == "SUM"){
			sql = "select c.email, SUM(o.paidPrice) as " + op2 + "_Total_Spending FROM Customer c, Order1 o WHERE c.email = o.email GROUP BY c.email ";
		}
		else if(op == "MIN"){
			sql = "select c.email, MIN(o.paidPrice) as " + op2 + "_Min_Purchase FROM Customer c, Order1 o WHERE c.email = o.email GROUP BY c.email ";
		}
		else{
			sql = "select c.email, MAX(o.paidPrice) as " + op2 + "_Max_Purchase FROM Customer c, Order1 o WHERE c.email = o.email GROUP BY c.email ";
		}

		if(op2 == "MIN"){
			sql += "HAVING "+ op +"(o.paidPrice) <= all (SELECT " + op + "(o2.paidPrice) FROM Order1 o2 GROUP BY o2.email)";
		}
		else{
			sql += "HAVING " + op + "(o.paidPrice) >= all (SELECT " + op + "(o2.paidPrice) FROM Order1 o2 GROUP BY o2.email)";
		}

		try {	
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			results = stmt.executeQuery(sql);

		} catch(SQLException ex) {
			System.out.println("Message: " + ex.getMessage());
			ex.printStackTrace();
		}

		return results;
	}

	public ResultSet getBranchRatings() {
		
		ResultSet results = null;
		try {	
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			results = stmt.executeQuery("SELECT BID, AVG(rating) FROM  Review R" +
										"GROUP BY BID ORDER BY AVG(rating) DESC");
		} catch(SQLException ex) {
			System.out.println("Message: " + ex.getMessage());
			ex.printStackTrace();
		}

		return results;
	}
}
