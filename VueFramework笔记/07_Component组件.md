## Component组件

​        Vue-Framework通过Component组件机制，实现“渐进式开发”理念。

### 一：Vue实例注册组件【熟练】

Vue实例.component( String componentName , Object vue实例 )

```html
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>Component组件</title>
        <script src="js/vue-3.0.11.prod.js"></script>
    </head>
    <body>

        <div id="app">
            <atstudy-button></atstudy-button>
        </div>

        <script>

        // Vue根实例
        const app = Vue.createApp({
            data(){
                return {

                }
            }
        })

        // Vue根实例 注册 组件
        app.component("atstudy-button",{
            template : `<button> Button </button>`
        })

        // Vue根实例 挂载视图
        app.mount( "#app" )

        </script>

    </body>
</html>
```

### 二：Component组件中的Vue成员【熟练】

template：视图（必须有唯一的根节点）

data：数据

methods：事件的处理方法

computed：计算属性

watch：侦听

```html
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>Component组件</title>
        <script src="js/vue-3.0.11.prod.js"></script>
    </head>
    <body>

        <div id="app">
            <atstudy-button></atstudy-button>
        </div>

        <script>

        // Vue根实例
        const app = Vue.createApp({
            data(){
                return {

                }
            }
        })

        // Vue根实例 注册 组件
        app.component("atstudy-button",{
            template : `
                <button @click="btn_clicked()"> {{count}} </button>
            `,
            data(){
                return {
                    count : 1
                }
            },
            methods : {
                btn_clicked(){
                    this.count++
                }
            }
        })

        // Vue根实例 挂载视图
        app.mount( "#app" )

        </script>

    </body>
</html>
```

### 三：Props数据传递【熟练】

Vue实例中的成员：

props：从组件标签上传递来的数据

>  /components/film.js

```javascript
export default {
    template : `
    <div class="film">
        <img :src="'./img/'+film.film_cover_image" style="width:100%"/>
        <div class="film-title"> {{ film.film_name }} </div>
        <div class="film-en-title"> {{ film.film_title }} </div>
    </div>
    `,
    props : ["film"]
}
```

> /components/film_list.js

```javascript
export default{
    template : `
        <div class="film-list">
            <film v-for="film of film_list"
                :key="film.film_id"
                :film="film"></film>
        </div>
    `,
    props : ["film_list"]
}
```

> html

```html
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>Component组件Props数据传递</title>
        <script src="js/vue-3.0.11.prod.js"></script>
        <style>
        .film{
            width: 200px;
            padding: 10px;
            border-radius: 6px;
            box-shadow: 0px 0px 8px #ccc;
            margin: 10px;
        }
        .film-title{
            font-size: 14px;
            color: #333;
            margin: 5px 0px;
            padding: 0px 10px;
        }
        .film-en-title{
            font-size: 12px;
            color: #666;
            padding: 0px 10px;
        }
        .film-list{
            width: 1200px;
            margin: 0px auto;
            display: flex;
            flex-wrap: wrap;
        }
        </style>
    </head>
    <body>

        <div id="app">
            <film-list :film_list="film_list"></film-list>
        </div>

        <script type="module">

        // 导入 当前页面 需要的 组件
        // 导入 一个影片的组件
        import component_film from './components/film.js'
        // 导入 影片列表的组件
        import component_film_list from './components/film_list.js'


        // Vue根实例
        const app = Vue.createApp({
            data(){
                return {
                    // 一组影片的数据
                    film_list : []
                }
            },
            // Vue中的生命周期事件  挂载视图之后
            mounted(){
                console.log( "挂载视图完毕" )
                // 向服务器发送请求 加载 获取影片列表数据
                fetch("./data/film_list.json").then( response => response.json() ).then( response => {
                    this.film_list = response
                } )
            }
        })

        // Vue根实例 注册 组件
        app.component( "film" , component_film )
        app.component( "film-list" , component_film_list )

        // Vue根实例 挂载 视图
        app.mount( "#app" )

        </script>

    </body>
</html>
```

### 四：InheritAttrs属性继承性【知道】

Vue的成员：

inheritAttrs：Boolean，HTML属性是否要继承到组件中的标签上

```html
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>Component组件_InheritAttrs属性继承性</title>
        <script src="js/vue-3.0.11.prod.js"></script>
    </head>
    <body>

        <div id="app">

            <comp title="看不清！点击换一张！"></comp>

        </div>

        <script>
        // 申明组件
        let comp = {
            template : `
                <button> 组件按钮 </button>
            `,
            inheritAttrs : false
        }

        // Vue根实例
        const app = Vue.createApp({
            data(){
                return {

                }
            }
        })

        // Vue根实例注册组件
        app.component( "comp" , comp )

        // Vue根实例挂载视图
        app.mount( "#app" )

        </script>

    </body>
</html>
```

### 五：Slot插槽【熟练】

