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
	
	@Excel(name = "父id", orderNum = "1")
	@ApiModelProperty("父id")
	private String parentId;
	
	@Excel(name = "部门名字", orderNum = "2")
	@ApiModelProperty("部门名字")
	private String stationName;
	
	@Excel(name = "组织机构代码", orderNum = "3")
	@ApiModelProperty("组织机构代码")
	private String orgcode;
	
	@Excel(name = "组织父id兼容提供这种模式", orderNum = "4")
	@ApiModelProperty("组织父id兼容提供这种模式")
	private String parentOrgcode;
	
	@Excel(name = "部门类型", orderNum = "5")
	@ApiModelProperty("部门类型")
	private String stationNum;
	
	@Excel(name = "经度", orderNum = "6")
	@ApiModelProperty("经度")
	private String longitude;
	
	@Excel(name = "维度", orderNum = "7")
	@ApiModelProperty("维度")
	private String latitude;
	
	@Excel(name = "电话", orderNum = "8")
	@ApiModelProperty("电话")
	private String tel;
	
	@Excel(name = "地址", orderNum = "9")
	@ApiModelProperty("地址")
	private String address;
	
	@Excel(name = "手台", orderNum = "10")
	@ApiModelProperty("手台")
	private String radioId;

	
}
