//
//  WXAliyunOssModule.m
//  WeexDemo
//
//  Created by yangxu on 2018/1/15.
//  Copyright © 2018年 taobao. All rights reserved.
//

#import "WXAliyunOssModule.h"
#import <AliyunOSSiOS/OSSService.h>
#import <WeexSDK/WXModuleProtocol.h>
@interface WXAliyunOssModule()<WXModuleProtocol>
@property (nonatomic, strong) OSSClient *client;
@end
@implementation WXAliyunOssModule
WX_EXPORT_METHOD(@selector(initOSSClient:accessKeyId:secretKeyId:))
WX_EXPORT_METHOD(@selector(upLoadFile:filePath:callback:objectKey:))
WX_EXPORT_METHOD(@selector(upLoadFiles:filePathList:callback:objectKeyList:))
-(void)initOSSClient:(NSString *)endPoint accessKeyId:(NSString *)accessKeyId secretKeyId:(NSString *)secretKeyId{
    id<OSSCredentialProvider> credential = [[OSSCustomSignerCredentialProvider alloc] initWithImplementedSigner:^NSString *(NSString *contentToSign, NSError *__autoreleasing *error) {
        NSString *signature = [OSSUtil calBase64Sha1WithData:contentToSign withSecret:secretKeyId];
        if (signature == nil) {
            return nil;
        }
        return [NSString stringWithFormat:@"OSS %@:%@",accessKeyId,signature];
    }];

    _client = [[OSSClient alloc] initWithEndpoint:endPoint credentialProvider:credential];
}

-(void)upLoadFile:(NSString *)bucketName filePath:(NSString *)filePath callback:(WXCallback)callback objectKey:(NSString *)objectKey {
    if (bucketName == nil || filePath == nil) {
        callback(@{@"result":@"fail",@"error":@"no input"});
        return;
    }
    OSSPutObjectRequest *put = [OSSPutObjectRequest new];
    put.bucketName = bucketName;
    put.objectKey = objectKey != nil?objectKey:[filePath lastPathComponent];
    if ([filePath hasPrefix:@"file://"]) {
        filePath = [filePath substringFromIndex:[@"file://" length]];
    }
    put.uploadingData = [NSData dataWithContentsOfFile:filePath];
    put.uploadProgress = ^(int64_t bytesSent, int64_t totalBytesSent, int64_t totalBytesExpectedToSend) {
        NSLog(@"%lld, %lld, %lld", bytesSent, totalBytesSent, totalBytesExpectedToSend);
    };
    
    OSSTask * putTask = [_client putObject:put];
    [putTask continueWithBlock:^id _Nullable(OSSTask * _Nonnull task) {
        if (!task.error) {
            NSLog(@"upload object success");
        }else{
            NSLog(@"upload object failed");
        }
        return nil;
    }];
}

-(void)upLoadFiles:(NSString *)bucketName filePathList:(NSArray *)filePaths callback:(WXModuleCallback)callback objectKeyList:(NSMutableArray *)objectKeys  {
    __block int finishedTask = 0;
    if (objectKeys.count != filePaths.count) {
        objectKeys = [NSMutableArray arrayWithCapacity:filePaths.count];
        for (NSString *string in filePaths) {
            [objectKeys addObject:[string lastPathComponent]];
        }
    }
    for(int index = 0;index < filePaths.count;index++){
        OSSPutObjectRequest *put = [OSSPutObjectRequest new];
        put.bucketName = bucketName;
        put.objectKey = [objectKeys objectAtIndex:index];
        NSString *filePath = [filePaths objectAtIndex:index];
        if ([filePath hasPrefix:@"file://"]) {
            filePath = [filePath substringFromIndex:[@"file://" length]];
        }
        put.uploadingData = [NSData dataWithContentsOfFile:filePath];        OSSTask *putTask = [_client putObject:put];
        [putTask continueWithBlock:^id _Nullable(OSSTask * _Nonnull task) {
            if (!task.error) {
                finishedTask ++;
                if (finishedTask == filePaths.count) {
                    callback(@{@"result":@"success"});
                }
            }else{
                finishedTask ++;
                if (finishedTask == filePaths.count) {
                    callback(@{@"result":@"success"});
                }
                NSLog(@"upload object failed");
            }
            return nil;
        }];
    }
}
@end
