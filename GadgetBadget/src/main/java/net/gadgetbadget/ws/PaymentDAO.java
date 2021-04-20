package net.gadgetbadget.ws;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAO {
	
private static PaymentDAO instance;
	
	private static Connection connection;
	private PreparedStatement preparedStatement;
	
	private PaymentDAO() {};
	
	public static PaymentDAO getInstance() {
		if(instance == null) {
			instance = new PaymentDAO();
		}
		return instance;
	}
	
	public List<Payment> ListAll(){
		//return new ArrayList<Payment>(data);
		List<Payment> payments = new ArrayList<Payment>();
		
		try {
			connection  = DatabaseUtils.getConnection();
			preparedStatement = connection.prepareStatement("select * from payments");
			
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()) {
				int payment_ID = rs.getInt("payment_ID");
				String payment_type = rs.getString("payment_type");
				float amount = rs.getFloat("amount");
				String date = rs.getString("date");
			
				Payment payment = new Payment(payment_ID, payment_type, amount, date);
				payments.add(payment);		
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		
		return payments;
	}
	
	public int add(Payment payment) {		
		try {
			connection = DatabaseUtils.getConnection();
			preparedStatement = connection.prepareStatement("insert into payments(payment_type, amount, date) values(?, ?, ?)", 
					Statement.RETURN_GENERATED_KEYS);
			
			preparedStatement.setString(1, payment.getPayment_type());
			preparedStatement.setFloat(2, payment.getAmount());
			preparedStatement.setString(3, payment.getDate());
			
			// Add Product
			int affectedRows = preparedStatement.executeUpdate();
			
			if (affectedRows == 0) {
	            throw new SQLException("Adding payment failed, no rows affected.");
	        }

	        try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	                return (int) generatedKeys.getLong(1);
	            }
	            else {
	                throw new SQLException("Adding payment failed, no ID obtained.");
	            }
	        }
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			/*
			 * Close prepared statement and database connectivity at the end of
			 * transaction
			 */
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return 0;
		
	}
	
	public Payment get(int id) {
		Payment payment = null;
		try {
			connection = DatabaseUtils.getConnection();
			preparedStatement = connection.prepareStatement("select * from payments where payment_ID= ?");
			preparedStatement.setInt(1, id);
			
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				int payment_ID = rs.getInt("payment_ID");
				String payment_type = rs.getString("payment_type");
				float amount = rs.getFloat("amount");
				String date = rs.getString("date");
				
				 payment = new Payment(payment_ID, payment_type, amount, date);

			}
				
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			/*
			 * Close prepared statement and database connectivity at the end of
			 * transaction
			 */
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return payment;	
		
		
	}
	
	public boolean update(Payment payment) {
		try {
			connection = DatabaseUtils.getConnection();
			preparedStatement = connection.prepareStatement("Update payments set payment_type =?, amount = ?, date =? where payment_ID = ?");
				
			preparedStatement.setString(1, payment.getPayment_type());
			preparedStatement.setFloat(2, payment.getAmount());
			preparedStatement.setString(3, payment.getDate());
			preparedStatement.setInt(4, payment.getPayment_ID());

			int affectedRows = preparedStatement.executeUpdate();
			if(affectedRows==1) {
				return true;
			}		
			//preparedStatement.executeUpdate();	
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return false;
	}
	
	public boolean delete(int id) {
		try {
			connection = DatabaseUtils.getConnection();
			preparedStatement = connection.prepareStatement("delete from payments where payment_ID = ?");
			preparedStatement.setInt(1, id);			
			
			int affectedRows = preparedStatement.executeUpdate();
			if(affectedRows==1) {
				return true;
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

}
