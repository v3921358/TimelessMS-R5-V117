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
package handling.login;

import client.MapleCharacter;
import constants.GameConstants;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

import handling.MapleServerHandler;
import handling.cashshop.CashShopServer;
import handling.cashshop.handler.CashShopOperation;
import handling.channel.ChannelServer;
import handling.mina.MapleCodecFactory;
import handling.world.CharacterTransfer;
import java.util.HashSet;
import org.apache.mina.common.ByteBuffer;
import org.apache.mina.common.SimpleByteBufferAllocator;
import org.apache.mina.common.IoAcceptor;

import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.SocketAcceptorConfig;
import org.apache.mina.transport.socket.nio.SocketAcceptor;
import server.ServerProperties;
import tools.Pair;
import tools.Triple;

public class LoginServer {

    public static final int PORT = 8484;
    private static InetSocketAddress InetSocketadd;
    private static IoAcceptor acceptor;
    private static Map<Integer, Integer> load = new HashMap<Integer, Integer>();
    private static String serverName, eventMessage;
    private static byte flag;
    private static int maxCharacters, userLimit, usersOn = 0;
    private static boolean finishedShutdown = true, adminOnly = false, LogIP = false;
    
    private static HashMap<Integer, Triple<String, String, Integer>> loginAuth = new HashMap<Integer, Triple<String, String, Integer>>();
    private static HashSet<String> loginIPAuth = new HashSet<String>();
     private static int serverID;  

     public static void putLoginAuth(int chrid, String ip, String tempIP, int channel) {
        loginAuth.put(chrid, new Triple<String, String, Integer>(ip, tempIP, channel));
        loginIPAuth.add(ip);
    }

    public static Triple<String, String, Integer> getLoginAuth(int chrid) {
        return loginAuth.remove(chrid);
    }

    public static boolean containsIPAuth(String ip) {
        return loginIPAuth.contains(ip);
    }

    public static void removeIPAuth(String ip) {
        loginIPAuth.remove(ip);
    }

    public static void addIPAuth(String ip) {
        loginIPAuth.add(ip);
    }

    public static final void addChannel(final int channel) {
        load.put(channel, 0);
    }

    public static final void removeChannel(final int channel) {
        load.remove(channel);
    }

    public static final void run_startup_configurations() {
        userLimit = Integer.parseInt(ServerProperties.getProperty("net.sf.odinms.login.userlimit"));
         serverID = Integer.parseInt(ServerProperties.getProperty("net.sf.odinms.login.serverID"));  
        serverName = ServerProperties.getProperty("net.sf.odinms.login.serverName");
        eventMessage = ServerProperties.getProperty("net.sf.odinms.login.eventMessage");
        flag = Byte.parseByte(ServerProperties.getProperty("net.sf.odinms.login.flag"));
        adminOnly = Boolean.parseBoolean(ServerProperties.getProperty("net.sf.odinms.world.admin", "false"));
        LogIP = Boolean.parseBoolean(ServerProperties.getProperty("net.sf.odinms.world.LogIP", "false"));
        maxCharacters = Integer.parseInt(ServerProperties.getProperty("net.sf.odinms.login.maxCharacters"));

        ByteBuffer.setUseDirectBuffers(false);
        ByteBuffer.setAllocator(new SimpleByteBufferAllocator());

        acceptor = new SocketAcceptor();
        final SocketAcceptorConfig cfg = new SocketAcceptorConfig();
        cfg.getSessionConfig().setTcpNoDelay(true);
        cfg.setDisconnectOnUnbind(true);
        cfg.getFilterChain().addLast("codec", new ProtocolCodecFilter(new MapleCodecFactory()));

        try {
            InetSocketadd = new InetSocketAddress(PORT);
            acceptor.bind(InetSocketadd, new MapleServerHandler(), cfg);
            //System.out.println("Port " + PORT + ".");
        } catch (IOException e) {
            System.err.println("Binding to port " + PORT + " failed" + e);
        }
    }

    public static final void shutdown() {
        if (finishedShutdown) {
            return;
        }
        System.out.println("Shutting down login...");
        acceptor.unbindAll();
        finishedShutdown = true; //nothing. lol
    }
    public static final int getServerID() {
        return serverID;
    }

    public static final void setServerID(final int sID) {
        serverID = sID;
    }  

    public static final String getServerName() {
        return serverName;
    }

    public static final String getTrueServerName() {
        return serverName.substring(0, serverName.length() - (GameConstants.GMS ? 2 : 3));
    }

    public static final String getEventMessage() {
        return eventMessage;
    }

    public static final byte getFlag() {
        return flag;
    }

    public static final int getMaxCharacters() {
        return maxCharacters;
    }

    public static final Map<Integer, Integer> getLoad() {
        return load;
    }

    public static void setLoad(final Map<Integer, Integer> load_, final int usersOn_) {
        load = load_;
        usersOn = usersOn_;
    }

    public static final void setEventMessage(final String newMessage) {
        eventMessage = newMessage;
    }

    public static final void setFlag(final byte newflag) {
        flag = newflag;
    }

    public static final int getUserLimit() {
        return userLimit;
    }

    public static final int getUsersOn() {
        return usersOn;
    }

    public static final void setUserLimit(final int newLimit) {
        userLimit = newLimit;
    }

    public static final int getNumberOfSessions() {
        return acceptor.getManagedSessions(InetSocketadd).size();
    }

    public static final boolean isAdminOnly() {
        return adminOnly;
    }
    public static final boolean isLogIP() {
        return LogIP;
    }
    public static final boolean isShutdown() {
        return finishedShutdown;
    }

    public static final void setOn() {
        finishedShutdown = false;
    }
}
