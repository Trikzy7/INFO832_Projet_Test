package discreteBehaviorSimulator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * @author Flavien Vernier
 *
 */
public class LogFormatter  extends Formatter {
	/**
	 * Formats the given LogRecord into a string.
	 * The resulting string consists of the timestamp, the log level, and the log message, each on a new line.
	 *
	 * @param rec The LogRecord to be formatted.
	 * @return The formatted string.
	 */
	public String format(LogRecord rec) {
		StringBuffer buf = new StringBuffer();
		
		buf.append(calcDate(rec.getMillis()));
		buf.append(": ");
		buf.append(rec.getLevel());
		buf.append(System.getProperty("line.separator"));
		buf.append(formatMessage(rec));
		buf.append(System.getProperty("line.separator"));
		
		return buf.toString();
	}
	/**
	 * Converts the given milliseconds into a formatted date string.
	 * The date is formatted as "yyyy.MM.dd HH:mm:ss.SS".
	 *
	 * @param millisecs The milliseconds to be converted.
	 * @return The formatted date string.
	 */
	private String calcDate(long millisecs) {
	    SimpleDateFormat date_format = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SS");
	    Date resultdate = new Date(millisecs);
	    return date_format.format(resultdate);
	  }

	  // this method is called just after the handler using this
	  // formatter is created
	  public String getHead(Handler h) {
		  return "";
	  }
	  
	// this method is called just after the handler using this
	  // formatter is closed
	  public String getTail(Handler h) {
	    return "";
	  }

}
