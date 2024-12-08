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
package dropspider;

import client.inventory.MapleInventoryType;
import server.MapleItemInformationProvider;

/**
 *
 * @author Simon
 */
public class DropEntry {
    private int version;
    private int item_id;
    private int monster_id;
    private int chance;
    private int mindrop;
    private int maxdrop;

    public DropEntry(int item_id, int monster_id, int version) {
        this.item_id = item_id;
        this.monster_id = monster_id;
        mindrop = 1;
        maxdrop = 1;
        chance = calculateChance(item_id);
        this.version = version;
    }

    private int calculateChance(int item_id) {
        MapleInventoryType mit = MapleItemInformationProvider.getInventoryType(item_id);
        boolean boss = DataTool.isBoss(monster_id);
        int number = (item_id / 1000) % 1000;
        switch (mit) {
            case EQUIP:
                if (boss) {
                    return 300000;
                }
                return 7000;
            case USE:
                if (boss) {
                    mindrop = 1;
                    maxdrop = 4;
                }
                switch (number) {
                    case 0: // normal potions
                        mindrop = 1;
                        if (version > 98) {
                            maxdrop = 5;
                        }
                        return 100000;
                    case 1: // watermelons, pills, speed potions, etc
                    case 2: // same thing
                        return 50000;
                    case 3: // advanced potions from crafting (should not drop)
                    case 4: // same thing
                    case 11: // poison mushroom
                    case 28: // cool items
                    case 30: // return scrolls
                    case 46: // gallant scrolls
                        return 0;
                    case 10: // strange potions like apples, eggs
                    case 12: // drakes blood, sap of ancient tree (rare use)
                    case 20: // salad, fried chicken, dews
                    case 22: // air bubbles and stuff. ALSO nependeath honey but oh well
                    case 50: // antidotes and stuff
                    case 290: // mastery books
                        return 10000;
                    case 40:
                    case 41:
                    case 43:
                    case 44:
                    case 48: // pet scrolls
                    case 100: // summon bags
                    case 101: // summon bags
                    case 102: // summon bags
                    case 109: // summon bags
                    case 120: // pet food
                    case 211: // cliffs special potion
                    case 240: // rings
                    case 270: // pheromone, additional weird stuff
                    case 310: // teleport rock
                    case 320: // weird drops
                    case 390: // weird
                    case 430: // quiz things? compass?
                    case 440: // jukebox
                    case 460: // magnifying glass
                    case 470: // golden hammer
                    case 490: // crystanol
                    case 500: // sp reset
                        return 0;
                    case 47: // tablets from dragon rider
                        return 250000;
                    case 49: // clean slats, potential scroll, ees
                    case 70: // throwing stars
                    case 210: // rare monster piece drops
                    case 330: // bullets
                        return 1000;
                    case 60: // bow arrows
                    case 61: // crossbow arrows
                        mindrop = 10;
                        maxdrop = 50;
                        return 20000;
                    case 213: // boss transfrom
                        return 300000;
                    case 280: // skill books
                        return 200000;
                    case 381: // monster book things
                    case 382:
                    case 383:
                    case 384:
                    case 385:
                    case 386:
                    case 387:
                    case 388:
                        return 20000;
                    case 510: // recipes
                    case 511:
                    case 512:
                        return 10000;
                    default:
                        return 0;

                }
            case ETC:
                switch (number) {
                    case 0: // monster pieces
                        return 400000;
                    case 4: // crystal ores
                    case 130: // simulators
                    case 131: // manuals
                        return 10000;
                    case 30: // game pieces
                        return 50000;
                    case 32: // misc items
                        return 250000;
                    default:
                        return 10000;
                }
            default:
                return 10000;
        }
    }

    public String getQuerySegment() {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        sb.append(monster_id);
        sb.append(", ");
        sb.append(item_id);
        sb.append(", ");
        sb.append(mindrop);//min
        sb.append(", ");
        sb.append(maxdrop);//max
        sb.append(", ");
        sb.append(0);//quest
        sb.append(", ");
        sb.append(chance);
        sb.append(")");
        return sb.toString();
    }
}