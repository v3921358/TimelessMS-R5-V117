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

public class CashItemInfo {

    private int itemId;
    private int count;
    private int price;
    private int sn;
    private int expire;
    private int gender;
    private boolean onSale;

    public CashItemInfo(int itemId, int count, int price, int sn, int expire, int gender, boolean sale) {
        this.itemId = itemId;
        this.count = count;
        this.price = price;
        this.sn = sn;
        this.expire = expire;
        this.gender = gender;
        this.onSale = sale;
    }

    public int getId() {
        return itemId;
    }

    public int getCount() {
        return count;
    }

    public int getPrice() {
        return price;
    }

    public int getSN() {
        return sn;
    }

    public int getPeriod() {
        return expire;
    }

    public int getGender() {
        return gender;
    }

    public boolean onSale() {
        return onSale || (CashItemFactory.getInstance().getModInfo(sn) != null && CashItemFactory.getInstance().getModInfo(sn).showUp);
    }

    public boolean genderEquals(int g) {
        return g == this.gender || this.gender == 2;
    }

    public static class CashModInfo {

        public int discountPrice, mark, priority, sn, itemid, flags, period, gender, count, meso, unk_1, unk_2, unk_3, extra_flags;
        public boolean showUp, packagez;
        private CashItemInfo cii;

        public CashModInfo(int sn, int discount, int mark, boolean show, int itemid, int priority, boolean packagez, int period, int gender, int count, int meso, int unk_1, int unk_2, int unk_3, int extra_flags) {
            this.sn = sn;
            this.itemid = itemid;
            this.discountPrice = discount;
            this.mark = mark; //0 = new, 1 = sale, 2 = hot, 3 = event
            this.showUp = show;
            this.priority = priority;
            this.packagez = packagez;
            this.period = period;
            this.gender = gender;
            this.count = count;
            this.meso = meso;
            this.unk_1 = unk_1; //0 = doesn't have, 1 = has, but false, 2 = has and true
            this.unk_2 = unk_2;
            this.unk_3 = unk_3;
            this.extra_flags = extra_flags;
            this.flags = extra_flags;

            if (this.itemid > 0) {
                this.flags |= 0x1;
            }
            if (this.count > 0) {
                this.flags |= 0x2;
            }
            if (this.discountPrice > 0) {
                this.flags |= 0x4;
            }
            if (this.unk_1 > 0) {
                this.flags |= 0x8;
            }
            if (this.priority >= 0) {
                this.flags |= 0x10;
            }
            if (this.period > 0) {
                this.flags |= 0x20;
            }
            //0x40 = ?
            if (this.meso > 0) {
                this.flags |= 0x80;
            }
            if (this.unk_2 > 0) {
                this.flags |= 0x100;
            }
            if (this.gender >= 0) {
                this.flags |= 0x200;
            }
            if (this.showUp) {
                this.flags |= 0x400;
            }
            if (this.mark >= -1 || this.mark <= 3) {
                this.flags |= 0x800;
            }
            if (this.unk_3 > 0) {
                this.flags |= 0x1000;
            }
            //0x2000, 0x4000, 0x8000, 0x10000, 0x20000, 0x100000, 0x80000 - ?
            if (this.packagez) {
                this.flags |= 0x40000;
            }
        }

        public CashItemInfo toCItem(CashItemInfo backup) {
            if (cii != null) {
                return cii;
            }
            final int item, c, price, expire, gen;
            final boolean onSale;
            if (itemid <= 0) {
                item = (backup == null ? 0 : backup.getId());
            } else {
                item = itemid;
            }
            if (count <= 0) {
                c = (backup == null ? 0 : backup.getCount());
            } else {
                c = count;
            }
            if (meso <= 0) {
                if (discountPrice <= 0) {
                    price = (backup == null ? 0 : backup.getPrice());
                } else {
                    price = discountPrice;
                }
            } else {
                price = meso;
            }
            if (period <= 0) {
                expire = (backup == null ? 0 : backup.getPeriod());
            } else {
                expire = period;
            }
            if (gender < 0) {
                gen = (backup == null ? 0 : backup.getGender());
            } else {
                gen = gender;
            }
            if (!showUp) {
                onSale = (backup == null ? false : backup.onSale());
            } else {
                onSale = showUp;
            }

            cii = new CashItemInfo(item, c, price, sn, expire, gen, onSale);
            return cii;
        }
    }
}
