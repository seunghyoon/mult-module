package com.hcl.carehe.common.api.code.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.carehe.common.api.code.mapper.CommonCodeMapper;
import com.hcl.carehe.common.api.code.service.CommonCodeService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CommonCodeServiceImpl implements CommonCodeService{
	
	@Autowired
	private CommonCodeMapper commonCodeMapper;
	
	@Override
	public Map<String, Object> findCodeGroupInfoService(String groupCode) {
		log.debug("CommonCodeServiceImpl.findCodeGroupInfoService groupCode => {}", groupCode);
		Map<String, Object> resData = new HashMap<>();
		//resData.put("codeGroupInfo", commonCodeMapper.findCommonCodegByGoupCode(groupCode));
		resData.put("codeGroupInfos", commonCodeMapper.findCommonCodegByGoupCodes(groupCode));
		// TODO Auto-generated method stub
		return resData;
	}
	
}
