/*
This file is part of the OdinMS Maple Story Server.
Copyright (C) 2008 ~ 2012 OdinMS

Copyright (C) 2011 ~ 2012 TimelessMS

Patrick Huy <patrick.huy@frz.cc> 
Matthias Butz <matze@odinms.de>
Jan Christian Meyer <vimes@odinms.de>

Burblish <burblish@live.com> (DO NOT RELEASE SOMEWHERE ELSE)

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License version 3
as published by the Free Software Foundation. You may not use, modify
or distribute this program under any other version of the
GNU Affero General Public License.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package constants;

import client.Skill;
import client.SkillEntry;
import client.SkillFactory;
import database.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Burblish
 */
public class JobConstants {

	private static final Map<Integer, SkillEntry> fixedSkill = new HashMap<>();

	public static void insertNewSkills() {
		//Way better than just adding every single skills in the game.
		//Doing so will ensure that only the right skills are added and
		//not stupid beginners skills that i don't want people to have.
		//More control hover the maxed skills.

		List<Skill> warriorSkills = new ArrayList<>();
		List<Skill> mageSkills = new ArrayList<>();
		List<Skill> archerSkills = new ArrayList<>();
		List<Skill> thiefSkills = new ArrayList<>();
		List<Skill> pirateSkills = new ArrayList<>();
		List<Skill> gmSkills = new ArrayList<>();

		for (Skill skill : SkillFactory.getAllSkills()) {
			if (skill.canBeLearnedBy(100)
					|| skill.canBeLearnedBy(110)
					|| skill.canBeLearnedBy(111)
					|| skill.canBeLearnedBy(112)
					|| skill.canBeLearnedBy(120)
					|| skill.canBeLearnedBy(121)
					|| skill.canBeLearnedBy(122)
					|| skill.canBeLearnedBy(130)
					|| skill.canBeLearnedBy(131)
					|| skill.canBeLearnedBy(132)
					|| skill.canBeLearnedBy(1100)
					|| skill.canBeLearnedBy(1110)
					|| skill.canBeLearnedBy(1111)
					|| skill.canBeLearnedBy(1112)
					|| skill.canBeLearnedBy(2100)
					|| skill.canBeLearnedBy(2110)
					|| skill.canBeLearnedBy(2111)
					|| skill.canBeLearnedBy(3100)
					|| skill.canBeLearnedBy(3110)
					|| skill.canBeLearnedBy(3111)
					|| skill.canBeLearnedBy(3112)) {
				warriorSkills.add(skill);
			} else if (skill.canBeLearnedBy(200) || skill.canBeLearnedBy(210) || skill.canBeLearnedBy(211) || skill.canBeLearnedBy(212) || skill.canBeLearnedBy(220) || skill.canBeLearnedBy(221) || skill.canBeLearnedBy(222) || skill.canBeLearnedBy(230) || skill.canBeLearnedBy(231) || skill.canBeLearnedBy(232) || skill.canBeLearnedBy(1200) || skill.canBeLearnedBy(1210) || skill.canBeLearnedBy(1211) || skill.canBeLearnedBy(1212) || skill.canBeLearnedBy(2200) || skill.canBeLearnedBy(2210) || skill.canBeLearnedBy(2211) || skill.canBeLearnedBy(2213) || skill.canBeLearnedBy(2214) || skill.canBeLearnedBy(2215) || skill.canBeLearnedBy(2216) || skill.canBeLearnedBy(2217) || skill.canBeLearnedBy(2218) || skill.canBeLearnedBy(3200) || skill.canBeLearnedBy(3210) || skill.canBeLearnedBy(3211) || skill.canBeLearnedBy(3212) && !BlockedSkills.isBlockedSkill(skill.getId())) {
				mageSkills.add(skill);
			} else if (skill.canBeLearnedBy(300) || skill.canBeLearnedBy(310) || skill.canBeLearnedBy(31) || skill.canBeLearnedBy(312) || skill.canBeLearnedBy(320) || skill.canBeLearnedBy(321) || skill.canBeLearnedBy(322) || skill.canBeLearnedBy(1300) || skill.canBeLearnedBy(1310) || skill.canBeLearnedBy(1311) || skill.canBeLearnedBy(1312) || skill.canBeLearnedBy(2300) || skill.canBeLearnedBy(2310) || skill.canBeLearnedBy(2311) || skill.canBeLearnedBy(2312) || skill.canBeLearnedBy(3300) || skill.canBeLearnedBy(3310) || skill.canBeLearnedBy(3311) || skill.canBeLearnedBy(3312) && !BlockedSkills.isBlockedSkill(skill.getId())) {
				archerSkills.add(skill);
			} else if (skill.canBeLearnedBy(400) || skill.canBeLearnedBy(410) || skill.canBeLearnedBy(411) || skill.canBeLearnedBy(412) || skill.canBeLearnedBy(420) || skill.canBeLearnedBy(421) || skill.canBeLearnedBy(422) || skill.canBeLearnedBy(430) || skill.canBeLearnedBy(431) || skill.canBeLearnedBy(432) || skill.canBeLearnedBy(433) || skill.canBeLearnedBy(434) || skill.canBeLearnedBy(1400) || skill.canBeLearnedBy(1410) || skill.canBeLearnedBy(1411) || skill.canBeLearnedBy(1412) && !BlockedSkills.isBlockedSkill(skill.getId())) {
				thiefSkills.add(skill);
			} else if (skill.canBeLearnedBy(500) || skill.canBeLearnedBy(501) || skill.canBeLearnedBy(510) || skill.canBeLearnedBy(511) || skill.canBeLearnedBy(512) || skill.canBeLearnedBy(520) || skill.canBeLearnedBy(521) || skill.canBeLearnedBy(522) || skill.canBeLearnedBy(530) || skill.canBeLearnedBy(531) || skill.canBeLearnedBy(532) || skill.canBeLearnedBy(1500) || skill.canBeLearnedBy(1510) || skill.canBeLearnedBy(1511) || skill.canBeLearnedBy(1512) || skill.canBeLearnedBy(3500) || skill.canBeLearnedBy(3510) || skill.canBeLearnedBy(3511) || skill.canBeLearnedBy(3512) && !BlockedSkills.isBlockedSkill(skill.getId())) {
				pirateSkills.add(skill);
			} else if (skill.canBeLearnedBy(900) || skill.canBeLearnedBy(910) && !BlockedSkills.isBlockedSkill(skill.getId())) {
				gmSkills.add(skill);
			} else {
				continue;
			}
		}

		gmSkills.add(SkillFactory.getSkill(20021004));
		gmSkills.add(SkillFactory.getSkill(20021061));

		Connection con = DatabaseConnection.getConnection();
		try (PreparedStatement ps = con.prepareStatement("TRUNCATE wz_fixedskills")) {
			ps.executeUpdate();
			ps.close();
		} catch (SQLException SQLEx) {
			SQLEx.printStackTrace();
		}

		for (Skill skilz : warriorSkills) {
			if (!BlockedSkills.isBlockedSkill(skilz.getId())) {
				try (PreparedStatement ps = con.prepareStatement("INSERT INTO wz_fixedskills(skillid, skilllevel, masterlevel) VALUES(?,?,?)")) {
					ps.setInt(1, skilz.getId());
					ps.setInt(2, skilz.getMaxLevel());
					ps.setInt(3, skilz.getMasterLevel());
					ps.executeUpdate();
					ps.close();
				} catch (SQLException SQLEx) {
					SQLEx.printStackTrace();
				}
			}
		}

		for (Skill skilz : mageSkills) {
			if (!BlockedSkills.isBlockedSkill(skilz.getId())) {
				try (PreparedStatement ps = con.prepareStatement("INSERT INTO wz_fixedskills(skillid, skilllevel, masterlevel) VALUES(?,?,?)")) {
					ps.setInt(1, skilz.getId());
					ps.setInt(2, skilz.getMaxLevel());
					ps.setInt(3, skilz.getMasterLevel());
					ps.executeUpdate();
					ps.close();
				} catch (SQLException SQLEx) {
					SQLEx.printStackTrace();
				}
			}
		}
		for (Skill skilz : archerSkills) {
			if (!BlockedSkills.isBlockedSkill(skilz.getId())) {
				try (PreparedStatement ps = con.prepareStatement("INSERT INTO wz_fixedskills(skillid, skilllevel, masterlevel) VALUES(?,?,?)")) {
					ps.setInt(1, skilz.getId());
					ps.setInt(2, skilz.getMaxLevel());
					ps.setInt(3, skilz.getMasterLevel());
					ps.executeUpdate();
					ps.close();
				} catch (SQLException SQLEx) {
					SQLEx.printStackTrace();
				}
			}
		}
		for (Skill skilz : thiefSkills) {
			if (!BlockedSkills.isBlockedSkill(skilz.getId())) {
				try (PreparedStatement ps = con.prepareStatement("INSERT INTO wz_fixedskills(skillid, skilllevel, masterlevel) VALUES(?,?,?)")) {
					ps.setInt(1, skilz.getId());
					ps.setInt(2, skilz.getMaxLevel());
					ps.setInt(3, skilz.getMasterLevel());
					ps.executeUpdate();
					ps.close();
				} catch (SQLException SQLEx) {
					SQLEx.printStackTrace();
				}
			}
		}

		for (Skill skilz : pirateSkills) {
			if (!BlockedSkills.isBlockedSkill(skilz.getId())) {
				try (PreparedStatement ps = con.prepareStatement("INSERT INTO wz_fixedskills(skillid, skilllevel, masterlevel) VALUES(?,?,?)")) {
					ps.setInt(1, skilz.getId());
					ps.setInt(2, skilz.getMaxLevel());
					ps.setInt(3, skilz.getMasterLevel());
					ps.executeUpdate();
					ps.close();
				} catch (SQLException SQLEx) {
					SQLEx.printStackTrace();
				}
			}
		}

		for (Skill skilz : gmSkills) {
			if (!BlockedSkills.isBlockedSkill(skilz.getId())) {
				try (PreparedStatement ps = con.prepareStatement("INSERT INTO wz_fixedskills(skillid, skilllevel, masterlevel) VALUES(?,?,?)")) {
					ps.setInt(1, skilz.getId());
					ps.setInt(2, skilz.getMaxLevel());
					ps.setInt(3, skilz.getMasterLevel());
					ps.executeUpdate();
					ps.close();
				} catch (SQLException SQLEx) {
					SQLEx.printStackTrace();
				}
			}
		}
	}

