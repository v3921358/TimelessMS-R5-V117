


/*

	히나 온라인 소스 스크립트입니다.

	미니던전 스크립트


*/

importPackage(java.lang);
importPackage(java.awt);
importPackage(Packages.tools.RandomStream);

function init() {
    
}

function setup(eim) {
    var eim = em.newInstance("MiniDungeon_"+Randomizer.nextInt());
    return eim;
}

function playerEntry(eim, player) {
    var id = -1;
    if (player.getParty() != null) {
        id = player.getParty().getLeader().getId();
    } else {
        id = player.getId();
    }
    
    var mapid = em.getProperty("Leader_"+id+"_Map");
    var mapzz = eim.getMapFactory().getMap(mapid);
    player.changeMap(mapzz, mapzz.getPortal("sp"));
}

function changedMap(eim, player, mapid) {
    eim.unregisterPlayer(player);
    if (eim != null) {
        if (eim.getPlayerCount() <= 0) {
            eim.dispose();
        }
    }
}

function scheduledTimeout(eim) {
    var id = -1;
    if (eim.getPlayers().get(0).getParty() != null) {
        id = eim.getPlayers().get(0).getParty().getLeader().getId();
    } else {
        id = eim.getPlayers().get(0).getId();
    }
    var exitmapid = eim.getProperty("Leader_"+id+"_Exit");
    var exit = em.getChannelServer().getMapFactory().getMap(exitmapid);
    var it = eim.getPlayers().iterator();
    while (it.hasNext()) {
        var chr = eim.getPlayers().iterator().next();
        eim.removePlayer(chr);
        chr.changeMap(exit, exit.getPortal(0));
        chr.Message(8, "시간이 초과되었습니다. 처음부터 다시 시작해주세요.");
    }
    eim.dispose();
}

function allMonstersDead(eim) {
    
}

function playerDead(eim, player) {
    eim.unregisterPlayer(player);
    if (eim != null) {
        if (eim.getPlayerCount() <= 0) {
            eim.dispose();
        }
    }
}

function playerRevive(eim, player) {
    
}

function playerDisconnected(eim, player) {
    /* 0 : 모두 나갈때 까지는 인스턴스 유지
     * 1 ~ : 일정 수준 이상의 사람만 남으면 누가 나가던지 인스턴스 유지
     * -1 ~ 이하 : 일정 수준 이상의 사람만 남으면 유지이나, 파티장이 나가면 인스턴스 삭제
     */
    return 0;
}

function monsterValue(eim, mobid) {
    /*
     * 킬 카운트를 더할 숫자 반환
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
    if (eim != null) {
        if (eim.getPlayerCount() <= 0) {
            eim.dispose();
        }
    }
}

function onMapLoad(eim, player) {
    
}

function cancelSchedule(a) {
    
}