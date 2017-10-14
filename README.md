# 简介
[![Platform](https://img.shields.io/badge/platform-android-green.svg)](http://developer.android.com/index.html)
[![License](https://img.shields.io/badge/license-MIT-green.svg?style=flat)](https://opensource.org/licenses/MIT)
[![API](https://img.shields.io/badge/API-19%2B-yellow.svg?style=flat)](https://android-arsenal.com/api?level=19)

Luban是一个移动端监视APP运行的工具应用，提供了侵入式的监视方案。整个项目结构是对《[由客户端内部通讯引发的插件化开发的随想和实践](http://www.cnblogs.com/youngytj/p/7455829.html)》的一个实践。

# 实现
整个项目利用Dagger2实现了插件式的开发，能够轻易的扩展出自定义的功能。整个工程包括了一个主程序和SDK包，SDK包可以集成到需要监视的APP内部，主程序会自动进行呈现和统计。

# 快速开始

## 引入SDK
将工程`luban.sdk`和`luban.aidl`导入到需要集成的APP内，添加如下引用：
```Java
compile project(':luban.sdk')
```

## 功能演示(以出参功能为例)
在需要的地方注册出参（推荐在Application的onCreate方法中）
```Java
LB.connect(getApplicationContext(), new AbsLBParaLoader() {
    @Override
    public void loadInParas(InParaManager im) {
    }

    @Override
    public void loadOutParas(OutParaManager om) {
        om.register("Test");
    }
});
```

当注册完成之后，可以开始传递出参的值：
```Java
LB.setOutPara("Test", String.valueOf(System.currentTimeMillis()));
```

此时在主程序中就能看到这个值：

<img src="http://7xqjah.com1.z0.glb.clouddn.com/2017-08-22-Android-Component-02.png" width="240">

# 其他功能
* 入参设置
* 全局异常捕获（Runtime和NDK）
* 日志记录
* TODO

