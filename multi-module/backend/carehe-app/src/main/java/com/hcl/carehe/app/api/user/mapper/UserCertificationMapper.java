package com.hcl.carehe.app.api.user.mapper;

import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import io.lettuce.core.dynamic.annotation.Param;

@Mapper
public interface UserCertificationMapper {
	
	public Map<String, Object> findUserByUserId(@Param("id") String id);	

}
