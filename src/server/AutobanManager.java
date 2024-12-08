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

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import client.MapleClient;
import handling.world.World;
import java.util.concurrent.locks.ReentrantLock;
import tools.packet.CWvsContext;

public class AutobanManager implements Runnable {

    private static class ExpirationEntry implements Comparable<ExpirationEntry> {

        public long time;
        public int acc;
        public int points;

        public ExpirationEntry(long time, int acc, int points) {
            this.time = time;
            this.acc = acc;
            this.points = points;
        }

        public int compareTo(AutobanManager.ExpirationEntry o) {
            return (int) (time - o.time);
        }

        @Override
        public boolean equals(Object oth) {
            if (!(oth instanceof AutobanManager.ExpirationEntry)) {
                return false;
            }
            final AutobanManager.ExpirationEntry ee = (AutobanManager.ExpirationEntry) oth;
            return (time == ee.time && points == ee.points && acc == ee.acc);
        }
    }
    private Map<Integer, Integer> points = new HashMap<Integer, Integer>();
    private Map<Integer, List<String>> reasons = new HashMap<Integer, List<String>>();
    private Set<ExpirationEntry> expirations = new TreeSet<ExpirationEntry>();
    private static final int AUTOBAN_POINTS = 5000; // change this if pop
    private static AutobanManager instance = new AutobanManager();
    private final ReentrantLock lock = new ReentrantLock(true);

    public static final AutobanManager getInstance() {
        return instance;
    }

    public final void autoban(final MapleClient c, final String reason) {
	/*if (c.getPlayer() == null) {
	    return;
	}
        if (c.getPlayer().isGM() || c.getPlayer().isClone()) {
            c.getPlayer().dropMessage(5, "[WARNING] A/b triggled : " + reason);
            return;
        }
        addPoints(c, AUTOBAN_POINTS, 0, reason);*/
    }

    public final void addPoints(final MapleClient c, final int points, final long expiration, final String reason) {
        /*lock.lock();
        try {
            List<String> reasonList;
            final int acc = c.getPlayer().getAccountID();

            if (this.points.containsKey(acc)) {
                final int SavedPoints = this.points.get(acc);
                if (SavedPoints >= AUTOBAN_POINTS) { // Already auto ban'd.
                    return;
                }
                this.points.put(acc, SavedPoints + points); // Add
                reasonList = this.reasons.get(acc);
                reasonList.add(reason);
            } else {
                this.points.put(acc, points);
                reasonList = new LinkedList<String>();
                reasonList.add(reason);
                this.reasons.put(acc, reasonList);
            }

            if (this.points.get(acc) >= AUTOBAN_POINTS) { // See if it's sufficient to auto ban
                if (c.getPlayer().isGM() || c.getPlayer().isClone()) {
                    c.getPlayer().dropMessage(5, "[WARNING] A/b triggled : " + reason);
                    return;
                }
                final StringBuilder sb = new StringBuilder("a/b ");
                sb.append(c.getPlayer().getName());
                sb.append(" (IP ");
                sb.append(c.getSession().getRemoteAddress().toString());
                sb.append("): ");
                for (final String s : reasons.get(acc)) {
                    sb.append(s);
                    sb.append(", ");
                }
                World.Broadcast.broadcastMessage(CWvsContext.serverNotice(0, "[Autoban] " + c.getPlayer().getName() + " banned by the system (Last reason: " + reason + ")"));
//		Calendar cal = Calendar.getInstance();
//		cal.add(Calendar.DATE, 60);
//		c.getPlayer().tempban(sb.toString(), cal, 1, false);
                c.getPlayer().ban(sb.toString(), false, true, false);
                c.disconnect(true, false);
            } else {
                if (expiration > 0) {
                    expirations.add(new ExpirationEntry(System.currentTimeMillis() + expiration, acc, points));
                }
            }
        } finally {
            lock.unlock();
        }*/
    }

    public final void run() {
        final long now = System.currentTimeMillis();
        for (final ExpirationEntry e : expirations) {
            if (e.time <= now) {
                this.points.put(e.acc, this.points.get(e.acc) - e.points);
            } else {
                return;
            }
        }
    }
}
