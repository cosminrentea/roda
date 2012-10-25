package dbext;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the users database table.
 * 
 */
@Entity
@Table(name="users")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Integer id;

	@Column(name="credential_provider", nullable=false, length=20)
	private String credentialProvider;

	//bi-directional many-to-one association to AuthData
	@OneToMany(mappedBy="user")
	private List<AuthData> authData;

	//bi-directional many-to-one association to UserAuthLog
	@OneToMany(mappedBy="user")
	private List<UserAuthLog> userAuthLogs;

	//bi-directional many-to-one association to UserMessage
	@OneToMany(mappedBy="user")
	private List<UserMessage> userMessages;

	//bi-directional many-to-many association to Role
    @ManyToMany
	@JoinTable(
		name="user_role"
		, joinColumns={
			@JoinColumn(name="user_id", nullable=false)
			}
		, inverseJoinColumns={
			@JoinColumn(name="role_id", nullable=false)
			}
		)
	private List<Role> roles;

	//bi-directional many-to-one association to UserSettingValue
	@OneToMany(mappedBy="user")
	private List<UserSettingValue> userSettingValues;

    public User() {
    }

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCredentialProvider() {
		return this.credentialProvider;
	}

	public void setCredentialProvider(String credentialProvider) {
		this.credentialProvider = credentialProvider;
	}

	public List<AuthData> getAuthData() {
		return this.authData;
	}

	public void setAuthData(List<AuthData> authData) {
		this.authData = authData;
	}
	
	public List<UserAuthLog> getUserAuthLogs() {
		return this.userAuthLogs;
	}

	public void setUserAuthLogs(List<UserAuthLog> userAuthLogs) {
		this.userAuthLogs = userAuthLogs;
	}
	
	public List<UserMessage> getUserMessages() {
		return this.userMessages;
	}

	public void setUserMessages(List<UserMessage> userMessages) {
		this.userMessages = userMessages;
	}
	
	public List<Role> getRoles() {
		return this.roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
	public List<UserSettingValue> getUserSettingValues() {
		return this.userSettingValues;
	}

	public void setUserSettingValues(List<UserSettingValue> userSettingValues) {
		this.userSettingValues = userSettingValues;
	}
	
}