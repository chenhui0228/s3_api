/* 
 * Copyright (c) 2016, S.F. Express Inc. All rights reserved.
 */
package com.sf.s3.operation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.AbortMultipartUploadRequest;
import com.sf.s3.configuration.RESTfulStatus;
import com.sf.s3.dto.ObjectInfo;
import com.sf.s3.dto.ResultInfo;
import com.sf.s3.model.S3Client;

/**
 * 描述：中断上传请求
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
public class AbortRequest implements OperationsHandler {
	private static final Logger L = LoggerFactory.getLogger(AbortRequest.class);
	/**
	 * 
	 * <p>
	 * 描述：中断上传请求，分片上传时中断处理
	 * @param objectInfo
	 * @return ResultInfo 
	 * @throws 无
	 * </p>
	 */
	@SuppressWarnings("finally")
	public ResultInfo abortMultiWrite(ObjectInfo objectInfo){
		//获取bucket名
 		String bucketName = objectInfo.getBaseInfo().getBucketName();
		//获取对象名
		String keyName = objectInfo.getBaseInfo().getKeyName();
		ResultInfo resultInfo = new ResultInfo();
		S3Client s3Client = InitiateS3Client.getS3ClientInstace(objectInfo);
	    try {
	    	//中断请求
	    	AbortMultipartUploadRequest abortmultipartuploadrequest = 
	    			new AbortMultipartUploadRequest(bucketName, keyName, objectInfo.getUploadId());
	    	s3Client.getS3().abortMultipartUpload(abortmultipartuploadrequest);
	    	resultInfo.setObjectInfo(objectInfo);
	    	resultInfo.setStatusCode(RESTfulStatus.SUCCESS);
	    	L.info("Abort write object request is success, bucket name is {}, object is {}, uploadID is {}.", bucketName, keyName, objectInfo.getUploadId());
		} catch (AmazonServiceException ase) {
			resultInfo.setStatusCode(ase.getStatusCode());
			resultInfo.setErrorMessage(ase.getErrorCode());
			resultInfo.setObjectInfo(objectInfo);
			L.error("Abort write object request is faild, status code is {}, error message is {}", ase.getStatusCode(), ase.getErrorCode());
		} catch (Exception e) {
			resultInfo.setStatusCode(RESTfulStatus.OTHER_ERROR);
			resultInfo.setErrorMessage("AmazonClientException or other error : " + e.getMessage());
			resultInfo.setObjectInfo(objectInfo);
			L.error("Abort write object request is faild, status code is {}, error message is {}", RESTfulStatus.OTHER_ERROR, e.getMessage());
		}finally {
			return resultInfo;
		}
	}
}
