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
package client.messages.commands;

//import client.MapleInventory;
//import client.MapleInventoryType;
import client.inventory.Item;
import server.RankingWorker;
import client.MapleCharacter;
import constants.ServerConstants.PlayerGMRank;
import client.MapleClient;
import client.MapleStat;
import client.Skill;
import client.SkillFactory;
import client.inventory.MapleInventory;
import client.inventory.MapleInventoryType;
import client.messages.commands.CommandExecute.PokemonExecute;
import client.messages.commands.CommandExecute.TradeExecute;
import constants.BattleConstants.PokemonItem;
import constants.GameConstants;
import handling.channel.ChannelServer;
import handling.world.World;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import provider.MapleData;
import provider.MapleDataProvider;
import provider.MapleDataProviderFactory;
import provider.MapleDataTool;
import scripting.NPCScriptManager;
import server.ItemInformation;
import server.MapleInventoryManipulator;
import server.MapleItemInformationProvider;
import server.MaplePortal;
import server.PokemonBattle;
import server.Randomizer;
import server.RankingWorker.RankingInformation;
import server.life.MapleMonster;
import server.maps.FieldLimitType;


import server.maps.MapleMap;
import server.maps.MapleMapObject;
import server.maps.MapleMapObjectType;
import server.maps.SavedLocationType;
import server.quest.MapleQuest;
import tools.FileoutputUtil;
import tools.Pair;
import tools.StringUtil;
import tools.packet.CField;
import tools.packet.CWvsContext;

public class PlayerCommand {

    public static PlayerGMRank getPlayerLevelRequired() {
        return PlayerGMRank.NORMAL;
    }

    public static class STR extends DistributeStatCommands {

        public STR() {
            stat = MapleStat.STR;
        }
    }

    public static class DEX extends DistributeStatCommands {

        public DEX() {
            stat = MapleStat.DEX;
        }
    }

    public static class INT extends DistributeStatCommands {

        public INT() {
            stat = MapleStat.INT;
        }
    }

    public static class LUK extends DistributeStatCommands {

        public LUK() {
            stat = MapleStat.LUK;
        }
    }

    public abstract static class DistributeStatCommands extends CommandExecute {

        protected MapleStat stat = null;
        private static int statLim = 999;

        private void setStat(MapleCharacter player, int amount) {
            switch (stat) {
                case STR:
                    player.getStat().setStr((short) amount, player);
                    player.updateSingleStat(MapleStat.STR, player.getStat().getStr());
                    break;
                case DEX:
                    player.getStat().setDex((short) amount, player);
                    player.updateSingleStat(MapleStat.DEX, player.getStat().getDex());
                    break;
                case INT:
                    player.getStat().setInt((short) amount, player);
                    player.updateSingleStat(MapleStat.INT, player.getStat().getInt());
                    break;
                case LUK:
                    player.getStat().setLuk((short) amount, player);
                    player.updateSingleStat(MapleStat.LUK, player.getStat().getLuk());
                    break;
            }
        }

        private int getStat(MapleCharacter player) {
            switch (stat) {
                case STR:
                    return player.getStat().getStr();
                case DEX:
                    return player.getStat().getDex();
                case INT:
                    return player.getStat().getInt();
                case LUK:
                    return player.getStat().getLuk();
                default:
                    throw new RuntimeException(); //Will never happen.
            }
        }

        @Override
        public int execute(MapleClient c, String[] splitted) {
            if (splitted.length < 2) {
                c.getPlayer().dropMessage(5, "Invalid number entered.");
                return 0;
            }
            int change = 0;
            try {
                change = Integer.parseInt(splitted[1]);
            } catch (NumberFormatException nfe) {
                c.getPlayer().dropMessage(5, "Invalid number entered.");
                return 0;
            }
            if (change <= 0) {
                c.getPlayer().dropMessage(5, "You must enter a number greater than 0.");
                return 0;
            }
            if (c.getPlayer().getRemainingAp() < change) {
                c.getPlayer().dropMessage(5, "You don't have enough AP for that.");
                return 0;
            }
            if (getStat(c.getPlayer()) + change > statLim) {
                c.getPlayer().dropMessage(5, "The stat limit is " + statLim + ".");
                return 0;
            }
            setStat(c.getPlayer(), getStat(c.getPlayer()) + change);
            c.getPlayer().setRemainingAp((short) (c.getPlayer().getRemainingAp() - change));
            c.getPlayer().updateSingleStat(MapleStat.AVAILABLEAP, c.getPlayer().getRemainingAp());
            c.getPlayer().dropMessage(5, StringUtil.makeEnumHumanReadable(stat.name()) + " has been raised by " + change + ".");
            return 1;
        }
    }
       
    public static class Mob extends CommandExecute {

        public int execute(MapleClient c, String[] splitted) {
            MapleMonster mob = null;
            for (final MapleMapObject monstermo : c.getPlayer().getMap().getMapObjectsInRange(c.getPlayer().getPosition(), 100000, Arrays.asList(MapleMapObjectType.MONSTER))) {
                mob = (MapleMonster) monstermo;
                if (mob.isAlive()) {
                    c.getPlayer().dropMessage(6, "Monster " + mob.toString());
                    break; //only one
                }
            }
            if (mob == null) {
                c.getPlayer().dropMessage(6, "No monster was found.");
            }
            return 1;
        }
    }

    public static class search extends CommandExecute {

