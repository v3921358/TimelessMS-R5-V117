DROP TABLE IF EXISTS `inner_ability_skills`;
CREATE TABLE `inner_ability_skills` (
  `player_id` int(11) unsigned NOT NULL,
  `skill_id` int(9) unsigned NOT NULL,
  `skill_level` tinyint(2) unsigned NOT NULL,
  `max_level` tinyint(2) unsigned NOT NULL,
  `rank` tinyint(1) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`player_id`,`skill_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;  