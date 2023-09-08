## Computed计算属性



​		Vue中的computed成员，存放的是一系列方法，必须有返回值。

​		VIew视图中，可以当做数据属性来使用。

​		computed计算属性，有缓存效果，只有当相关的数据改变时才会重新执行。



```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>Computed计算属性</title>
		<script src="js/vue-3.0.11.prod.js"></script>
	</head>
	<body>
		
		<div id="app">
			
			<ul>
				<li>{{method_fn()}}</li>
				<li>{{method_fn()}}</li>
				<li>{{method_fn()}}</li>
			</ul>
			
			<ul>
				<li>{{computed_fn}}</li>
				<li>{{computed_fn}}</li>
				<li>{{computed_fn}}</li>
			</ul>
			
		</div>
		
		<script>
		// Vue 根实例
		const app = Vue.createApp({
			data(){
				return {
					name : "张三",
					age : 20
				}
			},
			methods : {
				method_fn(){
					console.log( `method_fn()被调用了！` )
					return `大家好，我是${this.name},今年${this.age}岁！`
				}
			},
			computed : {
				computed_fn(){
					console.log( `computed_fn()被调用了！` )
					return `大家好，我是${this.name},今年${this.age}岁！`
				}
			}
		})
		// 挂载视图
		app.mount( "#app" )
		</script>
		
	</body>
</html>
```

