## Axios

​        Axios 是一个基于Ajax高度封装的网络通讯组件。

### 一：在项目工程中安装Axios组件

#### 1.1 安装Axios组件

​        在【命令提示符】，进入项目工程路径

​        执行命令：cnpm install axios --save

#### 1.2 安装qs组件

​        在【命令提示符】，进入项目工程路径

​        执行命令：cnpm install qs --save

### 二：配置反向代理

#### 2.1 CORS 跨域问题

​        origin 网络域：ip地址:端口号

​        前端应用程序所在的域：127.0.0.1:8080

​        后端应用程序所在的域：127.0.0.1:8001

​        前端应用程序，访问后端应用程序，出现跨域问题（CORS）

#### 2.2 跨域问题的解决方案

​        1：在后端应用程序，配置：允许跨域请求（不推荐，会产生XSS跨域攻击  ）

​        2：配置反向代理

​        反向代理的解决思路：

​        前端应用程序所在的域：127.0.0.1:8080

​        后端应用程序所在的域：127.0.0.1:8001

​        后端应用程序反向代理：127.0.0.1:8080/api

#### 2.3 开发环境中的反向代理配置方法

​        在VueCli项目工程路径下，增加配置文件：vue.config.js

```javascript
module.exports = {
    // 开发环境 服务器端配置
    devServer: {
        // 反向代理
        proxy: {
            // 代理路径  http://127.0.0.1:8080/api
            '/api': {
                // 代理目标路径  
                target: 'http://127.0.0.1:8001',
                // 允许跨域
                changeOrigin: true,
                ws: true,
                pathRewrite: {
                    '^/api': ''
                }
            }
        }
    }
}
```

​        重新启动VueCli项目。

#### 2.4 生产环境中的反向代理配置方法

​        前端应用程序 部署在 Nginx服务器上

​        后端应用程序 部署在 Tomcat服务器上

​        Nginx 反向代理 到 Tomcat

### 三：Axios直接使用

#### 3.0 Axios发送请求

引入 axios
import axios from 'axios'

axios 发送请求

axios.get( String url , Object arguments )

axios.post( String url , Object arguments )

axios.put( String url , Object arguments )

axios.patch( String url , Object arguments )

axios.delete( String url , Object arguments )

arguments：

url                                    请求的url路径

baseURL                         请求的基底url路径

method                            请求类型  GET POST PUT DELETE

headers                           请求头信息

params                            Get参数

data                                    Post参数（body参数）

responseType                响应类型（默认json）

transformRequest        Post请求前对data进行转换

transformResponse     响应后对响应数据进行转换

timeout                            请求超时的毫秒数

validateStatus                验证http响应状态（默认[200,300)之间为resolve）

#### 3.1 Axios发送Get类型请求

```javascript
// 引入 axios
import axios from 'axios'

// 发送 Get 请求
axios.get( "/api/brand" , { params : { cate_id : 1916 } } ).then( response=>{
    console.log("请求成功")
    console.log(response)
} , response=>{
    console.log("请求失败")
    console.log(response)
} )
```

#### 3.2 Axios发送Post类型请求

```javascript
// 引入 axios
import axios from 'axios'

// 引入 qs
import qs from 'qs'

// Post 请求报文体 中的 data 数据格式转换
// JSON：{ "user_phone" : "13900000001" , "user_password" : "abc123" , "user_name" : "axios张三" }
// 请求报文格式："user_phone=13900000001&user_password=abc123&user_name=axios%E5%BC%A0%E4%B8%89"

// 发送 Post 请求  【添加资源】
// 注册
axios.create()({
    url : "/api/regist",
    method : "POST",
    data : qs.stringify( { "user_phone" : "13900000001" , "user_password" : "abc123" , "user_name" : "axios张三" } )
}).then(response=>{
    console.log( "请求成功" )
    console.log( response )
},()=>{
    console.log( "请求失败" )
})
```

```javascript
// 登录
axios.create()({
    url : "/api/login",
    method : "POST",
    data : qs.stringify( { username : "13900000001" , password : "abc123" } )
}).then( response=>{
    console.log( "请求成功" )
    // 判断 登录 是否 成功
    if( response.data.httpcode == 200 ){
        console.log( "登录成功" )
        // 将 服务器 签发的令牌 token 存入到 本地缓存 localStorage中
        localStorage.setItem( "token" , response.data.data )
    }else if( response.data.httpcode == 401 ){
        console.log( "登录失败" )
    }
    console.log( response )
} , response=>{
    console.log( "请求失败" )
    console.log( response )
} )
```

