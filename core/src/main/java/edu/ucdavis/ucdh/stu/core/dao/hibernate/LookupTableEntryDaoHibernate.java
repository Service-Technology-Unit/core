package edu.ucdavis.ucdh.stu.core.dao.hibernate;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.orm.ObjectRetrievalFailureException;

import edu.ucdavis.ucdh.stu.core.beans.LookupTableEntry;
import edu.ucdavis.ucdh.stu.core.dao.LookupTableEntryDao;

/**
 * <p>This is the LookupTableEntry data access object.</p>
 */
public class LookupTableEntryDaoHibernate extends AbstractHibernateUpdateDao<LookupTableEntry> implements LookupTableEntryDao {

	/**
	 * <p>Constructs a new LookupTableEntryDaoHibernate using the parameters provided.</p>
	 */
	public LookupTableEntryDaoHibernate(SessionFactory sessionFactory) {
		super(LookupTableEntry.class, sessionFactory);
	}

	/**
	 * <p>Returns all LookupTableEntries with the specified context
	 * and table name.</p>
	 * 
	 * @param context the requested context
	 * @param tableName the name of the requested look-up table
	 * @return the requested LookupTableEntry
	 */
	public List<LookupTableEntry> findByContextAndTableName(String context, String tableName) {
		LookupTableEntry lookupTableEntry = new LookupTableEntry();
		lookupTableEntry.setContext(context);
		lookupTableEntry.setTableName(tableName);
		return findByExample(lookupTableEntry);
	}

	/**
	 * <p>Returns the LookupTableEntry with the specified context,
	 * table name, and entry id.</p>
	 * 
	 * @param context the requested context
	 * @param tableName the name of the requested look-up table
	 * @param entryId the id of the requested entry
	 * @return the requested LookupTableEntry
	 */
	public LookupTableEntry findByContextTableEntry(String context, String tableName, String entryId) {
		LookupTableEntry lookupTableEntry = new LookupTableEntry();
		lookupTableEntry.setContext(context);
		lookupTableEntry.setTableName(tableName);
		lookupTableEntry.setEntryId(entryId);
		List<LookupTableEntry> lookupTableEntries = findByExample(lookupTableEntry);
		if (lookupTableEntries != null && lookupTableEntries.size() > 0) {
			lookupTableEntry = lookupTableEntries.get(0);
		} else {
			throw new ObjectRetrievalFailureException(LookupTableEntry.class, context + "/" + tableName + "/" + entryId);
		}
		return lookupTableEntry;
	}
}
