package com.mes.bf.eqp.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mes.bf.cmn.service.LineService;
import com.mes.bf.eqp.service.InspcService;
import com.mes.bf.eqp.vo.InspcVO;
import com.mes.bf.eqp.vo.MchnVO;
import com.mes.bf.eqp.vo.VfindMchnVO;
import com.mes.bf.prod.service.ProcService;

@Controller
@RequestMapping("/eqp")
public class InspcController {
	
	@Autowired InspcService service;
	@Autowired LineService lineservice;
	
	//점검관리
	@RequestMapping("/inspcManage")
	public ModelAndView inspcManage() {
		ModelAndView mav = new ModelAndView("eqp/InspcManage");
		return mav;
	}
	
	//점검 등록
	@PostMapping("/inspc/insert")
	public ResponseEntity<Integer> inspcInsert(@RequestBody InspcVO vo){
		int result = service.inspcInsert(vo);
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}
	
	//점검 수정
	@PostMapping(value = "/inspc/update", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Integer> inspcUpdate(@RequestParam Map<String, String> QueryParameters) {
		int result = service.inspcUpdate(QueryParameters.get("priKey"), QueryParameters.get("updCol"), QueryParameters.get("updCont"));
		return new ResponseEntity<Integer>(result,HttpStatus.OK);
	}
	
	//설비점검내역(모달창)
	@GetMapping(value = "/findInspcList", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<InspcVO>> findInspcList(@RequestParam Map<String, String> QueryParameters) {
		List<InspcVO> list = service.findInspcList(QueryParameters.get("inspcSdate"), QueryParameters.get("inspcEdate"));
		return new ResponseEntity<List<InspcVO>>(list, HttpStatus.OK);
	}
	
	//설비점검대상조회(모달창)
	@GetMapping(value = "/findNxtDate", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<InspcVO>> findNxtDate(){
		List<InspcVO> list = service.findNxtDate();
		return new ResponseEntity<List<InspcVO>>(list, HttpStatus.OK);
	}
	
	//점검조회
	@RequestMapping("/inspcList")
	public String MchnListPage(Model model) {
		List<InspcVO> inspcs = service.listInspc();
		model.addAttribute("inspcs", inspcs);
		return "eqp/InspcList";
	}
	//점검상세조회
	@GetMapping(value="/inspcList/find", produces = { MediaType.APPLICATION_JSON_VALUE })
	public String MchnListFindPage(@RequestParam Map<String, String> QueryParameters, Model model) {
		List<InspcVO> inspcs = service.findListInspc(QueryParameters.get("sdate"), QueryParameters.get("edate"), QueryParameters.get("mchnCode"));
		model.addAttribute("inspcs", inspcs);
		return "eqp/ChangeInspcListTable";
	}
	
	// 설비명 조회
	@PostMapping(value = "/findmchn", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<MchnVO>> findMchn(@RequestBody MchnVO mchn) {
		List<MchnVO> list = lineservice.listMchn(mchn);
		return new ResponseEntity<List<MchnVO>>(list, HttpStatus.OK);// 결과값,상태값 OK = 200, NOTFOUND = 404
	}
}
