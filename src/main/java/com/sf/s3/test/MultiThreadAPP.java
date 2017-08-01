/* 
 * Copyright (c) 2016, S.F. Express Inc. All rights reserved.
 */
package com.sf.s3.test;

import com.sf.s3.dto.BaseInfo;
import com.sf.s3.dto.ObjectInfo;

/**
 * 描述：
 * 
 * <pre>
 * HISTORY
 * ****************************************************************************
 *  ID   DATE				PERSON				REASON
 *  1    2017年1月7日		01107267			Create
 * ****************************************************************************
 * </pre>
 * 
 * @author 01107267 chenhui
 * @since 1.0
 */
public class MultiThreadAPP {
	public static void main(String[] args){
		BaseInfo baseInfo = new BaseInfo();
    	baseInfo.setBucketName("s3testapibig");
    	baseInfo.setKeyName("112221212");
		ObjectInfo objectInfo1 = new ObjectInfo();
		ObjectInfo objectInfo2 = new ObjectInfo();
		ObjectInfo objectInfo3 = new ObjectInfo();
		objectInfo1.setBaseInfo(baseInfo);
    	objectInfo1.setStart(0L);
    	objectInfo1.setEnd(81993379L);
    	objectInfo2.setBaseInfo(baseInfo);
    	objectInfo2.setStart(81993380L);
    	objectInfo2.setEnd(163986757L);
    	objectInfo3.setBaseInfo(baseInfo);
    	objectInfo3.setStart(163986758L);
    	objectInfo3.setEnd(245980136L);
		MultiThreadRead multiThreadRead1 = new MultiThreadRead(objectInfo1);
		MultiThreadRead multiThreadRead2 = new MultiThreadRead(objectInfo2);
		MultiThreadRead multiThreadRead3 = new MultiThreadRead(objectInfo3);
		Thread thread1 = new Thread(multiThreadRead1);
		Thread thread2 = new Thread(multiThreadRead2);
		Thread thread3 = new Thread(multiThreadRead3);
		thread1.start();
		thread2.start();
		thread3.start();
	}
}
