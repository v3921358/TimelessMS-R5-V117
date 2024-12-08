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

import handling.world.World;
import java.util.concurrent.ScheduledFuture;
import server.Timer.EtcTimer;

public class PartySearch {
    private String name; //max 40
    private int partyId;
    private PartySearchType pst;
    private ScheduledFuture<?> removal;

    public PartySearch(String name, int partyId, PartySearchType pst) {
	this.name = name;
	this.partyId = partyId;
	this.pst = pst;
	scheduleRemoval();
    }

    public PartySearchType getType() {
	return pst;
    }

    public int getId() {
	return partyId;
    }

    public String getName() {
	return name;
    }

    public void scheduleRemoval() {
	cancelRemoval();
	removal = EtcTimer.getInstance().schedule(new Runnable() {
	    public void run() {
		World.Party.removeSearch(PartySearch.this, "The Party Listing was removed because it has expired.");
	    }
	}, pst.timeLimit * 60000);
    }

    public void cancelRemoval() {
	if (removal != null) {
	    removal.cancel(false);
	    removal = null;
	}
    }
}
