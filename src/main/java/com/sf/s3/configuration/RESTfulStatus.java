/* 
 * Copyright (c) 2016, S.F. Express Inc. All rights reserved.
 */
package com.sf.s3.configuration;

/**
 * 描述：RESTful规范下请求返回码
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
public class RESTfulStatus {
	//请求成功
	public static final int SUCCESS = 200;
	//请求失败，非S3服务端异常
	public static final int OTHER_ERROR = 600;
	//bucket存在
	public static final int BUCKET_IS_EXIST = 601;
}
