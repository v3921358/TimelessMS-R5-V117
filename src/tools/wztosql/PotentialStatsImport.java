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
package tools.wztosql;

import java.io.FileOutputStream;
import java.io.IOException;
import provider.MapleDataDirectoryEntry;
import provider.MapleDataFileEntry;
import provider.MapleDataProvider;
import provider.MapleDataProviderFactory;

/*
 * Author: Itzik
 */
public class PotentialStatsImport {

    public static void main(String args[]) throws IOException {
        MapleDataProvider potStatSource = MapleDataProviderFactory.getDataProvider(MapleDataProviderFactory.fileInWZPath("Item.wz"));
        final MapleDataDirectoryEntry root = potStatSource.getRoot();
        StringBuilder sb = new StringBuilder();
        FileOutputStream out = new FileOutputStream("Potential Stats Import.txt", false);
        System.out.println("Loading Potential Stats!");
        sb.append("Potential Stats:\r\n");
        for (MapleDataFileEntry topDir : root.getFiles()) {
            int id = Integer.parseInt(topDir.getName().substring(0, 8));
            sb.append(id).append(", ");
        }
        System.out.println("Finished loading Potential Stats!");
        System.out.println("Finished loading Female Faces!");
        sb.append("\r\n\r\n");
        sb.append("The ID's will be located at the same folder where is your .bat launcher.");
        out.write(sb.toString().getBytes());
    }
}