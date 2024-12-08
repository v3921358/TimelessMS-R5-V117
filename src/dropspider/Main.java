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
package dropspider;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

/**
 *
 * @author Simon
 */
public class Main {
    /**
     * @param args the command line arguments
     */
    private static ArrayList<DropEntry> drop_entries = new ArrayList<DropEntry>();
//    private static final String TEST_STRING = " <a href=\"/items/leftover/ligator-skin\" alt=\"/tip.php?nid=2138\">Ligator Skin</a>, <a href=\"/items/leftover/the-magic-rock\" alt=\"/tip.php?nid=3954\">The Magic Rock</a>, <a href=\"/items/quest/witch-grass-leaves\" alt=\"/tip.php?nid=6129\">Witch Grass Leaves</a>    </td> ";
    private static final String BASE_URL = "http://global.hidden-street.net";
    public static final int VERSION = 117;
    private static String[] pages = {"1-10", "11-20", "21-30", "31-40", "41-50", "51-60", "61-70", "71-80", "81-90", "91-100"};
    private static String[] additionalPages88 = {"101-150", "151-200"};
    private static String[] additionalPagesBB = {"101-120,", "121-140", "141-160", "161-180", "181-200"};

    public static void main(String[] args) {
        for (String s : pages) {
            crawlPage("http://global.hidden-street.net/monster/" + s);
        }
        if (VERSION > 92) { // big bang
            for (String s : additionalPagesBB) {
                crawlPage("http://global.hidden-street.net/monster/" + s);
            }
            crawlPage("http://global.hidden-street.net/monster/101-120?page=1"); //page 1's bugged
        } else {
            for (String s : additionalPages88) {
                crawlPage("http://global.hidden-street.net/monster/" + s);
            }
        }
        dumpQuery();
    }

    private static void crawlPage(String url) { //recursive method
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
            while (temp_data.contains("class=\"monster\">")) {
                String monster_section = getStringBetween(temp_data, "class=\"monster\">", "</table>");
                parseMonsterSection(monster_section);
                temp_data = trimUntil(temp_data, "</table>");
            }
            if (temp_data.contains("Go to next page")) {
                String next_url_segment = getStringBetween(temp_data, "<li class=\"pager-next\"><a href=\"", "\" title=\"Go to next page");
                String next_url = BASE_URL + next_url_segment;
                crawlPage(next_url);
            } else {
                System.out.println("Finished crawling section.");
            }
        } catch (MalformedURLException mue) {
            System.out.println("Error parsing URL: " + url);
            return;
        } catch (IOException ioe) {
            System.out.println("Error reading from URL: " + ioe.getLocalizedMessage());
            return;
        }
    }

    private static void parseMonsterSection(String html_data) {
        String monster_name = getStringBetween(html_data, "alt=\"", "\" title=");
        System.out.println(monster_name);
        //  System.out.println(html_data);
        //parse etc drop
        parseItemSection(getStringBetween(html_data, "Etc. drop:</strong>", "</td>"), monster_name);
        //parse useable drop
        parseItemSection(getStringBetween(html_data, "Useable drop:</strong>", "</td>"), monster_name);
        //parse ore drop
        parseItemSection(getStringBetween(html_data, "Ore drop:</strong>", "</td>"), monster_name);
        //parse equips
        parseItemSection(getStringBetween(html_data, "Common equipment:</strong>", "</div>"), monster_name);
        parseItemSection(getStringBetween(html_data, "Warrior equipment:</strong>", "</div>"), monster_name);
        parseItemSection(getStringBetween(html_data, "Magician equipment:</strong>", "</div>"), monster_name);
        parseItemSection(getStringBetween(html_data, "Bowman equipment:</strong>", "</div>"), monster_name);
        parseItemSection(getStringBetween(html_data, "Thief equipment:</strong>", "</div>"), monster_name);
        parseItemSection(getStringBetween(html_data, "Pirate equipment:</strong>", "</div>"), monster_name);

        //System.out.println(monster_name);
    }

    private static void parseItemSection(String html_data, String monster_name) {
        String temp_data = html_data;
        while (temp_data.contains("<a href")) {
            //  System.out.println("Temp_data: " + temp_data);
            String s1 = trimUntil(temp_data, ">");
            String item_name = getStringBetween(s1, "", "</a>");
            temp_data = trimUntil(temp_data, "</a>");
            
            boolean gender_equip = false;
            if (item_name.contains("(M)") || item_name.contains("(F)")) {
                item_name = item_name.replaceAll("(\\(M\\))|(\\(F\\))", "");
                gender_equip = true;
            }
            item_name = item_name.replaceAll("Throwing-Star", "Throwing-Stars").trim();
//            System.out.println("Item name: " + item_name);

            //drop entry
            ArrayList<Integer> monster_ids = DataTool.monsterIdsFromName(monster_name);
            ArrayList<Integer> item_ids = DataTool.itemIdsFromName(item_name);

            if (!monster_ids.isEmpty() && !item_ids.isEmpty()) {
                int item_id = item_ids.get(0);
                int item_id_2 = -1;
                for (Integer mob_id : monster_ids) {
                    System.out.println("Monster ID: " + mob_id + ", Item ID: " + item_id);
                    drop_entries.add(new DropEntry(item_id, mob_id, VERSION));
                    if (gender_equip && item_ids.size() > 1) {
                        item_id_2 = item_ids.get(1);
                        drop_entries.add(new DropEntry(item_id_2, mob_id, VERSION));

                    }
                }
            } else {
                System.out.println("Error parsing item " + item_name + " dropped by " + monster_name + ".");
//                System.out.println("Monster ids size: " + monster_ids.size() + ", Item IDs size: " + item_ids.size());
            }

        }
    }

    /**
     * Returns the string lying between the two specified strings.
     * @param line The string to parse
     * @param start The first string
     * @param end The last string
     * @return The string between the two specified strings
     */
    public static String getStringBetween(String line, String start, String end) {
        int start_offset = line.indexOf(start) + start.length();
        return line.substring(start_offset, line.substring(start_offset).indexOf(end) + start_offset);
    }

    public static String trimUntil(String line, String until) {
        int until_pos = line.indexOf(until);
        if (until_pos == -1) {
            return null;
        } else {
            return line.substring(until_pos + until.length());
        }
    }

    public static void dumpQuery() {
        String filename = "drops.sql";
        try {
            File output = new File(filename);
            BufferedWriter bw = new BufferedWriter(new FileWriter(output));
            PrintWriter pw = new PrintWriter(bw);
            StringBuilder sb = new StringBuilder();
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
}