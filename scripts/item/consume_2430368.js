


/*

	히나 온라인 소스 팩의 스크립트 입니다.

        제작 : 티썬

	엔피시아이디 : 
	
	엔피시 이름 :

	엔피시가 있는 맵 : 

	엔피시 설명 : 


*/

importPackage(Packages.client.items);
importPackage(java.lang);
var status = -1;

function start() {
    status = -1;
    action (1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1 || mode == 0) {
        cm.dispose();
        return;
    }
    if (mode == 1) {
        status++;
    }
    
    if (status == 0) {
        var leftslot = cm.getPlayer().getInventory(MapleInventoryType.USE).getNumFreeSlot();
        if (leftslot < 2) {
            cm.sendOk("인벤토리 공간이 최소한 5칸은 필요합니다. 소비 탭의 공간을 2칸이상 비워주신 후 다시 열어주세요.");
            cm.dispose();
            return;
        }
        var rand = Math.random() * 20;
        if (rand < 3) {
            cm.gainItem(2430029, 1);
        } else if (rand < 8) {
            cm.gainItem(2430026, 1);
        } else if (rand < 13) {
            cm.gainItem(2430027, 1);
        } else {
            cm.gainItem(2430028, 1);
        }
        cm.gainItem(2430368, -1);
        cm.dispose();
    }
}


