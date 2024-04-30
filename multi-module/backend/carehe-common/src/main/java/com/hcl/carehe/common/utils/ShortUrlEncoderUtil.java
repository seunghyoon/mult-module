package com.hcl.carehe.common.utils;

import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ShortUrlEncoderUtil {
	
    private final int BASE62 = 62;
    
    private final String BASE62_CHAR = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    private String encoding(long param) {
        StringBuffer sb = new StringBuffer();
        while(param > 0) {
            sb.append(BASE62_CHAR.charAt((int) (param % BASE62)));
            param /= BASE62;
        }
        return sb.toString();
    }

    private long decoding(String param) {
        long sum = 0;
        long power = 1;
        for (int i = 0; i < param.length(); i++) {
            sum += BASE62_CHAR.indexOf(param.charAt(i)) * power;
            power *= BASE62;
        }
        return sum;
    }

    //신퀀스를 인코딩
    public String urlEncoder(String seqStr) throws NoSuchAlgorithmException {
        String encodeStr = encoding(Integer.valueOf(seqStr));
        log.debug("base62 encode result:" + encodeStr);
        return encodeStr;
    }
   
    //디코딩
    public long urlDecoder(String encodeStr) throws NoSuchAlgorithmException {
        long decodeVal = decoding(encodeStr);
        log.debug("base62 decode result:" + decodeVal);
        return decodeVal;
    }
}