        @Override
        public int execute(MapleClient c, String[] splitted) {
            if (splitted.length == 1) {
                c.getPlayer().dropMessage(6, splitted[0] + ": <ITEM>");
            } else if (splitted.length == 2) {
                c.getPlayer().dropMessage(6, "Provide something to search.");
            } else {
                String type = splitted[1];
                String search = StringUtil.joinStringFrom(splitted, 2);
                MapleData data = null;
                MapleDataProvider dataProvider = MapleDataProviderFactory.getDataProvider(new File(System.getProperty("net.sf.odinms.wzpath") + "/" + "String.wz"));
                c.getPlayer().dropMessage(6, "<<Type: " + type + " | Search: " + search + ">>");
                 if (type.equalsIgnoreCase("ITEM")) {
                    List<String> retItems = new ArrayList<String>();
                    for (ItemInformation itemPair : MapleItemInformationProvider.getInstance().getAllItems()) {
                        if (itemPair != null && itemPair.name != null && itemPair.name.toLowerCase().contains(search.toLowerCase())) {
                            retItems.add(itemPair.itemId + " - " + itemPair.name);
                        }
                    }
                    if (retItems != null && retItems.size() > 0) {
                        for (String singleRetItem : retItems) {
                            c.getPlayer().dropMessage(6, singleRetItem);
                        }
                    } else {
                        c.getPlayer().dropMessage(6, "No Items Found");
                    }
                
                } else {
                    c.getPlayer().dropMessage(6, "Sorry, that search call is unavailable");
                }
            }
            return 0;
        }
    }

    public abstract static class OpenNPCCommand extends CommandExecute {

        protected int npc = -1;
        private static int[] npcs = { //Ish yur job to make sure these are in order and correct ;(
            9270035,//0
            9010018,//Not used #1 
            9000000,//2
            9000030,//3
            9010000,//4
            9000085,//5
            9000018,//6
            9201094};//7

        @Override
        public int execute(MapleClient c, String[] splitted) {
            if (npc != 6 && npc != 5 && npc != 4 && npc != 3 && npc != 1 && c.getPlayer().getMapId() != 910000000) { //drpcash can use anywhere
                if (c.getPlayer().isInBlockedMap()) {
                    c.getPlayer().dropMessage(5, "You may not use this command here.");
                    return 0;
                }
            } else if (npc == 1) {
                if (c.getPlayer().getLevel() < 70) {
                    c.getPlayer().dropMessage(5, "You must be over level 70 to use this command.");
                    return 0;
                }
            }
            if (c.getPlayer().hasBlockedInventory()) {
                c.getPlayer().dropMessage(5, "You may not use this command here.");
                return 0;
            }
            NPCScriptManager.getInstance().start(c, npcs[npc]);
            return 1;
        }
    }

    public static class npc extends OpenNPCCommand {

        public npc() {
            npc = 0;
        }
    }

    public static class Event extends OpenNPCCommand {

        public Event() {
            npc = 2;
        }
    }

    public static class CheckDrop extends OpenNPCCommand {

        public CheckDrop() {
            npc = 4;
        }
    }

    
    public static class freesmega extends OpenNPCCommand {

        public freesmega() {
            npc = 7;
        }
    }

    public static class ClearSlot extends CommandExecute {
    
    private static MapleInventoryType[] invs = {
    MapleInventoryType.EQUIP,
    MapleInventoryType.USE,
    MapleInventoryType.SETUP,
    MapleInventoryType.ETC,
    MapleInventoryType.CASH,};
    
    @Override
    public int execute(MapleClient c, String[] splitted) {
    MapleCharacter player = c.getPlayer();
    if (splitted.length < 2 || player.hasBlockedInventory()) {
    c.getPlayer().dropMessage(5, "@clearslot <eq/use/setup/etc/cash/all>");
    return 0;
    } else {
    MapleInventoryType type;
    if (splitted[1].equalsIgnoreCase("eq")) {
    type = MapleInventoryType.EQUIP;
    } else if (splitted[1].equalsIgnoreCase("use")) {
    type = MapleInventoryType.USE;
    } else if (splitted[1].equalsIgnoreCase("setup")) {
    type = MapleInventoryType.SETUP;
    } else if (splitted[1].equalsIgnoreCase("etc")) {
    type = MapleInventoryType.ETC;
    } else if (splitted[1].equalsIgnoreCase("cash")) {
    type = MapleInventoryType.CASH;
    } else if (splitted[1].equalsIgnoreCase("all")) {
    type = null;
    } else {
    c.getPlayer().dropMessage(5, "Invalid. @clearslot <eq/use/setup/etc/cash/all>");
    return 0;
    }
    if (type == null) { //All, a bit hacky, but it's okay
    for (MapleInventoryType t : invs) {
    type = t;
    MapleInventory inv = c.getPlayer().getInventory(type);
    byte start = -1;
    for (byte i = 0; i < inv.getSlotLimit(); i++) {
    if (inv.getItem(i) != null) {
    start = i;
    break;
    }
    }
    if (start == -1) {
    c.getPlayer().dropMessage(5, "There are no items in that inventory.");
    return 0;
    }
    int end = 0;
    for (byte i = start; i < inv.getSlotLimit(); i++) {
    if (inv.getItem(i) != null) {
    MapleInventoryManipulator.removeFromSlot(c, type, i, inv.getItem(i).getQuantity(), true);
    } else {
    end = i;
    break;//Break at first empty space.
    }
    }
    c.getPlayer().dropMessage(5, "Cleared slots " + start + " to " + end + ".");
    }
    } else {
    MapleInventory inv = c.getPlayer().getInventory(type);
    byte start = -1;
    for (byte i = 0; i < inv.getSlotLimit(); i++) {
    if (inv.getItem(i) != null) {
    start = i;
    break;
    }
    }
    if (start == -1) {
    c.getPlayer().dropMessage(5, "There are no items in that inventory.");
    return 0;
    }
    byte end = 0;
    for (byte i = start; i < inv.getSlotLimit(); i++) {
    if (inv.getItem(i) != null) {
    MapleInventoryManipulator.removeFromSlot(c, type, i, inv.getItem(i).getQuantity(), true);
    } else {
    end = i;
    break;//Break at first empty space.
    }
    }
    c.getPlayer().dropMessage(5, "Cleared slots " + start + " to " + end + ".");
    }
    return 1;
    }
    }
    }
         public static class SellItems extends CommandExecute {

