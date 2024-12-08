var status = 0;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
    } else {
        if (mode == 0 && status == 1) {
            cm.dispose();
            return;
        }
        if (mode == 1)
            status++;
        else
            status--;
            if (status == 0) {
    cm.sendNext ("Hello and welcome to TimelessMS! I am Roan Li, the Assistant. Before you go I can tell you some of the server features!");
        } else if (status == 1) { 
    cm.sendSimple ("Select one of the following option to find out more about our server. \r\n#L0##bWho are the staffs??#k #l\r\n#L2##bWhat are the features?#k #l\r\n#L3##bI'm ready to go!#k #l\r\n#L4##bLet me consider!#k");
            } else if (status == 2) {
                if (selection == 0) {
    if (selection == 0) {
    cm.sendOk ("#bOur Staff#k\r\n #rOwners#k \r\nBurblish");
    cm.dispose();
     }
     if (selection == 1) {
     cm.sendOk ("#bServer Features#k\r\n#rCustom Start With Starter Pack#k\r\n#bFM NPC#k\r\n#rBossPQ#k\r\n#bFree-Item NPC#k\r\n#r96% Skills working!#k\r\n#bStylist#k\r\n#rSmega Giver#k\r\n#rSkill Maxer#l\r\n#bJQ Warper#l\r\n#rSuper Rebirth's/MSI's#l\r\n#bRebirths#l");
     cm.dispose();
     }
     } else if (selection == 2) {
     cm.sendOk ("#bServer Features#k\r\n#rCustom Start With Starter Pack#k\r\n#bFM NPC#k\r\n#rBossPQ#k\r\n#bFree-Item NPC#k\r\n#r96% Skills working!#k\r\n#bStylist#k\r\n#rSmega Giver#k\r\n#rSkill Maxer#l\r\n#bJQ Warper#l\r\n#rSuper Rebirth's/MSI's#l\r\n#bRebirths#l");
    cm.dispose();
     }
    if (selection == 3) {
        cm.gainItem(2000005, 1000);
        cm.gainItem(2022179, 20);
        cm.gainMeso(200000000);
        cm.gainItem(1082149, 1); //Gloves
        cm.gainItem(2040804, 8); //7 Gloves for Att Scrolls 60%
        cm.warp(910000000);
        cm.dispose();
    }
    if (selection == 4) {
    cm.sendOk ("Okay, tell me when you've decided what you want.");
    cm.dispose();
      }
      }
      }
}