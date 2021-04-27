package member.model;

import java.util.Date;

public class EntMember {

	private String id;
	private String name;
	private String password;
	private String email;
	private String entname;
	private Date regDate;
	
	public EntMember(String id, String name, String password, String email, String entname, Date regDate) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.email = email;
		this.entname = entname;
		this.regDate = regDate;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public String getEmail() {
		return email;
	}

	public String getEntname() {
		return entname;
	}

	public Date getRegDate() {
		return regDate;
	}
	
	public boolean matchPassword(String pwd) {
		return password.equals(pwd);
	}
	
	public void changePassword(String newPwd) {
		this.password = newPwd;
	}
}
