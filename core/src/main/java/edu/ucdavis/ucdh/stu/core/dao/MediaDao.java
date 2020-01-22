package edu.ucdavis.ucdh.stu.core.dao;

import java.io.Serializable;
import java.util.List;

import edu.ucdavis.ucdh.stu.core.beans.Media;

/**
 * <p>This is the Media data access object interface.</p>
 */
public interface MediaDao extends Dao {

	/**
	 * <p>Returns all Medias in the database.</p>
	 * 
	 * @return all Medias in the database
	 */
	public List<Media> findAll();

	/**
	 * <p>Returns all Medias in the database that
	 * match the specified search criteria.</p>
	 * 
	 * @param media an example Media
	 * @return all Medias in the database that
	 * match the specified search criteria
	 */
	public List<Media> findByExample(Media media);

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
	public List<Media> findByProperty(String propertyName, Object propertyValue);

	/**
	 * <p>Returns the Media with the specified context and name.</p>
	 * 
	 * @param context the context of the requested Media
	 * @param location the location of the requested Media
	 * @param name the name of the requested Media
	 * @return the Media with the specified context and name
	 */
	public Media findByContextLocationAndName(String context, String location, String name);

	/**
	 * <p>Returns the Media with the specified id.</p>
	 * 
	 * @param id the id of the requested media
	 * @return the Media with the specified id
	 */
	public Media findById(Serializable id);

	/**
	 * <p>Saves the Media passed.</p>
	 * 
	 * @param media the media to save
	 */
	public void save(Media media);

	/**
	 * <p>Deletes the Media with the specified id.</p>
	 * 
	 * @param media the media to delete
	 */
	public void delete(Media media);
}
