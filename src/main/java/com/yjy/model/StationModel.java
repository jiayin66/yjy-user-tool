package com.yjy.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class StationModel {
	@Excel(name = "id", orderNum = "0")
	@ApiModelProperty("id")
	private String id;
	
	@Excel(name = "��id", orderNum = "1")
	@ApiModelProperty("��id")
	private String parentId;
	
	@Excel(name = "��������", orderNum = "2")
	@ApiModelProperty("��������")
	private String stationName;
	
	@Excel(name = "��֯��������", orderNum = "3")
	@ApiModelProperty("��֯��������")
	private String orgcode;
	
	@Excel(name = "��֯��id�����ṩ����ģʽ", orderNum = "4")
	@ApiModelProperty("��֯��id�����ṩ����ģʽ")
	private String parentOrgcode;
	
	@Excel(name = "��������", orderNum = "5")
	@ApiModelProperty("��������")
	private String stationNum;
	
	@Excel(name = "����", orderNum = "6")
	@ApiModelProperty("����")
	private String longitude;
	
	@Excel(name = "ά��", orderNum = "7")
	@ApiModelProperty("ά��")
	private String latitude;
	
	@Excel(name = "�绰", orderNum = "8")
	@ApiModelProperty("�绰")
	private String tel;
	
	@Excel(name = "��ַ", orderNum = "9")
	@ApiModelProperty("��ַ")
	private String address;
	
	@Excel(name = "��̨", orderNum = "10")
	@ApiModelProperty("��̨")
	private String radioId;

	
}
