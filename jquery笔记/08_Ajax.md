## Ajax



### 一：发送Get请求

```javascript
// 发送 Get 请求
$.ajax( {
  url : "http://127.0.0.1/api/category",		// 请求的URL路径
  type : "GET",									// 请求的方式 GET POST
  data : { pid : null },						// 请求的参数
  success(response){							// 请求成功的回调函数
    console.log( "Ajax请求成功" )
    console.log( response )
  },
  error(){										// 请求失败的回调函数
    console.log( "Ajax请求失败" )
    console.log( arguments )
  }
} )
```



### 二：发送Post请求

```javascript
$.ajax({
  url : "http://127.0.0.1/api/login",								// 请求的URL路径
  type : "POST",													// 请求的方式 GET POST
  data : { username : "13900000001" , password : "abc123" },		// 请求的参数
  success(response){												// 请求成功的回调函数
    console.log( "Ajax请求成功" )
    console.log( response )
  },
  error(){														// 请求失败的回调函数
    console.log( "Ajax请求失败" )
    console.log( arguments )
  }
})
```

