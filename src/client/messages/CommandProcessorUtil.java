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
package client.messages;

import tools.StringUtil;

/**
 *
 * @author Burblish
 */
public class CommandProcessorUtil {

    public static String joinAfterString(String splitted[], String str) {
        for (int i = 1; i < splitted.length; i++) {
            if (splitted[i].equalsIgnoreCase(str) && i + 1 < splitted.length) {
                return StringUtil.joinStringFrom(splitted, i + 1);
            }
        }
        return null;
    }

    public static int getOptionalIntArg(String splitted[], int position, int def) {
        if (splitted.length > position) {
            try {
                return Integer.parseInt(splitted[position]);
            } catch (NumberFormatException nfe) {
                return def;
            }
        }
        return def;
    }

    public static String getNamedArg(String splitted[], int startpos, String name) {
        for (int i = startpos; i < splitted.length; i++) {
            if (splitted[i].equalsIgnoreCase(name) && i + 1 < splitted.length) {
                return splitted[i + 1];
            }
        }
        return null;
    }

    public static Long getNamedLongArg(String splitted[], int startpos, String name) {
        String arg = getNamedArg(splitted, startpos, name);
        if (arg != null) {
            try {
                return Long.parseLong(arg);
            } catch (NumberFormatException nfe) {
                // swallow - we don't really care
            }
        }
        return null;
    }

    public static Integer getNamedIntArg(String splitted[], int startpos, String name) {
        String arg = getNamedArg(splitted, startpos, name);
        if (arg != null) {
            try {
                return Integer.parseInt(arg);
            } catch (NumberFormatException nfe) {
                // swallow - we don't really care
            }
        }
        return null;
    }

    public static int getNamedIntArg(String splitted[], int startpos, String name, int def) {
        Integer ret = getNamedIntArg(splitted, startpos, name);
        if (ret == null) {
            return def;
        }
        return ret.intValue();
    }

    public static Double getNamedDoubleArg(String splitted[], int startpos, String name) {
        String arg = getNamedArg(splitted, startpos, name);
        if (arg != null) {
            try {
                return Double.parseDouble(arg);
            } catch (NumberFormatException nfe) {
                // swallow - we don't really care
            }
        }
        return null;
    }
}
