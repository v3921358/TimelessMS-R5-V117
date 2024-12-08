/*
 * 히나온라인 소스 스크립트 입니다.
 * Translated / Recoded by JakeK from AthenaMS .
 */

function enter(pi) {
    var eim = pi.getPlayer().getEventInstance();
    if (eim.getProperty("choiceCave") == null) {
        pi.getPlayer().message(5, "You have not yet selected the cave.");
        return false;
    }
    if (eim.getProperty("choiceCave").equals("0")) {
        pi.allPartyWarp(240050300, false);
    } else {
        pi.allPartyWarp(240050310, false);
    }
    return true;
}