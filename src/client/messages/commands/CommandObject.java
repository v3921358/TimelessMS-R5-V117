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
package client.messages.commands;

import client.MapleCharacter;
import client.MapleClient;
import constants.ServerConstants.CommandType;

/**
 * Represents a command given by a user
 *
 * @author Burblish
 */
public class CommandObject {
    /** what {@link MapleCharacter#gm} level is required to use this command */
    private int gmLevelReq;
    /** what gets done when this command is used */
    private CommandExecute exe;

    public CommandObject(CommandExecute c, int gmLevel) {
        exe = c;
        gmLevelReq = gmLevel;
    }

    /**
     * Call this to apply this command to the specified {@link MapleClient}
     * with the specified arguments.
     *
     * @param c the MapleClient to apply this to
     * @param splitted the arguments
     * @return See {@link CommandExecute#execute}
     */
    public int execute(MapleClient c, String[] splitted) {
        return exe.execute(c, splitted);
    }

    public CommandType getType() {
        return exe.getType();
    }

    /**
     * Returns the GMLevel needed to use this command.
     *
     * @return the required GM Level
     */
    public int getReqGMLevel() {
        return gmLevelReq;
    }
}