Component组件的template视图中，使用<slot></slot>插槽，可以获取到组件双标签内部的文本数据。

```html
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>Component组件_Slot插槽</title>
        <script src="js/vue-3.0.11.prod.js"></script>
    </head>
    <body>
        <div id="app">

            <comp>首页</comp>
            <comp>上一页</comp>
            <comp>下一页</comp>
            <comp>尾页</comp>

        </div>
        <script>
        // 申明组件
        let comp = {
            template : `
                <button> <slot></slot> </button>
            `
        }

        // Vue根实例
        const app = Vue.createApp({
            data(){
                return {

                }
            }
        })

        // Vue根实例注册组件
        app.component( "comp" , comp )

        // Vue根实例挂载视图
        app.mount( "#app" )

        </script>
    </body>
</html>
```

### 六：Emit 载荷调用【知道】

```html
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>Component组件_Emit载荷调用</title>
        <script src="js/vue-3.0.11.prod.js"></script>
    </head>
    <body>

        <div id="app">

            <comp @prox_fn="btn_clicked" ></comp>

        </div>

        <script>
        // 申明组件
        let comp = {
            template : `
                <div>
                    <button @click="$emit('prox_fn',{index:0,name:'首页'})"> 首页 </button>
                    <button @click="$emit('prox_fn',{index:1,name:'上一页'})"> 上一页 </button>
                    <button @click="$emit('prox_fn',{index:2,name:'下一页'})"> 下一页 </button>
                    <button @click="$emit('prox_fn',{index:3,name:'尾页'})"> 尾页 </button>
                </div>
            `
        }

        // Vue根实例
        const app = Vue.createApp({
            data(){
                return {

                }
            },
            methods : {
                btn_clicked( payload ){
                    console.log( `第${payload.index}个${payload.name}按钮被点击了！` )
                }
            }
        })

        // Vue根实例注册组件
        app.component( "comp" , comp )

        // Vue根实例挂载视图
        app.mount( "#app" )

        </script>

    </body>
</html>
```

### 七：V-Model 双向绑定【知道】

根实例的数据 => 组件的视图 ：props数据传递

组件的视图 => 根实例的数据：emit 载荷调用·

```html
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>Component组件_V-Model双向绑定</title>
        <script src="js/vue-3.0.11.prod.js"></script>
    </head>
    <body>

        <div id="app">
            <comp :username="username" @proxy_input="username_input"></comp>
        </div>

        <script>

        // 申明 组件
        let comp = {
            template : `
            <form>
                <input type="text" name="username" v-model.lazy="username" @change="$emit('proxy_input',$event.target.value)" placeholder="请输入账户名称"/>
                <span>{{username}}</span>
                <br/><br/>
                <input type="text" name="userpass" placeholder="请输入账户密码"/>
                <br/><br/>
                <input type="text" placeholder="请再次确认密码"/>
                <br/><br/>
                <input type="text" name="nickname" placeholder="请输入用户昵称"/>
                <br/><br/>
                <button> 立即注册 </button>
            </form>
            `,
            props : ["username"]
        }
        // Vue根实例
        const app = Vue.createApp({
            data(){
                return {
                    username : "zhangsan"
                }
            },
            methods : {
                username_input( val ){
                    this.username = val
                }
            }
        })

        // Vue根实例注册组件
        app.component( "comp" , comp )

        // Vue根实例挂载视图
        app.mount( "#app" )
        </script>
    </body>
</html>
```

### 八：Is 组件别名【掌握】

#### 8.1 无HTML内联结构的组件

​    <component is="组件名"></component>

```html
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>Component组件_Is组件别名</title>
        <script src="js/vue-3.0.11.prod.js"></script>
        <style>
        .alert{
            margin: 10px 0px;
            padding: 10px 20px;
            border-radius: 6px;
            display:flex;
            justify-content: space-between;
        }
        .alert .dismiss{
            cursor: pointer;
        }
        .alert-danger{
            background-color: #c66;
            color: #fff;
        }
        </style>
    </head>
    <body>

        <div id="app">
            <!--  直接使用组件  -->
            <alert_danger>出现了错误</alert_danger>
            <!--  使用组件别名  -->
            <component is="alert_danger">出现了错误！</component>
        </div>

        <script>
        // 申明组件
        let alert_danger = {
            template : `
                <div class="alert alert-danger">
                    <slot></slot>
                    <div class="dismiss">×</div>
                </div>
            ` 
        }

        // Vue根实例
        const app = Vue.createApp({
            data(){
                return {
                    comp_name : "comp"
                }
            },
            methods : {
            }
        })

        // Vue根实例注册组件
        app.component( "alert_danger" , alert_danger )

        // Vue根实例挂载视图
        app.mount( "#app" )

        </script>

    </body>
</html>
```

#### 8.2 有HTML内联结构的组件

​        <html标签 v-is=" '组件名' "></html标签>

