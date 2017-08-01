/* 
 * Copyright (c) 2016, S.F. Express Inc. All rights reserved.
 */
package com.sf.s3.operation;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.sf.s3.configuration.RESTfulStatus;
import com.sf.s3.dto.ObjectInfo;
import com.sf.s3.dto.ResultInfo;
import com.sf.s3.model.S3Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;

/**
 * 描述：
 * 
 * <pre>
 * HISTORY
 * ****************************************************************************
 *  ID   DATE				PERSON				REASON
 *  1    2017年1月3日		01107267			Create
 * ****************************************************************************
 * </pre>
 * 
 * @author 01107267 chenhui
 * @since 1.0
 */
public class ReadRequest implements OperationsHandler {
	private static final Logger L = LoggerFactory.getLogger(ReadRequest.class);
	@SuppressWarnings("finally")
	public ResultInfo ReadObjectOperation(ObjectInfo objectInfo){
		//获取Bucket名
 		String bucketName = objectInfo.getBaseInfo().getBucketName();
		//获取对象名称
		String keyName = objectInfo.getBaseInfo().getKeyName();
		ResultInfo resultInfo = new ResultInfo();
		S3Client s3Client = InitiateS3Client.getS3ClientInstace(objectInfo);
	    GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, keyName);
		//大对象根据start，end分多次读出对象，小对象一次读出对象
		if (objectInfo.getStart() != null && objectInfo.getEnd() != null) {
			if (objectInfo.getStart() > objectInfo.getEnd() || objectInfo.getStart() == objectInfo.getEnd()) {
				resultInfo.setStatusCode(RESTfulStatus.OTHER_ERROR);
				resultInfo.setErrorMessage("Object read length is illegality, start is "+ objectInfo.getStart() +", end is "+ objectInfo.getEnd() +".");
				L.error("Object read length is illegality, start is {}, end is {}.", objectInfo.getStart(), objectInfo.getEnd());
			}
			getObjectRequest.setRange(objectInfo.getStart(), objectInfo.getEnd());
		}
	    try {
	    	S3Object s3Object = s3Client.getS3().getObject(getObjectRequest);
	    	InputStream inputStream = s3Object.getObjectContent();
	    	resultInfo.setStatusCode(RESTfulStatus.SUCCESS);
	    	resultInfo.setInputStream(inputStream);
	    	L.info("Read object request is success, bucket name is {}, object is {}.", bucketName, keyName);
		} catch (AmazonServiceException ase) {
			//返回写不成功时错误信息
			resultInfo.setStatusCode(ase.getStatusCode());
			resultInfo.setErrorMessage(ase.getErrorCode());
			L.error("Read object object request is faild, status code is {}, error message is {}", ase.getStatusCode(), ase.getErrorCode());
		} catch (Exception e) {
			resultInfo.setStatusCode(RESTfulStatus.OTHER_ERROR);
			resultInfo.setErrorMessage("AmazonClientException or other error : " + e.getMessage());
			L.error("Read object object request is faild, status code is {}, error message is {}", RESTfulStatus.OTHER_ERROR, e.getMessage());
		} finally {
			return resultInfo;
		}
	}
}
