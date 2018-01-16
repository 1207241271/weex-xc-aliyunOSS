package com.xunce.aliyun_oss;


import android.util.Base64;
import android.util.Log;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCustomSignerCredentialProvider;
import com.alibaba.sdk.android.oss.common.utils.OSSUtils;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class WXAliyunOSSModule extends WXModule{
    private OSS client;
    private static int finished;

    @JSMethod
    public void initOSSClient(String endPoint, final String accessKeyId, final String secretKeyId){
        OSSCustomSignerCredentialProvider credentialProvider = new OSSCustomSignerCredentialProvider() {
            @Override
            public String signContent(String content) {
                return OSSUtils.sign(accessKeyId,secretKeyId,content);
            }
        };
        client = new OSSClient(mWXSDKInstance.getContext(),endPoint,credentialProvider);
    }

    @JSMethod
    public void upLoadFile(String bucketName, String filePath, final JSCallback callback, String objectKey){
        if (bucketName == null || filePath == null){
            HashMap<String,String> map = new HashMap<>();
            map.put("result","fail");
            callback.invoke(map);
        }
        String[] fileName = filePath.split("\\/");
        objectKey = objectKey != null ? objectKey:fileName[fileName.length-1];
        if (filePath.startsWith("nat://static/image")){
            filePath =  filePath.substring("nat://static/image".length());
        }
        PutObjectRequest put = new PutObjectRequest(bucketName,objectKey,filePath);
        OSSAsyncTask task = client.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                HashMap<String,String> map = new HashMap<>();
                map.put("result","success");
                callback.invoke(map);
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientException, ServiceException serviceException) {
                HashMap<String,String> map = new HashMap<>();
                map.put("result","fail");
                callback.invoke(map);
            }
        });
    }

    @JSMethod
    public void upLoadFiles(String bucketName, final List<String> filePaths, final JSCallback callback, List<String> objectKeys){
         finished = 0;
        if (objectKeys == null || objectKeys.size() != filePaths.size()){
            objectKeys = new ArrayList<>();
            for (int index = 0;index<filePaths.size();index++){
                String filePath = filePaths.get(index);
                String[] fileName = filePath.split("\\/");
                objectKeys.add(fileName[fileName.length-1]);
            }
        }
        for (int i = 0; i < filePaths.size(); i++) {
            String filePath = filePaths.get(i);
            if (filePath.startsWith("nat://static/image")){
                filePath =  filePath.substring("nat://static/image".length());
            }
            PutObjectRequest put = new PutObjectRequest(bucketName,objectKeys.get(i),filePath);
            OSSAsyncTask task = client.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
                @Override
                public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                    finished ++;
                    if (finished == filePaths.size()){
                        HashMap<String,String> map = new HashMap<>();
                        map.put("result","success");
                        callback.invoke(map);
                    }
                }

                @Override
                public void onFailure(PutObjectRequest request, ClientException clientException, ServiceException serviceException) {
                    finished ++;
                    if (finished == filePaths.size()){
                        HashMap<String,String> map = new HashMap<>();
                        map.put("result","success");
                        callback.invoke(map);
                    }
                }
            });
        }

    }
}