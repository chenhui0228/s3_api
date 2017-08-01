/* 
 * Copyright (c) 2016, S.F. Express Inc. All rights reserved.
 */
package com.sf.s3.dto;

import java.util.List;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PartETag;

/**
 * 描述：对象请求传输信息类
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
public class ObjectInfo {
	/**
	 * S3 client configure
	 */
	private S3ClientConfigure s3ClientConfigure;
	/**
	 * Bucket和object名称信息
	 */
	private BaseInfo baseInfo = null;
	
	/**
	 * 分片上传Id，区分一个分片上传请求
	 */
	private String uploadId = null;
	
	/**
	 * 分片号,多个分片时，区分为一个上传文件的某个分片
	 */
	private int partNumber;
	
	/**
	 * 读文件起始位置，文件下载时一次读取文件的起始位置
	 */
	private Long start = null;
	
	/**
	 * 读文件结束位置，文件下载时一次读取文件的结束位置
	 */
	private Long end = null;
	
	/**
	 * MD5校验值
	 */
	private String md5Digest = null;
	
	/**
	 * 分片上传请求相应集合
	 */
	List<PartETag> partETags = null;
	
	/**
	 * 元数据
	 */
	private ObjectMetadata objectMetadata;

	public S3ClientConfigure getS3ClientConfigure() {
		return s3ClientConfigure;
	}

	public void setS3ClientConfigure(S3ClientConfigure s3ClientConfigure) {
		this.s3ClientConfigure = s3ClientConfigure;
	}

	public BaseInfo getBaseInfo() {
		return baseInfo;
	}

	public void setBaseInfo(BaseInfo baseInfo) {
		this.baseInfo = baseInfo;
	}
	
	public String getUploadId() {
		return uploadId;
	}

	public void setUploadId(String uploadId) {
		this.uploadId = uploadId;
	}

	public int getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(int partNumber) {
		this.partNumber = partNumber;
	}

	public Long getStart() {
		return start;
	}

	public void setStart(Long start) {
		this.start = start;
	}

	public Long getEnd() {
		return end;
	}

	public void setEnd(Long end) {
		this.end = end;
	}

	public String getMd5Digest() {
		return md5Digest;
	}

	public void setMd5Digest(String md5Digest) {
		this.md5Digest = md5Digest;
	}

	public List<PartETag> getPartETags() {
		return partETags;
	}

	public void setPartETags(List<PartETag> partETags) {
		this.partETags = partETags;
	}

	public ObjectMetadata getObjectMetadata() {
		return objectMetadata;
	}

	public void setObjectMetadata(ObjectMetadata objectMetadata) {
		this.objectMetadata = objectMetadata;
	}
}