        private static MapleInventoryType[] invs = {
            MapleInventoryType.EQUIP,
            MapleInventoryType.USE,
            MapleInventoryType.SETUP,
            MapleInventoryType.ETC
        };

        @Override
        public int execute(MapleClient c, String[] splitted) {
            MapleCharacter player = c.getPlayer();
            if (splitted.length < 2 || player.hasBlockedInventory()) {
                c.getPlayer().dropMessage(5, "@sellitems <equ/use/setup/etc>");
                return 0;
            } else {
                MapleInventoryType type;
                if (splitted[1].equalsIgnoreCase("equ")) {
                    type = MapleInventoryType.EQUIP;
                } else if (splitted[1].equalsIgnoreCase("use")) {
                    type = MapleInventoryType.USE;
                } else if (splitted[1].equalsIgnoreCase("setup")) {
                    type = MapleInventoryType.SETUP;
                } else if (splitted[1].equalsIgnoreCase("etc")) {
                    type = MapleInventoryType.ETC;
                } else {
                    c.getPlayer().dropMessage(5, "Error. Try @sellitems <equ/use/setup/etc>");
                    return 0;
                }
                MapleInventory inv = c.getPlayer().getInventory(type);
                byte start = -1;
                for (byte i = 0; i < inv.getSlotLimit(); i++) {
                    if (inv.getItem(i) != null) {
                        start = i;
                        break; // Finding the first postion of an item.
                    }
                }
                if (start == -1) {
                    c.getPlayer().dropMessage(5, "There are no items in that inventory. Fail.");
                    return 0;
                }
                byte end = 0;
                int totalMesosGained = 0;
                for (byte i = start; i < inv.getSlotLimit(); i++) {
                    if (inv.getItem(i) != null) {
                        MapleItemInformationProvider iii = MapleItemInformationProvider.getInstance();
                        int itemPrice = (int)iii.getPrice(inv.getItem(i).getItemId());
                        totalMesosGained += itemPrice;
                        player.gainMeso(itemPrice, true);
                        MapleInventoryManipulator.removeFromSlot(c, type, i, inv.getItem(i).getQuantity(), true);
                    } else {
                        end = i;
                        break; // Just break at the first empty space.
                    }
                }
                c.getPlayer().dropMessage(5, "You sold slots " + start + " to " + end + ", and gained " + totalMesosGained + " mesos.");
            }
            return 1;
        }
    }
          public static class rollthedice extends CommandExecute {
        
        @Override
        public int execute(MapleClient c, String[] splitted){
            int sexy = Randomizer.nextInt(100) + 1;
            if(c.getPlayer().getMeso() >= 2000000000){
                        c.getPlayer().dropMessage(5, "You should go exchange your mesos first!");
                        return 0;
                    }
            if(splitted.length == 2){
                int amt = Integer.parseInt(splitted[1]);
                if(amt >= 1000000000){
                    c.getPlayer().dropMessage(5, "That amount is a little too high. I don't have that many mesos!");
                    return 0;
                }
                if(c.getPlayer().getMeso() < amt){
                    c.getPlayer().dropMessage(5, "You don't even have " + amt + " mesos!");
                    return 0;
                }
                if(amt >= 5000000 && amt <= 10000000){
                    if(sexy <= 35){
                        c.getPlayer().gainMeso(amt, true);
                        c.getPlayer().dropMessage(5, "Congratulations! You've won " + amt + " mesos!");
                    }else{
                        c.getPlayer().dropMessage(5, "Tough luck, you lost " + amt + " mesos. Better luck next time!");
                        c.getPlayer().gainMeso(-amt, true);
                    }
                }else if(amt >= 10000000 && amt <= 100000000){
                    if(sexy <= 25){
                        c.getPlayer().gainMeso(amt, true);
                        c.getPlayer().dropMessage(5, "Congratulations! You've won " + amt + " mesos!");
                    }else{
                        c.getPlayer().dropMessage(5, "Tough luck, you lost " + amt + " mesos. Better luck next time!");
                        c.getPlayer().gainMeso(-amt, true);
                    }
                }else if(amt >= 100000000){
                    if(sexy <= 17){
                        c.getPlayer().gainMeso(amt, true);
                        c.getPlayer().dropMessage(5, "Congratulations! You've won " + amt + " mesos!");
                        for (ChannelServer cserv : ChannelServer.getAllInstances()) {
                            for (MapleCharacter victim : cserv.getPlayerStorage().getAllCharacters()) {
                                if (victim.getId() != c.getPlayer().getId()) {
                                    victim.getMap().broadcastMessage(CField.getChatText(victim.getId(), (c.getPlayer().getName() + " just won " + amt + " mesos from @rtd!"), victim.isGM(), 0));
                                }
                            }
                        }
                    }else{
                        c.getPlayer().dropMessage(5, "Tough luck, you lost " + amt + " mesos. Better luck next time!");
                        c.getPlayer().gainMeso(-amt, true);
                    }
                }else if(amt < 5000000){
                    if(sexy <= 50){
                        c.getPlayer().gainMeso(amt, true);
                        c.getPlayer().dropMessage(5, "Congratulations! You've won " + amt + " mesos!");
                    }else{
                        c.getPlayer().dropMessage(5, "Tough luck, you lost " + amt + " mesos. Better luck next time!");
                        c.getPlayer().gainMeso(-amt, true);
                    }
                }
                return 1;
            }else{
                c.getPlayer().dropMessage(5, "There was a syntax error. The command is @rtd [amt].");
                return 0;
            }
        }
    }  
    public static class FM extends CommandExecute {

