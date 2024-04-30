package com.hcl.carehe.common.api.code.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.hcl.carehe.common.api.common.entity.code.CommonGroupCodeEntity;

import io.lettuce.core.dynamic.annotation.Param;


@Mapper
public interface CommonCodeMapper {
	
	public List<Map<String, Object>> findCommonCodegByGoupCode(@Param("groupCode") String groupCode);
	
	public List<CommonGroupCodeEntity> findCommonCodegByGoupCodes(@Param("groupCode") String groupCode);

}
