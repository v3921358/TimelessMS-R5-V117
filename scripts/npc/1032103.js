/*

	Original Script made by leon of Nakedstory
	Edited by Yiyuan of NakedStory83
	
*/

var status = 0;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
    } else {
        if (mode == 0 && status == 0) {
            cm.dispose();
            return;
        }
        if (mode == 1)
            status++;
        else
            status--;
        if (status == 0) {
            cm.sendSimple("You Currently Have #c4000313# #i4000313#.\r\n\
You can get these via monsters in the FM rooms.\r\n\
#L1# Exchange 5000 #b#v4000313##k for a Set of #b#v1902009##k\r\n\
#L2# Exchange 5000 #b#v4000313##k for a Set of #b#v1902008##k\r\n\
#L3# Exchange 5000 #b#v4000313##k for a #b#v1122005##k\r\n\
#L4# Exchange 15000 #b#v4000313##k for a 300 WA #b#v1302101##k\r\n\
#L5# Exchange 15000 #b#v4000313##k for a 1.25k WA #b#v1302081##k\r\n\
#L6# Exchange 15000 #b#v4000313##k for a 1.25k WA #b#v1312037##k\r\n\
#L7# Exchange 15000 #b#v4000313##k for a 1.25k WA #b#v1322060##k\r\n\
#L8# Exchange 15000 #b#v4000313##k for a 1.25k WA #b#v1412033##k\r\n\
#L9# Exchange 15000 #b#v4000313##k for a 1.25k WA #b#v1402046##k\r\n\
#L10# Exchange 15000 #b#v4000313##k for a 1.25k WA #b#v1452057##k\r\n\
#L11# Exchange 15000 #b#v4000313##k for a 1.25k WA #b#v1332074##k\r\n\
#L12# Exchange 15000 #b#v4000313##k for a 1.25k WA #b#v1472068##k\r\n\
#L19# Exchange 8000 #b#v4000313##k for a  100% + 100WA #v2040912#\r\n\
#L20# Exchange 8000 #b#v4000313##k for a  100% + 100WA #v2043013#\r\n\
#L21# Exchange 8000 #b#v4000313##k for a  100% + 100WA #v2043108#\r\n\
#L22# Exchange 8000 #b#v4000313##k for a  100% + 100WA #v2043208#\r\n\
#L23# Exchange 8000 #b#v4000313##k for a  100% + 100WA #v2043308#\r\n\
#L24# Exchange 8000 #b#v4000313##k for a  100% + 100MA #v2043708#\r\n\
#L25# Exchange 8000 #b#v4000313##k for a  100% + 100MA #v2043808#\r\n\
#L26# Exchange 8000 #b#v4000313##k for a  100% + 100WA #v2044008#\r\n\
#L27# Exchange 8000 #b#v4000313##k for a  100% + 100WA #v2044108#\r\n\
#L28# Exchange 8000 #b#v4000313##k for a  100% + 100WA #v2044208#\r\n\
#L29# Exchange 8000 #b#v4000313##k for a  100% + 100WA #v2044308#\r\n\
#L30# Exchange 8000 #b#v4000313##k for a  100% + 100WA #v2044408#\r\n\
#L31# Exchange 8000 #b#v4000313##k for a  100% + 100WA #v2044508#\r\n\
#L32# Exchange 8000 #b#v4000313##k for a  100% + 100WA #v2044608#\r\n\
#L33# Exchange 8000 #b#v4000313##k for a  100% + 100WA #v2044708#");

} else if (status == 1) {
                cm.dispose();
          if (selection == 0) {
                if (cm.haveItem(4000313, 5000)) {
                    cm.gainItem(2022283, 1);
                    cm.gainItem(4000313, -5000);
                    cm.sendOk("Thank you for your #b#v4000313##k!");
                } else {
                    cm.sendOk("Sorry you dont have enough #b#v4000313##k!");
                }
          } else if (selection == 1) {
                if (cm.haveItem(4000313, 5000)) {
                    cm.gainItem(1912004, 1);
                    cm.gainItem(1902009, 1);
                    cm.gainItem(4000313, -5000);
                    cm.sendOk("Thank you for your #b#v4000313##k!");
                } else {
                    cm.sendOk("Sorry you dont have enough #b#v4000313##k!");
                }
                
	  } else if (selection == 2) {
                if (cm.haveItem(4000313, 5000)) {
                    cm.gainItem(1912003, 1);
                    cm.gainItem(1902008, 1);
                    cm.gainItem(4000313, -5000);
                    cm.sendOk("Thank you for your #b#v4000313##k!");
                } else {
                    cm.sendOk("Sorry you dont have enough #b#v4000313##k!");
                }

	 } else if (selection == 3) {
                if (cm.haveItem(4000313, 5000)) {
                    cm.gainItem(1122005, 1);
                    cm.gainItem(4000313, -5000);
                    cm.sendOk("Thank you for your #b#v4000313##k!");
                } else {
                    cm.sendOk("Sorry you dont have enough #b#v4000313##k!");
                }


	 } else if (selection == 4) {
                if (cm.haveItem(4000313, 15000)) {
                    cm.gainItem(1302101, 1);
                    cm.gainItem(4000313, -15000);
                    cm.sendOk("Thank you for your #b#v4000313##k!");
                } else {
                    cm.sendOk("Sorry you dont have enough #b#v4000313##k!");
                }

	 } else if (selection == 5) {
                if (cm.haveItem(4000313, 15000)) {
                    cm.gainItem(1302081, 1);
                    cm.gainItem(4000313, -3000);
                    cm.gainItem(4000313, -3000);
                    cm.gainItem(4000313, -3000);
                    cm.gainItem(4000313, -3000);
                    cm.gainItem(4000313, -3000);
                    cm.sendOk("Thank you for your #b#v4000313##k!");
                } else {
                    cm.sendOk("Sorry you dont have enough #b#v4000313##k!");
                }

	 } else if (selection == 6) {
                if (cm.haveItem(4000313, 15000)) {
                    cm.gainItem(4000313, -3000);
                    cm.gainItem(4000313, -3000);
                    cm.gainItem(4000313, -3000);
                    cm.gainItem(4000313, -3000);
                    cm.gainItem(4000313, -3000);
                    cm.sendOk("Thank you for your #b#v4000313##k!");
                    cm.gainItem(1312037, 1);
                } else {
                    cm.sendOk("Sorry you dont have enough #b#v4000313##k!");
                }

	 } else if (selection == 7) {
                if (cm.haveItem(4000313, 15000)) {
                    cm.gainItem(4000313, -3000);
                    cm.gainItem(4000313, -3000);
                    cm.gainItem(4000313, -3000);
                    cm.gainItem(4000313, -3000);
                    cm.gainItem(4000313, -3000);
                    cm.sendOk("Thank you for your #b#v4000313##k!");
                    cm.gainItem(1322060, 1);
                } else {
                    cm.sendOk("Sorry you dont have enough #b#v4000313##k!");
                }


	 } else if (selection == 8) {
                if (cm.haveItem(4000313, 15000)) {
                    cm.gainItem(4000313, -3000);
                    cm.gainItem(4000313, -3000);
                    cm.gainItem(4000313, -3000);
                    cm.gainItem(4000313, -3000);
                    cm.gainItem(4000313, -3000);
                    cm.sendOk("Thank you for your #b#v4000313##k!");
                    cm.gainItem(1412033, 1);
                } else {
                    cm.sendOk("Sorry you dont have enough #b#v4000313##k!");
                }


	 } else if (selection == 9) {
                if (cm.haveItem(4000313, 15000)) {
                    cm.gainItem(4000313, -3000);
                    cm.gainItem(4000313, -3000);
                    cm.gainItem(4000313, -3000);
                    cm.gainItem(4000313, -3000);
                    cm.gainItem(4000313, -3000);
                    cm.sendOk("Thank you for your #b#v4000313##k!");
                    cm.gainItem(1402046, 1);
                } else {
                    cm.sendOk("Sorry you dont have enough #b#v4000313##k!");
                }


	 } else if (selection == 10) {
                if (cm.haveItem(4000313, 15000)) {
                    cm.gainItem(4000313, -3000);
                    cm.gainItem(4000313, -3000);
                    cm.gainItem(4000313, -3000);
                    cm.gainItem(4000313, -3000);
                    cm.gainItem(4000313, -3000);
                    cm.sendOk("Thank you for your #b#v4000313##k!");
                    cm.gainItem(1452057, 1);
                } else {
                    cm.sendOk("Sorry you dont have enough #b#v4000313##k!");
                }


	 } else if (selection == 11) {
                if (cm.haveItem(4000313, 15000)) {
                    cm.gainItem(4000313, -3000);
                    cm.gainItem(4000313, -3000);
                    cm.gainItem(4000313, -3000);
                    cm.gainItem(4000313, -3000);
                    cm.gainItem(4000313, -3000);
                    cm.sendOk("Thank you for your #b#v4000313##k!");
                    cm.gainItem(1332074, 1);
                } else {
                    cm.sendOk("Sorry you dont have enough #b#v4000313##k!");
                }


	 } else if (selection == 12) {
                if (cm.haveItem(4000313, 15000)) {
                    cm.gainItem(4000313, -3000);
                    cm.gainItem(4000313, -3000);
                    cm.gainItem(4000313, -3000);
                    cm.gainItem(4000313, -3000);
                    cm.gainItem(4000313, -3000);
                    cm.sendOk("Thank you for your #b#v4000313##k!");
                    cm.gainItem(1472068, 1);
                } else {
                    cm.sendOk("Sorry you dont have enough #b#v4000313##k!");
                }

	 } else if (selection == 13) {
                if (cm.haveItem(4000313, 40000)) {
                    cm.gainItem(4000313, -30000);
                    cm.gainItem(4000313, -10000);
                    cm.sendOk("Thank you for your #b#v4000313##k!");
                    cm.gainItem(1040036, 1);
                } else {
                    cm.sendOk("Sorry you dont have enough #b#v4000313##k!");
                }

	 } else if (selection == 14) {
                if (cm.haveItem(4000313, 40000)) {
                    cm.gainItem(4000313, -30000);
                    cm.gainItem(4000313, -10000);
                    cm.sendOk("Thank you for your #b#v4000313##k!");
                    cm.gainItem(1041046, 1);
                } else {
                    cm.sendOk("Sorry you dont have enough #b#v4000313##k!");
                }

	 } else if (selection == 15) {
                if (cm.haveItem(4000313, 40000)) {
                    cm.gainItem(4000313, -30000);
                    cm.gainItem(4000313, -10000);
                    cm.sendOk("Thank you for your #b#v4000313##k!");
                    cm.gainItem(1060026, 1);
                } else {
                    cm.sendOk("Sorry you dont have enough #b#v4000313##k!");
                }

	 } else if (selection == 16) {
                if (cm.haveItem(4000313, 40000)) {
                    cm.gainItem(4000313, -30000);
                    cm.gainItem(4000313, -10000);
                    cm.sendOk("Thank you for your #b#v4000313##k!");
                    cm.gainItem(1061039, 1);
                } else {
                    cm.sendOk("Sorry you dont have enough #b#v4000313##k!");
                }

	 } else if (selection == 17) {
                if (cm.haveItem(4000313, 40000)) {
                    cm.gainItem(4000313, -30000);
                    cm.gainItem(4000313, -10000);
                    cm.sendOk("Thank you for your #b#v4000313##k!");
                    cm.gainItem(1102194, 1);
                } else {
                    cm.sendOk("Sorry you dont have enough #b#v4000313##k!");
                }

	 } else if (selection == 18) {
                if (cm.haveItem(4000313, 40000)) {
                    cm.gainItem(4000313, -30000);
                    cm.gainItem(4000313, -10000);
                    cm.sendOk("Thank you for your #b#v4000313##k!");
                    cm.gainItem(1072264, 1);
                } else {
                    cm.sendOk("Sorry you dont have enough #b#v4000313##k!");
                }
	 } else if (selection == 19) {
                if (cm.haveItem(4000313, 8000)) {
                    cm.gainItem(4000313, -8000);
                    cm.sendOk("Thank you for your #b#v4000313##k!");
                    cm.gainItem(20409124, 1);
                } else {
                    cm.sendOk("Sorry you dont have enough #b#v4000313##k!");
                }
	 } else if (selection == 20) {
                if (cm.haveItem(4000313, 8000)) {
                    cm.gainItem(4000313, -8000);
                    cm.sendOk("Thank you for your #b#v4000313##k!");
                    cm.gainItem(2043013, 1);
                } else {
                    cm.sendOk("Sorry you dont have enough #b#v4000313##k!");
                }
	 } else if (selection == 21) {
                if (cm.haveItem(4000313, 8000)) {
                    cm.gainItem(4000313, -8000);
                    cm.sendOk("Thank you for your #b#v4000313##k!");
                    cm.gainItem(2043108, 1);
                } else {
                    cm.sendOk("Sorry you dont have enough #b#v4000313##k!");
                }
	 } else if (selection == 22) {
                if (cm.haveItem(4000313, 8000)) {
                    cm.gainItem(4000313, -8000);
                    cm.sendOk("Thank you for your #b#v4000313##k!");
                    cm.gainItem(2043208, 1);
                } else {
                    cm.sendOk("Sorry you dont have enough #b#v4000313##k!");
                }
	 } else if (selection == 23) {
                if (cm.haveItem(4000313, 8000)) {
                    cm.gainItem(4000313, -8000);
                    cm.sendOk("Thank you for your #b#v4000313##k!");
                    cm.gainItem(2043308, 1);
                } else {
                    cm.sendOk("Sorry you dont have enough #b#v4000313##k!");
                }
	 } else if (selection == 24) {
                if (cm.haveItem(4000313, 8000)) {
                    cm.gainItem(4000313, -8000);
                    cm.sendOk("Thank you for your #b#v4000313##k!");
                    cm.gainItem(2043708, 1);
                } else {
                    cm.sendOk("Sorry you dont have enough #b#v4000313##k!");
                }
	 } else if (selection == 25) {
                if (cm.haveItem(4000313, 8000)) {
                    cm.gainItem(4000313, -8000);
                    cm.sendOk("Thank you for your #b#v4000313##k!");
                    cm.gainItem(2043808, 1);
                } else {
                    cm.sendOk("Sorry you dont have enough #b#v4000313##k!");
                }
	 } else if (selection == 26) {
                if (cm.haveItem(4000313, 8000)) {
                    cm.gainItem(4000313, -8000);
                    cm.sendOk("Thank you for your #b#v4000313##k!");
                    cm.gainItem(2044008, 1);
                } else {
                    cm.sendOk("Sorry you dont have enough #b#v4000313##k!");
                }
	 } else if (selection == 27) {
                if (cm.haveItem(4000313, 8000)) {
                    cm.gainItem(4000313, -8000);
                    cm.sendOk("Thank you for your #b#v4000313##k!");
                    cm.gainItem(2044108, 1);
                } else {
                    cm.sendOk("Sorry you dont have enough #b#v4000313##k!");
                }
	 } else if (selection == 28) {
                if (cm.haveItem(4000313, 8000)) {
                    cm.gainItem(4000313, -8000);
                    cm.sendOk("Thank you for your #b#v4000313##k!");
                    cm.gainItem(2044208, 1);
                } else {
                    cm.sendOk("Sorry you dont have enough #b#v4000313##k!");
                }
	 } else if (selection == 29) {
                if (cm.haveItem(4000313, 8000)) {
                    cm.gainItem(4000313, -8000);
                    cm.sendOk("Thank you for your #b#v4000313##k!");
                    cm.gainItem(2044308, 1);
                } else {
                    cm.sendOk("Sorry you dont have enough #b#v4000313##k!");
                }
	 } else if (selection == 30) {
                if (cm.haveItem(4000313, 8000)) {
                    cm.gainItem(4000313, -8000);
                    cm.sendOk("Thank you for your #b#v4000313##k!");
                    cm.gainItem(2044408, 1);
                } else {
                    cm.sendOk("Sorry you dont have enough #b#v4000313##k!");
                }
	 } else if (selection == 31) {
                if (cm.haveItem(4000313, 8000)) {
                    cm.gainItem(4000313, -8000);
                    cm.sendOk("Thank you for your #b#v4000313##k!");
                    cm.gainItem(2044508, 1);
                } else {
                    cm.sendOk("Sorry you dont have enough #b#v4000313##k!");
                }
	 } else if (selection == 32) {
                if (cm.haveItem(4000313, 8000)) {
                    cm.gainItem(4000313, -8000);
                    cm.sendOk("Thank you for your #b#v4000313##k!");
                    cm.gainItem(2044608, 1);
                } else {
                    cm.sendOk("Sorry you dont have enough #b#v4000313##k!");
                }
	 } else if (selection == 33) {
                if (cm.haveItem(4000313, 8000)) {
                    cm.gainItem(4000313, -8000);
                    cm.sendOk("Thank you for your #b#v4000313##k!");
                    cm.gainItem(2044708, 1);
                } else {
                    cm.sendOk("Sorry you dont have enough #b#v4000313##k!");
                }
            }
        }
    }
}
