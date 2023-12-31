## Vue 概述



### 一：Web应用程序的开发模型



#### 1.1 B-S模型



​		B-S模型（Browser-Server），浏览器与服务器进行交互的网络应用程序。

​		应用程序的客户端，就是浏览器。

​		客户端开发技术：HTML5+CSS3+Javascript。



#### 1.2 C-S模型



​		C-S模型（Client-Server），客户端程序与服务器进行交互的网络应用程序。

​		应用程序的客户端，是基于操作系统自行开发的应用程序。

​		客户端开发技术：

​				windows：C#（.net framework）

​				android：Java + Kotlin

​				iOS：Objective-C、Swift



### 二：Web应用程序的开发模式



#### 2.1 DHTML开发模型



​		DHTML（ Dynamic HTML ）在服务器端动态生成HTML，开发模式。

​		是一种传统的开发模式。

​		优点：对于前端程序员来说，开发比较简单，只需要实现界面布局样式即可。

​		缺点：只能实现B-S模型的Web应用程序。



#### 2.2 前后端分离开发模式



​		前后端分离开发模式，是在服务器端只提供数据接口（WebApi）。

​		是一种适用于客户端多平台需求的主流开发模式。

​		优点：服务器端可以兼容B-S模型和C-S模型，实现客户端多平台。

​		缺点：对前端开发人员的技术要求提高。不光需要实现界面布局，还要实现业务流程。



​		目前主流的 前后端分离开发模式 的 技术生态圈：

​		1：Vue 生态圈（国内）

​		2：React 生态圈（Facebook 国外）



### 三：Vue 生态圈



#### 3.1 VueFramework（Vue框架）



​		Vue借鉴的MVVM开发模式

​		基于Component组件实现的《渐进式》开发思想



#### 3.2 VueRouter（Vue路由）

​		

​		基于Router路由系统实现的《单页面》开发思想

​		路由守卫



#### 3.3 VueCli（Vue脚手架）



​		标准化的初始化项目工程

​		应用程序打包和压缩 



#### 3.4 Vuex（Vue状态管理）



​		模块化分离、同步和异步分离、数据和状态分离



#### 3.5 Axios（网络通讯组件）



​		高度封装 Ajax 组件



#### 3.6 可以选择一个UI型组件



​		Element-UI

​		Vurify

​		Bootstrap-for-Vue

​		......



### 四：官网和版本信息



​	 	Vue 官网：https://cn.vuejs.org/



​		主要版本信息：

​				2.x  

​				3.x（2020年9月 发布）（本教程使用 3.x版本）

