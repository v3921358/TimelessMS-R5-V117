
/*

    ���� �ҽ� ���� ��ũ��Ʈ �Դϴ�. (���� : Ƽ��)

    ���ǽþ��̵� : ?
    
    ���ǽ� �̸� : ������ ���

    ���ǽð� �ִ� �� : ?

    ���ǽ� ���� : 60���� ������


*/
importPackage(Packages.client.items);
var status = -1;

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
            var leftslot = cm.getPlayer().getInventory(MapleInventoryType.EQUIP).getNumFreeSlot();
            if (leftslot < 9) {
                cm.sendOk("�κ��丮 ������ �ּ��� 9ĭ�� �ʿ��մϴ�. ��� ���� ������ 9ĭ�̻� ����ֽ� �� �ٽ� �����ּ���.");
                cm.dispose();
                return;
            }
            
            switch (cm.getPlayer().getJob()) {
                case 110:
                case 120:
                    if (cm.getPlayer().getGender() == 0) {
                        cm.gainItem(1040182, 1);
                        cm.gainItem(1060170, 1);
                    } else {
                        cm.gainItem(1041185, 1);
                        cm.gainItem(1061194, 1);

                    }
                    cm.gainItem(1003336, 1);
                    cm.gainItem(1072599, 1);
                    cm.gainItem(1082381, 1); 
                    cm.gainItem(1092103, 1);
                    
                    cm.gainItem(1302190, 1);
                    break;
                case 130:
                    if (cm.getPlayer().getGender() == 0) {
                        cm.gainItem(1040182, 1);
                        cm.gainItem(1060170, 1);
                    } else {
                        cm.gainItem(1041185, 1);
                        cm.gainItem(1061194, 1);

                    }
                    cm.gainItem(1003336, 1);
                    cm.gainItem(1072599, 1);
                    cm.gainItem(1082381, 1); 
                    cm.gainItem(1092103, 1);
                    
                    cm.gainItem(1432114, 1);
                    break;
                case 1110:
                    if (cm.getPlayer().getGender() == 0) {
                        cm.gainItem(1040182, 1);
                        cm.gainItem(1060170, 1);
                    } else {
                        cm.gainItem(1041185, 1);
                        cm.gainItem(1061194, 1);

                    }
                    cm.gainItem(1003336, 1);
                    cm.gainItem(1072599, 1);
                    cm.gainItem(1082381, 1); 
                    cm.gainItem(1092103, 1);
                    
                    cm.gainItem(1302190, 1);
                    break;
                case 2110:
                    if (cm.getPlayer().getGender() == 0) {
                        cm.gainItem(1040182, 1);
                        cm.gainItem(1060170, 1);
                    } else {
                        cm.gainItem(1041185, 1);
                        cm.gainItem(1061194, 1);

                    }
                    cm.gainItem(1003336, 1);
                    cm.gainItem(1072599, 1);
                    cm.gainItem(1082381, 1); 
                    cm.gainItem(1092103, 1);
                    
                    cm.gainItem(1442152, 1);
                    break;
                case 3110:
                    if (cm.getPlayer().getGender() == 0) {
                        cm.gainItem(1040182, 1);
                        cm.gainItem(1060170, 1);
                    } else {
                        cm.gainItem(1041185, 1);
                        cm.gainItem(1061194, 1);

                    }
                    cm.gainItem(1003336, 1);
                    cm.gainItem(1072599, 1);
                    cm.gainItem(1082381, 1); 
                    cm.gainItem(1092103, 1);
                    
                    cm.gainItem(1322120, 1);
                    break;
                case 5110:
                    if (cm.getPlayer().getGender() == 0) {
                        cm.gainItem(1040182, 1);
                        cm.gainItem(1060170, 1);
                    } else {
                        cm.gainItem(1041185, 1);
                        cm.gainItem(1061194, 1);

                    }
                    cm.gainItem(1003336, 1);
                    cm.gainItem(1072599, 1);
                    cm.gainItem(1082381, 1); 
                    cm.gainItem(1092103, 1);
                    
                    cm.gainItem(1302190, 1);
                    break;
                case 210:
                case 220:
                case 230:
                case 1210:
                case 2214:
                case 3210:
                    if (cm.getPlayer().getGender() == 0) {
                        cm.gainItem(1050205, 1);
//                        cm.gainItem(1060156, 1);
                    } else {
                        cm.gainItem(1051249, 1);
//                        cm.gainItem(1061184, 1);
                    }
                    cm.gainItem(1003337, 1);
                    cm.gainItem(1072600, 1);
                    cm.gainItem(1082382, 1);
                    cm.gainItem(1372116, 1);
                    break;
                case 310:
                case 1310:
                    if (cm.getPlayer().getGender() == 0) {
                        cm.gainItem(1050204, 1);
                        //cm.gainItem(1060166, 1);
                    } else {
                        cm.gainItem(1051248, 1);
                        //cm.gainItem(1061190, 1);
                    }
                    cm.gainItem(1003338, 1);
                    cm.gainItem(1072601, 1);
                    cm.gainItem(1082383, 1);
                    cm.gainItem(1452145, 1);
                    break;
                case 320:
                    if (cm.getPlayer().getGender() == 0) {
                        cm.gainItem(1050204, 1);
                        //cm.gainItem(1060166, 1);
                    } else {
                        cm.gainItem(1051248, 1);
                        //cm.gainItem(1061190, 1);
                    }
                    cm.gainItem(1003338, 1);
                    cm.gainItem(1072601, 1);
                    cm.gainItem(1082383, 1);
                    cm.gainItem(1462134, 1);
                    break;
                case 2310:
                    if (cm.getPlayer().getGender() == 0) {
                        cm.gainItem(1050204, 1);
                        //cm.gainItem(1060166, 1);
                    } else {
                        cm.gainItem(1051248, 1);
                        //cm.gainItem(1061190, 1);
                    }
                    cm.gainItem(1003338, 1);
                    cm.gainItem(1072601, 1);
                    cm.gainItem(1082383, 1);
                    cm.gainItem(1522036, 1);
                    break;
                case 3310:
                    if (cm.getPlayer().getGender() == 0) {
                        cm.gainItem(1050204, 1);
                        //cm.gainItem(1060166, 1);
                    } else {
                        cm.gainItem(1051248, 1);
                        //cm.gainItem(1061190, 1);
                    }
                    cm.gainItem(1003338, 1);
                    cm.gainItem(1072601, 1);
                    cm.gainItem(1082383, 1);
                    cm.gainItem(1462134, 1);
                    break;
                case 410:
                case 420:
                case 1410:
                    if (cm.getPlayer().getGender() == 0) {
                        cm.gainItem(1040183, 1);
                        cm.gainItem(1060171, 1);
                    } else {
                        cm.gainItem(1041186, 1);
                        cm.gainItem(1061195, 1);
                    }
                    cm.gainItem(1003339, 1);
                    cm.gainItem(1072602, 1);
                    cm.gainItem(1082384, 1);
                    
                    cm.gainItem(1472119, 1);
                    cm.gainItem(1332166, 1);
                    break;
                    
                case 432:
                    if (cm.getPlayer().getGender() == 0) {
                        cm.gainItem(1040183, 1);
                        cm.gainItem(1060171, 1);
                    } else {
                        cm.gainItem(1041186, 1);
                        cm.gainItem(1061195, 1);
                    }
                    cm.gainItem(1003339, 1);
                    cm.gainItem(1072602, 1);
                    cm.gainItem(1082384, 1);
                    
                    cm.gainItem(1332166, 1);
                    cm.gainItem(1342051, 1);
                    
                    break;
                case 2410:
                    if (cm.getPlayer().getGender() == 0) {
                        cm.gainItem(1040183, 1);
                        cm.gainItem(1060171, 1);
                    } else {
                        cm.gainItem(1041186, 1);
                        cm.gainItem(1061195, 1);
                    }
                    cm.gainItem(1003339, 1);
                    cm.gainItem(1072602, 1);
                    cm.gainItem(1082384, 1);
                    
                    cm.gainItem(1362054, 1);
                    break;
                case 510:
                case 520:
                case 1510:
                    cm.gainItem(1003340, 1);
                    cm.gainItem(1052397, 1);
                    cm.gainItem(1072603, 1);
                    cm.gainItem(1082385, 1);
                    cm.gainItem(1482118, 1);
                    cm.gainItem(1492117, 1);
                    break;
                case 3510:
                    cm.gainItem(1003340, 1);
                    cm.gainItem(1052397, 1);
                    cm.gainItem(1072603, 1);
                    cm.gainItem(1082385, 1);
                    cm.gainItem(1492117, 1);
                    break;
                case 530:
                    cm.gainItem(1003340, 1);
                    cm.gainItem(1052397, 1);
                    cm.gainItem(1072603, 1);
                    cm.gainItem(1082385, 1);
                    cm.gainItem(1532053, 1);
                    break;
                default:
                    cm.sendOk("��� ���޹��� �� �ִ� �����ܰ谡 �ƴմϴ�. ������ ������ ������ ���, ������ �Ͻ� �� ��� ���� �� �ֽ��ϴ�.");
                    cm.dispose();
                    return;
                    
            }
	    cm.gainItem(2430451,-1);
	    cm.dispose();
        }
    }
}