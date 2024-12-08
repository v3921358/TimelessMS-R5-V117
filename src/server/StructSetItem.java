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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class StructSetItem {

    public byte completeCount, setItemID;
    public Map<Integer, SetItem> items = new LinkedHashMap<Integer, SetItem>();
    public List<Integer> itemIDs = new ArrayList<Integer>();

    public static class SetItem {

        public int incPDD, incMDD, incSTR, incDEX, incINT, incLUK, incACC, incPAD, incMAD, incSpeed, incMHP, incMMP, incMHPr, incMMPr, incAllStat,
		option1, option2, option1Level, option2Level;
    }

    public Map<Integer, SetItem> getItems() {
        return new LinkedHashMap<Integer, SetItem>(items);
    }
}
