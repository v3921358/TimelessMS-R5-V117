var status = 0;    

function start() {    
    status = -1;    
    action(1, 0, 0);    
}    

function action(mode, type, selection) {    
         
    if (mode == -1) {    
        cm.dispose();    
    }    
    else {     
        if (status >= 2 && mode == 0) {     
            cm.sendOk("Alright, come back soon!");     
            cm.dispose();     
            return;     
        }     
            
        if (mode == 1) {    
            status++;    
        }        
        else {    
            status--;    
        }    

        if (status == 0) {    
cm.sendNext("#eHello, I am the #eVote Point NPC Trader.");

} else if (status == 1) {
if (cm.getVPoints() > 0) {
cm.sendSimple("#e#rWhich category would you like?\r\n\r\n#fUI/UIWindow.img/QuestIcon/3/0##b\r\n#L1#Chairs#l\r\n#L4#USE Items#l\r\n#L6#Cash Shop#l\r\n#L7#Taming Mounts#l");
} else  {
cm.sendOk("You need to have #eVote Points#n in order to check out or use this NPC.  While your voting, please make sure you are logged off.");
cm.dispose();
}
} else if (status == 2) {
if (selection == 0) {
cm.sendSimple("#e#rPlease select what you would like?\r\nThese cost 5 VP each.#b\r\n#L7#Diligent Explorer Medal#l\r\n#L8#PQ Mania Medal#l\r\n#L9#Quest Specialist Medal#l\r\n#L10#Celebrity Medal#l\r\n#L11#Legendary Hunter Medal#l\r\n#L12#Maple Idol Medal#l\r\n#L13#Pink Bean Slayer Medal#l");
} else if (selection == 1) {
cm.sendSimple("#e#rPPlease select what you would like? \r\n#L14#Under the Maple Tree - 2VP#l\r\n#L16#Pink Round Chair - 6VP#l\r\n#L17#Moon Star Chair - 8VP#l\r\n#L18#Love Chair - 10VP#l");
} else if (selection == 2) {
cm.sendSimple("#e#rPlease select the amount of NX you would like? \r\n#L19#7k NX - 1VP#l\r\n#L20#15kNX - 2VP#l\r\n#L21#25k NX - 3VP#l\r\n#L22#40k NX - 4VP#l");
} else if (selection == 3) {
cm.sendSimple("#e#rPPlease select what you would like?\r\nThese cost 8VP each.\r\n#L23#Janus Set#l\r\n#L24#Saciel Set");
} else if (selection == 4) {
cm.sendSimple("#e#rPPlease select what you would like? \r\n#L25#2 Chaos scrolls - 2VP#l\r\n#L26#2 White scrolls - 2VP#l\r\n#L27#10 Onyx apples - 4VP#l\r\n#L28#10 Naricain's demon elixirs - 5VP#l\r\n#L29#2 Equip Enhancement Scroll - 1VP#l\r\n#L30#1 Advanced Potential Scroll - 5VP#l");
} else if (selection == 5) {
cm.sendSimple("#e#rPPlease select what you would like? \r\n#L29#200 Stiff Feathers - 1VP#l\r\n#L30#1 Tiru's Feather - 2VP#l\r\n#L31#1 Timu's Feather - 3VP#l\r\n#L32#1 Soft Feather - 5VP#l");
} else if (selection == 6) {
cm.sendSimple("#e#rPlease select what you would like? \r\n#L34#30 Minute Maple Booster 1.5x EXP Coupon - 3VP#l\r\n#L35#Super Miracle Cube [2] - 5VP#l\r\n#L37#30 Minute Maple Booster 2x EXP Coupon - 5VP#l");
} else if (selection == 7) {
cm.sendSimple("#e#rPlease select what you would like? \r\n#L38#Adventurer Taming Mount & Saddle - 8VP#l\r\n#L39#KOC Taming Mount & Saddle - 9VP#l\r\n#L40#Aran Taming Mount & Saddle - 9VP#l\r\n#L41#Scooter Taming Mount & Saddle - 10VP#l\r\n#L42#Mapler Racing Car Taming Mount & Saddle - 10VP#l");
} else if (selection == 8) {
cm.sendSimple("#e#rPPlease select what you would like? \r\n#L35#1 Free Ticket - 1VP#l\r\n#L36#3 Free Ticket - 2VP#l\r\n#L37#5 Free Tickets - 4VP#l");
}
} else if (status == 3) {
if (selection == 7) {
if (cm.getVPoints() > 4) {
 cm.setVotePoints(-5);
cm.gainItem(1142000, 1);
cm.dispose();
} else {
cm.sendOk("Sorry not enough votepoints.");
cm.dispose();
}
}else if (selection == 8) {
if (cm.getVPoints() > 4) {
 cm.setVotePoints(-5);
cm.gainItem(1142001, 1);
cm.dispose();
} else {
cm.sendOk("Sorry not enough votepoints.");
cm.dispose();
}
} else if (selection == 9) {
if (cm.getVPoints() > 4) {
 cm.setVotePoints(-5);
cm.gainItem(1142002, 1);
cm.dispose();
} else {
cm.sendOk("Sorry not enough votepoints.");
cm.dispose();
}
} else if (selection == 10) {
if (cm.getVPoints() > 4) {
 cm.setVotePoints(-5);
cm.gainItem(1142003, 1);
cm.dispose();
} else {
cm.sendOk("Sorry not enough votepoints.");
cm.dispose();
}
} else if (selection == 11) {
if (cm.getVPoints() > 4) {
 cm.setVotePoints(-5);
cm.gainItem(1142005, 1);
cm.dispose();
} else {
cm.sendOk("Sorry not enough votepoints.");
cm.dispose();
}
} else if (selection == 12) {
if (cm.getVPoints() > 4) {
 cm.setVotePoints(-5);
cm.gainItem(1142006, 1);
cm.dispose();
} else {
cm.sendOk("Sorry not enough votepoints.");
cm.dispose();
}
} else if (selection == 13) {
if (cm.getVPoints() > 4) {
 cm.setVotePoints(-5);
cm.gainItem(1142008, 1);
cm.dispose();
} else {
cm.sendOk("Sorry not enough votepoints.");
cm.dispose();
}
} else if (selection == 14) {
if (cm.getVPoints() > 1) {
 cm.setVotePoints(-2);
cm.gainItem(3010036, 1);
cm.dispose();
} else {
cm.sendOk("Sorry not enough votepoints.");
cm.dispose();
}
} else if (selection == 15) {
if (cm.getVPoints() > 3) {
 cm.setVotePoints(-4);
cm.gainItem(1132001, 1);
cm.dispose();
} else {
cm.sendOk("Sorry not enough votepoints.");
cm.dispose();
}
} else if (selection == 16) {
if (cm.getVPoints() > 5) {
 cm.setVotePoints(-6);
cm.gainItem(3010009, 1);
cm.dispose();
} else {
cm.sendOk("Sorry not enough votepoints.");
cm.dispose();
}
} else if (selection == 17) {
if (cm.getVPoints() > 4) {
 cm.setVotePoints(-8);
cm.gainItem(3010014, 1);
cm.dispose();
} else {
cm.sendOk("Sorry not enough votepoints.");
cm.dispose();
}
} else if (selection == 18) {
if (cm.getVPoints() > 9) {
 cm.setVotePoints(-10);
cm.gainItem(3010191, 1);
cm.dispose();
} else {
cm.sendOk("Sorry not enough votepoints.");
cm.dispose();
}
} else if (selection == 19){
                if (cm.getVPoints() > 0) {
                    cm.setVotePoints(-1);
                    cm.modifyNX(7000, 1);
                    cm.dispose();
                } else {
                    cm.sendOk("Sorry not enough votepoints.");
                    cm.dispose();
                }

} else if (selection == 20){
                if (cm.getVPoints() > 0) {
                    cm.setVotePoints(-2);
                    cm.modifyNX(15000, 1);
                    cm.dispose();
                } else {
                    cm.sendOk("Sorry not enough votepoints.");
                    cm.dispose();
                }

} else if (selection == 21){
                if (cm.getVPoints() > 0) {
                    cm.setVotePoints(-3);
                    cm.modifyNX(25000, 1);
                    cm.dispose();
                } else {
                    cm.sendOk("Sorry not enough votepoints.");
                    cm.dispose();
                }

} else if (selection == 22){
                if (cm.getVPoints() > 0) {
                    cm.setVotePoints(-4);
                    cm.modifyNX(40000, 1);
                    cm.dispose();
                } else {
                    cm.sendOk("Sorry not enough votepoints.");
                    cm.dispose();
                }

} else if (selection == 23) {
if (cm.getVPoints() > 7) {
 cm.setVotePoints(-8);
cm.gainItem(1000032, 1);
cm.gainItem(1001047, 1);
cm.gainItem(1052093, 1);
cm.gainItem(1102097, 1);
cm.gainItem(1072283, 1);
cm.gainItem(1702118, 1);
cm.dispose();
} else {
cm.sendOk("Sorry not enough votepoints.");
cm.dispose();
}
} else if (selection == 24) {
if (cm.getVPoints() > 7) {
 cm.setVotePoints(-8);
cm.gainItem(1001045, 1);
cm.gainItem(1000030, 1);
cm.gainItem(1052091, 1);
cm.gainItem(1102096, 1);
cm.gainItem(1072281, 1);
cm.gainItem(1702119, 1);
cm.dispose();
} else {
cm.sendOk("Sorry not enough votepoints.");
cm.dispose();
}
} else if (selection == 25) {
if (cm.getVPoints() > 1) {
 cm.setVotePoints(-2);
cm.gainItem(2049100, 2);
cm.dispose();
} else {
cm.sendOk("Sorry not enough votepoints.");
cm.dispose();
}
} else if (selection == 26) {
if (cm.getVPoints() > 1) {
 cm.setVotePoints(-2);
cm.gainItem(2340000, 2);
cm.dispose();
} else {
cm.sendOk("Sorry not enough votepoints.");
cm.dispose();
}
} else if (selection == 27) {
if (cm.getVPoints() > 3) {
 cm.setVotePoints(-4);
cm.gainItem(2022179, 10);
cm.dispose();
} else {
cm.sendOk("Sorry not enough votepoints.");
cm.dispose();
}
} else if (selection == 28) {
if (cm.getVPoints() > 4) {
 cm.setVotePoints(-5);
cm.gainItem(2022282, 10);
cm.dispose();
} else {
cm.sendOk("Sorry not enough votepoints.");
cm.dispose();
}
} else if (selection == 29) {
if (cm.getVPoints() > 0) {
 cm.setVotePoints(-1);
cm.gainItem(2049307, 2);
cm.dispose();
} else {
cm.sendOk("Sorry not enough votepoints.");
cm.dispose();
}
} else if (selection == 30) {
if (cm.getVPoints() > 9) {
 cm.setVotePoints(-10);
cm.gainItem(2049400, 2);
cm.dispose();
} else {
cm.sendOk("Sorry not enough votepoints.");
cm.dispose();
}
} else if (selection == 31) {
if (cm.getVPoints() > 2) {
 cm.setVotePoints(-3);
cm.gainItem(4000484, 1);
cm.dispose();
} else {
cm.sendOk("Sorry not enough votepoints.");
cm.dispose();
}
} else if (selection == 32) {
if (cm.getVPoints() > 4) {
 cm.setVotePoints(-5);
cm.gainItem(4003005, 1);
cm.dispose();
} else {
cm.sendOk("Sorry not enough votepoints.");
cm.dispose();
}
} else if (selection == 33) {
if (cm.getVPoints() > 11) {
 cm.setVotePoints(-12);
cm.gainItem(4000313, 1);
cm.dispose();
} else {
cm.sendOk("Sorry not enough votepoints.");
cm.dispose();
}
} else if (selection == 34){
if (cm.getVPoints() > 2) {
 cm.setVotePoints(-3);
cm.gainItem(5530063, 1);
} else {
cm.sendOk("Sorry you do not have enough votepoints.");
cm.dispose();
}
} else if (selection == 35){
if (cm.getVPoints() > 3) {
 cm.setVotePoints(-4);
cm.gainItem(5062002, 2);
} else {
cm.sendOk("Sorry you do not have enough votepoints.");
cm.dispose();
}
} else if (selection == 36){
if (cm.getVPoints() > 3) {
 cm.setVotePoints(-4);
cm.gainItem(5221000, 1);
} else {
cm.sendOk("Sorry you do not have enough votepoints.");
cm.dispose();
}
} else if (selection == 37){
if (cm.getVPoints() > 4) {
 cm.setVotePoints(-5);
cm.gainItem(5530062, 2);
} else {
cm.sendOk("Sorry you do not have enough votepoints.");
cm.dispose();
}
} else if (selection == 38) {
if (cm.getPlayer().getVPoints() > 7) {      
 cm.getPlayer().gainvpoints(-8);                    
 cm.gainItem(1902000, 1);
 cm.gainItem(1902001, 1);
 cm.gainItem(1902002, 1);
 cm.gainItem(1912000, 1);
 cm.dispose();
} else {
 cm.sendOk("Sorry you do not have enough votepoints.");
 cm.dispose();
}
} else if (selection == 39) {
if (cm.getPlayer().getVPoints() > 8) {      
 cm.getPlayer().gainvpoints(-9);                    
 cm.gainItem(1902005, 1);
 cm.gainItem(1902006, 1);
 cm.gainItem(1902007, 1);
 cm.gainItem(1912005, 1);
 cm.dispose();
} else {
 cm.sendOk ("Sorry you do not have enough votepoints.");
 cm.dispose();
}
} else if (selection == 40) {
if (cm.getPlayer().getVPoints() > 8) {      
 cm.getPlayer().gainvpoints(-9);                    
 cm.gainItem(1902015, 1);
 cm.gainItem(1902016, 1);
 cm.gainItem(1902017, 1);
 cm.gainItem(1902018, 1);
 cm.gainItem(1912011, 1);
 cm.dispose();
} else {
 cm.sendOk ("Sorry you do not have enough votepoints.");
 cm.dispose();
}
} else if (selection == 41) {
if (cm.getPlayer().getVPoints() > 9) {      
 cm.getPlayer().gainvpoints(-10);                    
 cm.gainItem(1912029, 1);
 cm.gainItem(1912032, 1);
 cm.gainItem(1902038, 1);
 cm.gainItem(1902039, 1);
 cm.dispose();
} else {
 cm.sendOk ("Sorry you do not have enough votepoints.");
 cm.dispose();
}
} else if (selection == 42) {
if (cm.getPlayer().getVPoints() > 9) {      
 cm.getPlayer().gainvpoints(-10);                    
 cm.gainItem(1902036, 1);
 cm.gainItem(1912029, 1);
 cm.dispose();
} else {
 cm.sendOk ("Sorry you do not have enough votepoints.");
 cm.dispose();
}
}
}
}
}