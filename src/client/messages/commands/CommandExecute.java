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

import client.MapleClient;
import constants.ServerConstants.CommandType;

/**
 * Interface for the executable part of a {@link CommandObject}.
 *
 * @author Burblish
 */
public abstract class CommandExecute {

    /**
     * The method executed when this command is used.
     *
     * @param c the client executing this command
     * @param splitted the command and any arguments attached
     *
     * @return 1 if you want to log the command, 0 if not. TODO: USE {@link #ReturnValue}
     */
    public abstract int execute(MapleClient c, String[] splitted);
    //1 = Success
    //0 = Something Went Wrong

    enum ReturnValue {

        DONT_LOG,
        LOG;
    }

    public CommandType getType() {
        return CommandType.NORMAL;
    }

    public static abstract class TradeExecute extends CommandExecute {

        @Override
        public CommandType getType() {
            return CommandType.TRADE;
        }
    }

    public static abstract class PokemonExecute extends CommandExecute {

        @Override
        public CommandType getType() {
            return CommandType.POKEMON;
        }
    }
}