```html
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>Component组件_v-is组件别名</title>
        <script src="js/vue-3.0.11.prod.js"></script>
    </head>
    <body>

        <div id="app">
            <table border="1px" cellpadding="4px" cellspacing="4px">
                <thead>
                    <tr>
                        <th>学生学号</th>
                        <th>学生姓名</th>
                        <th>学生年龄</th>
                        <th>学生性别</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-is="'comp'" 
                        v-for="student of student_list"
                        :key="student.student_id"
                        :student="student"></tr>
                </tbody>
            </table>
        </div>

        <script>
        // 申明组件
        let comp = {
            template : `
                <tr>
                    <td>{{student.student_id}}</td>
                    <td>{{student.student_name}}</td>
                    <td>{{student.student_age}}</td>
                    <td>{{student.student_gender}}</td>
                </tr>
            `,
            props : ["student"]
        }
        // Vue根实例
        const app = Vue.createApp({
            data(){
                return {
                    student_list : [
                        { student_id : 10001 , student_name : "张三" , student_age : 21 , student_gender : "男" },
                        { student_id : 10002 , student_name : "李四" , student_age : 22 , student_gender : "女" },
                        { student_id : 10003 , student_name : "王五" , student_age : 23 , student_gender : "男" },
                        { student_id : 10004 , student_name : "赵六" , student_age : 24 , student_gender : "男" },
                    ]
                }
            },
            methods : {
            }
        })

        // Vue根实例注册组件
        app.component( "comp" , comp )

        // Vue根实例挂载视图
        app.mount( "#app" )
        </script>

    </body>
</html>
```

### 九：动态组件【掌握】

```html
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>Component组件_Is组件别名</title>
        <script src="js/vue-3.0.11.prod.js"></script>
        <style>
        .alert{
            margin: 10px 0px;
            padding: 10px 20px;
            border-radius: 6px;
            display:flex;
            justify-content: space-between;
        }
        .alert .dismiss{
            cursor: pointer;
        }
        .alert-danger{
            background-color: #c66;
            color: #fff;
        }
        .alert-success{
            background-color: #6c6;
            color: #fff;
        }
        </style>
    </head>
    <body>

        <div id="app">
            <component :is="operation_success?'alert_success':'alert_danger'">提示信息！</component>
        </div>

        <script>
        // 申明组件
        let alert_danger = {
            template : `
                <div class="alert alert-danger">
                    <slot></slot>
                    <div class="dismiss">×</div>
                </div>
            ` 
        }

        let alert_success = {
            template : `
                <div class="alert alert-success">
                    <slot></slot>
                    <div class="dismiss">×</div>
                </div>
            ` 
        }

        // Vue根实例
        const app = Vue.createApp({
            data(){
                return {
                    operation_success : false
                }
            },
            methods : {
            }
        })

        // Vue根实例注册组件
        app.component( "alert_danger" , alert_danger )
        app.component( "alert_success" , alert_success )

        // Vue根实例挂载视图
        app.mount( "#app" )

        </script>

    </body>
</html>
```

### 十：Keep Alive 状态持久化【知道】

```html
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>Component组件_KeepAlive状态持久</title>
        <script src="js/vue-3.0.11.prod.js"></script>
    </head>
    <body>

        <div id="app">

            <button @click="login_clicked()">登录</button>
            <button @click="regist_clicked()">注册</button>

            <hr/>

            <keep-alive>
                <component :is="'comp_'+page"></component>
            </keep-alive>

        </div>

        <script>
        // 申明组件
        let comp_login = {
            template : `
            <form name="form_login">
                <input type="text" name="login_username" placeholder="请输入账户名称"/>
                <br/><br/>
                <input type="text" name="login_userpass" placeholder="请输入账户密码"/>
                <br/><br/>
                <button>立即登录</button>
            </form>
            `
        }

        let comp_regist = {
            template : `
            <form name="form_regist">
                <input type="text" name="regist_username" placeholder="请输入账户名称"/>
                <br/><br/>
                <input type="text" name="regist_userpass" placeholder="请输入账户密码"/>
                <br/><br/>
                <input type="text" name="regist_checkpass" placeholder="请再次确认密码"/>
                <br/><br/>
                <input type="text" name="regist_nickname" placeholder="请输入用户昵称"/>
                <br/><br/>
                <button>立即注册</button>
            </form>
            `
        }

        // Vue根实例
        const app = Vue.createApp({
            data(){
                return {
                    page : "login"
                }
            },
            methods : {
                login_clicked(){
                    this.page = "login"
                },
                regist_clicked(){
                    this.page = "regist"
                }
            }
        })

        // Vue根实例 注册组件
        app.component( "comp_login" , comp_login )
        app.component( "comp_regist" , comp_regist )

        // Vue根实例 挂载视图
        app.mount( "#app" )
        </script>

    </body>
</html>
```
