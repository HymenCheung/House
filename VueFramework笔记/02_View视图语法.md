## View 视图语法

### 一：Text和HTML

​        Text绑定：{{ 成员 }}

​        HTML绑定：v-html="成员"

```html
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>View视图语法</title>
        <script src="js/vue-3.0.11.prod.js"></script>
    </head>
    <body>

        <div id="app">
            <table border="1px" cellpadding="4px" cellspacing="4px" align="center">
                <tr>
                    <td>Text绑定：</td>
                    <td>{{brand}}</td>
                </tr>
                <tr>
                    <td>HTML绑定：</td>
                    <td v-html="brand"></td>
                </tr>
            </table>
        </div>

        <script>

        // 创建 Vue 根实例
        const app = Vue.createApp({
            data(){
                return {
                    brand : "<font color='red'>学掌门 www.atstudy.com</font>"
                }
            }
        })

        // Vue实例 挂载 视图
        app.mount( "#app" )

        </script>

    </body>
</html>
```

### 二：Attribute

​        v-bind:attribute="成员"

```html
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>View视图语法</title>
        <script src="js/vue-3.0.11.prod.js"></script>
    </head>
    <body>

        <div id="app">
            <table border="1px" cellpadding="4px" cellspacing="4px" align="center">
                <tr>
                    <td>Attribute绑定：</td>
                    <td> <input v-bind:type="controller_type" v-bind:checked="agree"/> 同意本站协议 </td>
                </tr>
            </table>
        </div>

        <script>

        // 创建 Vue 根实例
        const app = Vue.createApp({
            data(){
                return {
                    controller_type : "checkbox",
                    agree : true
                }
            }
        })

        // Vue实例 挂载 视图
        app.mount( "#app" )

        </script>

    </body>
</html>
```

### 三：动态Attribute

​        v-bind:[成员]="成员"

```html
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>View视图语法</title>
        <script src="js/vue-3.0.11.prod.js"></script>
    </head>
    <body>

        <div id="app">
            <table border="1px" cellpadding="4px" cellspacing="4px" align="center">
                <tr>
                    <td>动态Attribute绑定：</td>
                    <td> <input type="checkbox" v-bind:[attribute_name]="attribute_value"/> 同意本站协议 </td>
                </tr>
            </table>
        </div>

        <script>

        // 创建 Vue 根实例
        const app = Vue.createApp({
            data(){
                return {
                    attribute_name : "disabled",
                    attribute_value : true
                }
            }
        })

        // Vue实例 挂载 视图
        app.mount( "#app" )

        </script>

    </body>
</html>
```

### 四：Class

​        v-bind:class="成员"        （成员：String\Array\Object）

```html
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>View视图语法</title>
        <script src="js/vue-3.0.11.prod.js"></script>
        <style>
        .btn{
            padding: 0.5rem 1.25rem;
            border: 0rem;
            border-radius: 0.5rem;
            cursor: pointer;
        }
        .btn-primary{
            background-color: #37d;
            color: #fff;
        }
        </style>
    </head>
    <body>

        <div id="app">
            <table border="1px" cellpadding="4px" cellspacing="4px" align="center">
                <tr>
                    <td>Class绑定单值：</td>
                    <td>
                        <button :class="class_name"> 按钮 </button>
                    </td>
                </tr>
                <tr>
                    <td>Class绑定数组：</td>
                    <td>
                        <button :class="class_array"> 按钮 </button>
                    </td>
                </tr>
                <tr>
                    <td>Class绑定JSon：</td>
                    <td>
                        <button :class="class_json"> 按钮 </button>
                    </td>
                </tr>
            </table>
        </div>

        <script>

        // 创建 Vue 根实例
        const app = Vue.createApp({
            data(){
                return {
                    class_name : "btn",
                    class_array : [ "btn" , "btn-primary" ],
                    class_json : { "btn" : true , "btn-primary" : false }
                }
            }
        })

        // Vue实例 挂载 视图
        app.mount( "#app" )

        </script>

    </body>
</html>
```

### 五：Style

​        v-bind:style="成员"        （成员：Object）

```html
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>View视图语法</title>
        <script src="js/vue-3.0.11.prod.js"></script>
    </head>
    <body>

        <div id="app">
            <table border="1px" cellpadding="4px" cellspacing="4px" align="center">
                <tr>
                    <td>Style绑定JSon：</td>
                    <td>
                        <button :style="style_json"> Button </button>
                    </td>
                </tr>
            </table>
        </div>

        <script>

        // 创建 Vue 根实例
        const app = Vue.createApp({
            data(){
                return {
                    style_json : { "background-color" : "#fcc" , "color" : "#393" , "padding" : "0.5rem 1.25rem"  }
                }
            }
        })

        // Vue实例 挂载 视图
        app.mount( "#app" )

        </script>

    </body>
</html>
```

