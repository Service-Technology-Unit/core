package edu.ucdavis.ucdh.stu.core.resources;

import java.util.Date;
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

import edu.ucdavis.ucdh.stu.core.beans.ConfigProperty;
import edu.ucdavis.ucdh.stu.core.manager.ConfigPropertyManager;

@Path("/property/{context}")
@Component
@Scope("request")
public class ConfigPropertyResource {
	private Log log = LogFactory.getLog(getClass());
	private ConfigPropertyManager configPropertyManager;

	@GET
	@Path("/")
	@Produces("text/xml")
	public List<ConfigProperty> getConfigProperty(@PathParam("context") String context) throws NotFoundException {
		List<ConfigProperty> configProperties = configPropertyManager.findByContext(context);
		if (configProperties == null || configProperties.size() == 0) {
			throw new NotFoundException("No such configuration properties.");
		}
		if (log.isDebugEnabled()) {
			log.debug("Returning " + configProperties.size() + " properties.");
		}
	return configProperties;
	}

	@GET
	@Path("/{name}")
	@Produces("text/xml")
	public ConfigProperty getConfigProperty(@PathParam("context") String context, @PathParam("name") String name) throws NotFoundException {
		ConfigProperty configProperty = configPropertyManager.findByContextAndPropertyName(context, name);
		if (configProperty == null) {
			throw new NotFoundException("No such configuration property.");
		}
		return configProperty;
	}

	@POST
	@Path("/{name}")
	@Consumes("application/x-www-form-urlencoded")
	@Produces("text/xml")
	public ConfigProperty saveConfigProperty(@PathParam("context") String context, @PathParam("name") String name, @FormParam("value") String value) {
		ConfigProperty configProperty = configPropertyManager.findByContextAndPropertyName(context, name);

		String userId = "ConfigPropertyResource";
		Date rightNow = new Date();
		if (configProperty == null) {
			configProperty = new ConfigProperty();
			configProperty.setContext(context);
			configProperty.setName(name);
			configProperty.setCreationDate(rightNow);
			configProperty.setCreatedBy(userId);
		}
		configProperty.setValue(value);
		configProperty.setLastUpdate(rightNow);
		configProperty.setLastUpdateBy(userId);
		configPropertyManager.save(configProperty);

		return configPropertyManager.findByContextAndPropertyName(context, name);
	}

	@DELETE
	@Path("/{name}")
	@Produces("text/xml")
	public String deleteConfigProperty(@PathParam("context") String context, @PathParam("name") String name) throws NotFoundException {
		ConfigProperty configProperty = configPropertyManager.findByContextAndPropertyName(context, name);
		if (configProperty == null) {
			throw new NotFoundException("No such configuration property.");
		}
		configPropertyManager.delete(configProperty);
		return "<message>Property deleted.</message>";
	}

	/**
	 * @param configPropertyManager the configPropertyManager to set
	 */
	public void setConfigPropertyManager(ConfigPropertyManager configPropertyManager) {
		this.configPropertyManager = configPropertyManager;
	}
}
