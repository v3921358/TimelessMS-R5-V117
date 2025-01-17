/*
This file is part of the OdinMS Maple Story Server.
Copyright (C) 2008 ~ 2012 OdinMS

Copyright (C) 2011 ~ 2012 TimelessMS

Patrick Huy <patrick.huy@frz.cc> 
Matthias Butz <matze@odinms.de>
Jan Christian Meyer <vimes@odinms.de>

Burblish <burblish@live.com> (DO NOT RELEASE SOMEWHERE ELSE)

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License version 3
as published by the Free Software Foundation. You may not use, modify
or distribute this program under any other version of the
GNU Affero General Public License.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package tools.dropfetcher;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Scanner;


/**
 * @author Simon
 * @author Sonic
 * @author Burblish
 */
public class DropFetcher {
    
    /**
     * @param args
     *        The command line arguments.
     */
    private static ArrayList<DropEntry> drop_entries = new ArrayList<DropEntry>();
    private static final String BASE_URL = "http://maplearchive.com/";
    private static final String MONSTER_PAGE = "mob-wp.php";
    private static int NumberOfPages = 157; // As for v120, but it's probably going to change so idk
    private static int CurrentPage = 1; // Hurr durr
    public static final int VERSION = 120;
    private static int MonstersDone = 0; // Total monsters done
    private static int MonstersWithDrops = 0; // Monsters that have drop
    private static int MonstersWithoutDrops = 0; // Monsters that have no drops
    private static int Errors = 0; // How many times I failed with this script
    
    /**
     * Crawls the mob data page with the given URL, fetching the drop data.
     * 
     * @param url
     *        The URL of the page to crawl.
     */
    private static void crawlPage(final String url) { // Recursive method bitches
	try {
	    URL page = new URL(url);
	    InputStream is = page.openStream();
	    Scanner s = new Scanner(is);
	    String temp_data = "";
	    while (s.hasNext()) {
		temp_data += s.nextLine() + "\n";
	    }
	    s.close();
	    is.close();
	    while (temp_data.contains("class=\"mobImage\"")) {
		try {
		    String monster_section;
		    if (!temp_data.contains("<div class=\"entityBox\">")) {
			monster_section = getStringBetween(temp_data, "class=\"mobImage\"", "<div class=\"pagination\">"); // Who cares, it works
		    } else {
			monster_section = getStringBetween(temp_data, "class=\"mobImage\"", "<div class=\"entityBox\">");
		    }
		    parseMonsterSection(monster_section);
		    temp_data = trimUntil(temp_data, "<div class=\"entityBox\">");
		    if (temp_data == null) {
			break;
		    }
		} catch (StringIndexOutOfBoundsException ex) {
		    System.out.println("Whoops! Something went wrong. Skipping this one...");
		    Errors++;
		    break;
		}
	    }
	    System.out.println("Finished crawling page " + CurrentPage + ".");
	    if (CurrentPage % 10 == 0) {
		System.out.println();
		System.out.println("Status so far:");
		System.out.println("Monsters: " + MonstersDone + " || Monsters with drops: " + MonstersWithDrops + " || Monsters without drops: " + MonstersWithoutDrops + " || Items: " + drop_entries.size() + " || Errors: " + Errors);
		System.out.println();
	    }
	} catch (MalformedURLException mue) {
	    System.out.println("Error parsing URL: " + url);
	    Errors++;
	    return;
	} catch (IOException ioe) {
	    System.out.println("Error reading from URL: " + ioe.getLocalizedMessage());
	    Errors++;
	    return;
	}
    }
    
    /**
     * Builds an SQL file to insert the fetched data to a database.
     */
    public static void dumpQuery() {
	String filename = "MapleArchive-Drops-v" + VERSION + ".sql";
	final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	try {
	    File output = new File(filename);
	    BufferedWriter bw = new BufferedWriter(new FileWriter(output));
	    PrintWriter pw = new PrintWriter(bw);
	    StringBuilder sb = new StringBuilder();
	    pw.write("/*\r\n * This file was built using the MapleArchive drop fetcher script by Sonic/Simon/Burblish. \r\n * Creation date: " + sdf.format(Calendar.getInstance().getTime()) + "\r\n * MapleArchive Database version: GlobalMS v" + VERSION + "\r\n * \r\n * -Drop chances are estimated as they cannot be obtained!-\r\n */\r\n\r\n");
	    pw.write("TRUNCATE TABLE `drop_data`;\r\n");
	    pw.write("INSERT INTO `drop_data` (`dropperid`, `itemid`, `minimum_quantity`, `maximum_quantity`, `questid`, `chance`) VALUES ");
	    for (Iterator<DropEntry> i = drop_entries.iterator(); i.hasNext();) {
		DropEntry de = i.next();
		pw.write(de.getQuerySegment());
		if (i.hasNext()) {
		    pw.write(", \r\n");
		}
	    }
	    pw.write(sb.toString());
	    pw.close();
	    bw.close();
	} catch (IOException ioe) {
	    System.out.println("Error writing to file: " + ioe.getLocalizedMessage());
	}
    }
    
    /**
     * Returns the string lying between the two specified strings.
     * 
     * @param line
     *        The string to parse
     * @param start
     *        The first string
     * @param end
     *        The last string
     * @return The string between the two specified strings
     */
    public static String getStringBetween(final String line, final String start, final String end) {
	int start_offset = line.indexOf(start) + start.length();
	return line.substring(start_offset, line.substring(start_offset).indexOf(end) + start_offset);
    }
    
