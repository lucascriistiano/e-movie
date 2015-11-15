package br.ufrn.imd.emovie.model;

import java.io.Serializable;
import java.lang.Integer;
import java.lang.String;
import java.util.Date;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: User
 *
 */
@Entity
@Table(schema = "public", name = "user")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	@Column(name = "id_user")
	private Integer id;
	private String name;
	private String password;
	private String email;
	
	@Enumerated(EnumType.ORDINAL)
	private UserType type;
	
	@Column(name = "created_at")
	private Date createdAt;

	public User() {
		super();
	}
	
	public User(String name, String password, String email, UserType type, Date createdAt) {
		this();
		this.name = name;
		this.password = password;
		this.email = email;
		this.type = type;
		this.createdAt = createdAt;
	}

	public User(Integer id, String name, String password, String email, UserType type, Date createdAt) {
		this(name, password, email, type, createdAt);
		this.id = id;
	}
	
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public UserType getType() {
		return type;
	}

	public void setType(UserType type) {
		this.type = type;
	}

	public String getFirstName() {
		return this.name.trim().split(" ")[0];
	}
	
	public boolean compareLogin(User anotherUser) {
		if(email.equals(anotherUser.getEmail()) && password.equals(anotherUser.getPassword())) {
			return true;
		}
		return false;
	}

}
