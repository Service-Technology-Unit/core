package edu.ucdavis.ucdh.stu.core.dao;

import java.io.Serializable;
import java.util.List;

import edu.ucdavis.ucdh.stu.core.beans.LookupTable;

/**
 * <p>This is the LookupTable data access object interface.</p>
 */
public interface LookupTableDao extends Dao {

	/**
	 * <p>Returns all LookupTables in the database.</p>
	 * 
	 * @return all LookupTables in the database
	 */
	public List<LookupTable> findAll();

	/**
	 * <p>Returns all LookupTables in the database that
	 * match the specified search criteria.</p>
	 * 
	 * @param lookupTable an example LookupTable
	 * @return all LookupTables in the database that
	 * match the specified search criteria
	 */
	public List<LookupTable> findByExample(LookupTable lookupTable);

	/**
	 * <p>Returns all LookupTables in the database that
	 * match the specified search criteria.</p>
	 * 
	 * @param propertyName the name of the specified property
	 * @param propertyValue the search value for the specified
	 * property
	 * @return all LookupTables in the database that
	 * match the specified search criteria
	 */
	public List<LookupTable> findByProperty(String propertyName, Object propertyValue);

	/**
	 * <p>Returns all LookupTables in the database for the specified
	 * context.</p>
	 * 
	 * @param context the requested context
	 * @return all LookupTables in the database for the specified
	 * context
	 */
	public List<LookupTable> findByContext(String context);

	/**
	 * <p>Returns the LookupTable with the specified context and
	 * table name.</p>
	 * 
	 * @param context the context of the requested LookupTable
	 * @param tableName the name of the requested LookupTable
	 * @return the LookupTable with the specified context and
	 * table name
	 */
	public LookupTable findByContextAndTableName(String context, String tableName);

	/**
	 * <p>Returns the LookupTable with the specified id.</p>
	 * 
	 * @param id the id of the requested lookupTable
	 * @return the LookupTable with the specified id
	 */
	public LookupTable findById(Serializable id);

	/**
	 * <p>Saves the LookupTable passed.</p>
	 * 
	 * @param lookupTable the lookupTable to save
	 */
	public void save(LookupTable LookupTable);

	/**
	 * <p>Deletes the LookupTable with the specified id.</p>
	 * 
	 * @param lookupTable the lookupTable to delete
	 */
	public void delete(LookupTable LookupTable);
}
