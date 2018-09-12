
--字典类型修改
UPDATE code_type SET type_code = 'NOTICE_CATEGORY' AND type_name='通知分类' WHERE type_code = 'NOTICE_TYPE';
--字典表添加字段 type_code
ALTER  TABLE `code_value` add type_code varchar(30);
--字典表数据修改，将新增的字段type_code获取并修改
update code_value AS cv SET type_code = (SELECT type_code FROM code_type WHERE id= cv.type_id)
--管理员手动新增通知分类 -- 活动动态 ACTIVITY_STATUS

--系统通知消息表
CREATE TABLE `sys_notice` (
`id`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`notice_type`  varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`notice_category`  varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`rel_id`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`sender_id`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`receiver_id`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`receiver_type`  varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`notice_title`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`notice_content`  varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`has_send_msg`  varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`has_read`  varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`create_by`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`create_time`  datetime NULL DEFAULT NULL ,
`edit_by`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`edit_time`  datetime NULL DEFAULT NULL ,
`valid`  varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
ROW_FORMAT=COMPACT
;


--将原数据表里的数据转移进新表
INSERT INTO sys_notice(id,sender_id,receiver_id,receiver_type,notice_category,rel_id,notice_content,create_by,
create_time,
edit_by,
edit_time,
valid,has_send_msg,has_read) SELECT id,sender_id,receiver_id,
(SELECT code FROM code_value WHERE id = notice.receiver_type) AS receiver_type,
(SELECT code FROM code_value WHERE id = notice.notice_type) AS notice_category,
message_id AS rel_id,
notice_content,
create_by,
create_time,
edit_by,
edit_time,
valid,
'1' AS has_send_msg,
is_read AS has_read
 FROM cooperation_notice notice


--企业信息表添加字段 entType
ALTER  TABLE `sys_enterprise_base` add entType varchar(30);
ALTER  TABLE `sys_enterprise_base` add cantonCode varchar(30);
--更新字段cantonCode
UPDATE sys_enterprise_base ent,sys_org_com_canton_relation can SET ent.cantonCode = can.CantonCode WHERE ent.entId = can.ComID
--更新字段entType
UPDATE  sys_enterprise_base base,waste_enterprise_type type,code_value cv SET base.entType = cv.code
WHERE base.entId = type.enterprise_id AND type.enterprise_type_id = cv.id

--企业信息表添加字段  posx  posy
ALTER  TABLE `sys_enterprise_base` add posx DECIMAL(12,4);
ALTER  TABLE `sys_enterprise_base` add posy DECIMAL(12,4);

--更新字段 posx posy
UPDATE sys_enterprise_base ent,sys_org_com com,sys_coordinate co SET ent.posx = co.posx,ent.posy=co.posy
WHERE ent.entId = com.ComID AND com.CoordinateId = co.CoordinateId

--危废名录表添加字段   waste_type_code
ALTER  TABLE `waste` add waste_type_code varchar(30);
UPDATE waste w,waste_type t SET w.waste_type_code = t.`code` WHERE w.waste_type_id = t.id

-- start_date以及end_date和许可证同步
update disposition_capacity_release
set release_startdate = (
select start_date from operation_licence where operation_licence.id = disposition_capacity_release.operation_licence_id
)
where release_startdate is null

UPDATE disposition_capacity_release cap,operation_licence lic SET cap.release_startdate = lic.start_date
WHERE cap.operation_licence_id = lic.id AND cap.release_startdate != lic.start_date

UPDATE disposition_capacity_release cap,operation_licence lic SET cap.release_enddate = lic.end_date
WHERE cap.operation_licence_id = lic.id AND lic.end_date != cap.release_enddate

UPDATE disposition_capacity_release cap,operation_licence lic SET cap.valid = lic.valid
WHERE cap.operation_licence_id = lic.id AND lic.valid != '1'

--产废企业发布危废信息
alter table disposition_planitem_release add column status_code VARCHAR(10) null;

update disposition_planitem_release item_release 
set status_code =  (select code from code_value where item_release.release_status = id);
--企业产废单位
alter table ENTERPRISE_WASTE add column unit_code varchar(32) null;

update ENTERPRISE_WASTE
set unit_code =  (select code from code_value where enterprise_waste.unit_id = id);


ALTER table disposition_capacity_response add column resource_type varchar(20) null;
alter table disposition_capacity_response add COLUMN resource_id varchar(32) null;

ALTER table orders add column resource_type varchar(20) null;
alter table orders add COLUMN resource_id varchar(32) null;

ALTER table shopping_cart add column resource_type varchar(20) null;
alter table shopping_cart add COLUMN resource_id varchar(32) null;


--数据校验
-- SELECT lic.start_date,lic.end_date,cap.release_startdate,cap.release_enddate FROM disposition_capacity_release cap LEFT JOIN operation_licence lic ON cap.operation_licence_id = lic.id
-- WHERE lic.start_date != cap.release_startdate OR lic.end_date != cap.release_enddate
--SELECT * FROM disposition_capacity_release cap LEFT JOIN operation_licence lic ON cap.operation_licence_id = lic.id
--WHERE lic.valid != '1' AND cap.valid = '1'

--许可证增加字段 licence_status_code 和 audit_status_code
ALTER TABLE operation_licence ADD licence_status_code varchar(30);
UPDATE operation_licence ol,code_value cv set ol.licence_status_code = cv.code WHERE ol.licence_status = cv.id
ALTER TABLE operation_licence ADD audit_status_code varchar(30);
UPDATE operation_licence ol,code_value cv set ol.audit_status_code = cv.code WHERE ol.audit_status = cv.id

--处置能力发布增加release_status_code
ALTER TABLE disposition_capacitydetail_release ADD release_status_code varchar(30);
UPDATE disposition_capacitydetail_release dcr,code_value cv SET dcr.release_status_code = cv. CODE WHERE dcr.release_status = cv.id