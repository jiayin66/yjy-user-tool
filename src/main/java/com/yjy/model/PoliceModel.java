package com.yjy.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class PoliceModel {
	@Excel(name = "id", orderNum = "0")
	private String id;
	
	private String userId;
	
	@Excel(name = "��Ա����", orderNum = "1")
	private String policeName;
	
	@Excel(name = "��Ա���", orderNum = "2")
	private String policeCode;
	
	@Excel(name = "��֯��������", orderNum = "3",width=20)
	@ApiModelProperty("���ŵ���֯��������")
	private String orgcode;
	
	@Excel(name = "���������ҵĲ���id", orderNum = "4",width=25)
	@ApiModelProperty("���������ҵĲ���id")
	private String otherStationId;
		
	@ApiModelProperty("���ǵĲ���id")
	private String stationId;
	
	@Excel(name = "���������ҵĲ���name", orderNum = "5",width=25)
	@ApiModelProperty("���������ҵĲ�����")
	private String otherStationName;
	
	@Excel(name = "�Ա�", orderNum = "6")
	@ApiModelProperty("�Ա�")
	private String sex;
	
	@Excel(name = "�ֻ���", orderNum = "7")
	@ApiModelProperty("�ֻ���")
	private String phone;
	
	@Excel(name = "�칫�ҵ绰", orderNum = "8")
	@ApiModelProperty("�ֻ���")
	private String officeTel;
	
	@Excel(name = "ְ��", orderNum = "9")
	@ApiModelProperty("ְ��")
	private String policePosition;
	
	@Excel(name = "��̨", orderNum = "10")
	@ApiModelProperty("��̨")
	private String radio;
	
	@ApiModelProperty("����")
	private Integer sort;
	
}
