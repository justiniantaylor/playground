package com.playground.payroll.util.date;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Helper methods for date and date times.
 * 
 * @author Justin Taylor
 * @version %I%, %G%
 * @since 1.0
 */
public class DateTimeUtils {
	
	public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd MMMM uuuu");
	
	/**
	 * This will format/combine two dates (a range) using the supplied formatter and separator.
	 * 
	 * @param startDate	the start date of the range
	 * @param endDate	the end date of the range
	 * @param formatter	the formatter to use for the dates
	 * @param separator	the separator to use to combine the date range
	 * @return 			the formatted and combined data range
	 */
	public static String formateDateRange(LocalDate startDate, LocalDate endDate , DateTimeFormatter formatter, String separator) {
		return startDate.format(formatter) + " " + separator + " " + endDate.format(formatter);
	}
}
