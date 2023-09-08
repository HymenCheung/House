## Ajax

​    Ajax 既 Asynchronous Javascript And XML （异步的Javascript和XML）。

​    使用Ajax技术网页应用能够快速地将增量更新呈现在用户界面上，而不需要重载（刷新）整个页面，这使得程序能够更快地回应用户的操作。

​    Web前端应用程序，和后端服务，进行异步请求、异步交互数据。

### Javascript 原生 Ajax

```html
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Javascript</title>
    </head>
    <body>
        <script>
            // 实例化 XMLHttpRequest 对象
            let request = new XMLHttpRequest()
            // 设置请求参数
            request.open( 'GET' , 'http://127.0.0.1:8001/category/1' , true )
            // 发送请求
            request.send()
            // 请求状态改变
            request.onreadystatechange = ()=>{
                // 请求状态为4：解析响应报文完毕
                if(request.readyState == 4){
                    // 输出响应内容
                    console.log(request.responseText)
                }
            }
        </script>
    </body>
</html>
```

### 跨域请求的处理

使用Nginx（Web服务器）的反向代理

修改Nginx的配置文件：/conf/nginx.conf

```shell
...
    server {
      ...
      location / {
          root   D:\ADWF24\03_Javascript\13_Javascript_Day13\Demo\Javascript_Day13;
          index  index.html index.htm;
      }
      location /api/ {
          proxy_pass http://127.0.0.1:8001/;
      }
      ...
    }
```

Web前端网络域：127.0.0.1:80

服务器端网络域：127.0.0.1:8001

Nginx反向代理：127.0.0.1:80/api/   =>  127.0.0.1:8001

### Fetch API

```html
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Javascript</title>
    </head>
    <body>
        <script>
            // fetch 发送请求
            fetch('http://127.0.0.1:8001/category/1')
            // 解析响应报文为json格式
            .then(response=>response.json())
            // 响应报文解析完毕
            .then( data =>{
                console.log( data )
            })
        </script>
    </body>
</html>
```
