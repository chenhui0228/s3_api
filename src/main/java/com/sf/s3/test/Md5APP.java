/* 
 * Copyright (c) 2016, S.F. Express Inc. All rights reserved.
 */
package com.sf.s3.test;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * 描述：
 * 
 * <pre>
 * HISTORY
 * ****************************************************************************
 *  ID   DATE				PERSON				REASON
 *  1    2017年1月11日		01107267			Create
 * ****************************************************************************
 * </pre>
 * 
 * @author 01107267 chenhui
 * @since 1.0
 */
public class Md5APP {

	/**
	 * <p>
	 * 描述： 
	 * @param @param args 
	 * @return void 
	 * @throws NoSuchAlgorithmException 
	 * @throws 
	 * </p>
	 */
	public static void main(String[] args) throws NoSuchAlgorithmException {
		String md5Hex1 = "311c4131fc0a1b8f403d2d31399d6dee";
		String md5Hex2 = "f69bdc5969c4a02bf6c544f6cdd76c06";
		String md5Hex3 = "d10ec7d896eb3eaba72dfe3390a49e73";
		String md5HexTotal = "321398cb4fbebf2d885c24f2f8398226";
		byte[] b1 = hexString2Bytes(md5Hex1);
		System.out.println(md5Hex1.getBytes());
		byte[] b2 = hexString2Bytes(md5Hex2);
		byte[] b3 = hexString2Bytes(md5Hex3);
		byte[] tmp1 = Arrays.copyOf(b1, b1.length + b2.length);
		System.arraycopy(b2, 0, tmp1, b1.length, b2.length);
		byte[] tmp2 = Arrays.copyOf(tmp1, tmp1.length + b2.length);
		System.arraycopy(b3, 0, tmp2, tmp1.length, b3.length);
		System.out.println(tmp2.toString());
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(tmp2);		
		StringBuffer buf = new StringBuffer();
		byte[] bits = md.digest(); 
		for(int i=0;i<bits.length;i++){
			int a = bits[i];
			if(a<0) a+=256;
			if(a<16) buf.append("0");
			buf.append(Integer.toHexString(a));
		}
		System.out.println(buf.toString());
	}
	public static String hexString2String(String hexString){
		String r = bytes2String(hexString2Bytes(hexString));
		return r;
	}
	public static byte[] hexString2Bytes(String hexString){
		if (null == hexString || hexString.equals("")) {
			return null;
		} 
		else if (hexString.length() % 2 != 0) {
			return null;
		}
		else {
			hexString = hexString.toUpperCase();
			int len = hexString.length() / 2;
			byte[] b = new byte[len];
			char[] hc = hexString.toCharArray();
			for (int i = 0; i < len; i++) {
				int p = 2*i;
				b[i] = (byte)(charToByte(hc[p]) << 4 | charToByte(hc[p+1]));
			}
			return b;
		}
	}
	public static byte charToByte(char c) {
		return (byte)"0123456789ABCDEF".indexOf(c);
	}
	@SuppressWarnings("finally")
	public static String bytes2String(byte[] b) {
		String r = null;
		try {
			r = new String (b,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			r = null;
		} finally {
			return r;
		}
	}
}
