package edu.ucdavis.ucdh.stu.core.utils;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>A collection of convenience methods for use in Velocity templates.</p>
 */
public class CurrentDate {
	private Map<String,String> dayNameValues = new HashMap<String,String>();
	private Map<String,String> shortDayNameValues = new HashMap<String,String>();
	private Map<String,String> monthNameValues = new HashMap<String,String>();
	private Map<String,String> shortMonthNameValues = new HashMap<String,String>();
	private Date date = null;
	private Calendar calendar = null;
	private String time = null;
	private String dateShort = null;
	private String dateMedium = null;
	private String dateLong = null;
	private String timeShort = null;
	private String timeMedium = null;
	private String timeLong = null;
	private String dateTimeShort = null;
	private String dateTimeMedium = null;
	private String dateTimeLong = null;
	private String amPm = null;
	private String dayOfMonth = null;
	private String dayOfWeek = null;
	private String dayOfWeekInMonth = null;
	private String dayOfYear = null;
	private String era = null;
	private String hour = null;
	private String hourOfDay = null;
	private String millisecond = null;
	private String minute = null;
	private String month = null;
	private String second = null;
	private String weekOfMonth = null;
	private String weekOfYear = null;
	private String year = null;
	private String dayName = null;
	private String dayNameShort = null;
	private String monthName = null;
	private String monthNameShort = null;

	/**
	 * <p>Default constructor</p>
	 */
	public CurrentDate() {
		date = new Date();
		calendar = new GregorianCalendar();
		calendar.setTime(date);
		//set up day names
		dayNameValues.put("1", "Sunday");
		dayNameValues.put("2", "Monday");
		dayNameValues.put("3", "Tuesday");
		dayNameValues.put("4", "Wednesday");
		dayNameValues.put("5", "Thursday");
		dayNameValues.put("6", "Friday");
		dayNameValues.put("7", "Saturday");
		//set up short day names
		shortDayNameValues.put("1", "Sun");
		shortDayNameValues.put("2", "Mon");
		shortDayNameValues.put("3", "Tue");
		shortDayNameValues.put("4", "Wed");
		shortDayNameValues.put("5", "Thu");
		shortDayNameValues.put("6", "Fri");
		shortDayNameValues.put("7", "Sat");
		//set up month names
		monthNameValues.put("1", "January");
		monthNameValues.put("2", "February");
		monthNameValues.put("3", "March");
		monthNameValues.put("4", "April");
		monthNameValues.put("5", "May");
		monthNameValues.put("6", "June");
		monthNameValues.put("7", "July");
		monthNameValues.put("8", "August");
		monthNameValues.put("9", "September");
		monthNameValues.put("10", "October");
		monthNameValues.put("11", "November");
		monthNameValues.put("12", "December");
		//set up short month names
		shortMonthNameValues.put("1", "Jan");
		shortMonthNameValues.put("2", "Feb");
		shortMonthNameValues.put("3", "Mar");
		shortMonthNameValues.put("4", "Apr");
		shortMonthNameValues.put("5", "May");
		shortMonthNameValues.put("6", "Jun");
		shortMonthNameValues.put("7", "Jul");
		shortMonthNameValues.put("8", "Aug");
		shortMonthNameValues.put("9", "Sep");
		shortMonthNameValues.put("10", "Oct");
		shortMonthNameValues.put("11", "Nov");
		shortMonthNameValues.put("12", "Dec");
	}

	/**
	 * <p>Returns the date in milliseconds.</p>
	 * 
	 * @return the date in milliseconds
	 */
	public String getTime() {
		if (time == null) {
			time = date.getTime() + "";
		}
		return time;
	}

	/**
	 * <p>Returns the date using the short format.</p>
	 * 
	 * @return the date using the short format
	 */
	public String getDateShort() {
		if (dateShort == null) {
			dateShort = DateFormat.getDateInstance(DateFormat.SHORT).format(date);
		}
		return dateShort;
	}

	/**
	 * <p>Returns the date using the medium format.</p>
	 * 
	 * @return the date using the medium format
	 */
	public String getDateMedium() {
		if (dateMedium == null) {
			dateMedium = DateFormat.getDateInstance(DateFormat.MEDIUM).format(date);
		}
		return dateMedium;
	}

	/**
	 * <p>Returns the date using the long format.</p>
	 * 
	 * @return the date using the long format
	 */
	public String getDateLong() {
		if (dateLong == null) {
			dateLong = DateFormat.getDateInstance(DateFormat.LONG).format(date);
		}
		return dateLong;
	}

	/**
	 * <p>Returns the time using the short format.</p>
	 * 
	 * @return the time using the short format
	 */
	public String getTimeShort() {
		if (timeShort == null) {
			timeShort = DateFormat.getTimeInstance(DateFormat.SHORT).format(date);
		}
		return timeShort;
	}

	/**
	 * <p>Returns the time using the medium format.</p>
	 * 
	 * @return the time using the medium format
	 */
	public String getTimeMedium() {
		if (timeMedium == null) {
			timeMedium = DateFormat.getTimeInstance(DateFormat.MEDIUM).format(date);
		}
		return timeMedium;
	}

	/**
	 * <p>Returns the time using the long format.</p>
	 * 
	 * @return the time using the long format
	 */
	public String getTimeLong() {
		if (timeLong == null) {
			timeLong = DateFormat.getTimeInstance(DateFormat.LONG).format(date);
		}
		return timeLong;
	}

	/**
	 * <p>Returns the date/time using the short format.</p>
	 * 
	 * @return the date/time using the short format
	 */
	public String getDateTimeShort() {
		if (dateTimeShort == null) {
			dateTimeShort = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(date);
		}
		return dateTimeShort;
	}

