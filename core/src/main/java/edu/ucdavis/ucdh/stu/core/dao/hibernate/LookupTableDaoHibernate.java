package edu.ucdavis.ucdh.stu.core.dao.hibernate;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.orm.ObjectRetrievalFailureException;

import edu.ucdavis.ucdh.stu.core.beans.LookupTable;
import edu.ucdavis.ucdh.stu.core.dao.LookupTableDao;

/**
 * <p>This is the LookupTable data access object.</p>
 */
public class LookupTableDaoHibernate extends AbstractHibernateUpdateDao<LookupTable> implements LookupTableDao {

	/**
	 * <p>Constructs a new LookupTableDaoHibernate using the parameters provided.</p>
	 */
	public LookupTableDaoHibernate(SessionFactory sessionFactory) {
		super(LookupTable.class, sessionFactory);
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
		LookupTable lookupTable = new LookupTable();
		lookupTable.setContext(context);
		lookupTable.setTableName(tableName);
		List<LookupTable> lookupTableEntries = findByExample(lookupTable);
		if (lookupTableEntries != null && lookupTableEntries.size() > 0) {
			lookupTable = lookupTableEntries.get(0);
		} else {
			throw new ObjectRetrievalFailureException(LookupTable.class, context + "/" + tableName);
		}
		return lookupTable;
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
		return findByProperty("context", context);
	}
}