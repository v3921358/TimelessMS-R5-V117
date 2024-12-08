var status;
var cstype;
var hasEnough;
var nx;

function start() {
    status = -1;
    action(1,0,0);
}

function action(mode, type, selection) {
    if (mode != 1) {
        cm.dispose();
        return;
    } else
        status++;

    if (status == 0)
        cm.sendNext("I am a name-changing NPC.");
    else if (status == 1) {
        if (cm.getPlayer().getLevel() < 30) {
            cm.sendOk("Sorry, but I'm only available to those over level 30.");
            cm.dispose();
            return;
        } else 
            cm.sendYesNo("Would you like to change your name? The cost is 30,000 NX cash.");
    } else if (status == 2) {
        for (cstype = 1; cstype <= 4; cstype++) {
            if (cstype == 3)
                continue;
            if (hasEnough)
                break;
            else {
                hasEnough = calculate(30000, cstype);
            }
        } if (!hasEnough) {
            cm.sendOk("Sorry, you don't have enough NX cash!");
            cm.dispose();
            return;
        } else 
            cm.sendGetText("What would you like your name to be?\r\n#eNO SPECIAL CHARACTERS #n(as in numbers)#e, OR DIE#n\r\nBe #e#rSUPER#k#n careful typing, no re-dos!");
    } else if (status == 3) {
        var name = cm.getText();
        if (cm.hasChangedName(name))
            cm.sendOk("You must wait until the server restart to change your name again!");
        else if (cm.ifNameExist(name))
            cm.sendNext("That name already exists or it has special characters. Please pick another.");
        else {
            cm.doNameChange(name);
            cm.gainNX(--cstype, -30000, true);
            cm.sendOk("Your name has been changed to ''" + name + "''.\r\nYour name will be changed when you relog.\r\nRemember, if you incorrectly typed it, you're out of luck.");
        }
        cm.dispose();
        return;
    }
}

function calculate(cost, cstype) {
    nx = cm.getNX(cstype);
    if (cost <= nx)
        return true;
    else
        return false;
}  