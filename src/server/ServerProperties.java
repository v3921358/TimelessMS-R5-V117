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

import constants.GameConstants;
import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import database.DatabaseConnection;

/**
 *
 * @author Burblish
 */
public class ServerProperties {

    private static final Properties props = new Properties();

    private ServerProperties() {
    }

    static {
        String toLoad = "channel.properties";
        loadProperties(toLoad);
		if (getProperty("GMS") != null) {
			GameConstants.GMS = Boolean.parseBoolean(getProperty("GMS"));
		}
        try {
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM auth_server_channel_ip");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                //if (rs.getString("name").equalsIgnoreCase("gms")) {
                //    GameConstants.GMS = Boolean.parseBoolean(rs.getString("value"));
                //} else {
                    props.put(rs.getString("name") + rs.getInt("channelid"), rs.getString("value"));
                //}
            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.exit(0); //Big ass error.
        }
        toLoad = GameConstants.GMS ? "worldGMS.properties" : "world.properties";
        loadProperties(toLoad);

    }

    public static void loadProperties(String s) {
        FileReader fr;
        try {
            fr = new FileReader(s);
            props.load(fr);
            fr.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static String getProperty(String s) {
        return props.getProperty(s);
    }

    public static void setProperty(String prop, String newInf) {
        props.setProperty(prop, newInf);
    }

    public static String getProperty(String s, String def) {
        return props.getProperty(s, def);
    }
}
