package edu.ucdavis.ucdh.stu.core.utils;

import java.math.BigInteger;

/**
 * <p>This transient entity defines a single batch job statistic.</p>
 */
public class BatchJobServiceStatistic {
	private String label = null;
	private String format = null;
	private BigInteger value = null;

	/**
	 * <p>Two-argument constructor (label and value) -- defaults to Integer format</p>
	 * 
	 * @param label the label for the statistic
	 * @param value the value for the statistic
	 */
	public BatchJobServiceStatistic(String label, BigInteger value) {
		this(label, BatchJobService.FORMAT_INTEGER, value);
	}

	/**
	 * <p>Three-argument constructor (label and value) -- defaults to Integer format</p>
	 * 
	 * @param label the label for the statistic
	 * @param format the format of the statistic
	 * @param value the value for the statistic
	 */
	public BatchJobServiceStatistic(String label, String format, BigInteger value) {
		super();
		this.label = label;
		this.format = format;
		this.value = value;
		if (!BatchJobService.validFormat(format)) {
			format = BatchJobService.FORMAT_INTEGER;
		}
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}
	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}
	/**
	 * @return the format
	 */
	public String getFormat() {
		return format;
	}
	/**
	 * @param format the format to set
	 */
	public void setFormat(String format) {
		this.format = format;
	}
	/**
	 * @return the value
	 */
	public BigInteger getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(BigInteger value) {
		this.value = value;
	}
}
