/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.common.utils;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 封装各种生成唯一性ID算法的工具类.
 * @author jhmis
 * @version 2016-01-15
 */
public class IdGen {

	private static SecureRandom random = new SecureRandom();
	
	/**
	 * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
	 */
	public static String uuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	/**
	 * 使用SecureRandom随机生成Long. 
	 */
	public static long randomLong() {
		return Math.abs(random.nextLong());
	}

	/**
	 * 基于Base62编码的SecureRandom随机生成bytes.
	 */
	public static String randomBase62(int length) {
		byte[] randomBytes = new byte[length];
		random.nextBytes(randomBytes);
		return Encodes.encodeBase62(randomBytes);
	}
	public static String makeBillCode(){
		String date = new SimpleDateFormat("yyMMdd").format(new Date());
		int hash = Math.abs(UUID.randomUUID().toString().replaceAll("-", "").hashCode());
		String hashStr = StringUtils.leftPad(String.valueOf(hash), 10, "0");
		StringBuffer bf = new StringBuffer();
		bf.append(date).append(hashStr);
		return bf.toString();
	}
	public static String  makePaySn(){
		String dateStr = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
		int nextInt = (int)(Math.random()*900)+100;
		return dateStr+nextInt;
	}
}
