## Animate动画



### 一：show/hide动画



JQuery.show( [ Number duration ] , [ Function callback ] )

JQuery.hide( [ Number duration ] , [ Function callback ] )



```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>JQuery</title>
		<script src="js/jquery-3.4.1.js"></script>
		<style>
			#div1{width:200px; height:200px;background: linear-gradient(to right,#fcc,#ccf);}
		</style>
	</head>
	<body>
		<button id="bt1"> 隐藏 </button>
		<button id="bt2"> 显示 </button>
		<hr/>
		<div id="div1"></div>
		<script>
			$(document).ready(function(){
				$("#bt1").click(function(){
					$("#div1").hide(1000,function(){
						$("#bt1").attr("disabled",true);
						$("#bt2").attr("disabled",false);
					});
				});
				$("#bt2").click(function(){
					$("#div1").show(1000,function(){
						$("#bt1").attr("disabled",false);
						$("#bt2").attr("disabled",true);
					});
				});
			});
		</script>
	 </body>
</html>
```



### 二：fadeIn/fadeOut动画



JQuery.fadeIn( [ Number duration ] , [ Function callback ] )

JQuery.fadeOut( [ Number duration ] , [ Function callback ] )



```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>JQuery</title>
		<script src="js/jquery-3.4.1.js"></script>
		<style>
			#div1{width:200px; height:200px;background: linear-gradient(to right,#fcc,#ccf);}
		</style>
	</head>
	<body>
		<button id="bt1"> 隐藏 </button>
		<button id="bt2"> 显示 </button>
		<hr/>
		<div id="div1"></div>
		<script>
			$(document).ready(function(){
				$("#bt1").click(function(){
					$("#div1").fadeOut(1000,function(){
						$("#bt1").attr("disabled",true);
						$("#bt2").attr("disabled",false);
					});
				});
				$("#bt2").click(function(){
					$("#div1").fadeIn(1000,function(){
						$("#bt1").attr("disabled",false);
						$("#bt2").attr("disabled",true);
					});
				});
			});
		</script>
	 </body>
</html>
```



### 三：slideUp/slideDown动画



JQuery.slideUp( [ Number duration ] , [ Function callback ] )

JQuery.slideDown( [ Number duration ] , [ Function callback ] )



```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>JQuery</title>
		<script src="js/jquery-3.4.1.js"></script>
		<style>
			#div1{width:200px; height:200px;background: linear-gradient(to right,#fcc,#ccf);}
		</style>
	</head>
	<body>
		<button id="bt1"> 隐藏 </button>
		<button id="bt2"> 显示 </button>
		<hr/>
		<div id="div1"></div>
		<script>
			$(document).ready(function(){
				$("#bt1").click(function(){
					$("#div1").slideUp(1000,function(){
						$("#bt1").attr("disabled",true);
						$("#bt2").attr("disabled",false);
					});
				});
				$("#bt2").click(function(){
					$("#div1").slideDown(1000,function(){
						$("#bt1").attr("disabled",false);
						$("#bt2").attr("disabled",true);
					});
				});
			});
		</script>
	 </body>
</html>
```



### 四：animate自定义动画



JQuery.animate( Object json , Number duration , [ Function callback ] )



```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>JQuery</title>
		<script src="js/jquery-3.4.1.js"></script>
		<style>
			#div1{width:200px; height:200px;background:#ccf;}
		</style>
	</head>
	<body>
		<button id="bt1"> 开始动画 </button>
		<hr/>
		<div id="div1"></div>
		<script>
			$(document).ready(function(){
				$("#bt1").click(function(){
					$("#div1").animate({"width":400,"opacity":0.5},1000,function(){
						$("#bt1").text("再来一次");
					});
				});
			});
		</script>
	 </body>
</html>
```



### 五：Queue动画队列



JQuery.animate( Object json , Object json )

--  duration  -- queue  -- complete



```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>JQuery</title>
		<script src="js/jquery-3.4.1.js"></script>
		<style>
			#div1{width:200px; height:200px;background:#ccf;}
		</style>
	</head>
	<body>
		<button id="bt1"> 开始动画 </button>
		<hr/>
		<div id="div1"></div>
		<script>
			$(document).ready(function(){
				$("#bt1").click(function(){
					$("#div1").animate({"width":400,"opacity":0.7},{duration:400,queue:true})
							  .animate({"height":400,"opacity":0.5},{duration:400,queue:true})
							  .animate({"height":200,"opacity":0.7},{duration:400,queue:true})
							  .animate({"width":200,"opacity":1},{duration:400,queue:true,complete:function(){
								  $("#bt1").text("再来一次");
							  }});
				});
			});
		</script>
	 </body>
</html>
```



### 六：delay动画延迟



JQuery.delay( Number duration )



```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>JQuery</title>
		<script src="js/jquery-3.4.1.js"></script>
		<style>
			#div1{width:200px; height:200px;background:#ccf;}
		</style>
	</head>
	<body>
		<button id="bt1"> 开始动画 </button>
		<hr/>
		<div id="div1"></div>
		<script>
			$(document).ready(function(){
				$("#bt1").click(function(){
					$("#div1").animate({"width":400,"opacity":0.7},{duration:400,queue:true})
							  .animate({"height":400,"opacity":0.5},{duration:400,queue:true})
							  .delay(400)
							  .animate({"height":200,"opacity":0.7},{duration:400,queue:true})
							  .animate({"width":200,"opacity":1},{duration:400,queue:true,complete:function(){
								  $("#bt1").text("再来一次");
							  }});
				});
			});
		</script>
	 </body>
</html>
```



### 七：stop动画暂停



JQuery.stop( Boolean clearQueue , Boolean gotoEnd )



```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>JQuery</title>
		<script src="js/jquery-3.4.1.js"></script>
		<style>
			#div1{width:200px; height:200px;background:#ccf;}
		</style>
	</head>
	<body>
		<button id="bt1"> 开始动画 </button>
		<button id="bt2"> 暂停动画 </button>
		<hr/>
		<div id="div1"></div>
		<script>
			$(document).ready(function(){
				$("#bt1").click(function(){
					$("#div1").animate({"width":400,"opacity":0.7},{duration:400,queue:true})
							  .animate({"height":400,"opacity":0.5},{duration:400,queue:true})
							  .delay(400)
							  .animate({"height":200,"opacity":0.7},{duration:400,queue:true})
							  .animate({"width":200,"opacity":1},{duration:400,queue:true,complete:function(){
								  $("#bt1").text("再来一次");
							  }});
				});
				$("#bt2").click(function(){
					$("#div1").stop(true,false);
				});
			});
		</script>
	 </body>
</html>
```

