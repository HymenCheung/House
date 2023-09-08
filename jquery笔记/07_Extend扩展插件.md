## Extend扩展插件



JQuery.fn.extend( Object json ) 	用于将对象合并到JQuery原型



```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>JQuery</title>
		<script src="js/jquery-3.4.1.js"></script>
	</head>
	<body>
		账户名称：
		<input type="text" name="username"/>
		<span></span>
		<script>
			$.fn.extend({
				validRegExp:function(regExp,target,errorInfo){
					$(this).keyup(function(){
						if( regExp.test( $(this).val() ) ){
							target.html(`<font color='green'>OK</font>`);
						}else{
							target.html(`<font color='red'>${errorInfo}</font>`);
						}
					});
				}
			});
			$("input[name='username']").validRegExp(
				/^\w{6,12}$/,
				$("input[name='username']").next("span"),
				"账户名称长度必须6~12位");
		</script>
	</body>
</html>
```

