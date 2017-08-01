/* 
 * Copyright (c) 2016, S.F. Express Inc. All rights reserved.
 */
package com.sf.s3.test;

import java.lang.reflect.Method;

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
public class Test {
	public static void main(String[] args) throws Exception{
		System.out.println(encodeBase64("311c4131fc0a1b8f403d2d31399d6dee".getBytes()));

	}
	/***
	 * encode by Base64
	 */
	public static String encodeBase64(byte[]input) throws Exception{
		Class clazz=Class.forName("com.sun.org.apache.xerces.internal.impl.dv.util.Base64");
		Method mainMethod= clazz.getMethod("encode", byte[].class);
		mainMethod.setAccessible(true);
		 Object retObj=mainMethod.invoke(null, new Object[]{input});
		 return (String)retObj;
	}
	/***
	 * decode by Base64
	 */
	public static byte[] decodeBase64(String input) throws Exception{
		Class clazz=Class.forName("com.sun.org.apache.xerces.internal.impl.dv.util.Base64");
		Method mainMethod= clazz.getMethod("decode", String.class);
		mainMethod.setAccessible(true);
		 Object retObj=mainMethod.invoke(null, input);
		 return (byte[])retObj;
	}
}
