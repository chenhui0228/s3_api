/* 
 * Copyright (c) 2016, S.F. Express Inc. All rights reserved.
 */
package com.sf.s3.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 描述：
 * 
 * <pre>
 * HISTORY
 * ****************************************************************************
 *  ID   DATE				PERSON				REASON
 *  1    2016年12月30日		01107267			Create
 * ****************************************************************************
 * </pre>
 * 
 * @author 01107267 chenhui
 * @since 1.0
 */
public class HashUtil {
	/**
	 * 
	 * <p>
	 * 描述：  
	 * @param data
	 * @throws NoSuchAlgorithmException 
	 * @return String 
	 * </p>
	 */
	public static String md5(String data) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(data.getBytes());
		StringBuffer buf = new StringBuffer();
		byte[] bits = md.digest();
		for(int i=0;i<bits.length;i++){
			int a = bits[i];
			if(a<0) a+=256;
			if(a<16) buf.append("0");
			buf.append(Integer.toHexString(a));
		}
		return buf.toString();
	}
	
	/**
	 * 
	 * <p>
	 * 描述： 
	 * @param data
	 * @throws NoSuchAlgorithmException 
	 * @return String 
	 * </p>
	 */
	public static String sha1(String data) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA1");
		md.update(data.getBytes());
		StringBuffer buf = new StringBuffer();
		byte[] bits = md.digest();
		for(int i=0;i<bits.length;i++){
			int a = bits[i];
			if(a<0) a+=256;
			if(a<16) buf.append("0");
			buf.append(Integer.toHexString(a));
		}
		return buf.toString();
	}
}
