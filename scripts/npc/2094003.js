/*
GM Roar
Made By iTzLanders
*/
var status = 0;

function start() {
cm.sendSimple (
"I teach you a GM JOB and you can be when you want for 1000 rebirths, just talk to me when you're ready!\r\n#L0#Buy a GM Job."
);
}

function action(mode, type, selection) {
if (selection == 0) {
if (cm.getPlayer().getReborns() > 1000) {
cm.changeJobById(900);
cm.dispose();
} else {
cm.sendOk("You do not have the requirements!");
cm.dispose();
}
}
}