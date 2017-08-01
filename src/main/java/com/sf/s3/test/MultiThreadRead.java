/* 
 * Copyright (c) 2016, S.F. Express Inc. All rights reserved.
 */
package com.sf.s3.test;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sf.s3.dto.ObjectInfo;
import com.sf.s3.dto.ResultInfo;
import com.sf.s3.operation.ReadRequest;

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
public class MultiThreadRead implements Runnable{
	private static final Logger L = LoggerFactory.getLogger(MultiThreadRead.class);
	private ObjectInfo objectInfo;
	/**
	 * @param objectInfo
	 */
	public MultiThreadRead(ObjectInfo objectInfo) {
		super();
		this.objectInfo = objectInfo;
	}

	public void run() {
		ReadRequest readRequest = new ReadRequest();
    	ResultInfo resultInfo = readRequest.ReadObjectOperation(objectInfo);
    	InputStream inputStream = resultInfo.getInputStream();
    	try {
			@SuppressWarnings("resource")
			RandomAccessFile accessFile = new RandomAccessFile(new File("e:/Shared Folders/aws-java-sdk-1.11.766.zip"), "rw");
			if(0 != objectInfo.getStart()){
				accessFile.seek(objectInfo.getStart() - 1);
			}
			accessFile.seek(objectInfo.getStart());
			int bytesRead = 0;
			BigDecimal total = new BigDecimal(objectInfo.getEnd() - objectInfo.getStart());
			Long curr = 0L;
			while ((bytesRead = inputStream.read()) != -1) {
				curr++;
				if (curr%1048576 == 0){
					BigDecimal v = new BigDecimal(curr);
					Double per = v.divide(total,2,BigDecimal.ROUND_HALF_UP).doubleValue()*100;
					L.info("downloaded {}%",per);
				}
				accessFile.writeByte(bytesRead);
			}
			L.info(Integer.toString(resultInfo.getStatusCode()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
