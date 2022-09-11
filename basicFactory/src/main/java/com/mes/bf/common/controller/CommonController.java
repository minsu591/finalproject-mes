package com.mes.bf.common.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mes.bf.common.Criteria;
import com.mes.bf.common.PageDTO;
import com.mes.bf.common.service.CommonService;
import com.mes.bf.eqp.vo.VfindMchnVO;
import com.mes.bf.prod.service.ProcService;

@Controller
@RequestMapping("/common")
public class CommonController {

	@Autowired
	CommonService service;
	@Autowired
	ProcService procservice;

	// 설비명 조회
	@GetMapping(value = "/findmchn", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<VfindMchnVO>> findEmp(@RequestParam Map<String, String> QueryParameters) {
		List<VfindMchnVO> list = procservice.findMchn(QueryParameters.get("mchnCode"), QueryParameters.get("mchnName"));
		return new ResponseEntity<List<VfindMchnVO>>(list, HttpStatus.OK);// 결과값,상태값 OK = 200, NOTFOUND = 404
	}

	@RequestMapping("/mchnList")
	public String getMchnList(Model model, @ModelAttribute("cri") Criteria cri) {
		// 페이징
		// 전체 건수
		int total = service.getMchnTotalCount(cri);
		System.out.println(service.getMchnTotalCount(cri));
		cri.setAmount(10); // 한페이지당 10개씩 설정
		model.addAttribute("pageMaker", new PageDTO(cri, total));
		model.addAttribute("mchnList", service.findMchn(cri));
		// model.addAttribute("criteria", cri); 자동 등록 됨
		return "common/mchnList";
	}

}
