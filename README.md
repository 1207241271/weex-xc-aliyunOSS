# weex-amap

<img width="320" src="https://img.alicdn.com/tps/TB19sYlPFXXXXaRaXXXXXXXXXXX-600-450.png" />


一款高德地图weex插件，当前版本支持定位，缩放等地图常用操作。    
请配合[高德地图开发文档](http://lbs.amap.com/api/android-sdk/summary/)使用该插件    

### 快速开始

使用方法
``` bash
//install
npm install weex-xc-amap
weexpack plugin add ./node_modules/weex-xc-amap
//uninstall
weexpack plugin remove weex-amap
```


编辑你的weex文件

``` we
<template>
  <div class="container">
      <weex-amap class="map" id="map2017" scale="true" geolocation="true" center="{{pos}}" >
        <weex-amap-marker position="{{point.position}}" title="{{point.title}}"></weex-amap-marker>
      </weex-amap>
  </div>
</template>

<style>
  .container{
    position: relative;
    height: 100%;
    
  }
  .map{
    width:100%;
    height: 600;
    background-color: #000;
  }
</style>

<script>

  module.exports = {
    data: {
      pos:[116.487, 40.00003],
      point: {
        position: [112,36],
        title: 'this is a marker'
      }
    },
    
    created () {

    },
    
  }
</script>

```

### API

#### weex-amap 属性

| 属性        | 类型         | Demo  | 描述  |
| ------------- |:-------------:| -----:|----------:|
| sdkKey   | object | {ios:'xxx',android: 'xxx',h5: 'xxx'} | 指定开发者的 SDK 密匙 
| scale   | boolean | true | 设置比例尺功能是否可用 
| center     | array | [116.487, 40.00003] | 传入地理位置坐标[x,y] 默认为当前定位位置 |
| zoom      | number    |  合法值范围 [3,19] | 缩放级别 |
| compass      | boolean    |  true | 是否允许显示指南针 |
| zoomEnable | boolean  | true | 是否允许缩放
| marker |  array | [`{position:[116,12]}]` |  点标记物的属性
| geolocation  | boolean | true | 是否显示当前位置
| zoomPosition  | String | center|bottom | 设置缩放按钮的位置
| gestures  | String | ["zoom","rotate","tilt","scroll"] | 设置允许对地图做哪些手势操作
| myLocationEnabled  | boolean | true | 定位按钮是否显示
| showMyLocation  | boolean | true | 是否显示当前位置
| customEnabled  | boolean | true | 是否开启自定义地图样式
| setMapCustomEnable  | String | {"android":"/data/custom_map"} |设置自定义地图资源文件的路径
| indoorswitch  | boolean | false |设置室内地图楼层切换控件是否可见
| mapStylePath  | String | style.data | 设置地图样式 |


**建议你前往[高德开发者社区](http://lbs.amap.com/)申明你对应产品的Key，保证地图正常工作**

#### weex-amap 事件    
**zoomchange**
用户缩放地图时触发该事件    
事件格式    
```json
{
  "targetCoordinate" : "缩放后的位置/[lng,lat]",
  "zoom" :  "目标可视区域的缩放级别。/float",
  "tilt" : "目标可视区域的倾斜度，以角度为单位。",
  "bearing" : "可视区域指向的方向，以角度为单位，从正北向逆时针方向计算，从0 度到360 度。",
  "isAbroad" : "该位置是否在国内（此属性不是精确计算，不能用于边界区域）",
  "scalePerPixel" : "Weex中一像素对应实际距离的长度（单位米）",
  "visibleRegion" : "可视区域的范围"
}
```  
**dragend**    
用户拖动地图时触发该事件



#### weex-amap-marker 属性    

| 属性        | 类型         | Demo  | 描述  |
| ------------- |:-------------:| -----:|----------:|
| position     | array | [116.487, 40.00003] | 传入地理位置坐标[x,y] 默认为当前定位位置 |
| icon | string     |    some_icon_url | 图标的url地址 |
| title | string   |   'this is a marker' | 坐标点的名称 |
| hideCallout | boolean   |   true | 设置marker是否可点击 |
| open | boolean   |   true | 是否显示InfoWindow |

#### weex-amap-marker 事件
**click**    
点击marker时触发

#### weex-amap-info-window 属性    

| 属性        | 类型         | Demo| 描述  |
| ------------- |:-------------:| ------:|-----:|
| open     | boolean | true|是否显示InfoWindow |
| position     | String | [116.487, 40.00003]|InfoWindow位置 |
| offset     | String |[0,-20]| InfoWindow偏移量[x,y] |

``` we
<template>
    <div>
     <template v-for='item in mapCfg.markers'>
        <weex-amap-marker :carId="item.carId" :id="item.id" :position="item.position" icon="http://118.89.104.130/adminTest/static/img/avatars/profile.jpg" @click="markerClicker" hide-callout="true">
        </weex-amap-marker>
        <weex-amap-info-window :open="true" :position="item.position">
            <div>
                <text class="info-window-text">测试</text>
            </div>
        </weex-amap-info-window>
     </template>
    </div>
</template>

<style>
.info-window-text{
    text-align: center;
    color: red;
    background-color: white;
    width:350px;
}
</style>

<script>
export default {

    methods:{
        markerClicker(e){
            console.log(e);
        }
    }
    
}
</script>

```

#### weex-amap-circle 属性
| 属性        | 类型         | 描述  |
| ------------- |:-------------:| -----:|
| center     | String | 中心点 |
| strokeColor     | String | 描边颜色 |
| fillColor     | String | 填充颜色 |
| fillOpacity | float | 填充颜色透明度 |
| strokeWidth     | float | 描边宽度 |
| radius     | float | 半径 |
| strokeStyle     | String | 描边样式，可为dashed或空 |


#### weex-amap-polygon 属性    

| 属性        | 类型         | 描述  |
| ------------- |:-------------:| -----:|
| path     | String | 路径 |
| strokeColor     | String | 描边颜色 |
| fillColor     | String | 填充颜色 |
| fillOpacity | float | 填充颜色透明度 |
| strokeWidth     | float | 描边宽度 |
| strokeStyle     | String | 描边样式，可为dashed或空 |


#### weex-amap-polyline 属性    

| 属性        | 类型         | 描述  |
| ------------- |:-------------:| -----:|
| path     | String | 路径 |
| strokeColor     | String | 描边颜色 |
| strokeStyle     | String | 描边样式，可为dashed或空 |
| strokeWidth     | float | 描边宽度 |

#### Amap 模块

#####  getUserLocation(completeFunc,errorFunc)

+ completeFunc 定位成功后的回调函数，返回的数据:
```
{ 
  data:{
    position: []
  },
  result: 'success' 
}
```
##### getCenterLocation(elemRef,completeFunc)

+ completeFunc 获取到中心点的回调函数，返回的数据：
```
{
    data:[lng,lat],
    result:'success'
}
```

##### choosePosition(completeFunc)

+ completeFunc 选取完点的回调函数，返回的数据：
```
{
    data:[lng,lat],
    result:'success'
}
```

##### reGeoPositions(postions,completeFunc)

+ completeFunc 批量转换完之后的数据
```
{
    result:'success',
    data:[{
        formattedAddress:'湖北省武汉市洪山区关山街道珞瑜东路',
        addressComponent:{
            city:'武汉市',
            country:'中国',
            township:'关山街道',
            streetNumber:{
                street:'珞瑜东路'
            }
        }
    }，···]
}
```

#####  getLineDistance(posA, posB, callback)

+ 获取两点间的直线距离

#####  polygonContainsMarker(position, id, callback)

+ 判断点是否在合围范围内

##### 使用Amap模块

``` html 
<template>
  <weex-amap class="map" id="map2017" center="{{pos}}" ></weex-amap>
  <div class="btn-wrap">
    <div onclick="setUserLocation" class="btnbox"><text class="btn" >set location </text></div>
    <text class="tips">进行当前定位</text>
  </div>
</template>

<script>
  const Amap = require('@weex-module/amap');
  module.exports = {
    data: {
      pos:[116.487, 40.00003]
    },
    
    methods: {
      setUserLocation() {
        const self = this;
        Amap.getUserLocation(this.$el('map2017').ref, function (data) {
          if(data.result == 'success') {
            self.pos = data.data.position;
          }
        });  
    }
  };
  
</script>
```
