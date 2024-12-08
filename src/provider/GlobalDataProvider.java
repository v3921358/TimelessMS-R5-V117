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
    package provider;
     
    import java.io.File;
     
    public class GlobalDataProvider {
        private static MapleDataProvider stringData, skillData, itemData, mapData, mobData;
        private static MapleData mapStringData;
     
        public static MapleDataProvider getStringDataProvider() {
            if (stringData == null) {
                stringData = MapleDataProviderFactory.getDataProvider(new File(System.getProperty("wzpath") + "/String.wz"));
            }
            return stringData;
        }
     
        public static MapleDataProvider getSkillDataProvider() {
            if (skillData == null) {
                skillData = MapleDataProviderFactory.getDataProvider(new File(System.getProperty("wzpath") + "/Skill.wz"));
            }
            return skillData;
        }
     
        public static MapleDataProvider getItemDataProvider() {
            if (itemData == null) {
                itemData = MapleDataProviderFactory.getDataProvider(new File(System.getProperty("wzpath") + "/Item.wz"));
            }
            return itemData;
        }
     
        public static MapleDataProvider getMapDataProvider() {
            if (mapData == null) {
                mapData = MapleDataProviderFactory.getDataProvider(new File(System.getProperty("wzpath") + "/Map.wz"));
            }
            return mapData;
        }
     
        public static MapleDataProvider getMobDataProvider() {
            if (mobData == null) {
                mobData = MapleDataProviderFactory.getDataProvider(new File(System.getProperty("wzpath") + "/Mob.wz"));
            }
            return mobData;
        }
     
        public static MapleData getMapStringData() {
            if (mapStringData == null) {
                mapStringData = getStringDataProvider().getData("Map.img");
            }
            return mapStringData;
        }
    }
