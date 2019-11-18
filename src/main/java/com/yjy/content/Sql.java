package com.yjy.content;

import org.springframework.stereotype.Component;

public class Sql {
	public static String yjy_user="create table YJY_USER \r\n" + 
			"(\r\n" + 
			"   ID                   VARCHAR2(64)         not null,\r\n" + 
			"   NAME                 VARCHAR2(100),\r\n" + 
			"   CODE                 VARCHAR2(100),\r\n" + 
			"   SEX                  VARCHAR2(64),\r\n" + 
			"   PHONE                VARCHAR2(64),\r\n" + 
			"   OFFICE_TEL           VARCHAR2(64),\r\n" + 
			"   STATION_ID           VARCHAR2(64),\r\n" + 
			"   ORGCODE              VARCHAR2(100),\r\n" + 
			"   STATIONID            VARCHAR2(64),\r\n" + 
			"   STATIONNAME          VARCHAR2(300),\r\n" + 
			"   POLICE_POSITION      VARCHAR2(64),\r\n" + 
			"   RADIO_ID             VARCHAR2(64),\r\n" + 
			"   constraint PK_YJY_USER primary key (ID)\r\n" + 
			");\r\n" + 
			"\r\n" + 
			"comment on table YJY_USER is\r\n" + 
			"'通用的人员信息导入';\r\n" + 
			"\r\n" + 
			"comment on column YJY_USER.ID is\r\n" + 
			"'主键';\r\n" + 
			"\r\n" + 
			"comment on column YJY_USER.NAME is\r\n" + 
			"'姓名';\r\n" + 
			"\r\n" + 
			"comment on column YJY_USER.CODE is\r\n" + 
			"'警号';\r\n" + 
			"\r\n" + 
			"comment on column YJY_USER.SEX is\r\n" + 
			"'性别';\r\n" + 
			"\r\n" + 
			"comment on column YJY_USER.PHONE is\r\n" + 
			"'手机号码';\r\n" + 
			"\r\n" + 
			"comment on column YJY_USER.OFFICE_TEL is\r\n" + 
			"'办公室电话';\r\n" + 
			"\r\n" + 
			"comment on column YJY_USER.STATION_ID is\r\n" + 
			"'我们的部门id';\r\n" + 
			"\r\n" + 
			"comment on column YJY_USER.ORGCODE is\r\n" + 
			"'【兼容】组织机构代码';\r\n" + 
			"\r\n" + 
			"comment on column YJY_USER.STATIONID is\r\n" + 
			"'【兼容】 部门id，最终优化我们的';\r\n" + 
			"\r\n" + 
			"comment on column YJY_USER.STATIONNAME is\r\n" + 
			"'【兼容】部门名';\r\n" + 
			"\r\n" + 
			"comment on column YJY_USER.POLICE_POSITION is\r\n" + 
			"'存职务的id，有专门的职务表';\r\n" + 
			"\r\n" + 
			"comment on column YJY_USER.RADIO_ID is\r\n" + 
			"'固定手台';\r\n" + 
			"";
	
	public static String yjy_station="create table YJY_STATION \r\n" + 
			"(\r\n" + 
			"   ID                   VARCHAR2(64)         not null,\r\n" + 
			"   PARENTID             VARCHAR2(64),\r\n" + 
			"   STATIONNAME          VARCHAR2(200),\r\n" + 
			"   ORGCODE              VARCHAR2(64),\r\n" + 
			"   STATIONNUM           VARCHAR2(64),\r\n" + 
			"   LONGITUDE            VARCHAR2(64),\r\n" + 
			"   LATITUDE             VARCHAR2(64),\r\n" + 
			"   TEL                  VARCHAR2(64),\r\n" + 
			"   ADDRESS              VARCHAR2(200),\r\n" + 
			"   RADIO_ID             VARCHAR2(64),\r\n" + 
			"   constraint PK_YJY_STATION primary key (ID)\r\n" + 
			");\r\n" + 
			"\r\n" + 
			"comment on table YJY_STATION is\r\n" + 
			"'通用的部门信息';\r\n" + 
			"\r\n" + 
			"comment on column YJY_STATION.ID is\r\n" + 
			"'部门id';\r\n" + 
			"\r\n" + 
			"comment on column YJY_STATION.PARENTID is\r\n" + 
			"'父部门id';\r\n" + 
			"\r\n" + 
			"comment on column YJY_STATION.STATIONNAME is\r\n" + 
			"'部门名称';\r\n" + 
			"\r\n" + 
			"comment on column YJY_STATION.ORGCODE is\r\n" + 
			"'部门组织机构代码';\r\n" + 
			"\r\n" + 
			"comment on column YJY_STATION.STATIONNUM is\r\n" + 
			"'部门类型（市局SJ，分局FJ，派出所PCS，直属机构ZSJG）';\r\n" + 
			"\r\n" + 
			"comment on column YJY_STATION.LONGITUDE is\r\n" + 
			"'部门的经度，用来定位时候初始化点位';\r\n" + 
			"\r\n" + 
			"comment on column YJY_STATION.LATITUDE is\r\n" + 
			"'部门的纬度，用来定位时候初始化点';\r\n" + 
			"\r\n" + 
			"comment on column YJY_STATION.TEL is\r\n" + 
			"'部门电话';\r\n" + 
			"\r\n" + 
			"comment on column YJY_STATION.ADDRESS is\r\n" + 
			"'部门地址';\r\n" + 
			"\r\n" + 
			"comment on column YJY_STATION.RADIO_ID is\r\n" + 
			"'部门手台';\r\n" + 
			"";
}
