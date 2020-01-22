package edu.ucdavis.ucdh.stu.core.beans;


/**
 * <p>This transient entity bean defines an address list of a specific type
 * for a message to be delivered.</p>
 */
public class MessageAddress {
	private String addressType = null;
	private String[] addressValues = new String[0];

	/**
	 * <p>Constructs a new MessageAddress.</p>
	 */
	public MessageAddress() {
		super();
	}

	/**
	 * <p>Constructs a new MessageAddress using the parameters provided.</p>
	 * 
	 * @param addressType the addressType of this MessageAddress
	 */
	public MessageAddress(String addressType) {
		super();
		this.addressType = addressType;
	}

	/**
	 * @param addressValues the addressValues to set
	 */
	public void addAddressValue(String addressValue) {
		if (addressValues == null) {
			addressValues = new String[0];
		}
		String[] newValues = new String[addressValues.length + 1];
		for (int i=0; i<addressValues.length; i++) {
			newValues[i] = addressValues[i];
		}
		newValues[addressValues.length] = addressValue;
		addressValues = newValues;
	}

	/**
	 * @return the addressType
	 */
	public String getAddressType() {
		return addressType;
	}
	/**
	 * @param addressType the addressType to set
	 */
	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}
	/**
	 * @return the addressValues
	 */
	public String[] getAddressValues() {
		return addressValues;
	}
	/**
	 * @param addressValues the addressValues to set
	 */
	public void setAddressValues(String[] addressValues) {
		this.addressValues = addressValues;
	}
}