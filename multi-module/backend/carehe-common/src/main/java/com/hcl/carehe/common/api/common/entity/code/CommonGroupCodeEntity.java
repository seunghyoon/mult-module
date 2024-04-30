package com.hcl.carehe.common.api.common.entity.code;

import java.util.List;
import org.apache.ibatis.type.Alias;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Alias("commonGroupCode")
@NoArgsConstructor
@AllArgsConstructor
public class CommonGroupCodeEntity {
	//CommonGroupCodeEntity
	@Column(name = "group_cd")
	private String groupCd;
	
	@Column(name = "group_cd_nm")
	private String groupCdNm;
	
	@Column(name = "group_cd_desc")
	private String groupCdDesc;
	
	@Column(name = "reg_id")
	private String regId;
	
	@Column(name = "reg_dtm")
	private String regDtm;
	
	@Column(name = "chg_id")
	private String chgId;
	
	@Column(name = "chg_dtm")
	private String chgDtm;
	
	private List<CommonCodeEntity> groupCodeArray;

}