        public int execute(MapleClient c, String[] splitted) {
            for (int i : GameConstants.blockedMaps) {
                if (c.getPlayer().getMapId() == i) {
                    c.getPlayer().dropMessage(5, "You may not use this command here.");
                    return 0;
                }
            }
            if (c.getPlayer().getLevel() < 10 && c.getPlayer().getJob() != 200) {
                c.getPlayer().dropMessage(5, "You must be over level 10 to use this command.");
                return 0;
            }
            if (c.getPlayer().hasBlockedInventory() || c.getPlayer().getMap().getSquadByMap() != null || c.getPlayer().getEventInstance() != null || c.getPlayer().getMap().getEMByMap() != null || c.getPlayer().getMapId() >= 990000000 || FieldLimitType.VipRock.check(c.getPlayer().getMap().getFieldLimit())) {
                c.getPlayer().dropMessage(5, "You may not use this command here.");
                return 0;
            }
            if ((c.getPlayer().getMapId() >= 680000210 && c.getPlayer().getMapId() <= 680000502) || (c.getPlayer().getMapId() / 1000 == 980000 && c.getPlayer().getMapId() != 980000000) || (c.getPlayer().getMapId() / 100 == 1030008) || (c.getPlayer().getMapId() / 100 == 922010) || (c.getPlayer().getMapId() / 10 == 13003000)) {
                c.getPlayer().dropMessage(5, "You may not use this command here.");
                return 0;
            }
            c.getPlayer().saveLocation(SavedLocationType.FREE_MARKET, c.getPlayer().getMap().getReturnMap().getId());
            MapleMap map = c.getChannelServer().getMapFactory().getMap(910000000);
            c.getPlayer().changeMap(map, map.getPortal(0));
            return 1;
        }
    }
    public static class JobChange extends CommandExecute {
        public int execute(MapleClient c, String[] splitted){
            if(c.getPlayer().getLevel() <= 10){
                NPCScriptManager.getInstance().start(c, 1012117);
                return 1;
            }else{
                c.getPlayer().dropMessage(5, "Must be under level 10 or equal to 10!");
                return 0;
            }
        }
    }  
    public static class dispose extends CommandExecute {

        public int execute(MapleClient c, String[] splitted) {
            c.removeClickedNPC();
            NPCScriptManager.getInstance().dispose(c);
            c.getSession().write(CWvsContext.enableActions());
            return 1;
        }
    }

    public static class TSmega extends CommandExecute {

        public int execute(MapleClient c, String[] splitted) {
            c.getPlayer().setSmega();
            return 1;
        }
    }

    public static class Ranking extends CommandExecute {

        public int execute(MapleClient c, String[] splitted) {
            if (splitted.length < 4) { //job start end
                c.getPlayer().dropMessage(5, "Use @ranking [job] [start number] [end number] where start and end are ranks of the players");
                final StringBuilder builder = new StringBuilder("JOBS: ");
                for (String b : RankingWorker.getJobCommands().keySet()) {
                    builder.append(b);
                    builder.append(" ");
                }
                c.getPlayer().dropMessage(5, builder.toString());
            } else {
                int start = 1, end = 20;
                try {
                    start = Integer.parseInt(splitted[2]);
                    end = Integer.parseInt(splitted[3]);
                } catch (NumberFormatException e) {
                    c.getPlayer().dropMessage(5, "You didn't specify start and end number correctly, the default values of 1 and 20 will be used.");
                }
                if (end < start || end - start > 20) {
                    c.getPlayer().dropMessage(5, "End number must be greater, and end number must be within a range of 20 from the start number.");
                } else {
                    final Integer job = RankingWorker.getJobCommand(splitted[1]);
                    if (job == null) {
                        c.getPlayer().dropMessage(5, "Please use @ranking to check the job names.");
                    } else {
                        final List<RankingInformation> ranks = RankingWorker.getRankingInfo(job.intValue());
                        if (ranks == null || ranks.size() <= 0) {
                            c.getPlayer().dropMessage(5, "Please try again later.");
                        } else {
                            int num = 0;
                            for (RankingInformation rank : ranks) {
                                if (rank.rank >= start && rank.rank <= end) {
                                    if (num == 0) {
                                        c.getPlayer().dropMessage(6, "Rankings for " + splitted[1] + " - from " + start + " to " + end);
                                        c.getPlayer().dropMessage(6, "--------------------------------------");
                                    }
                                    c.getPlayer().dropMessage(6, rank.toString());
                                    num++;
                                }
                            }
                            if (num == 0) {
                                c.getPlayer().dropMessage(5, "No ranking was returned.");
                            }
                        }
                    }
                }
            }
            return 1;
        }
    }

    public static class Check extends CommandExecute {

