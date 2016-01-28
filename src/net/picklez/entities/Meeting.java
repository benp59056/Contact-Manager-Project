package net.picklez.entities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import net.picklez.utils.strings.StringUtils;

/**
 * @author Ben Pickering
 * @since 3 Jan 2016 | 15:10:31
 * Description: Object to hold meeting information 
 */

public class Meeting {

	private String topic;
	private long id;
	private Calendar date;
	private ArrayList<String> contacts;

	/**
	 * Creates new instance of a meeting with topic, id and date
	 */
	public Meeting(String s, long l, Calendar c) {
		this.topic = s;
		this.id = l;
		this.date = c;
		this.contacts = new ArrayList<String>();
	}

	/**
	 * Creates a new instance of a meeting with topic, id, date and contacts 
	 */
	public Meeting(String s, String id, String date, String contacts) {
		this.topic = s;
		
		try {
			this.id = Long.parseLong(id);
		} catch (Exception e) {
			this.id = (long)(Math.random() * Long.MAX_VALUE);
		}
		
		
		try {
			Date date1 = new SimpleDateFormat("dd/mm/yyyy").parse(date);
			Calendar c = Calendar.getInstance();
			c.setTime(date1);
			
			this.date = c;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		this.contacts = parseContactsFromString(contacts);
	}

	/**
	 * Returns the topic
	 */
	public String getTopic() {
		return this.topic;
	}

	/**
	 * Returns the id
	 */
	public long getID() {
		return this.id;
	}

	/**
	 * Returns the date
	 */
	public Calendar getDate() {
		return this.date;
	}

	/**
	 * Returns the contacts as an arraylist
	 */
	public ArrayList<String> getContacts() {
		return this.contacts;
	}

	/**
	 * Returns class in formatted way
	 */
	public String asString() {
		return StringUtils.formatString("Meeting[Topic='{0}'; ID='{1}'; Date='{2}'; Contacts='{3}']", this.topic, this.id, this.getDateAsString(), this.getContactsAsString());
	}

	/**
	 * Returns the date as a string
	 */
	public String getDateAsString() {
		return new SimpleDateFormat("dd/mm/yyyy").format(this.date.getTime());
	}
	
	/**
	 * Parses contacts from string into an arraylist
	 */
	public ArrayList<String> parseContactsFromString(String s) {
		ArrayList<String> contacts = new ArrayList<String>();
		
		s = s.replace("Contacts { ", "");
		s = s.replace("}", "");
		
		String[] split = s.split(", ");
		for (String contact : split) {
			contacts.add(contact);
		}
		
		return contacts;
	}
	
	/**
	 * Returns all the contacts as a formatted string
	 */
	public String getContactsAsString() {
		String result = "Contacts { ";

		int curIndex = 0;
		for (String c : this.contacts) {
			result += c + (curIndex == this.contacts.size() - 1 ? " " : ", ");
			curIndex ++;
		}
		
		result += "}";
		
		return result;
	}

	/**
	 * Returns the class and data in File format
	 */
	public String asFileFormat() {
		return StringUtils.formatString("{0},{1},{2},{3}",this.topic, this.id, this.getDateAsString(), this.getContactsAsString());
	}

	/**
	 * Checks if the class as a string contains data
	 * @return
	 */
	public boolean containsData(String text) {
		if (this.asFileFormat().toLowerCase().contains(text.toLowerCase())) 
			return true;
		return false;
	}
}