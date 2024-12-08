
/*

    히나 소스 팩의 스크립트 입니다. (제작 : 엑시즈)

    엔피시아이디 : ?
    
    엔피시 이름 : 메이플 운영자

    엔피시가 있는 맵 : ?

    엔피시 설명 : 큐브조각 교환


*/
var status;
var select;

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
            var text = "#b마스터 미라클 큐브 조각 #r#c2430481#개#k를 가지고 있습니다. 은빛 각인의 인장은 #r10개#k, 고급 장비강화 주문서는 #r20개#k, 에픽 잠재능력 주문서 80%는 #r30개#k를 사용하여 교환할 수 있습니다.\r\n\r\n";
                text += "#b#L0##i2049501# #z2049501##l\r\n";
		text += "#b#L1##i2049300# #z2049300##l\r\n";
		text += "#b#L2##i2049701# #z2049701##l";
            cm.sendSimple(text);
        } else if(status == 1) {
            if (selection == 0) {
                if (cm.haveItem(2430481, 10)) {
		    cm.sendYesNo("정말 #b#i2049501# #z2049501##k로 바꾸시겠습니까?");
		    select = 0;
                } else {
                    cm.sendNext("죄송하지만 #b#z2430481##k이 충분하지 않으신것 같네요.");
		    cm.dispose();
                }
            } else if (selection == 1) {
                if (cm.haveItem(2430481, 20)) {
		    cm.sendYesNo("정말 #b#i2049300# #z2049300##k로 바꾸시겠습니까?");
		    select = 1;
		} else {
                    cm.sendNext("죄송하지만 #b#z2430481##k이 충분하지 않으신것 같네요.");
		    cm.dispose();
		}
	    } else if (selection == 2) {
                if (cm.haveItem(2430481, 30)) {
		    cm.sendYesNo("정말 #b#i2049701# #z20497010##k로 바꾸시겠습니까?");
		    select = 2;
		} else {
                    cm.sendNext("죄송하지만 #b#z2430481##k이 충분하지 않으신것 같네요.");
		    cm.dispose();
		}        
            } else {
                cm.dispose();
            }
	} else if (status == 2) {
            if (select == 0) {
                if (cm.canHold(2049501)) {
                    cm.gainItem(2430481, -10);
                    cm.gainItem(2049501, 1);
		    cm.sendNext("교환이 완료되었습니다.");
                } else {
                    cm.sendNext("죄송하지만 인벤토리 공간이 충분하지 않으신 것 같네요. #b소비#k탭의 인벤토리 공간을 비워주세요.");
                }
                cm.dispose();
            } else if (select == 1) {
		if (cm.canHold(2049300)) {
                    cm.gainItem(2430481, -20);
                    cm.gainItem(2049300, 1);
                } else {
                    cm.sendNext("죄송하지만 인벤토리 공간이 충분하지 않으신 것 같네요. #b소비#k탭의 인벤토리 공간을 비워주세요.");
                }
                cm.dispose();
	    } else if (select == 2) {
		if (cm.canHold(2049701)) {
                    cm.gainItem(2430481, -30);
                    cm.gainItem(2049701, 1);
                } else {
                    cm.sendNext("죄송하지만 인벤토리 공간이 충분하지 않으신 것 같네요. #b소비#k탭의 인벤토리 공간을 비워주세요.");
                }
                cm.dispose();        
            } else {
                cm.dispose();
            }
        } else { 
            cm.dispose();
        }
    }
}