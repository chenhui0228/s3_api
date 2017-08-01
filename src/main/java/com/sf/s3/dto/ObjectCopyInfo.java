/* 
 * Copyright (c) 2016, S.F. Express Inc. All rights reserved.
 */
package com.sf.s3.dto;

/**
 * 描述：对象拷贝数据传输类
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
public class ObjectCopyInfo extends ObjectInfo{
	/**
	 * object copy source info
	 */
	private BaseInfo sourceBaseInfo;
	
	/**
	 * object copy destination info
	 */
	private BaseInfo destinationBaseInfo;

	public BaseInfo getSourceBaseInfo() {
		return sourceBaseInfo;
	}

	public void setSourceBaseInfo(BaseInfo sourceBaseInfo) {
		this.sourceBaseInfo = sourceBaseInfo;
	}

	public BaseInfo getDestinationBaseInfo() {
		return destinationBaseInfo;
	}

	public void setDestinationBaseInfo(BaseInfo destinationBaseInfo) {
		this.destinationBaseInfo = destinationBaseInfo;
	}
}
