// Made by the puppet coding Team!
// Founder of the team is coder
// Please do not change or remove the credits
// If posting this npc, post it with proper credits and original scripting
// Thanks for using puppet Team npcs!  
// =========================================================================================================
var status = -1; 

function start() 
{ 
    status = -1; 
    action(1, 0, 0); 
}

function action(mode, type, selection) { 
    if (mode == 1) 
    status++; 
    else
    status--;
    if (status == -1) {
            cm.dispose();
    } else if (status == 0) {
        cm.sendSimple("Hi, which jumpquest would you like to be warped to? THERE WILL BE NO REWARD. \r\n#L0#Physical Fitness Test \r\n#L1#Henesys Jump Quest \r\n#L2#Ludibrium Jump Quest\r\n#L3#Valley of Heroes\r\n#L4#Breathe of Lava\r\n#L5#Ghost Chimney\r\n#L6#Ola Ola\r\n#L7#Forest of Patience\r\n#L8#Deep Forest of Patience");
    } else if (status == 1) {
    if (selection == 0) {
        cm.warp(109040000);
		cm.dispose();
    } else if (selection == 1) {
        cm.warp(100000202);
	    cm.dispose();
    } else if (selection == 2) {
        cm.warp(220000006);
        cm.dispose();		
    } else if (selection == 3) {
        cm.warp(610020000);
        cm.dispose();
		} else if (selection == 4) {
		cm.warp(280020000);
		cm.dispose();
		} else if (selection == 5) {
		cm.warp(682000200);
		cm.dispose();
		} else if (selection == 6) {
		cm.warp(109030002);
		cm.dispose();
    } else if (selection == 7) {
	    cm.warp(101000100);
		cm.dispose();
	} else if (selection == 8) {
	    cm.warp(105040310);
	    cm.dispose();
		}
}
}