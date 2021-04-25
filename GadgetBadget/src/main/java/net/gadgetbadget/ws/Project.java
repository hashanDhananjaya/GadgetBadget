package net.gadgetbadget.ws;

public class Project {

	private int proj_Id;
	private String proj_Name;
	private String proj_Description;
	private int proj_Duration;
	private float proj_Budget;
	private int user_Id;
	
	public Project() {
		
	}

	public Project(int proj_Id, String proj_Name, String proj_Description, int proj_Duration, float proj_Budget,
			int user_Id) {
		this.proj_Id = proj_Id;
		this.proj_Name = proj_Name;
		this.proj_Description = proj_Description;
		this.proj_Duration = proj_Duration;
		this.proj_Budget = proj_Budget;
		this.user_Id = user_Id;
	}

	public int getProj_Id() {
		return proj_Id;
	}

	public void setProj_Id(int proj_Id) {
		this.proj_Id = proj_Id;
	}

	public String getProj_Name() {
		return proj_Name;
	}

	public void setProj_Name(String proj_Name) {
		this.proj_Name = proj_Name;
	}

	public String getProj_Description() {
		return proj_Description;
	}

	public void setProj_Description(String proj_Description) {
		this.proj_Description = proj_Description;
	}

	public int getProj_Duration() {
		return proj_Duration;
	}

	public void setProj_Duration(int proj_Duration) {
		this.proj_Duration = proj_Duration;
	}

	public float getProj_Budget() {
		return proj_Budget;
	}

	public void setProj_Budget(float proj_Budget) {
		this.proj_Budget = proj_Budget;
	}

	public int getUser_Id() {
		return user_Id;
	}

	public void setUser_Id(int user_Id) {
		this.user_Id = user_Id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + proj_Id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Project other = (Project) obj;
		if (proj_Id != other.proj_Id)
			return false;
		return true;
	}
	
	
}