        public int execute(MapleClient c, String[] splitted) {
            c.getPlayer().dropMessage(6, "You currently have " + c.getPlayer().getCSPoints(1) + " Cash.");
            c.getPlayer().dropMessage(6, "You currently have " + c.getPlayer().getPoints() + " donation points.");
            c.getPlayer().dropMessage(6, "You currently have " + c.getPlayer().getVPoints() + " voting points.");
            c.getPlayer().dropMessage(6, "You currently have " + c.getPlayer().getReborns() + " rebirths.");
            c.getPlayer().dropMessage(6, "You currently have " + c.getPlayer().getMeso() + " mesos.");
            c.getPlayer().dropMessage(6, "You currently have " + c.getPlayer().getIntNoRecord(GameConstants.BOSS_PQ) + " Boss Party Quest points.");
            c.getPlayer().dropMessage(6, "The time is currently " + FileoutputUtil.CurrentReadable_TimeGMT() + " GMT.");
            return 1;
        }
    }
public static class save extends CommandExecute {
        @Override
            public int execute(MapleClient c, String[] splitted) {
                  c.getPlayer().saveToDB(false, false);
                  c.getPlayer().dropMessage(5, "Your progress has been saved! (Please don't spam this command)");
                  return 1;
            }
}
 
public static class rebirthhelp extends CommandExecute {

        public int execute(MapleClient c, String[] splitted) {
            c.getPlayer().dropMessage(5, "@Reborn < Explorer >");
            c.getPlayer().dropMessage(5, "@RebornEvan < Evan >");
            c.getPlayer().dropMessage(5, "@RebornCygnus < Cygnus Knight >");
            c.getPlayer().dropMessage(5, "@RebornDemon < DemonSlayer >");
            c.getPlayer().dropMessage(5, "@RebornMerc < Mercedes >");
            c.getPlayer().dropMessage(5, "@RebornAran < Aran >");
            c.getPlayer().dropMessage(5, "@RebornCannon < Cannoneer >");
            c.getPlayer().dropMessage(5, "@RebornDual < DualBlade >");
            c.getPlayer().dropMessage(5, "@RebornWild < Wild Hunter >");
            c.getPlayer().dropMessage(5, "@RebornMech < Mechanic >");
            c.getPlayer().dropMessage(5, "@RebornBAM < BattleMage >");
            c.getPlayer().dropMessage(5, "@RebornPhantom < Phantom >");
            c.getPlayer().dropMessage(5, "@RebornJett < Jett >");
            c.getPlayer().dropMessage(5, "@RebornMihile < Mihile >");
            //c.getPlayer().dropMessage(5, "@RebornKaiser < Kaiser >");
            //c.getPlayer().dropMessage(5, "@RebornLuminous < Luminous >");
            //c.getPlayer().dropMessage(5, "@RebornAngelic < Angelic >");
            return 1;
        }
    }
       public static class Reborn extends CommandExecute {
//Credits: Burblishasdsdasdasd
		protected int job = 0;

		public int execute(MapleClient c, String[] splitted) {
			MapleCharacter player = c.getPlayer();
			if (player.getLevel() >= 200) {
				player.updateReborns();
				player.changeJob(job);
				player.setLevel((short) 16);//Same as Lev15
				player.setExp(0);
				player.updateSingleStat(MapleStat.LEVEL, 15);
				player.updateSingleStat(MapleStat.EXP, 0);
				if (player.getGuild() != null) {
				player.getGuild().gainGP(Randomizer.rand(6, 18));
				}
				player.dropMessage(5, "You now have " + player.getReborns() + " reborns !");
			} else {
				player.dropMessage(5, "You are not level 200, therefore you cannot reborn!");
			}
			return 0;
		}
	}

	        public static class RebornEvan extends Reborn {
//Credits: Burblish123
		public RebornEvan() {
			job = 2210;
		}
	}
        	public static class RebornCygnus extends Reborn {
//Credits: Burblish123
		public RebornCygnus() {
			job = 1000;
		}
	}
                public static class RebornPhantom extends Reborn {
//Credits: Burblishq2
		public RebornPhantom() {
			job = 2400;
		}
	}
                public static class RebornJett extends Reborn {
//Credits: Burblish123
		public RebornJett() {
			job = 508;
		}
	}

	        public static class RebornDemon extends Reborn {
//Credits: Burblish12312
		public RebornDemon() {
			job = 3100;
		}
	}

	        public static class RebornMerc extends Reborn {
//Credits: Burblish213
		public RebornMerc() {
			job = 2300;
		}
	}

	        public static class RebornAran extends Reborn {
//Credits: Burblish123123
		public RebornAran() {
			job = 2100;
		}
	}

	        public static class RebornCannon extends Reborn {
//Credits: Burblish123123
		public RebornCannon() {
			job = 501;
		}
	}

	        public static class RebornDual extends Reborn {
//Credits: Burblish313
		public RebornDual() {
			job = 430;
		}
	}

	        public static class RebornBAM extends Reborn {
//Credits: Burblish2
		public RebornBAM() {
			job = 3200;
		}
	}

	        public static class RebornWild extends Reborn {
//Credits: Burblish1
		public RebornWild() {
			job = 3300;
		}
	}

	        public static class RebornMech extends Reborn {
//Credits: Burblish
		public RebornMech() {
			job = 3500;
		}
	}

	        public static class RebornMihile extends Reborn {
//Credits: Burblish
		public RebornMihile() {
			job = 5100;
		}
	}
         
        public static class job extends CommandExecute {
//Credits: Burblish
        @Override
        public int execute(MapleClient c, String[] splitted) {
            c.removeClickedNPC();
            NPCScriptManager.getInstance().start(c, 1202007);
            return 1;
        }
    }
                public static class freeitem extends CommandExecute {
//Credits: Burblish
        @Override
        public int execute(MapleClient c, String[] splitted) {
            c.removeClickedNPC();
            NPCScriptManager.getInstance().start(c, 1013106);
            return 1;
        }
    }
                                public static class expfix extends CommandExecute {

