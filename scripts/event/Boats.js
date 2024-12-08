importPackage(Packages.client);
importPackage(Packages.packet.creators);
importPackage(Packages.server.life);
importPackage(Packages.tools);
importPackage(java.lang);

//Time Setting is in millisecond
var closeTime = 240000; //The time to close the gate
var beginTime = 300000; //The time to begin the ride
var rideTime = 600000; //The time that require move to destination
var invasionTime = 60000; //The time that spawn balrog
var Orbis_btf;
var Boat_to_Orbis;
var Orbis_Boat_Cabin;
var Orbis_docked;
var Ellinia_btf;
var Ellinia_Boat_Cabin;
var Ellinia_docked;
var Orbis_Station;

function init() {
    Orbis_btf = em.getChannelServer().getMapFactory().getMap(200000112);
    Ellinia_btf = em.getChannelServer().getMapFactory().getMap(104020111);
    Ellinia_docked = em.getChannelServer().getMapFactory().getMap(104020110);
    Boat_to_Orbis = em.getChannelServer().getMapFactory().getMap(200090010);
    Orbis_Boat_Cabin = em.getChannelServer().getMapFactory().getMap(200090011);
    Boat_to_Ellinia = em.getChannelServer().getMapFactory().getMap(200090000);
    Ellinia_Boat_Cabin = em.getChannelServer().getMapFactory().getMap(200090001);
    Orbis_docked = em.getChannelServer().getMapFactory().getMap(200000111);
    Orbis_Station = em.getChannelServer().getMapFactory().getMap(200000100);
    em.setProperty("docked", "false");
    em.setProperty("entry", "false");
    em.setProperty("haveBalrog","false");
    var time = CurrentTime.getLeftTimeFromMinute(15) * 1000;
    em.schedule("scheduleNew", time);
}

function scheduleNew() {
    em.setProperty("docked", "true");
    em.setProperty("entry", "true");
    em.setProperty("haveBalrog","false");
    em.schedule("stopentry", closeTime);
    em.schedule("takeoff", beginTime);
    
    Ellinia_btf.setMapTimerNotDB(System.currentTimeMillis() + beginTime);
    Orbis_btf.setMapTimerNotDB(System.currentTimeMillis() + beginTime);
    
    Orbis_docked.broadcastMessage(MainPacketCreator.boatPacket(1548));
    Ellinia_docked.broadcastMessage(MainPacketCreator.boatPacket(1548));
}

function stopentry() {
    em.setProperty("entry","false");
    Orbis_Boat_Cabin.resetReactors();
    Ellinia_Boat_Cabin.resetReactors();
}

function takeoff() {
    em.setProperty("docked","false");
    
    Orbis_Boat_Cabin.setMapTimerNotDB(System.currentTimeMillis() + rideTime);
    Ellinia_Boat_Cabin.setMapTimerNotDB(System.currentTimeMillis() + rideTime);
    Boat_to_Orbis.setMapTimerNotDB(System.currentTimeMillis() + rideTime);
    Boat_to_Ellinia.setMapTimerNotDB(System.currentTimeMillis() + rideTime);
    
    var temp1 = Orbis_btf.getCharacters().iterator();
    while(temp1.hasNext()) {
        temp1.next().changeMap(Boat_to_Ellinia, Boat_to_Ellinia.getPortal(0));
    }
    var temp2 = Ellinia_btf.getCharacters().iterator();
    while(temp2.hasNext()) {
        temp2.next().changeMap(Boat_to_Orbis, Boat_to_Orbis.getPortal(0));
    }
    em.schedule("invasion", invasionTime);
    em.schedule("arrived", rideTime);
    
    Orbis_docked.broadcastMessage(MainPacketCreator.boatPacket2(3));
    Ellinia_docked.broadcastMessage(MainPacketCreator.boatPacket2(3));
}

function arrived() {
    var temp1 = Boat_to_Orbis.getCharacters().iterator();
    while(temp1.hasNext()) {
        temp1.next().changeMap(Orbis_Station, Orbis_Station.getPortal(0));
    }
    var temp2 = Orbis_Boat_Cabin.getCharacters().iterator();
    while(temp2.hasNext()) {
        temp2.next().changeMap(Orbis_Station, Orbis_Station.getPortal(0));
    }
    var temp3 = Boat_to_Ellinia.getCharacters().iterator();
    while(temp3.hasNext()) {
        temp3.next().changeMap(Ellinia_docked, Ellinia_docked.getPortal(0));
    }
    var temp4 = Ellinia_Boat_Cabin.getCharacters().iterator();
    while(temp4.hasNext()) {
        temp4.next().changeMap(Ellinia_docked, Ellinia_docked.getPortal(0));
    }
    Boat_to_Orbis.killAllMonsters(true);
    Boat_to_Ellinia.killAllMonsters(true);
    scheduleNew();
}

function invasion() {
    var numspawn;
    var chance = Math.floor(Math.random() * 10);
    if (chance <= 5)
        numspawn = 0;
    else
        numspawn = 2;
    if (numspawn > 0) {
        for (var i=0; i < numspawn; i++) {
            Boat_to_Orbis.spawnMonsterOnGroundBelow(AthenaLifeProvider.getMonster(8150000), new java.awt.Point(485, -221));
            Boat_to_Ellinia.spawnMonsterOnGroundBelow(AthenaLifeProvider.getMonster(8150000), new java.awt.Point(-590, -221));
        }
        Boat_to_Orbis.broadcastMessage(MainPacketCreator.boatPacket(1034));
        Boat_to_Ellinia.broadcastMessage(MainPacketCreator.boatPacket(1034));
        Boat_to_Orbis.broadcastMessage(MainPacketCreator.musicChange("Bgm04/ArabPirate"));
        Boat_to_Ellinia.broadcastMessage(MainPacketCreator.musicChange("Bgm04/ArabPirate"));
        em.setProperty("haveBalrog","true");
    }
}
//
//function OBoatsetup() {
//    em.getChannelServer().getMapFactory().getMap(200090011).getPortal("out00").setScriptName("OBoat1");
//    em.getChannelServer().getMapFactory().getMap(200090011).getPortal("out01").setScriptName("OBoat2");
//}
//
//function EBoatsetup() {
//    em.getChannelServer().getMapFactory().getMap(200090001).getPortal("out00").setScriptName("EBoat1");
//    em.getChannelServer().getMapFactory().getMap(200090001).getPortal("out01").setScriptName("EBoat2");
//}

function cancelSchedule() {
}