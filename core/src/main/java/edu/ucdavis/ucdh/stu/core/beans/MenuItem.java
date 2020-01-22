package edu.ucdavis.ucdh.stu.core.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.hibernate.annotations.IndexColumn;

/**
 * <p>This entity bean defines a single menu item.</p>
 */
@SuppressWarnings("deprecation")
@Entity
@Table(name = "menuitem")
@XmlRootElement
@XmlType(propOrder={"id", "context", "name", "sequence", "label", "destination", "authorizedRoles", "menuItem", "creationDate", "createdBy", "lastUpdate", "lastUpdateBy"})
public class MenuItem {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id = null;
	@Column(name = "context")
	private String context = null;
	@Column(name = "name")
	private String name = null;
	@ManyToOne
	@JoinColumn(name="parentId")
	private MenuItem parent = null;
	@Column(name = "sequence")
	private Integer sequence = null;
	@Column(name = "label")
	private String label = null;
	@Column(name = "destination")
	private String destination = null;
	@Column(name = "authorizedRoles")
	private String authorizedRoles = null;
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="parentId")
	@IndexColumn(name="sequence")
	private List<MenuItem> menuItem = null;
	@Column(name = "creationDate")
	private Date creationDate = null;
	@Column(name = "createdBy")
	private String createdBy = null;
	@Column(name = "lastUpdate")
	private Date lastUpdate = null;
	@Column(name = "lastUpdateBy")
	private String lastUpdateBy = null;

	/**
	 * <p>Adds the passed menu item to the bottom of the list.</p>
	 * 
	 * @param menuItem the MenuItem to add
	 */
	public void addMenuItem(MenuItem newItem) {
		if (newItem != null) {
			if (menuItem == null) {
				menuItem = new ArrayList<MenuItem>();
			}
			newItem.setContext(context);
			newItem.setName(name);
			newItem.setParent(this);
			newItem.setSequence(menuItem.size());
			menuItem.add(newItem);
		}
	}

	public void afterUnmarshal(Unmarshaller u, Object parent) {
		this.parent = (MenuItem) parent;
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
	 * @return the parent
	 */
	@XmlTransient
	public MenuItem getParent() {
		return parent;
	}
	/**
	 * @param parent the parent to set
	 */
	public void setParent(MenuItem parent) {
		this.parent = parent;
	}
	/**
	 * @return the sequence
	 */
	public Integer getSequence() {
		return sequence;
	}
	/**
	 * @param sequence the sequence to set
	 */
	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}
	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}
	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}
	/**
	 * @return the destination
	 */
	public String getDestination() {
		return destination;
	}
	/**
	 * @param destination the destination to set
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	}
	/**
	 * @return the authorizedRoles
	 */
	public String getAuthorizedRoles() {
		return authorizedRoles;
	}
	/**
	 * @param authorizedRoles the authorizedRoles to set
	 */
	public void setAuthorizedRoles(String authorizedRoles) {
		this.authorizedRoles = authorizedRoles;
	}
	/**
	 * @return the menuItem
	 */
	@XmlElementWrapper(name = "children")
	public List<MenuItem> getMenuItem() {
		return menuItem;
	}
	/**
	 * @param menuItem the menuItem to set
	 */
	public void setMenuItem(List<MenuItem> menuItem) {
		this.menuItem = menuItem;
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
