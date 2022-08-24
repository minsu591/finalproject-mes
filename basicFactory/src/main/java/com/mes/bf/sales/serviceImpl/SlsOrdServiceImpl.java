package com.mes.bf.sales.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mes.bf.sales.mapper.SlsOrdMapper;
import com.mes.bf.sales.service.SlsOrdService;
import com.mes.bf.sales.vo.SlsOrdHdDtlVO;
import com.mes.bf.sales.vo.SlsOrdPlanVO;

@Service
public class SlsOrdServiceImpl implements SlsOrdService{

	@Autowired SlsOrdMapper mapper;
	
	@Override
	public List<SlsOrdHdDtlVO> findAllOrder() {
		return mapper.findAllOrder();
	}
	
	@Override
	public List<SlsOrdHdDtlVO> findOrder(String ordSdate, String ordEdate, String vendorName) {
		return mapper.findOrder(ordSdate, ordEdate, vendorName);
	}

	@Override
	public List<SlsOrdPlanVO> findOrderForPlan(String ordSdate, String ordEdate, String ordType) {
		return mapper.findOrderForPlan(ordSdate, ordEdate, ordType);
	}
}
