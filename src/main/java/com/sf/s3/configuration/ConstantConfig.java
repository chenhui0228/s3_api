/* 
 * Copyright (c) 2016, S.F. Express Inc. All rights reserved.
 */
package com.sf.s3.configuration;

/**
 * 描述：常量配置类
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
public class ConstantConfig {
	//非法的分片号0
	public static final int INVALID_PART_NUMBER_ZERO = 0;
	//Bucket允许的最大Object数
	public static final long BUCKET_MAX_OBJECTS = 1000000L;
	//Bucket允许存入对象大小,以KB为单位,-1表示不设置
	public static final long DEF_MAX_SIZE_KB = -1L;
	//
	public static final long DEF_MAX_OBJS_SOFT_THRESHOLD = -1L;
	//
	public static final long DEF_MAX_SIZE_SOFT_THRESHOLD = -1L;
	//
	public static final String QUOTA_TYPE_BUCKET = "bucket";
	//
	public static final String QUOTA_TYPE_USER = "user";
}
