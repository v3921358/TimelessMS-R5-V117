
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
            var text = "#h #님 안녕하세요, 메이플 월드의 여행은 즐거우신가요?\r\n#b미라클 큐브 조각#k을 #b잠재부여 주문서#k로 교환해 드리고 있습니다. \r\n현재 #r#c2430112##k개의 미라클 큐브 조각이 있습니다.\r\n원하시는 교환 항목을 선택해 주세요.\r\n\r\n";
            if(cm.haveItem(2430112, 10)) {
                text += "#b#L0#조각 10개로 1개의 #z2049401#로 교환하기.#l\r\n";
            } else {
                text += "#k#L0#조각 10개로 1개의 #z2049401#로 교환하기.#l\r\n";
            }
            if(cm.haveItem(2430112, 15)) {
                text += "#b#L1#조각 15개로 1개의 #z2049400#로 교환하기.#l\r\n";
            } else {
                text += "#k#L1#조각 15개로 1개의 #z2049400#로 교환하기.#l\r\n";
            }
            text += "#L2##b교환하지 않겠습니다.#l";
            cm.sendSimple(text);
        } else if(status == 1) {
            if(selection == 0) {
                if(cm.haveItem(2430112, 10)) {
                    if(cm.canHold(2049401)) {
                        cm.gainItem(2430112, -10);
                        cm.gainItem(2049401, 1);
                    } else {
                        cm.sendOk("죄송하지만 인벤토리 공간이 충분하지 않으신 것 같네요. #b소비#k탭의 인벤토리 공간을 비워주세요.");
                    }
                } else {
                    cm.sendOk("죄송하지만 #b#z2430112##k이 충분하지 않으신것 같네요.");
                }
                cm.dispose();
            } else if(selection == 1) {
                if(cm.haveItem(2430112, 15)) {
                    if(cm.canHold(2049400)) {
                        cm.gainItem(2430112, -15);
                        cm.gainItem(2049400, 1);
                    } else {
                        cm.sendOk("죄송하지만 인벤토리 공간이 충분하지 않으신 것 같네요. #b소비#k탭의 인벤토리 공간을 비워주세요.");
                    }
                } else {
                    cm.sendOk("죄송하지만 #b#z2430112##k이 충분하지 않으신것 같네요.");
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