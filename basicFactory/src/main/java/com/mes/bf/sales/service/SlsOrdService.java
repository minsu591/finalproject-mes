package com.mes.bf.sales.service;

import java.util.List;

import com.mes.bf.sales.vo.SlsOrdDtlVO;
import com.mes.bf.sales.vo.SlsOrdHdDtlVO;
import com.mes.bf.sales.vo.SlsOrdHdVO;
import com.mes.bf.sales.vo.SlsOrdPlanVO;

public interface SlsOrdService {
	
	//주문내역 전체조회
	List<SlsOrdHdDtlVO> findAllOrder();
	
	//일자, 거래처별 주문조회
	List<SlsOrdHdDtlVO> findOrder(String ordSdate, String ordEdate, String vendorName);
	
	//주문관리에서 주문내역 조회 모달
	List<SlsOrdHdVO> findOrderModal(String ordSdate, String ordEdate);
	
	//주문관리에서 주문내역 상세조회
	List<SlsOrdHdDtlVO> findDtlOrder (String slsOrdHdNo);
	
	//생산계획관리의 미계획 주문내역 조회
	List<SlsOrdPlanVO> findOrderForPlan(String ordSdate, String ordEdate, String ordType);
	
	//주문관리 수정
	void orderUpdate(String priKey, String updCol, String updCont);
	
	//주문관리 삭제
	void orderDelete(String priKey);
	
	//주문관리 헤더등록
	void orderInsertHd(SlsOrdHdVO vo);
	
	//주문관리 바디등록(신규등록)
	void orderInsertDtl(List<SlsOrdDtlVO> vo);
	
	//주문관리 바디등록(기존 주문내역 추가등록)
	void orderDtlAddInsert(SlsOrdDtlVO vo);
}
