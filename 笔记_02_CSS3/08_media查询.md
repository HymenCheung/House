## media查询

### media查询

```css
@media not|only mediatype and (expressions条件表达式) {
    CSS代码;
}
```

> not 排除、olny 仅适用

> mediatype：

| 值      | 描述               |
| ------ | ---------------- |
| all    | 用于所有多媒体类型设备      |
| print  | 用于打印机            |
| screen | 用于电脑屏幕，平板，智能手机等。 |
| speech | 用于屏幕阅读器          |

### 标签式



> 案例：

```html
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>CSS3</title>
        <style>
        #div1{ width: 100px; height: 100px; border: 1px solid black;}
        @media screen and (min-width:1280px) {
            #div1{ background-color: #ddddff; }
        }
        @media screen and (min-width:1024px) and (max-width:1279px) {
            #div1{ background-color: #ddffdd; }
        }
        @media screen and (min-width:800px) and (max-width:1023px) {
            #div1{ background-color: #ffdddd; }
        }
        @media screen and (max-width:799px) {
            #div1{ background-color: #000000; }
        }
        </style>
    </head>
    <body>
        <div id="div1"></div>
    </body>
</html>
```

### 导入式

```css
<link rel="stylesheet" media="mediatype and (expressions)" href="css文件路径">
```

> 案例

```html
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>CSS3</title>
        <link href="theme/blue.css" media="screen and (min-width:1280px)" rel="stylesheet" />
        <link href="theme/green.css" media="screen and (min-width:1024px) and (max-width:1279px)" rel="stylesheet" />
        <link href="theme/red.css" media="screen and (min-width:800px) and (max-width:1023px)" rel="stylesheet" />
        <link href="theme/dark.css" media="screen and (max-width:799px)" rel="stylesheet" />
        <style>
        #div1{ width: 100px; height: 100px; border: 1px solid black;}
        </style>
    </head>
    <body>
        <div id="div1"></div>
    </body>
</html>
```
