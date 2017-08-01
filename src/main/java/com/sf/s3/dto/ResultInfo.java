/* 
 * Copyright (c) 2016, S.F. Express Inc. All rights reserved.
 */
package com.sf.s3.dto;

import java.io.InputStream;

/**
 * 描述：请求结果类
 * 
 * <pre>
 * HISTORY
 * ****************************************************************************
 *  ID   DATE				PERSON				REASON
 *  1    2016年12月29日		01107267			Create
 * ****************************************************************************
 * </pre>
 * 
 * @author 01107267 chenhui
 * @since 1.0
 */
public class ResultInfo {
	/**
	 * 返回码，符合RESTful规范
	 */
	private int statusCode;
	
	/**
	 * 对象请求信息
	 */
	private ObjectInfo objectInfo = null;
	
	/**
	 * 错误信息
	 */
	private String errorMessage = null;
	
	/**
	 * 读文件流
	 */
	private InputStream inputStream = null;
	
	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public ObjectInfo getObjectInfo() {
		return objectInfo;
	}

	public void setObjectInfo(ObjectInfo objectInfo) {
		this.objectInfo = objectInfo;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
}
