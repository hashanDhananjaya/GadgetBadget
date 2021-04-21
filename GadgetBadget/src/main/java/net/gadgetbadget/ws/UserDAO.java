package net.gadgetbadget.ws;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
	
private static UserDAO instance;
	
	private static Connection connection;
	private PreparedStatement preparedStatement;
	
	private UserDAO() {};
	
	public static UserDAO getInstance() {
		if(instance == null) {
			instance = new UserDAO();
		}
		return instance;
	}
	
	public List<User> ListAll(){
		//return new ArrayList<User>(data);
		List<User> users = new ArrayList<User>(); 
		
		try {
			connection  = DatabaseUtils.getConnection();
			preparedStatement = connection.prepareStatement("select * from users");
			
			ResultSet res = preparedStatement.executeQuery();
			while(res.next()) {
				int userId = res.getInt("user_Id");
				String userType = res.getString("user_Type");
				String userName = res.getString("user_Name");
				String email = res.getString("user_email");
				String phone = res.getString("user_phone");
				String address = res.getString("user_address");
			
				User user = new User(userId, userType, userName, email, phone, address);
				users.add(user);
				
				
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		
		return users;
	}
	
	public int add(User user) {		
		try {
			connection = DatabaseUtils.getConnection();
			preparedStatement = connection.prepareStatement("insert into users(user_Type, user_Name, user_email, user_phone, user_address) values(?, ?, ?, ?, ?)", 
					Statement.RETURN_GENERATED_KEYS);
			
			preparedStatement.setString(1, user.getUserType());
			preparedStatement.setString(2, user.getUserName());
			preparedStatement.setString(3, user.getEmail());
			preparedStatement.setString(4, user.getPhone());
			preparedStatement.setString(5, user.getAddress());
			
			// Add User
			int affectedRows = preparedStatement.executeUpdate();
			
			if (affectedRows == 0) {
	            throw new SQLException("Adding project failed, no rows affected.");
	        }

	        try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	                return (int) generatedKeys.getLong(1);
	            }
	            else {
	                throw new SQLException("Adding project failed, no ID obtained.");
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
	public User get(int id) {
		User user = null;
		try {
			connection = DatabaseUtils.getConnection();
			preparedStatement = connection.prepareStatement("select * from users where user_Id= ?");
			preparedStatement.setInt(1, id);
			
			ResultSet res = preparedStatement.executeQuery();
			while (res.next()) {
				int userId = res.getInt("user_Id");
				String userType = res.getString("user_Type");
				String userName = res.getString("user_Name");
				String email = res.getString("user_email");
				String phone = res.getString("user_phone");
				String address = res.getString("user_address");
				
				 user = new User(userId, userType, userName, email, phone, address);

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
		
		return user;	
		
		
	}
	
	public boolean update(User user) {
		try {
			connection = DatabaseUtils.getConnection();
			preparedStatement = connection.prepareStatement("Update users set user_Type =?, user_Name = ?, user_email =?, user_phone = ? , user_address = ? where user_Id = ?");
				
			preparedStatement.setString(1, user.getUserType());
			preparedStatement.setString(2, user.getUserName());
			preparedStatement.setString(3, user.getEmail());
			preparedStatement.setString(4, user.getPhone());
			preparedStatement.setString(5, user.getAddress());
			preparedStatement.setInt(6, user.getUserId());

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
			preparedStatement = connection.prepareStatement("delete from users where user_Id = ?");
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
	
