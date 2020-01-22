package edu.ucdavis.ucdh.stu.core.resources;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

public class RestResourceInitializer extends Application {

	/**
	 * Gets the classes.
	 *
	 * @return the classes
	 */
	public Set<Class<?>> getClasses() {
		Set<Class<?>> classes = new HashSet<Class<?>>();

		// Resources
		classes.add(org.glassfish.jersey.jackson.JacksonFeature.class);
		classes.add(org.glassfish.jersey.server.spring.scope.RequestContextFilter.class);
		classes.add(org.glassfish.jersey.media.multipart.MultiPartFeature.class);

		// Rest classes within Application.
		classes.add(BatchJobInstanceResource.class);
		classes.add(BatchJobResource.class);
		classes.add(BatchJobScheduleResource.class);
		classes.add(ConfigPropertyResource.class);
		classes.add(MediaResource.class);
		classes.add(MenuItemResource.class);
		classes.add(PageResource.class);
		classes.add(UserResource.class);

		return classes;
	}
}