### 六：v-if 和 v-show

​        v-if="Boolean 条件表达式"

​        v-show="Boolean 条件表达式"

​        v-if：条件成立的Dom对象才会渲染出来，条件不成立的Dom对象不会被渲染出来。

​                适用于视图渲染一次后，条件结果不会改变的场景。

​        v-show：Dom对象一定会被渲染出来，条件成立的Dom对象显示，条件不成立的Dom对象隐藏。

​                适用于视图渲染一次后，条件结果会不断改变的场景。

```html
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>View视图语法</title>
        <script src="js/vue-3.0.11.prod.js"></script>
    </head>
    <body>

        <div id="app">
            <table border="1px" cellpadding="4px" cellspacing="4px" align="center">
                <tr>
                    <td>v-if：</td>
                    <td>
                        <button v-if="enable==false"> 启用 </button>
                        <button v-if="enable==true"> 禁用 </button>
                    </td>
                </tr>
                <tr>
                    <td>v-show：</td>
                    <td>
                        <button v-show="enable==false"> 启用 </button>
                        <button v-show="enable==true"> 禁用 </button>
                    </td>
                </tr>
            </table>
        </div>

        <script>
        // 创建 Vue 根实例
        const app = Vue.createApp({
            data(){
                return {
                    enable : true
                }
            }
        })
        // Vue 实例 挂载 视图
        app.mount( "#app" )

        </script>

    </body>
</html>
```

### 七：v-for

​        v-for="(item,index) of data" 

​        v-bind:key="成员"

```html
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>View视图语法</title>
        <script src="js/vue-3.0.11.prod.js"></script>
    </head>
    <body>

        <div id="app">
            <table border="1px" cellpadding="4px" cellspacing="4px" align="center">
                <thead>
                    <tr>
                        <th>序号</th>
                        <th>学生编号</th>
                        <th>学生姓名</th>
                        <th>学生年龄</th>
                        <th>学生性别</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="(student,index) of student_list" :key="student.student_id">
                        <td>{{index+1}}</td>
                        <td>{{student.student_id}}</td>
                        <td>{{student.student_name}}</td>
                        <td>{{student.student_age}}</td>
                        <td>{{student.student_gender}}</td>
                    </tr>
                </tbody>
            </table>
        </div>

        <script>

        // 创建 Vue 根实例
        const app = Vue.createApp( {
            data(){
                return {
                    student_list : [
                        { student_id : 10001 , student_name : "张三" , student_age : 21 , student_gender : "男" },
                        { student_id : 10002 , student_name : "李四" , student_age : 22 , student_gender : "男" },
                        { student_id : 10003 , student_name : "王五" , student_age : 23 , student_gender : "女" },
                        { student_id : 10004 , student_name : "赵六" , student_age : 24 , student_gender : "男" },
                    ]
                }
            }
        } )
        // Vue 实例 挂载 视图
        app.mount( "#app" )
        </script>

    </body>
</html>
```

### 八：v-for和v-if的嵌套使用

```html
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>View视图语法</title>
        <script src="js/vue-3.0.11.prod.js"></script>
    </head>
    <body>

        <div id="app">
            <table border="1px" cellpadding="4px" cellspacing="4px" align="center">
                <thead>
                    <tr>
                        <th>序号</th>
                        <th>学生编号</th>
                        <th>学生姓名</th>
                        <th>学生年龄</th>
                        <th>学生性别</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="(student,index) of student_list" :key="student.student_id">
                        <template v-if="student.student_gender == '男' ">
                            <td>{{index+1}}</td>
                            <td>{{student.student_id}}</td>
                            <td>{{student.student_name}}</td>
                            <td>{{student.student_age}}</td>
                            <td>{{student.student_gender}}</td>
                        </template>
                    </tr>
                </tbody>
            </table>
        </div>

        <script>

        // 创建 Vue 根实例
        const app = Vue.createApp( {
            data(){
                return {
                    student_list : [
                        { student_id : 10001 , student_name : "张三" , student_age : 21 , student_gender : "男" },
                        { student_id : 10002 , student_name : "李四" , student_age : 22 , student_gender : "男" },
                        { student_id : 10003 , student_name : "王五" , student_age : 23 , student_gender : "女" },
                        { student_id : 10004 , student_name : "赵六" , student_age : 24 , student_gender : "男" },
                    ]
                }
            }
        } )
        // Vue 实例 挂载 视图
        app.mount( "#app" )
        </script>

    </body>
</html>
```
