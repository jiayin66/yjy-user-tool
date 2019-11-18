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
			"'ͨ�õ���Ա��Ϣ����';\r\n" + 
			"\r\n" + 
			"comment on column YJY_USER.ID is\r\n" + 
			"'����';\r\n" + 
			"\r\n" + 
			"comment on column YJY_USER.NAME is\r\n" + 
			"'����';\r\n" + 
			"\r\n" + 
			"comment on column YJY_USER.CODE is\r\n" + 
			"'����';\r\n" + 
			"\r\n" + 
			"comment on column YJY_USER.SEX is\r\n" + 
			"'�Ա�';\r\n" + 
			"\r\n" + 
			"comment on column YJY_USER.PHONE is\r\n" + 
			"'�ֻ�����';\r\n" + 
			"\r\n" + 
			"comment on column YJY_USER.OFFICE_TEL is\r\n" + 
			"'�칫�ҵ绰';\r\n" + 
			"\r\n" + 
			"comment on column YJY_USER.STATION_ID is\r\n" + 
			"'���ǵĲ���id';\r\n" + 
			"\r\n" + 
			"comment on column YJY_USER.ORGCODE is\r\n" + 
			"'�����ݡ���֯��������';\r\n" + 
			"\r\n" + 
			"comment on column YJY_USER.STATIONID is\r\n" + 
			"'�����ݡ� ����id�������Ż����ǵ�';\r\n" + 
			"\r\n" + 
			"comment on column YJY_USER.STATIONNAME is\r\n" + 
			"'�����ݡ�������';\r\n" + 
			"\r\n" + 
			"comment on column YJY_USER.POLICE_POSITION is\r\n" + 
			"'��ְ���id����ר�ŵ�ְ���';\r\n" + 
			"\r\n" + 
			"comment on column YJY_USER.RADIO_ID is\r\n" + 
			"'�̶���̨';\r\n" + 
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
			"'ͨ�õĲ�����Ϣ';\r\n" + 
			"\r\n" + 
			"comment on column YJY_STATION.ID is\r\n" + 
			"'����id';\r\n" + 
			"\r\n" + 
			"comment on column YJY_STATION.PARENTID is\r\n" + 
			"'������id';\r\n" + 
			"\r\n" + 
			"comment on column YJY_STATION.STATIONNAME is\r\n" + 
			"'��������';\r\n" + 
			"\r\n" + 
			"comment on column YJY_STATION.ORGCODE is\r\n" + 
			"'������֯��������';\r\n" + 
			"\r\n" + 
			"comment on column YJY_STATION.STATIONNUM is\r\n" + 
			"'�������ͣ��о�SJ���־�FJ���ɳ���PCS��ֱ������ZSJG��';\r\n" + 
			"\r\n" + 
			"comment on column YJY_STATION.LONGITUDE is\r\n" + 
			"'���ŵľ��ȣ�������λʱ���ʼ����λ';\r\n" + 
			"\r\n" + 
			"comment on column YJY_STATION.LATITUDE is\r\n" + 
			"'���ŵ�γ�ȣ�������λʱ���ʼ����';\r\n" + 
			"\r\n" + 
			"comment on column YJY_STATION.TEL is\r\n" + 
			"'���ŵ绰';\r\n" + 
			"\r\n" + 
			"comment on column YJY_STATION.ADDRESS is\r\n" + 
			"'���ŵ�ַ';\r\n" + 
			"\r\n" + 
			"comment on column YJY_STATION.RADIO_ID is\r\n" + 
			"'������̨';\r\n" + 
			"";
}
