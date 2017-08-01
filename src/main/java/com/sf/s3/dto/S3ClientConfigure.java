/* 
 * Copyright (c) 2016, S.F. Express Inc. All rights reserved.
 */
package com.sf.s3.dto;

/**
 * 描述：
 * 
 * <pre>
 * HISTORY
 * ****************************************************************************
 *  ID   DATE				PERSON				REASON
 *  1    2017年1月14日		01107267			Create
 * ****************************************************************************
 * </pre>
 * 
 * @author 01107267 chenhui
 * @since 1.0
 */
public class S3ClientConfigure {
	/**
	 * User name, is required
	 */
	private String owner;
	
	/**
	 * access key, is required
	 */
	private String accessKey;
	
	/**
	 * secret key, is required
	 */
	private String secretKey;
	
	/**
	 * url, is required;
	 */
	private String endPoint;
	
	/**
	 * @param accessKey
	 * @param secretKey
	 * @param endPoint
	 */
	public S3ClientConfigure(String owner, String accessKey, String secretKey, String endPoint) {
		super();
		this.owner = owner;
		this.accessKey = accessKey;
		this.secretKey = secretKey;
		this.endPoint = endPoint;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getAccessKey() {
		return accessKey;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}
}
