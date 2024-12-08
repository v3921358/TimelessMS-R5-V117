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
package server.movement;

import java.awt.Point;

import tools.data.MaplePacketLittleEndianWriter;

public class AbsoluteLifeMovement extends AbstractLifeMovement {

    private Point pixelsPerSecond, offset;
    private short unk, fh;

    public AbsoluteLifeMovement(int type, Point position, int duration, int newstate) {
        super(type, position, duration, newstate);
    }

    public void setPixelsPerSecond(Point wobble) {
        this.pixelsPerSecond = wobble;
    }

    public void setOffset(Point wobble) {
        this.offset = wobble;
    }

    public void setFh(short fh) {
        this.fh = fh;
    }

    public void setUnk(short unk) {
        this.unk = unk;
    }

    public short getUnk() {
        return unk;
    }

    public void defaulted() {
        unk = 0;
        fh = 0;
        pixelsPerSecond = new Point(0, 0);
        offset = new Point(0, 0);
    }

    @Override
    public void serialize(MaplePacketLittleEndianWriter lew) {
        lew.write(getType());
        lew.writePos(getPosition());
        lew.writePos(pixelsPerSecond);
        lew.writeShort(unk);
        if (getType() == 14) {
            lew.writeShort(fh);
        }
        if (getType() != 44) {
            lew.writePos(offset);
        }
        lew.write(getNewstate());
        lew.writeShort(getDuration());
    }
}
