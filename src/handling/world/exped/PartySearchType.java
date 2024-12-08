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

public enum PartySearchType {
    Kerning(20, 200, 1000, false),
    Ludi(30, 200, 1001, false),
    Orbis(50, 200, 1002, false),
    Pirate(60, 200, 1003, false),
    Magatia(70, 200, 1004, false),
    ElinForest(40, 200, 1005, false),
    Pyramid(40, 200, 1008, false),
    Dragonica(100, 200, 1009, false), //what the fk
    Hoblin(80, 200, 1011, false),
    Henesys(10, 200, 1012, false),
    Dojo(25, 200, 1013, false),

    Balrog_Normal(50, 200, 2001, true),
    Zakum(50, 200, 2002, true),
    Horntail(80, 200, 2003, true),
    PinkBean(140, 200, 2004, true),
    ChaosZakum(100, 200, 2005, true),
    ChaosHT(110, 200, 2006, true),
    CWKPQ(90, 200, 2007, true),
    VonLeon(120, 200, 2008, true);

    public int id, minLevel, maxLevel, timeLimit;
    public boolean exped;
    private PartySearchType(int minLevel, int maxLevel, int value, boolean exped) {
	this.id = value;
	this.minLevel = minLevel;
	this.maxLevel = maxLevel;
	this.exped = exped;
	this.timeLimit = exped ? 20 : 5;
    }

    public static PartySearchType getById(int id) {
	for (PartySearchType pst : PartySearchType.values()) {
	    if (pst.id == id) {
		return pst;
	    }
	}
	return null;
    }
}
