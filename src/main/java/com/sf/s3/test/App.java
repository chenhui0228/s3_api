package com.sf.s3.test;

import com.sf.s3.dto.BaseInfo;
import com.sf.s3.dto.ObjectInfo;
import com.sf.s3.dto.ResultInfo;
import com.sf.s3.operation.ReadRequest;
import com.sf.s3.operation.WriteRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.math.BigDecimal;

/**
 * Hello world!
 *
 */
public class App 
{
	private static final Logger L = LoggerFactory.getLogger(App.class);
	public static Long size;
    public static void main( String[] args )
    {
//    	writeTest();
    	readTest2();
    }

	public static void readTest2(){
		ObjectInfo objectInfo = new ObjectInfo();
		BaseInfo baseInfo = new BaseInfo();
		baseInfo.setBucketName("bucket-44d6e133-9a6f-481c-8cdd-00bc647afe19-2017-01-12-2");
		baseInfo.setKeyName("Si3583Setup4sd465444.exe");
		objectInfo.setBaseInfo(baseInfo);
		ReadRequest readRequest = new ReadRequest();
		ResultInfo resultInfo = readRequest.ReadObjectOperation(objectInfo);
		InputStream inputStream = resultInfo.getInputStream();
		try {
			RandomAccessFile accessFile = new RandomAccessFile(new File("e:/Si358asdSetup11.exe"), "rw");
			Long fileLength = accessFile.length();
			accessFile.seek(fileLength);
			int bytesRead = 0;
//			BigDecimal total = new BigDecimal(end - start);
			Long curr = 0L;
			while ((bytesRead = inputStream.read()) != -1) {
				curr++;
//				if (curr % 1048576 == 0) {
					BigDecimal v = new BigDecimal(curr);
//					Double per = v.divide(total, 2, BigDecimal.ROUND_HALF_UP).doubleValue() * 100;
//					L.info("downloaded {}%", per);
//				}
				accessFile.writeByte(bytesRead);
			}
		}catch (Exception e){
			L.info(Integer.toString(resultInfo.getStatusCode()));
		}
	}
    public static void readTest(){
    	ObjectInfo objectInfo = new ObjectInfo();
    	BaseInfo baseInfo = new BaseInfo();
    	baseInfo.setBucketName("bucket_44d6e133_9a6f_481c_8cdd_00bc647afe19_2017_01_12_1");
    	baseInfo.setKeyName("7feda097898e44d8ac4fcbe913182586");
    	objectInfo.setBaseInfo(baseInfo);

//    	Long start = 0L;
//    	Long end = 125980136L;
//    	Long start = 125980137L;
//    	Long end = 245980136L;
    	Long start = 0L;
    	Long end = 6228048L;
    	objectInfo.setStart(start);
    	objectInfo.setEnd(end);
//    	objectInfo.setStart(new Long(0));
//    	objectInfo.setEnd(new Long(245980136));
    	ReadRequest readRequest = new ReadRequest();
    	ResultInfo resultInfo = readRequest.ReadObjectOperation(objectInfo);
    	InputStream inputStream = resultInfo.getInputStream();
//    	BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
//    	StringBuffer buffer = new StringBuffer();
//    	String line = "";
//    	try {
//    		while ((line = in.readLine()) != null){
//    		buffer.append(line);
//    	}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//    	System.out.println( buffer.toString());
    	
//    	OutputStream os;
    	try {
			@SuppressWarnings("resource")
			RandomAccessFile accessFile = new RandomAccessFile(new File("e:/Si3583Setup11.exe"), "rw");
			Long fileLength = accessFile.length();
			accessFile.seek(fileLength);
//			os = new FileOutputStream("e:/Shared Folders/aws-java-sdk-1.11.744.zip");
			int bytesRead = 0;
			BigDecimal total = new BigDecimal(end - start);
			Long curr = 0L;
			while ((bytesRead = inputStream.read()) != -1) {
				curr++;
				if (curr%1048576 == 0){
					BigDecimal v = new BigDecimal(curr);
					Double per = v.divide(total,2,BigDecimal.ROUND_HALF_UP).doubleValue()*100;
					L.info("downloaded {}%",per);
				}
				accessFile.writeByte(bytesRead);
//				os.write(bytesRead);
			}
			L.info(Integer.toString(resultInfo.getStatusCode()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
    public static void writeTest(){
    	ObjectInfo objectInfo = new ObjectInfo();
    	BaseInfo baseInfo = new BaseInfo();
    	baseInfo.setBucketName("bucket-44d6e133-9a6f-481c-8cdd-00bc647afe19-2017-01-12-2");
    	baseInfo.setKeyName("Si3583Setup4sd465444.exe");
    	objectInfo.setBaseInfo(baseInfo);
    	WriteRequest witeRequest = new WriteRequest();
    	File file = new File("e:/Shared Folders/Si3583Setup.exe");
    	size = file.length();
    	ResultInfo resultInfo = new ResultInfo();
    	try {
    		FileInputStream fileInputStream = new FileInputStream(file);
    		resultInfo = witeRequest.writeObjectSingleOperation(objectInfo, fileInputStream);
    		L.info(Integer.toString(resultInfo.getStatusCode()));
//    		resultInfo = witeRequest.initiateMultipartWriteOperation(objectInfo);
//    		L.info(Integer.toString(resultInfo.getStatusCode()));
//    		objectInfo = resultInfo.getObjectInfo();
//    		objectInfo.setPartNumber(1);
//    		resultInfo = witeRequest.writeObjectMultipartOperation(objectInfo, fileInputStream);
//    		L.info(Integer.toString(resultInfo.getStatusCode()));
//    		objectInfo = resultInfo.getObjectInfo();
//    		resultInfo = witeRequest.completeMultipartWriteOperation(objectInfo);
//    		L.info(Integer.toString(resultInfo.getStatusCode()));
    	} catch (FileNotFoundException e) {
    		L.info(e.getMessage());
    	}
    }
}