	public static void loadAllSkills() {
		Skill skil;
		Connection con = DatabaseConnection.getConnection();

		//ReRun this when updating version 
		//to make sure all new skills are added. vvvvvvvvvvvvvv

		/*for (Skill skilz : SkillFactory.getAllSkills()) {
		 try (PreparedStatement ps = con.prepareStatement("INSERT INTO wz_fixedskills(skillid, skilllevel, masterlevel) VALUES(?,?,?)")) {
		 ps.setInt(1, skilz.getId());
		 ps.setInt(2, skilz.getMaxLevel());
		 ps.setInt(3, skilz.getMasterLevel());
		 ps.executeUpdate();
		 ps.close();
		 } catch (SQLException SQLEx) {
		 SQLEx.printStackTrace();
		 }
		 }*/

		try {
			try (PreparedStatement ps = con.prepareStatement("SELECT skillid, skilllevel, masterlevel FROM wz_fixedskills"); ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					if (rs.getInt("skillid") == 4341003) { // This don't even work (Monster bomb)
						continue;
					}
					fixedSkill.put(rs.getInt("skillid"), new SkillEntry(rs.getByte("skilllevel"), rs.getByte("masterlevel"), -1));
				}
			}
			//}
		} catch (SQLException e) {
			System.out.println("Failed to load main skills. Reason: " + e);
		}
	}

	public static Map<Integer, SkillEntry> getNormalSkills() {
		return fixedSkill;
	}
}
