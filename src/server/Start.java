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
package server;

import client.SkillFactory;
import client.inventory.MapleInventoryIdentifier;
import constants.BattleConstants;
import constants.ServerConstants;
import handling.MapleServerHandler;
import handling.channel.ChannelServer;
import handling.channel.MapleGuildRanking;
import handling.login.LoginServer;
import handling.cashshop.CashShopServer;
import handling.login.LoginInformationProvider;
import handling.world.World;
import java.sql.SQLException;
import database.DatabaseConnection;
import handling.world.family.MapleFamily;
import handling.world.guild.MapleGuild;
import java.sql.PreparedStatement;
import server.Timer.*;
import server.events.MapleOxQuizFactory;
import server.life.MapleLifeFactory;
import server.life.MapleMonsterInformationProvider;
import server.life.MobSkillFactory;
import server.life.PlayerNPC;
import server.quest.MapleQuest;
import java.util.concurrent.atomic.AtomicInteger;
import server.maps.MapleMapFactory;

public class Start {

    public static long startTime = System.currentTimeMillis();
    public static final Start instance = new Start();
    public static AtomicInteger CompletedLoadingThreads = new AtomicInteger(0);

    public void run() throws InterruptedException {

        if (Boolean.parseBoolean(ServerProperties.getProperty("net.sf.odinms.world.admin")) || ServerConstants.Use_Localhost) {
            System.out.println("[Currently Admin Only]");
        }
        if (Boolean.parseBoolean(ServerProperties.getProperty("net.sf.odinms.world.logpackets"))) {
            System.out.println("Logging Packets");
        }  
        try {
            final PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement("UPDATE accounts SET loggedin = 0");
            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            throw new RuntimeException("[EXCEPTION] Please check if the SQL server is active.");
        }

        System.out.println("-----" + ServerProperties.getProperty("net.sf.odinms.login.serverName") + "----- ");
        System.out.println("[Currently Revision " + ServerProperties.getProperty("net.sf.odinms.world.Rev") + "] ");
        //System.out.println("[MESO " + ServerProperties.getProperty("net.sf.odinms.world.meso") + "] ");
        //System.out.println("[DROP " + ServerProperties.getProperty("net.sf.odinms.world.drop") + "] ");
        World.init();
        WorldTimer.getInstance().start();
        EtcTimer.getInstance().start();
        MapTimer.getInstance().start();
        CloneTimer.getInstance().start();
        EventTimer.getInstance().start();
        System.out.println("First 5 instances done.");
        BuffTimer.getInstance().start();
        PingTimer.getInstance().start();
        MapleGuildRanking.getInstance().load();
        MapleGuild.loadAll(); 
        MapleFamily.loadAll(); 
        System.out.println("First 10 instances done.");
        MapleLifeFactory.loadQuestCounts();
        MapleQuest.initQuests();
        MapleItemInformationProvider.getInstance().runEtc(); 
        MapleMonsterInformationProvider.getInstance().load(); 
        MapleItemInformationProvider.getInstance().runItems(); 
        System.out.println("First 15 instances done.");
        SkillFactory.load();
        LoginInformationProvider.getInstance();
        RandomRewards.load();
        MapleOxQuizFactory.getInstance();
        MapleCarnivalFactory.getInstance();
        System.out.println("First 20 instances done.");
	CharacterCardFactory.getInstance().initialize(); 
        MobSkillFactory.getInstance();
        SpeedRunner.loadSpeedRuns();
        MTSStorage.load();
        MapleInventoryIdentifier.getInstance();
        System.out.println("First 25 instances done.");
        MapleMapFactory.loadCustomLife();
        CashItemFactory.getInstance().initialize(); 
        MapleServerHandler.initiate();
        LoginServer.run_startup_configurations();
        ChannelServer.startChannel_Main();
        System.out.println("First 30 instances done.");
        CashShopServer.run_startup_configurations();
        Runtime.getRuntime().addShutdownHook(new Thread(new Shutdown()));
        World.registerRespawn();
        ShutdownServer.registerMBean();
        PlayerNPC.loadAll();
        System.out.println("First 35 instances done.");
        MapleMonsterInformationProvider.getInstance().addExtra();
        LoginServer.setOn(); 
        RankingWorker.run();
        System.out.println("All instances done.");
        System.out.println("You may now start the server.");
    }
/*
 * 
*/
 
    public static class Shutdown implements Runnable {

        @Override
        public void run() {
            ShutdownServer.getInstance().run();
            ShutdownServer.getInstance().run();
        }
    }

    public static void main(final String args[]) throws InterruptedException {
        instance.run();
    }
}
