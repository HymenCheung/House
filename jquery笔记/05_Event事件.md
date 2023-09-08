## Event 事件



### 一：Dom/Bom事件



JQuery.event( Function callback )	给JQuery中Dom容器中的所有Dom对象添加事件



```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>JQuery</title>
		<script src="js/jquery-3.4.1.js"></script>
	</head>
	<body>
		<button class="btn"> 按钮 </button>
		<button class="btn"> 按钮 </button>
		<button class="btn"> 按钮 </button>
		<script>
			//	JQuery.event( Function callback )	给JQuery中Dom容器中的所有Dom对象添加事件
			 $(".btn").click(function(){
				 console.log("按钮被点击了");
			 });
		</script>
	 </body>
</html>
```



### 二：文档加载完毕生命周期事件



```javascript
$(document).ready(function(){
    ......
})
```



### 三：组合事件



JQuery.hover( Function mouseEnter, Function mouseLeave )



```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>JQuery</title>
		<script src="js/jquery-3.4.1.js"></script>
	</head>
	<body>
		<button class="btn"> 按钮 </button>
		<button class="btn"> 按钮 </button>
		<button class="btn"> 按钮 </button>
		<script>
			$(document).ready(function(){
				$(".btn").hover(function(){
					$(this).css({"box-shadow":"5px 5px 5px #ccc"});
				},function(){
					$(this).css({"box-shadow":"0px 0px 0px #ccc"});
				});
			});
		</script>
	 </body>
</html>
```

