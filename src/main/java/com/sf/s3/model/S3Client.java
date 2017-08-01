/* 
 * Copyright (c) 2016, S.F. Express Inc. All rights reserved.
 */
package com.sf.s3.model;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;

/**
 * 描述：Amazon S3 Client 类
 * 
 * <pre>
 * HISTORY
 * ****************************************************************************
 *  ID   DATE				PERSON				REASON
 *  1    2016年12月27日		01107267			Create
 * ****************************************************************************
 * </pre>
 * 
 * @author 01107267 chenhui
 * @since 1.0
 */
public class S3Client {
	
	private AWSCredentials credentials;
	private ClientConfiguration configuration;
	private AmazonS3 s3;
	
	private void setCredentials(String ak, String sk) {
		this.credentials = new BasicAWSCredentials(ak, sk);
	}
	
	private void setConfiguration() {
		this.configuration = new ClientConfiguration();
		this.configuration.setProtocol(Protocol.HTTP);
	}
	
	public AmazonS3 getS3() {
		return s3;
	}
	private void setS3() {
		this.s3 = new AmazonS3Client(credentials,configuration);
	}
	
	public S3Client(String accessKey, String secretKey, String endPoint) {
		//鉴权
		setCredentials(accessKey, secretKey);
		//HTTP请求方式
		setConfiguration();
		setS3();
		s3.setEndpoint(endPoint);
	}
}
