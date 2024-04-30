package com.hcl.carehe.common.api.common.entity.code;

import java.sql.Timestamp;

import org.apache.ibatis.type.Alias;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Alias("commonCode")
@NoArgsConstructor
@AllArgsConstructor
public class CommonCodeEntity {
	
	@Column(name = "code")
	private String code;
	
	@Column(name = "grp_cd")
	private String grpCd;
	
	@Column(name = "code_nm")
	private String codeNm;
	
	@Column(name = "code_desc")
	private String codeDesc;
	
	@Column(name = "upper_cd")
	private String upperCd;
	
	@Column(name = "lev")
	private Integer lev;
	
	
	@Column(name = "sort_no")
	private Integer sortNo;
	
	@Column(name = "attr1")
	private String attr1;
	
	@Column(name = "attr2")
	private String attr2;
	
	@Column(name = "attr3")
	private String attr3;
	
	
	@Column(name = "use_yn")
	private String useYn;
	
	@Column(name = "reg_id")
	private String regId;
	
	
	@Column(name = "reg_dt")
	private Timestamp regDt;
	
	@Column(name = "mod_id")
	private String modId;
	
	
	@Column(name = "mod_dt")
	private Timestamp modDt;
	
	
}
