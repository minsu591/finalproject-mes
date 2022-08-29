package com.mes.bf.rsc.vo;

import java.sql.Date;

import lombok.Data;

@Data
public class RscOutVO {

	private String rscOutCode;
	private Date rscOutDate;
	private String processPerfomNo;
	private String rscCdCode;
	private String rscCdName;
	private String rscLotNo;
	private int rscOutVol;
	private int rscOutCls;
	private String empName;
	private String vendCdNm;
	
}
