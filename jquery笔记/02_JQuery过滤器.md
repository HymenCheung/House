## JQuery过滤器

### 一：eq过滤器

JQuery.eq(Number index)    // 从jquery对象的dom仓库中，过滤出指定索引的元素

```html
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>JQuery</title>
        <script src="js/jquery-3.4.1.min.js"></script>
    </head>
    <body>
        <ul id="lessons">
            <li id="part1">Web前端基础语法</li>
                <ul>
                    <li>HTML超文本标记语言</li>
                    <li>CSS层叠样式</li>
                    <li class="important">Javascript/ECMAScript</li>
                </ul>
            <li id="part2">Web前端基础框架</li>
            <li id="part3">服务器端开发技术</li>
            <li id="part4" class="important">Web前端企业级框架</li>
        </ul>
        <script>
            // eq过滤器
            $("#lessons>li").eq(2).css({"color":"#ff9911"});
        </script>
    </body>
</html>
```

### 二：first过滤器

JQuery.first()    // 从jquery对象的dom仓库中，过滤出第一个元素

```html
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>JQuery</title>
        <script src="js/jquery-3.4.1.min.js"></script>
    </head>
    <body>
        <ul id="lessons">
            <li id="part1">Web前端基础语法</li>
                <ul>
                    <li>HTML超文本标记语言</li>
                    <li>CSS层叠样式</li>
                    <li class="important">Javascript/ECMAScript</li>
                </ul>
            <li id="part2">Web前端基础框架</li>
            <li id="part3">服务器端开发技术</li>
            <li id="part4" class="important">Web前端企业级框架</li>
        </ul>
        <script>
            // first过滤器
            $("#lessons>li").first().css({"color":"#ff9911"});
        </script>
    </body>
</html>
```

### 三：last过滤器

JQuery.last()    // 从jquery对象的dom仓库中，过滤出最后一个元素

```html
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>JQuery</title>
        <script src="js/jquery-3.4.1.min.js"></script>
    </head>
    <body>
        <ul id="lessons">
            <li id="part1">Web前端基础语法</li>
                <ul>
                    <li>HTML超文本标记语言</li>
                    <li>CSS层叠样式</li>
                    <li class="important">Javascript/ECMAScript</li>
                </ul>
            <li id="part2">Web前端基础框架</li>
            <li id="part3">服务器端开发技术</li>
            <li id="part4" class="important">Web前端企业级框架</li>
        </ul>
        <script>
            // last过滤器
            $("#lessons>li").last().css({"color":"#ff9911"});
        </script>
    </body>
</html>
```

### 四：children过滤器

JQuery.children( String selector )    // 在jquery对象中，通过选择表达式过滤子级元素

```html
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>JQuery</title>
        <script src="js/jquery-3.4.1.min.js"></script>
    </head>
    <body>
        <ul id="lessons">
            <li id="part1">Web前端基础语法</li>
                <ul>
                    <li>HTML超文本标记语言</li>
                    <li>CSS层叠样式</li>
                    <li class="important">Javascript/ECMAScript</li>
                </ul>
            <li id="part2">Web前端基础框架</li>
            <li id="part3">服务器端开发技术</li>
            <li id="part4" class="important">Web前端企业级框架</li>
        </ul>
        <script>
            // children 过滤器
            $("#lessons").children(".important").css({"color":"#ff9911"});
        </script>
    </body>
</html>
```

### 五：find过滤器

JQuery.find( String selector )    // 在jquery对象中，通过选择表达式过滤子级元素

```html
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>JQuery</title>
        <script src="js/jquery-3.4.1.min.js"></script>
    </head>
    <body>
        <ul id="lessons">
            <li id="part1">Web前端基础语法</li>
                <ul>
                    <li>HTML超文本标记语言</li>
                    <li>CSS层叠样式</li>
                    <li class="important">Javascript/ECMAScript</li>
                </ul>
            <li id="part2">Web前端基础框架</li>
            <li id="part3">服务器端开发技术</li>
            <li id="part4" class="important">Web前端企业级框架</li>
        </ul>
        <script>
            // find 过滤器
            $("#lessons").find(".important").css({"color":"#ff9911"});
        </script>
    </body>
</html>
```

### 六：next过滤器

JQuery.next( String selector )    //  通过选择表达式过滤jquery对象的后续元素

```html
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>JQuery</title>
        <script src="js/jquery-3.4.1.min.js"></script>
    </head>
    <body>
        <ul id="lessons">
            <li id="part1">Web前端基础语法</li>
                <ul>
                    <li>HTML超文本标记语言</li>
                    <li>CSS层叠样式</li>
                    <li class="important">Javascript/ECMAScript</li>
                </ul>
            <li id="part2">Web前端基础框架</li>
            <li id="part3">服务器端开发技术</li>
            <li id="part4" class="important">Web前端企业级框架</li>
        </ul>
        <script>
            // next 过滤器
            $("#part2").next("li").css({"color":"#ff9911"});
        </script>
    </body>
</html>
```

