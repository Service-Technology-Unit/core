package edu.ucdavis.ucdh.stu.core.servlets.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.lang.StringUtils;

import edu.ucdavis.ucdh.stu.core.beans.Media;
import edu.ucdavis.ucdh.stu.core.manager.MediaManager;
import edu.ucdavis.ucdh.stu.core.servlets.ServletBase;

/**
 * <p>This servlet provides media upload services.</p>
 */
public class UploadServlet extends ServletBase {
	private static final long serialVersionUID = 1;
	private String redirectDestination = "uploadmedia.html";
	private MediaManager mediaManager;

	/**
	 * <p>The Servlet "service" method.</p>
	 *
	 * @param req the <code>HttpServletRequest</code> object
	 * @param res the <code>HttpServletResponse</code> object
	 * @throws ServletException
	 * @throws IOException
	 */
	public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String method = req.getMethod();
		if ("post".equalsIgnoreCase(method)) {
			doPost(req, res);
		} else {
			sendError(req, res, 405, "Method Not Allowed. The \"" + method.toUpperCase() + "\" is not appropriate for this resource.");
		}
	}

	/**
	 * <p>The Servlet "doPost" method -- this method is used to process
	 * files uploaded with an HTTP "POST" method.</p>
	 *
	 * @param req the <code>HttpServletRequest</code> object
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String error = "false";

		// Check that we have a file upload request
		if (ServletFileUpload.isMultipartContent(req)) {

			Media userInput = new Media();
			Properties formFields = new Properties();
			boolean fileFound = false;

			// Create a new file upload handler
			ServletFileUpload upload = new ServletFileUpload();

			// Go through the uploaded items
			try {
				FileItemIterator i = upload.getItemIterator(req);
				while (i.hasNext()) {
					FileItemStream item = i.next();
					if (item.isFormField()) {
						formFields.setProperty(item.getFieldName(), Streams.asString(item.openStream()));
					} else {
						if (fileFound) {
							log.warn("Additional file (\"" + item.getName() + "\") found in request parameters has been ignored.");
						} else {
							userInput.setContentType(item.getContentType());
							if ("application/x-javascript".equalsIgnoreCase(userInput.getContentType())) {
								userInput.setContentType("text/javascript");
							}
							if (userInput.getContentType().startsWith("text")) {
								userInput.setContent(Streams.asString(item.openStream()));
							} else {
								userInput.setBinaryContent(getBinaryContent(item));
							}
							userInput.setName(item.getName());
							fileFound = true;
						}
				    }
				}
				String context = formFields.getProperty("context");
				String location = formFields.getProperty("location");
				String name = formFields.getProperty("name");
				if (StringUtils.isNotEmpty(context) && StringUtils.isNotEmpty(name)) {
					Date rightNow = new Date();
					String userId = req.getRemoteUser();
					Media media = mediaManager.findByContextLocationAndName(context, location, name);
					if (media == null) {
						media = new Media();
						media.setContext(context);
						media.setLocation(location);
						if (StringUtils.isNotEmpty(name)) {
							media.setName(name);
						} else {
							media.setName(userInput.getName());
						}
						media.setCreationDate(rightNow);
						media.setCreatedBy(userId);
					}
					media.setDescription(formFields.getProperty("description"));
					if (StringUtils.isEmpty(media.getDescription())) {
						media.setDescription("Uploaded by " + req.getRemoteUser() + " from " + req.getRemoteAddr() + " on " + rightNow);
					}
					media.setContentType(userInput.getContentType());
					media.setContent(userInput.getContent());
					media.setBinaryContent(userInput.getBinaryContent());
					media.setLastUpdate(rightNow);
					media.setLastUpdateBy(userId);
					mediaManager.save(media);
				} else {
					log.error("The required parameter(s) context and/or name were not present in the request.");
					error = HttpServletResponse.SC_BAD_REQUEST + "";
				}
			} catch (FileUploadException e) {
				log.error("Exception encountered handling the uploaded file.", e);
				error = HttpServletResponse.SC_INTERNAL_SERVER_ERROR + "";
			}
		} else {
			log.error("This is not a multi-part request.");
			error = HttpServletResponse.SC_EXPECTATION_FAILED + "";
		}

		res.sendRedirect(redirectDestination + "?error=" + error);
	}

	/**
	 * <p>This method returns the binary content of the file item.</p>
	 * 
	 * @param item the <code>FileItemStream</code> object
	 * @return the the binary content of the file item
	 */
	private byte[] getBinaryContent(FileItemStream item) {
		byte[] content = new byte[0];

		BufferedInputStream is = null;								
		ByteArrayOutputStream os = null;
		BufferedOutputStream bos = null;
		try {
			is = new BufferedInputStream(item.openStream());
			os = new ByteArrayOutputStream();
			bos = new BufferedOutputStream(os);
			int bytesRead = 0;
			byte[] buffer = new byte[is.available()];
			while((bytesRead = is.read(buffer, 0, buffer.length)) != -1) {						    
				bos.write(buffer, 0, bytesRead);
			}
			bos.flush();
			content = os.toByteArray();
		} catch (Exception e) {
			log.error("Exception encountered processing file upload: " + e, e);
		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (Exception e) {
					log.warn("Exception encountered closing buffered output stream: " + e, e);
				}
			}
			if (is != null) {
				try {
					is.close();
				} catch (Exception e) {
					log.warn("Exception encountered closing buffered input stream: " + e, e);
				}
			}
		}
		return content;
	}

	/**
	 * @param redirectDestination the redirectDestination to set
	 */
	public void setRedirectDestination(String redirectDestination) {
		this.redirectDestination = redirectDestination;
	}

	/**
	 * @param mediaManager the mediaManager to set
	 */
	public void setMediaManager(MediaManager mediaManager) {
		this.mediaManager = mediaManager;
	}
}
