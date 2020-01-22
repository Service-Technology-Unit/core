package edu.ucdavis.ucdh.stu.core.beans;

/**
 * <p>This entity bean defines a single person.</p>
 */
public class Person {
	private String id = null;
	private String uri = null;
	private String name = null;
	private String email = null;

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
}