### 七：nextAll过滤器

JQuery.nextAll( String selector )    //  通过选择表达式过滤jquery对象的全部后续元素

```html
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>JQuery</title>
        <script src="js/jquery-3.4.1.min.js"></script>
    </head>
    <body>
        <ul id="lessons">
            <li id="part1">Web前端基础语法</li>
                <ul>
                    <li>HTML超文本标记语言</li>
                    <li>CSS层叠样式</li>
                    <li class="important">Javascript/ECMAScript</li>
                </ul>
            <li id="part2">Web前端基础框架</li>
            <li id="part3">服务器端开发技术</li>
            <li id="part4" class="important">Web前端企业级框架</li>
        </ul>
        <script>
            // nextAll 过滤器
            $("#part2").nextAll("li").css({"color":"#ff9911"});
        </script>
    </body>
</html>
```

### 八：prev过滤器

JQuery.prev( String selector )    // 通过选择表达式过滤jquery对象的前续元素

```html
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>JQuery</title>
        <script src="js/jquery-3.4.1.min.js"></script>
    </head>
    <body>
        <ul id="lessons">
            <li id="part1">Web前端基础语法</li>
                <ul>
                    <li>HTML超文本标记语言</li>
                    <li>CSS层叠样式</li>
                    <li class="important">Javascript/ECMAScript</li>
                </ul>
            <li id="part2">Web前端基础框架</li>
            <li id="part3">服务器端开发技术</li>
            <li id="part4" class="important">Web前端企业级框架</li>
        </ul>
        <script>
            // prev 过滤器
            $("#part4").prev("li").css({"color":"#ff9911"});
        </script>
    </body>
</html>
```

### 九：prevAll过滤器

JQuery.prevAll( String selector )    // 通过选择表达式过滤jquery对象的全部前续元素

```html
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>JQuery</title>
        <script src="js/jquery-3.4.1.min.js"></script>
    </head>
    <body>
        <ul id="lessons">
            <li id="part1">Web前端基础语法</li>
                <ul>
                    <li>HTML超文本标记语言</li>
                    <li>CSS层叠样式</li>
                    <li class="important">Javascript/ECMAScript</li>
                </ul>
            <li id="part2">Web前端基础框架</li>
            <li id="part3">服务器端开发技术</li>
            <li id="part4" class="important">Web前端企业级框架</li>
        </ul>
        <script>
            // prevAll 过滤器
            $("#part4").prevAll("li").css({"color":"#ff9911"});
        </script>
    </body>
</html>
```

### 十：siblings过滤器

JQuery.siblings( String selector )        // 通过选择表达式过滤jquery对象的全部前续后续元素

```html
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>JQuery</title>
        <script src="js/jquery-3.4.1.min.js"></script>
    </head>
    <body>
        <ul id="lessons">
            <li id="part1">Web前端基础语法</li>
                <ul>
                    <li>HTML超文本标记语言</li>
                    <li>CSS层叠样式</li>
                    <li class="important">Javascript/ECMAScript</li>
                </ul>
            <li id="part2">Web前端基础框架</li>
            <li id="part3">服务器端开发技术</li>
            <li id="part4" class="important">Web前端企业级框架</li>
        </ul>
        <script>
            // siblings 过滤器
            $("#part3").siblings("li").css({"color":"#ff9911"});
        </script>
    </body>
</html>
```

### 十一：parent过滤器

JQuery.parent( String selector )        // 通过选择表达式过滤jquery对象的父级元素

```html
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>JQuery</title>
        <style>
            #div1{width:300px; height:300px;background-color:#ffcccc;}
            #div2{width:200px; height:200px;background-color:#ccffcc; position:relative; left:50px; top:50px;}
            #div3{width:100px; height:100px;background-color:#ccccff; position:relative; left:50px; top:50px;}
        </style>
        <script src="js/jquery-3.4.1.min.js"></script>
    </head>
    <body>
        <div id="div1">
            <div id="div2">
                <div id="div3"></div>
            </div>
        </div>
        <script>
            // parent 过滤器
            $("#div3").parent("div").css({"border":"3px solid black"});
        </script>
    </body>
</html>
```

### 十二：parents过滤器

JQuery.parents( String selector )        // 通过选择表达式过滤jquery对象的全部父级元素

```html
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>JQuery</title>
        <style>
            #div1{width:300px; height:300px;background-color:#ffcccc;}
            #div2{width:200px; height:200px;background-color:#ccffcc; position:relative; left:50px; top:50px;}
            #div3{width:100px; height:100px;background-color:#ccccff; position:relative; left:50px; top:50px;}
        </style>
        <script src="js/jquery-3.4.1.min.js"></script>
    </head>
    <body>
        <div id="div1">
            <div id="div2">
                <div id="div3"></div>
            </div>
        </div>
        <script>
            // parents 过滤器
            $("#div3").parents("div").css({"border":"3px solid black"});
        </script>
    </body>
</html>
```
