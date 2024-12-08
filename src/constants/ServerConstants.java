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
package constants;
import database.DatabaseProperties;
import java.util.Calendar;
import java.util.List;
import java.util.LinkedList;

public class ServerConstants {

    public static boolean TESPIA = false; // true = uses GMS test server, for MSEA it does nothing though
     public static final byte[] Gateway_IP = new byte[]{(byte) 8, (byte) 31, (byte) 98, (byte) 52};//Ususally nexons IP. Other than that its localhost
     

    public static final byte Class_Bonus_EXP(final int job) {
        switch (job) {
            case 1000:
            case 2100:
            case 508:
            case 501:
            case 2400:
            case 3000:
            case 3100:
            case 3200:
            case 3300:
            case 3400:
            case 3500:
            case 100:
            case 200:
            case 300:
            case 400:
            case 500:
            case 600:
            case 800://Manager.
            case 900://GM
            case 910://SuperGM
                return 10;
        }
        return 0;
    }
    
    public static boolean getEventTime() {
        int time = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        switch (Calendar.DAY_OF_WEEK) {
            case 1:
                return time >= 1 && time <= 5;
            case 2:
                return time >= 4 && time <= 9;
            case 3:
                return time >= 7 && time <= 12;
            case 4:
                return time >= 10 && time <= 15;
            case 5:
                return time >= 13 && time <= 18;
            case 6:
                return time >= 16 && time <= 21;
         }
        return time >= 19 && time <= 24;
    }

    // Start of Poll
    public static final boolean PollEnabled = true;
    public static final String Poll_Question = "Do you like the server?";
    public static final String[] Poll_Answers = {"YES", "MAYBE", "NO"};
    // End of Poll
    public static final short MAPLE_VERSION = (short) 117;
    public static final String MAPLE_PATCH = "1";
    public static final boolean BLOCK_CS = false;
    public static boolean Use_Localhost = false; // true = packets are logged, false = others can connect to server
    public static final int MIN_MTS = 110; //lowest amount an item can be, GMS = 110
    public static final int MTS_BASE = 500; //+amount to everything, GMS = 500, MSEA = 1000
    public static final int MTS_TAX = 10; //+% to everything, GMS = 10
    public static final int MTS_MESO = 5000; //mesos needed, GMS = 5000
    public static final boolean TRIPLE_TRIO = true;
    public static final int CURRENCY = 4001055; //maybe chg to something else
    public static final String FM_BGM = "Bgm13/CokeTown";
    public static final String GM_BGM = "Bgm13/CokeTown";//Newly added. A test before I started coding portals/maps.
    public static boolean release = true;//NO TOUCHHE
    public static final String SQL_USER = "root", SQL_PASS = "";
    //NO TOUCH DOWN THERE
    public static final String SQL_PORT = DatabaseProperties.getProperty("port"),//NO TOUCH 
    SQL_IP = DatabaseProperties.getProperty("ip"),//NO TOUCH
    SQL_DB = DatabaseProperties.getProperty("database");//NO TOUCH
    public static final long number1 = (142449577 + 753356065 + 611816275297389857L);
    public static final long number2 = 1877319832;
    public static final long number3 = 202227478981090217L;
    
       
    public static enum PlayerGMRank {

        NORMAL('@', 0),
        DONATOR('#', 1),
        SUPERDONATOR('$', 2),
        INTERN('%', 3),
        GM('!', 4),
        SUPERGM('!', 5),
        ADMIN('!', 6);
        private char commandPrefix;
        private int level;

        PlayerGMRank(char ch, int level) {
            commandPrefix = ch;
            this.level = level;
        }
        
        public char getCommandPrefix() {
            return commandPrefix;
        }

        public int getLevel() {
            return level;
        }
    }
    
     public static enum CommandType {

        NORMAL(0),
        TRADE(1),
        POKEMON(2);
        private int level;

        CommandType(int level) {
            this.level = level;
        }

        public int getType() {
            return level;
        }
    }
}