package net.gadgetbadget.ws;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
	
private static ProductDAO instance;
	
	private static Connection connection;
	private PreparedStatement preparedStatement;
	
	private ProductDAO() {};
	
	public static ProductDAO getInstance() {
		if(instance == null) {
			instance = new ProductDAO();
		}
		return instance;
	}
	
	public List<Product> ListAll(){
		List<Product>products  = new ArrayList<Product>();
		
		try {
			connection  = DatabaseUtils.getConnection();
			preparedStatement = connection.prepareStatement("select * from product ");
			
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()) {
				int product_ID = rs.getInt("product_ID");
				String product_Name = rs.getString("product_Name");
				String product_Description = rs.getString("product_Description");
				String manufactured_date =rs.getString("manufactured_date");
				float price = rs.getFloat("price");
			
				Product product = new Product(product_ID, product_Name, product_Description, manufactured_date, price);
				products.add(product);		
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		
		return products;
	}
	
	public int add(Product product) {		
		try {
			connection = DatabaseUtils.getConnection();
			preparedStatement = connection.prepareStatement("insert into product(product_Name, product_Description, manufactured_date, price) values(?, ?, ?, ?)", 
					Statement.RETURN_GENERATED_KEYS);
			
			preparedStatement.setString(1, product.getProduct_Name());
			preparedStatement.setString(2, product.getProduct_Description());
			preparedStatement.setString(3, product.getManufactured_date());
			preparedStatement.setFloat(4, product.getPrice());
			
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
	                throw new SQLException("Adding product failed, no ID obtained.");
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
	
	public Product get(int id) {
		Product product = null;
		try {
			connection = DatabaseUtils.getConnection();
			preparedStatement = connection.prepareStatement("select * from product where product_Id = ?");
			preparedStatement.setInt(1, id);
			
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				
				int product_ID = rs.getInt("product_ID");
				String product_Name = rs.getString("product_Name");
				String product_Description = rs.getString("product_Description");
				String manufactured_date =rs.getString("manufactured_date");
				float price = rs.getFloat("price");
			
				
				 product = new Product(product_ID, product_Name, product_Description, manufactured_date, price);

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
		
		return product;		
		
	}
	
	public boolean update(Product product) {
		try {
			connection = DatabaseUtils.getConnection();
			preparedStatement = connection.prepareStatement("Update product set product_Name =?, product_Description = ?, manufactured_date =?, price =? where product_Id = ?");
				
			preparedStatement.setString(1, product.getProduct_Name());
			preparedStatement.setString(2, product.getProduct_Description());
			preparedStatement.setString(3, product.getManufactured_date());
			preparedStatement.setFloat(4, product.getPrice());
			preparedStatement.setInt(5, product.getProduct_Id());
			
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
			preparedStatement = connection.prepareStatement("delete from product where product_Id = ?");
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