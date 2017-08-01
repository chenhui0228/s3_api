package com.sf.s3.operation;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.sf.s3.configuration.RESTfulStatus;
import com.sf.s3.dto.ObjectInfo;
import com.sf.s3.dto.ResultInfo;
import com.sf.s3.model.S3Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class Name:DeleteRequest
 * Description:删除对象请求
 * Created by 01107267 on 2017/1/21.
 * Version 1.0
 */
public class DeleteRequest implements OperationsHandler {
    private static final Logger L = LoggerFactory.getLogger(DeleteRequest.class);

    /**
     * 描述：删除对象
     * @param objectInfo
     * @return 请求结果
     */
    public ResultInfo deleteObjectOperation(ObjectInfo objectInfo){
        //获取bucket名
        String bucketName = objectInfo.getBaseInfo().getBucketName();
        //获取对象名
        String keyName = objectInfo.getBaseInfo().getKeyName();
        //请求返回对象
        ResultInfo resultInfo = new ResultInfo();
        //获取客户端实例
        S3Client s3Client = InitiateS3Client.getS3ClientInstace(objectInfo);
        DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucketName, keyName);
        try {
            s3Client.getS3().deleteObject(deleteObjectRequest);
            resultInfo.setObjectInfo(objectInfo);
            resultInfo.setStatusCode(RESTfulStatus.SUCCESS);
            L.info("Delete object request is success, bucket name is {}, object is {}.", bucketName, keyName);
        } catch (AmazonServiceException ase) {
            resultInfo.setStatusCode(ase.getStatusCode());
            resultInfo.setErrorMessage(ase.getErrorCode());
            resultInfo.setObjectInfo(objectInfo);
            L.error("Delete object request is faild, status code is {}, error message is {}", ase.getStatusCode(), ase.getErrorCode());
        } catch (Exception e) {
            resultInfo.setStatusCode(RESTfulStatus.OTHER_ERROR);
            resultInfo.setErrorMessage("AmazonClientException or other error : " + e.getMessage());
            resultInfo.setObjectInfo(objectInfo);
            L.error("Delete object request is faild, status code is {}, error message is {}", RESTfulStatus.OTHER_ERROR, e.getMessage());
        } finally {
            return resultInfo;
        }
    }
}
