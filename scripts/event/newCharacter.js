


/*

	���� �¶��� �ҽ� ��ũ��Ʈ�Դϴ�.

	�ʺ� ������ ��ũ��Ʈ


*/

importPackage(Packages.packet.creators);
importPackage(Packages.server.life);
importPackage(java.lang);
importPackage(java.awt);
importPackage(Packages.tools.RandomStream);
var mapidnum = -1;


function init() {
    
}

function setup(eim) {
    var eim = em.newInstance("newCharacter");
    eim.startEventTimer(300000);
    eim.setProperty("level", "0");
    return eim;
}

function playerEntry(eim, player) {
    player.send(UIPacket.showInfo("�ʱ� �����忡 ���Ű��� ȯ���մϴ�! �־��� ���͸� �����ð����� óġ�Ͻø� �˴ϴ�."));
    player.send(MainPacketCreator.getGMText(7, "�ʱ� �����忡 ���Ű��� ȯ���մϴ�! �־��� ���͸� �����ð����� óġ�Ͻø� �˴ϴ�."));
    eim.schedule("announce", 5000);
    var mapzz = eim.getMapFactory().getMap(209000003);
    player.changeMap(mapzz, mapzz.getPortal("sp"));
    player.send(UIPacket.AchievementRatio(0));
}

function announce(eim) {
    var text = "5�� �� ���Ͱ� ��ȯ�˴ϴ�.";
    eim.getMapFactory().getMap(209000003).broadcastMessage(UIPacket.showInfo(text));
    eim.getMapFactory().getMap(209000003).broadcastMessage(MainPacketCreator.getGMText(7, text));
    var level = Integer.parseInt(eim.getProperty("level"));
    if (level == 0) {
        eim.schedule("summon", 5000);
    } else if (level == 1) {
        eim.schedule("summon", 5000);
    } else if (level == 2) {
        eim.schedule("summon", 5000);
    } else if (level == 3) {
        eim.schedule("summon", 5000);
    }
}

function summon(eim) {
    var text = "���Ͱ� ��ȯ�Ǿ����ϴ�!";
    eim.getMapFactory().getMap(209000003).broadcastMessage(UIPacket.showInfo(text));
    eim.getMapFactory().getMap(209000003).broadcastMessage(MainPacketCreator.getGMText(5, text)); //-109,154
    var mobid = 0;
    var level = Integer.parseInt(eim.getProperty("level"));
    if (level == 0) {
        mobid = 100100;
    } else if (level == 1) {
        mobid = 100122;
    } else if (level == 2) {
        mobid = 210100;
    } else if (level == 3) {
        mobid = 100124;
    }
    for (var i = 0 ; i < 10 ; i++) {
        var mob = AthenaLifeProvider.getMonster(mobid);
        eim.registerMonster(mob);
        eim.getMapFactory().getMap(209000003).spawnMonsterOnGroundBelow(mob, new Point(Randomizer.rand(-300, 300), 154));
    }
    level++;
    eim.setProperty("level", level);
}

function changedMap(eim, player, mapid) {
    
}

function scheduledTimeout(eim) {
    var exit = em.getChannelServer().getMapFactory().getMap(209000001);
    var chr = eim.getPlayers().iterator().next();
    chr.changeMap(exit, exit.getPortal(0));
    chr.Message(8, "�ð��� �ʰ��Ǿ����ϴ�. ó������ �ٽ� �������ּ���.");
    eim.unregisterPlayer(chr);
    eim.dispose();
}

function allMonstersDead(eim) {
    var level = Integer.parseInt(eim.getProperty("level"));
    if (level >= 1 && level <= 3) {
        var text = "���� "+level+"�� ���͸� ��� �����̽��ϴ�! ��� �� ���� ������ �Ѿ�ϴ�.";
        eim.getMapFactory().getMap(209000003).broadcastMessage(UIPacket.showInfo(text));
        eim.getMapFactory().getMap(209000003).broadcastMessage(MainPacketCreator.showEffect("monsterPark/clear"));
        eim.getMapFactory().getMap(209000003).broadcastMessage(MainPacketCreator.playSound("Party1/Clear"));
        eim.getMapFactory().getMap(209000003).broadcastMessage(MainPacketCreator.serverNotice(6, text));
        eim.getMapFactory().getMap(209000003).broadcastMessage(UIPacket.AchievementRatio(25 * level));
        eim.schedule("announce", 5000);
        var chr = eim.getPlayers().iterator().next();
        chr.gainExp(level * 330, true, true, true);
    }
    if (level == 4) {
        var text = "������ ��� �ϼ��ϼ̽��ϴ�! �����մϴ�.";
        var exit = em.getChannelServer().getMapFactory().getMap(100000000);
        var chr = eim.getPlayers().iterator().next();
        eim.getMapFactory().getMap(209000003).broadcastMessage(UIPacket.showInfo(text));
        eim.getMapFactory().getMap(209000003).broadcastMessage(MainPacketCreator.getClock(20));
        eim.getMapFactory().getMap(209000003).broadcastMessage(MainPacketCreator.showEffect("monsterPark/clearF"));
        eim.getMapFactory().getMap(209000003).broadcastMessage(MainPacketCreator.playSound("Party1/Clear"));
        eim.getMapFactory().getMap(209000003).broadcastMessage(MainPacketCreator.serverNotice(6, text));
        eim.getMapFactory().getMap(209000003).broadcastMessage(UIPacket.AchievementRatio(100));
        
        var startTime = (chr.getKeyValue2("1stTrialStartTime"));
        chr.setKeyValue2("1stJobTrialCompleteTime", ((System.currentTimeMillis() / 1000) - startTime));
        chr.gainExp(3000, true, true, true);
        chr.gainExp(10, true, true, true);
        chr.gainItem(1142126, 1, false, -1, "�ʱ� �����忡�� ���� ������");
        chr.gainItem(2000002, 100, false, -1, "�ʱ� �����忡�� ���� ������");
        chr.gainItem(2000006, 70, false, -1, "�ʱ� �����忡�� ���� ������");
        chr.gainItem(2022000, 40, false, -1, "�ʱ� �����忡�� ���� ������");
        chr.setKeyValue("start_complete", "1");
        chr.changeMap(exit, exit.getPortal(0));
        chr.Message(1, "�����>> ��...���� �ʿ��ϽŰ���.....? ������.. ���ּ���..");
        eim.unregisterPlayer(chr);
        eim.dispose();
    }
}

function playerDead(eim, player) {
    eim.unregisterPlayer(player);
    //if (eim.getPlayerCount() <= 0) {
        eim.dispose();
    //}
}

function playerRevive(eim, player) {
    
}

function playerDisconnected(eim, player) {
    /* 0 : ��� ������ ������ �ν��Ͻ� ����
     * 1 ~ : ���� ���� �̻��� ����� ������ ���� �������� �ν��Ͻ� ����
     * -1 ~ ���� : ���� ���� �̻��� ����� ������ �����̳�, ��Ƽ���� ������ �ν��Ͻ� ����
     */
    return 0;
}

function monsterValue(eim, mobid) {
    /*
     * ų ī��Ʈ�� ���� ���� ��ȯ
     */
    return 1;
}

function leftParty(eim, player) {
    
}

function disbandParty(eim) {
    
}

function clearPQ(eim) {
}

function playerExit(eim, player) {
    eim.unregisterPlayer(player);
    eim.dispose();
}

function onMapLoad(eim, player) {
    
}

function cancelSchedule(a) {
    
}