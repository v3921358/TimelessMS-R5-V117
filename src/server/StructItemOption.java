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
import java.util.Map;

/**
 *
 * @author Burblish
 */
public class StructItemOption {

    public static String[] types = {"incSTR", "incDEX", "incINT", "incLUK", "incACC", "incEVA", "incSpeed", "incJump",
        "incPAD", "incMAD", "incPDD", "incMDD", "prop", "time", "incSTRr", "incDEXr", "incINTr",
        "incLUKr", "incMHPr", "incMMPr", "incACCr", "incEVAr", "incPADr", "incMADr", "incPDDr",
        "incMDDr", "incCr", "incDAMr", "RecoveryHP", "RecoveryMP", "HP", "MP", "level",
        "ignoreTargetDEF", "ignoreDAM", "incAllskill", "ignoreDAMr", "RecoveryUP",
        "incCriticaldamageMin", "incCriticaldamageMax", "incTerR", "incAsrR", "DAMreflect",
        "mpconReduce", "reduceCooltime", "incMesoProp", "incRewardProp", "boss", "incMHP", "incMMP", "attackType"};
    public int optionType, reqLevel, opID; // opID = nebulite Id or potential ID
    public String face; // angry, cheers, love, blaze, glitter
    public Map<String, Integer> data = new HashMap<>();

    public int get(final String type) {
        return data.get(type) != null ? data.get(type) : 0;
    }

    @Override
    public final String toString() { // I should read from the "string" value instead.
        return data.toString();
    }
}
