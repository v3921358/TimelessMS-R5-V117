function start() {
cm.sendSimple ("Hello I am the boss map warper!!! \r\n#L4#Scarlion/Targa (Need 1 or 2 meso in the map to spawn them)#l\r\n#L0#Zakum#l\r\n#L1#Horntail#l\r\n#L2#Pink Bean#l\r\n#L3#Buy Eye of Fire(100 000 meso)!#l");
}

function action(mode, type, selection) {
	cm.dispose();
	if (selection == 0) {
            cm.warp(280030000);
            cm.dispose();
	} else if (selection == 1) {
            cm.warp(240060200);
            cm.dispose();
        } else if (selection == 2) {
            cm.warp(270050100);
            cm.dispose();
} else if (selection == 3) {
    if (cm.getMeso() >= 100000) {
cm.gainItem(4001017, 1);
cm.gainMeso(-100000);
    cm.dispose();
    } else {
        cm.sendOk("You don't have enough mesos!");
}
}
 else if (selection == 4) {
cm.warp(551030200);
cm.dispose();
}
}