```javascript
// 添加 收货地址
axios.create()({
    url : "/api/customer/useraddress",
    method : "POST",
    headers : {
        Authorization : localStorage.getItem("token")
    },
    data : qs.stringify({
        uaddr_name : "张妈妈",
        uaddr_phone : "13900000002",
        uaddr_province : "上海市",
        uaddr_city : "徐汇区",
        uaddr_district : "市区",
        uaddr_address : "虹梅南路2700号1号楼",
        uaddr_isdefault : 0
    })
}).then( response=>{
    console.log( "请求成功" )
    console.log( response )
} , response=>{
    console.log( "请求失败" )
    console.log( response )
} )
```

#### 3.3 Axios发送Put类型请求

```javascript
// 发送 Put 请求  【全局修改资源】
axios.create()({
    url : "/api/customer/useraddress",
    method : "PUT",
    headers : {
        Authorization : localStorage.getItem("token")
    },
    data : qs.stringify({
        uaddr_name : "张妈妈",
        uaddr_phone : "13900000002",
        uaddr_province : "上海市",
        uaddr_city : "徐汇区",
        uaddr_district : "市区",
        uaddr_address : "虹梅南路2700号1号楼",
        uaddr_isdefault : 0,
        uaddr_id : 33
    })
}).then( response=>{
    console.log( "请求成功" )
    console.log( response )
} , response=>{
    console.log( "请求失败" )
    console.log( response )
} )
```

#### 3.4 Axios发送Delete类型请求

```javascript
// 发送 Delete 请求        【删除资源】
axios.create()({
    url : "/api/customer/useraddress/32",
    method : "DELETE",
    headers : {
        Authorization : localStorage.getItem("token")
    }
}).then( response=>{
    console.log( "请求成功" )
    console.log( response )
} , response=>{
    console.log( "请求失败" )
    console.log( response )
} )
```

### 四：Axios封装

封装思路：

1：URL地址

​        

​        http://127.0.0.1:8080/api/customer/useraddress/32

​        后端网络域：http://127.0.0.1:8080/api/  => http://127.0.0.1:8001/

​        资源路径：customer/useraddress/

​        资源id：32

2：method

​        get        查询资源

​        post        添加资源

​        put        全局修改资源

​        patch    局部修改资源

​        delete    删除资源

3：headers

​        每次请求都添加：Authorization = 从localStorage中获取token

4：请求参数部分

​        get   =>   将 参数放入 params中

​        post/put/patch/delete => 将 参数  使用qs.stringify() 放入 data 中

5：请求错误时，根据业务状态号，进行统一错误处理

```javascript
// 导入 axios 模块
import axios from 'axios'
// 导入 qs 模块
import qs from 'qs'
// 导入 路由
import router from '@/router'

// 配置 axios
const request = axios.create({
    // 所有请求的统一基底路径  后端网络域
    baseURL : "/api/",
    // 发送 POST请求（POST/PUT/PATCH/DELETE） 之前 调用的方法
    transformRequest(data){
        // 形参 data 是 json 格式
        return qs.stringify(data)
        // 返回 是 通过 qs.stringify() 序列化 后的 字符串
    }
})

// 配置 Axios 的 请求 拦截器
request.interceptors.request.use( config=>{
    // 每次发送请求之前 从 localStorage中 获取 token令牌 放在 请求头中
    config.headers.Authorization = localStorage.getItem("token")
    return config
})

// 配置 Axios 的 响应 拦截器
request.interceptors.response.use( response=>{
    // 根据 响应报文体 中的 业务 状态号 进行 判断 业务错误类型
    switch( response.data.httpcode ){
        case 401 : router.push( { path: "/error" , query: { message : "请先登录！" } } );break;
        case 403 : router.push( { path: "/error" , query: { message : "没有访问权限！" } } );break;
        default :  return response
    }
} )

// 分别导出 get post  put  patch delete 5种请求方法

/**
 * @description 发送 get 请求的方法
 * @param {String} resource_url 请求的资源的路径
 * @param {Object} data 请求的参数
 * @return {Object} Promise异步任务对象
 */
export const get = ( resource_url , data = {} )=>{
    return request({
        url : resource_url,
        method : "GET",
        params : data
    })
}

/**
 * @description 发送 post 请求的方法
 * @param {String} resource_url 请求的资源的路径
 * @param {Object} data 请求的参数
 * @return {Object} Promise异步任务对象
 */
export const post = ( resource_url , data = {} )=>{
    return request({
        url : resource_url,
        method : "POST",
        data : data
    })
}

/**
 * @description 发送 put 请求的方法
 * @param {String} resource_url 请求的资源的路径
 * @param {Object} data 请求的参数
 * @return {Object} Promise异步任务对象
 */
export const put = ( resource_url , data = {} )=>{
    return request({
        url : resource_url,
        method : "PUT",
        data : data
    })
}

/**
 * @description 发送 patch 请求的方法
 * @param {String} resource_url 请求的资源的路径
 * @param {Object} data 请求的参数
 * @return {Object} Promise异步任务对象
 */
export const patch = ( resource_url , data = {} )=>{
    return request({
        url : resource_url,
        method : "PATCH",
        data : data
    })
}

/**
 * @description 发送 delete 请求的方法
 * @param {String} resource_url 请求的资源的路径
 * @param {Object} data 请求的参数
 * @return {Object} Promise异步任务对象
 */
export const del = ( resource_url , data = {} )=>{
    return request({
        url : resource_url,
        method : "DELETE",
        data : data
    })
}
```

