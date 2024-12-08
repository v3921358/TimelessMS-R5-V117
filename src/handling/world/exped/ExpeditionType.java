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

public enum ExpeditionType {
    Normal_Balrog(15, 2001, 50, 200),
    Horntail(30, 2003, 80, 200),
    Zakum(30, 2002, 50, 200),
    Chaos_Zakum(30, 2005, 100, 200),
    ChaosHT(30, 2006, 110, 200),
    Pink_Bean(30, 2004, 140, 200),
    CWKPQ(30, 2007, 90, 200),
    Von_Leon(30, 2008, 120, 200),
    Cygnus(18, 2009, 170, 200);

    public int maxMembers, maxParty, exped, minLevel, maxLevel;
    private ExpeditionType(int maxMembers, int exped, int minLevel, int maxLevel) {
	this.maxMembers = maxMembers;
	this.exped = exped;
	this.maxParty = (maxMembers / 2) + (maxMembers % 2 > 0 ? 1 : 0);
	this.minLevel = minLevel;
	this.maxLevel = maxLevel;
    }

    public static ExpeditionType getById(int id) {
	for (ExpeditionType pst : ExpeditionType.values()) {
	    if (pst.exped == id) {
		return pst;
	    }
	}
	return null;
    }
}
