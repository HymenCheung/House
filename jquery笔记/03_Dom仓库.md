## Dom仓库

​        一个JQuery对象拥有一个Dom仓库，可以包含1个或多个Dom对象。

### 一：length 属性

​    JQuery.length    获取JQuery对象Dom仓库中Dom对象的数量

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
        <button class="btn"> 按 钮 </button>
        <button class="btn"> 按 钮 </button>
        <script>
            // 获取JQuery对象Dom容器中Dom对象的数量
            console.log( $(".btn").length );
        </script>
    </body>
</html>
```

### 二：get 方法

​        JQuery.get( Number index )    获取JQuery对象Dom仓库中指定索引的Dom对象

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
        <button class="btn"> 按 钮 </button>
        <button class="btn"> 按 钮 </button>
        <script>
            // 获取JQuery对象Dom容器中指定索引的Dom对象
            console.log( $(".btn").get(0) );
        </script>
    </body>
</html>
```

### 三：index 方法

​        JQuery.index( Object jquery )     获取dom对象在dom仓库中的索引

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
            // 获取dom对象在dom容器中的索引
            console.log( $(".btn").index( $("#bt3") ) );
        </script>
    </body>
</html>
```

### 四：each 方法

​        JQuery.each( Function callback )    迭代遍历JQuery对象Dom仓库中的每一个Dom对象

这个index是遍历到的dom对象的索引，而dom就是遍历到的dom对象

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
            // 迭代遍历JQuery对象Dom容器中的每一个Dom对象
            $(".btn").each(function(index,dom){
                $(dom).text(`Button${index+1}`);
            });
        </script>
    </body>
</html>
```
