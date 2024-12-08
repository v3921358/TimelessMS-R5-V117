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
package handling.world.family;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import handling.world.World;
import java.util.logging.Logger;

public class FamilyLoad {

    public static final int NumSavingThreads = 8;
    private static final TimingThread[] Threads = new TimingThread[NumSavingThreads];

    static {
        for (int i = 0; i < Threads.length; i++) {
            Threads[i] = new TimingThread(new FamilyLoadRunnable());
        }
    }
    private static final AtomicInteger Distribute = new AtomicInteger(0);

    public static void QueueFamilyForLoad(int hm) {
        int Current = Distribute.getAndIncrement() % NumSavingThreads;
        Threads[Current].getRunnable().Queue(Integer.valueOf(hm));
    }

    public static void Execute(Object ToNotify) {
        for (int i = 0; i < Threads.length; i++) {
            Threads[i].getRunnable().SetToNotify(ToNotify);
        }
        for (int i = 0; i < Threads.length; i++) {
            Threads[i].start();
        }
    }

    private static class FamilyLoadRunnable implements Runnable {
        private Object ToNotify;
        private ArrayBlockingQueue<Integer> Queue = new ArrayBlockingQueue<Integer>(1000);

        public void run() {
            try {
                while (!Queue.isEmpty()) {
                    World.Family.addLoadedFamily(new MapleFamily(Queue.take()));
                }
                synchronized (ToNotify) {
                    ToNotify.notify();
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(FamilyLoad.class.getName()).log(Level.SEVERE, null, ex);
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

        private final FamilyLoadRunnable ext;

        public TimingThread(FamilyLoadRunnable r) {
            super(r);
            ext = r;
        }

        public FamilyLoadRunnable getRunnable() {
            return ext;
        }
    }
}
