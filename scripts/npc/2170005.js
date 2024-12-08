var wui = 0;

function start() {
    cm.sendSimple ("#eHello! I'm the #bInformation NPC#k.#n\r\n\r\n#L0# #rRebirth System Info#k #l\r\n#L4# #bMax my Skills! More info coming soon.")
}

function action(mode, type, selection) {
cm.dispose();
    if (selection == 0) {
cm.sendOk("Welcome to the #rTIMELESSMS Rebirth information#k. \r\n When you finally achieve level 200 you can finally rebirth. When you rebirth your level will be reset back to level 1, but at the same time you won't lose any of your skills, it's pretty much just a fresh start with better advantages. \r\n \r\n #r@rebirthhelp - Shows Information about rebirthing.#k ");
cm.dispose();

       } else if (selection == 1) {
cm.sendOk("Welcome to the TIMELESSMS MSI-guide!\r\n \r\n Talk to #rAssistant Red.");
cm.dispose();

      } else if (selection == 2) {
cm.sendOk(" Welcome to #e#bSTARMS bank system#k. \r\n @tetris (Exchanges 2bil for a blue tetris piece). \r\n Type @mesoz to exchange one Tetris into 2bil mesos.");
        cm.dispose();
		
		} else if (selection == 3) {
		cm.sendOk("Not done...");
		cm.dispose();
        }   else if (selection == 4) {
			cm.maxAllSkills(); 
		cm.dispose();
        }
    
}  
