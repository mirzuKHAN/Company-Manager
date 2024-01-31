import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Day implements Cloneable, Comparable<Day> {

	SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
	private int year;
	private int month;
	private int day;
	public static final String MonthNames = "JanFebMarAprMayJunJulAugSepOctNovDec";

	// Constructor
	public Day(int y, int m, int d) {
		this.year = y;
		this.month = m;
		this.day = d;
	}

	public int getNum() {
		return this.year * 10000 + this.month * 100 + this.day;
	}

	public void set(String sDay) // Set year,month,day based on a string like 01-Jan-2022
	{
		String[] sDayParts = sDay.split("-");
		this.day = Integer.parseInt(sDayParts[0]); // Apply Integer.parseInt for sDayParts[0];
		this.year = Integer.parseInt(sDayParts[2]);
		this.month = MonthNames.indexOf(sDayParts[1]) / 3 + 1;
	}

	public Day add(int dur) {
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(sdf.parse(this.toString()));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		cal.add(Calendar.DAY_OF_MONTH, dur - 1);
		String new_Date = sdf.format(cal.getTime());

		return new Day(new_Date);
	}

	public int substract(Day start) {

		Date firstDate, secondDate;
		long diff = -1;
		try {
			firstDate = sdf.parse(this.toString());
			secondDate = sdf.parse(start.toString());
			long diffInMillies = Math.abs(firstDate.getTime() - secondDate.getTime());
			diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return (int) diff;
	}

	public Day(String sDay) {
		set(sDay);
	} // Constructor, simply call set(sDay)

	@Override
	public String toString() {
		return day + "-" + MonthNames.substring((month - 1) * 3, (month) * 3) + "-" + year; // (month-1)*3,(month)*3
	}

	@Override
	public Day clone() {
		Day copy = null;
		try {
			copy = (Day) super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return copy;
	}

	// check if a given year is a leap year
	static public boolean isLeapYear(int y) {
		if (y % 400 == 0)
			return true;
		else if (y % 100 == 0)
			return false;
		else if (y % 4 == 0)
			return true;
		else
			return false;
	}

	// check if y,m,d valid
	static public boolean valid(int y, int m, int d) {
		if (m < 1 || m > 12 || d < 1)
			return false;
		switch (m) {
			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
			case 12:
				return d <= 31;
			case 4:
			case 6:
			case 9:
			case 11:
				return d <= 30;
			case 2:
				if (isLeapYear(y))
					return d <= 29;
				else
					return d <= 28;
		}
		return false;
	}

	@Override
	public int compareTo(Day another) {
		if (this.getNum() > another.getNum()) {
			return 1;
		} else if (this.getNum() < another.getNum()) {
			return -1;
		} else {
			return 0;
		}
	}
	// d MMM yyyy

	// Return a string for the day like dd MMM yyyy
	// public String toString() {

	// final String[] MonthNames = {
	// "Jan", "Feb", "Mar", "Apr",
	// "May", "Jun", "Jul", "Aug",
	// "Sep", "Oct", "Nov", "Dec"};

	// return day+" "+ MonthNames[month-1] + " "+ year;
	// }
}