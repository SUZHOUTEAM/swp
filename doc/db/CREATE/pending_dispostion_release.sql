/*
Navicat MySQL Data Transfer

Source Server         : mycat
Source Server Version : 50508
Source Host           : 192.168.252.132:8066
Source Database       : TESTDB

Target Server Type    : MYSQL
Target Server Version : 50508
File Encoding         : 65001

Date: 2017-04-19 16:20:12
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for pending_dispostion_release
-- ----------------------------
DROP TABLE IF EXISTS `pending_dispostion_release`;
CREATE TABLE `pending_dispostion_release` (
  `id` varchar(32) NOT NULL,
  `release_enterprise_id` varchar(32) NOT NULL,
  `plan_quantity` decimal(19,6) DEFAULT NULL,
  `unit_id` varchar(32) DEFAULT NULL,
  `waste_address` varchar(255) DEFAULT NULL,
  `contacts_tel` varchar(20) DEFAULT NULL,
  `status` varchar(32) DEFAULT NULL,
  `create_by` varchar(32) NOT NULL,
  `create_time` datetime NOT NULL,
  `edit_by` varchar(32) NOT NULL,
  `edit_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
