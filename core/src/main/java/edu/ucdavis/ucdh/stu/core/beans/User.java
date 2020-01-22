package edu.ucdavis.ucdh.stu.core.beans;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * <p>This entity bean defines a single user.</p>
 */
@Entity
@Table(name = "user")
@XmlRootElement
@XmlType(propOrder={"id", "context", "name", "lastName", "firstName", "middleName", "phoneNr", "email", "roles", "creationDate", "createdBy", "lastUpdate", "lastUpdateBy"})
public class User implements UserDetails {
	private static final long serialVersionUID = 1;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id = null;
	@Column(name = "context")
	private String context = null;
	@Column(name = "name")
	private String name = null;
	@Column(name = "lastName")
	private String lastName = null;
	@Column(name = "firstName")
	private String firstName = null;
	@Column(name = "middleName")
	private String middleName = null;
	@Column(name = "phoneNr")
	private String phoneNr = null;
	@Column(name = "email")
	private String email = null;
	@Column(name = "roles")
	private String roles = null;
	@Column(name = "creationDate")
	private Date creationDate = null;
	@Column(name = "createdBy")
	private String createdBy = null;
	@Column(name = "lastUpdate")
	private Date lastUpdate = null;
	@Column(name = "lastUpdateBy")
	private String lastUpdateBy = null;

	/**
	 * <p>Returns the authorities granted to the user. Cannot return <code>null</code>.</p>
	 * 
	 * @return the authorities, sorted by natural key (never <code>null</code>)
	 */
	public Collection<GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		if (StringUtils.isNotEmpty(roles)) {
			String[] role = roles.split(",");
			for (int i=0; i<role.length; i++) {
				authorities.add(new SimpleGrantedAuthority(role[i]));
			}
		}
		return authorities;
	}

	/**
	 * <p>Returns the password used to authenticate the user. Cannot return <code>null</code>.</p>
	 * 
	 * @return the password (never <code>null</code>)
	 */
	public String getPassword() {
		return "password";
	}

	/**
	 * <p>Returns the username used to authenticate the user. Cannot return <code>null</code>.</p>
	 * 
	 * @return the username (never <code>null</code>)
	 */
	public String getUsername() {
		return name;
	}

	/**
	 * <p>Indicates whether the user's account has expired. An expired account cannot be authenticated.</p>
	 * 
	 * @return <code>true</code> if the user's account is valid (ie non-expired), <code>false</code> if no longer valid (ie expired)
	 */
	public boolean isAccountNonExpired() {
		return true;
	}

	/**
	 * <p>Indicates whether the user is locked or unlocked. A locked user cannot be authenticated.</p>
	 * 
	 * @return <code>true</code> if the user is not locked, <code>false</code> otherwise
	 */
	public boolean isAccountNonLocked() {
		return true;
	}

	/**
	 * <p>Indicates whether the user's credentials (password) has expired. Expired credentials prevent authentication.</p>
	 * 
	 * @return <code>true</code> if the user's credentials are valid (ie non-expired), <code>false</code> if no longer valid (ie expired)
	 */
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/**
	 * <p>Indicates whether the user is enabled or disabled. A disabled user cannot be authenticated.</p>
	 * 
	 * @return <code>true</code> if the user is enabled, <code>false</code> otherwise
	 */
	public boolean isEnabled() {
		return true;
	}

	/**
	 * <p>Overrides the <code>toString()</code> method of <code>Object</code>.</p>
	 * 
	 * @return a String representation of this object
	 */
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the context
	 */
	public String getContext() {
		return context;
	}
	/**
	 * @param context the context to set
	 */
	public void setContext(String context) {
		this.context = context;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return the middleName
	 */
	public String getMiddleName() {
		return middleName;
	}
	/**
	 * @param middleName the middleName to set
	 */
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	/**
	 * @return the phoneNr
	 */
	public String getPhoneNr() {
		return phoneNr;
	}
	/**
	 * @param phoneNr the phoneNr to set
	 */
	public void setPhoneNr(String phoneNr) {
		this.phoneNr = phoneNr;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the roles
	 */
	public String getRoles() {
		return roles;
	}
	/**
	 * @param roles the roles to set
	 */
	public void setRoles(String roles) {
		this.roles = roles;
	}
	/**
	 * @return the creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}
	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}
	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	/**
	 * @return the lastUpdate
	 */
	public Date getLastUpdate() {
		return lastUpdate;
	}
	/**
	 * @param lastUpdate the lastUpdate to set
	 */
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	/**
	 * @return the lastUpdateBy
	 */
	public String getLastUpdateBy() {
		return lastUpdateBy;
	}
	/**
	 * @param lastUpdateBy the lastUpdateBy to set
	 */
	public void setLastUpdateBy(String lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}
}
