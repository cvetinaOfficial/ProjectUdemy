package uni.masters.udemy.bean;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import javax.persistence.JoinColumn;

@Entity
@Table(name = "users")
public class UserBean {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="firstName", nullable = false, length = 40)
	private String firstName;
	
	@Column(name="lastName", nullable = false, length = 40)
	private String lastName;
	
	@Column(name="username", nullable = false, unique = true, length = 40)
	private String username;
	
	@Column(name="passwordHash", nullable = false, length = 32)
	private String passwordHash;
	
	@Column(name="cardMoney", nullable = false, precision = 2)
	private double cardMoney;
	
	@Column(name="imagePath")
	private String imagePath;
	
	@Column(name="email", nullable = false, unique = true, length = 256)
	private String email;
	
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles", 
      joinColumns=@JoinColumn(name = "user_id"), 
      inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleBean> roles;
	
	public UserBean() {}
	
	public UserBean(String username, String passwordHash, String email) {
		this.username = username;
		this.passwordHash = passwordHash;
		this.email = email;
	}
	
	public UserBean(String username, String passwordHash, String email,String firstName, String lastName,String imagePath) {
		this.username = username;
		this.passwordHash = passwordHash;
		this.email = email;	
		this.firstName = firstName;
		this.lastName = lastName;
		this.imagePath = imagePath;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPassword(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<RoleBean> getRoles() {
		return roles;
	}

	public void setRoles(Set<RoleBean> roles) {
		this.roles = roles;
	}

	public double getCardMoney() {
		return cardMoney;
	}

	public void setCardMoney(double cardMoney) {
		this.cardMoney = cardMoney;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	@Override
	public String toString() {
		return "UserBean [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", username=" + username
				+ ", passwordHash=" + passwordHash + ", cardMoney=" + cardMoney + ", imagePath=" + imagePath
				+ ", email=" + email + ", roles=" + roles + "]";
	}
	
}
