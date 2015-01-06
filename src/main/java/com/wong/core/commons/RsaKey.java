package com.wong.core.commons;

import java.security.interfaces.RSAPublicKey;

import org.apache.commons.codec.binary.Hex;

import com.wong.core.util.RSAUtil;


public class RsaKey {
	
	private static RSAPublicKey key = RSAUtil.getDefaultPublicKey(); 
	
	public static String getModulus(){
		return Hex.encodeHexString(key.getModulus().toByteArray());
	}
	
	public static String getExponent(){
		return Hex.encodeHexString(key.getPublicExponent().toByteArray());
	}
	
}