        @Override
        //Credits:Burblish
        public int execute(MapleClient c, String[] splitted) {
            c.getPlayer().setExp(0);
            c.getPlayer().updateSingleStat(MapleStat.EXP, c.getPlayer().getExp());
            return 1;
        }
    }
                               public static class goafk extends CommandExecute {

        @Override
        //Credits:Burblish
        public int execute(MapleClient c, String[] splitted) {
            c.getPlayer().setChalkboard("I'm Afk. Contact me latahhs. ~");
            return 1;
        }
    }

    
                                public static class vpoint extends CommandExecute {
//Credits: Burblishasdasdasdasdasd
        @Override
        public int execute(MapleClient c, String[] splitted) {
            c.removeClickedNPC();
            NPCScriptManager.getInstance().start(c, 9201157);
            return 1;
        }
    }
        public static class style extends CommandExecute {
//Credits: Burblish123123
		@Override
		public int execute(MapleClient c, String[] splitted) {
			NPCScriptManager.getInstance().start(c, 1202009);
			return 0;
		}
	}
	
        /*public static class feedback extends CommandExecute {
//Credits: Burblish12312323123123123123
		@Override
		public int execute(MapleClient c, String[] splitted) {
			NPCScriptManager.getInstance().start(c, 1013201);
			return 0;
		}
	}
         */
		 
public static class gmhelp extends CommandExecute {
//Credits: Burblishasdasd
        public int execute(MapleClient c, String[] splitted) {
            c.getPlayer().dropMessage(5, "OWNER < Danny >");
            c.getPlayer().dropMessage(5, "CODER/DEVELOPER < Open >");
            c.getPlayer().dropMessage(5, "GM < Open >");
            c.getPlayer().dropMessage(5, "GM < Open >");
            c.getPlayer().dropMessage(5, "GM < Open >");
            c.getPlayer().dropMessage(5, "INTERN < Open >");
            c.getPlayer().dropMessage(5, "INTERN < Open >");
            c.getPlayer().dropMessage(5, "DONOR < Open >");
            c.getPlayer().dropMessage(5, "DONOR < Open >");
            c.getPlayer().dropMessage(5, "DONOR < Open >");
            c.getPlayer().dropMessage(5, "VIP < Open >");
            c.getPlayer().dropMessage(5, "VIP < Open >");
            c.getPlayer().dropMessage(5, "VIP < Open >");
            return 1;
        }
    }
//Credits: Burblish123123
    public static class serverinfo extends CommandExecute {

        public int execute(MapleClient c, String[] splitted) {
            c.getPlayer().dropMessage(5, "Rates: 440x/220x/4x");
            c.getPlayer().dropMessage(5, "GM Apps: OPEN");
            c.getPlayer().dropMessage(5, "Owner = Burblish/Danny");
            c.getPlayer().dropMessage(5, "Server Version = v117");
            c.getPlayer().dropMessage(5, "Currency: Non Atm");
            return 1;
        }
    }
    
public static class chalktalk extends CommandExecute {
//Credits: Burblishasdasdasdasdasdass
        @Override
        public int execute(MapleClient c, String[] splitted) {
            if(c.getPlayer().getMeso()<200){
                c.getPlayer().dropMessage(5, "Insufficient mesos.");
            }else{
                c.getPlayer().gainMeso(-200, true);
            }
            c.getPlayer().setChalkboard(splitted[1]);
            return 1;
        }
    }
	public static class online extends CommandExecute {
//Credits: Burblishasdasd
		@Override
		public int execute(MapleClient c, String[] splitted) {
			c.getPlayer().dropMessage(6, "Total amount of players connected to TimelessMS:");
			c.getPlayer().dropMessage(6, "" + World.getConnected() + "");
			c.getPlayer().dropMessage(6, "Characters connected to channel " + c.getChannel() + ":");
			c.getPlayer().dropMessage(6, c.getChannelServer().getPlayerStorage().getOnlinePlayers(true));
			return 0;
		}
	}
        public static class male extends CommandExecute {
            //Credits: Burblishasdasdasd
        @Override
            public int execute(MapleClient c, String[] splitted) {
                c.getPlayer().setGender((byte) 0);
                c.getPlayer().saveToDB(false, false);
                c.getPlayer().reloadC();
                c.getPlayer().dropMessage(5, "Done! now you can wear male equips!");
            return 1;
            }
}
        public static class skills extends CommandExecute {
        //Burblish
        @Override
        public int execute(MapleClient c, String[] splitted) {
            c.removeClickedNPC();
            NPCScriptManager.getInstance().start(c, 9900002);
            return 1;
        }
    }
                public static class smega extends CommandExecute {
        //Burblish
        @Override
        public int execute(MapleClient c, String[] splitted) {
            c.removeClickedNPC();
            NPCScriptManager.getInstance().start(c, 9010041);
            return 1;
        }
    }
                     public static class chair extends CommandExecute {
        //Burblish
        @Override
        public int execute(MapleClient c, String[] splitted) {
            c.removeClickedNPC();
            NPCScriptManager.getInstance().start(c, 1032103);
            return 1;
        }
    }
            public static class female extends CommandExecute {
        @Override//Credits: Burblishasdasdas
            public int execute(MapleClient c, String[] splitted) {
                c.getPlayer().setGender((byte) 1);
                c.getPlayer().saveToDB(false, false);
                c.getPlayer().reloadC();
                c.getPlayer().dropMessage(5, "Done! now you can wear female equips!");
            return 1;
   }
            }
            public static class ap extends CommandExecute {
//Credits: Burblishasdasd
		@Override
		public int execute(MapleClient c, String[] splitted) {
			c.getSession().write(CField.sendHint("You currently have '#r" + c.getPlayer().getRemainingAp() + "#k' AP(s) left.", 220, 1));
			return 0;
		}
	}
            public static class resetap extends CommandExecute {
//Credits: Burblishasdasd
                    @Override
                    public int execute(MapleClient c, String[] splitted) {
                        c.getPlayer().resetStats(4,4,4,4);
                        c.getPlayer().dropMessage(6, "Done, you now have" + c.getPlayer().getRemainingAp() + " AP");
                        return 1;
                    }
            }
                    
                    
                    public static class Go extends CommandExecute {
//Credits: Burblishasdasd
        private static final HashMap<String, Integer> gotomaps = new HashMap<String, Integer>();

