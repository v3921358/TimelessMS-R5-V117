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

import database.DatabaseConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Map;
import handling.world.exped.ExpeditionType;
import tools.Pair;
import tools.Triple;
import tools.StringUtil;

public class SpeedRunner {
    private static final Map<ExpeditionType, Triple<String, Map<Integer, String>, Long>> speedRunData = new EnumMap<ExpeditionType, Triple<String, Map<Integer, String>, Long>>(ExpeditionType.class);

    public final static Triple<String, Map<Integer, String>, Long> getSpeedRunData(ExpeditionType type) {
        return speedRunData.get(type);
    }

    public final static void addSpeedRunData(ExpeditionType type, Pair<StringBuilder, Map<Integer, String>> mib, long tmp) {
        speedRunData.put(type, new Triple<String, Map<Integer, String>, Long>(mib.getLeft().toString(), mib.getRight(), tmp));
    }

    public final static void removeSpeedRunData(ExpeditionType type) {
        speedRunData.remove(type);
    }

    public final static void loadSpeedRuns() {
        if (speedRunData.size() > 0) {
            return;
        }
        for (ExpeditionType type : ExpeditionType.values()) {
            loadSpeedRunData(type);
        }
    }

    public final static String getPreamble(ExpeditionType type) {
	return "#rThese are the speedrun times for " + StringUtil.makeEnumHumanReadable(type.name()).toUpperCase() + ".#k\r\n\r\n";
    }

    public final static void loadSpeedRunData(ExpeditionType type) {
	try {
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM speedruns WHERE type = ? ORDER BY time LIMIT 25"); //or should we do less
            ps.setString(1, type.name());
            StringBuilder ret = new StringBuilder(getPreamble(type));
            Map<Integer, String> rett = new LinkedHashMap<Integer, String>();
            ResultSet rs = ps.executeQuery();
            int rank = 1;
	    Set<String> leaders = new HashSet<String>();
            boolean cont = rs.first();
            boolean changed = cont;
	    long tmp = 0;
            while (cont) {
	        if (!leaders.contains(rs.getString("leader"))) {
                    addSpeedRunData(ret, rett, rs.getString("members"), rs.getString("leader"), rank, rs.getString("timestring"));
                    rank++;
		    leaders.add(rs.getString("leader"));
		    tmp = rs.getLong("time");
	        }
                cont = rs.next() && rank < 25;
            }
            rs.close();
            ps.close();
            if (changed) {
                speedRunData.put(type, new Triple<String, Map<Integer, String>, Long>(ret.toString(), rett, tmp));
            }
	} catch (SQLException e) {
	    e.printStackTrace();
	}
    }

    public final static Pair<StringBuilder, Map<Integer, String>> addSpeedRunData(StringBuilder ret, Map<Integer, String> rett, String members, String leader, int rank, String timestring) {
        StringBuilder rettt = new StringBuilder();

        String[] membrz = members.split(",");
        rettt.append("#bThese are the squad members of " + leader + "'s squad at rank " + rank + ".#k\r\n\r\n");
        for (int i = 0; i < membrz.length; i++) {
            rettt.append("#r#e");
            rettt.append(i + 1);
            rettt.append(".#n ");
            rettt.append(membrz[i]);
            rettt.append("#k\r\n");
        }
        rett.put(rank, rettt.toString());
        ret.append("#b#L").append(rank).append("#Rank #e").append(rank).append("#n#k : ").append(leader).append(", in ").append(timestring);
        if (membrz.length > 1) {
            ret.append("#l");
        }
        ret.append("\r\n");
        return new Pair<StringBuilder, Map<Integer, String>>(ret, rett);
    }
}
