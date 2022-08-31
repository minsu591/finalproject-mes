package com.mes.bf.prod.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mes.bf.prod.service.PlanService;
import com.mes.bf.prod.vo.ColPlanOrdVO;
import com.mes.bf.prod.vo.ColPlanVO;
import com.mes.bf.prod.vo.PlanHdVO;
import com.mes.bf.prod.vo.PlanVO;
import com.mes.bf.sales.service.SlsOrdService;
import com.mes.bf.sales.vo.SlsOrdDtlVO;
import com.mes.bf.sales.vo.SlsOrdHdVO;
import com.mes.bf.sales.vo.SlsOrdPlanVO;

@Controller
@RequestMapping("/prod")
public class PlanController {
	@Autowired PlanService service;
	@Autowired SlsOrdService ordService;
	
	//생산 계획 조회
	@RequestMapping("/planView")
	public String planView(Model model) {
		List<ColPlanOrdVO> plans = service.findPlanOrd(null, null,null);
		model.addAttribute("plans",plans);
		return "prod/PlanView";
	}
	
	//생산 계획 조건 조회
	@GetMapping(value = "/planView/org", produces = { MediaType.APPLICATION_JSON_VALUE })
	public String planDateView(@RequestParam Map<String, String> QueryParameters, Model model){
		List<ColPlanOrdVO> plans = service.findPlanOrd(QueryParameters.get("sdate"), QueryParameters.get("edate"),QueryParameters.get("vendorCd"));
		model.addAttribute("plans",plans);
		return "prod/changePlanTable";
	}
	
	//생산 계획 관리에서 내 생산계획 조회 모달
	@GetMapping("/myPlanView")
	public ResponseEntity<List<ColPlanVO>> planMyView(@RequestParam Map<String, String> QueryParameters){
		List<ColPlanVO> plans = service.findMyPlan(QueryParameters.get("sdate"), QueryParameters.get("edate"), QueryParameters.get("empId"));
		return new ResponseEntity<List<ColPlanVO>>(plans, HttpStatus.OK);
	}
	//생산 계획 관리에서 내 생산계획 상세 조회
	@GetMapping("/myPlanView/dtl")
	public ResponseEntity<List<ColPlanOrdVO>> planMyDtlView(@RequestParam Map<String, String> QueryParameters){
		List<ColPlanOrdVO> plans = service.findPlanOrd("0", "0", QueryParameters.get("planCode"));
		return new ResponseEntity<List<ColPlanOrdVO>>(plans, HttpStatus.OK);
	}
	
	//생산 관리 페이지
	@RequestMapping("/planManage")
	public String planManage() {
		return "prod/PlanManage";
	}
	
	//생산 관리에서 미계획 주문내역 모달 조회
	@GetMapping(value="/notDoneOrd", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<SlsOrdHdVO>> notDoneOrd(@RequestParam Map<String, String> QueryParameters){
		List<SlsOrdHdVO> ords = ordService.findOrderForPlan(QueryParameters.get("sdate"), QueryParameters.get("edate"));
		return new ResponseEntity<List<SlsOrdHdVO>>(ords, HttpStatus.OK);
	}
	
	//생산 관리에서 미계획 주문내역 모달 상세 조회
	@GetMapping(value="/notDoneOrd/dtl", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<SlsOrdDtlVO>> notDoneOrdDtl(@RequestParam Map<String, String> QueryParameters){
		List<SlsOrdDtlVO> ords = ordService.findOrderForPlanDtl(QueryParameters.get("slsOrdHdNo"));
		return new ResponseEntity<List<SlsOrdDtlVO>>(ords, HttpStatus.OK);
	}
	
	//생산 지시에서 미지시 생산계획 조회
	@GetMapping(value="/planNotDoneView", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<PlanHdVO>> planNotDoneView(@RequestParam Map<String, String> QueryParameters){
		List<PlanHdVO> plans = service.findPlanInst(QueryParameters.get("sdate"), QueryParameters.get("edate"));
		return new ResponseEntity<List<PlanHdVO>>(plans, HttpStatus.OK);
	}
	//계획 상세 조회
	@GetMapping(value="/planNotDoneView/dtl", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<PlanVO>> planNotDoneViewDtl(@RequestParam String planHdCode){
		List<PlanVO> plans = service.findPlan(planHdCode);
		return new ResponseEntity<List<PlanVO>>(plans, HttpStatus.OK);
	}
	
	
	
	
	
	
	
	
}
