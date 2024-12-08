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
package handling.world.exped;

import handling.world.MapleParty;
import handling.world.World;
import java.util.ArrayList;
import java.util.List;

public class MapleExpedition {
    private List<Integer> parties;
    private ExpeditionType et;
    private int leaderId, id;

    public MapleExpedition(ExpeditionType ett, int leaderId, int id) {
	this.et = ett;
	this.id = id;
	this.leaderId = leaderId;
	this.parties = new ArrayList<Integer>(ett.maxParty);
    }

    public ExpeditionType getType() {
	return et;
    }

    public int getLeader() {
	return leaderId;
    }

    public List<Integer> getParties() {
	return parties;
    }

    public int getId() {
	return id;
    }

    public int getAllMembers() {
	int ret = 0;
	for (int i = 0; i < parties.size(); i++) {
	    MapleParty pp = World.Party.getParty(parties.get(i));
	    if (pp == null) {
		parties.remove(i);
	    } else {
		ret += pp.getMembers().size();
	    }
	}
	return ret;
    } 

    public int getFreeParty() {
	for (int i = 0; i < parties.size(); i++) {
	    MapleParty pp = World.Party.getParty(parties.get(i));
	    if (pp == null) {
		parties.remove(i);
	    } else if (pp.getMembers().size() < 6) {
		return pp.getId();
	    }
	}
	if (parties.size() < et.maxParty) {
	    return 0;
	}
	return -1;
    }

    public int getIndex(final int partyId) {
	for (int i = 0; i < parties.size(); i++) {
	    if (parties.get(i) == partyId) {
		return i;
	    }
	}
	return -1;
    }

    public void setLeader(int newLead) {
	this.leaderId = newLead;
    }
}