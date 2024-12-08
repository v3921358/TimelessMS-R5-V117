
/*

    히나 소스 팩의 스크립트 입니다. (제작 : 티썬)

    엔피시아이디 : ?
    
    엔피시 이름 : 메이플 운영자

    엔피시가 있는 맵 : ?

    엔피시 설명 : 큐브조각 교환


*/
var status;

function start() {
    status = -1;
    action(1, 1, 0);
}

function action(mode, type, selection) {
    if (mode < 0) {
        cm.dispose();
    return;
    } else {
        if (mode == 1)
            status++;
        else
            status--;
        if (status == 0) {
	    if (!cm.canHold(2290285)) {
                cm.sendOk("인벤토리 공간이 부족합니다.");
		cm.dispose();
		return;
	    }
            cm.gainItem(2430144, -1);
	    cm.gainItem(2290285, 1);
	    cm.getPlayer().dropMessage(6, "신비의 마스터리 북으로 교환되었습니다.");
	    cm.dispose();
            
        } else { 
            cm.dispose();
        }
    }
}