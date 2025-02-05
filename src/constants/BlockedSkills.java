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

/**
 *
 * @author Burblish
 */
public class BlockedSkills {
	//MOVED HERE CAUSE THERE WAS TOO MANY SKILLS LMFAO

	public static final boolean isBlockedSkill(int skillid) {
		switch (skillid) {
			/*case 23121004:
			 case 0000110:
			 case 80000000:
			 case 80000001:
			 case 30000022:
			 case 30010022:
			 case 30010112:
			 case 80001034:
			 case 80001035:
			 case 80001036:
			 case 20021110:
			 case 80001040:
			 case 80001041:
			 case 80001042:
			 case 80001043:
			 case 80001079:
			 case 80001080:
			 case 80001081:
			 case 80001091:
			 case 80001092:
			 case 80001093:
			 case 80001094:
			 case 80001095:
			 case 80001096:
			 case 80001097:
			 case 80001098:
			 case 80001099:
			 case 80001100:
			 case 80001101:
			 case 80001102:
			 case 80001103:
			 case 80001104:
			 case 80001105:
			 case 80001106:
			 case 80001107:
			 case 80001108:
			 case 0001013:
			 case 0001014:
			 case 0001015:
			 case 10001046:
			 case 20001046:
			 case 20011046:
			 case 20021013:
			 case 30001013:
			 case 30011013:
			 case 80001085:
			 case 8000:
			 case 8001:
			 case 8002:
			 case 8003:
			 case 8004:
			 case 8005:
			 case 8006:
			 case 9000:
			 case 20000014:
			 case 20000015:
			 case 20000016:
			 case 20000017:
			 case 20000018:
			 case 20001013:
			 case 10000013:
			 case 1009:
			 case 1010:
			 case 1011:
			 case 1007:
			 case 93:
			 case 20000093:
			 case 20010093:
			 case 20020093:
			 case 30000093:
			 case 20000194:
			 case 73:
			 case 10000073:
			 case 20000073:
			 case 20010073:
			 case 20020073:
			 case 30000073:
			 case 30010073:
			 case 20008000:
			 case 20008001:
			 case 20008002:
			 case 20008003:
			 case 20008004:
			 case 20008005:
			 case 20008006:
			 case 20018000:
			 case 20018001:
			 case 20018002:
			 case 20018003:
			 case 20018004:
			 case 20018005:
			 case 20018006:
			 case 20028000:
			 case 20028001:
			 case 20028002:
			 case 20028003:
			 case 20028004:
			 case 20028005:
			 case 20028006:
			 case 30008000:
			 case 30008001:
			 case 30008002:
			 case 30008003:
			 case 30008004:
			 case 30008005:
			 case 30008006:
			 case 30018000:
			 case 30018001:
			 case 30018002:
			 case 30018003:
			 case 30018004:
			 case 30018005:
			 case 30018006:
			 case 0000012:
			 case 10000012:
			 case 20000012:
			 case 20010012:
			 case 20020012:
			 case 30000012:
			 case 30010012:
			 case 10000097:
			 case 10000099:
			 case 10000100:
			 case 10000103:
			 case 10000104:
			 case 20000097:
			 case 20000099:
			 case 20000100:
			 case 20000103:
			 case 20000104:
			 case 20010097:
			 case 20010099:
			 case 200100100:
			 case 200100103:
			 case 200100104:
			 case 20020097:
			 case 20020099:
			 case 20020100:
			 case 20020103:
			 case 20020104:
			 case 30000097:
			 case 30000099:
			 case 30000100:
			 case 30000103:
			 case 30000104:
			 case 30010097:
			 case 30010099:
			 case 30010100:
			 case 30010103:
			 case 30010104:
			 case 0001066:
			 case 0001067:
			 case 10001066:
			 case 10001067:
			 case 20001066:
			 case 20001067:
			 case 20011066:
			 case 20011067:
			 case 30001066:
			 case 30001067:
			 case 10000190:
			 case 20000190:
			 case 20010190:
			 case 20020190:
			 case 30000190:
			 case 30010190:
			 case 0001113:
			 case 0001114:
			 case 0001115:
			 case 0001121:
			 case 0001122:
			 case 0001123:
			 case 0001124:
			 case 1129:
			 case 0001130:
			 case 0001136:
			 case 1138:
			 case 1139:
			 case 0001142:
			 case 0001143:
			 case 0001144:
			 case 0001145:
			 case 0001146:
			 case 0001147:
			 case 1148:
			 case 1149:
			 case 0001150:
			 case 0001151:
			 case 0001152:
			 case 0001153:
			 case 0001154:
			 case 0001155:
			 case 0001156:
			 case 0001157:
			 case 1158:
			 case 1115:
			 case 10001115:
			 case 20001115:
			 case 20011115:
			 case 30001115:
			 case 80001057:
			 case 1114:
			 case 1113:
			 case 10001113:
			 case 10001114:
			 case 20001113:
			 case 20001114:
			 case 20011113:
			 case 20011114:
			 case 30001113:
			 case 30001114:
			 case 1118:
			 case 10001118:
			 case 20001118:
			 case 20011118:
			 case 30001118:
			 case 80001058:
			 case 1121:
			 case 10001121:
			 case 20001121:
			 case 20011121:
			 case 30001121:
			 case 80001059:
			 case 1122:
			 case 10001122:
			 case 20001122:
			 case 20011122:
			 case 30001122:
			 case 80001060:
			 case 1123:
			 case 10001123:
			 case 20001123:
			 case 20011123:
			 case 30001123:
			 case 80001082:
			 case 1124:
			 case 10001124:
			 case 20001124:
			 case 20011124:
			 case 30001124:
			 case 10001129:
			 case 20001129:
			 case 20011129:
			 case 30001129:
			 case 80001061:
			 case 1130:
			 case 10001130:
			 case 20001130:
			 case 20011130:
			 case 30001130:
			 case 1136:
			 case 10001136:
			 case 20001136:
			 case 20011136:
			 case 30001136:
			 case 1038:
			 case 10001038:
			 case 20001038:
			 case 20011038:
			 case 30001038:
			 case 80001032:
			 case 80001047:
			 case 10001139:
			 case 20001139:
			 case 20011139:
			 case 30001139:
			 case 80001062:
			 case 1142:
			 case 10001142:
			 case 20001142:
			 case 20011142:
			 case 30001142:
			 case 1143:
			 case 10001143:
			 case 20001143:
			 case 20011143:
			 case 30001143:
			 case 80001063:
			 case 1144:
			 case 10001144:
			 case 20001144:
			 case 20011144:
			 case 30001144:
			 case 80001012:
			 case 80001064:
			 case 1145:
			 case 10001145:
			 case 20001145:
			 case 20011145:
			 case 30001145:
			 case 80001016:
			 case 80001065:
			 case 1146:
			 case 10001146:
			 case 20001146:
			 case 20011146:
			 case 30001146:
			 case 80001066:
			 case 1147:
			 case 10001147:
			 case 20001147:
			 case 20011147:
			 case 30001147:
			 case 80001067:
			 case 10001148:
			 case 20001148:
			 case 20011148:
			 case 30001148:
			 case 80001068:
			 case 10001149:
			 case 20001149:
			 case 20011149:
			 case 30001149:
			 case 80001069:
			 case 1150:
			 case 10001150:
			 case 20001150:
			 case 20011150:
			 case 30001150:
			 case 80001007:
			 case 80001070:
			 case 1151:
			 case 10001151:
			 case 20001151:
			 case 20011151:
			 case 30001151:
			 case 80001071:
			 case 1152:
			 case 10001152:
			 case 20001152:
			 case 20011152:
			 case 30001152:
			 case 80001014:
			 case 80001072:
			 case 1154:
			 case 1153:
			 case 10001153:
			 case 10001154:
			 case 20001153:
			 case 20001154:
			 case 20011153:
			 case 20011154:
			 case 30001153:
			 case 30001154:
			 case 80001008:
			 case 80001011:
			 case 80001073:
			 case 80001074:
			 case 1155:
			 case 10001155:
			 case 20001155:
			 case 20011155:
			 case 30001155:
			 case 80001075:
			 case 1069:
			 case 1156:
			 case 10001069:
			 case 10001156:
			 case 20001069:
			 case 20001156:
			 case 20011069:
			 case 20011156:
			 case 30001069:
			 case 30001156:
			 case 80001031:
			 case 80001051:
			 case 80001076:
			 case 10001157:
			 case 20001157:
			 case 20011157:
			 case 30001157:
			 case 80001077:
			 case 10001158:
			 case 20001158:
			 case 20011158:
			 case 30001158:
			 case 80001078:
			 case 220:
			 case 0001054:
			 case 10001054:
			 case 20001054:
			 case 20011054:
			 case 30001054:
			 case 80001030:
			 case 80001050:
			 case 20001164:
			 case 10001096:
			 case 20001096:
			 case 20011096:
			 case 30001096:
			 case 80001054:
			 case 80001084:
			 case 0001032:
			 case 10001032:
			 case 20001032:
			 case 20011032:
			 case 20021032:
			 case 20031032:
			 case 30001032:
			 case 30011032:
			 case 80001044:
			 case 0001102:
			 case 30001102:
			 case 80001056:
			 case 80001083:
			 case 0001047:
			 case 10001015:
			 case 10001047:
			 case 20001047:
			 case 20011047:
			 case 20021014:
			 case 30001014:
			 case 30011014:
			 case 80001086:
			 case 0001071:
			 case 10001071:
			 case 20001071:
			 case 20011071:
			 case 30001071:
			 case 80001053:
			 case 1098:
			 case 10001098:
			 case 20001098:
			 case 20011098:
			 case 20021098:
			 case 30001098:
			 case 30011098:
			 case 0001105:
			 case 10001105:
			 case 20001105:
			 case 20011105:
			 case 20021105:
			 case 30001105:
			 case 30011105:
			 case 1048:
			 case 10001016:
			 case 10001048:
			 case 20001048:
			 case 20011048:
			 case 20021015:
			 case 30001015:
			 case 30011015:
			 case 80001087:
			 case 0001070:
			 case 10001070:
			 case 20001070:
			 case 20011070:
			 case 30001070:
			 case 80001052:
			 case 0001065:
			 case 10001065:
			 case 20001065:
			 case 20011065:
			 case 30001065:
			 case 80001088:
			 case 80001001:
			 case 80001024:
			 case 80001025:
			 case 80001002:
			 case 80001026:
			 case 5010004:
			 case 5000006:
			 case 15000005:
			 case 35000005:
			 case 80001023:
			 case 20020022:
			 case 1100000:
			 case 1100009:
			 case 1200009:
			 case 1300009:
			 case 3100006:
			 case 3200006:
			 case 21100008:
			 case 23100008:
			 case 31100005:
			 case 33100010:
			 case 35100011:
			 case 1120012:
			 case 1100002:
			 case 1120013:
			 case 1200002:
			 case 1300002:
			 case 3100001:
			 case 3120008:
			 case 3200001:
			 case 4221001:
			 case 11101002:
			 case 13101002:
			 case 21100010:
			 case 21120012:
			 case 23100006:
			 case 23120012:
			 case 33100009:
			 case 33120011:
			 case 1210001:
			 case 4210000:
			 case 1220005:
			 case 1220006:
			 case 1310009:
			 case 1000006:
			 case 5100009:
			 case 11000005:
			 case 15100007:
			 case 31000003:
			 case 2000006:
			 case 12000005:
			 case 2100006:
			 case 2200006:
			 case 2300006:
			 case 12100007:
			 case 22120002:
			 case 2100007:
			 case 2200007:
			 case 2300007:
			 case 22120001:
			 case 32100007:
			 case 2110001:
			 case 2210001:
			 case 12110001:
			 case 2121009:
			 case 2221009:
			 case 2321010:
			 case 2310008:
			 case 3000001:
			 case 5200007:
			 case 13000000:
			 case 3000002:
			 case 13000001:
			 case 3100000:
			 case 13100000:
			 case 3120005:
			 case 13110003:
			 case 33120000:
			 case 3200000:
			 case 33100000:
			 case 3220004:
			 case 3120011:
			 case 3220009:
			 case 4000000:
			 case 14000000:
			 case 4000001:
			 case 14000001:
			 case 4100000:
			 case 14100000:
			 case 4100001:
			 case 14100001:
			 case 4100006:
			 case 4200006:
			 case 4310004:
			 case 4120010:
			 case 4200000:
			 case 4201002:
			 case 5010003:
			 case 5301002:
			 case 5300005:
			 case 5300004:
			 case 5300008:
			 case 5310006:
			 case 5310007:
			 case 5320009:
			 case 4300000:
			 case 12110000:
			 case 15000000:
			 case 5100008:
			 case 15110000:
			 case 21100000:
			 case 21101006:
			 case 21110007:
			 case 21110008:
			 case 21110000:
			 case 21110010:
			 case 21120009:
			 case 21120010:
			 case 21120001:
			 case 21120004:
			 case 22000000:
			 case 22140000:
			 case 22150000:
			 case 22170001:
			 case 23000003:
			 case 23100005:
			 case 23111004:
			 case 23120009:
			 case 31000002:
			 case 31100006:
			 case 31110006:
			 case 31110007:
			 case 31120008:
			 case 31120009:
			 case 32100006:
			 case 32110001:
			 case 32110000:
			 case 32120001:
			 case 32120000:
			 case 32120009:
			 case 33120010:
			 case 35100000:
			 case 35110014:
			 case 35120000:
			 case 35121006:
			 case 35120001:
			 case 11100007:*/
			case 4340010:
				return true;
			default:
				return false;
		}
	}
}
