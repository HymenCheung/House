## Watch侦听



​		Vue实例中成员watch，存放的是一系列方法，方法名称要和侦听的data数据一致。

​		当Vue实例中的data数据改变时，就会调用对应名称的watch侦听方法。



```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>Watch侦听</title>
		<script src="js/vue-3.0.11.prod.js"></script>
	</head>
	<body>
		
		<div id="app">
			<form>
				<input type="text" v-model="username" placeholder="请输入账户名称"/>
				<span v-html="username_info"></span>
			</form>
		</div>
		
		<script>
		const app = Vue.createApp({
			data(){
				return {
					// 双向绑定 用户输入的账户名称
					username : "",
					// 账户名称 提示信息
					username_info : ""
				}
			},
			watch : {
				/**
				 * @description 侦听成员username值的改变
				 * @param {Object} newValue 改变后的值
				 * @param {Object} oldValue 改变前的值
				 * */
				username( newValue , oldValue ){
					// 判断 改变后的值 newValue 的 长度是否合法
					if( newValue.length < 6 ){
						this.username_info = "<font color='red'>账户名称最少6位！</font>"
					}else if( newValue.length > 12 ){
						this.username_info = "<font color='red'>账户名称最多12位！</font>"
					}else{
						this.username_info = "<font color='green'>合法账户名称！</font>"
					}
				}
			}
		})
		app.mount("#app")
		</script>
		
	</body>
</html>
```

