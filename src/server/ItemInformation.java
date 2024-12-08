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

import client.inventory.Equip;
import java.util.List;
import java.util.Map;
import tools.Triple;

public class ItemInformation {
    public List<Integer> scrollReqs = null, questItems = null, incSkill = null;
    public short slotMax, itemMakeLevel;
    public Equip eq = null;
    public Map<String, Integer> equipStats;
    public double price = 0.0;
    public int itemId, wholePrice, monsterBook, stateChange, meso, questId, totalprob, replaceItem, mob, cardSet, create, flag;
    public String name, desc, msg, replaceMsg, afterImage;
    public byte karmaEnabled;
    public List<StructRewardItem> rewardItems = null;
    public List<Triple<String, String, String>> equipAdditions = null;
    public Map<Integer, Map<String, Integer>> equipIncs = null;
}