//Script by Alcandon

var status;
var text = "Hey Quick Pick One Before I Shoot You!";

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == 1)
        status++;
    else {
        cm.sendOk("#e#kOk, see you next time!");
        cm.dispose();
        return;
    }
        if (status == 12) {
            cm.sendNext("Hello I am the Boss Spawner of GreekMaple, pick a boss to spawn NOW!!");
        }
        else if (status == 0) {
    cm.sendSimple("Pick one to spawn noob.\r\n#L0#Headless Horseman\r\n#L1#Capt. Latanica\r\n#L2#Black Crow\r\n#L3#Papulatus\r\n#L4#Pianus\r\n#L5#Zakum\r\n#L6#Big Foot\r\n#L7#Anego\r\n#L22#Kill and Clear drops!");
        }
        else if (status == 1) {

        if (selection == 0) {
            cm.summonMob(9400549, 3500000, 600000, 5);
            cm.dispose();
        } else {
      cm.sendOk("You can't spawn monsters if there is already monsters on the map");
      cm.dispose();
}
}
        else if (selection == 1) {
            cm.summonMob(9420513, 2000000, 370000, 1);
            cm.dispose();
        } else {
      cm.sendOk("You can't spawn monsters if there is already monsters on the map");
      cm.dispose();
}
}
        else if (selection == 2) {
            cm.summonMob(9400014, 35000000, 2980000, 1);
            cm.dispose();
        } else {
      cm.sendOk("You can't spawn monsters if there is already monsters on the map");
      cm.dispose();
}
}
        else if (selection == 3) {
            cm.summonMob(8500001, 23000000, 1210000, 1);
            cm.dispose();
        } else {
      cm.sendOk("You can't spawn monsters if there is already monsters on the map");
      cm.dispose();
}
}
        else if (selection == 4) {
            cm.summonMob(8510000, 30000000, 1800000, 1);
            cm.dispose();
        } else {
      cm.sendOk("You can't spawn monsters if there is already monsters on the map");
      cm.dispose();
}
}
        else if (selection == 5) {
            cm.summonMob(100100, 66000000, 4000000, 1);
            cm.dispose();
        } else {
      cm.sendOk("You can't spawn monsters if there is already monsters on the map");
      cm.dispose();
}
}
        else if (selection == 6) {
            cm.summonMob(9400575, 32000000, 4000000, 1);
            cm.dispose();
        } else {
      cm.sendOk("You can't spawn monsters if there is already monsters on the map");
      cm.dispose();
}
}
        else if (selection == 7) {
            cm.summonMob(9400121, 75000000, 4000000, 1);
            cm.dispose();
        } else {
      cm.sendOk("You can't spawn monsters if there is already monsters on the map");
      cm.dispose();
}
}
        else if (selection == 8) {
     if (cm.getPlayer().getMap().getMonsterCount() == 0) {
            cm.summonMob(9420513, 4000000, 210000, 1);
            cm.dispose();
        } else {
      cm.sendOk("You can't spawn monsters if there is already monsters on the map");
      cm.dispose();
}
}
        else if (selection == 9) {
     if (cm.getPlayer().getMap().getMonsterCount() == 0) {
            cm.summonMob(8510000, 55000000, 1000000, 1);
            cm.dispose();
        } else {
      cm.sendOk("You can't spawn monsters if there is already monsters on the map");
      cm.dispose();
}
}
        else if (selection == 10) {
     if (cm.getPlayer().getMap().getMonsterCount() == 0) {
            cm.summonMob(8520000, 50000000, 800000, 1);
            cm.dispose();
        } else {
      cm.sendOk("You can't spawn monsters if there is already monsters on the map");
      cm.dispose();
}
}
        else if (selection == 11) {
     if (cm.getPlayer().getMap().getMonsterCount() == 0) {
            cm.summonMob(8220003, 23400000, 80000, 1);
            cm.dispose();
        } else {
      cm.sendOk("You can't spawn monsters if there is already monsters on the map");
      cm.dispose();
}
}
        else if (selection == 12) {
     if (cm.getPlayer().getMap().getMonsterCount() == 0) {
            cm.summonMob(9500150, 6900, 1400, 1);
            cm.dispose();
        } else {
      cm.sendOk("You can't spawn monsters if there is already monsters on the map");
      cm.dispose();
}
}
        else if (selection == 13) {
     if (cm.getPlayer().getMap().getMonsterCount() == 0) {
            cm.summonMob(9500139, 50000, 6000, 1);
            cm.dispose();
        } else {
      cm.sendOk("You can't spawn monsters if there is already monsters on the map");
      cm.dispose();
}
}
        else if (selection == 14) {
     if (cm.getPlayer().getMap().getMonsterCount() == 0) {
            cm.summonMob(9500140, 100000, 10000, 1);
            cm.dispose();
        } else {
      cm.sendOk("You can't spawn monsters if there is already monsters on the map");
      cm.dispose();
}
}
        else if (selection == 15) {
cm.killAllMonsters();
            cm.dispose();
        }

        else if (selection == 16) {
     if (cm.getPlayer().getMap().getPlayerCount() == 1) {
            cm.clearDrops();
            cm.dispose();
        } else {
      cm.sendOk("You can't use it while theres players!");
      cm.dispose();
}
}
        else if (selection == 17) {
     if (cm.getPlayer().getMap().getMonsterCount() == 0) {
            cm.summonMob(9400013, 45000, 1850, 1);
            cm.dispose();
        } else {
      cm.sendOk("You can't spawn monsters if there is already monsters on the map");
      cm.dispose();
}
}
        else if (selection == 18) {
     if (cm.getPlayer().getMap().getMonsterCount() == 0) {
            cm.summonMob(100101, 100, 30, 1);
            cm.dispose();
        } else {
      cm.sendOk("You can't spawn monsters if there is already monsters on the map");
      cm.dispose();
}
}
        else if (selection == 19) {
     if (cm.getPlayer().getMap().getMonsterCount() == 0) {
            cm.summonMob(9300106, 680000, 9000, 1);
            cm.dispose();
        } else {
      cm.sendOk("You can't spawn monsters if there is already monsters on the map");
      cm.dispose();
}
}
        else if (selection == 20) {
     if (cm.getPlayer().getMap().getMonsterCount() == 0) {
            cm.summonMob(9400581, 45000, 3547, 1);
            cm.dispose();
        } else {
      cm.sendOk("You can't spawn monsters if there is already monsters on the map");
      cm.dispose();
}
}
        else if (selection == 21) {
     if (cm.getPlayer().getMap().getMonsterCount() == 0) {
            cm.summonMob(9400112, 200000000, 11800000, 1);
            cm.dispose();
        } else {
      cm.sendOk("You can't spawn monsters if there is already monsters on the map");
      cm.dispose();
}
}
        else if (selection == 22) {
        cm.killAllMonsters();
        cm.clearDrops();
        cm.dispose();
}
        }
    }