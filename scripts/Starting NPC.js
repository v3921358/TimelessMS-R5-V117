/* Author: Burblish
 * ID: Unknown
 * Name: Unknown
 * Function: Job Advance Screen
 */
function start() {
    cm.sendSimple ("Chose a Category.#k\r\n#L0##rLev. 10 Job Advance#k\r\n#L1##rLev. 30 Job Advance#k\r\n#L2##rLev. 70 Job Advance#k\r\n#L3##rLev. 120 Job Advance#k");
}

function action(mode, type, selection) {
    cm.dispose();
    if (selection == 0) {
        cm.openNpc(9010038);
      } else if (selection == 1) {
        cm.openNpc (1012124);
      } else if (selection == 2) {
        cm.openNpc (9330026);
      } else if (selection == 3) {
        cm.openNpc (9201157);
      } else if (selection == 4) {
        cm.openNpc (9201022);
      } else if (selection == 5) {
        cm.openNpc (1012117);
    } else {
        cm.dispose();
    }
}  