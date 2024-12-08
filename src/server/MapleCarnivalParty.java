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

import client.MapleCharacter;
import handling.channel.ChannelServer;
import java.util.LinkedList;
import java.util.List;
import java.lang.ref.WeakReference;
import server.maps.MapleMap;
import tools.packet.CField;

/**
  * @author Burblish
 */
public class MapleCarnivalParty {

    private List<Integer> members = new LinkedList<Integer>();
    private WeakReference<MapleCharacter> leader;
    private byte team;
    private int channel;
    private short availableCP = 0, totalCP = 0;
    private boolean winner = false;

    public MapleCarnivalParty(final MapleCharacter owner, final List<MapleCharacter> members1, final byte team1) {
        leader = new WeakReference<MapleCharacter>(owner);
        for (MapleCharacter mem : members1) {
            members.add(mem.getId());
            mem.setCarnivalParty(this);
        }
        team = team1;
        channel = owner.getClient().getChannel();
    }

    public final MapleCharacter getLeader() {
        return leader.get();
    }

    public void addCP(MapleCharacter player, int ammount) {
        totalCP += ammount;
        availableCP += ammount;
        player.addCP(ammount);
    }

    public int getTotalCP() {
        return totalCP;
    }

    public int getAvailableCP() {
        return availableCP;
    }

    public void useCP(MapleCharacter player, int ammount) {
        availableCP -= ammount;
        player.useCP(ammount);
    }

    public List<Integer> getMembers() {
        return members;
    }

    public int getTeam() {
        return team;
    }

    public void warp(final MapleMap map, final String portalname) {
        for (int chr : members) {
            final MapleCharacter c = ChannelServer.getInstance(channel).getPlayerStorage().getCharacterById(chr);
            if (c != null) {
                c.changeMap(map, map.getPortal(portalname));
            }
        }
    }

    public void warp(final MapleMap map, final int portalid) {
        for (int chr : members) {
            final MapleCharacter c = ChannelServer.getInstance(channel).getPlayerStorage().getCharacterById(chr);
            if (c != null) {
                c.changeMap(map, map.getPortal(portalid));
            }
        }
    }

    public boolean allInMap(MapleMap map) {
        for (int chr : members) {
            if (map.getCharacterById(chr) == null) {
                return false;
            }
        }
        return true;
    }

    public void removeMember(MapleCharacter chr) {
        for (int i = 0; i < members.size(); i++) {
            if (members.get(i) == chr.getId()) {
                members.remove(i);
                chr.setCarnivalParty(null);
            }
        }

    }

    public boolean isWinner() {
        return winner;
    }

    public void setWinner(boolean status) {
        winner = status;
    }

    public void displayMatchResult() {
        final String effect = winner ? "quest/carnival/win" : "quest/carnival/lose";
        final String sound = winner ? "MobCarnival/Win" : "MobCarnival/Lose";
        boolean done = false;
        for (int chr : members) {
            final MapleCharacter c = ChannelServer.getInstance(channel).getPlayerStorage().getCharacterById(chr);
            if (c != null) {
                c.getClient().getSession().write(CField.showEffect(effect));
                c.getClient().getSession().write(CField.playSound(sound));
                if (!done) {
                    done = true;
                    c.getMap().killAllMonsters(true);
                    c.getMap().setSpawns(false); //resetFully will take care of this
                }
            }
        }

    }
}
