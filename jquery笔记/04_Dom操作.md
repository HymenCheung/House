## Dom操作

### 一：JQuery.attr()

JQuery.attr( String name )                    获取dom属性的文档值（无法获取innerText值即标签值）

JQuery.attr( String name,Value value )    设置dom属性的文档值（无法设置innerText值）

JQuery.attr( Object json )                    设置多个dom属性的文档值

```html
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>JQuery</title>
        <script src="js/jquery-3.4.1.min.js"></script>
    </head>
    <body>
        <button class="btn" id="bt1"> 按 钮 </button>
        <button class="btn" id="bt2"> 按 钮 </button>
        <button class="btn" id="bt3"> 按 钮 </button>
        <button class="btn" id="bt4"> 按 钮 </button>
        <button class="btn" id="bt5"> 按 钮 </button>
        <script>
            //    获取dom属性的值
            console.log( $(".btn:first").attr("id") );
            //    设置dom属性的值
            $("#bt1").attr("disabled",true);
            //    设置多个dom属性的值
            $("#bt2").attr({"disabled":false,"title":"点击查看详情"});
        </script>
    </body>
</html>
```

### 二：JQuery.prop()

JQuery.prop( String name )            获取一个dom属性的当前值，状态属性会转换成Boolean值

JQuery.prop( String name , Boolean value )    设置一个dom属性的当前值

JQuery.prop( Object json )            设置多个dom属性的当前值值



.prop与.attr的区别

用户在浏览器中修改值，会将prop属性中的值一并修改，而attr获取到的还是原来的值

```html
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>JQuery</title>
        <script src="js/jquery-3.5.1.min.js"></script>
    </head>
    <body>
        <input type="text" id="count" name="count" value="1"/>
        <button class="btn-add"> + </button>
        <button class="btn-sub"> - </button>
        <script>
        $(document).ready(()=>{
            $(".btn-add").click(()=>{
                let count = $("#count").prop("value") * 1
                $("#count").prop( "value" , count+1 )
            })
            $(".btn-sub").click(()=>{
                let count = $("#count").attr("value") * 1
                $("#count").attr( "value" , count-1 )
            })
        })
        </script>
    </body>
</html>
```

### 三：内部HTML

JQuery.html()                获取Dom对象内部的HTML

JQuery.html( String html )    设置Dom对象内部的HTML

```html
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>JQuery</title>
        <script src="js/jquery-3.4.1.min.js"></script>
    </head>
    <body>
        <div id="div1">博为峰网校</div>
        <script>
            //    获取Dom对象内部的HTML
            console.log( $("#div1").html() );
            //    设置Dom对象内部的HTML
            $("#div1").html("<font color='red'>www.atstudy.com</font>");
        </script>
    </body>
</html>
```

### 四：内部文本

JQuery.text()                获取Dom对象内部的文字

JQuery.text( String text)        设置Dom对象内部的文字

```html
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>JQuery</title>
        <script src="js/jquery-3.4.1.min.js"></script>
    </head>
    <body>
        <div id="div1">博为峰网校</div>
        <script>
            //    获取Dom对象内部的文字
            console.log( $("#div1").text() );
            //    设置Dom对象内部的文字
            $("#div1").text("<font color='red'>www.atstudy.com</font>");
        </script>
    </body>
</html>
```

### 五：Value值

JQuery.val()                获取Dom对象的value值

JQuery.val( String value )    设置Dom对象的value值

```html
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>JQuery</title>
        <script src="js/jquery-3.4.1.min.js"></script>
    </head>
    <body>
        <input type="text" id="txt1" value="博为峰网校"/>
        <script>
            //    获取Dom对象的value值
            console.log( $("#txt1").val() );
            //    设置Dom对象的value值
            $("#txt1").val("www.atstudy.com");
        </script>
    </body>
</html>
```

### 六：CSS样式

JQuery.css( String name )                获取Dom对象的css样式值

JQuery.css( String name , String value )            设置Dom对象的css样式值

JQuery.css( Object json )                            设置Dom对象的多个css样式值

```html
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>JQuery</title>
        <script src="js/jquery-3.4.1.js"></script>
        <style>
            div{width: 200px; height: 30px; line-height: 30px; text-align: center;border: 1px solid black;}
        </style>
    </head>
    <body>
        <div id="div1">Div1</div>
        <div id="div2">Div2</div>
        <div id="div3">Div3</div>
        <script>
            // JQuery.css( String name )                    获取Dom对象的css样式值
            console.log( $("#div1").css("border") );
            // JQuery.css( String name , String value )        设置Dom对象的css样式值
            $("#div2").css("background-color","#ccffcc");
            // JQuery.css( Object json )                    设置Dom对象的多个css样式值
            $("#div3").css({"background-color":"#ccccff","color":"#ff8833"});
        </script>
     </body>
</html>
```

