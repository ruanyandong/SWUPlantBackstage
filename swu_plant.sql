/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50140
Source Host           : localhost:3306
Source Database       : swu_plant

Target Server Type    : MYSQL
Target Server Version : 50140
File Encoding         : 65001

Date: 2019-03-11 23:31:38
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `collection`
-- ----------------------------
DROP TABLE IF EXISTS `collection`;
CREATE TABLE `collection` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `plant_chinese_name` varchar(30) NOT NULL,
  `username` varchar(100) NOT NULL,
  `collection_time` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=gbk;

-- ----------------------------
-- Records of collection
-- ----------------------------
INSERT INTO `collection` VALUES ('10', '梅花', '222', '2019-03-08 14:36:21');

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(100) CHARACTER SET utf8 NOT NULL,
  `password` varchar(100) CHARACTER SET utf8 NOT NULL,
  `register_time` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=gbk;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('27', 'jdjdjjdjdjj', 'jdjrididi', '0000-00-00 00:00:00');
INSERT INTO `user` VALUES ('28', 'jsjdj', 'jdjdj', '0000-00-00 00:00:00');
INSERT INTO `user` VALUES ('29', '111111', '222', '0000-00-00 00:00:00');
INSERT INTO `user` VALUES ('30', '222', '0000', '0000-00-00 00:00:00');
INSERT INTO `user` VALUES ('31', 'drrr', 'edfft', '0000-00-00 00:00:00');
INSERT INTO `user` VALUES ('32', 'drr', 'edfft', '0000-00-00 00:00:00');
INSERT INTO `user` VALUES ('33', 'fcvbvx', 'vffh', '0000-00-00 00:00:00');
INSERT INTO `user` VALUES ('34', 'frrr', 'jkjh', '0000-00-00 00:00:00');
INSERT INTO `user` VALUES ('35', 'bbgf', 'hgg', '0000-00-00 00:00:00');
INSERT INTO `user` VALUES ('36', '0000', '0000', '0000-00-00 00:00:00');
INSERT INTO `user` VALUES ('37', 'llllll', 'ikkii', '0000-00-00 00:00:00');
INSERT INTO `user` VALUES ('38', '67', '00', '0000-00-00 00:00:00');
INSERT INTO `user` VALUES ('39', '23', '32', '0000-00-00 00:00:00');
INSERT INTO `user` VALUES ('40', '99', '88', '0000-00-00 00:00:00');
INSERT INTO `user` VALUES ('41', '76', '90', '0000-00-00 00:00:00');
INSERT INTO `user` VALUES ('42', 'mmmm', '9090', '0000-00-00 00:00:00');
INSERT INTO `user` VALUES ('43', '9087', '1qwe', '0000-00-00 00:00:00');
INSERT INTO `user` VALUES ('44', 'oolo', 'pij', '2019-03-03 21:32:08');
