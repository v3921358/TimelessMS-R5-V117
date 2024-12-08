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
package handling.world.guild;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import handling.world.World;
import handling.world.guild.MapleBBSThread.MapleBBSReply;
import java.util.Map;
import java.util.logging.Logger;

public class GuildLoad {

    public static final int NumSavingThreads = 6;
    private static Map<Integer, Map<Integer, MapleBBSReply>> replies = null;
    private static final TimingThread[] Threads = new TimingThread[NumSavingThreads];

    static {
        for (int i = 0; i < Threads.length; i++) {
            Threads[i] = new TimingThread(new GuildLoadRunnable());
        }
    }
    private static final AtomicInteger Distribute = new AtomicInteger(0);

    public static void QueueGuildForLoad(int hm, Map<Integer, Map<Integer, MapleBBSReply>> replie) {
        int Current = Distribute.getAndIncrement() % NumSavingThreads;
        Threads[Current].getRunnable().Queue(Integer.valueOf(hm));
	if (replies == null) {
	    replies = replie;
	}
    }

    public static void Execute(Object ToNotify) {
        for (int i = 0; i < Threads.length; i++) {
            Threads[i].getRunnable().SetToNotify(ToNotify);
        }
        for (int i = 0; i < Threads.length; i++) {
            Threads[i].start();
        }
    }

    private static class GuildLoadRunnable implements Runnable {
        private Object ToNotify;
        private ArrayBlockingQueue<Integer> Queue = new ArrayBlockingQueue<Integer>(1000); //1000 Start Capacity (Should be plenty)

        public void run() {
            try {
                while (!Queue.isEmpty()) {
                    World.Guild.addLoadedGuild(new MapleGuild(Queue.take(), replies));
                }
                synchronized (ToNotify) {
                    ToNotify.notify();
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(GuildLoad.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        private void Queue(Integer hm) {
            Queue.add(hm);
        }

        private void SetToNotify(Object o) {
            if (ToNotify == null) {
                ToNotify = o;
            }
        }
    }

    private static class TimingThread extends Thread {

        private final GuildLoadRunnable ext;

        public TimingThread(GuildLoadRunnable r) {
            super(r);
            ext = r;
        }

        public GuildLoadRunnable getRunnable() {
            return ext;
        }
    }
}