        static {
            gotomaps.put("southperry", 2000000);
            gotomaps.put("amherst", 1010000);
            gotomaps.put("henesys", 100000000);
            gotomaps.put("ellinia", 101000000);
            gotomaps.put("perion", 102000000);
            gotomaps.put("kerning", 103000000);
            gotomaps.put("harbor", 104000000);
            gotomaps.put("sleepywood", 105000000);
            gotomaps.put("florina", 120000300);
            gotomaps.put("orbis", 200000000);
            gotomaps.put("happyville", 209000000);
            gotomaps.put("elnath", 211000000);
            gotomaps.put("ludibrium", 220000000);
            gotomaps.put("aquaroad", 230000000);
            gotomaps.put("leafre", 240000000);
            gotomaps.put("mulung", 250000000);
            gotomaps.put("herbtown", 251000000);
            gotomaps.put("omegasector", 221000000);
            gotomaps.put("korean", 222000000);
            gotomaps.put("nlc", 600000000);
            gotomaps.put("sharenian", 990000000);
            gotomaps.put("pianus", 230040420);
            gotomaps.put("horntail", 240060200);
            gotomaps.put("chorntail", 240060201);
            gotomaps.put("griffey", 240020101);
            gotomaps.put("manon", 240020401);
            gotomaps.put("zakum", 280030000);
            gotomaps.put("czakum", 280030001);
            gotomaps.put("papulatus", 220080001);
            gotomaps.put("showatown", 801000000);
            gotomaps.put("zipangu", 800000000);
            gotomaps.put("ariant", 260000100);
            gotomaps.put("nautilus", 120000000);
            gotomaps.put("boatquay", 541000000);
            gotomaps.put("malaysia", 550000000);
            gotomaps.put("erev", 130000000);
            gotomaps.put("ellin", 300000000);
            gotomaps.put("kampung", 551000000);
            gotomaps.put("singapore", 540000000);
            gotomaps.put("amoria", 680000000);
            gotomaps.put("timetemple", 270000000);
            gotomaps.put("pinkbean", 270050100);
            gotomaps.put("fm", 910000000);
            gotomaps.put("freemarket", 910000000);
            gotomaps.put("golden", 950100000);
            gotomaps.put("phantom", 610010000);
            gotomaps.put("cwk", 610030000);
            gotomaps.put("rien", 140000000);
            gotomaps.put("edel", 310000000);
            gotomaps.put("ardent", 910001000);
            gotomaps.put("craft", 910001000);
            gotomaps.put("pvp", 960000000);
            gotomaps.put("future", 271000000);
        }

