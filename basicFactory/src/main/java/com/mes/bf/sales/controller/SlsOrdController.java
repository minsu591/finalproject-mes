package com.mes.bf.sales.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.mes.bf.cmn.vo.VendorCodeVO;
import com.mes.bf.prod.service.InstructionService;
import com.mes.bf.prod.vo.FindEmpVO;
import com.mes.bf.sales.service.SlsOrdService;
import com.mes.bf.sales.vo.SlsOrdDtlVO;
import com.mes.bf.sales.vo.SlsOrdHdDtlVO;
import com.mes.bf.sales.vo.SlsOrdHdVO;
import com.mes.bf.sales.vo.SlsOrdInsertVO;

@RestController
@RequestMapping("/sls")
public class SlsOrdController {
	
	@Autowired SlsOrdService service;
	@Autowired InstructionService instService;
	
	//주문조회 페이지 이동
	@RequestMapping("/ord")
	public ModelAndView order() {
		ModelAndView mav = new ModelAndView("sales/order");
		return mav;
	}

	//주문내역 전체 조회
	@GetMapping("/findAllOrder")
	public List<SlsOrdHdDtlVO> findAllOrder() {
		List<SlsOrdHdDtlVO> list = service.findAllOrder();
		return list;
	}
	
	//주문내역 조건 조회
	@GetMapping(value = "/findOrder")
	public List<SlsOrdHdDtlVO> findOrder(@RequestParam Map<String, String> param) {
		List<SlsOrdHdDtlVO> list = service.findOrder(param.get("ordSdate"),
													 param.get("ordEdate"),
													 param.get("vendorName"));
		return list;
	}
	
	//주문관리 페이지 이동
	@RequestMapping("/ordManage")
	public ModelAndView orderManage() {
		ModelAndView mav = new ModelAndView("sales/orderManage");
		return mav;
	}
	
	//주문관리 등록(신규 주문등록)
	@PostMapping("/ordManage/hdDtlInsert")
	public void orderHdDtlInsert(@RequestBody SlsOrdInsertVO vo) {
		//주문 헤더 등록
		service.orderInsertHd(vo.getSlsOrdHdVO());
		
		//주문 디테일 등록
		for(SlsOrdDtlVO ordDtlVO :vo.getSlsOrdDtlVO()) {
			service.orderInsertDtl(ordDtlVO);
		}
	}
	
	//주문관리 등록(기존 주문내역에 추가 등록할 경우)
	@PostMapping("/ordManage/dtlInsert")
	public void orderDtlInsert(@RequestBody SlsOrdDtlVO vo) {
		service.orderDtlAddInsert(vo);
	}
	
	//주문관리 수정
	@PutMapping("/ordManage/update")
	public void orderUpdate(@RequestParam Map<String, String> params) {
					service.orderUpdate(params.get("priKey"),
										 params.get("updCol"),
										 params.get("updCont"));
	}
	
	//주문관리 삭제
	@DeleteMapping("/ordManage/delete")
	public void orderDelete(@RequestParam(value="delList[]") List<String> delList) {
		System.out.println("들어와?");
		service.orderDelete(delList);
	}
	
	//주문관리에서 주문내역 조회 모달
	@GetMapping("/orderView")
	public List<SlsOrdHdVO> orderView(@RequestParam Map<String, String> param) {
		List<SlsOrdHdVO> list = service.findOrderModal(param.get("ordSdate"),
				 								  	   param.get("ordEdate"));
		System.out.println(list);
		return list;
	}
	
	//주문관리에서 주문상세 조회
	@GetMapping("/orderView/dtl")
	public List<SlsOrdHdDtlVO> orderDtlView(@RequestParam Map<String, String> param){
		List<SlsOrdHdDtlVO> list = service.findDtlOrder(param.get("slsOrdHdNo"));
		return list;
	}
	
	//거래처 전체조회
	@GetMapping(value = "/findvendorcode", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<VendorCodeVO>> findAllVInstruction(@RequestParam Map<String,String> queryParameters) {
		List<VendorCodeVO> list = instService.findVendorCode(queryParameters.get("vendorCode"),queryParameters.get("vendCdClfy"));
		return new ResponseEntity<List<VendorCodeVO>>(list, HttpStatus.OK);// 결과값,상태값 OK = 200, NOTFOUND = 404
	}
	
	//담당자 조회
	@GetMapping(value = "/findemp", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<FindEmpVO>> findEmp(@RequestParam Map<String, String> QueryParameters) {
		List<FindEmpVO> list = instService.findEmp(QueryParameters.get("empName"));
		return new ResponseEntity<List<FindEmpVO>>(list, HttpStatus.OK);// 결과값,상태값 OK = 200, NOTFOUND = 404
	}
}