### 七：内部追加

JQuery1.append( Object JQuery2 )            内部内容后追加   Jquery2追加到Jquery1后面

JQuery.prepend( Object JQuery )             内部内容前追加

JQuery1.appendTo( Object JQuery2 )        追加到目标内部内容后     Jquery1追加到Jquery2后面

JQuery.prependTo( Object JQuery )        追加到目标内部内容前

```html
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>JQuery</title>
        <script src="js/jquery-3.4.1.js"></script>
        <style>
            #div1{width: 400px; height: 60px; line-height: 60px; text-align: center;border: 1px solid black;}
        </style>
    </head>
    <body>
        <div id="div1">Div1</div>
        <button id="bt1"> Button 1 </button>
        <button id="bt2"> Button 2 </button>
        <button id="bt3"> Button 3 </button>
        <button id="bt4"> Button 4 </button>
        <script>
            // JQuery.append( Object JQuery )        内部内容后追加
            $("#div1").append( $("#bt1") );
            // JQuery.prepend( Object JQuery )         内部内容前追加
            $("#div1").prepend( $("#bt2") );
            // JQuery.appendTo( Object JQuery )        追加到目标内部内容后
            $("#bt3").appendTo( $("#div1") );
            // JQuery.prependTo( Object JQuery )    追加到目标内部内容前
            $("#bt4").prependTo( $("#div1") );
        </script>
     </body>
</html>
```

### 八：外部追加

JQuery1.after( Object jquery2 )            外部后追加   Jquery2追加到Jquery1后面

JQuery.before( Object jquery )        外部前追加

JQuery1.insertAfter( Object jquery2 )        追加到目标外部后方   Jquery2追加到Jquery1后面

JQuery.insertBefore( Object jquery )    追加到目标外部前方

```html
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>JQuery</title>
        <script src="js/jquery-3.4.1.js"></script>
        <style>
            #div1{width: 400px; height: 60px; line-height: 60px; text-align: center;border: 1px solid black;}
        </style>
    </head>
    <body>
        <div id="div1">Div1</div>
        <button id="bt1"> Button 1 </button>
        <button id="bt2"> Button 2 </button>
        <button id="bt3"> Button 3 </button>
        <button id="bt4"> Button 4 </button>
        <script>
            // JQuery.after( Object jquery )        外部后追加
            $("#div1").after( $("#bt1") );
            // JQuery.before( Object jquery )        外部前追加
            $("#div1").before( $("#bt2") );
            // JQuery.insertAfter( Object jquery )    追加到目标外部后方
            $("#bt3").insertAfter( $("#div1") );
            // JQuery.insertBefore( Object jquery )    追加到目标外部前方
            $("#bt4").insertBefore( $("#div1") );
        </script>
     </body>
</html>
```

### 九：替换

JQuery1.replaceWith( Object jquery2 )    替换为 Jquery2替换1，Jquery1会消失

JQuery1.replaceAll( Object jquery2 )        替换目标   Jquery1替换2，Jquery2会消失

```html
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>JQuery</title>
        <script src="js/jquery-3.4.1.js"></script>
    </head>
    <body>
        <button id="bt1"> Button 1 </button>
        <button id="bt2"> Button 2 </button>
        <button id="bt3"> Button 3 </button>
        <button id="bt4"> Button 4 </button>
        <script>
            //    JQuery.replaceWith( Object jquery )    // 替换为
            $("#bt1").replaceWith( $("#bt4") );
            //    JQuery.replaceAll( Object jquery )    // 替换目标
            $("#bt3").replaceAll( $("#bt2") );
        </script>
     </body>
</html>
```

### 十：删除

JQuery.empty()    清空内部内容		//不会删除标记本身

JQuery.remove()    删除Dom对象	//清楚内部内容并且会将标记本身一并删除

```html
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>JQuery</title>
        <script src="js/jquery-3.4.1.js"></script>
    </head>
    <body>
        <button id="bt1"> Button 1 </button>
        <button id="bt2"> Button 2 </button>
        <button id="bt3"> Button 3 </button>
        <button id="bt4"> Button 4 </button>
        <script>
            //    JQuery.empty()    清空内部内容
            $("#bt1").empty();
            //    JQuery.remove()    删除Dom对象
            $("#bt2").remove();
        </script>
     </body>
</html>
```

