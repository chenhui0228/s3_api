/* 
 * Copyright (c) 2016, S.F. Express Inc. All rights reserved.
 */
package com.sf.s3.operation;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.*;
import com.sf.s3.configuration.ConstantConfig;
import com.sf.s3.configuration.RESTfulStatus;
import com.sf.s3.dto.ObjectInfo;
import com.sf.s3.dto.ResultInfo;
import com.sf.s3.model.S3Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述：对象写请求接口
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
public class WriteRequest implements OperationsHandler {
	private static final Logger L = LoggerFactory.getLogger(WriteRequest.class);
	
	/**
	 * <p>
	 * 描述： 单次写对象向操作
	 * @param objectInfo，写入对象的信息
	 * @param in，写入文件流
	 * @return ResultInfo，返回写请求结果
	 * @throws 
	 * </p>
	 */
	@SuppressWarnings("finally")
	public ResultInfo writeObjectSingleOperation(ObjectInfo objectInfo, InputStream in) {
		//获取bucket name
		String bucketName = objectInfo.getBaseInfo().getBucketName();
		//获取object name
		String keyName = objectInfo.getBaseInfo().getKeyName();
		ResultInfo resultInfo = new ResultInfo();
		S3Client s3Client = InitiateS3Client.getS3ClientInstace(objectInfo);
	    try {
	    	if (null == objectInfo.getObjectMetadata()) {
	    		ObjectMetadata objectMetadata = new ObjectMetadata();
	    		objectInfo.setObjectMetadata(objectMetadata);
	    	}
			//对象MD5校验信息
	    	objectInfo.getObjectMetadata().setContentMD5(objectInfo.getMd5Digest());
			//实例化写对象请求
	    	PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, 
	    			keyName, 
	    			in, 
	    			objectInfo.getObjectMetadata());
	    	//写入分布式存储
	    	s3Client.getS3().putObject(putObjectRequest);
	    	//写成功返回状态码
	    	resultInfo.setStatusCode(RESTfulStatus.SUCCESS);
	    	L.info("Put object request is success, bucket is {}, object is {}.", bucketName, keyName);
		} catch (AmazonServiceException ase) {
			//返回写不成功时错误信息
			resultInfo.setStatusCode(ase.getStatusCode());
			resultInfo.setErrorMessage(ase.getErrorCode());
			resultInfo.setObjectInfo(objectInfo);
			L.error("Put object request is faild, status code is {}, error message is {}", ase.getStatusCode(), ase.getErrorCode());
		} catch (Exception e) {
			resultInfo.setStatusCode(RESTfulStatus.OTHER_ERROR);
			resultInfo.setErrorMessage("AmazonClientException or other error : " + e.getMessage());
			resultInfo.setObjectInfo(objectInfo);
			L.error("Put object request is faild, status code is {}, error message is {}", RESTfulStatus.OTHER_ERROR, e.getMessage());
		} finally {
			return resultInfo;
		}
	}
	
	/**
	 * <p>
	 * 描述：初始化 分片写对象请求
	 * @param objectInfo 写的对象信息
	 * @return ResultInfo 结果信息
	 * @throws 无
	 * </p>
	 */
	@SuppressWarnings("finally")
	public ResultInfo initiateMultipartWriteOperation(ObjectInfo objectInfo){
		//获取Bucket名
		String bucketName = objectInfo.getBaseInfo().getBucketName();
		//获取对象名称
		String keyName = objectInfo.getBaseInfo().getKeyName();
		ResultInfo resultInfo = new ResultInfo();
		S3Client s3Client = InitiateS3Client.getS3ClientInstace(objectInfo);
	    List<PartETag>  partETags = new ArrayList<PartETag>();
	    try {
		    InitiateMultipartUploadRequest initiateMultipartUploadRequest = 
		    		new InitiateMultipartUploadRequest(bucketName, keyName);
		    initiateMultipartUploadRequest.setObjectMetadata(objectInfo.getObjectMetadata());
	    	InitiateMultipartUploadResult initiateMultipartUploadResult = 
	    			s3Client.getS3().initiateMultipartUpload(initiateMultipartUploadRequest);
	    	objectInfo.setUploadId(initiateMultipartUploadResult.getUploadId());
	    	objectInfo.setPartETags(partETags);
	    	resultInfo.setStatusCode(RESTfulStatus.SUCCESS);
	    	resultInfo.setObjectInfo(objectInfo);
	    	L.info("Initiate multipart write request is success, bucket is {}, object is {}", bucketName, keyName);
		} catch (AmazonServiceException ase) {
			//返回写不成功时错误信息
			resultInfo.setStatusCode(ase.getStatusCode());
			resultInfo.setErrorMessage(ase.getErrorCode());
			resultInfo.setObjectInfo(objectInfo);
			L.error("Initiate multipart write request is faild, status code is {}, error message is {}", ase.getStatusCode(), ase.getErrorCode());
		} catch (Exception e) {
			resultInfo.setStatusCode(RESTfulStatus.OTHER_ERROR);
			resultInfo.setErrorMessage("AmazonClientException or other error : " + e.getMessage());
			resultInfo.setObjectInfo(objectInfo);
			L.error("Initiate multipart write request is faild, status code is {}, error message is {}", RESTfulStatus.OTHER_ERROR, e.getMessage());
		} finally {
			return resultInfo;
		}
	}
	
	/**
	 * <p>
	 * 描述： 分片上传请求
	 * @param objectInfo 写对象信息
	 * @param in 写对象流
	 * @return ResultInfo 结果信息
	 * @throws 无
	 * </p>
	 */
	@SuppressWarnings("finally")
	public ResultInfo writeObjectMultipartOperation(ObjectInfo objectInfo, InputStream in){
		//获取bucket 名
		String bucketName = objectInfo.getBaseInfo().getBucketName();
		//获取文件对象名
		String keyName = objectInfo.getBaseInfo().getKeyName();
		ResultInfo resultInfo = new ResultInfo();
		S3Client s3Client = InitiateS3Client.getS3ClientInstace(objectInfo);
	    //分片号不允许为0
	    if (ConstantConfig.INVALID_PART_NUMBER_ZERO == objectInfo.getPartNumber()){
	    	resultInfo.setStatusCode(RESTfulStatus.OTHER_ERROR);
			resultInfo.setErrorMessage("Multipart write is not allowed part number 0");
			resultInfo.setObjectInfo(objectInfo);
			return resultInfo;
	    }
	    try {
	    	//上传分片请求
	    	UploadPartRequest uploadPartRequest = new UploadPartRequest()
	    			.withBucketName(bucketName)
	    			.withKey(keyName)
	    			.withUploadId(objectInfo.getUploadId())
	    			.withPartNumber(objectInfo.getPartNumber())
	    			.withInputStream(in)
	    			.withPartSize(in.available())
	    			.withMD5Digest(objectInfo.getMd5Digest());
	    	//将分片ETag信息写入List
	    	objectInfo.getPartETags().add(s3Client.getS3().uploadPart(uploadPartRequest).getPartETag());
	    	resultInfo.setStatusCode(RESTfulStatus.SUCCESS);
	    	resultInfo.setObjectInfo(objectInfo);
	    	L.info("Multipart write request is success, bucket is {}, object is {}", bucketName, keyName);
		} catch (AmazonServiceException ase) {
			//返回写不成功时错误信息
			resultInfo.setStatusCode(ase.getStatusCode());
			resultInfo.setErrorMessage(ase.getErrorCode());
			resultInfo.setObjectInfo(objectInfo);
			L.error("Multipart write request is faild, status code is {}, error message is {}", ase.getStatusCode(), ase.getErrorCode());
		} catch (Exception e) {
			resultInfo.setStatusCode(RESTfulStatus.OTHER_ERROR);
			resultInfo.setErrorMessage("AmazonClientException or other error : " + e.getMessage());
			resultInfo.setObjectInfo(objectInfo);
			L.error("Multipart write request is faild, status code is {}, error message is {}", RESTfulStatus.OTHER_ERROR, e.getMessage());
		} finally {
			return resultInfo;
		}
	}

	/**
	 * 
	 * <p>
	 * 描述： 分片上传结束请求
	 * @param objectInfo 写对象信息
	 * @return ResultInfo 结果信息
	 * @throws 无
	 * </p>
	 */
	@SuppressWarnings("finally")
	public ResultInfo completeMultipartWriteOperation(ObjectInfo objectInfo){
		//获取bucket 名
		String bucketName = objectInfo.getBaseInfo().getBucketName();
		//获取对象名
		String keyName = objectInfo.getBaseInfo().getKeyName();
		ResultInfo resultInfo = new ResultInfo();
		//初始化S3客户端实例
		S3Client s3Client = InitiateS3Client.getS3ClientInstace(objectInfo);
	    try {
	    	//初始化分片上传结束请求实例
	    	CompleteMultipartUploadRequest completeMultipartUploadRequest = 
	    			new CompleteMultipartUploadRequest(bucketName, 
	    					keyName, 
	    					objectInfo.getUploadId(), 
	    					objectInfo.getPartETags());
	    	CompleteMultipartUploadResult completeMultipartUploadResult = 
	    			s3Client.getS3().completeMultipartUpload(completeMultipartUploadRequest);
	    	//获取整文件MD5，并返回
	    	objectInfo.setMd5Digest(completeMultipartUploadResult.getETag());
	    	resultInfo.setObjectInfo(objectInfo);
	    	resultInfo.setStatusCode(RESTfulStatus.SUCCESS);
	    	L.info("Complete multipart write request is success, bucket is {}, object is {}", bucketName, keyName);
		} catch (AmazonServiceException ase) {
			//返回写不成功时错误信息
			resultInfo.setStatusCode(ase.getStatusCode());
			resultInfo.setErrorMessage(ase.getErrorCode());
			resultInfo.setObjectInfo(objectInfo);
			s3Client.getS3().abortMultipartUpload(new AbortMultipartUploadRequest(
					bucketName, keyName, objectInfo.getUploadId()));
			L.error("Complete multipart write request is faild, status code is {}, error message is {}", ase.getStatusCode(), ase.getErrorCode());
		} catch (Exception e) {
			resultInfo.setStatusCode(RESTfulStatus.OTHER_ERROR);
			resultInfo.setErrorMessage("AmazonClientException or other error : " + e.getMessage());
			resultInfo.setObjectInfo(objectInfo);
			L.error("Complete multipart write request is faild, status code is {}, error message is {}", RESTfulStatus.OTHER_ERROR, e.getMessage());
		} finally {
			return resultInfo;
		}
	}
}
