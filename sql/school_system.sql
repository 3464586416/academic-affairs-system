/*
 Navicat Premium Dump SQL

 Source Server         : School_System
 Source Server Type    : MySQL
 Source Server Version : 80041 (8.0.41)
 Source Host           : localhost:3306
 Source Schema         : school_system

 Target Server Type    : MySQL
 Target Server Version : 80041 (8.0.41)
 File Encoding         : 65001

 Date: 28/06/2026 17:55:35
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for classroom
-- ----------------------------
DROP TABLE IF EXISTS `classroom`;
CREATE TABLE `classroom`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '教室ID',
  `room_no` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '教室号',
  `building` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '教学楼',
  `floor` int NULL DEFAULT NULL COMMENT '楼层',
  `capacity` int NOT NULL COMMENT '容量',
  `room_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '普通教室' COMMENT '教室类型',
  `has_multimedia` tinyint NULL DEFAULT 0 COMMENT '是否有多媒体设备',
  `has_projector` tinyint NULL DEFAULT 0 COMMENT '是否有投影仪',
  `has_air_conditioner` tinyint NULL DEFAULT 0 COMMENT '是否有空调',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '空闲' COMMENT '状态 空闲/占用/维修',
  `description` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `room_no`(`room_no` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '教室信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of classroom
-- ----------------------------
INSERT INTO `classroom` VALUES (1, 'A101', '教学楼A', 1, 120, '阶梯教室', 1, 1, 1, '空闲', NULL, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `classroom` VALUES (2, 'A102', '教学楼A', 1, 80, '普通教室', 1, 1, 1, '空闲', NULL, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `classroom` VALUES (3, 'A201', '教学楼A', 2, 60, '普通教室', 1, 0, 1, '空闲', NULL, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `classroom` VALUES (4, 'A202', '教学楼A', 2, 60, '普通教室', 0, 1, 0, '空闲', NULL, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `classroom` VALUES (5, 'B101', '教学楼B', 1, 200, '阶梯教室', 1, 1, 1, '空闲', NULL, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `classroom` VALUES (6, 'B102', '教学楼B', 1, 100, '阶梯教室', 1, 1, 1, '空闲', NULL, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `classroom` VALUES (7, 'B201', '教学楼B', 2, 50, '普通教室', 0, 0, 1, '空闲', NULL, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `classroom` VALUES (8, 'B202', '教学楼B', 2, 50, '普通教室', 0, 1, 0, '空闲', NULL, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `classroom` VALUES (9, 'C101', '实验楼C', 1, 40, '机房', 1, 1, 1, '空闲', NULL, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `classroom` VALUES (10, 'C102', '实验楼C', 1, 40, '机房', 1, 1, 1, '空闲', NULL, '2026-06-17 12:27:45', '2026-06-17 12:27:45');

-- ----------------------------
-- Table structure for classroom_borrow
-- ----------------------------
DROP TABLE IF EXISTS `classroom_borrow`;
CREATE TABLE `classroom_borrow`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '借用ID',
  `classroom_id` bigint NOT NULL COMMENT '教室ID',
  `borrower_id` bigint NOT NULL COMMENT '申请人ID',
  `borrow_date` date NOT NULL COMMENT '借用日期',
  `start_time` time NOT NULL COMMENT '开始时间',
  `end_time` time NOT NULL COMMENT '结束时间',
  `purpose` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '借用目的',
  `approve_status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '待审批' COMMENT '审批状态',
  `approver_id` bigint NULL DEFAULT NULL COMMENT '审批人ID',
  `approve_comment` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '审批意见',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态 0-取消 1-正常',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_classroom_date`(`classroom_id` ASC, `borrow_date` ASC) USING BTREE,
  INDEX `fk_borrow_borrower`(`borrower_id` ASC) USING BTREE,
  INDEX `fk_borrow_approver`(`approver_id` ASC) USING BTREE,
  CONSTRAINT `fk_borrow_approver` FOREIGN KEY (`approver_id`) REFERENCES `sys_user` (`id`) ON DELETE SET NULL ON UPDATE RESTRICT,
  CONSTRAINT `fk_borrow_borrower` FOREIGN KEY (`borrower_id`) REFERENCES `teacher` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `fk_borrow_classroom` FOREIGN KEY (`classroom_id`) REFERENCES `classroom` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '教室借用申请表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of classroom_borrow
-- ----------------------------
INSERT INTO `classroom_borrow` VALUES (1, 1, 1, '2026-03-15', '18:00:00', '20:00:00', '计算机学院学术讲座', '已通过', 1, NULL, 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `classroom_borrow` VALUES (2, 9, 4, '2026-03-18', '14:00:00', '17:00:00', '软件工程实训课程', '已通过', 1, NULL, 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `classroom_borrow` VALUES (3, 5, 3, '2026-03-20', '09:00:00', '11:00:00', '数学建模竞赛培训', '待审批', NULL, NULL, 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '课程ID',
  `course_no` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '课程号',
  `course_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '课程名称',
  `course_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '课程类别',
  `credits` decimal(3, 1) NOT NULL COMMENT '学分',
  `class_hours` int NOT NULL COMMENT '学时',
  `theory_hours` int NULL DEFAULT 0 COMMENT '理论学时',
  `practice_hours` int NULL DEFAULT 0 COMMENT '实践学时',
  `dept_id` bigint NULL DEFAULT NULL COMMENT '开课院系ID',
  `teacher_id` bigint NULL DEFAULT NULL COMMENT '主讲教师ID',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态 0-停用 1-启用',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `course_no`(`course_no` ASC) USING BTREE,
  INDEX `fk_course_dept`(`dept_id` ASC) USING BTREE,
  INDEX `fk_course_teacher`(`teacher_id` ASC) USING BTREE,
  CONSTRAINT `fk_course_dept` FOREIGN KEY (`dept_id`) REFERENCES `sys_department` (`id`) ON DELETE SET NULL ON UPDATE RESTRICT,
  CONSTRAINT `fk_course_teacher` FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`id`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '课程信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO `course` VALUES (1, 'CS101', '计算机导论', '专业必修', 3.0, 48, 40, 8, 1, 1, 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `course` VALUES (2, 'CS201', '数据结构', '专业必修', 4.0, 64, 48, 16, 1, 1, 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `course` VALUES (3, 'CS202', '数据库原理', '专业必修', 4.0, 64, 48, 16, 1, 1, 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `course` VALUES (4, 'CS301', '计算机网络', '专业必修', 3.5, 56, 44, 12, 1, 2, 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `course` VALUES (5, 'CS302', '操作系统', '专业必修', 4.0, 64, 48, 16, 1, 4, 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `course` VALUES (6, 'CS303', '软件工程', '专业必修', 3.0, 48, 40, 8, 1, 4, 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `course` VALUES (7, 'CS401', '人工智能导论', '专业选修', 2.5, 40, 32, 8, 1, 4, 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `course` VALUES (8, 'CS402', 'Web开发技术', '专业选修', 2.5, 40, 24, 16, 1, 2, 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `course` VALUES (9, 'MATH101', '高等数学（上）', '公共必修', 5.0, 80, 80, 0, 2, 3, 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `course` VALUES (10, 'MATH102', '高等数学（下）', '公共必修', 5.0, 80, 80, 0, 2, 3, 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `course` VALUES (11, 'MATH201', '线性代数', '公共必修', 3.0, 48, 48, 0, 2, 5, 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `course` VALUES (12, 'MATH202', '概率论与数理统计', '公共必修', 3.5, 56, 56, 0, 2, 5, 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `course` VALUES (13, 'FL101', '大学英语（一）', '公共必修', 3.0, 48, 40, 8, 3, NULL, 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `course` VALUES (14, 'PE101', '中国古典诗词鉴赏', '公共选修', 2.0, 32, 32, 0, 3, 3, 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `course` VALUES (15, 'PE102', '西方哲学导论', '公共选修', 2.0, 32, 32, 0, 4, 5, 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `course` VALUES (16, 'PE103', '音乐鉴赏', '公共选修', 1.5, 24, 24, 0, 3, 3, 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `course` VALUES (17, 'PE104', '美术鉴赏', '公共选修', 1.5, 24, 24, 0, 3, 3, 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `course` VALUES (18, 'PE105', '心理健康与调适', '公共选修', 1.0, 16, 16, 0, 4, 5, 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `course` VALUES (19, 'PE106', '创新创业基础', '公共选修', 2.0, 32, 32, 0, 4, 1, 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `course` VALUES (20, 'PE107', '演讲与口才', '公共选修', 1.5, 24, 24, 0, 3, 2, 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `course` VALUES (21, 'PE108', '摄影技术', '公共选修', 1.5, 24, 16, 8, 1, 4, 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');

-- ----------------------------
-- Table structure for course_prerequisite
-- ----------------------------
DROP TABLE IF EXISTS `course_prerequisite`;
CREATE TABLE `course_prerequisite`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `course_id` bigint NOT NULL COMMENT '课程ID',
  `prerequisite_id` bigint NOT NULL COMMENT '先修课程ID',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_course_prereq`(`course_id` ASC, `prerequisite_id` ASC) USING BTREE,
  INDEX `fk_prereq_prereq`(`prerequisite_id` ASC) USING BTREE,
  CONSTRAINT `fk_prereq_course` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `fk_prereq_prereq` FOREIGN KEY (`prerequisite_id`) REFERENCES `course` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '先修课程关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of course_prerequisite
-- ----------------------------

-- ----------------------------
-- Table structure for course_textbook
-- ----------------------------
DROP TABLE IF EXISTS `course_textbook`;
CREATE TABLE `course_textbook`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `course_id` bigint NOT NULL COMMENT '课程ID',
  `textbook_id` bigint NOT NULL COMMENT '教材ID',
  `semester_id` bigint NOT NULL COMMENT '学期ID',
  `is_required` tinyint NOT NULL DEFAULT 1 COMMENT '是否必修',
  `quantity` int NULL DEFAULT 0 COMMENT '征订数量',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_course_textbook_semester`(`course_id` ASC, `textbook_id` ASC, `semester_id` ASC) USING BTREE,
  INDEX `fk_ct_textbook`(`textbook_id` ASC) USING BTREE,
  INDEX `fk_ct_semester`(`semester_id` ASC) USING BTREE,
  CONSTRAINT `fk_ct_course` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `fk_ct_semester` FOREIGN KEY (`semester_id`) REFERENCES `sys_semester` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `fk_ct_textbook` FOREIGN KEY (`textbook_id`) REFERENCES `textbook` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '课程教材关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of course_textbook
-- ----------------------------
INSERT INTO `course_textbook` VALUES (1, 1, 1, 1, 1, 60, '2026-06-17 12:27:45');
INSERT INTO `course_textbook` VALUES (2, 2, 2, 1, 1, 60, '2026-06-17 12:27:45');
INSERT INTO `course_textbook` VALUES (3, 3, 3, 1, 1, 50, '2026-06-17 12:27:45');
INSERT INTO `course_textbook` VALUES (4, 4, 4, 1, 1, 50, '2026-06-17 12:27:45');
INSERT INTO `course_textbook` VALUES (5, 5, 5, 1, 1, 45, '2026-06-17 12:27:45');
INSERT INTO `course_textbook` VALUES (6, 9, 6, 1, 1, 100, '2026-06-17 12:27:45');
INSERT INTO `course_textbook` VALUES (7, 11, 7, 1, 1, 80, '2026-06-17 12:27:45');
INSERT INTO `course_textbook` VALUES (8, 8, 8, 2, 1, 50, '2026-06-17 12:27:45');

-- ----------------------------
-- Table structure for exam
-- ----------------------------
DROP TABLE IF EXISTS `exam`;
CREATE TABLE `exam`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '考试ID',
  `exam_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '考试名称',
  `course_id` bigint NOT NULL COMMENT '课程ID',
  `semester_id` bigint NOT NULL COMMENT '学期ID',
  `classroom_id` bigint NULL DEFAULT NULL COMMENT '考场教室ID',
  `exam_date` date NOT NULL COMMENT '考试日期',
  `start_time` time NOT NULL COMMENT '开始时间',
  `end_time` time NOT NULL COMMENT '结束时间',
  `exam_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '类型',
  `invigilator_ids` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '监考老师ID列表',
  `capacity` int NULL DEFAULT NULL COMMENT '考场容量',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态 0-取消 1-正常',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_exam_course`(`course_id` ASC) USING BTREE,
  INDEX `fk_exam_semester`(`semester_id` ASC) USING BTREE,
  INDEX `fk_exam_classroom`(`classroom_id` ASC) USING BTREE,
  CONSTRAINT `fk_exam_classroom` FOREIGN KEY (`classroom_id`) REFERENCES `classroom` (`id`) ON DELETE SET NULL ON UPDATE RESTRICT,
  CONSTRAINT `fk_exam_course` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `fk_exam_semester` FOREIGN KEY (`semester_id`) REFERENCES `sys_semester` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '考试安排表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of exam
-- ----------------------------
INSERT INTO `exam` VALUES (1, '2025-2026学年第一学期 计算机导论期末考试', 1, 1, 1, '2026-01-10', '09:00:00', '11:00:00', '期末考试', NULL, 120, 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `exam` VALUES (2, '2025-2026学年第一学期 数据结构期末考试', 2, 1, 2, '2026-01-11', '09:00:00', '11:00:00', '期末考试', NULL, 80, 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `exam` VALUES (3, '2025-2026学年第一学期 数据库原理期末考试', 3, 1, 9, '2026-01-12', '14:00:00', '16:00:00', '期末考试', NULL, 40, 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `exam` VALUES (4, '2025-2026学年第一学期 计算机网络期末考试', 4, 1, 3, '2026-01-13', '09:00:00', '11:00:00', '期末考试', NULL, 60, 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `exam` VALUES (5, '2025-2026学年第一学期 操作系统期末考试', 5, 1, 4, '2026-01-13', '14:00:00', '16:00:00', '期末考试', NULL, 60, 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `exam` VALUES (6, '2025-2026学年第一学期 软件工程期末考试', 6, 1, 1, '2026-01-14', '09:00:00', '11:00:00', '期末考试', NULL, 120, 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `exam` VALUES (7, '2025-2026学年第一学期 高等数学（上）期末考试', 9, 1, 5, '2026-01-09', '09:00:00', '11:00:00', '期末考试', NULL, 200, 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `exam` VALUES (8, '2025-2026学年第一学期 线性代数期末考试', 11, 1, 6, '2026-01-10', '14:00:00', '16:00:00', '期末考试', NULL, 100, 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');

-- ----------------------------
-- Table structure for exam_seat
-- ----------------------------
DROP TABLE IF EXISTS `exam_seat`;
CREATE TABLE `exam_seat`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '座位ID',
  `exam_id` bigint NOT NULL COMMENT '考试ID',
  `student_id` bigint NOT NULL COMMENT '学生ID',
  `seat_no` int NOT NULL COMMENT '座位号',
  `attend_status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '未签到' COMMENT '签到状态',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_exam_student`(`exam_id` ASC, `student_id` ASC) USING BTREE,
  INDEX `fk_seat_student`(`student_id` ASC) USING BTREE,
  CONSTRAINT `fk_seat_exam` FOREIGN KEY (`exam_id`) REFERENCES `exam` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `fk_seat_student` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '考场座位表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of exam_seat
-- ----------------------------

-- ----------------------------
-- Table structure for grade
-- ----------------------------
DROP TABLE IF EXISTS `grade`;
CREATE TABLE `grade`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '成绩ID',
  `student_id` bigint NOT NULL COMMENT '学生ID',
  `course_id` bigint NOT NULL COMMENT '课程ID',
  `semester_id` bigint NOT NULL COMMENT '学期ID',
  `regular_score` decimal(5, 2) NULL DEFAULT NULL COMMENT '平时成绩',
  `final_score` decimal(5, 2) NULL DEFAULT NULL COMMENT '期末成绩',
  `total_score` decimal(5, 2) NULL DEFAULT NULL COMMENT '总评成绩',
  `grade_point` decimal(3, 1) NULL DEFAULT NULL COMMENT '绩点',
  `credit_earned` decimal(3, 1) NULL DEFAULT NULL COMMENT '获得学分',
  `is_makeup` tinyint NULL DEFAULT 0 COMMENT '是否补考',
  `is_retake` tinyint NULL DEFAULT 0 COMMENT '是否重修',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态 0-删除 1-正常',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_student_course_semester`(`student_id` ASC, `course_id` ASC, `semester_id` ASC) USING BTREE,
  INDEX `fk_grade_course`(`course_id` ASC) USING BTREE,
  INDEX `fk_grade_semester`(`semester_id` ASC) USING BTREE,
  CONSTRAINT `fk_grade_course` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `fk_grade_semester` FOREIGN KEY (`semester_id`) REFERENCES `sys_semester` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `fk_grade_student` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '成绩记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of grade
-- ----------------------------
INSERT INTO `grade` VALUES (1, 1, 1, 1, 85.00, 88.00, 87.00, 3.7, 3.0, 0, 0, NULL, 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `grade` VALUES (2, 1, 2, 1, 90.00, 92.00, 91.20, 4.0, 4.0, 0, 0, NULL, 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `grade` VALUES (3, 1, 9, 1, 78.00, 82.00, 80.40, 3.3, 5.0, 0, 0, NULL, 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `grade` VALUES (4, 1, 11, 1, 88.00, 85.00, 86.20, 3.7, 3.0, 0, 0, NULL, 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `grade` VALUES (5, 2, 1, 1, 92.00, 95.00, 93.80, 4.0, 3.0, 0, 0, NULL, 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `grade` VALUES (6, 2, 3, 1, 80.00, 78.00, 78.80, 3.0, 4.0, 0, 0, NULL, 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `grade` VALUES (7, 2, 9, 1, 75.00, 80.00, 78.00, 3.0, 5.0, 0, 0, NULL, 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `grade` VALUES (8, 3, 2, 1, 70.00, 55.00, 61.00, 2.0, 4.0, 0, 0, NULL, 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `grade` VALUES (9, 3, 4, 1, 82.00, 86.00, 84.40, 3.7, 3.5, 0, 0, NULL, 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `grade` VALUES (10, 3, 11, 1, 65.00, 58.00, 60.80, 1.7, 3.0, 0, 0, NULL, 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `grade` VALUES (11, 4, 1, 1, 95.00, 97.00, 96.20, 4.0, 3.0, 0, 0, NULL, 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `grade` VALUES (12, 4, 5, 1, 88.00, 90.00, 89.20, 4.0, 4.0, 0, 0, NULL, 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `grade` VALUES (13, 4, 9, 1, 72.00, 68.00, 69.60, 2.3, 5.0, 0, 0, NULL, 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `grade` VALUES (14, 5, 3, 1, 83.00, 85.00, 84.20, 3.7, 4.0, 0, 0, NULL, 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `grade` VALUES (15, 5, 6, 1, 90.00, 88.00, 88.80, 4.0, 3.0, 0, 0, NULL, 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `grade` VALUES (16, 5, 9, 1, 77.00, 82.00, 80.00, 3.3, 5.0, 0, 0, NULL, 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `grade` VALUES (17, 6, 2, 1, 86.00, 88.00, 87.20, 3.7, 4.0, 0, 0, NULL, 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `grade` VALUES (18, 6, 4, 1, 79.00, 83.00, 81.40, 3.3, 3.5, 0, 0, NULL, 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `grade` VALUES (19, 7, 9, 1, 92.00, 96.00, 94.40, 4.0, 5.0, 0, 0, NULL, 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `grade` VALUES (20, 7, 11, 1, 88.00, 90.00, 89.20, 4.0, 3.0, 0, 0, NULL, 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `grade` VALUES (21, 7, 12, 1, 85.00, 82.00, 83.20, 3.7, 3.5, 0, 0, NULL, 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `grade` VALUES (22, 8, 9, 1, 60.00, 55.00, 57.00, 0.0, 0.0, 0, 0, NULL, 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `grade` VALUES (23, 8, 11, 1, 72.00, 70.00, 70.80, 2.3, 3.0, 0, 0, NULL, 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `grade` VALUES (24, 8, 12, 1, 68.00, 72.00, 70.40, 2.3, 3.5, 0, 0, NULL, 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');

-- ----------------------------
-- Table structure for grade_remake
-- ----------------------------
DROP TABLE IF EXISTS `grade_remake`;
CREATE TABLE `grade_remake`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `student_id` bigint NOT NULL COMMENT '学生ID',
  `course_id` bigint NOT NULL COMMENT '课程ID',
  `original_grade_id` bigint NULL DEFAULT NULL COMMENT '原成绩ID',
  `remake_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '类型 补考/重修',
  `remake_semester_id` bigint NOT NULL COMMENT '学期ID',
  `score` decimal(5, 2) NULL DEFAULT NULL COMMENT '成绩',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '状态 0-未完成 1-已完成',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_remake_student`(`student_id` ASC) USING BTREE,
  INDEX `fk_remake_course`(`course_id` ASC) USING BTREE,
  INDEX `fk_remake_grade`(`original_grade_id` ASC) USING BTREE,
  INDEX `fk_remake_semester`(`remake_semester_id` ASC) USING BTREE,
  CONSTRAINT `fk_remake_course` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `fk_remake_grade` FOREIGN KEY (`original_grade_id`) REFERENCES `grade` (`id`) ON DELETE SET NULL ON UPDATE RESTRICT,
  CONSTRAINT `fk_remake_semester` FOREIGN KEY (`remake_semester_id`) REFERENCES `sys_semester` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `fk_remake_student` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '补考/重修记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of grade_remake
-- ----------------------------

-- ----------------------------
-- Table structure for schedule
-- ----------------------------
DROP TABLE IF EXISTS `schedule`;
CREATE TABLE `schedule`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '排课ID',
  `course_id` bigint NOT NULL COMMENT '课程ID',
  `teacher_id` bigint NULL DEFAULT NULL COMMENT '教师ID',
  `classroom_id` bigint NOT NULL COMMENT '教室ID',
  `class_id` bigint NULL DEFAULT NULL COMMENT '上课班级ID',
  `semester_id` bigint NOT NULL COMMENT '学期ID',
  `day_of_week` tinyint NOT NULL COMMENT '星期 1-7',
  `start_time` time NOT NULL COMMENT '开始时间',
  `end_time` time NOT NULL COMMENT '结束时间',
  `week_start` int NULL DEFAULT NULL COMMENT '起始周',
  `week_end` int NULL DEFAULT NULL COMMENT '结束周',
  `section_start` int NULL DEFAULT NULL COMMENT '起始节次',
  `section_end` int NULL DEFAULT NULL COMMENT '结束节次',
  `schedule_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '正常' COMMENT '类型',
  `original_schedule_id` bigint NULL DEFAULT NULL COMMENT '原排课ID',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态 0-取消 1-正常',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_schedule_course`(`course_id` ASC) USING BTREE,
  INDEX `fk_schedule_teacher`(`teacher_id` ASC) USING BTREE,
  INDEX `fk_schedule_classroom`(`classroom_id` ASC) USING BTREE,
  INDEX `fk_schedule_class`(`class_id` ASC) USING BTREE,
  INDEX `fk_schedule_semester`(`semester_id` ASC) USING BTREE,
  CONSTRAINT `fk_schedule_class` FOREIGN KEY (`class_id`) REFERENCES `sys_class` (`id`) ON DELETE SET NULL ON UPDATE RESTRICT,
  CONSTRAINT `fk_schedule_classroom` FOREIGN KEY (`classroom_id`) REFERENCES `classroom` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `fk_schedule_course` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `fk_schedule_semester` FOREIGN KEY (`semester_id`) REFERENCES `sys_semester` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `fk_schedule_teacher` FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '排课表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of schedule
-- ----------------------------
INSERT INTO `schedule` VALUES (1, 1, 1, 1, 1, 1, 1, '08:00:00', '09:40:00', 1, 16, 1, 2, '正常', NULL, NULL, 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `schedule` VALUES (2, 2, 1, 2, 1, 1, 3, '10:00:00', '11:40:00', 1, 16, 3, 4, '正常', NULL, NULL, 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `schedule` VALUES (3, 3, 1, 9, 1, 1, 2, '14:00:00', '15:40:00', 1, 16, 5, 6, '正常', NULL, NULL, 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `schedule` VALUES (4, 4, 2, 3, 2, 1, 4, '08:00:00', '09:40:00', 1, 16, 1, 2, '正常', NULL, NULL, 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `schedule` VALUES (5, 5, 4, 4, 3, 1, 5, '10:00:00', '11:40:00', 1, 16, 3, 4, '正常', NULL, NULL, 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `schedule` VALUES (6, 9, 3, 5, 4, 1, 1, '14:00:00', '15:40:00', 1, 16, 5, 6, '正常', NULL, NULL, 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `schedule` VALUES (7, 11, 5, 6, 4, 1, 2, '08:00:00', '09:40:00', 1, 16, 1, 2, '正常', NULL, NULL, 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `schedule` VALUES (8, 13, NULL, 7, 5, 1, 3, '10:00:00', '11:40:00', 1, 16, 3, 4, '正常', NULL, NULL, 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `schedule` VALUES (9, 6, 4, 1, 3, 2, 1, '08:00:00', '09:40:00', 1, 16, 1, 2, '正常', NULL, NULL, 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `schedule` VALUES (10, 7, 4, 10, 1, 2, 4, '14:00:00', '15:40:00', 1, 16, 5, 6, '正常', NULL, NULL, 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `schedule` VALUES (11, 8, 2, 3, 2, 2, 2, '10:00:00', '11:40:00', 1, 16, 3, 4, '正常', NULL, NULL, 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');

-- ----------------------------
-- Table structure for schedule_change
-- ----------------------------
DROP TABLE IF EXISTS `schedule_change`;
CREATE TABLE `schedule_change`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '申请ID',
  `schedule_id` bigint NOT NULL COMMENT '原排课ID',
  `teacher_id` bigint NOT NULL COMMENT '教师ID',
  `change_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '类型',
  `new_classroom_id` bigint NULL DEFAULT NULL COMMENT '新教室ID',
  `new_date` date NULL DEFAULT NULL COMMENT '新日期',
  `new_start_time` time NULL DEFAULT NULL COMMENT '新开始时间',
  `new_end_time` time NULL DEFAULT NULL COMMENT '新结束时间',
  `reason` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '原因',
  `approve_status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '待审批' COMMENT '审批状态',
  `approver_id` bigint NULL DEFAULT NULL COMMENT '审批人ID',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态 0-取消 1-正常',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_change_schedule`(`schedule_id` ASC) USING BTREE,
  INDEX `fk_change_teacher`(`teacher_id` ASC) USING BTREE,
  INDEX `fk_change_classroom`(`new_classroom_id` ASC) USING BTREE,
  INDEX `fk_change_approver`(`approver_id` ASC) USING BTREE,
  CONSTRAINT `fk_change_approver` FOREIGN KEY (`approver_id`) REFERENCES `sys_user` (`id`) ON DELETE SET NULL ON UPDATE RESTRICT,
  CONSTRAINT `fk_change_classroom` FOREIGN KEY (`new_classroom_id`) REFERENCES `classroom` (`id`) ON DELETE SET NULL ON UPDATE RESTRICT,
  CONSTRAINT `fk_change_schedule` FOREIGN KEY (`schedule_id`) REFERENCES `schedule` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `fk_change_teacher` FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '调课申请表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of schedule_change
-- ----------------------------
INSERT INTO `schedule_change` VALUES (1, 1, 1, '调换教室', 2, NULL, '08:00:00', '09:40:00', 'A101设备维修，申请调换至A102', '已通过', 1, 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `schedule_change` VALUES (2, 3, 1, '调换时间', NULL, NULL, '16:00:00', '17:40:00', '与教研活动冲突，申请调整时段', '待审批', NULL, 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');

-- ----------------------------
-- Table structure for selection_phase
-- ----------------------------
DROP TABLE IF EXISTS `selection_phase`;
CREATE TABLE `selection_phase`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '阶段ID',
  `phase_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '阶段名称',
  `phase_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '阶段编码',
  `semester_id` bigint NOT NULL COMMENT '学期ID',
  `start_time` datetime NOT NULL COMMENT '开始时间',
  `end_time` datetime NOT NULL COMMENT '结束时间',
  `is_active` tinyint NOT NULL DEFAULT 0 COMMENT '是否当前阶段',
  `description` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '说明',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_phase_semester`(`semester_id` ASC) USING BTREE,
  CONSTRAINT `fk_phase_semester` FOREIGN KEY (`semester_id`) REFERENCES `sys_semester` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '选课阶段表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of selection_phase
-- ----------------------------

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '学生ID',
  `student_no` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '学号',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '姓名',
  `gender` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '性别 M-男 F-女',
  `birth_date` date NULL DEFAULT NULL COMMENT '出生日期',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '手机号',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱',
  `class_id` bigint NOT NULL COMMENT '班级ID',
  `enrollment_year` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '入学年份',
  `enroll_status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '在读' COMMENT '学籍状态 在读/休学/毕业',
  `native_place` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '籍贯',
  `user_id` bigint NULL DEFAULT NULL COMMENT '关联系统用户ID',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态 0-删除 1-正常',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `student_no`(`student_no` ASC) USING BTREE,
  INDEX `fk_student_class`(`class_id` ASC) USING BTREE,
  INDEX `fk_student_user`(`user_id` ASC) USING BTREE,
  CONSTRAINT `fk_student_class` FOREIGN KEY (`class_id`) REFERENCES `sys_class` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_student_user` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '学生信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES (1, '2023001', '赵一', 'M', '2005-01-10', '13900000001', 'zhaoyi@stu.univ.edu.cn', 1, '2023', '在读', '北京', NULL, 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `student` VALUES (2, '2023002', '钱二', 'F', '2005-03-22', '13900000002', 'qianer@stu.univ.edu.cn', 1, '2023', '在读', '上海', NULL, 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `student` VALUES (3, '2023003', '孙三', 'M', '2004-12-05', '13900000003', 'sansan@stu.univ.edu.cn', 1, '2023', '在读', '广州', NULL, 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `student` VALUES (4, '2023004', '李四', 'F', '2005-06-18', '13900000004', 'lisi@stu.univ.edu.cn', 2, '2023', '在读', '深圳', NULL, 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `student` VALUES (5, '2023005', '周五', 'M', '2005-08-30', '13900000005', 'zhouwu@stu.univ.edu.cn', 2, '2023', '在读', '杭州', NULL, 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `student` VALUES (6, '2023006', '吴六', 'F', '2004-11-15', '13900000006', 'wuliu@stu.univ.edu.cn', 3, '2023', '在读', '南京', NULL, 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `student` VALUES (7, '2023007', '郑七', 'M', '2005-02-28', '13900000007', 'zhengqi@stu.univ.edu.cn', 4, '2023', '在读', '成都', NULL, 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `student` VALUES (8, '2023008', '王八', 'F', '2005-04-10', '13900000008', 'wangba@stu.univ.edu.cn', 4, '2023', '在读', '武汉', NULL, 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');

-- ----------------------------
-- Table structure for student_selection
-- ----------------------------
DROP TABLE IF EXISTS `student_selection`;
CREATE TABLE `student_selection`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '选课ID',
  `student_id` bigint NOT NULL COMMENT '学生ID',
  `course_id` bigint NOT NULL COMMENT '课程ID',
  `semester_id` bigint NOT NULL COMMENT '学期ID',
  `phase_id` bigint NULL DEFAULT NULL COMMENT '选课阶段ID',
  `selection_status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '已选' COMMENT '选课状态',
  `is_lottery` tinyint NULL DEFAULT 0 COMMENT '是否抽签',
  `selected_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '选课时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_student_course_semester`(`student_id` ASC, `course_id` ASC, `semester_id` ASC) USING BTREE,
  INDEX `fk_selection_course`(`course_id` ASC) USING BTREE,
  INDEX `fk_selection_semester`(`semester_id` ASC) USING BTREE,
  INDEX `fk_selection_phase`(`phase_id` ASC) USING BTREE,
  CONSTRAINT `fk_selection_course` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `fk_selection_phase` FOREIGN KEY (`phase_id`) REFERENCES `selection_phase` (`id`) ON DELETE SET NULL ON UPDATE RESTRICT,
  CONSTRAINT `fk_selection_semester` FOREIGN KEY (`semester_id`) REFERENCES `sys_semester` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `fk_selection_student` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '学生选课记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student_selection
-- ----------------------------
INSERT INTO `student_selection` VALUES (1, 1, 14, 2, NULL, '已选', 0, '2026-03-01 10:00:00');
INSERT INTO `student_selection` VALUES (2, 1, 15, 2, NULL, '已选', 0, '2026-03-01 10:05:00');
INSERT INTO `student_selection` VALUES (3, 1, 17, 2, NULL, '已选', 0, '2026-03-01 10:10:00');
INSERT INTO `student_selection` VALUES (4, 2, 14, 2, NULL, '已选', 0, '2026-03-01 10:15:00');
INSERT INTO `student_selection` VALUES (5, 2, 16, 2, NULL, '已选', 0, '2026-03-01 10:20:00');
INSERT INTO `student_selection` VALUES (6, 2, 18, 2, NULL, '已选', 0, '2026-03-01 10:25:00');
INSERT INTO `student_selection` VALUES (7, 3, 15, 2, NULL, '已选', 0, '2026-03-01 11:00:00');
INSERT INTO `student_selection` VALUES (8, 3, 19, 2, NULL, '已选', 0, '2026-03-01 11:05:00');
INSERT INTO `student_selection` VALUES (9, 4, 14, 2, NULL, '已选', 0, '2026-03-01 11:10:00');
INSERT INTO `student_selection` VALUES (10, 4, 17, 2, NULL, '已选', 0, '2026-03-01 11:15:00');
INSERT INTO `student_selection` VALUES (11, 4, 20, 2, NULL, '已选', 0, '2026-03-01 11:20:00');
INSERT INTO `student_selection` VALUES (12, 5, 16, 2, NULL, '已选', 0, '2026-03-01 11:25:00');
INSERT INTO `student_selection` VALUES (13, 5, 18, 2, NULL, '已选', 0, '2026-03-01 11:30:00');
INSERT INTO `student_selection` VALUES (14, 6, 14, 2, NULL, '已选', 0, '2026-03-01 11:35:00');
INSERT INTO `student_selection` VALUES (15, 6, 17, 2, NULL, '已选', 0, '2026-03-01 11:40:00');
INSERT INTO `student_selection` VALUES (16, 6, 19, 2, NULL, '已选', 0, '2026-03-01 11:45:00');
INSERT INTO `student_selection` VALUES (17, 7, 15, 2, NULL, '已选', 0, '2026-03-02 09:00:00');
INSERT INTO `student_selection` VALUES (18, 7, 20, 2, NULL, '已选', 0, '2026-03-02 09:05:00');
INSERT INTO `student_selection` VALUES (19, 8, 14, 2, NULL, '已选', 0, '2026-03-02 09:10:00');
INSERT INTO `student_selection` VALUES (20, 8, 16, 2, NULL, '已选', 0, '2026-03-02 09:15:00');
INSERT INTO `student_selection` VALUES (21, 8, 18, 2, NULL, '已选', 0, '2026-03-02 09:20:00');

-- ----------------------------
-- Table structure for sys_class
-- ----------------------------
DROP TABLE IF EXISTS `sys_class`;
CREATE TABLE `sys_class`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '班级ID',
  `class_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '班级名称',
  `class_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '班级编码',
  `dept_id` bigint NOT NULL COMMENT '所属院系ID',
  `advisor_id` bigint NULL DEFAULT NULL COMMENT '班主任教师ID',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态 0-停用 1-启用',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `class_code`(`class_code` ASC) USING BTREE,
  INDEX `fk_class_dept`(`dept_id` ASC) USING BTREE,
  INDEX `fk_class_advisor`(`advisor_id` ASC) USING BTREE,
  CONSTRAINT `fk_class_advisor` FOREIGN KEY (`advisor_id`) REFERENCES `teacher` (`id`) ON DELETE SET NULL ON UPDATE RESTRICT,
  CONSTRAINT `fk_class_dept` FOREIGN KEY (`dept_id`) REFERENCES `sys_department` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '班级表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_class
-- ----------------------------
INSERT INTO `sys_class` VALUES (1, '计算机科学与技术2023级1班', 'CS202301', 1, 1, 1, '2026-06-17 12:27:45');
INSERT INTO `sys_class` VALUES (2, '计算机科学与技术2023级2班', 'CS202302', 1, 2, 1, '2026-06-17 12:27:45');
INSERT INTO `sys_class` VALUES (3, '软件工程2023级1班', 'SE202301', 1, 4, 1, '2026-06-17 12:27:45');
INSERT INTO `sys_class` VALUES (4, '数学与应用数学2023级1班', 'MATH202301', 2, 3, 1, '2026-06-17 12:27:45');
INSERT INTO `sys_class` VALUES (5, '英语2023级1班', 'EN202301', 3, NULL, 1, '2026-06-17 12:27:45');
INSERT INTO `sys_class` VALUES (6, '工商管理2023级1班', 'BA202301', 4, NULL, 1, '2026-06-17 12:27:45');

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '配置ID',
  `config_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '配置键',
  `config_value` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '配置值',
  `description` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '配置说明',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `config_key`(`config_key` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统参数配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO `sys_config` VALUES (1, 'max_selection_count', '8', '学生每学期最大选课数', '2026-06-17 12:27:29', '2026-06-17 12:27:29');
INSERT INTO `sys_config` VALUES (2, 'selection_lottery_mode', '0', '选课是否启用抽签 0-否 1-是', '2026-06-17 12:27:29', '2026-06-17 12:27:29');
INSERT INTO `sys_config` VALUES (3, 'grade_pass_score', '60', '及格分数线', '2026-06-17 12:27:29', '2026-06-17 12:27:29');
INSERT INTO `sys_config` VALUES (4, 'system_name', '校园教务管理系统', '系统名称', '2026-06-17 12:27:29', '2026-06-17 12:27:29');

-- ----------------------------
-- Table structure for sys_department
-- ----------------------------
DROP TABLE IF EXISTS `sys_department`;
CREATE TABLE `sys_department`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '院系ID',
  `dept_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '院系名称',
  `dept_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '院系编码',
  `description` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '院系描述',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态 0-停用 1-启用',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `dept_code`(`dept_code` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '院系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_department
-- ----------------------------
INSERT INTO `sys_department` VALUES (1, '计算机科学与技术学院', 'CS', '计算机科学与技术学院', 1, '2026-06-17 12:27:29');
INSERT INTO `sys_department` VALUES (2, '数学与统计学院', 'MATH', '数学与统计学院', 1, '2026-06-17 12:27:29');
INSERT INTO `sys_department` VALUES (3, '外国语学院', 'FL', '外国语学院', 1, '2026-06-17 12:27:29');
INSERT INTO `sys_department` VALUES (4, '经济管理学院', 'ECO', '经济管理学院', 1, '2026-06-17 12:27:29');

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '日志ID',
  `user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户名',
  `operation` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '操作内容',
  `method` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '请求方法',
  `duration` bigint NULL DEFAULT NULL COMMENT '执行时长（毫秒）',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_log_user`(`user_id` ASC) USING BTREE,
  CONSTRAINT `fk_log_user` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '操作日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_log
-- ----------------------------
INSERT INTO `sys_log` VALUES (1, 1, 'admin', '新增教师用户：张伟', 'POST /api/teacher', 125, '2026-06-17 12:27:45');
INSERT INTO `sys_log` VALUES (2, 1, 'admin', '新增教师用户：李娜', 'POST /api/teacher', 118, '2026-06-17 12:27:45');
INSERT INTO `sys_log` VALUES (3, 1, 'admin', '新增课程：计算机导论', 'POST /api/course', 95, '2026-06-17 12:27:45');
INSERT INTO `sys_log` VALUES (4, 1, 'admin', '新增排课记录', 'POST /api/scheduling', 210, '2026-06-17 12:27:45');
INSERT INTO `sys_log` VALUES (5, 1, 'admin', '批量导入学生信息', 'POST /api/student/import', 3520, '2026-06-17 12:27:45');
INSERT INTO `sys_log` VALUES (6, 1, 'admin', '修改学期信息', 'PUT  /api/semester/2', 78, '2026-06-17 12:27:45');
INSERT INTO `sys_log` VALUES (7, 2, 'zhangwei', '录入成绩：CS101', 'POST /api/grade/batch', 450, '2026-06-17 12:27:45');
INSERT INTO `sys_log` VALUES (8, 3, 'lina', '提交教室借用申请', 'POST /api/classroom/borrow', 88, '2026-06-17 12:27:45');
INSERT INTO `sys_log` VALUES (9, 4, 'liuqiang', '提交调课申请', 'POST /api/schedule/change', 102, '2026-06-17 12:27:45');
INSERT INTO `sys_log` VALUES (10, 1, 'admin', '审批调课申请', 'PUT  /api/schedule/change/1/approve', 65, '2026-06-17 12:27:45');
INSERT INTO `sys_log` VALUES (11, 1, 'admin', '保存用户信息', 'POST /system/user/save', 255, '2026-06-26 15:40:49');
INSERT INTO `sys_log` VALUES (12, 1, 'admin', '删除用户', 'DELETE /system/user/7', 33, '2026-06-26 15:40:56');
INSERT INTO `sys_log` VALUES (13, 1, 'admin', '保存用户信息', 'POST /system/user/save', 202, '2026-06-26 15:41:37');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色名称',
  `role_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色编码',
  `description` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '角色描述',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态 0-禁用 1-启用',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `role_name`(`role_name` ASC) USING BTREE,
  UNIQUE INDEX `role_code`(`role_code` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '管理员', 'ADMIN', '系统管理员，拥有所有权限', 1, '2026-06-17 12:27:29');
INSERT INTO `sys_role` VALUES (2, '教师', 'TEACHER', '教师角色', 1, '2026-06-17 12:27:29');
INSERT INTO `sys_role` VALUES (3, '教务秘书', 'SECRETARY', '负责排课、选课等日常教务工作', 1, '2026-06-17 12:27:29');

-- ----------------------------
-- Table structure for sys_semester
-- ----------------------------
DROP TABLE IF EXISTS `sys_semester`;
CREATE TABLE `sys_semester`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '学期ID',
  `semester_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '学期名称',
  `start_date` date NOT NULL COMMENT '开始日期',
  `end_date` date NOT NULL COMMENT '结束日期',
  `is_current` tinyint NOT NULL DEFAULT 0 COMMENT '是否当前学期 0-否 1-是',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态 0-停用 1-启用',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '学期表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_semester
-- ----------------------------
INSERT INTO `sys_semester` VALUES (1, '2025-2026学年第一学期', '2025-09-01', '2026-01-15', 0, 1, '2026-06-17 12:27:29');
INSERT INTO `sys_semester` VALUES (2, '2025-2026学年第二学期', '2026-02-20', '2026-07-10', 1, 1, '2026-06-17 12:27:29');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码（加密）',
  `real_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '真实姓名',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态 0-禁用 1-启用',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', '$2a$10$cpVozj.woLHtQWQdtb37SuENY41iYU0Gj4GOBJRhmSNh9k8JrnpZG', '系统管理员', 1, '2026-06-17 12:27:29', '2026-06-17 12:27:29');
INSERT INTO `sys_user` VALUES (2, 'zhangwei', '$2a$10$cpVozj.woLHtQWQdtb37SuENY41iYU0Gj4GOBJRhmSNh9k8JrnpZG', '张伟', 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `sys_user` VALUES (3, 'lina', '$2a$10$cpVozj.woLHtQWQdtb37SuENY41iYU0Gj4GOBJRhmSNh9k8JrnpZG', '李娜', 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `sys_user` VALUES (4, 'wangfang', '$2a$10$cpVozj.woLHtQWQdtb37SuENY41iYU0Gj4GOBJRhmSNh9k8JrnpZG', '王芳', 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `sys_user` VALUES (5, 'liuqiang', '$2a$10$cpVozj.woLHtQWQdtb37SuENY41iYU0Gj4GOBJRhmSNh9k8JrnpZG', '刘强', 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `sys_user` VALUES (6, 'chenming', '$2a$10$cpVozj.woLHtQWQdtb37SuENY41iYU0Gj4GOBJRhmSNh9k8JrnpZG', '陈明', 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `sys_user` VALUES (7, 'zhulin', '$2a$10$cpVozj.woLHtQWQdtb37SuENY41iYU0Gj4GOBJRhmSNh9k8JrnpZG', '朱琳', 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `role_id` bigint NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_user_role`(`user_id` ASC, `role_id` ASC) USING BTREE,
  INDEX `fk_user_role_role`(`role_id` ASC) USING BTREE,
  CONSTRAINT `fk_user_role_role` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `fk_user_role_user` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户角色关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1, 1);
INSERT INTO `sys_user_role` VALUES (6, 2, 2);
INSERT INTO `sys_user_role` VALUES (3, 3, 2);
INSERT INTO `sys_user_role` VALUES (5, 4, 2);
INSERT INTO `sys_user_role` VALUES (4, 5, 2);
INSERT INTO `sys_user_role` VALUES (2, 6, 2);
INSERT INTO `sys_user_role` VALUES (7, 7, 3);

-- ----------------------------
-- Table structure for teacher
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '教师ID',
  `teacher_no` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '工号',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '姓名',
  `gender` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '性别 M-男 F-女',
  `birth_date` date NULL DEFAULT NULL COMMENT '出生日期',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '手机号',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱',
  `dept_id` bigint NOT NULL COMMENT '所属院系ID',
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '职称 教授/副教授/讲师/助教',
  `education` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '学历 博士/硕士/学士',
  `research_direction` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '研究方向',
  `user_id` bigint NULL DEFAULT NULL COMMENT '关联系统用户ID',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态 0-离职 1-在职',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `teacher_no`(`teacher_no` ASC) USING BTREE,
  INDEX `fk_teacher_dept`(`dept_id` ASC) USING BTREE,
  INDEX `fk_teacher_user`(`user_id` ASC) USING BTREE,
  CONSTRAINT `fk_teacher_dept` FOREIGN KEY (`dept_id`) REFERENCES `sys_department` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_teacher_user` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '教师信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of teacher
-- ----------------------------
INSERT INTO `teacher` VALUES (1, 'T001', '张伟', 'M', '1980-03-15', '13800000001', 'zhangwei@univ.edu.cn', 1, '教授', '博士', '数据库与分布式系统', 2, 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `teacher` VALUES (2, 'T002', '李娜', 'F', '1985-07-22', '13800000002', 'lina@univ.edu.cn', 1, '副教授', '博士', '计算机网络与信息安全', 3, 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `teacher` VALUES (3, 'T003', '王芳', 'F', '1982-11-08', '13800000003', 'wangfang@univ.edu.cn', 2, '教授', '博士', '应用数学与算法分析', 4, 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `teacher` VALUES (4, 'T004', '刘强', 'M', '1978-01-30', '13800000004', 'liuqiang@univ.edu.cn', 1, '副教授', '博士', '软件工程与人工智能', 5, 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `teacher` VALUES (5, 'T005', '陈明', 'M', '1990-05-12', '13800000005', 'chenming@univ.edu.cn', 2, '讲师', '硕士', '高等数学与线性代数', 6, 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');

-- ----------------------------
-- Table structure for textbook
-- ----------------------------
DROP TABLE IF EXISTS `textbook`;
CREATE TABLE `textbook`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '教材ID',
  `isbn` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'ISBN书号',
  `title` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '教材名称',
  `author` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '作者',
  `publisher` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '出版社',
  `publish_date` date NULL DEFAULT NULL COMMENT '出版日期',
  `edition` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '版次',
  `price` decimal(8, 2) NULL DEFAULT NULL COMMENT '价格',
  `stock` int NULL DEFAULT 0 COMMENT '库存数量',
  `description` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '教材简介',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态 0-停用 1-启用',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `isbn`(`isbn` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '教材信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of textbook
-- ----------------------------
INSERT INTO `textbook` VALUES (1, '9787040012345', '计算机导论（第3版）', '张伟', '高等教育出版社', '2023-08-01', '第3版', 45.00, 200, '计算机专业入门教材', 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `textbook` VALUES (2, '9787111234567', '数据结构（C语言版）', '严蔚敏', '机械工业出版社', '2022-06-01', '第2版', 52.00, 180, '经典数据结构教材', 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `textbook` VALUES (3, '9787040056789', '数据库系统概论（第5版）', '王珊', '高等教育出版社', '2023-03-01', '第5版', 49.00, 150, '数据库经典教材', 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `textbook` VALUES (4, '9787115123456', '计算机网络（第8版）', '谢希仁', '电子工业出版社', '2022-01-01', '第8版', 55.00, 160, '计算机网络权威教材', 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `textbook` VALUES (5, '9787111345678', '操作系统概念（第10版）', 'Silberschatz', '机械工业出版社', '2021-09-01', '第10版', 68.00, 120, '操作系统经典教材', 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `textbook` VALUES (6, '9787040078901', '高等数学（上）（第7版）', '同济大学', '高等教育出版社', '2020-07-01', '第7版', 42.00, 300, '高等数学通用教材', 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `textbook` VALUES (7, '9787040090123', '线性代数（第4版）', '陈明', '高等教育出版社', '2023-02-01', '第4版', 38.00, 250, '线性代数教材', 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');
INSERT INTO `textbook` VALUES (8, '9787121234569', 'Web前端开发技术（第2版）', '李娜', '电子工业出版社', '2023-06-01', '第2版', 46.00, 100, 'HTML/CSS/JavaScript实战', 1, '2026-06-17 12:27:45', '2026-06-17 12:27:45');

-- ----------------------------
-- Table structure for textbook_distribute
-- ----------------------------
DROP TABLE IF EXISTS `textbook_distribute`;
CREATE TABLE `textbook_distribute`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '发放ID',
  `textbook_id` bigint NOT NULL COMMENT '教材ID',
  `student_id` bigint NULL DEFAULT NULL COMMENT '领取学生ID',
  `quantity` int NOT NULL DEFAULT 1 COMMENT '数量',
  `unit_price` decimal(8, 2) NULL DEFAULT NULL COMMENT '单价',
  `total_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '总金额',
  `distribute_date` date NOT NULL COMMENT '发放日期',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_distribute_textbook`(`textbook_id` ASC) USING BTREE,
  INDEX `fk_distribute_student`(`student_id` ASC) USING BTREE,
  CONSTRAINT `fk_distribute_student` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`) ON DELETE SET NULL ON UPDATE RESTRICT,
  CONSTRAINT `fk_distribute_textbook` FOREIGN KEY (`textbook_id`) REFERENCES `textbook` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '教材发放记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of textbook_distribute
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
