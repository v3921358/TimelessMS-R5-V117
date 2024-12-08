UPDATE `characters` set `str` = '10';
UPDATE `characters` set `dex` = '10';
UPDATE `characters` set `int` = '10';
UPDATE `characters` set `luk` = '10';
UPDATE `characters` set `hp` = '50';
UPDATE `characters` set `mp` = '50';
UPDATE `characters` set `maxhp` = '50';
UPDATE `characters` set `maxmp` = '50';
UPDATE `characters` set `meso` = '0';
UPDATE `characters` set `hpApUsed` = '0';
UPDATE `characters` set `fame` = '10';
UPDATE `characters` set `ap` = '40';
UPDATE `characters` set `sp` = '0,0,0,0,0,0,0,0,0,0';
UPDATE `characters` set `charm` = '0';
UPDATE `characters` set `fatigue` = '0';
UPDATE `characters` set `craft` = '0';
UPDATE `characters` set `charisma` = '0';
UPDATE `characters` set `will` = '0';
UPDATE `characters` set `sense` = '0';
UPDATE `characters` set `insight` = '0';
UPDATE `characters` set `pvpExp` = '0';
UPDATE `characters` set `pvpPoints` = '0';

DELETE FROM `inventoryitems`;
DELETE FROM `inventoryequipment`;
DELETE FROM `rings`;
DELETE FROM `skills`;
DELETE FROM `skills_cooldowns`;
DELETE FROM `wishlist`;
DELETE FROM `pets`;
DELETE FROM `achievements`;

UPDATE `accounts` set `ACash` = '0';
UPDATE `accounts` set `mPoints` = '0';
UPDATE `accounts` set `lastvote` = '0';

