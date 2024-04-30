package com.hcl.carehe.common.api.code.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hcl.carehe.common.api.code.service.CommonCodeService;
import com.hcl.carehe.common.message.GenericMessage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/carehe/v1.0")
public class CommonCodeController {
	
	@Autowired
	private CommonCodeService commonCodeService;
	
	
	@GetMapping(value = {"/group/{groupCode}/code/info"})
	public GenericMessage findGroupCodceInfo(GenericMessage message, 
			@PathVariable("groupCode") String groupCode){
		log.debug("CommonCodeController.findGroupCodceInfo  groupCode => {}", groupCode);
		message.setData(commonCodeService.findCodeGroupInfoService(groupCode));
		
        return message;
    }
}
