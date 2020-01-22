package edu.ucdavis.ucdh.stu.core.manager.impl;

import java.util.List;

import edu.ucdavis.ucdh.stu.core.beans.LookupTable;
import edu.ucdavis.ucdh.stu.core.dao.LookupTableDao;
import edu.ucdavis.ucdh.stu.core.manager.LookupTableManager;

/**
 * <p>Concrete implementation of the LookupTable manager interface.</p>
 */
public class LookupTableManagerImpl implements LookupTableManager {
	private LookupTableDao dao;

	/**
	 * <p>Returns all LookupTables in the database.</p>
	 * 
	 * @return all LookupTables in the database
	 */
	public List<LookupTable> findAll() {
		return dao.findAll();
	}

	/**
	 * <p>Returns all LookupTables in the database that
	 * match the specified search criteria.</p>
	 * 
	 * @param lookupTable an example LookupTable
	 * @return all LookupTables in the database that
	 * match the specified search criteria
	 */
	public List<LookupTable> findByExample(LookupTable lookupTable) {
		return dao.findByExample(lookupTable);
	}

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
	public List<LookupTable> findByProperty(String propertyName, Object propertyValue) {
		return dao.findByProperty(propertyName, propertyValue);
	}

	/**
	 * <p>Returns all LookupTables in the database for the specified
	 * context.</p>
	 * 
	 * @param context the requested context
	 * @return all LookupTables in the database for the specified
	 * context
	 */
	public List<LookupTable> findByContext(String context) {
		return dao.findByContext(context);
	}

	/**
	 * <p>Returns the LookupTable with the specified context and
	 * table name.</p>
	 * 
	 * @param context the context of the requested LookupTable
	 * @param tableName the name of the requested LookupTable
	 * @return the LookupTable with the specified context and
	 * table name
	 */
	public LookupTable findByContextAndTableName(String context, String tableName) {
		return dao.findByContextAndTableName(context, tableName);
	}

	/**
	 * <p>Returns the LookupTable with the specified id.</p>
	 * 
	 * @param id the id of the requested lookupTable
	 * @return the LookupTable with the specified id
	 */
	public LookupTable findById(String id) {
		return dao.findById(id);
	}

	/**
	 * <p>Saves the LookupTable passed.</p>
	 * 
	 * @param lookupTable the lookupTable to save
	 */
	public void save(LookupTable lookupTable) {
		dao.save(lookupTable);
	}

	/**
	 * <p>Deletes the LookupTable with the specified id.</p>
	 * 
	 * @param lookupTable the lookupTable to delete
	 */
	public void delete(LookupTable lookupTable) {
		dao.delete(lookupTable);
	}

	/**
	 * <p>Sets the LookupTableDao.</p>
	 * 
	 * @param lookupTableDao the lookupTableDao to set
	 */
	public void setLookupTableDao(LookupTableDao dao) {
		this.dao = dao;
	}
}
