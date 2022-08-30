package com.mes.bf.rsc.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mes.bf.rsc.service.RscOutService;
import com.mes.bf.rsc.vo.RscOutVO;

@Controller
@RequestMapping("/rsc")
public class RscOutController {
	
	@Autowired RscOutService rscOutService;

	//출고
	@RequestMapping("/out")
	public ModelAndView out() {
		return new ModelAndView("rsc/out");
	}
	
	//출고입력&수정
	@RequestMapping(value="/outInAndUp", method = RequestMethod.POST)
	@ResponseBody
	public String outInAndUp(@RequestBody List<RscOutVO> list) {
		int insert = 0;
		int update = 0;
		for(int i = 0; i < list.size() ; i++) {
			if(list.get(i).getRscOutCode() == null) {
				rscOutService.OutInsert(list.get(i));	
				insert++;
			}else {
				rscOutService.OutUpdate(list.get(i));
				update++;
			}
		}
		System.out.println("insert"+ insert + "update" + update);
		return "rsc/out";
	}
	
	@RequestMapping("/outList")
	public void outList(Model model) {
		List<RscOutVO> nList = rscOutService.normalOutList(null, null, null, null);
		List<RscOutVO> eList = rscOutService.exceptOutList(null, null, null, null);
		model.addAttribute("nList", nList);
		model.addAttribute("eList", eList);
	}
	
	@RequestMapping(value = "/outListTable", produces = { MediaType.APPLICATION_JSON_VALUE })
	public String outTableList(@RequestParam Map<String, String> QueryParameters, Model model) {
		System.out.println("진입확인");
		List<RscOutVO> nList = rscOutService.normalOutList(QueryParameters.get("rscOutCode"), QueryParameters.get("rscCdCode"), 
														QueryParameters.get("rscOutSDate"), QueryParameters.get("rscOutEDate"));
		List<RscOutVO> eList = rscOutService.exceptOutList(QueryParameters.get("rscOutCode"), QueryParameters.get("rscCdCode"), 
														QueryParameters.get("rscOutSDate"), QueryParameters.get("rscOutEDate"));
		model.addAttribute("nList", nList);
		model.addAttribute("eList", eList);
		return "rsc/table/OutListTable";
	}
}
