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

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import provider.GlobalDataProvider;
import provider.MapleData;
import provider.MapleDataDirectoryEntry;
import provider.MapleDataFileEntry;
import provider.MapleDataProvider;
import provider.MapleDataProviderFactory;
import provider.MapleDataTool;
import server.MapleItemInformationProvider;
import tools.Pair;

/**
 *
 * @author Simon
 */
public class DataTool {
    private static ArrayList<Pair<Integer, String>> npc_list = null;
    private static LinkedList<Pair<Integer, String>> mob_pairs = null;
    private static MapleDataProvider data = GlobalDataProvider.getMobDataProvider();
    private static HashSet<Integer> bosses = null;

    public static ArrayList<Integer> monsterIdsFromName(String name) {
        MapleData data = null;
        MapleDataProvider dataProvider = MapleDataProviderFactory.getDataProvider(new File(System.getProperty("wzpath") + "/" + "String.wz"));
        ArrayList<Integer> ret = new ArrayList<Integer>();
        data = dataProvider.getData("Mob.img");
        if (mob_pairs == null) {
            mob_pairs = new LinkedList<Pair<Integer, String>>();
            for (MapleData mobIdData : data.getChildren()) {
                int mobIdFromData = Integer.parseInt(mobIdData.getName());
                String mobNameFromData = MapleDataTool.getString(mobIdData.getChildByPath("name"), "NO-NAME");
                mob_pairs.add(new Pair<Integer, String>(mobIdFromData, mobNameFromData));
            }
        }
        for (Pair<Integer, String> mobPair : mob_pairs) {
            if (mobPair.getRight().toLowerCase().equals(name.toLowerCase())) {
                ret.add(mobPair.getLeft());
            }
        }
        return ret;
    }

    private static void populateBossList() {
        bosses = new HashSet<Integer>();
        MapleDataDirectoryEntry mob_data = data.getRoot();
        for (MapleDataFileEntry mdfe : mob_data.getFiles()) {
            MapleData boss_candidate = data.getData(mdfe.getName());
            MapleData monsterInfoData = boss_candidate.getChildByPath("info");
            int mid = Integer.valueOf(boss_candidate.getName().replaceAll("[^0-9]", ""));
            boolean boss = MapleDataTool.getIntConvert("boss", monsterInfoData, 0) > 0 || mid == 8810018 || mid == 9410066;
            if (boss) {
                bosses.add(mid);
            }
        }
    }

    public static boolean isBoss(int mid) {
        if (bosses == null) {
            populateBossList();
        }
        return bosses.contains(mid);
    }

    public static ArrayList<Integer> itemIdsFromName(String name) {
        ArrayList<Integer> ret = new ArrayList<Integer>();
        for (Pair<Integer, String> itemPair : MapleItemInformationProvider.getInstance().getAllItems2()) {
            String item_name = itemPair.getRight().toLowerCase().replaceAll("\\&quot;", "");
            item_name = item_name.replaceAll("'", "");
            item_name = item_name.replaceAll("\\'", "");
            name = name.toLowerCase().replaceAll("\\&quot;", "");
            name = name.replaceAll("'", "");
            name = name.replaceAll("\\'", "");
            if (item_name.equals(name)) {
                ret.add(itemPair.getLeft());
                return ret;
            }
        }
        return ret;
    }

    public static ArrayList<Integer> npcIdsFromName(String name) {
        MapleDataProvider dataProvider = MapleDataProviderFactory.getDataProvider(new File(System.getProperty("wzpath") + "/" + "String.wz"));
        ArrayList<Integer> ret = new ArrayList<Integer>();
        if (npc_list == null) {
            ArrayList<Pair<Integer, String>> searchList = new ArrayList<Pair<Integer, String>>();
            for (MapleData searchData : dataProvider.getData("Npc.img").getChildren()) {
                int searchFromData = Integer.parseInt(searchData.getName());
                String infoFromData = MapleDataTool.getString(searchData.getChildByPath("name"), "NO-NAME");
                searchList.add(new Pair<Integer, String>(searchFromData, infoFromData));
            }
            npc_list = searchList;
        }
        for (Pair<Integer, String> searched : npc_list) {
            if (searched.getRight().toLowerCase().contains(name.toLowerCase())) {
                ret.add(searched.getLeft());
            }
        }
        return ret;
    }
}