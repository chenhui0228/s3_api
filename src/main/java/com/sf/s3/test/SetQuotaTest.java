/* 
 * Copyright (c) 2016, S.F. Express Inc. All rights reserved.
 */
package com.sf.s3.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sf.s3.configuration.ClientConfig;
import com.sf.s3.dto.BaseInfo;
import com.sf.s3.dto.ObjectInfo;
import com.sf.s3.dto.ResultInfo;
import com.sf.s3.dto.S3ClientConfigure;
import com.sf.s3.operation.CreateBucketRequest;

/**
 * 描述：
 * 
 * <pre>
 * HISTORY
 * ****************************************************************************
 *  ID   DATE				PERSON				REASON
 *  1    2017年1月12日		01107267			Create
 * ****************************************************************************
 * </pre>
 * 
 * @author 01107267 chenhui
 * @since 1.0
 */
public class SetQuotaTest {
	private static final Logger L = LoggerFactory.getLogger(SetQuotaTest.class);
	public static void main(String[] args){
		
		ObjectInfo objectInfo = new ObjectInfo();
		S3ClientConfigure clientConfigure = new S3ClientConfigure("hangz-01", 
				ClientConfig.ACCESSKEY, 
				ClientConfig.SECRETKEY, 
				ClientConfig.ENDPOINT);
		objectInfo.setS3ClientConfigure(clientConfigure);
		BaseInfo baseInfo = new BaseInfo();
		baseInfo.setBucketName("test-create-bucket-name-002");
		objectInfo.setBaseInfo(baseInfo);
		ResultInfo info = new CreateBucketRequest().CreateBucketOperation(objectInfo);
		L.info(info.getErrorMessage());
	}
	public static void setQuota(){
		
	}
}
