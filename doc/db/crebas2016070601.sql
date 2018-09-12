/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2016/7/6 14:39:42                            */
/*==============================================================*/


drop table if exists disposition_plan_release;

drop table if exists disposition_plan_remark;

drop table if exists disposition_plan_response;

drop table if exists disposition_planitem_release;

drop table if exists disposition_planitem_response;

/*==============================================================*/
/* Table: disposition_plan_release                              */
/*==============================================================*/
create table disposition_plan_release
(
   id                   varchar(32) not null,
   release_enterprise_id varchar(32) not null,
   plan_release_code    varchar(32) not null,
   plan_startdate       datetime not null,
   plan_enddate         datetime not null,
   release_startdate    datetime not null,
   release_enddate      datetime not null,
   is_cfr               varchar(1) not null,
   quote_type           varchar(32) not null,
   remark               varchar(200),
   release_status       varchar(32) not null,
   versioncode          timestamp not null DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   create_by            varchar(32) not null,
   create_time          datetime not null,
   edit_by              varchar(32) not null,
   edit_time            datetime not null,
   valid                varchar(1) not null,
   primary key (id)
);

/*==============================================================*/
/* Table: disposition_plan_remark                               */
/*==============================================================*/
create table disposition_plan_remark
(
   id                   varchar(32) not null,
   plan_response_id     varchar(32) not null,
   enterprise_id        varchar(32) not null,
   order_code           varchar(32),
   remark               varchar(500),
   create_by            varchar(32) not null,
   create_time          datetime not null,
   edit_by              varchar(32) not null,
   edit_time            datetime not null,
   valid                varchar(1) not null,
   primary key (id)
);

alter table disposition_plan_remark comment '�˱���ί�д���ƻ�����˫����˽�б�ע��Ϣ���˱���Ϣֻ����������ҵ�����ܿ����������˿�����';

/*==============================================================*/
/* Table: disposition_plan_response                             */
/*==============================================================*/
create table disposition_plan_response
(
   id                   varchar(32) not null,
   release_enterprise_id varchar(32) not null,
   response_enterprise_id varchar(32) not null,
   plan_release_id      varchar(32) not null,
   plan_release_code    varchar(32) not null,
   plan_startdate       datetime not null,
   plan_enddate         datetime not null,
   is_cfr               varchar(1) not null,
   quote_type           varchar(32) not null,
   total_amount         decimal(19,6),
   remark               varchar(200),
   response_date        datetime not null,
   confirm_date         datetime not null,
   plan_status          varchar(32) not null,
   versioncode          timestamp not null DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   create_by            varchar(32) not null,
   create_time          datetime not null,
   edit_by              varchar(32) not null,
   edit_time            datetime not null,
   valid                varchar(1) not null,
   primary key (id)
);

/*==============================================================*/
/* Table: disposition_planitem_release                          */
/*==============================================================*/
create table disposition_planitem_release
(
   id                   varchar(32) not null,
   release_enterprise_id varchar(32) not null,
   enterprise_waste_id  varchar(32) not null,
   disposition_type     varchar(32) not null,
   plan_quantity        decimal(19,6) not null,
   create_by            varchar(32) not null,
   create_time          datetime not null,
   edit_by              varchar(32) not null,
   edit_time            datetime not null,
   valid                varchar(1) not null,
   primary key (id)
);

/*==============================================================*/
/* Table: disposition_planitem_response                         */
/*==============================================================*/
create table disposition_planitem_response
(
   id                   varchar(32) not null,
   plan_release_id      varchar(32) not null,
   enterprise_waste_id  varchar(32) not null,
   disposition_type     varchar(32) not null,
   plan_quantity        decimal(19,6) not null,
   response_quantity    decimal(19,6) not null,
   response_price       decimal(19,6),
   response_amount      decimal(19,6),
   create_by            varchar(32) not null,
   create_time          datetime not null,
   edit_by              varchar(32) not null,
   edit_time            datetime not null,
   valid                varchar(1) not null,
   primary key (id)
);

alter table disposition_planitem_response comment '�˱��н϶��������ࡣ����ҵ��������Ϊ������Ϣ������˫����Ӧ�������ݸ������Ա��������У����쳣����';