	/**
	 * <p>Returns the date/time using the medium format.</p>
	 * 
	 * @return the date/time using the medium format
	 */
	public String getDateTimeMedium() {
		if (dateTimeMedium == null) {
			dateTimeMedium = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM).format(date);
		}
		return dateTimeMedium;
	}

	/**
	 * <p>Returns the date/time using the long format.</p>
	 * 
	 * @return the date/time using the long format
	 */
	public String getDateTimeLong() {
		if (dateTimeLong == null) {
			dateTimeLong = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG).format(date);
		}
		return dateTimeLong;
	}

	/**
	 * <p>Returns the amPm.</p>
	 * 
	 * @return the amPm
	 */
	public String getAmPm() {
		if (amPm == null) {
			amPm = calendar.get(Calendar.AM_PM) + "";
		}
		return amPm;
	}

	/**
	 * <p>Returns the dayOfMonth.</p>
	 * 
	 * @return the dayOfMonth
	 */
	public String getDayOfMonth() {
		if (dayOfMonth == null) {
			dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH) + "";
		}
		return dayOfMonth;
	}

	/**
	 * <p>Returns the dayOfWeek.</p>
	 * 
	 * @return the dayOfWeek
	 */
	public String getDayOfWeek() {
		if (dayOfWeek == null) {
			dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) + "";
		}
		return dayOfWeek;
	}

	/**
	 * <p>Returns the dayOfWeekInMonth.</p>
	 * 
	 * @return the dayOfWeekInMonth
	 */
	public String getDayOfWeekInMonth() {
		if (dayOfWeekInMonth == null) {
			dayOfWeekInMonth = calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH) + "";
		}
		return dayOfWeekInMonth;
	}

	/**
	 * <p>Returns the dayOfYear.</p>
	 * 
	 * @return the dayOfYear
	 */
	public String getDayOfYear() {
		if (dayOfYear == null) {
			dayOfYear = calendar.get(Calendar.DAY_OF_YEAR) + "";
		}
		return dayOfYear;
	}

	/**
	 * <p>Returns the era.</p>
	 * 
	 * @return the era
	 */
	public String getEra() {
		if (era == null) {
			era = calendar.get(Calendar.ERA) + "";
		}
		return era;
	}

	/**
	 * <p>Returns the hour.</p>
	 * 
	 * @return the hour
	 */
	public String getHour() {
		if (hour == null) {
			hour = calendar.get(Calendar.HOUR) + "";
		}
		return hour;
	}

	/**
	 * <p>Returns the hourOfDay.</p>
	 * 
	 * @return the hourOfDay
	 */
	public String getHourOfDay() {
		if (hourOfDay == null) {
			hourOfDay = calendar.get(Calendar.HOUR_OF_DAY) + "";
		}
		return hourOfDay;
	}

	/**
	 * <p>Returns the millisecond.</p>
	 * 
	 * @return the millisecond
	 */
	public String getMillisecond() {
		if (millisecond == null) {
			millisecond = calendar.get(Calendar.MILLISECOND) + "";
		}
		return millisecond;
	}

	/**
	 * <p>Returns the minute.</p>
	 * 
	 * @return the minute
	 */
	public String getMinute() {
		if (minute == null) {
			minute = calendar.get(Calendar.MINUTE) + "";
		}
		return minute;
	}

	/**
	 * <p>Returns the month.</p>
	 * 
	 * @return the month
	 */
	public String getMonth() {
		if (month == null) {
			month = calendar.get(Calendar.MONTH) + "";
		}
		return month;
	}

	/**
	 * <p>Returns the second.</p>
	 * 
	 * @return the second
	 */
	public String getSecond() {
		if (second == null) {
			second = calendar.get(Calendar.SECOND) + "";
		}
		return second;
	}

	/**
	 * <p>Returns the weekOfMonth.</p>
	 * 
	 * @return the weekOfMonth
	 */
	public String getWeekOfMonth() {
		if (weekOfMonth == null) {
			weekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH) + "";
		}
		return weekOfMonth;
	}

	/**
	 * <p>Returns the weekOfYear.</p>
	 * 
	 * @return the weekOfYear
	 */
	public String getWeekOfYear() {
		if (weekOfYear == null) {
			weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR) + "";
		}
		return weekOfYear;
	}

	/**
	 * <p>Returns the year.</p>
	 * 
	 * @return the year
	 */
	public String getYear() {
		if (year == null) {
			year = calendar.get(Calendar.YEAR) + "";
		}
		return year;
	}

	/**
	 * <p>Returns the dayName.</p>
	 * 
	 * @return the dayName
	 */
	public String getDayName() {
		if (dayName == null) {
			dayName = dayNameValues.get(getDayOfWeek());
		}
		return dayName;
	}

	/**
	 * <p>Returns the dayNameShort.</p>
	 * 
	 * @return the dayNameShort
	 */
	public String getDayNameShort() {
		if (dayNameShort == null) {
			dayNameShort = shortDayNameValues.get(getDayOfWeek());
		}
		return dayNameShort;
	}

	/**
	 * <p>Returns the monthName.</p>
	 * 
	 * @return the monthName
	 */
	public String getMonthName() {
		if (monthName == null) {
			monthName = monthNameValues.get(getMonth());
		}
		return monthName;
	}

	/**
	 * <p>Returns the monthNameShort.</p>
	 * 
	 * @return the monthNameShort
	 */
	public String getMonthNameShort() {
		if (monthNameShort == null) {
			monthNameShort = shortMonthNameValues.get(getMonth());
		}
		return monthNameShort;
	}
}
