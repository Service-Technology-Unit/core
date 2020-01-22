package edu.ucdavis.ucdh.stu.core.utils;

import java.io.StringWriter;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

/**
 * <p>This class is a simple wrapper for Velocity.</p>
 */
public class SimpleVelocityService {
	private static final Log log = LogFactory.getLog(SimpleVelocityService.class);
	private static SimpleVelocityService STATIC_SERVICE = null;

	/**
	 * <p>Constructs a new SimpleVelocityService.</p>
	 */
	private SimpleVelocityService() {
		try {
			Velocity.init();
		} catch (Exception e) {
			log.error("Exception initializing SimpleVelocityService: Velocity runtime engine initialization failure: " + e, e);
		}
	}

	/**
	 * <p>Uses Velocity to evaluate the template using the source data for the
	 * Velocity context.</p>
	 * 
	 * @param template the velocity template
	 * @param sourceData the source data used for the Velocity context
	 * @return the resolved template
	 */
	public static String evaluate(String template, Object sourceData) {
		return getService().evaluateTemplate(template, sourceData);
	}

	/**
	 * <p>Uses Velocity to evaluate the template using the source data for the
	 * Velocity context.</p>
	 * 
	 * @param template the velocity template
	 * @param sourceData the source data used for the Velocity context
	 * @return the resolved template
	 */
	@SuppressWarnings("rawtypes")
	private String evaluateTemplate(String template, Object sourceData) {
		StringWriter writer = new StringWriter();

		VelocityContext context = null;
		if (sourceData.getClass().isAssignableFrom(VelocityContext.class)) {
			context = (VelocityContext) sourceData;
		} else {
			try {
				Map values = (Map) sourceData;
				context = new VelocityContext(values);
			} catch (ClassCastException e) {
				context = new VelocityContext();
			}
		}

		try {
			Velocity.evaluate(context, writer, getClass().getName(), template);
		} catch (Exception e) {
			log.error("Exception caught on SimpleVelocityService.evaluate(): " + e, e);
		}

		return writer.toString();
	}

	/**
	 * <p>Returns the currently active service object.</p>
	 * 
	 * @return the currently active service object
	 */
	public static SimpleVelocityService getService() {
		if (STATIC_SERVICE == null) {
			STATIC_SERVICE = new SimpleVelocityService();
		}
		return STATIC_SERVICE;
	}
}
