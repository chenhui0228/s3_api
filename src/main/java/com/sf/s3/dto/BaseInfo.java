/* 
 * Copyright (c) 2016, S.F. Express Inc. All rights reserved.
 */
package com.sf.s3.dto;

/**
 * 描述：bucket,object名称类
 * 
 * <pre>
 * HISTORY
 * ****************************************************************************
 *  ID   DATE				PERSON				REASON
 *  1    2017年1月9日		01107267			Create
 * ****************************************************************************
 * </pre>
 * 
 * @author 01107267 chenhui
 * @since 1.0
 */
public class BaseInfo{
	/**
	 * bucketName，文件实际存放的桶，命名规则：bucket-{xxxx}
	 */
	private String bucketName = null;
	
	/**
	 * 对象名，对应唯一的uuid
	 */
	private String keyName = null;

	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}	
}