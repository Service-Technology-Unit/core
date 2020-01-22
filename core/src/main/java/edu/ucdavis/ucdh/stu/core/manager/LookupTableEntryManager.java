package edu.ucdavis.ucdh.stu.core.manager;

import java.util.List;

import edu.ucdavis.ucdh.stu.core.beans.LookupTableEntry;

/**
 * <p>This is the LookupTableEntry manager interface.</p>
 */
public interface LookupTableEntryManager {

	/**
	 * <p>Returns all LookupTableEntries in the database.</p>
	 * 
	 * @return all LookupTableEntries in the database
	 */
	public List<LookupTableEntry> findAll();

	/**
	 * <p>Returns all LookupTableEntries in the database that
	 * match the specified search criteria.</p>
	 * 
	 * @param lookupTableEntry an example LookupTableEntry
	 * @return all LookupTableEntries in the database that
	 * match the specified search criteria
	 */
	public List<LookupTableEntry> findByExample(LookupTableEntry lookupTableEntry);

	/**
	 * <p>Returns all LookupTableEntries in the database that
	 * match the specified search criteria.</p>
	 * 
	 * @param propertyName the name of the specified property
	 * @param propertyValue the search value for the specified
	 * property
	 * @return all LookupTableEntries in the database that
	 * match the specified search criteria
	 */
	public List<LookupTableEntry> findByProperty(String propertyName, Object propertyValue);

	/**
	 * <p>Returns all LookupTableEntries with the specified context
	 * and table name.</p>
	 * 
	 * @param context the requested context
	 * @param tableName the name of the requested look-up table
	 * @return the requested LookupTableEntry
	 */
	public List<LookupTableEntry> findByContextAndTableName(String context, String tableName);

	/**
	 * <p>Returns the LookupTableEntry with the specified context,
	 * table name, and entry id.</p>
	 * 
	 * @param context the requested context
	 * @param tableName the name of the requested look-up table
	 * @param entryId the id of the requested entry
	 * @return the requested LookupTableEntry
	 */
	public LookupTableEntry findByContextTableEntry(String context, String tableName, String entryId);

	/**
	 * <p>Returns the LookupTableEntry with the specified id.</p>
	 * 
	 * @param id the id of the requested lookupTableEntry
	 * @return the LookupTableEntry with the specified id
	 */
	public LookupTableEntry findById(int id);

	/**
	 * <p>Saves the LookupTableEntry passed.</p>
	 * 
	 * @param lookupTableEntry the lookupTableEntry to save
	 */
	public void save(LookupTableEntry lookupTableEntry);

	/**
	 * <p>Deletes the LookupTableEntry with the specified id.</p>
	 * 
	 * @param lookupTableEntry the lookupTableEntry to delete
	 */
	public void delete(LookupTableEntry lookupTableEntry);
}
