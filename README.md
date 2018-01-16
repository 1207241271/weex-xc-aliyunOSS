# weex-aliyun-oss

<img width="100" src="https://ss0.bdstatic.com/-0U0bnSm1A5BphGlnYG/tam-ogel/30d56dac6deed198f47e593eb89d8333_121_121.jpg" />


一款阿里云 对象存储OSS，weex插件，当前版本支持上传文件。    
### 快速开始

使用方法
``` bash
//install
npm install weex-aliyun-oss 
weexpack plugin add ./node_modules/weex-aliyun-oss
//uninstall
weexpack plugin weex-aliyun-oss
```


编辑你的weex文件

``` we
<script>
	const aliyunOss = weex.requireModule("aliyunOss");
	module.exports = {
		data:{
   			endPoint:"",
   			accessKeyId:"",
	   		secretKeyId:""
   		},
    
	   created () {
			aliyunOss.initOSSClient(
	   		   	endPoint,
   		  		accessKeyId,
	      		secretKeyId,
   		 	);
	   },
   		methods:{
   			aliyunOss.upLoadFile("XXX","filePath",res=>{
   				console.log(res);
   			},"objectKey");
	   }
	}
</script>

```

### API
#### Amap 模块

#####  初始化OSS Client
+ initOSSClient(endPoint,accessKeyId,secretKeyId)

| 属性       		| 类型	     	| 示例		  | 描述              |
| ------------- 	|:---------:	| -----:	  | ----------:      |
| endPoint		|String		|oss-cn-hangzhou.aliyuncs.com	  | 设置的Endpoint |
| accessKeyId		|String		| 44CF9590006BF252F707  | AccessKeyId       |
| secretKeyId		|String      | OtxrzxIsfpFjA7SwPzILwy8Bw21TLhquhboDYROV | AccessKeySecret     |

#### 单文件上传 
+ upLoadFile(bucketName,filePath,callback,objectKey)

| 属性       		| 类型	     	| 示例		  | 描述              |
| ------------- 	|:---------:	| -----:	  | ----------:      |
| bucketName		|String		| xc-img	  | 存储空间 |
| filePath		|String		| /storage/0/image.png  |文件的绝对路径 |
| callback		|function    | res=>{} | 回调函数     |
| objectKey		|String	   | image-b.png | 存储的文件名    |

#### 多文件上传
+ upLoadFiles(bucketName,filePaths,callback,objectKeys)

| 属性       		| 类型	     	| 示例		  | 描述              |
| ------------- 	|:---------:	| -----:	  | ----------:      |
| bucketName		|String		| xc-img	  | 存储空间 |
| filePaths		|Array		| ["/storage/0/image.png",…]  |文件的绝对路径 |
| callback		|function    | res=>{} | 回调函数     |
| objectKeys		| Array	   | ["image-b.png",…] | 存储的文件名    |