/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package client.messages.commands;

import client.MapleCharacter;
import client.MapleCharacterUtil;
import client.MapleClient;
import client.inventory.Equip;
import client.inventory.Item;
import client.inventory.MapleInventoryType;
import client.messages.CommandProcessorUtil;
import constants.GameConstants;
import constants.ServerConstants.PlayerGMRank;
import handling.channel.ChannelServer;
import handling.world.World;
import java.util.LinkedList;
import server.MapleInventoryManipulator;
import server.MapleItemInformationProvider;
import server.MaplePortal;
import server.maps.MapleMap;
import tools.StringUtil;
import tools.packet.CWvsContext;

/**
 *
 * @author Emilyx3
 */
public class SuperDonatorCommand {

    public static PlayerGMRank getPlayerLevelRequired() {
        return PlayerGMRank.SUPERDONATOR;
    }
    
     
        
            public static class itemi extends CommandExecute {
   static int[] blockedids = {4001471, 4000519, 4000520, 1002959, 1532000, 1532001, 1532005, 1532006, 1532045, 1532046, 1532050, 1532051, 1532039, 1522022, 1522019, 1522017, 4000174, 1112309, 1112310, 1112311, 4001126, 4031830, 1002140, 1003274, 1062140, 1042223, 1003142, 1042003, 1062007, 1322013};

        @Override
        public int execute(MapleClient c, String[] splitted) {
            final int itemId = Integer.parseInt(splitted[1]);
            final short quantity = (short) CommandProcessorUtil.getOptionalIntArg(splitted, 2, 1);

            MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
                            for (int i = 0; i < blockedids.length; i++) {
        	if (itemId == blockedids[i]) {
                c.getPlayer().dropMessage(5, "You can't make this item");
		return 0;
                }
                }
            if (GameConstants.isPet(itemId)) {
                c.getPlayer().dropMessage(5, "Please purchase a pet from the cash shop instead.");
            } else if (!ii.itemExists(itemId)) {
                c.getPlayer().dropMessage(5, itemId + " does not exist");
            } else {
                Item item;

                if (GameConstants.getInventoryType(itemId) == MapleInventoryType.EQUIP) {
                    item = ii.randomizeStats((Equip) ii.getEquipById(itemId));
                } else {
                    item = new client.inventory.Item(itemId, (byte) 0, quantity, (byte) 0);

                }
                    item.setOwner(c.getPlayer().getName() + "'s SD Item");
                    item.setGMLog(c.getPlayer().getName() + " used $itemi");


                MapleInventoryManipulator.addbyItem(c, item);
            }
            return 1;
        }
    }
                           
        public static class smega extends CommandExecute {

        @Override
        public int execute(MapleClient c, String[] splitted) {
            MapleCharacter player = c.getPlayer();
    MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
     String medal = "";
        Item medalItem = c.getPlayer().getInventory(MapleInventoryType.EQUIPPED).getItem((byte) -49);
        if (medalItem != null) {
            medal = "<" + ii.getName(medalItem.getItemId()) + "> ";
        }
            for (ChannelServer cservs : ChannelServer.getAllInstances()) {
                for (MapleCharacter online : cservs.getPlayerStorage().getAllCharacters()) {
                    online.getClient().getSession().write(CWvsContext.serverNotice(3, c.getChannel(), medal + player.getName() + " : " + StringUtil.joinStringFrom(splitted, 1)));
                }
            }
            return 1;
        }
        }

    

                

            
        public static class online extends CommandExecute {

        @Override
        public int execute(MapleClient c, String[] splitted) {
            MapleCharacter player = c.getPlayer();
            int i = 0;
            for (ChannelServer cs : ChannelServer.getAllInstances()) {
                if (cs.getPlayerStorage().getAllCharacters().size() > 0) {
                    StringBuilder sb = new StringBuilder();
                     player.dropMessage(5, "Channel " + cs.getChannel());
                    for (MapleCharacter chr : cs.getPlayerStorage().getAllCharacters()) {
                        i++;
                        if (sb.length() > 150) {
                             player.dropMessage(5, sb.toString());
                            sb = new StringBuilder();
                        }
                        sb.append(MapleCharacterUtil.makeMapleReadable(chr.getName() + "   "));
                    }
                     player.dropMessage(5, sb.toString());
                }
            }
            return 1;
        }
        }
            
         
            
            
            
            
    public static class chat extends CommandExecute {

        @Override
        public int execute(MapleClient c, String[] splitted) {
            if (splitted.length > 1) {
                StringBuilder sb = new StringBuilder();
                sb.append("[");
                sb.append("Super Donor - ");
                sb.append(c.getPlayer().getName());
                sb.append("] ");
                sb.append(StringUtil.joinStringFrom(splitted, 1));
                World.Broadcast.broadcastMessage(CWvsContext.serverNotice(c.getPlayer().isGM() ? 6 : 5, sb.toString()));
            } else {
                c.getPlayer().dropMessage(6, "Syntax: chat <message>");
                return 0;
            }
            return 1;
        }
    }

        public static class Warp extends CommandExecute {

        @Override
        public int execute(MapleClient c, String[] splitted) {
            MapleCharacter victim = c.getChannelServer().getPlayerStorage().getCharacterByName(splitted[1]);
            if (victim != null && !victim.inPVP() && !c.getPlayer().inPVP()) {
                if (splitted.length == 2) {
                    c.getPlayer().changeMap(victim.getMap(), victim.getMap().findClosestSpawnpoint(victim.getTruePosition()));
                }
            } else {
                try {
                    victim = c.getPlayer();
                    int ch = World.Find.findChannel(splitted[1]);
                    if (ch < 0) {
                        MapleMap target = c.getChannelServer().getMapFactory().getMap(Integer.parseInt(splitted[1]));
                        if (target == null) {
                            c.getPlayer().dropMessage(6, "Map does not exist");
                            return 0;
                        }
                        MaplePortal targetPortal = null;
                        if (splitted.length > 2) {
                            try {
                                targetPortal = target.getPortal(Integer.parseInt(splitted[2]));
                            } catch (IndexOutOfBoundsException e) {
                                // noop, assume the gm didn't know how many portals there are
                                c.getPlayer().dropMessage(5, "Invalid portal selected.");
                            } catch (NumberFormatException a) {
                                // noop, assume that the gm is drunk
                            }
                        }
                        if (targetPortal == null) {
                            targetPortal = target.getPortal(0);
                        }
                        c.getPlayer().changeMap(target, targetPortal);
                    } else {
                        victim = ChannelServer.getInstance(ch).getPlayerStorage().getCharacterByName(splitted[1]);
                        c.getPlayer().dropMessage(6, "Cross changing channel. Please wait.");
                        if (victim.getMapId() != c.getPlayer().getMapId()) {
                            final MapleMap mapp = c.getChannelServer().getMapFactory().getMap(victim.getMapId());
                            c.getPlayer().changeMap(mapp, mapp.findClosestPortal(victim.getTruePosition()));
                        }
                        c.getPlayer().changeChannel(ch);
                    }
                } catch (Exception e) {
                    c.getPlayer().dropMessage(6, "Something went wrong " + e.getMessage());
                    return 0;
                }
            }
            return 1;
        }
    }            
                        }
    

