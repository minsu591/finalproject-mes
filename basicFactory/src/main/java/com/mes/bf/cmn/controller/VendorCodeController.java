package com.mes.bf.cmn.controller;

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

import com.mes.bf.cmn.service.VendorService;
import com.mes.bf.cmn.vo.VendorCodeVO;

@Controller
@RequestMapping("/cmn")
public class VendorCodeController {
	@Autowired VendorService vendService;
	
	@RequestMapping("/vendorCode")
	public String vendorCodePage(Model model) {
		List<VendorCodeVO> vendors = vendService.listVendor(null);
		model.addAttribute("vendors",vendors);
		return "cmn/VendorCode";
	}
	
	@GetMapping(value = "/vendorCode/name", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<VendorCodeVO>> vendorCodeName(@RequestParam Map<String, String> QueryParameters){
		List<VendorCodeVO> vends = vendService.listVendor(QueryParameters.get("vendorName"));
		return new ResponseEntity<List<VendorCodeVO>>(vends, HttpStatus.OK);
	}
}
