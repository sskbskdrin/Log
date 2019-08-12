# Android Log
Android日志库

引用

```
	implementation 'cn.sskbskdrin:log:0.3.0'
```

通过静态方法打印

```
L.v(tag,message,obj...)
L.d(tag,message,obj...)
L.i(tag,message,obj...)
L.w(tag,message,obj...)
L.e(tag,message,obj...)
```

创建对象打印

```
SSKLog log = new SSKLog();
log.v(tag,message,obj...)
log.d(tag,message,obj...)
log.i(tag,message,obj...)
log.w(tag,message,obj...)
log.e(tag,message,obj...)
```
* message内容可通过加入"{}"代表参数，参数值从obj...中按顺序获取
* 当{}数量大于obj时，多出的{}会替换为空串
* 当{}小于obj时，多出的obj会依次追加到后面
* obj 可以是任意类型
* 如果需要打印"{}",则设置为"{\0}"即可
