package com.itheima.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {
	/**
	 * 使用md5的算法进行加密
	 */
	public static String md5(String plainText) {
		byte[] secretBytes = null;
		try {
			secretBytes = MessageDigest.getInstance("md5").digest(
					plainText.getBytes());
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("没有md5这个算法！");
		}
		String md5code = new BigInteger(1, secretBytes).toString(16);// 16进制数字
		// 如果生成数字未满32位，需要前面补0
		for (int i = 0; i < 32 - md5code.length(); i++) {
			md5code = "0" + md5code;
		}
		return md5code;
	}

	public static void main(String[] args) {
		//System.out.println(md5("1234"));
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		System.out.println(encoder.encode("admin"));
		System.out.println(encoder.encode("1234"));
		System.out.println(encoder.encode("1234"));
		System.out.println(encoder.matches("1234","$2a$10$PdQbRLvhKQAb6Jd1aLqO.eNDvLEtgeFdO02J4eYIxo6NifZ6HWvda"));
		System.out.println(encoder.matches("1234","$2a$10$PlYJnkfjZAEhZvq/SpbWKeBiW9d5DWwT/.6m/uck8iCnmw5dBBOw."));
		System.out.println(encoder.matches("1234","$2a$10$oQtRtTYLH8f42/A5HSzcMuMgxUWj8t9ZmgayW639WxgFlOSC6Vzku"));
	}
}