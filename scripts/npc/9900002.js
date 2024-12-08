importPackage(java.lang);
function start() {
    cm.sendSimple ("#k\r\n#L0##rMax Skill (ALL SKILLS)#k\r\n#L1##rReset Stats#k\r\n#L2##rMaxSkills (BY JOB NOT ALL)#k\r\n#L3##rClear Skills#k\r\n#L4##rSetAP/SP to 0#k\r\n#L5##rClear Skills#k");
}

function action(mode, type, selection) {
    cm.dispose();
    if (selection == 0) {
      cm.sendOk("All of your skills are maxed now. Enjoy TimelessMS");
      cm.maxAllSkills();
      cm.dispose();
      } else if (selection == 1) {
      cm.getPlayer().resetStats(4, 4, 4, 4);
      cm.sendOk("I have cleared your stats. Enjoy TimelessMS");
      cm.dispose();
      } else if (selection == 2) {
      cm.sendOk("All your jobs skills are maxed! Enjoy TimelessMS");
      cm.maxSkillsByJob();
      cm.dispose(); 
      } else if (selection == 3) {
      cm.sendOk("I have cleared your skills. Enjoy TimelessMS");
      cm.clearSkills();
      cm.dispose(); 
      } else if (selection == 4) {
      cm.sendOk("I have cleared your AP/SP. Enjoy TimelessMS");
      cm.getPlayer().resetAPSP();
      cm.dispose(); 
    }
}  