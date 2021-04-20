package net.gadgetbadget.ws;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import net.gadgetbadget.ws.DatabaseUtils;
import net.gadgetbadget.ws.Project;

public class ProjectDAO {
	
	private static ProjectDAO instance;
	
	private static Connection connection;
	private PreparedStatement preparedStatement;
	
	private ProjectDAO() {};
	
	public static ProjectDAO getInstance() {
		if(instance == null) {
			instance = new ProjectDAO();
		}
		return instance;
	}
	
	public List<Project> ListAll(){
		//return new ArrayList<Product>(data);
		List<Project> projects = new ArrayList<Project>(); 
		
		try {
			connection  = DatabaseUtils.getConnection();
			preparedStatement = connection.prepareStatement("select * from projects");
			
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()) {
				int proj_Id = rs.getInt("proj_Id");
				String proj_Name = rs.getString("proj_Name");
				String proj_Description = rs.getString("proj_Description");
				int proj_Duration = rs.getInt("proj_Duration");
				float proj_Budget = rs.getFloat("proj_Budget");
				int user_Id = rs.getInt("user_Id");
			
				Project project = new Project(proj_Id, proj_Name, proj_Description, proj_Duration, proj_Budget, user_Id);
				projects.add(project);
				
				
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		
		return projects;
	}
	
	public int add(Project project) {		
		try {
			connection = DatabaseUtils.getConnection();
			preparedStatement = connection.prepareStatement("insert into projects(proj_Name, proj_Description, proj_Duration, proj_Budget, user_Id) values(?, ?, ?, ?, ?)", 
					Statement.RETURN_GENERATED_KEYS);
			
			preparedStatement.setString(1, project.getProj_Name());
			preparedStatement.setString(2, project.getProj_Description());
			preparedStatement.setInt(3, project.getProj_Duration());
			preparedStatement.setFloat(4, project.getProj_Budget());
			preparedStatement.setInt(5, project.getUser_Id());
			
			// Add Product
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
	
	public Project get(int id) {
//		Product productToFind = new Product(id);
//		
//		int index = data.indexOf(productToFind);
//		if(index >= 0) {
//			return data.get(index);
//		}
//		return null;
		Project project = null;
		try {
			connection = DatabaseUtils.getConnection();
			preparedStatement = connection.prepareStatement("select * from projects where proj_Id= ?");
			preparedStatement.setInt(1, id);
			
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				int proj_Id = rs.getInt("proj_Id");
				String proj_Name = rs.getString("proj_Name");
				String proj_Description = rs.getString("proj_Description");
				int proj_Duration = rs.getInt("proj_Duration");
				float proj_Budget = rs.getFloat("proj_Budget");
				int user_Id = rs.getInt("user_Id");
				
				 project = new Project(proj_Id, proj_Name, proj_Description, proj_Duration, proj_Budget, user_Id);

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
		
		return project;	
		
		
	}
	
	public boolean update(Project project) {
//		int index = data.indexOf(product);
//		if(index >= 0) {
//			data.set(index, product);
//			return true;
//		}
//		return false;
		//int index = data.indexOf(product);
		try {
			connection = DatabaseUtils.getConnection();
			preparedStatement = connection.prepareStatement("Update projects set proj_Name =?, proj_Description = ?, proj_Duration =?, proj_Budget = ? , user_Id = ? where proj_Id = ?");
				
			preparedStatement.setString(1, project.getProj_Name());
			preparedStatement.setString(2, project.getProj_Description());
			preparedStatement.setInt(3, project.getProj_Duration());
			preparedStatement.setFloat(4, project.getProj_Budget());
			preparedStatement.setInt(5, project.getUser_Id());
			preparedStatement.setInt(6, project.getProj_Id());

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
//		Product productToFind = new Product(id);
//		
//		int index = data.indexOf(productToFind);
//		if(index >= 0) {
//			 data.remove(index);
//			 return true;
//		}
//		return false;
		//Product productToFind = new Product(id);
		//int index = data.indexOf(productToFind);
		try {
			connection = DatabaseUtils.getConnection();
			preparedStatement = connection.prepareStatement("delete from projects where proj_Id = ?");
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