    public static void main(final String[] args) {
	long startTime = System.currentTimeMillis();
	System.out.println("MapleArchive Drop Data Fetcher\nOriginal script by Simon/Burblish --- Modified version by Sonic");
	System.out.println("---------------------------------------");
	System.out.println("Here we go!");
	System.out.println();
	for (CurrentPage = 1; CurrentPage <= NumberOfPages; CurrentPage++) {
	    System.out.println("Starting to crawl page " + CurrentPage + " out of " + NumberOfPages + "...");
	    crawlPage(BASE_URL + MONSTER_PAGE + "?page=" + CurrentPage);
	}
	long dataEndTime = System.currentTimeMillis();
	
	System.out.println("Finished fetching the drop data.");
	System.out.println();
	System.out.println("Building the SQL file...");
	long sqlStartTime = System.currentTimeMillis();
	dumpQuery();
	long sqlEndTime = System.currentTimeMillis();
	System.out.println("Finished building the SQL file.");
	System.out.println("------------------------");
	System.out.println("Process finished!");
	System.out.println("Total monsters parsed: " + MonstersDone + " || Total monsters with drops: " + MonstersWithDrops + "|| Total monsters without drops: " + MonstersWithoutDrops + " || Total items: " + drop_entries.size() + " || Total errors: " + Errors);
	System.out.println("Data reading time: " + ((dataEndTime - startTime) / 1000) + " seconds || SQL building time: " + ((sqlEndTime - sqlStartTime) / 1000) + " seconds");
	System.out.println("Total time: " + ((System.currentTimeMillis() - startTime) / 1000) + " seconds.");
	System.out.println("------------------------");
	System.out.println("The SQL script can be found in this directory under the name \"" + "MapleArchive-Drops-v" + VERSION + ".sql" + "\".");
	System.out.println("Thanks for using this tool!");
	System.out.println("~Sonic/Simon/Burblish");
    }
    
    /**
     * Parses the item section from a given string and adds the data to the
     * {@link drop_entries} variable.
     * 
     * @param html_data
     *        The string to parse.
     * @param MonsterId
     *        The monster ID the drop data belongs to.
     */
    private static void parseItemSection(final String html_data, final int MonsterId, final boolean isBoss) {
	String temp_data = html_data;
	while (temp_data.contains("AJAXLoad('Item', 'id=")) {
	    int ItemId = Integer.parseInt(getStringBetween(temp_data, "AJAXLoad('Item', 'id=", "');\">"));
	    drop_entries.add(new DropEntry(ItemId, MonsterId, isBoss, VERSION));
	    if (temp_data.contains("javascript:return ")) {
		temp_data = trimUntil(temp_data, "javascript:return ");
	    } else { // Abusive shit and stuff
		return;
	    }
	}
    }
    
    /**
     * Parses a monster section from a given data string.
     * 
     * @param html_data
     *        The string containing the HTML data to parse from.
     */
    private static void parseMonsterSection(final String html_data) {
	MonstersDone++;
	int MonsterId = Integer.parseInt(getStringBetween(html_data, "alt=\"Mob:", "\" />")); // Will it blend? ;-)
	boolean isBoss = false;
	String BossString = getStringBetween(html_data, "<tr><td class=\"statName\"><b>Boss:</b></td><td class=\"statValue\">", "</td></tr>");
	if (BossString.equalsIgnoreCase("No")) {
	    isBoss = false;
	} else if (BossString.equalsIgnoreCase("Yes")) {
	    isBoss = true;
	}
	
	if (getStringBetween(html_data, "<td class=\"tdDrops\" ", "</td>").contains("<ul><li>None/Unknown</li></ul>")) {
	    //System.out.println("Whoops!");
	    MonstersWithoutDrops++;
	    return;
	}
	// Parse Equipment drops
	parseItemSection(getStringBetween(html_data, ">Equipment</a>", "</ul></li>"), MonsterId, isBoss);
	// Parse Potion drops
	parseItemSection(getStringBetween(html_data, ">Potion</a>", "</ul></li>"), MonsterId, isBoss);
	// Parse Food drops
	parseItemSection(getStringBetween(html_data, ">Food</a>", "</ul></li>"), MonsterId, isBoss);
	// Parse Arrow drops
	parseItemSection(getStringBetween(html_data, ">Arrows</a>", "</ul></li>"), MonsterId, isBoss);
	// Parse Misc. Box (The hell is that? :O)
	parseItemSection(getStringBetween(html_data, ">Misc. Box</a>", "</ul></li>"), MonsterId, isBoss);
	// Parse Familiar drops
	parseItemSection(getStringBetween(html_data, ">Familiar</a>", "</ul></li>"), MonsterId, isBoss);
	// Parse Setup drops
	parseItemSection(getStringBetween(html_data, ">Setup</a>", "</ul></li>"), MonsterId, isBoss);
	// Parse ETC drops
	parseItemSection(getStringBetween(html_data, ">Etc</a>", "</ul></li>"), MonsterId, isBoss);
	
	MonstersWithDrops++;
    }
    
    /**
     * Trims a string until the first occurrence of the provided substring,
     * including the substring itself.
     * 
     * @param line
     *        The string to trim.
     * @param until
     *        The substring to stop the trimming after.
     * @return The trimmed string.
     */
    public static String trimUntil(final String line, final String until) {
	int until_pos = line.indexOf(until);
	if (until_pos == -1) {
	    return null;
	} else {
	    return line.substring(until_pos + until.length());
	}
    }
}

