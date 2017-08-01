/* 
 * Copyright (c) 2016, S.F. Express Inc. All rights reserved.
 */
package com.sf.s3.operation;

import com.sf.s3.configuration.ClientConfig;
import com.sf.s3.dto.ObjectInfo;
import com.sf.s3.model.S3Client;

/**
 * 描述：请求客户端实例类
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
public class InitiateS3Client implements OperationsHandler {

	/**
	 * 描述：获取客户端实例
	 * @param objectInfo ObjectInfo中客户端信息
	 * @return 客户端实例
     */
	public static S3Client getS3ClientInstace(ObjectInfo objectInfo) {
		S3Client s3Client = null;
		//初始化S3客户端实例
		if (null == objectInfo.getS3ClientConfigure()) {
		    s3Client = new S3Client(ClientConfig.ACCESSKEY, 
		    		ClientConfig.SECRETKEY, 
		    		ClientConfig.ENDPOINT);
		}else {
			s3Client = new S3Client(objectInfo.getS3ClientConfigure().getAccessKey(), 
	    		objectInfo.getS3ClientConfigure().getSecretKey(), 
	    		objectInfo.getS3ClientConfigure().getEndPoint());
		}
		return s3Client;
	}
}
