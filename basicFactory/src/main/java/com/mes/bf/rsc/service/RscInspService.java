package com.mes.bf.rsc.service;

import java.util.List;

import com.mes.bf.rsc.vo.RscInspVO;
import com.mes.bf.rsc.vo.RscOrderVO;

public interface RscInspService {
	
	//발주코드 1건 조회 해서 입력
	List<RscOrderVO> inspListLoad(String rscOrderCode, String rscOrderTitle, String rscOrderDate);
	
	//자재 검사 insert&update
	Integer inspInsert(RscInspVO vo);
	Integer inspUpdate(RscInspVO vo);
	
	List<RscInspVO> inspList(String rscInspCode, String rscCdCode, String rscInspSDate, String rscInspEDate);
	
}
