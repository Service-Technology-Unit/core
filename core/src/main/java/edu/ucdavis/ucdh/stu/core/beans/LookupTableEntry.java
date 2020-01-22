package edu.ucdavis.ucdh.stu.core.beans;

/**
 * <p>This entity bean defines a single look-up table entry.</p>
 */
public class LookupTableEntry extends PersistentBeanBase {
	private static final long serialVersionUID = 1;
	private int id = -1;
	private String context = null;
	private String tableName = null;
	private String entryId = null;
	private String description = null;
	private String[] property = new String[20];

	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getEntryId() {
		return entryId;
	}
	public void setEntryId(String entryId) {
		this.entryId = entryId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProperty(int i) {
		return property[i];
	}
	public void setProperty(int i, String property) {
		this.property[i] = property;
	}
	public String getProperty00() {
		return property[0];
	}
	public void setProperty00(String property00) {
		this.property[0] = property00;
	}
	public String getProperty01() {
		return property[1];
	}
	public void setProperty01(String property01) {
		this.property[1] = property01;
	}
	public String getProperty02() {
		return property[2];
	}
	public void setProperty02(String property02) {
		this.property[2] = property02;
	}
	public String getProperty03() {
		return property[3];
	}
	public void setProperty03(String property03) {
		this.property[3] = property03;
	}
	public String getProperty04() {
		return property[4];
	}
	public void setProperty04(String property04) {
		this.property[4] = property04;
	}
	public String getProperty05() {
		return property[5];
	}
	public void setProperty05(String property05) {
		this.property[5] = property05;
	}
	public String getProperty06() {
		return property[6];
	}
	public void setProperty06(String property06) {
		this.property[6] = property06;
	}
	public String getProperty07() {
		return property[7];
	}
	public void setProperty07(String property07) {
		this.property[7] = property07;
	}
	public String getProperty08() {
		return property[8];
	}
	public void setProperty08(String property08) {
		this.property[8] = property08;
	}
	public String getProperty09() {
		return property[9];
	}
	public void setProperty09(String property09) {
		this.property[9] = property09;
	}
	public String getProperty10() {
		return property[10];
	}
	public void setProperty10(String property10) {
		this.property[10] = property10;
	}
	public String getProperty11() {
		return property[11];
	}
	public void setProperty11(String property11) {
		this.property[11] = property11;
	}
	public String getProperty12() {
		return property[12];
	}
	public void setProperty12(String property12) {
		this.property[12] = property12;
	}
	public String getProperty13() {
		return property[13];
	}
	public void setProperty13(String property13) {
		this.property[13] = property13;
	}
	public String getProperty14() {
		return property[14];
	}
	public void setProperty14(String property14) {
		this.property[14] = property14;
	}
	public String getProperty15() {
		return property[15];
	}
	public void setProperty15(String property15) {
		this.property[15] = property15;
	}
	public String getProperty16() {
		return property[16];
	}
	public void setProperty16(String property16) {
		this.property[16] = property16;
	}
	public String getProperty17() {
		return property[17];
	}
	public void setProperty17(String property17) {
		this.property[17] = property17;
	}
	public String getProperty18() {
		return property[18];
	}
	public void setProperty18(String property18) {
		this.property[18] = property18;
	}
	public String getProperty19() {
		return property[19];
	}
	public void setProperty19(String property19) {
		this.property[19] = property19;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
}
