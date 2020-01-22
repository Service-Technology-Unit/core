package edu.ucdavis.ucdh.stu.core.manager.impl;

import java.util.List;

import edu.ucdavis.ucdh.stu.core.beans.Media;
import edu.ucdavis.ucdh.stu.core.dao.MediaDao;
import edu.ucdavis.ucdh.stu.core.manager.MediaManager;

/**
 * <p>Concrete implementation of the Media manager interface.</p>
 */
public class MediaManagerImpl implements MediaManager {
	private MediaDao dao;

	/**
	 * <p>Returns all Medias in the database.</p>
	 * 
	 * @return all Medias in the database
	 */
	public List<Media> findAll() {
		return dao.findAll();
	}

	/**
	 * <p>Returns all Medias in the database that
	 * match the specified search criteria.</p>
	 * 
	 * @param media an example Media
	 * @return all Medias in the database that
	 * match the specified search criteria
	 */
	public List<Media> findByExample(Media media) {
		return dao.findByExample(media);
	}

	/**
	 * <p>Returns all Medias in the database that
	 * match the specified search criteria.</p>
	 * 
	 * @param propertyName the name of the specified property
	 * @param propertyValue the search value for the specified
	 * property
	 * @return all Medias in the database that
	 * match the specified search criteria
	 */
	public List<Media> findByProperty(String propertyName, Object propertyValue) {
		return dao.findByProperty(propertyName, propertyValue);
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
		return dao.findByContextLocationAndName(context, location, name);
	}

	/**
	 * <p>Returns the Media with the specified id.</p>
	 * 
	 * @param id the id of the requested media
	 * @return the Media with the specified id
	 */
	public Media findById(Integer id) {
		return dao.findById(id);
	}

	/**
	 * <p>Saves the Media passed.</p>
	 * 
	 * @param media the media to save
	 */
	public void save(Media media) {
		dao.save(media);
	}

	/**
	 * <p>Deletes the Media with the specified id.</p>
	 * 
	 * @param media the media to delete
	 */
	public void delete(Media media) {
		dao.delete(media);
	}

	/**
	 * <p>Sets the MediaDao.</p>
	 * 
	 * @param mediaDao the mediaDao to set
	 */
	public void setMediaDao(MediaDao dao) {
		this.dao = dao;
	}
}
