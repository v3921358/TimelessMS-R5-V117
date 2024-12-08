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
package server.shops;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Burblish
 */
public class HiredMerchantSave {

    public static final int NumSavingThreads = 5;
    private static final TimingThread[] Threads = new TimingThread[NumSavingThreads];

    static {
        for (int i = 0; i < Threads.length; i++) {
            Threads[i] = new TimingThread(new HiredMerchantSaveRunnable());
        }
    }
    private static final AtomicInteger Distribute = new AtomicInteger(0);

    public static void QueueShopForSave(HiredMerchant hm) {
        int Current = Distribute.getAndIncrement() % NumSavingThreads;
        Threads[Current].getRunnable().Queue(hm);
    }

    public static void Execute(Object ToNotify) {
        for (int i = 0; i < Threads.length; i++) {
            Threads[i].getRunnable().SetToNotify(ToNotify);
        }
        for (int i = 0; i < Threads.length; i++) {
            Threads[i].start();
        }
    }

    private static class HiredMerchantSaveRunnable implements Runnable {

        private static AtomicInteger RunningThreadID = new AtomicInteger(0);
        private int ThreadID = RunningThreadID.incrementAndGet();
        private long TimeTaken = 0;
        private int ShopsSaved = 0;
        private Object ToNotify;
        private ArrayBlockingQueue<HiredMerchant> Queue = new ArrayBlockingQueue<HiredMerchant>(500); //500 Start Capacity (Should be plenty)

        public void run() {
            try {
                while (!Queue.isEmpty()) {
                    HiredMerchant next = Queue.take();
                    long Start = System.currentTimeMillis();
                if (next.getMCOwner() != null && next.getMCOwner().getPlayerShop() == next) {
            next.getMCOwner().setPlayerShop(null);
          }
          next.closeShop(true, false);  
                    TimeTaken += (System.currentTimeMillis() - Start);
                    ShopsSaved++;
                }
                System.out.println("[HiredMerchantSave Thread " + ThreadID + "] Shops Saved: " + ShopsSaved + " | Time Taken: " + TimeTaken + " Milliseconds");
                synchronized (ToNotify) {
                    ToNotify.notify();
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(HiredMerchantSave.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        private void Queue(HiredMerchant hm) {
            Queue.add(hm);
        }

        private void SetToNotify(Object o) {
            if (ToNotify == null) {
                ToNotify = o;
            }
        }
    }

    private static class TimingThread extends Thread {

        private final HiredMerchantSaveRunnable ext;

        public TimingThread(HiredMerchantSaveRunnable r) {
            super(r);
            ext = r;
        }

        public HiredMerchantSaveRunnable getRunnable() {
            return ext;
        }
    }
}
