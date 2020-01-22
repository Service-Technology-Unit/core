package edu.ucdavis.ucdh.stu.core.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;

import edu.ucdavis.ucdh.stu.core.beans.LookupTable;
import edu.ucdavis.ucdh.stu.core.beans.LookupTableEntry;
import edu.ucdavis.ucdh.stu.core.beans.LookupTableProperty;
import edu.ucdavis.ucdh.stu.core.manager.LookupTableEntryManager;
import edu.ucdavis.ucdh.stu.core.manager.LookupTableManager;

/**
 * <p>This is the LookupTable service.</p>
 */
public class LookupTableService {
	private String context = null;
	private LookupTableManager lookupTableManager = null;
	private LookupTableEntryManager lookupTableEntryManager = null;

	/**
	 * <p>Returns a list of lookup tables in this context.</p>
	 * 
	 * @return a list of lookup tables in this context
	 */
	public List<LookupTable> getTables() {
		return lookupTableManager.findByContext(context);
	}

	/**
	 * <p>Returns a list of lookup table names in this context.</p>
	 * 
	 * @return a list of lookup table names in this context
	 */
	public List<String> getTableNames() {
		List<String> tableNames = new ArrayList<String>();

		List<LookupTable> tableInfo = lookupTableManager.findByContext(context);
		if (tableInfo != null) {
			Iterator<LookupTable> i = tableInfo.iterator();
			while (i.hasNext()) {
				tableNames.add(i.next().getTableName());
			}
		}

		return tableNames;
	}

	/**
	 * <p>Returns the definition of the requested lookup table.</p>
	 * 
	 * @param tableName the name of the requested table
	 * @return the definition of the requested lookup table
	 */
	public LookupTable getTableDefinition(String tableName) {
		return lookupTableManager.findByContextAndTableName(context, tableName);
	}

	/**
	 * <p>Returns the contents of the requested lookup table.</p>
	 * 
	 * @param tableName the name of the requested table
	 * @return the contents of the requested lookup table
	 */
	public Map<String,Map<String,Object>> getTable(String tableName) {
		Map<String,Map<String,Object>> table = null;

		LookupTable lookupTable = lookupTableManager.findByContextAndTableName(context, tableName);
		if (lookupTable != null) {
			table = new TreeMap<String,Map<String,Object>>();
			List<LookupTableEntry> lookupTableEntryList = lookupTableEntryManager.findByContextAndTableName(context, tableName);
			if (lookupTableEntryList != null && lookupTableEntryList.size() > 0) {
				Iterator<LookupTableEntry> i = lookupTableEntryList.iterator();
				while (i.hasNext()) {
					LookupTableEntry lookupTableEntry = i.next();
					table.put(lookupTableEntry.getEntryId(), buildTableEntry(lookupTable, lookupTableEntry));
				}
			}
		}

		return table;
	}

	/**
	 * <p>Returns the requested table entry.</p>
	 * 
	 * @param tableName the name of the requested table
	 * @param entryId the id of the requested entry
	 * @return the requested table entry
	 */
	public Map<String,Object> getTableEntry(String tableName, String entryId) {
		Map<String,Object> entry = null;

		LookupTable lookupTable = lookupTableManager.findByContextAndTableName(context, tableName);
		if (lookupTable != null) {
			LookupTableEntry lookupTableEntry = lookupTableEntryManager.findByContextTableEntry(context, tableName, entryId);
			if (lookupTableEntry != null) {
				entry = buildTableEntry(lookupTable, lookupTableEntry);
			}
		}

		return entry;
	}

	/**
	 * <p>Returns the requested table entry.</p>
	 * 
	 * @param tableName the name of the requested table
	 * @param entryId the id of the requested entry
	 * @return the requested table entry
	 */
	private Map<String,Object> buildTableEntry(LookupTable lookupTable, LookupTableEntry lookupTableEntry) {
		Map<String,Object> entry = new TreeMap<String,Object>();

		entry.put("id", lookupTableEntry.getEntryId());
		entry.put("description", lookupTableEntry.getDescription());
		if (lookupTable.getProperties() != null && lookupTable.getProperties().size() > 0) {
			Iterator<LookupTableProperty> i = lookupTable.getProperties().iterator();
			while (i.hasNext()) {
				LookupTableProperty lookupTableProperty = i.next();
				int index = lookupTableProperty.getSequence();
				String name = lookupTableProperty.getName();
				if (StringUtils.isEmpty(lookupTableEntry.getProperty(index))) {
					entry.put(name, "");
				} else {
					entry.put(name, lookupTableEntry.getProperty(index));
				}
			}
		}

		return entry;
	}

	/**
	 * @param context the context to set
	 */
	public void setContext(String context) {
		this.context = context;
	}
	/**
	 * @param lookupTableManager the lookupTableManager to set
	 */
	public void setLookupTableManager(LookupTableManager lookupTableManager) {
		this.lookupTableManager = lookupTableManager;
	}
	/**
	 * @param lookupTableEntryManager the lookupTableEntryManager to set
	 */
	public void setLookupTableEntryManager(LookupTableEntryManager lookupTableEntryManager) {
		this.lookupTableEntryManager = lookupTableEntryManager;
	}
}
