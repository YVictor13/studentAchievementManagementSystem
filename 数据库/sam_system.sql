/*
 Navicat Premium Data Transfer

 Source Server         : Yang
 Source Server Type    : MySQL
 Source Server Version : 80020
 Source Host           : localhost:3306
 Source Schema         : sam_system

 Target Server Type    : MySQL
 Target Server Version : 80020
 File Encoding         : 65001

 Date: 26/10/2020 10:33:41
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account`  (
  `accountId` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `type` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`accountId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of account
-- ----------------------------
INSERT INTO `account` VALUES ('0001', '1234', '1');
INSERT INTO `account` VALUES ('10004', '10004', '2');
INSERT INTO `account` VALUES ('10005', '10005', '2');
INSERT INTO `account` VALUES ('10006', '10006', '2');
INSERT INTO `account` VALUES ('10007', '10007', '2');
INSERT INTO `account` VALUES ('17010220192', '17010220192', '0');
INSERT INTO `account` VALUES ('17010220193', '17010220193', '0');
INSERT INTO `account` VALUES ('17010220194', '17010220194', '0');
INSERT INTO `account` VALUES ('17010220195', '17010220195', '0');
INSERT INTO `account` VALUES ('17010220196', '17010220196', '0');
INSERT INTO `account` VALUES ('17010220197', '17010220197', '0');

-- ----------------------------
-- Table structure for courses
-- ----------------------------
DROP TABLE IF EXISTS `courses`;
CREATE TABLE `courses`  (
  `courseId` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `collegeId` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `majorId` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `courseName` varchar(63) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`courseId`) USING BTREE,
  INDEX `courseId`(`courseId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of courses
-- ----------------------------
INSERT INTO `courses` VALUES ('10004', '03', '01', '计算机组成');

-- ----------------------------
-- Table structure for stucourse
-- ----------------------------
DROP TABLE IF EXISTS `stucourse`;
CREATE TABLE `stucourse`  (
  `studentId` varchar(18) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `courseId` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `term` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `grade` varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`studentId`, `courseId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of stucourse
-- ----------------------------
INSERT INTO `stucourse` VALUES ('17010220192', '10004', '2020', '100');
INSERT INTO `stucourse` VALUES ('17010220193', '10004', '2020', '91');
INSERT INTO `stucourse` VALUES ('17010220194', '10004', '2020', '92');
INSERT INTO `stucourse` VALUES ('17010220195', '10004', '2020', '93');
INSERT INTO `stucourse` VALUES ('17010220196', '10004', '2020', '94');
INSERT INTO `stucourse` VALUES ('17010220197', '10004', '2020', '96');

-- ----------------------------
-- Table structure for students
-- ----------------------------
DROP TABLE IF EXISTS `students`;
CREATE TABLE `students`  (
  `studentId` varchar(18) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `passWord` varchar(23) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(63) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `sex` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `birthday` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `nation` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `idNumber` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `political` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `classId` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `collegeId` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `grade` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `majorId` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`studentId`) USING BTREE,
  INDEX `studentId`(`studentId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of students
-- ----------------------------
INSERT INTO `students` VALUES ('17010220192', '17010220192', '张三', '男', '2020-11-10', '汉', '127672637623', '共青团员', '2017', '1', '101', '1');
INSERT INTO `students` VALUES ('17010220193', '17010220193', '李四', '女', '2020-11-11', '苗', '127672637623', '共青团员', '2018', '2', '201', '1');
INSERT INTO `students` VALUES ('17010220194', '17010220194', '王二', '男', '2020-11-12', '布依族', '127672637623', '共青团员', '2019', '3', '101', '2');
INSERT INTO `students` VALUES ('17010220195', '17010220195', '麻子', '女', '2020-11-13', '黎', '127672637623', '共青团员', '2020', '4', '202', '2');
INSERT INTO `students` VALUES ('17010220196', '17010220196', '小明', '男', '2020-11-14', '回', '127672637623', '共青团员', '2018', '5', '201', '1');
INSERT INTO `students` VALUES ('17010220197', '17010220197', '小红', '女', '2020-11-15', '白', '127672637623', '共青团员', '2019', '6', '103', '03');

-- ----------------------------
-- Table structure for teachcourse
-- ----------------------------
DROP TABLE IF EXISTS `teachcourse`;
CREATE TABLE `teachcourse`  (
  `teacherId` varchar(18) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `CourseId` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `term` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `courseRoom` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `courseDate` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`teacherId`, `CourseId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of teachcourse
-- ----------------------------
INSERT INTO `teachcourse` VALUES ('10007', '10004', '2020', 'D-101', '2020-10-01');

-- ----------------------------
-- Table structure for teachers
-- ----------------------------
DROP TABLE IF EXISTS `teachers`;
CREATE TABLE `teachers`  (
  `teacherId` varchar(18) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `passWord` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(63) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `sex` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `brithday` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `IdNumber` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `political` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `nation` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `collegeId` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`teacherId`) USING BTREE,
  INDEX `teacherId`(`teacherId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of teachers
-- ----------------------------
INSERT INTO `teachers` VALUES ('10004', '10004', '小明', '男', '2020-11-10', '128192831', '党员', '汉', '通信工程');
INSERT INTO `teachers` VALUES ('10005', '10005', '小红', '男', '2020-11-11', '128192832', '党员', '汉', '计算机科学与技术');
INSERT INTO `teachers` VALUES ('10006', '10006', '小白', '女', '2020-11-12', '128192833', '党员', '汉', '网络工程');
INSERT INTO `teachers` VALUES ('10007', '10007', '小吴', '女', '2020-11-13', '128192834', '党员', '汉', '电子工程');

SET FOREIGN_KEY_CHECKS = 1;
