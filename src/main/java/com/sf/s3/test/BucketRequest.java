/* 
 * Copyright (c) 2016, S.F. Express Inc. All rights reserved.
 */
package com.sf.s3.test;

import com.sf.s3.dto.BaseInfo;
import com.sf.s3.dto.ObjectInfo;
import com.sf.s3.dto.ResultInfo;
import com.sf.s3.operation.CreateBucketRequest;
import com.sf.s3.operation.OperationsHandler;
import com.sf.s3.util.HashUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;

/**
 * 描述：封装Bucket操作类
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
public class BucketRequest implements OperationsHandler {
	private static final Logger L = LoggerFactory.getLogger(BucketRequest.class);
	/**
	 * 
	 * <p>
	 * 描述： 根据写入对象创建时间生成对象存储的Bucket名
	 * @param objectInfo
	 * @return name 
	 * @throws 无
	 * </p>
	 */
	public String getBucketName(Date d){
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.SUNDAY);
		calendar.setTime(d);
		StringBuffer buffer = new StringBuffer();
		buffer.append(Integer.toString(calendar.get(Calendar.YEAR)));
		buffer.append(Integer.toString(calendar.get(Calendar.WEEK_OF_YEAR)));
		String name = buffer.toString();
		try {
			name = HashUtil.md5(name);
		} catch (NoSuchAlgorithmException e) {
			if (L.isDebugEnabled()) {
				L.error("Can not generate bucket name, {}", e.getMessage());
			}
			name = null;
		}
		return name;
	}
	public static void main (String[] args){
		ObjectInfo objectInfo = new ObjectInfo();
		BaseInfo baseInfo = new BaseInfo();
		baseInfo.setBucketName("bucket_000111");
		objectInfo.setBaseInfo(baseInfo);
		CreateBucketRequest bucketRequest = new CreateBucketRequest();
		ResultInfo resultInfo =  bucketRequest.CreateBucketOperation(objectInfo);
		L.info(resultInfo.getStatusCode() + "   "+ resultInfo.getErrorMessage());
	}
}
