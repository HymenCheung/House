## Vuex 状态管理

​        Vue是通过Component组件实现"渐进式开发"思想，提高代码复用性。

​        Component组件带来的问题：组件和组件之间的通讯，非常繁琐。

​        使用Vuex状态管理，来统一管理 数据、事件。

### 一：Store 状态仓库

#### 1.1 State 状态数据

​        Vuex统一管理数据。代替各个组件Vue实例中的data成员。

#### 1.2 Mutations 同步方法

​        Vuex统一管理同步事件。

​        同步事件：让主任务去完成的事件。一般存放执行速度非常快的，非耗时任务。操作内存中的数据。

#### 1.3 Actions 异步方法

​        Vuex统一管理异步事件。

​        异步事件：单独创建异步任务去完成的事件。一般存放执行速度较慢，耗时任务。异步请求服务器端数据。

#### 1.4 Modules 模块化

​        Vuex 根据 业务模块 分模块Store仓库 。

### 二：Store 分仓库

src/store/modules/customer.js

```javascript
// customer 客户模块的 Store仓库
export default {
    // 是否开启独立命名空间
    // false ：逻辑分区
    // true    ： 物理分区
    namespaced : true,
    state: {
    },
    mutations: {
    },
    actions: {
    },
}
```

src/store/modules/order.js

```javascript
// order 订单模块的 Store仓库
export default {
    // 是否开启独立命名空间
    // false ：逻辑分区
    // true    ： 物理分区
    namespaced : true,
    state: {
    },
    mutations: {
    },
    actions: {
    },
}
```

src/store/modules/product.js

```javascript
// product 商品模块的 Store仓库
export default {
    // 是否开启独立命名空间
    // false ：逻辑分区
    // true    ： 物理分区
    namespaced : true,
    state: {
    },
    mutations: {
    },
    actions: {
    },
}
```

总仓库拼装子仓库

src/store/index.js

```javascript
// 从 vuex 模块 导入 createStore 创建状态仓库 方法
import { createStore } from 'vuex'

// 导入 分 仓库
import customer from '@/store/modules/customer.js'
import order from 'modules/order.js'
import product from 'modules/product.js'

// 导出 创建的 总仓库
export default createStore({
  // 总仓库 只进行 分仓库的拼装
  modules: {
      customer,
      order,
      product
  }
})
```

### 三：State 状态数据

#### 3.1 mapState 辅助函数

```javascript
// 从 vuex 模块 中 导入 辅助函数
import {mapState} from 'vuex'
```

#### 3.2 State 状态数据映射

```javascript
export default {
    // computed 计算 属性
    computed: {
        // 将 仓库中的state状态数据 映射 到本地Vue实例中 computed 计算属性
        ...mapState( [ "product" ] )
    }
}
```

#### 3.3 视图中使用State状态数据

```vue
<div class="category"
     v-for="category of product.category_list_1"
     :key="`category-${category.cate_id}`">{{category.cate_name}}</div>
```

### 四：Mutations 同步方法

#### 4.1 Mutations 同步方法的格式

```javascript
/**
 * @description 【添加分类】按钮被点击了
 * @param {Object} context 当前仓库的上下文对象
 * @param {Object} payload 载荷数据
 */
add_category_clicked( context , payload ){
    // 将 用户填写的 分类编号、分类名称 添加到 分类列表 中
    context.category_list_1.push({ 
        cate_id : context.add_cate_id , 
        cate_name : context.add_cate_name
    })
}
```

#### 4.2 mapMutations 辅助函数

```javascript
// 从 vuex 模块 中 导入 辅助函数
import {mapMutations} from 'vuex'
```

#### 4.3 Mutations 同步方法映射

```javascript
// methods 成员
methods:{
    // 将 仓库中的mutations同步方法 映射 到本地Vue实例中 methods 成员中
    ...mapMutations({
        // 映射到本地Vue实例中的方法名称 : 要映射的目标方法
        add_category_clicked : "product/add_category_clicked"
    })
}
```

#### 4.4 视图中调用映射方法

```vue
<button type="button" @click="add_category_clicked( {name:'张三',age:20,gender:'男'} )"> 
    添加分类 
</button>
```

### 五：Actions 异步方法

#### 5.1 Actions 异步方法的格式

```javascript
/**
 * @description 根据父级分类编号获取下属分类列表的方法
 * @param {Object} context 当前仓库的上下文对象
 *         @field {Object} state 当前仓库中的state状态数据
 *         @field {Function} commit 当前仓库中的mutations同步方法的载荷调用方法
 *         @field {Function} dispatch 当前仓库中的actions异步放的载荷调用方法
 *         @field {Object} getters 当前仓库中state状态数据的访问器
 * @param {Object} payload 载荷数据
 */
getCategoryList( context , payload ){

    // 访问 本仓库中的 state 状态数据
    // context.state.category_list_1

    // 调用 本仓库中的 action 异步方法
    // context.dispatch( "testAction" , { name : '张三' , age : 20 } )

    // 调用 本仓库中的 mutation 同步方法
    // context.commit( "testMutation" , { name : '张三' , age : 20 } )

    // 发送ajax请求 根据父级分类编号 查询 下属的分类列表
    getCategoryList( { pid : '' } ).then(response=>{
        console.log( "请求成功" )
        // 将 获取到的 分类列表 赋值给 state 状态数据中的 category_list_1
        context.state.category_list_1 = response.data.data
    },response=>{
        console.log( "请求失败" )
        console.log(response)
    })
}
```

#### 5.2 mapActions 辅助函数

```javascript
// 从 vuex 模块 中 导入 辅助函数
import {mapActions} from 'vuex'
```

#### 5.3 Actions 异步方法映射

```javascript
// methods 成员
methods:{
    // 将 仓库中的actions异步方法 映射 到本地Vue实例中 methods 成员中
    ...mapActions({
        // 映射到本地Vue实例中的方法名称 : 要映射的目标方法
        getCategoryList : "product/getCategoryList"
    })
}
```

#### 5.4 调用映射方法

```javascript
// 发送 ajax 请求 获取 所有的一级分类数据
this.getCategoryList()
```
