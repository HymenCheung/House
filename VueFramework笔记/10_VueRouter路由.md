## VueRouter路由

​        Vue使用Router路由系统，实现单页面应用（Single Page）的开发思想。

### 一：Router路由对象

/src/router/index.js

```javascript
// 导入 页面级组件
import Home from '@/views/Home.vue'
import FilmList from '@/views/FilmList.vue'

// 配置 路由 列表
const routes = [
    {
        path : '/',            // 路由映射的URL路径
        name : 'Home',        // 路由的名称
        component : Home    // 路由的页面级组件
    },
    {
        path : '/filmlist',
        name : 'FilmList',
        component : FilmList
    }
]
```

### 二：Router-View 路由视图

Vue组件的视图中

```vue
<!--  每个页面 不一样的 部分  【路由视图】  -->
<router-view></router-view>
<!--  /每个页面 不一样的 部分  【路由视图】  -->
```

### 三：Router-Link 路由链接

Vue组件的视图中

```vue
<router-link to="./">首页</router-link>
```

### 四：路由守卫

​        路由守卫：路由的生命周期方法

```javascript
// 路由守卫
router.beforeEach( ( to , from , next )=>{

    console.log( "路由守卫：进入到 beforeEach() 方法" )
    // 判断 当前要访问的页面 是否需要登录
    if( to.meta.needLogin ){
        // 当前要访问的页面 需要登录
        console.log( "路由守卫：当前要访问的页面 需要登录" )
        // 判断 当前用户 是否已经 登录 成功
        // 获取 存放在 LocalStorage 中的 token
        let token = localStorage.getItem( "token" )
        if( token == null ){
            // 当前用户 还没有 登录
            console.log( "路由守卫：当前用户 还没有 登录" )
            // 开始 路由 转发
            next( { name : "Error" , params : { message : "请先登录！" } } )
        }else{
            // 当前用户 已经 登录
            console.log( "路由守卫：当前用户 已经 登录" )
            // 开始 路由分发
            next()
        }
    }else{
        // 当前要访问的页面 不需要登录
        console.log( "路由守卫：当前要访问的页面 不需要登录" )
        // 开始 路由分发
        next()
    }

} )
```

整理代码：

```javascript
// 路由守卫
router.beforeEach( ( to , from , next )=>{
    // 判断 当前要访问的页面 是否需要登录
    if( to.meta.needLogin ){
        // 当前要访问的页面 需要登录
        // 判断 当前用户 是否已经 登录 成功
        // 获取 存放在 LocalStorage 中的 token
        let token = localStorage.getItem( "token" )
        if( token == null ){
            // 当前用户 还没有 登录
            // 开始 路由 转发
            next( { name : "Error" , params : { message : "请先登录！" } } )
        }
    }
    // 代码执行到这一步：
    // 1：当前页面根本就不需要登录
    // 2：当前页面需要登录，但是用户已经登录成功
    next()
} )
```

### 五：Children 子路由

```javascript
// 配置 路由 列表
const routes = [
    {
        path : '/',            // 路由映射的URL路径
        name : 'Home',        // 路由的名称
        component : Home    // 路由的页面级组件
    },
    {
        path : '/filmlist',
        name : 'FilmList',
        component : FilmList,
        // Children 子路由
        children : [
            {
                path : 'story',
                name : 'Story',
                component : Story
            },
            {
                path : 'science',
                name : 'Science',
                component : Science
            },
            {
                path : 'cartoon',
                name : 'Cartoon',
                component : Cartoon
            }
        ]
    },
    {
        path : '/login',
        name : 'Login',
        component : Login
    },
    {
        path : '/error',
        name : 'Error',
        component : Error
    },
    {
        path : '/usercenter',
        name : 'UserCenter',
        component : UserCenter,
        meta : {
            needLogin : true
        }
    },
    {
        path : '/order',
        name : 'Order',
        component : Order,
        meta : {
            needLogin : true
        }
    }
]
```
