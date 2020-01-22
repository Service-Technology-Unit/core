package edu.ucdavis.ucdh.stu.core.servlets.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import edu.ucdavis.ucdh.stu.core.beans.Media;
import edu.ucdavis.ucdh.stu.core.manager.MediaManager;
import edu.ucdavis.ucdh.stu.core.servlets.ServletBase;

/**
 * <p>This servlet fetches media content.</p>
 */
public class FetchServlet extends ServletBase {
	private static final long serialVersionUID = 1;
	private String servletPath = "/media/";
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
		if ("get".equalsIgnoreCase(method)) {
			doGet(req, res);
		} else {
			sendError(req, res, 405, "Method Not Allowed. The \"" + method.toUpperCase() + "\" is not appropriate for this resource.");
		}
	}

	/**
	 * <p>The Servlet "doGet" method -- the "GET" method is used to fetch the
	 * binary object from the database as if it were an actual file.</p>
	 *
	 * @param request the <code>HttpServletRequest</code> object
	 * @param response the <code>HttpServletResponse</code> object
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		Media media = getMedia(req);

		if (media == null) {
			res.sendError(404, "The requested resource was not found on this server.");
		} else {
			if (media.getContentType().startsWith("text")) {
				sendTextContent(media, req, res);
			} else {
				if (media.getBinaryContent() != null && media.getBinaryContent().length > 0) {
					sendBinaryContent(media, req, res);
				} else {
					res.sendError(404, "The requested resource was not found on this server.");
				}
			}
		}
	}

	/**
	 * <p>Returns the text content via the response object</p>
	 *
	 * @param media the <code>Media</code> object
	 * @param request the <code>HttpServletRequest</code> object
	 * @param response the <code>HttpServletResponse</code> object
	 */
	public void sendTextContent(Media media, HttpServletRequest req, HttpServletResponse res) throws IOException {
		res.setContentType(media.getContentType());			
		PrintWriter pw = res.getWriter();
		pw.println(media.getContent());
    }

	/**
	 * <p>Returns the binary content via the response object</p>
	 *
	 * @param media the <code>Media</code> object
	 * @param request the <code>HttpServletRequest</code> object
	 * @param response the <code>HttpServletResponse</code> object
	 */
	public void sendBinaryContent(Media media, HttpServletRequest req, HttpServletResponse res) throws IOException {
		InputStream is = null;
		BufferedInputStream bis = null;								
		ServletOutputStream out = null;
		BufferedOutputStream bos = null;
			
		try {
			is = new ByteArrayInputStream(media.getBinaryContent());
			bis = new BufferedInputStream(is);								
								
			out = res.getOutputStream();
			bos = new BufferedOutputStream(out);
			res.setContentType(media.getContentType());			
			res.setHeader("Content-disposition", "inline; filename=" + media.getName());

			// The number of bytes read
			int bytesRead = 0;
							    
			// The buffer to hold the read in data
			byte[] buf = new byte[bis.available()];
							    
			// Read from source and write to the destination
			while((bytesRead = bis.read(buf, 0, buf.length)) != -1) {						    
				// Write to the destination
				bos.write(buf, 0, bytesRead);    
			}	
		} catch (Exception e) {
			log.error("Exception processing binary media content: " + e.toString(), e);
		} finally {					
			if (bos != null)
				bos.close();
			if (out != null)
				out.close();				
			if (bis != null)
				bis.close();
			if (is != null)
				is.close();	
		}
    }

	/**
	 * <p>This method obtains the media record from the database.</p>
	 *
	 * @param request the <code>HttpServletRequest</code> object
	 * @return the media object associated with this request
	 */
	private Media getMedia(HttpServletRequest req) {
		Media media = null;

		String idString = getIdFromUrl(req, servletPath);
		if (StringUtils.isNotEmpty(idString)) {
			if (idString.indexOf("/") != -1) {
				String[] parts = idString.split("/");
				if (parts.length > 1) {
					int nameIndex = parts.length - 1;
					String context = parts[0].trim();
					String location = "";
					String name = parts[nameIndex].trim();
					if (parts.length > 2) {
						String separator = "";
						for (int i=1; i<nameIndex; i++) {
							location += separator + parts[i].trim();
							separator = "/";
						}
					}
					media = mediaManager.findByContextLocationAndName(context, location, name);
				}
			}
		}

		return media;
    }

	/**
	 * @param servletPath the servletPath to set
	 */
	public void setServletPath(String servletPath) {
		this.servletPath = servletPath;
	}

	/**
	 * @param mediaManager the mediaManager to set
	 */
	public void setMediaManager(MediaManager mediaManager) {
		this.mediaManager = mediaManager;
	}
}
