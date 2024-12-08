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

/**
 *
 * @author Burblish
 */
public class ItemIdCreator {

    public static void main(String args[]) throws IOException {
        MapleDataProvider weaponSource = MapleDataProviderFactory.getDataProvider(MapleDataProviderFactory.fileInWZPath("Character.wz/Weapon"));
        MapleDataProvider capSource = MapleDataProviderFactory.getDataProvider(MapleDataProviderFactory.fileInWZPath("Character.wz/Cap"));
        MapleDataProvider accessorySource = MapleDataProviderFactory.getDataProvider(MapleDataProviderFactory.fileInWZPath("Character.wz/Accessory"));
        MapleDataProvider capeSource = MapleDataProviderFactory.getDataProvider(MapleDataProviderFactory.fileInWZPath("Character.wz/Cape"));
        MapleDataProvider coatSource = MapleDataProviderFactory.getDataProvider(MapleDataProviderFactory.fileInWZPath("Character.wz/Coat"));
        MapleDataProvider dragonSource = MapleDataProviderFactory.getDataProvider(MapleDataProviderFactory.fileInWZPath("Character.wz/Dragon"));
        MapleDataProvider gloveSource = MapleDataProviderFactory.getDataProvider(MapleDataProviderFactory.fileInWZPath("Character.wz/Glove"));
        MapleDataProvider longcoatSource = MapleDataProviderFactory.getDataProvider(MapleDataProviderFactory.fileInWZPath("Character.wz/Longcoat"));
        MapleDataProvider mechanicSource = MapleDataProviderFactory.getDataProvider(MapleDataProviderFactory.fileInWZPath("Character.wz/Mechanic"));
        MapleDataProvider pantsSource = MapleDataProviderFactory.getDataProvider(MapleDataProviderFactory.fileInWZPath("Character.wz/Pants"));
        MapleDataProvider petequipSource = MapleDataProviderFactory.getDataProvider(MapleDataProviderFactory.fileInWZPath("Character.wz/PetEquip"));
        MapleDataProvider ringSource = MapleDataProviderFactory.getDataProvider(MapleDataProviderFactory.fileInWZPath("Character.wz/Ring"));
        MapleDataProvider shieldSource = MapleDataProviderFactory.getDataProvider(MapleDataProviderFactory.fileInWZPath("Character.wz/Shield"));
        MapleDataProvider shoesSource = MapleDataProviderFactory.getDataProvider(MapleDataProviderFactory.fileInWZPath("Character.wz/Shoes"));
        MapleDataProvider tamingmobSource = MapleDataProviderFactory.getDataProvider(MapleDataProviderFactory.fileInWZPath("Character.wz/TamingMob"));
        MapleDataProvider andriodSource = MapleDataProviderFactory.getDataProvider(MapleDataProviderFactory.fileInWZPath("Character.wz/Andriod"));
        MapleDataProvider familiarSource = MapleDataProviderFactory.getDataProvider(MapleDataProviderFactory.fileInWZPath("Character.wz/Familiar"));
        
        final MapleDataDirectoryEntry root = weaponSource.getRoot();
        StringBuilder sb = new StringBuilder();
        FileOutputStream out = new FileOutputStream("ItemID-Burblish.txt", false);
        System.out.println("Loading Weapons!");
        sb.append("Weapons:\r\n");
        for (MapleDataFileEntry topDir : root.getFiles()) {
            int id = Integer.parseInt(topDir.getName().substring(0, 8));
            sb.append(id).append(", ");
        }
        System.out.println("Finished loading Weapons!");
        System.out.println("Loading Female Hairs!");
        sb.append("\r\n\r\n");
        System.out.println("Loading Caps!");
        sb.append("Caps:\r\n");
        final MapleDataDirectoryEntry root2 = capSource.getRoot();
        for (MapleDataFileEntry topDir2 : root2.getFiles()) {
            int id = Integer.parseInt(topDir2.getName().substring(0, 8));
            sb.append(id).append(", ");
        }
        sb.append("\r\n\r\n");
        System.out.println("Loading Accessory!");
        sb.append("Accessory:\r\n");
        final MapleDataDirectoryEntry root3 = accessorySource.getRoot();
        for (MapleDataFileEntry topDir3 : root3.getFiles()) {
            int id = Integer.parseInt(topDir3.getName().substring(0, 8));
            sb.append(id).append(", ");
        }
        sb.append("\r\n\r\n");
        System.out.println("Loading Capes!");
        sb.append("Capes:\r\n");
        final MapleDataDirectoryEntry root4 = capeSource.getRoot();
        for (MapleDataFileEntry topDir4 : root4.getFiles()) {
            int id = Integer.parseInt(topDir4.getName().substring(0, 8));
            sb.append(id).append(", ");
        }
        sb.append("\r\n\r\n");
        System.out.println("Loading Andriod!");
        sb.append("Andriod:\r\n");
        final MapleDataDirectoryEntry root16 = andriodSource.getRoot();
        for (MapleDataFileEntry topDir16 : root16.getFiles()) {
            int id = Integer.parseInt(topDir16.getName().substring(0, 8));
            sb.append(id).append(", ");
        }
        sb.append("\r\n\r\n");
        System.out.println("Loading Coats!");
        sb.append("Coats:\r\n");
        final MapleDataDirectoryEntry root5 = coatSource.getRoot();
        for (MapleDataFileEntry topDir5 : root5.getFiles()) {
            int id = Integer.parseInt(topDir5.getName().substring(0, 8));
            sb.append(id).append(", ");
        }
        sb.append("\r\n\r\n");
        System.out.println("Loading Dragon!");
              sb.append("Dragon:\r\n");
            final MapleDataDirectoryEntry root6 = dragonSource.getRoot();
          for (MapleDataFileEntry topDir6 : root6.getFiles()) {
            int id = Integer.parseInt(topDir6.getName().substring(0, 8));
          sb.append(id).append(", ");
        }
        sb.append("\r\n\r\n");
        System.out.println("Loading Gloves!");
        sb.append("Gloves:\r\n");
        final MapleDataDirectoryEntry root7 = gloveSource.getRoot();
        for (MapleDataFileEntry topDir7 : root7.getFiles()) {
            int id = Integer.parseInt(topDir7.getName().substring(0, 8));
            sb.append(id).append(", ");
        }
        sb.append("\r\n\r\n");
        System.out.println("Loading Longcoats!");
        sb.append("Longcoats:\r\n");
        final MapleDataDirectoryEntry root8 = longcoatSource.getRoot();
        for (MapleDataFileEntry topDir8 : root8.getFiles()) {
            int id = Integer.parseInt(topDir8.getName().substring(0, 8));
            sb.append(id).append(", ");
        }
        sb.append("\r\n\r\n");
              System.out.println("Loading Mechanic!");
            sb.append("Mechanic:\r\n");
          final MapleDataDirectoryEntry root9 = mechanicSource.getRoot();
         for (MapleDataFileEntry topDir9 : root9.getFiles()) {
          int id = Integer.parseInt(topDir9.getName().substring(0, 8));
         sb.append(id).append(", ");
         }
         sb.append("\r\n\r\n");
        System.out.println("Loading Pants!");
        sb.append("Pants:\r\n");
        final MapleDataDirectoryEntry root10 = pantsSource.getRoot();
        for (MapleDataFileEntry topDir10 : root10.getFiles()) {
            int id = Integer.parseInt(topDir10.getName().substring(0, 8));
            sb.append(id).append(", ");
        }
                 sb.append("\r\n\r\n");
        System.out.println("Loading Familiar!");
        sb.append("Familiar:\r\n");
        final MapleDataDirectoryEntry root17 = familiarSource.getRoot();
        for (MapleDataFileEntry topDir17 : root17.getFiles()) {
            int id = Integer.parseInt(topDir17.getName().substring(0, 8));
            sb.append(id).append(", ");
        }
        sb.append("\r\n\r\n");
        System.out.println("Loading Pet Equips!");
        sb.append("Pet Equips:\r\n");
        final MapleDataDirectoryEntry root11 = petequipSource.getRoot();
        for (MapleDataFileEntry topDir11 : root11.getFiles()) {
            int id = Integer.parseInt(topDir11.getName().substring(0, 8));
            sb.append(id).append(", ");
        }
        sb.append("\r\n\r\n");
        System.out.println("Loading Rings!");
        sb.append("Rings:\r\n");
        final MapleDataDirectoryEntry root12 = ringSource.getRoot();
        for (MapleDataFileEntry topDir12 : root12.getFiles()) {
            int id = Integer.parseInt(topDir12.getName().substring(0, 8));
            sb.append(id).append(", ");
        }
        sb.append("\r\n\r\n");
        System.out.println("Loading Shields!");
        sb.append("Shields:\r\n");
        final MapleDataDirectoryEntry root13 = shieldSource.getRoot();
        for (MapleDataFileEntry topDir13 : root13.getFiles()) {
            int id = Integer.parseInt(topDir13.getName().substring(0, 8));
            sb.append(id).append(", ");
        }
        sb.append("\r\n\r\n");
        System.out.println("Loading Shoes!");
        sb.append("Shoes:\r\n");
        final MapleDataDirectoryEntry root14 = shoesSource.getRoot();
        for (MapleDataFileEntry topDir14 : root14.getFiles()) {
            int id = Integer.parseInt(topDir14.getName().substring(0, 8));
            sb.append(id).append(", ");
        }
        sb.append("\r\n\r\n");
        System.out.println("Loading Taming Mobs!");
        sb.append("Taming Mobs:\r\n");
        final MapleDataDirectoryEntry root15 = tamingmobSource.getRoot();
        for (MapleDataFileEntry topDir15 : root15.getFiles()) {
            int id = Integer.parseInt(topDir15.getName().substring(0, 8));
            sb.append(id).append(", ");
        }
        sb.append("\r\n\r\n");
        sb.append("The ID's will be located at the same folder where is your .bat launcher.");
        out.write(sb.toString().getBytes());
    }
}