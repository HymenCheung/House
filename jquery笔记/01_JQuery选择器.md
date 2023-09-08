## JQuery选择器

### 一：Dom选择器

$( Object dom );    // 通过dom对象封装jquery对象

```html
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>JQuery</title>
        <script src="js/jquery-3.4.1.min.js"></script>
    </head>
    <body>
        <button id="bt1"> 按 钮 </button>
        <script>
            // 获取id为bt1的dom对象
            let dom = document.getElementById("bt1");
            // 根据dom对象封装jquery对象
            let jquery = $(dom);
            // 调用jquery对象的成员方法
            jquery.css({"background-color":"#ffcccc"});
        </script>
    </body>
</html>
```

### 二：CSS3选择器

$( String css3Selector )    // css3选择器表达式

> ID选择器:

```html
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>JQuery</title>
        <script src="js/jquery-3.4.1.min.js"></script>
    </head>
    <body>
        <button id="bt1"> 按 钮 </button>
        <script>
            // id选择器
            $("#bt1").css({"background-color":"#ffcccc"});
        </script>
    </body>
</html>
```

> 类别选择器：

```html
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>JQuery</title>
        <script src="js/jquery-3.4.1.min.js"></script>
    </head>
    <body>
        <button class="btn"> 按 钮 </button>
        <button class="btn"> 按 钮 </button>
        <button class="btn"> 按 钮 </button>
        <script>
            // class选择器
            $(".btn").css({"background-color":"#ffcccc"});
        </script>
    </body>
</html>
```

> 标记选择器：

```html
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>JQuery</title>
        <script src="js/jquery-3.4.1.min.js"></script>
    </head>
    <body>
        <button class="btn"> 按 钮 </button>
        <button class="btn"> 按 钮 </button>
        <button class="btn"> 按 钮 </button>
        <script>
            // 标签选择器
            $("button").css({"background-color":"#ffcccc"});
        </script>
    </body>
</html>
```

> 详细CSS3选择器请参阅CSS3部分的文档
