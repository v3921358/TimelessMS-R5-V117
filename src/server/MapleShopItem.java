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

public class MapleShopItem {

    private short buyable;
    private int itemId;
    private int price;
    private int reqItem;
    private int reqItemQ;
    private byte rank;
	
	public MapleShopItem(int itemId, int price, short buyable) {
		this.buyable = buyable;
		this.itemId = itemId;
		this.price = price;
		this.reqItem = 0;
		this.reqItemQ = 0;
		this.rank = (byte)0;
	}

    public MapleShopItem(short buyable, int itemId, int price, int reqItem, int reqItemQ, byte rank) {
        this.buyable = buyable;
        this.itemId = itemId;
        this.price = price;
        this.reqItem = reqItem;
        this.reqItemQ = reqItemQ;
	this.rank = rank;
    }

    public short getBuyable() {
        return buyable;
    }

    public int getItemId() {
        return itemId;
    }

    public int getPrice() {
        return price;
    }

    public int getReqItem() {
        return reqItem;
    }

    public int getReqItemQ() {
        return reqItemQ;
    }

    public byte getRank() {
        return rank;
    }
}
