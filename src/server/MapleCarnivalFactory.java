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

import client.MapleDisease;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import server.life.MobSkillFactory;
import provider.MapleDataProvider;
import provider.MapleDataProviderFactory;
import provider.MapleData;
import provider.MapleDataTool;
import server.life.MobSkill;

public class MapleCarnivalFactory {

    private final static MapleCarnivalFactory instance = new MapleCarnivalFactory();
    private final Map<Integer, MCSkill> skills = new HashMap<Integer, MCSkill>();
    private final Map<Integer, MCSkill> guardians = new HashMap<Integer, MCSkill>();
    private final MapleDataProvider dataRoot = MapleDataProviderFactory.getDataProvider(new File(System.getProperty("net.sf.odinms.wzpath") + "/Skill.wz"));

    public MapleCarnivalFactory() {
        //whoosh
	initialize();
    }

    public static final MapleCarnivalFactory getInstance() {
        return instance;
    }

    private void initialize() {
        if (skills.size() != 0) {
            return;
        }
        for (MapleData z : dataRoot.getData("MCSkill.img")) {
            skills.put(Integer.parseInt(z.getName()), new MCSkill(MapleDataTool.getInt("spendCP", z, 0), MapleDataTool.getInt("mobSkillID", z, 0), MapleDataTool.getInt("level", z, 0), MapleDataTool.getInt("target", z, 1) > 1));
        }
        for (MapleData z : dataRoot.getData("MCGuardian.img")) {
            guardians.put(Integer.parseInt(z.getName()), new MCSkill(MapleDataTool.getInt("spendCP", z, 0), MapleDataTool.getInt("mobSkillID", z, 0), MapleDataTool.getInt("level", z, 0), true));
        }
    }

    public MCSkill getSkill(final int id) {
        return skills.get(id);
    }

    public MCSkill getGuardian(final int id) {
        return guardians.get(id);
    }

    public static class MCSkill {

        public int cpLoss, skillid, level;
        public boolean targetsAll;

        public MCSkill(int _cpLoss, int _skillid, int _level, boolean _targetsAll) {
            cpLoss = _cpLoss;
            skillid = _skillid;
            level = _level;
            targetsAll = _targetsAll;
        }

        public MobSkill getSkill() {
            return MobSkillFactory.getMobSkill(skillid, 1); //level?
        }

        public MapleDisease getDisease() {
            if (skillid <= 0) {
                return MapleDisease.getRandom();
            }
            return MapleDisease.getBySkill(skillid);
        }
    }
}
