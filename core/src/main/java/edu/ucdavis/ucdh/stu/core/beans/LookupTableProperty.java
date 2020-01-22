package edu.ucdavis.ucdh.stu.core.beans;

/**
 * <p>This entity bean defines a single look-up table property.</p>
 */
public class LookupTableProperty extends PersistentBeanBase {
	private static final long serialVersionUID = 1;
	private int id = -1;
	private int tableId = 0;
	private int sequence = 0;
	private boolean inputRequired = false;
	private boolean displayOnList = false;
	private String name = null;
	private String type = null;
	private String size = null;
	private String label = null;
	private String colHeading = null;
	private String source = null;
	private String notes = null;

	public String getColHeading() {
		return colHeading;
	}
	public void setColHeading(String colHeading) {
		this.colHeading = colHeading;
	}
	public boolean isDisplayOnList() {
		return displayOnList;
	}
	public void setDisplayOnList(boolean displayOnList) {
		this.displayOnList = displayOnList;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean isInputRequired() {
		return inputRequired;
	}
	public void setInputRequired(boolean inputRequired) {
		this.inputRequired = inputRequired;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public int getSequence() {
		return sequence;
	}
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public int getTableId() {
		return tableId;
	}
	public void setTableId(int tableId) {
		this.tableId = tableId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
