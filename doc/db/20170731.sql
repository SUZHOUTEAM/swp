CREATE TABLE `waste_activity` (
  `id`  varchar(32) NOT NULL COMMENT '活动id' ,
  `activity_name`  varchar(500) NULL COMMENT '活动名称' ,
  `activity_remark`  varchar(2500) NULL COMMENT '活动说明' ,
  `parent_activity_id`  varchar(32) NULL COMMENT '父活动id' ,
  `start_date`  datetime NULL COMMENT '开始时间' ,
  `end_date`  datetime NULL COMMENT '结束时间' ,
  `price_type`  varchar(255) NULL COMMENT '价格类型',
  `canton_code`  varchar(8) NULL COMMENT '活动区域行政区编码',
  `subject_file_id`  varchar(32) NULL COMMENT '宣传图片id',
  `logo_file_id`  varchar(32) NULL COMMENT 'logo图标id',
  `status`  varchar(1)  NOT NULL DEFAULT '1' COMMENT '数据状态',
  `create_by`  varchar(50)  NOT NULL ,
  `create_time`  datetime NOT NULL ,
  `edit_by`  varchar(50)  NOT NULL ,
  `edit_time`  datetime NOT NULL ,
  PRIMARY KEY (`id`)
)
;

CREATE TABLE `waste_activity_enterprise` (
  `id`  varchar(32) NOT NULL COMMENT '数据id' ,
  `activity_id`  varchar(32) NOT NULL COMMENT '活动id' ,
  `ent_id`  varchar(32) NOT NULL COMMENT '参与企业id' ,
  `ent_name`  varchar(500) NULL COMMENT '参与企业名称' ,
  `ent_remark`  varchar(2500) NULL COMMENT '企业说明' ,
  `start_price`  varchar(50) NOT NULL COMMENT '价格类型',
  `end_price`  varchar(50) NOT NULL COMMENT '价格类型',
  `status`  varchar(1)  NOT NULL DEFAULT '1' COMMENT '数据状态',
  `create_by`  varchar(50)  NOT NULL ,
  `create_time`  datetime NOT NULL ,
  `edit_by`  varchar(50)  NOT NULL ,
  `edit_time`  datetime NOT NULL ,
  PRIMARY KEY (`id`),
  INDEX `idx_wae_actid` (`activity_id`, `start_price`, `end_price`) USING BTREE
)
;

CREATE TABLE `waste_activity_contacts` (
`id`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`activity_id`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`user_name`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`user_phone`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`ent_id`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`ent_name`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`status`  varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' ,
`create_time`  datetime NULL DEFAULT NULL ,
`create_by`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`update_time`  datetime NULL DEFAULT NULL ,
`update_by`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
ROW_FORMAT=COMPACT
;

--通知类型codeType中的codeValue中添加字典  ACTIVITY_STATUS	活动动态（手动在正式库管理员界面添加）

--disposition_planitem_response和disposition_capacitydetail_response添加字段remark  询价的备注
ALTER  TABLE `disposition_planitem_response` add remark varchar(1024);
ALTER  TABLE `disposition_capacitydetail_response` add remark varchar(1024);



