## V-Model双向绑定



### 一：双向绑定



​		v-bind：单向绑定  

​		Model模型数据的改变，会重新渲染View视图界面。

​		View视图界面中的改变，不会同步更改Model模型数据。



​		v-model：双向绑定

​		Model模型数据的改变，会重新渲染View视图界面。

​		View视图界面中的改变，会同步更改Model模型数据。



```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>V-Model双向绑定</title>
		<script src="js/vue-3.0.11.prod.js"></script>
	</head>
	<body>
		
		<div id="app">
			
			<table border="1px" cellpadding="4px" cellspacing="4px" align="center">
				<tr>
					<td> <input type="text" v-bind:value="username"/> </td>
					<td> 账户名称：{{username}} </td>
				</tr>
				<tr>
					<td> <input type="text" v-model="username"/> </td>
					<td> 账户名称：{{username}} </td>
				</tr>
			</table>
			
		</div>
		
		<script>
			const app = Vue.createApp({
				data(){
					return {
						username : "zhangsan"
					}
				}
			})
			app.mount( "#app" )
		</script>
		
	</body>
</html>
```





### 二：文本框的双向绑定



​		v-model：绑定的是onkeyup事件

​		v-model.lazy：绑定的是onchange事件



```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>V-Model双向绑定</title>
		<script src="js/vue-3.0.11.prod.js"></script>
	</head>
	<body>
		
		<div id="app">
			
			<table border="1px" cellpadding="4px" cellspacing="4px" align="center">
				<tr>
					<td> <input type="text" v-model="username"/> </td>
					<td> 账户名称：{{username}} </td>
				</tr>
				<tr>
					<td> <input type="text" v-model.lazy="username"/> </td>
					<td> 账户名称：{{username}} </td>
				</tr>
			</table>
			
		</div>
		
		<script>
			const app = Vue.createApp({
				data(){
					return {
						username : "zhangsan"
					}
				}
			})
			app.mount( "#app" )
		</script>
		
	</body>
</html>
```



### 三：单选按钮的双向绑定



```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>V-Model双向绑定</title>
		<script src="js/vue-3.0.11.prod.js"></script>
	</head>
	<body>
		
		<div id="app">
			
			<table border="1px" cellpadding="4px" cellspacing="4px" align="center">
				<tr>
					<td>
						<label> <input type="radio" name="gender" value="男" v-model="gender"/> 男 </label>
						<label> <input type="radio" name="gender" value="女" v-model="gender"/> 女 </label>
						<label> <input type="radio" name="gender" value="保密" v-model="gender"/> 保密 </label>
					</td>
					<td> 用户性别：{{gender}} </td>
				</tr>
			</table>
			
		</div>
		
		<script>
			const app = Vue.createApp({
				data(){
					return {
						gender : "保密"
					}
				}
			})
			app.mount( "#app" )
		</script>
		
	</body>
</html>
```



### 四：复选框的双向绑定



```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>V-Model双向绑定</title>
		<script src="js/vue-3.0.11.prod.js"></script>
	</head>
	<body>
		
		<div id="app">
			
			<table border="1px" cellpadding="4px" cellspacing="4px" align="center">
				<tr>
					<td>
						<label> <input type="checkbox" value="美食" v-model="hobby"/> 美食 </label>
						<label> <input type="checkbox" value="音乐" v-model="hobby"/> 音乐 </label>
						<label> <input type="checkbox" value="旅游" v-model="hobby"/> 旅游 </label>
					</td>
					<td> 兴趣爱好：{{hobby}} </td>
				</tr>
			</table>
			
		</div>
		
		<script>
			const app = Vue.createApp({
				data(){
					return {
						hobby : []
					}
				}
			})
			app.mount( "#app" )
		</script>
		
	</body>
</html>
```



### 五：下拉列表的双向绑定



```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>V-Model双向绑定</title>
		<script src="js/vue-3.0.11.prod.js"></script>
	</head>
	<body>
		
		<div id="app">
			
			<table border="1px" cellpadding="4px" cellspacing="4px" align="center">
				<tr>
					<td>
						<select v-model="course">
							<option>HTML5</option>
							<option>CSS3</option>
							<option>Javascript</option>
						</select>
					</td>
					<td> 选择课程：{{course}} </td>
				</tr>
			</table>
			
		</div>
		
		<script>
			const app = Vue.createApp({
				data(){
					return {
						course : "Javascript"
					}
				}
			})
			app.mount( "#app" )
		</script>
		
	</body>
</html>
```









