var slx = -1;
var status = 0;

function start() {
    cm.sendSimple("There's nothing like soaring trough the sky in an airplane. You really must try it! I had to stop due my age, but I can still teach you youngsters how to fly!\r\n#L0##bI would like to rent an airplane#k#l\r\n#L1##bCould you give me some details about the airplane?#k#l\r\n#L2##bWhat types of airplanes are there?#k#l");
}

function action(mode, type, selection) {
    if (mode == 1) {
	status++;
    } else {
        cm.dispose();
    }    
    if (status == 1) {
		if (selection == 0) {
			cm.sendSimple("Which airplane would you like to rent? \r\n#L12##v1932049##bWooden Airplane (1 day)#k #r10,000 mesos.#k\r\n#L13##v1932049##bWooden Airplane (7 days)#k #r50,000 mesos.#k\r\n#L14##v1932050##bWooden Airplane (1 day)#k #r30,000 mesos.#k\r\n#L15##v1932050##bWooden Airplane (7 days)#k#r150,000 mesos.#k");
			slx = 0;
		} else if (selection == 1) {
			cm.sendNext("You... You don't know what an airplane is? Well, I guess it is a little new. It's, er, a bit like the mounts that you have, but can take you long distances, like to other continents.");
			slx = 1;
		} else if (selection == 2) {
			cm.sendOk("There are two airplanes that can be rented. The first is the #bWooden Airplane#k. It's inexpensive and rliable. The other is the #bRed Airplane#k. This one is more pricey, but flies faster, and will get you to your destination 2 minutes quicker.");
			cm.dispose();
		}
	}
	if (slx == 0) {
		error = "Didn't I already lend you an airplane?";
		error1 = "You don't have enough mesos. You need money to fly in style!";
		if (status == 2) {
			if (selection == 12) {
				if (cm.haveItem(1932049)) {
					cm.sendOk(error);
				} else if (cm.getMeso() < 10000) {
					cm.sendOk(error1);
				} else {
					cm.gainItem(1932049, 1);
					cm.gainMeso(-10000);
				}
				cm.dispose();
			} else if (selection == 13) {
				if (cm.haveItem(1932049)) {
					cm.sendOk(error);
				} else if (cm.getMeso() < 50000) {
					cm.sendOk(error1);
				} else {
					cm.gainItem(1932049, 1);
					cm.gainMeso(-50000); // no timelapse.
				}
				cm.dispose();
			} else if (selection == 14) {
				if (cm.haveItem(1932050)) {
					cm.sendOk(error);
				} else if (cm.getMeso() < 30000) {
					cm.sendOk(error1);
				} else {
					cm.gainItem(1932050, 1);
					cm.gainMeso(-30000);
				}
				cm.dispose();
			} else if (selection == 15) {
				if (cm.haveItem(1932050)) {
					cm.sendOk(error);
				} else if (cm.getMeso() < 150000) {
					cm.sendOk(error1);
				} else {
					cm.gainItem(1932050, 1);
					cm.gainMeso(-150000); // no timelapse.
				}
				cm.dispose();
			}
		}
	} else if (slx == 1) {
		if (status == 2) {
			cm.sendNextPrev("'Course you can't fly to any continent. You can fly to #bVicotira Island, Ereve, Edelstein, Ludibrium, ariant, Mu Lung or Leafre#k from #bOrbis#k using the airplane. You can also fly the opposite route, of course. Lastly, you can fly to #bVictoria Island#k from #bEdelstein#k and vice-versa. These are the only location you can take an airplane to... The others are a bit too dangerous yet...");
		} else if (status == 3) {
			cm.sendNextPrev("If you want to go somewhere using an airplane, talk to the various people running the intercontinental flights like #bIsa the Station Guide#k at Orbis Station or #bCherry Cabin Crew#k at Station to Orbis.");
		} else if (status == 4) {
			cm.sendOk("That's it. Any more questions?");
			cm.dispose();
		}
	}
}