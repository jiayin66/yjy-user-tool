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
	
	@Excel(name = "警员姓名", orderNum = "1")
	private String policeName;
	
	@Excel(name = "警员编号", orderNum = "2")
	private String policeCode;
	
	@Excel(name = "组织机构代码", orderNum = "3",width=20)
	@ApiModelProperty("部门的组织机构代码")
	private String orgcode;
	
	@Excel(name = "第三方厂家的部门id", orderNum = "4",width=25)
	@ApiModelProperty("第三方厂家的部门id")
	private String otherStationId;
		
	@ApiModelProperty("我们的部门id")
	private String stationId;
	
	@Excel(name = "第三方厂家的部门name", orderNum = "5",width=25)
	@ApiModelProperty("第三方厂家的部门名")
	private String otherStationName;
	
	@Excel(name = "性别", orderNum = "6")
	@ApiModelProperty("性别")
	private String sex;
	
	@Excel(name = "手机号", orderNum = "7")
	@ApiModelProperty("手机号")
	private String phone;
	
	@Excel(name = "办公室电话", orderNum = "8")
	@ApiModelProperty("手机号")
	private String officeTel;
	
	@Excel(name = "职务", orderNum = "9")
	@ApiModelProperty("职务")
	private String policePosition;
	
	@Excel(name = "手台", orderNum = "10")
	@ApiModelProperty("手台")
	private String radio;
	
	@ApiModelProperty("排序")
	private Integer sort;
	
}
