package edu.ucdavis.ucdh.stu.core.dao.hibernate;

import java.util.List;

import org.hibernate.SessionFactory;

import edu.ucdavis.ucdh.stu.core.beans.Media;
import edu.ucdavis.ucdh.stu.core.dao.MediaDao;

/**
 * <p>This is the Media data access object.</p>
 */
public class MediaDaoHibernate extends AbstractHibernateUpdateDao<Media> implements MediaDao {

	/**
	 * <p>Constructs a new MediaDaoHibernate using the parameters provided.</p>
	 */
	public MediaDaoHibernate(SessionFactory sessionFactory) {
		super(Media.class, sessionFactory);
	}

	/**
	 * <p>Returns the Media with the specified context and name.</p>
	 * 
	 * @param context the context of the requested Media
	 * @param location the location of the requested Media
	 * @param name the name of the requested Media
	 * @return the Media with the specified context and name
	 */
	public Media findByContextLocationAndName(String context, String location, String name) {
		Media media = new Media();
		media.setId(null);
		media.setContext(context);
		media.setLocation(location);
		media.setName(name);
		List<Media> mediaEntries = findByExample(media);
		if (mediaEntries != null && mediaEntries.size() > 0) {
			media = mediaEntries.get(0);
		} else {
			media = null;
		}
		return media;
	}
}