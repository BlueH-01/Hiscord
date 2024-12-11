package models;

public class User {
	private int id;
	private String password;
	private String username;
	private String userImageUrl;
	
	public User(int id,String password,String username) {
		this.id=id;
		this.password=password;
		this.username=username;
	}
	public void setUserImage(String userImageUrl) {
		this.userImageUrl=userImageUrl;
	}
	public int getId() {
		return id;
	}
	public String getPassword() {
		return password;
	}
	public String getUsername() {
		return username;
	}
	public String getUserImageUrl() {
		return userImageUrl;
	}
	
}
