/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80026
 Source Host           : localhost:3306
 Source Schema         : test2

 Target Server Type    : MySQL
 Target Server Version : 80026
 File Encoding         : 65001

 Date: 10/11/2021 16:07:05
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_admin
-- ----------------------------
DROP TABLE IF EXISTS `t_admin`;
CREATE TABLE `t_admin`  (
  `id` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_date` datetime(0) NULL DEFAULT NULL,
  `modify_date` datetime(0) NULL DEFAULT NULL,
  `mobile` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '姓名',
  `head_img` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `login_date` datetime(0) NULL DEFAULT NULL,
  `login_ip` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `is_lock` bit(1) NULL DEFAULT NULL,
  `login_failure_count` int(0) NOT NULL,
  `lock_date` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username_UNIQUE`(`username`) USING BTREE,
  UNIQUE INDEX `mobile_UNIQUE`(`mobile`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_admin
-- ----------------------------
INSERT INTO `t_admin` VALUES ('493fe2bf-daf5-11e7-ac5f-484d7eae60c1', '1900-01-01 00:00:00', '1900-01-01 00:00:00', '15972200949', 'admin', '21232f297a57a5a743894a0e4a801fc3', '管理员', NULL, '2021-08-20 05:57:18', '127.0.0.1', b'0', 1, NULL);
INSERT INTO `t_admin` VALUES ('d0d4258a-d530-11e9-9b7c-8cec4b9bb733', '2019-09-12 15:42:05', '2019-09-12 15:42:05', '13888888888', 'liuyifan', 'e10adc3949ba59abbe56e057f20f883e', '刘亦帆', NULL, '2019-12-04 16:12:48', '127.0.0.1', b'0', 1, NULL);

-- ----------------------------
-- Table structure for t_admin_role
-- ----------------------------
DROP TABLE IF EXISTS `t_admin_role`;
CREATE TABLE `t_admin_role`  (
  `admin_id` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `role_id` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`admin_id`, `role_id`) USING BTREE,
  INDEX `fk_role_admin_idx`(`role_id`) USING BTREE,
  CONSTRAINT `fk_admin_role` FOREIGN KEY (`admin_id`) REFERENCES `t_admin` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_role_admin` FOREIGN KEY (`role_id`) REFERENCES `t_role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_admin_role
-- ----------------------------
INSERT INTO `t_admin_role` VALUES ('d0d4258a-d530-11e9-9b7c-8cec4b9bb733', '169d2723-d530-11e9-9b7c-8cec4b9bb733');
INSERT INTO `t_admin_role` VALUES ('493fe2bf-daf5-11e7-ac5f-484d7eae60c1', 'dd00e78c-da57-11e7-ac5f-484d7eae60c1');

-- ----------------------------
-- Table structure for t_auth_code
-- ----------------------------
DROP TABLE IF EXISTS `t_auth_code`;
CREATE TABLE `t_auth_code`  (
  `id` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_date` datetime(0) NULL DEFAULT NULL,
  `modify_date` datetime(0) NULL DEFAULT NULL,
  `mobile` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `auth_code` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `send_time` datetime(0) NULL DEFAULT NULL,
  `is_enabled` bit(1) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_auth_code
-- ----------------------------

-- ----------------------------
-- Table structure for t_code_source
-- ----------------------------
DROP TABLE IF EXISTS `t_code_source`;
CREATE TABLE `t_code_source`  (
  `id` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_date` datetime(0) NULL DEFAULT NULL,
  `modify_date` datetime(0) NULL DEFAULT NULL,
  `admin_id` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `admin_name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `file_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `file_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_code_source
-- ----------------------------
INSERT INTO `t_code_source` VALUES ('4a93ec8b-8d06-11eb-9b74-8cec4b9bb733', '2021-03-25 01:06:12', '2021-03-25 01:06:12', '493fe2bf-daf5-11e7-ac5f-484d7eae60c1', '管理员', 'AllCode', 'AllCode.zip');

-- ----------------------------
-- Table structure for t_resource
-- ----------------------------
DROP TABLE IF EXISTS `t_resource`;
CREATE TABLE `t_resource`  (
  `id` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_date` datetime(0) NULL DEFAULT NULL,
  `modify_date` datetime(0) NULL DEFAULT NULL,
  `name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `path` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `allow_type` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `authority_list_store` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `path_UNIQUE`(`path`) USING BTREE,
  UNIQUE INDEX `name_UNIQUE`(`name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_resource
-- ----------------------------
INSERT INTO `t_resource` VALUES ('0a7df2e5-d52c-11e9-9b7c-8cec4b9bb733', '2019-09-12 15:07:54', '2019-09-12 15:07:54', '登录方法', '/login/**', 'permit', NULL);
INSERT INTO `t_resource` VALUES ('12309694-d52c-11e9-9b7c-8cec4b9bb733', '2019-09-12 15:08:07', '2019-09-12 15:08:07', '登录认证', '/login_check', 'permit', NULL);
INSERT INTO `t_resource` VALUES ('1b74c74e-d52c-11e9-9b7c-8cec4b9bb733', '2019-09-12 15:08:22', '2019-09-12 15:08:22', '配置文件动态刷新', '/refresh', 'permit', NULL);
INSERT INTO `t_resource` VALUES ('20781418-d52c-11e9-9b7c-8cec4b9bb733', '2019-09-12 15:08:31', '2019-09-12 15:08:31', 'WebSocket处理', '/ws/**', 'ignore', NULL);
INSERT INTO `t_resource` VALUES ('259bb614-d52c-11e9-9b7c-8cec4b9bb733', '2019-09-12 15:08:39', '2019-09-12 15:08:39', '第三方支付处理', '/pay/**', 'ignore', NULL);
INSERT INTO `t_resource` VALUES ('2bfc47f5-d52c-11e9-9b7c-8cec4b9bb733', '2019-09-12 15:08:50', '2019-09-12 15:08:50', '服务Api', '/api/**', 'ignore', NULL);
INSERT INTO `t_resource` VALUES ('3674c66c-d52c-11e9-9b7c-8cec4b9bb733', '2019-09-12 15:09:08', '2019-09-12 15:09:08', '微信公众号通信处理', '/wechat', 'ignore', NULL);
INSERT INTO `t_resource` VALUES ('42303f10-d52c-11e9-9b7c-8cec4b9bb733', '2019-09-12 15:09:27', '2019-09-12 15:09:27', '网站端资源', '/web/**', 'ignore', NULL);
INSERT INTO `t_resource` VALUES ('d549f435-c66b-11ea-8718-8cec4b9bb733', '2020-07-15 15:21:43', '2020-07-15 15:21:43', 'Swagger', '/swagger-ui.html', 'ignore', NULL);
INSERT INTO `t_resource` VALUES ('f5ff11f7-d52b-11e9-9b7c-8cec4b9bb733', '2019-09-12 15:07:19', '2019-09-12 15:07:19', '静态资源', '/static/**', 'ignore', NULL);
INSERT INTO `t_resource` VALUES ('f82565f3-d534-11e9-9b7c-8cec4b9bb733', '2019-09-12 16:11:48', '2019-09-12 16:11:48', 'Admin方法', '/admin/**', 'intercept', '[\"ROLE_ADMIN\"]');
INSERT INTO `t_resource` VALUES ('f896f091-f309-11ea-8bfc-8cec4b9bb733', '2020-09-10 10:04:33', '2020-09-10 10:04:33', '首页', '/', 'permit', NULL);
INSERT INTO `t_resource` VALUES ('fcbfd0fa-d534-11e9-9b7c-8cec4b9bb733', '2019-09-12 16:11:56', '2019-09-12 16:11:56', 'Role方法', '/role/**', 'intercept', '[\"ROLE_ROLE\"]');
INSERT INTO `t_resource` VALUES ('fe6a44fb-f4a8-11e9-a13c-8cec4b9bb733', '2019-10-22 16:50:27', '2019-10-22 16:50:27', 'WebService', '/services/**', 'ignore', NULL);

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role`  (
  `id` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_date` datetime(0) NULL DEFAULT NULL,
  `modify_date` datetime(0) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `authority_list_store` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES ('169d2723-d530-11e9-9b7c-8cec4b9bb733', '2019-09-12 15:36:52', '2019-09-12 15:36:52', '客服人员', '客服人员', '[\"ROLE_ADMIN\",\"ROLE_ROLE\",\"ROLE_BASE\"]');
INSERT INTO `t_role` VALUES ('1a346430-d879-11eb-93a6-8cec4b9bb733', '2021-06-29 01:27:00', '2021-06-29 01:27:00', '2', '2', '[\"ROLE_ADMIN\",\"ROLE_ROLE\",\"ROLE_CODE\",\"ROLE_PAY\",\"ROLE_STATIC_PAGE\",\"ROLE_UPLOAD\",\"ROLE_BASE\"]');
INSERT INTO `t_role` VALUES ('dd00e78c-da57-11e7-ac5f-484d7eae60c1', '1900-01-01 00:00:00', '1900-01-01 00:00:00', '超级管理员', '超级管理员', '[\"ROLE_ADMIN\",\"ROLE_ROLE\",\"ROLE_CODE\",\"ROLE_PAY\",\"ROLE_STATIC_PAGE\",\"ROLE_UPLOAD\",\"ROLE_BASE\"]');

-- ----------------------------
-- Table structure for t_wechat_reply
-- ----------------------------
DROP TABLE IF EXISTS `t_wechat_reply`;
CREATE TABLE `t_wechat_reply`  (
  `id` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_date` datetime(0) NULL DEFAULT NULL,
  `modify_date` datetime(0) NULL DEFAULT NULL,
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NULL,
  `keyword` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `img_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `is_open` bit(1) NULL DEFAULT NULL,
  `content_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of t_wechat_reply
-- ----------------------------

-- ----------------------------
-- Table structure for t_wechat_user
-- ----------------------------
DROP TABLE IF EXISTS `t_wechat_user`;
CREATE TABLE `t_wechat_user`  (
  `id` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_date` datetime(0) NULL DEFAULT NULL,
  `modify_date` datetime(0) NULL DEFAULT NULL,
  `open_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `nick` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `sex` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `pic_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `country` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `province` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `city` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `bind_time` datetime(0) NULL DEFAULT NULL,
  `attention_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `login_last_time` datetime(0) NULL DEFAULT NULL,
  `is_lock` bit(1) NULL DEFAULT NULL,
  `referee_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  PRIMARY KEY (`id`(32)) USING BTREE,
  UNIQUE INDEX `u_open_id`(`open_id`) USING BTREE,
  INDEX `u_phone`(`phone`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_wechat_user
-- ----------------------------
INSERT INTO `t_wechat_user` VALUES ('bc249f73-165e-11ea-a6d2-8cec4b9bb733', '2019-12-04 14:24:32', '2019-12-04 14:24:32', 'oUtiT6HwZZ5hBPGLxIzZ5Qh34nzw', 'Hare_Sama', 'male', 'http://thirdwx.qlogo.cn/mmopen/JTQskqHN1iayULSdvpmuBDSeEzZL0aV0rIWiajIfBuDPS4DMEGlh35hMOh0CNw3ciaTKdXZ8DVCRrXYl3SKJSvbGBRvohLEmrzZ/132', '中国', '湖北', '武汉', NULL, NULL, 'concerned', '2019-12-04 15:28:15', b'0', NULL);

SET FOREIGN_KEY_CHECKS = 1;
