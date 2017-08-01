/* 
 * Copyright (c) 2016, S.F. Express Inc. All rights reserved.
 */
package com.sf.s3.util;

import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sf.s3.dto.ObjectInfo;

/**
 * 描述：
 * 
 * <pre>
 * HISTORY
 * ****************************************************************************
 *  ID   DATE				PERSON				REASON
 *  1    2016年12月30日		01107267			Create
 * ****************************************************************************
 * </pre>
 * 
 * @author 01107267 chenhui
 * @since 1.0
 */
public class ObjectUtil {
	private static final Logger L = LoggerFactory.getLogger(ObjectUtil.class);
	public static String getObjectName(ObjectInfo objectInfo){
		StringBuffer buffer = new StringBuffer();
//		buffer.append(objectInfo.getName());
//		buffer.append(Long.toString(objectInfo.getCreateTime().getTime()));
		String name = buffer.toString();
		try {
			name = HashUtil.md5(name);
		} catch (NoSuchAlgorithmException e) {
			L.error("Can not generate object name, {}",e.getMessage());
			name = null;
//			if (L.isDebugEnabled()) {
//			}
		}
		return name;
	}
}
