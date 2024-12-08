importPackage(net.sf.odinms.server);

/* 
	Amon
*/
var status = 0;
var map = 0;

function start() {
	status = -1;
	action(1, 0, 0);
}

function action(mode, type, selection) {
	if (mode == -1) {
		cm.dispose();
	} else {
		if (mode == 0) {
			cm.dispose();
			return;
		}
		if (mode == 1)
			status++;
		else
			status--;
		if (status == 0) {
			cm.sendSimple("Welcome to #rSpooky World#k. I can not help with the fight, but can I do anything else for you?\r\n#L1#Spawn Targa for 1 Mesos!#l\r\n\#L2#Spawn Scarlion for 2 Mesos!#l\r\n\#L3#Get me Out of Here :(#l");
		} else if (status == 1) {
			if (selection == 1) {
				if(cm.getMeso() >= 1 && cm.getPlayer().getMap().getMonsterCount() == 0) {
				cm.summonMob(9420542, 60000000, 568800, 1);
				cm.gainMeso(-1);
				cm.dispose();
		} else {
				cm.sendOk("Sorry, you do not have enough mesos or its already spawned!");
				}
				cm.dispose();
			} else if (selection == 2) {
				if(cm.getMeso() >= 2 && cm.getPlayer().getMap().getMonsterCount() == 0) {
				cm.summonMob(9420547, 60000000, 568800, 1);
				cm.gainMeso(-2);
				cm.dispose();
		} else {
				cm.sendOk("Sorry, you do not have enough mesos or its already spawned!");
				}
				cm.dispose();
			} else if (selection == 3) {
			cm.warp(100000000);
			cm.dispose();
			} else if (selection == 4) {
				cm.resetReactors();
				cm.sendOk("Done");
				cm.dispose();
			}
		}
	}
}
