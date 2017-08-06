/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50626
Source Host           : 127.0.0.1:3306
Source Database       : yoyo

Target Server Type    : MYSQL
Target Server Version : 50626
File Encoding         : 65001

Date: 2017-06-17 22:26:04
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '日志编号',
  `user_name` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '用户名',
  `operation` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '用户操作',
  `method` varchar(4000) COLLATE utf8_bin DEFAULT NULL COMMENT '请求方法',
  `params` varchar(4000) COLLATE utf8_bin DEFAULT NULL COMMENT '请求参数',
  `ip` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT 'IP地址',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='系统日志';

-- ----------------------------
-- Records of sys_log
-- ----------------------------
INSERT INTO `sys_log` VALUES ('1', 'outline', '验证码校验', 'cn.hejinyo.manage.controller.LoginController.verifyImgCheck()', '\"{\\\"verifyCode\\\":\\\"rote\\\"}\"', '0:0:0:0:0:0:0:1', '2017-06-17 18:10:25');
INSERT INTO `sys_log` VALUES ('2', 'outline', '验证码校验', 'cn.hejinyo.manage.controller.LoginController.verifyImgCheck()', '\"{\\\"verifyCode\\\":\\\"rote\\\"}\"', '0:0:0:0:0:0:0:1', '2017-06-17 18:10:25');
INSERT INTO `sys_log` VALUES ('3', 'outline', '验证码校验', 'cn.hejinyo.manage.controller.LoginController.verifyImgCheck()', '\"{\\\"verifyCode\\\":\\\"rote\\\"}\"', '0:0:0:0:0:0:0:1', '2017-06-17 18:10:55');
INSERT INTO `sys_log` VALUES ('4', 'outline', '验证码校验', 'cn.hejinyo.manage.controller.LoginController.verifyImgCheck()', '\"{\\\"verifyCode\\\":\\\"aaaa\\\"}\"', '0:0:0:0:0:0:0:1', '2017-06-17 18:11:07');
INSERT INTO `sys_log` VALUES ('5', 'outline', '验证码校验', 'cn.hejinyo.manage.controller.LoginController.verifyImgCheck()', '\"{\\\"verifyCode\\\":\\\"onep\\\"}\"', '0:0:0:0:0:0:0:1', '2017-06-17 18:11:44');
INSERT INTO `sys_log` VALUES ('6', 'outline', '验证码校验', 'cn.hejinyo.manage.controller.LoginController.verifyImgCheck()', '\"{\\\"verifyCode\\\":\\\"tonk\\\"}\"', '0:0:0:0:0:0:0:1', '2017-06-17 18:22:01');
INSERT INTO `sys_log` VALUES ('7', 'outline', '验证码校验', 'cn.hejinyo.manage.controller.LoginController.verifyImgCheck()', '\"{\\\"verifyCode\\\":\\\"onlo\\\"}\"', '0:0:0:0:0:0:0:1', '2017-06-17 18:33:59');
INSERT INTO `sys_log` VALUES ('8', 'outline', '验证码校验', 'cn.hejinyo.manage.controller.LoginController.verifyImgCheck()', '\"{\\\"verifyCode\\\":\\\"moil\\\"}\"', '0:0:0:0:0:0:0:1', '2017-06-17 18:35:45');
INSERT INTO `sys_log` VALUES ('9', 'outline', '验证码校验', 'cn.hejinyo.manage.controller.LoginController.verifyImgCheck()', '\"{\\\"verifyCode\\\":\\\"fats\\\"}\"', '0:0:0:0:0:0:0:1', '2017-06-17 20:47:21');
INSERT INTO `sys_log` VALUES ('10', 'outline', '验证码校验', 'cn.hejinyo.manage.controller.LoginController.verifyImgCheck()', '\"{\\\"verifyCode\\\":\\\"fats\\\"}\"', '0:0:0:0:0:0:0:1', '2017-06-17 20:47:22');
INSERT INTO `sys_log` VALUES ('11', 'outline', '验证码校验', 'cn.hejinyo.manage.controller.LoginController.verifyImgCheck()', '\"{\\\"verifyCode\\\":\\\"trce\\\"}\"', '0:0:0:0:0:0:0:1', '2017-06-17 21:09:09');
INSERT INTO `sys_log` VALUES ('12', 'outline', '验证码校验', 'cn.hejinyo.manage.controller.LoginController.verifyImgCheck()', '\"{\\\"verifyCode\\\":\\\"trce\\\"}\"', '0:0:0:0:0:0:0:1', '2017-06-17 21:09:09');
INSERT INTO `sys_log` VALUES ('13', 'outline', '验证码校验', 'cn.hejinyo.manage.controller.LoginController.verifyImgCheck()', '\"{\\\"verifyCode\\\":\\\"foil\\\"}\"', '0:0:0:0:0:0:0:1', '2017-06-17 21:43:55');

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `perm_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '权限编号',
  `res_code` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '资源编码',
  `perm_code` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '权限编码',
  `perm_name` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '权限名称',
  `perm_url` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '资源URL',
  `state` int(1) DEFAULT NULL COMMENT '状态 0：正常；1：锁定；-1：禁用(删除)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_id` int(11) DEFAULT NULL COMMENT '创建人员',
  PRIMARY KEY (`perm_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='权限表 权限表';

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES ('1', 'user', 'created', '人员添加', '*', '0', '2017-06-17 21:43:15', '0');
INSERT INTO `sys_permission` VALUES ('2', 'tools', 'view', '开发工具查看', '*', '0', '2017-06-17 21:43:15', '0');
INSERT INTO `sys_permission` VALUES ('3', 'sql', 'view', 'sql监控', 'druid', '0', '2017-06-17 21:43:15', '0');
INSERT INTO `sys_permission` VALUES ('4', 'user', 'view', '人员管理查看', 'resources/documentation.html', '0', '2017-06-17 21:43:15', '0');
INSERT INTO `sys_permission` VALUES ('5', 'menu', 'view', '菜单管理查看', '*', '0', '2017-06-17 21:43:15', '0');
INSERT INTO `sys_permission` VALUES ('6', 'Poshy tip', 'view', 'Poshy tip', 'resources/vendor/poshytip/demo/demo.html', '0', '2017-06-17 21:43:15', '0');
INSERT INTO `sys_permission` VALUES ('7', 'clip', 'view', 'clip查看', 'sysMenu/editMenu', '0', '2017-06-17 21:43:15', '0');
INSERT INTO `sys_permission` VALUES ('8', 'jqGrid', 'view', 'jqGrid查看', '*', '0', '2017-06-17 21:43:15', '0');
INSERT INTO `sys_permission` VALUES ('9', '31', 'view', '31', 'resources/vendor/poshytip/demo/demo.html', '0', '2017-06-17 21:43:15', '0');
INSERT INTO `sys_permission` VALUES ('10', '32', 'view', '32', 'test/needcreate', '0', '2017-06-17 21:43:15', '0');
INSERT INTO `sys_permission` VALUES ('11', '41', 'view', '41', 'user/manager', '0', '2017-06-17 21:43:15', '0');

-- ----------------------------
-- Table structure for sys_resource
-- ----------------------------
DROP TABLE IF EXISTS `sys_resource`;
CREATE TABLE `sys_resource` (
  `res_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '资源编号',
  `res_type` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '资源类型',
  `res_code` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '资源编码',
  `res_name` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '资源名称',
  `res_pid` int(11) DEFAULT NULL COMMENT '父资源编号',
  `res_level` int(1) DEFAULT NULL COMMENT '资源级别',
  `res_icon` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '显示图标',
  `seq` int(11) DEFAULT NULL COMMENT '排序号',
  `state` int(1) DEFAULT NULL COMMENT '状态 0：正常；1：锁定；-1：禁用(删除)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_id` int(11) DEFAULT NULL COMMENT '创建人员',
  PRIMARY KEY (`res_id`),
  UNIQUE KEY `IDXU_sys_resource_res_code` (`res_code`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='资源表 资源表';

-- ----------------------------
-- Records of sys_resource
-- ----------------------------
INSERT INTO `sys_resource` VALUES ('1', 'menu', 'user', '用户管理', '0', '1', 'ti-user', '1', '0', '2017-04-22 11:38:09', '1');
INSERT INTO `sys_resource` VALUES ('2', 'menu', 'menu', '菜单管理', '0', '1', 'ti-layers-alt', '2', '0', '2017-04-22 11:39:09', '1');
INSERT INTO `sys_resource` VALUES ('3', 'menu', 'tools', '开发工具', '0', '1', 'ti-bag', '3', '0', '2017-04-22 11:39:43', '1');
INSERT INTO `sys_resource` VALUES ('4', 'menu', 'clip', 'clip', '3', '2', 'ti-menu', '1', '0', '2017-04-22 11:40:23', '1');
INSERT INTO `sys_resource` VALUES ('5', 'menu', 'jqGrid', 'jqGrid', '3', '2', 'ti-menu', '2', '0', '2017-04-22 11:41:02', '1');
INSERT INTO `sys_resource` VALUES ('6', 'menu', 'Poshy tip', 'Poshy tip', '3', '2', 'ti-menu', '3', '0', '2017-04-22 11:41:33', '1');
INSERT INTO `sys_resource` VALUES ('7', 'menu', '31', '31', '5', '3', 'ti-menu', '1', '0', '2017-04-22 11:41:33', '1');
INSERT INTO `sys_resource` VALUES ('8', 'menu', '32', '32', '5', '3', 'ti-menu', '2', '0', '2017-04-22 11:41:33', '1');
INSERT INTO `sys_resource` VALUES ('9', 'menu', '41', '41', '2', '2', 'ti-menu', '1', '0', '2017-04-22 11:41:33', '1');
INSERT INTO `sys_resource` VALUES ('10', 'menu', 'sql', 'sql监控', '0', '1', 'ti-layers-alt', '4', '0', '2017-04-22 11:39:09', '1');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色编号',
  `role_code` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '角色编码',
  `role_name` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '角色名称',
  `role_description` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '角色描述',
  `state` int(1) DEFAULT NULL COMMENT '状态 0：正常；1：锁定；-1：禁用(删除)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_id` int(11) DEFAULT NULL COMMENT '创建人员',
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `IDXU_sys_role_role_code` (`role_code`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='角色表 角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', 'admin', '管理员', '拥有系统绝对控制权利', '0', '2017-06-16 23:06:06', '0');

-- ----------------------------
-- Table structure for sys_role_resource
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_resource`;
CREATE TABLE `sys_role_resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色资源编号',
  `role_id` int(11) DEFAULT NULL COMMENT '角色编号',
  `res_id` int(11) DEFAULT NULL COMMENT '资源编号',
  `perm_id` int(11) DEFAULT NULL COMMENT '权限编号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='角色资源表';

-- ----------------------------
-- Records of sys_role_resource
-- ----------------------------
INSERT INTO `sys_role_resource` VALUES ('1', '1', '1', '1');
INSERT INTO `sys_role_resource` VALUES ('2', '1', '1', '4');
INSERT INTO `sys_role_resource` VALUES ('3', '1', '2', '5');
INSERT INTO `sys_role_resource` VALUES ('4', '1', '3', '2');
INSERT INTO `sys_role_resource` VALUES ('5', '1', '4', '7');
INSERT INTO `sys_role_resource` VALUES ('6', '1', '5', '8');
INSERT INTO `sys_role_resource` VALUES ('7', '1', '6', '6');
INSERT INTO `sys_role_resource` VALUES ('8', '1', '7', '9');
INSERT INTO `sys_role_resource` VALUES ('9', '1', '8', '10');
INSERT INTO `sys_role_resource` VALUES ('10', '1', '9', '11');
INSERT INTO `sys_role_resource` VALUES ('11', '1', '10', '3');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户编号',
  `user_name` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '用户姓名',
  `user_pwd` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '密码',
  `user_salt` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '用户盐',
  `email` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '手机号',
  `login_ip` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '最后登录IP',
  `login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `state` int(1) DEFAULT NULL COMMENT '用户状态 0：正常；1：锁定；-1：禁用(删除)',
  `create_time` datetime DEFAULT NULL COMMENT '注册时间',
  `create_id` int(11) DEFAULT NULL COMMENT '创建人员',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `IDXU_sys_user_user_name` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户表 用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'admin', '00b3187384f2708025074f28764a4a30', 'salt', 'hejinyo@gmai.com', '17600222250', '127.0.0.1', '2017-06-16 23:04:49', '0', '2017-06-16 23:04:58', '0');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_id` int(11) DEFAULT NULL COMMENT '用户编号',
  `role_id` int(11) DEFAULT NULL COMMENT '角色编号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户角色表';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1', '1', '1');
