/* 
 * Copyright (c) 2016, S.F. Express Inc. All rights reserved.
 */
package com.sf.s3.operation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.Quota;
import com.amazonaws.services.s3.model.SetQuotaRequest;
import com.sf.s3.configuration.ConstantConfig;
import com.sf.s3.configuration.RESTfulStatus;
import com.sf.s3.dto.ObjectInfo;
import com.sf.s3.dto.ResultInfo;
import com.sf.s3.model.S3Client;

/**
 * 描述：创建Bucket请求
 * 
 * <pre>
 * HISTORY
 * ****************************************************************************
 *  ID   DATE				PERSON				REASON
 *  1    2017年1月10日		01107267			Create
 * ****************************************************************************
 * </pre>
 * 
 * @author 01107267 chenhui
 * @since 1.0
 */
public class CreateBucketRequest implements OperationsHandler {
	private static final Logger L = LoggerFactory.getLogger(CreateBucketRequest.class);
	/**
	 * <p>
	 * 描述： 创建bucket请求
	 * @param objectInfo 请求对象信息，bucketName为必须设置项
	 * @return ResultInfo 请求结果信息
	 * @throws 无
	 * </p>
	 */
	@SuppressWarnings("finally")
	public ResultInfo CreateBucketOperation(ObjectInfo objectInfo){
		//获取Bucket名
		String bucketName = objectInfo.getBaseInfo().getBucketName();
		ResultInfo resultInfo = new ResultInfo();
		S3Client s3Client = InitiateS3Client.getS3ClientInstace(objectInfo);
	    try {
			//Bucket 已经存在则返回相应错误码，否则创建bucket
	    	if(!(s3Client.getS3().doesBucketExist(bucketName)))
            {
            	s3Client.getS3().createBucket(bucketName);
            	resultInfo.setStatusCode(RESTfulStatus.SUCCESS);
            	L.info("Create bucket request is success, bucket name is {}.", bucketName);
				//非System用户时设置用户配额
//            	if (objectInfo.getS3ClientConfigure() != null) {
//            		setQuota(resultInfo, s3Client , objectInfo.getS3ClientConfigure().getOwner());
//            	}
            }else {
            	resultInfo.setStatusCode(RESTfulStatus.BUCKET_IS_EXIST);
            	resultInfo.setErrorMessage("Bucket " + bucketName + " is already existed.");
            	L.error("Bucket {} is already existed.", bucketName);
            } 
		} catch (AmazonServiceException ase) {
			//返回写不成功时错误信息
			resultInfo.setStatusCode(ase.getStatusCode());
			resultInfo.setErrorMessage(ase.getErrorCode());
			resultInfo.setObjectInfo(objectInfo);
			L.error("Create bucket request is faild, status code is {}, error message is {}", ase.getStatusCode(), ase.getErrorCode());
		} catch (Exception e) {
			resultInfo.setStatusCode(RESTfulStatus.OTHER_ERROR);
			resultInfo.setErrorMessage("AmazonClientException or other error : " + e.getMessage());
			resultInfo.setObjectInfo(objectInfo);
			L.error("Create bucket request is faild, status code is {}, error message is {}", RESTfulStatus.OTHER_ERROR, e.getMessage());
		} finally {
			return resultInfo;
		}
	}

	/**
	 * <p>
	 * 描述： 为非系统用户的bucket设置quota，不支持系统用户设置quota
	 * @param resultInfo 请求返回结果
	 * @param s3Client S3客户端
	 * @param owner 租户
	 * @return null
	 * @throws 无
	 * </p>
	 */
	private void setQuota(ResultInfo resultInfo, S3Client s3Client, String owner) {
		Quota quota = new Quota();
		quota.setMaxObjects(ConstantConfig.BUCKET_MAX_OBJECTS);
		quota.setEnabled(true);
		quota.setMaxSizeKb(ConstantConfig.DEF_MAX_SIZE_KB);
		quota.setMaxObjsSoftThreshold(ConstantConfig.DEF_MAX_OBJS_SOFT_THRESHOLD);
		quota.setMaxSizeSoftThreshold(ConstantConfig.DEF_MAX_SIZE_SOFT_THRESHOLD);
		SetQuotaRequest setQuotaRequest = new SetQuotaRequest(owner, ConstantConfig.QUOTA_TYPE_BUCKET);
		setQuotaRequest.setQuota(quota);
		L.info("Set quota for bucket, owner is {}",owner);
		try {
			s3Client.getS3().setQuota(setQuotaRequest);
			L.info("Set quota for bucket success.");
		} catch (AmazonServiceException ase){
			//返回写不成功时错误信息
			resultInfo.setStatusCode(ase.getStatusCode());
			resultInfo.setErrorMessage(ase.getErrorCode());
			L.error("Set quota for bucket faild, status code is {}, error message is {}", ase.getStatusCode(), ase.getErrorCode());
		} catch (Exception e) {
			resultInfo.setStatusCode(RESTfulStatus.OTHER_ERROR);
			resultInfo.setErrorMessage("AmazonClientException or other error : " + e.getMessage());
			L.error("Set quota for bucket faild, status code is {}, error message is {}", RESTfulStatus.OTHER_ERROR, e.getMessage());
		}
	}
}
