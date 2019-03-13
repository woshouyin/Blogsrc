package database.gas;

import java.sql.Date;

public class UserGas {
	protected long id;
	protected String name;
	protected Date regDate;
	protected int privilege;
	protected String email;
	
	public UserGas() {
		
	}
	
	public UserGas(long id) {
		this.id = id;
	}
	
	public void addUser() {
		
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public Date getRegDate() {
		return regDate;
	}
	
	public int getPrivilege() {
		return privilege;
	}

	public String getEmail() {
		return email;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	
	public void setPrivilege(int privilege) {
		this.privilege = privilege;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
}
