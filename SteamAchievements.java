import org.xml.sax.helpers.*;
import org.xml.sax.*;
import javax.xml.parsers.*;
import java.util.Date;
import java.util.ArrayList;
import java.util.Iterator;

public class SteamAchievements extends DefaultHandler {
	String tmpStr;
	Achievement tmpAch;
	ArrayList<Achievement> ach;
	public static void main(String[] args) {
		SteamAchievements xyz = new SteamAchievements("MoonStruckHorrors", "Osmos");
		xyz.print();
	}
	public SteamAchievements(String uName, String gName) {
		try {
			ach = new ArrayList<Achievement>();
			SAXParserFactory factory = SAXParserFactory.newInstance();
			factory.setValidating(true);
			SAXParser parser = factory.newSAXParser();
			String url = String.format("http://steamcommunity.com/id/%s/stats/%s/?xml=1", uName, gName);
			parser.parse(url, this);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	//Event handlers
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		tmpStr = "";
		if(qName.equals("achievement")) {
			boolean unlocked = attributes.getValue("closed").equals("1") ? true : false;
			tmpAch = new Achievement(unlocked);
		}
	}
	public void characters(char[] ch, int start, int length) throws SAXException {
		tmpStr = new String(ch, start, length);
	}
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if(qName.equals("achievement")) {
			ach.add(tmpAch);
		} else if(qName.equals("name")) {
			tmpAch.setName(tmpStr);
		} else if(qName.equals("description")) {
			tmpAch.setDesc(tmpStr);
		} else if(qName.equals("unlockTimestamp")) {
			tmpAch.setDateUnlocked(tmpStr);
		} else if(qName.equals("iconClosed")) {
			if(tmpAch.isUnlocked() == true) {
				tmpAch.setIcon(tmpStr);
			}
		} else if(qName.equals("iconOpen")) {
			if(tmpAch.isUnlocked() == false) {
				tmpAch.setIcon(tmpStr);
			}
		}
	}
	public void print() {
		Iterator<Achievement> i = ach.iterator();
		while(i.hasNext()) {
			Achievement a = i.next();
			System.out.println("Name: " + a.getName());
			System.out.println("Description: " + a.getDesc());
			if(a.isUnlocked() == true) {
				System.out.println("Unlocked on: " + a.getDateUnlocked().toString());
			}
		}
	}
	public ArrayList<Achievement> getAchList() {
		return ach;
	}
}

class Achievement {
	boolean unlocked;
	Date dateUnlocked;
	String name, desc, icon;
	public Achievement(boolean unlocked) {
		this.unlocked = unlocked;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public void setDateUnlocked(String dateUnlocked) {
		this.dateUnlocked = new Date(Long.parseLong(dateUnlocked) * 1000); //Constructing Date from "the epoch"
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getName() {
		return name;
	}
	public String getDesc() {
		return desc;
	}
	public Date getDateUnlocked() {
		return dateUnlocked;
	}
	public boolean isUnlocked() {
		return unlocked;
	}
	public String getIcon() {
		return icon;
	}
}