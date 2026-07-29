/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80027
 Source Host           : localhost:3306
 Source Schema         : duzhe_study

 Target Server Type    : MySQL
 Target Server Version : 80027
 File Encoding         : 65001

 Date: 02/01/2025 19:13:19
*/

SET NAMES utf8mb4;
SET
FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`
(
    `id`             bigint                             NOT NULL AUTO_INCREMENT,
    `category_name`  varchar(100) CHARACTER SET utf8mb4 NOT NULL,
    `category_order` int                                NOT NULL,
    `createdBy`      datetime(0) NULL DEFAULT NULL,
    `updatedBy`      datetime(0) NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4    ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category`
VALUES (1, '自然百科', 1, NULL, NULL);
INSERT INTO `category`
VALUES (2, '科技前沿', 2, NULL, NULL);
INSERT INTO `category`
VALUES (3, '科学原理', 3, NULL, NULL);
INSERT INTO `category`
VALUES (4, '环保科普', 4, NULL, NULL);
INSERT INTO `category`
VALUES (5, '健康生活', 5, NULL, NULL);

-- ----------------------------
-- Table structure for channel
-- ----------------------------
DROP TABLE IF EXISTS `channel`;
CREATE TABLE `channel`
(
    `id`            bigint                             NOT NULL AUTO_INCREMENT,
    `channel_name`  varchar(100) CHARACTER SET utf8mb4 NOT NULL,
    `channel_order` int                                NOT NULL,
    `category_id`   bigint                             NOT NULL,
    `createdBy`     datetime(0) NULL DEFAULT NULL,
    `updatedBy`     datetime(0) NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4    ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of channel
-- ----------------------------
INSERT INTO `channel`
VALUES (1, '自然奇观', 1, 1, NULL, NULL);
INSERT INTO `channel`
VALUES (2, '动物世界', 2, 1, NULL, NULL);
INSERT INTO `channel`
VALUES (3, '植物王国', 3, 1, NULL, NULL);
INSERT INTO `channel`
VALUES (4, '海洋生态', 4, 1, NULL, NULL);
INSERT INTO `channel`
VALUES (5, '航空航天', 1, 2, NULL, NULL);
INSERT INTO `channel`
VALUES (6, '新能源', 2, 2, NULL, NULL);
INSERT INTO `channel`
VALUES (7, '人工智能', 3, 2, NULL, NULL);
INSERT INTO `channel`
VALUES (8, '生物工程', 4, 2, NULL, NULL);
INSERT INTO `channel`
VALUES (9, '物理学', 1, 3, NULL, NULL);
INSERT INTO `channel`
VALUES (10, '化学', 2, 3, NULL, NULL);
INSERT INTO `channel`
VALUES (11, '数学', 3, 3, NULL, NULL);
INSERT INTO `channel`
VALUES (12, '生态保护', 1, 4, NULL, NULL);
INSERT INTO `channel`
VALUES (13, '绿色生活', 2, 4, NULL, NULL);
INSERT INTO `channel`
VALUES (14, '疾病防治', 1, 5, NULL, NULL);
INSERT INTO `channel`
VALUES (15, '健康饮食', 2, 5, NULL, NULL);
INSERT INTO `channel`
VALUES (16, '心理健康', 3, 5, NULL, NULL);
INSERT INTO `channel`
VALUES (17, '健身运动', 4, 5, NULL, NULL);

-- ----------------------------
-- Table structure for collection
-- ----------------------------
DROP TABLE IF EXISTS `collection`;
CREATE TABLE `collection`
(
    `id`        bigint                             NOT NULL AUTO_INCREMENT,
    `user_id`   bigint                             NOT NULL,
    `folder_id` bigint                             NOT NULL,
    `work_id`   varchar(100) CHARACTER SET utf8mb4 NOT NULL,
    `createdBy` datetime(0) NULL DEFAULT NULL,
    `updatedBy` datetime(0) NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4    ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of collection
-- ----------------------------

-- ----------------------------
-- Table structure for folder
-- ----------------------------
DROP TABLE IF EXISTS `folder`;
CREATE TABLE `folder`
(
    `id`                 bigint                             NOT NULL AUTO_INCREMENT,
    `user_id`            bigint                             NOT NULL,
    `folder_name`        varchar(100) CHARACTER SET utf8mb4 NOT NULL,
    `folder_cover_image` varchar(500) CHARACTER SET utf8mb4 NOT NULL,
    `createdBy`          datetime(0) NULL DEFAULT NULL,
    `updatedBy`          datetime(0) NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4    ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of folder
-- ----------------------------
INSERT INTO `folder`
VALUES (3, 1, '人工智能', 'http://localhost/image/1728703254744_Screenshot_2024_1012_112038.png',
        '2024-10-12 03:20:58', NULL);
INSERT INTO `folder`
VALUES (4, 1, '大数据', 'http://localhost/image/1728703324531_Screenshot_2024_1012_112151.png',
        '2024-10-12 03:22:07', NULL);

-- ----------------------------
-- Table structure for follow
-- ----------------------------
DROP TABLE IF EXISTS `follow`;
CREATE TABLE `follow`
(
    `id`             bigint NOT NULL AUTO_INCREMENT,
    `user_id`        bigint NOT NULL,
    `follow_user_id` bigint NOT NULL,
    `createdBy`      datetime(0) NULL DEFAULT NULL,
    `updatedBy`      datetime(0) NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4    ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of follow
-- ----------------------------
INSERT INTO `follow`
VALUES (1, 1, 4, '2024-07-24 07:54:06', NULL);
INSERT INTO `follow`
VALUES (2, 1, 6, '2024-07-24 08:16:43', NULL);
INSERT INTO `follow`
VALUES (3, 1, 6, '2024-07-24 08:17:11', NULL);

-- ----------------------------
-- Table structure for soc_article
-- ----------------------------
DROP TABLE IF EXISTS `soc_article`;
CREATE TABLE `soc_article`
(
    `id`           bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
    `user_id`      bigint NULL DEFAULT NULL COMMENT '用户id',
    `authority`    int NULL DEFAULT NULL COMMENT '访问权限 0：全部人可见，1：仅好友可见',
    `publish_time` bigint NULL DEFAULT NULL COMMENT '域名历史流',
    `content`      varchar(650) CHARACTER SET utf8mb4 NULL DEFAULT NULL COMMENT '内容',
    `img_list`     text CHARACTER SET utf8mb4      NULL COMMENT '图片地址',
    `view_num`     int NULL DEFAULT NULL COMMENT '浏览量',
    `create_time`  datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
    `update_time`  datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '更新时间',
    `is_deleted`   bit(1) NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 32 CHARACTER SET = utf8mb4     COMMENT = '动态' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of soc_article
-- ----------------------------

-- ----------------------------
-- Table structure for soc_article_like
-- ----------------------------
DROP TABLE IF EXISTS `soc_article_like`;
CREATE TABLE `soc_article_like`
(
    `id`          bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
    `user_id`     bigint NULL DEFAULT NULL COMMENT '用户id',
    `article_id`  bigint NULL DEFAULT NULL COMMENT '动态id',
    `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '点赞时间',
    `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '更新时间',
    `is_deleted`  bit(1) NULL DEFAULT b'0' COMMENT '取消点赞：0：点赞 1：取消点赞',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb4     COMMENT = '动态点赞' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of soc_article_like
-- ----------------------------

-- ----------------------------
-- Table structure for soc_authority
-- ----------------------------
DROP TABLE IF EXISTS `soc_authority`;
CREATE TABLE `soc_authority`
(
    `id`          bigint UNSIGNED NOT NULL AUTO_INCREMENT,
    `type`        varchar(30) CHARACTER SET utf8mb4 NULL DEFAULT NULL,
    `user_id`     bigint NULL DEFAULT NULL,
    `information` varchar(255) CHARACTER SET utf8mb4 NULL DEFAULT NULL,
    `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
    `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
    `is_deleted`  bit(1) NULL DEFAULT b'0',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4    ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of soc_authority
-- ----------------------------

-- ----------------------------
-- Table structure for soc_comment
-- ----------------------------
DROP TABLE IF EXISTS `soc_comment`;
CREATE TABLE `soc_comment`
(
    `id`           bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
    `article_id`   bigint NULL DEFAULT NULL COMMENT '动态id',
    `user_id`      bigint NULL DEFAULT NULL COMMENT '用户id',
    `content`      varchar(100) CHARACTER SET utf8mb4 NULL DEFAULT NULL COMMENT '内容',
    `publish_time` bigint NULL DEFAULT NULL COMMENT '域名历史流',
    `create_time`  datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '发布时间',
    `update_time`  datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '更新时间',
    `is_deleted`   bit(1) NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 249475602489413633 CHARACTER SET = utf8mb4     COMMENT = '评论' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of soc_comment
-- ----------------------------

-- ----------------------------
-- Table structure for soc_comment_like
-- ----------------------------
DROP TABLE IF EXISTS `soc_comment_like`;
CREATE TABLE `soc_comment_like`
(
    `id`          bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
    `user_id`     bigint NULL DEFAULT NULL COMMENT '用户id',
    `comment_id`  bigint NULL DEFAULT NULL COMMENT '评论id',
    `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '评论点赞时间',
    `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '更新时间',
    `is_deleted`  bit(1) NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4     COMMENT = '评论点赞' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of soc_comment_like
-- ----------------------------

-- ----------------------------
-- Table structure for soc_friend
-- ----------------------------
DROP TABLE IF EXISTS `soc_friend`;
CREATE TABLE `soc_friend`
(
    `id`           bigint UNSIGNED NOT NULL AUTO_INCREMENT,
    `my_id`        bigint NULL DEFAULT NULL COMMENT '用户id',
    `friend_id`    bigint NULL DEFAULT NULL COMMENT '好友id',
    `notation`     varchar(30) CHARACTER SET utf8mb4 NULL DEFAULT NULL COMMENT '备注',
    `session_id`   bigint NULL DEFAULT NULL COMMENT '缓存id',
    `last_message` varchar(255) CHARACTER SET utf8mb4 NULL DEFAULT NULL COMMENT '最后一条信息',
    `unread`       int NULL DEFAULT NULL COMMENT '未读消息数',
    `is_hidden`    bit(1) NULL DEFAULT NULL COMMENT '是否隐藏',
    `create_time`  datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
    `update_time`  datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '更新时间',
    `is_deleted`   bit(1) NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 114 CHARACTER SET = utf8mb4     COMMENT = '好友表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of soc_friend
-- ----------------------------

-- ----------------------------
-- Table structure for soc_group
-- ----------------------------
DROP TABLE IF EXISTS `soc_group`;
CREATE TABLE `soc_group`
(
    `id`           bigint NOT NULL COMMENT 'id',
    `name`         varchar(30) CHARACTER SET utf8mb4 NULL DEFAULT NULL COMMENT '圈名',
    `avatar`       varchar(255) CHARACTER SET utf8mb4 NULL DEFAULT NULL COMMENT '头像',
    `introduction` varchar(255) CHARACTER SET utf8mb4 NULL DEFAULT NULL COMMENT '圈介绍',
    `owner_id`     bigint NULL DEFAULT NULL COMMENT '创建者id',
    `create_time`  datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
    `update_time`  datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '更新时间',
    `is_deleted`   bit(1) NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4     COMMENT = '读友圈表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of soc_group
-- ----------------------------

-- ----------------------------
-- Table structure for soc_group_message
-- ----------------------------
DROP TABLE IF EXISTS `soc_group_message`;
CREATE TABLE `soc_group_message`
(
    `id`           bigint NOT NULL COMMENT 'id',
    `group_id`     bigint NULL DEFAULT NULL COMMENT '圈id',
    `sender_id`    bigint NULL DEFAULT NULL COMMENT '发送者id',
    `sender_name`  varchar(255) CHARACTER SET utf8mb4 NULL DEFAULT NULL COMMENT '发送者',
    `avatar`       varchar(255) CHARACTER SET utf8mb4 NULL DEFAULT NULL COMMENT '头像',
    `send_time`    varchar(25) CHARACTER SET utf8mb4 NULL DEFAULT NULL COMMENT '发送时间',
    `content`      varchar(600) CHARACTER SET utf8mb4 NULL DEFAULT NULL COMMENT '内容',
    `message_type` varchar(10) CHARACTER SET utf8mb4 NULL DEFAULT NULL COMMENT '消息类型',
    `is_withdrawn` int NULL DEFAULT NULL COMMENT '是否撤回',
    `create_time`  datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
    `update_time`  datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '更新时间',
    `is_deleted`   bit(1) NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4     COMMENT = '圈信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of soc_group_message
-- ----------------------------

-- ----------------------------
-- Table structure for soc_member
-- ----------------------------
DROP TABLE IF EXISTS `soc_member`;
CREATE TABLE `soc_member`
(
    `id`           bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
    `member_id`    bigint NULL DEFAULT NULL COMMENT '圈成员id',
    `group_id`     bigint NULL DEFAULT NULL COMMENT '圈id',
    `unread`       int NULL DEFAULT NULL COMMENT '未读消息',
    `last_message` varchar(255) CHARACTER SET utf8mb4 NULL DEFAULT NULL COMMENT '最后一条消息',
    `is_hidden`    varchar(255) CHARACTER SET utf8mb4 NULL DEFAULT NULL COMMENT '是否隐藏',
    `is_deleted`   bit(1) NULL DEFAULT b'0' COMMENT '是否删除',
    `create_time`  datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
    `update_time`  datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 92 CHARACTER SET = utf8mb4     COMMENT = '圈内成员表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of soc_member
-- ----------------------------

-- ----------------------------
-- Table structure for soc_message
-- ----------------------------
DROP TABLE IF EXISTS `soc_message`;
CREATE TABLE `soc_message`
(
    `id`           bigint NOT NULL COMMENT 'id',
    `session_id`   bigint NULL DEFAULT NULL COMMENT 'session_id',
    `sender_id`    bigint NULL DEFAULT NULL COMMENT '发送者id',
    `receiver_id`  bigint NULL DEFAULT NULL COMMENT '接收者id',
    `send_time`    varchar(25) CHARACTER SET utf8mb4 NULL DEFAULT NULL COMMENT '发送时间',
    `content`      varchar(600) CHARACTER SET utf8mb4 NULL DEFAULT NULL COMMENT '内容',
    `message_type` varchar(10) CHARACTER SET utf8mb4 NULL DEFAULT NULL COMMENT '消息类型',
    `is_withdrawn` int NULL DEFAULT NULL COMMENT '是否撤回',
    `create_time`  datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
    `update_time`  datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
    `is_deleted`   bit(1) NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4     COMMENT = '好友信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of soc_message
-- ----------------------------

-- ----------------------------
-- Table structure for soc_notice
-- ----------------------------
DROP TABLE IF EXISTS `soc_notice`;
CREATE TABLE `soc_notice`
(
    `id`          bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
    `sender_id`   bigint NULL DEFAULT NULL COMMENT '发送者id',
    `receiver_id` bigint NULL DEFAULT NULL COMMENT '接收者id',
    `type`        varchar(20) CHARACTER SET utf8mb4 NULL DEFAULT NULL COMMENT '类型（同意、拒绝）',
    `information` tinytext CHARACTER SET utf8mb4      NULL COMMENT '信息',
    `is_read`     bit(1) NULL DEFAULT b'0' COMMENT '是否已读',
    `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
    `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '更新时间',
    `is_deleted`  bit(1) NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 107 CHARACTER SET = utf8mb4     COMMENT = '通知表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of soc_notice
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
    `id`         bigint                             NOT NULL AUTO_INCREMENT,
    `user_name`  varchar(100) CHARACTER SET utf8mb4 NOT NULL,
    `password`   varchar(100) CHARACTER SET utf8mb4 NOT NULL,
    `avatar_url` varchar(500) CHARACTER SET utf8mb4 NOT NULL,
    `createdBy`  datetime(0) NULL DEFAULT NULL,
    `updatedBy`  datetime(0) NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4    ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user`
VALUES (1, 'root1', 'admin', 'http://first-bucket20201002.oss-cn-hangzhou.aliyuncs.com/img/img/avatar/me.jpg', NULL,
        NULL);
INSERT INTO `user`
VALUES (2, 'root2', 'admin', 'http://first-bucket20201002.oss-cn-hangzhou.aliyuncs.com/img/img/avatar/me.jpg', NULL,
        NULL);
INSERT INTO `user`
VALUES (3, 'test1', 'admin', 'http://first-bucket20201002.oss-cn-hangzhou.aliyuncs.com/img/img/avatar/me.jpg',
        '2024-07-22 11:03:34', NULL);
INSERT INTO `user`
VALUES (4, 'Zyl', '123456', 'http://first-bucket20201002.oss-cn-hangzhou.aliyuncs.com/img/img/avatar/me.jpg',
        '2024-07-24 07:51:58', NULL);
INSERT INTO `user`
VALUES (5, 'A', '12345', 'http://first-bucket20201002.oss-cn-hangzhou.aliyuncs.com/img/img/avatar/me.jpg',
        '2024-07-24 08:10:00', NULL);
INSERT INTO `user`
VALUES (6, 'Ab', '12345', 'http://first-bucket20201002.oss-cn-hangzhou.aliyuncs.com/img/img/avatar/me.jpg',
        '2024-07-24 08:15:14', NULL);
INSERT INTO `user`
VALUES (7, 'Zyl', '12345', 'http://first-bucket20201002.oss-cn-hangzhou.aliyuncs.com/img/img/avatar/me.jpg',
        '2024-07-24 08:20:19', NULL);


DROP TABLE IF EXISTS `video_info`;
CREATE TABLE `video_info`
(
    `id`   bigint       NOT NULL AUTO_INCREMENT,
    `name` varchar(100) NOT NULL,
    `path` varchar(100) NOT NULL,
    `tag1` varchar(20)  NOT NULL,
    `tag2` varchar(20)  NOT NULL,
    `tag3` varchar(20)  NOT NULL,
    `tag4` varchar(20)  NOT NULL,
    `tag5` varchar(20)  NOT NULL,
    `tag6` varchar(20)  NOT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4    ROW_FORMAT = Dynamic;

INSERT INTO `video_info` (`name`, `path`, `tag1`, `tag2`, `tag3`, `tag4`, `tag5`, `tag6`)
VALUES ('日落延时', '/data/video/sunset_timelapse.mp4', '自然', '风景', '延时', '户外', '放松', '高清'),
       ('野生动物纪录片', '/data/video/wildlife_doc.mp4', '自然', '动物', '纪录片', '教育', '冒险', '4K'),
       ('山地徒步', '/data/video/mountain_hike.mp4', '旅行', '冒险', '健身', '自然', '徒步', '高清'),
       ('意大利面制作', '/data/video/cooking_pasta.mp4', '美食', '烹饪', '教程', '食谱', '意大利', '高清'),
       ('健身计划', '/data/video/fitness_routine.mp4', '健康', '健身', '锻炼', '训练', '有氧', '高清'),
       ('2025汽车评测', '/data/video/car_review_2025.mp4', '汽车', '评测', '科技', '车辆', '生活方式', '4K'),
       ('编程教程', '/data/video/coding_tutorial.mp4', '科技', '编程', '教程', 'Java', '教育', '高清'),
       ('太空探索', '/data/video/space_exploration.mp4', '科学', '太空', '纪录片', '教育', '宇宙', '4K'),
       ('城市漫步', '/data/video/city_walkthrough.mp4', '旅行', '城市', '视频日志', '都市', '旅游', '高清'),
       ('雨林声音', '/data/video/rainforest_sounds.mp4', '自然', '放松', 'ASMR', '雨林', '声音', '高清'),
       ('瑜伽课程', '/data/video/yoga_session.mp4', '健康', '瑜伽', '健身', '冥想', '放松', '高清'),
       ('街头美食之旅', '/data/video/street_food_tour.mp4', '美食', '旅行', '视频日志', '街头', '文化', '高清'),
       ('音乐视频', '/data/video/music_video.mp4', '娱乐', '音乐', '流行', '视频', '热门', '高清'),
       ('DIY家居装饰', '/data/video/diy_home_decor.mp4', '生活方式', 'DIY', '家居', '装饰', '教程', '高清'),
       ('沙滩排球', '/data/video/beach_volleyball.mp4', '体育', '沙滩', '排球', '户外', '团队', '高清'),
       ('历史纪录片', '/data/video/historical_doc.mp4', '历史', '教育', '纪录片', '文化', '信息丰富', '高清'),
       ('科技开箱', '/data/video/tech_unboxing.mp4', '科技', '评测', '开箱', '小工具', '最新', '高清'),
       ('冬季仙境', '/data/video/winter_wonderland.mp4', '自然', '风景', '冬季', '放松', '雪景', '高清'),
       ('钓鱼冒险', '/data/video/fishing_adventure.mp4', '体育', '钓鱼', '户外', '放松', '冒险', '高清'),
       ('吉他课程', '/data/video/guitar_lesson.mp4', '音乐', '教育', '乐器', '教程', '吉他', '高清');


SET
FOREIGN_KEY_CHECKS = 1;
