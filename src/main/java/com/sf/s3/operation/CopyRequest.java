/* 
 * Copyright (c) 2016, S.F. Express Inc. All rights reserved.
 */
package com.sf.s3.operation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.AmazonServiceException;
import com.sf.s3.configuration.RESTfulStatus;
import com.sf.s3.dto.ObjectCopyInfo;
import com.sf.s3.dto.ResultInfo;
import com.sf.s3.model.S3Client;

/**
 * 描述：对象拷贝，允许对象从一个bucket拷贝至其他bucket
 * 
 * <pre>
 * HISTORY
 * ****************************************************************************
 *  ID   DATE				PERSON				REASON
 *  1    2017年1月5日		01107267			Create
 * ****************************************************************************
 * </pre>
 * 
 * @author 01107267 chenhui
 * @since 1.0
 */
public class CopyRequest implements OperationsHandler{
	private static final Logger L = LoggerFactory.getLogger(CopyRequest.class);
	@SuppressWarnings("finally")
	public ResultInfo copyObjectSingleOperation(ObjectCopyInfo objectInfo) {
		String sourceBucketName = objectInfo.getSourceBaseInfo().getBucketName();
		String sourceKeyName = objectInfo.getSourceBaseInfo().getKeyName();
		String destinationBucketName = objectInfo.getDestinationBaseInfo().getBucketName();
		String destinationKeyName = objectInfo.getDestinationBaseInfo().getKeyName();
		ResultInfo resultInfo = new ResultInfo();
		S3Client s3Client = InitiateS3Client.getS3ClientInstace(objectInfo);
	    try {
	    	s3Client.getS3().copyObject(sourceBucketName, 
	    			sourceKeyName, 
	    			destinationBucketName, 
	    			destinationKeyName);
	    	resultInfo.setStatusCode(RESTfulStatus.SUCCESS);
	    	L.info("Copy object request is success, source object is {}, source bucket name is {}, destination object is {}, destination bucket name is {}.", sourceKeyName, sourceBucketName, destinationKeyName, destinationBucketName);
		} catch (AmazonServiceException ase) {
			resultInfo.setStatusCode(ase.getStatusCode());
			resultInfo.setErrorMessage(ase.getErrorCode());
			resultInfo.setObjectInfo(objectInfo);
			L.error("Copy object request is faild, status code is {}, error message is {}", ase.getStatusCode(), ase.getErrorCode());
		} catch (Exception e) {
			resultInfo.setStatusCode(RESTfulStatus.OTHER_ERROR);
			resultInfo.setErrorMessage("AmazonClientException or other error : " + e.getMessage());
			resultInfo.setObjectInfo(objectInfo);
			L.error("Copy object request is faild, status code is {}, error message is {}", RESTfulStatus.OTHER_ERROR, e.getMessage());
		}finally {
			return resultInfo;
		}
	}
}
