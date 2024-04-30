package com.hcl.carehe.common.api.common.entity.rsa;

import java.sql.Timestamp;

import org.apache.ibatis.type.Alias;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Alias("rsaKeyInfo")
public class RsaKeyInfoEntity {
	
	private Long keyId;
	private String sysGbn;
	private String publicKeyModulus;
	private String publicKeyExponent;
	private String privateEncoderKey;
	private String useYn;
	private String regId;
	private Timestamp regDt;
	private String modId;
	private Timestamp modDt;
	
	private String changUseYn;
	
	
	
}