### 五：封装业务模块

src/api/customer.js

```javascript
// 导入 utils/request.js 中的 get\post\put\patch\del 五大方法
import {get,post,put,del} from '@/utils/request.js'

// 资源路径
const CHINA_RESOURCE = "china"        // 行政地区资源路径
const REGIST_RESOURCE = "regist"    // 注册资源路径
const LOGIN_RESOURCE = "login"        // 登录资源路径
const USER_RESOURCE = "customer/user"    // 客户资源路径
const USERADDRESS_RESOURCE = "customer/useraddress"    // 客户收货信息资源路径


// 导出 业务操作 方法

// 查询行政地区列表
export const getChinaList = data => get( CHINA_RESOURCE , data )

// 客户注册
export const regist = data => post( REGIST_RESOURCE , data )

// 客户登录
export const login = data => post( LOGIN_RESOURCE , data )

// 获取客户信息
export const getUser = () => get( USER_RESOURCE )

// 添加收货信息
export const postUserAddress = data => post( USERADDRESS_RESOURCE , data )

// 修改收货信息
export const putUserAddress = data => put( USERADDRESS_RESOURCE , data )

// 删除收货信息
export const deleteUserAddress = id => del( USERADDRESS_RESOURCE + "/" + id )

// 查询收货信息列表
export const getUserAddressList = () => get( USERADDRESS_RESOURCE )
```

src/api/order.js

```javascript
// 导入 utils/request.js 中的 get\post\put\patch\del 五大方法
import {get,post,put,patch,del} from '@/utils/request.js'

// 资源路径
const CART_RESOURCE = "customer/cart"        // 购物车资源路径
const ORDER_RESOURCE = "customer/order"        // 订单资源路径

// 导出 业务操作 方法

// 添加购物车
export const postCart = data => post( CART_RESOURCE , data )

// 修改购物车
export const putCart = data => put( CART_RESOURCE , data )

// 删除购物车
export const deleteCart = id => del( CART_RESOURCE + "/" + id )

// 清空购物车
export const emptyCart = () => del( CART_RESOURCE )

// 查询购物车
export const getCartList = () => get( CART_RESOURCE )

// 添加订单
export const postOrder = data => post( ORDER_RESOURCE , data )

// 修改订单
export const patchOrder = data => patch( ORDER_RESOURCE , data )

// 查询单个订单信息
export const getOrder = id => get( ORDER_RESOURCE + "/" + id )

// 查询订单列表
export const getOrderList = () => get( ORDER_RESOURCE )
```

src/api/product.js

```javascript
// 导入 utils/request.js 中的 get\post\put\patch\del 五大方法
import {get} from '@/utils/request.js'

// 申明 资源路径
const BRAND_RESOURCE = "brand"            // 商品品牌资源路径
const CATEGORY_RESOURCE = "category"    // 商品分类资源路径
const ATTRIBUTE_RESOURCE = "attr"        // 商品属性资源路径
const SPU_RESOURCE = "spu"                // 商品Spu资源路径

// 导出 业务操作 方法

// 查询品牌列表
export const getBrandList = data => get( BRAND_RESOURCE , data )

// 查询分类列表
export const getCategoryList = data => get( CATEGORY_RESOURCE, data )

// 查询单个分类信息
export const getCategory =  id => get( CATEGORY_RESOURCE + "/" + id  )

// 查询商品属性列表
export const getAttributeList = data => get( ATTRIBUTE_RESOURCE , data )

// 查询单个商品Spu
export const getSpu = id => get( SPU_RESOURCE + "/" + id )

// 查询商品Spu列表
export const getSpuList = data => get( SPU_RESOURCE , data )
```