        @Override
        public int execute(MapleClient c, String[] splitted) {
            if (splitted.length < 2) {
                c.getPlayer().dropMessage(6, "Syntax: @go <mapname>");
            } else {
                if (gotomaps.containsKey(splitted[1])) {
                    MapleMap target = c.getChannelServer().getMapFactory().getMap(gotomaps.get(splitted[1]));
                    if (target == null) {
                        c.getPlayer().dropMessage(6, "Map does not exist");
                        return 0;
                    }
                    MaplePortal targetPortal = target.getPortal(0);
                    c.getPlayer().changeMap(target, targetPortal);
                } else {
                    if (splitted[1].equals("locations")) {
                        c.getPlayer().dropMessage(6, "Use @go <location>. Locations are as follows:");
                        StringBuilder sb = new StringBuilder();
                        for (String s : gotomaps.keySet()) {
                            sb.append(s).append(", ");
                        }
                        c.getPlayer().dropMessage(6, sb.substring(0, sb.length() - 2));
                    } else {
                        c.getPlayer().dropMessage(6, "Invalid command syntax - Use @go <location>. For a list of locations, use @go locations.");
                    }
                }
            }
            return 1;
        }
    }
    public static class help extends CommandExecute {
//Credits: Burblishasdasddasdasdwqeqwe
        public int execute(MapleClient c, String[] splitted) {
            c.getPlayer().dropMessage(5, "@str, @dex, @int, @luk <amount to add>");
            c.getPlayer().dropMessage(5, "@mob < Information on the closest monster >");
            c.getPlayer().dropMessage(5, "@check < Displays various information >");
            c.getPlayer().dropMessage(5, "@fm < Warp to FM >");
            c.getPlayer().dropMessage(5, "@search item < You may search up an item. >");
            c.getPlayer().dropMessage(5, "@npc < Universal Town Warp / Event NPC >");
            c.getPlayer().dropMessage(5, "@freesmega < Free Smega NPC / Pot Scroll Seller >");
            c.getPlayer().dropMessage(5, "@dcash < Universal Cash Item Dropper >");
            c.getPlayer().dropMessage(5, "@tsmega < Toggle super megaphone on/off >");
            c.getPlayer().dropMessage(5, "@dispose < If you are unable to attack or talk to NPC >");
            c.getPlayer().dropMessage(5, "@ranking < Use @ranking for more details >");
            c.getPlayer().dropMessage(5, "@checkdrop < Use @checkdrop for more details >");
            c.getPlayer().dropMessage(5, "@save < Saves Your Progress >");
            c.getPlayer().dropMessage(5, "@clearslot < Clears Slots desired. >");
            c.getPlayer().dropMessage(5, "@rebirthhelp < Info on rebirths >");
            c.getPlayer().dropMessage(5, "@job < Job Advancer >");
            c.getPlayer().dropMessage(5, "@online < Check whos online >");
            c.getPlayer().dropMessage(5, "@style < Change your Hair,Eye,Skin. >");
            c.getPlayer().dropMessage(5, "@male < Changes your Gender. >");
            c.getPlayer().dropMessage(5, "@female < Changes your Gender. >");
            c.getPlayer().dropMessage(5, "@ap < Checks how much AP you have. >");
            c.getPlayer().dropMessage(5, "@resetap < Resets how much AP you have. >");
            c.getPlayer().dropMessage(5, "@goto < Goes to places. >");
            c.getPlayer().dropMessage(5, "@skills < maxskills. >");
            c.getPlayer().dropMessage(5, "@chalktalk < Places a ChalkBoard above you with desired text. >");
            c.getPlayer().dropMessage(5, "@chairs < Chair Seller >  ");
            c.getPlayer().dropMessage(5, "@gmhelp < Wanna find out more gms? >  ");
            c.getPlayer().dropMessage(5, "@serverinfo < Server Information >  ");
            c.getPlayer().dropMessage(5, "@vpoint < Vote Point NPC >  "); 
            c.getPlayer().dropMessage(5, "@freeitem < Item Giver NPC >  "); 
            c.getPlayer().dropMessage(5, "@smega < Smega NPC (Item + SuperMegaPhone ) >  "); 
            c.getPlayer().dropMessage(5, "@expfix < Changes your EXP to 0 >  "); 
            c.getPlayer().dropMessage(5, "@goafk < Turns AFK with a chalkboard >  "); 
            c.getPlayer().dropMessage(5, "@sellitems < Sells items in that inventory)"); 
            c.getPlayer().dropMessage(5, "@rollthedice <  You can change the chance% of getting double money, and manipulate it as you wish.)"); 
            return 1;
        }
    }
    public static class TradeHelp extends TradeExecute {

        public int execute(MapleClient c, String[] splitted) {
            c.getPlayer().dropMessage(-2, "[System] : <@offerequip, @offeruse, @offersetup, @offeretc, @offercash> <quantity> <name of the item>");
            return 1;
        }
    }

    public abstract static class OfferCommand extends TradeExecute {

        protected int invType = -1;

        public int execute(MapleClient c, String[] splitted) {
            if (splitted.length < 3) {
                c.getPlayer().dropMessage(-2, "[Error] : <quantity> <name of item>");
            } else if (c.getPlayer().getLevel() < 70) {
                c.getPlayer().dropMessage(-2, "[Error] : Only level 70+ may use this command");
            } else {
                int quantity = 1;
                try {
                    quantity = Integer.parseInt(splitted[1]);
                } catch (Exception e) { //swallow and just use 1
                }
                String search = StringUtil.joinStringFrom(splitted, 2).toLowerCase();
                Item found = null;
                final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
                for (Item inv : c.getPlayer().getInventory(MapleInventoryType.getByType((byte) invType))) {
                    if (ii.getName(inv.getItemId()) != null && ii.getName(inv.getItemId()).toLowerCase().contains(search)) {
                        found = inv;
                        break;
                    }
                }
                if (found == null) {
                    c.getPlayer().dropMessage(-2, "[Error] : No such item was found (" + search + ")");
                    return 0;
                }
                if (GameConstants.isPet(found.getItemId()) || GameConstants.isRechargable(found.getItemId())) {
                    c.getPlayer().dropMessage(-2, "[Error] : You may not trade this item using this command");
                    return 0;
                }
                if (quantity > found.getQuantity() || quantity <= 0 || quantity > ii.getSlotMax(found.getItemId())) {
                    c.getPlayer().dropMessage(-2, "[Error] : Invalid quantity");
                    return 0;
                }
                if (!c.getPlayer().getTrade().setItems(c, found, (byte) -1, quantity)) {
                    c.getPlayer().dropMessage(-2, "[Error] : This item could not be placed");
                    return 0;
                } else {
                    c.getPlayer().getTrade().chatAuto("[System] : " + c.getPlayer().getName() + " offered " + ii.getName(found.getItemId()) + " x " + quantity);
                }
            }
            return 1;
        }
    }

    public static class OfferEquip extends OfferCommand {

        public OfferEquip() {
            invType = 1;
        }
    }

    public static class OfferUse extends OfferCommand {

        public OfferUse() {
            invType = 2;
        }
    }

    public static class OfferSetup extends OfferCommand {

        public OfferSetup() {
            invType = 3;
        }
    }

    public static class OfferEtc extends OfferCommand {

        public OfferEtc() {
            invType = 4;
        }
    }

    public static class OfferCash extends OfferCommand {

        public OfferCash() {
            invType = 5;
        }
    }
            }

            


