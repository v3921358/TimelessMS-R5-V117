


/*

	���� �¶��� �ҽ� ���� ��ũ��Ʈ �Դϴ�.

        ���� : Ƽ��

	���ǽþ��̵� : 
	
	���ǽ� �̸� :

	���ǽð� �ִ� �� : 

	���ǽ� ���� : 


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
            cm.sendOk("�κ��丮 ������ �ּ��� 5ĭ�� �ʿ��մϴ�. �Һ� ���� ������ 2ĭ�̻� ����ֽ� �� �ٽ� �����ּ���.");
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

