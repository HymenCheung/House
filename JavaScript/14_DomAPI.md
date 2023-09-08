## DomAPI

​    文档对象模型（Document Object Model，简称DOM），是W3C组织推荐的处理可扩展置标记语言的标准编程API接口。

### Dom对象获取

```html
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>ECMAScript</title> 
    </head>
    <body>
        <button id="bt1"> id = bt1 </button>
        <hr/>
        <button class="btn"> class = btn </button>
        <button class="btn"> class = btn </button>
        <button class="btn"> class = btn </button>
        <hr/>
        <button name="bwf"> name = bwf </button>
        <button name="bwf"> name = bwf </button>
        <button name="bwf"> name = bwf </button>
        <hr/>
        <script>
            // 根据id获取Dom对象
            let a = document.getElementById("bt1");
            document.write(a+"<hr/>");
            // 根据class获取Dom对象集合
            let b = document.getElementsByClassName("btn");
            document.write(b+"<hr/>");
            // 根据name获取Dom对象集合
            let c = document.getElementsByName("bwf");
            document.write(c+"<hr/>");
            // 根据标签名获取Dom对象集合
            let d = document.getElementsByTagName("button");
            document.write(d+"<hr/>");
        </script>
    </body>
</html>
```

### Dom对象操作

​    操作html属性和css样式：

```html
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>ECMAScript</title> 
    </head>
    <body>
        <div id="div1">Div1</div>
        <script>
            // 获取id为div1的dom对象
            var div1 = document.getElementById("div1");
            // 设置dom对象的html属性
            div1.align = "center";
            // 设置dom对象的style属性（css属性）
            div1.style.width = "200px";
            div1.style.border = "1px solid black";
        </script>
    </body>
</html>
```

​    操作内部内容：

```html
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>ECMAScript</title> 
    </head>
    <body>

        <div id="div1"></div>
        <div id="div2"></div>

        <script>
            // innerText 内部显示内容（HTML标签会被转义）
            document.getElementById("div1").innerText = "<font color='red'>博为峰网校</font>";
            // innerHTML 内部HTML内容（HTML标签不会被转义）
            document.getElementById("div2").innerHTML = "<font color='red'>博为峰网校</font>";
        </script>
    </body>
</html>
```

### Event 事件

#### Event 事件使用方式

##### 1 行内式

```html
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>ECMAScript</title> 
    </head>
    <body>
        <button onclick="javascript:{console.log('aaa');}"> 按 钮 </button>
    </body>
</html>
```

```html
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>ECMAScript</title> 
    </head>
    <body>
        <button id="bt1" onclick="bwf()"> 按 钮 </button>
        <script>
            function bwf(){
                console.log("aaa");
            }
        </script>
    </body>
</html>
```

##### 2 编程式

```html
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>ECMAScript</title> 
    </head>
    <body>
        <button id="bt1"> 按 钮 </button>
        <script>
            document.getElementById("bt1").onclick=function(){
                console.log("aaa");
            }
        </script>
    </body>
</html>
```

#### Event 事件分类

##### 1 Dom事件  （Document Object Model 文档对象模型）

​    鼠标事件：

​       onclick             鼠标单击监听行为

​       ondblclick        鼠标双击监听行为

​       onmouseover  鼠标进入可见部分监听行为

​       onmouseout    鼠标离开可见部分监听行为

​       onmousemove 鼠标在可见部分移动监听行为

​    键盘事件：

​        onkeydown   按键按下监听行为    在按键回显前调用，监听任意按键（回显键+功能键）

​        onkeypress    按键回显监听行为    在按键回显前调用，只监听回显键

​        onkeyup        按键弹起监听行为     在按键回显后调用，监听任意按键（回显键+功能键）

​    焦点事件：

​       onfocus     获取焦点监听行为

​       onblur       失去焦点监听行为

​    表单事件：

​        onchange  内容改变监听行为

​        onsubmit        表单提交    

##### 2 Bom事件  （Browser Object Model 文档对象模型）

​        

​        onresize        窗口调整尺寸

​        onscroll        窗口滚动

​        onload        页面加载并且渲染完毕

​        onunload        窗口关闭

​        

#### Event 事件对象

​        

​        event.srcElement                    // 事件源对象

​        event.clientX                            // 光标x轴坐标

​        event.clientY                            // 光标y轴坐标

​        event.preventDefault();        // 阻止默认事件

​        event.stopPropagation();        // 阻止事件传递

### Date 日期时间对象

```html
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>ECMAScript</title> 
    </head>
    <body>
        <script>
            // Date对象存放当前时间
            let date = new Date();
            console.log(`date = ${date}`);
            console.log(`FullYear = ${date.getFullYear()}`);
            console.log(`Month = ${date.getMonth()+1}`);
            console.log(`Date = ${date.getDate()}`);
            console.log(`Day = ${date.getDay()}`);
            console.log(`Hours = ${date.getHours()}`);
            console.log(`Minutes = ${date.getMinutes()}`);
            console.log(`Seconds = ${date.getSeconds()}`);
            console.log(`Milliseconds = ${date.getMilliseconds()}`);
            console.log(`time = ${date.getTime())}`);
          </script>
    </body>
</html>
```

### 异步任务

#### Timeout

```html
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>ECMAScript</title> 
    </head>
    <body>

        <input type="text" id="txt1"/>

        <script>
            // 将当前系统时间显示在id为txt1的单行文本中
            function showTime(){
                let date = new Date();
                document.getElementById("txt1").value =
                    `${date.getHours()} : ${date.getMinutes()} : ${date.getSeconds()}`;
                // 递归延时1秒调用函数
                setTimeout(showTime,1000);
            }
            // 延时1秒调用函数
            setTimeout(showTime,1000);
        </script>
    </body>
</html>
```

#### Interval

```html
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>ECMAScript</title> 
    </head>
    <body>

        <input type="text" id="txt1"/>
        <button id="bt1"> 暂停 </button>
        <button id="bt2"> 继续 </button>

        <script>
            // 将当前系统时间显示在id为txt1的单行文本中
            function showTime(){
                let date = new Date();
                document.getElementById("txt1").value =
                    `${date.getHours()} : ${date.getMinutes()} : ${date.getSeconds()}`;
            }
            // 延时1秒调用函数
            let timer = setInterval(showTime,1000);
            // 暂停计时器
            document.getElementById("bt1").onclick=function(){
                clearInterval(timer);
            }
            // 重启计时器
            document.getElementById("bt2").onclick=function(){
                timer = setInterval(showTime,1000);
            }
        </script>
    </body>
</html>
```

### parentNode和childNodes
