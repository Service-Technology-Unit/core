package edu.ucdavis.ucdh.stu.core.resources;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javassist.NotFoundException;

import edu.ucdavis.ucdh.stu.core.beans.User;
import edu.ucdavis.ucdh.stu.core.manager.UserManager;

@Path("/user/{context}")
@Component
@Scope("request")
public class UserResource {
	private Log log = LogFactory.getLog(getClass());
	private UserManager userManager;
	
	@GET
	@Path("/")
	@Produces("application/xml")
	public List<User> getUser(@PathParam("context") String context) throws NotFoundException {
		List<User> users = userManager.findByContext(context);
		if (users == null || users.size() == 0) {
			throw new NotFoundException("No such users.");
		}
		if (log.isDebugEnabled()) {
			log.debug("Returning " + users.size() + " users.");
		}
		return users;
	}

	@GET
	@Path("/role/{role}")
	@Produces("application/xml")
		public List<User> getUserbyRole(@PathParam("context") String context, @PathParam("role") String role) throws NotFoundException {
		User user = new User();
		user.setContext(context);
		user.setRoles(role);
		List<User> users = userManager.findByExample(user);
		if (users == null || users.size() == 0) {
			throw new NotFoundException("No such users.");
		}
		if (log.isDebugEnabled()) {
			log.debug("Returning " + users.size() + " users.");
		}
		return users;
	}
	
	@GET
	@Path("/js/usermap.js")
	@Produces("text/javascript")
	public String getUserMap(@PathParam("context") String context) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("var getUserName = {");
		List<User> users = userManager.findByContext(context);
		if (users != null && users.size() > 0) {
			String separator = "";
			Iterator<User> i = users.iterator();
			while (i.hasNext()) {
				User thisUser = i.next();
				buffer.append(separator);
				buffer.append("\n\"");
				buffer.append(thisUser.getUsername());
				buffer.append("\":\"");
				buffer.append(thisUser.getFirstName());
				buffer.append(" ");
				buffer.append(thisUser.getLastName());
				buffer.append("\"");
				separator = ",";
			}
		}
		buffer.append("};");
		if (log.isDebugEnabled()) {
			log.debug("Returning " + buffer);
		}
		return buffer.toString();
	}

	@GET
	@Path("/{name}")
	@Produces("application/xml")
	public User getUser(@PathParam("context") String context, @PathParam("name") String name) throws NotFoundException {
		User user = userManager.findByContextAndName(context, name);
		if (user == null) {
			throw new NotFoundException("No such user.");
		}
		if (log.isDebugEnabled()) {
			log.debug("Returning user " + user.getId());
		}
		return user;
	}

	@POST
	@Path("/{name}")
	@Consumes("application/x-www-form-urlencoded")
	@Produces("application/xml")
	public User saveUser(@PathParam("context") String context,
			@PathParam("name") String name,
			@FormParam("lastName") String lastName,
			@FormParam("firstName") String firstName,
			@FormParam("middleName") String middleName,
			@FormParam("phoneNr") String phoneNr,
			@FormParam("email") String email,
			@FormParam("roles") String roles) {
		User user = userManager.findByContextAndName(context, name);

		String userId = "UserResource";
		Date rightNow = new Date();
		if (user == null) {
			user = new User();
			user.setContext(context);
			user.setName(name);
			user.setCreationDate(rightNow);
			user.setCreatedBy(userId);
		}
		user.setLastName(lastName);
		user.setFirstName(firstName);
		user.setMiddleName(middleName);
		user.setPhoneNr(phoneNr);
		user.setEmail(email);
		user.setRoles(roles);
		user.setLastUpdate(rightNow);
		user.setLastUpdateBy(userId);
		if (log.isDebugEnabled()) {
			log.debug("Updating user " + user.getId());
		}
		userManager.save(user);

		return userManager.findByContextAndName(context, name);
	}

	@DELETE
	@Path("/{name}")
	@Produces("application/xml")
	public String deleteUser(@PathParam("context") String context, @PathParam("name") String name) throws NotFoundException {
		User user = userManager.findByContextAndName(context, name);
		if (user == null) {
			throw new NotFoundException("No such user.");
		}
		if (log.isDebugEnabled()) {
			log.debug("Deleting user " + user.getId());
		}
		userManager.delete(user);
		return "<message>Property deleted.</message>";
	}

	/**
	 * @param userManager the userManager to set
	 */
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}
}
