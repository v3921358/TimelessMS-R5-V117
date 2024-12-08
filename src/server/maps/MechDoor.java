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
package server.maps;

import client.MapleCharacter;
import client.MapleClient;
import java.awt.Point;
import tools.packet.CField;

public class MechDoor extends MapleMapObject {

    private int owner,partyid,id;

    public MechDoor(MapleCharacter owner, Point pos, int id) {
        super();
        this.owner = owner.getId();
        this.partyid = owner.getParty() == null ? 0 : owner.getParty().getId();
        setPosition(pos);
        this.id = id;
    }

    @Override
    public void sendSpawnData(MapleClient client) {
        client.getSession().write(CField.spawnMechDoor(this, false));
    }

    @Override
    public void sendDestroyData(MapleClient client) {
        client.getSession().write(CField.removeMechDoor(this, false));
    }

    public int getOwnerId() {
        return this.owner;
    }

    public int getPartyId() {
        return this.partyid;
    }

    public int getId() {
        return this.id;
    }

    @Override
    public MapleMapObjectType getType() {
        return MapleMapObjectType.DOOR;
    }
}