### 十一：克隆

JQuery.clone()            浅复制，只复制表象

JQuery.clone( true )        深复制，复制表象及事件

```html
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>JQuery</title>
        <script src="js/jquery-3.4.1.js"></script>
    </head>
    <body>
        <button class="btn"> Button 1 </button>
        <button class="btn"> Button 2 </button>
        <script>
            // 按钮添加点击事件
            $(".btn").click(function(){
                alert("aaa");
            });
            //    JQuery.clone()            浅复制，只复制表象
            $(".btn:eq(0)").clone().appendTo( $(document.documentElement) );
            //    JQuery.clone( true )    深复制，复制表象及事件
            $(".btn:eq(1)").clone(true).appendTo( $(document.documentElement) ); 
        </script>
     </body>
</html>
```

### 十二：定位

JQuery.offset()                获取相对于window的位置

JQuery.offset( Object json )    设置相对于window的位置

JQuery.position()            获取相对于父级Dom对象的位置

```html
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>JQuery</title>
        <script src="js/jquery-3.4.1.js"></script>
        <style>
            #div1{width: 200px; height: 200px; margin: 20px 50px; border: 1px solid black; position: relative;}
            #div2{width: 100px; height: 100px; background-color: #ffcccc; position: absolute; left:0px; top:0px;}
            #div3{width: 100px; height: 100px; background-color: #ccccff; position: absolute; left:0px; top:100px;}
        </style>
    </head>
    <body>
        <div id="div1">
            <div id="div2"></div>
            <div id="div3"></div>
        </div>
        <script>
            // JQuery.offset()                获取相对于window的位置
            let {left:x,top:y} = $("#div2").offset();
            console.log(`offset left = ${x} , top = ${y}`);
            // JQuery.offset( Object json )    设置相对于window的位置
            $("#div2").offset({left:159,top:21});
            // JQuery.position()            获取相对于父级Dom对象的位置
            let {left:l,top:t} = $("#div3").position();
            console.log(`position left = ${l} , top = ${t}`);
        </script>
     </body>
</html>
```

### 十三：尺寸

JQuery.width()                    获取宽度

JQuery.width( Number width )        设置宽度

JQuery.height()                获取高度

JQuery.height( Number height )    设置高度

```html
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>JQuery</title>
        <script src="js/jquery-3.4.1.js"></script>
    </head>
    <body>
        <button id="bt1"> 按钮 1 </button>
        <button id="bt2"> 按钮 2 </button>
        <button id="bt3"> 按钮 3 </button>
        <button id="bt4"> 按钮 4 </button>
        <script>
            //     JQuery.width()                    获取宽度
            console.log( $("#bt1").width() );
            //     JQuery.width( Number width )    设置宽度
            $("#bt2").width(80);
            //     JQuery.height()                    获取高度
            console.log( $("#bt3").height() );
            //     JQuery.height( Number height )    设置高度
            $("#bt4").height(24);
        </script>
     </body>
</html>
```

### 十四：滚动

JQuery.scrollLeft()                获取溢出内容向左滚动距离

JQuery.scrollLeft( Number left )    设置溢出内容向左滚动距离

JQuery.scrollTop()                获取溢出内容向上滚动距离

JQuery.scrollTop( Number top )    设置溢出内容向上滚动距离

```html
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>JQuery</title>
        <script src="js/jquery-3.4.1.js"></script>
        <style>
            #div1{width: 200px; height: 100px; border: 1px solid black; overflow: hidden;}
            #div2{
                width: 1000px; height: 1000px;
                background: linear-gradient(45deg,#fff,#000);
            }
        </style>
    </head>
    <body>
        <div id="div1">
            <div id="div2"></div>
        </div>
        <script>
            // JQuery.scrollLeft()                获取溢出内容向左滚动距离
            console.log( $("#div1").scrollLeft() );
            //    JQuery.scrollTop()                获取溢出内容向上滚动距离
            console.log( $("#div1").scrollTop() );
            //    JQuery.scrollLeft( Number left )    设置溢出内容向左滚动距离
            $("#div1").scrollLeft(200);
            //    JQuery.scrollTop( Number top )    设置溢出内容向上滚动距离
            $("#div1").scrollTop(400);
        </script>
     </body>
</html>
```
