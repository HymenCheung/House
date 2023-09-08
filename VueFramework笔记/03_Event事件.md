## Event事件



### 一：Vue成员methods



​		Vue成员methods中，存放事件的处理方法。



### 二：View视图中注册事件



​		v-on:event="成员"（简化 @event="成员" ）



```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>Event事件</title>
		<script src="js/vue-3.0.11.prod.js"></script>
		<style>
		#big{
			width: 300px;
			height: 300px;
			border: 1px solid black;
			background-color: #fcc;
		}
		</style>
	</head>
	<body>
		
		<div id="app">
			
			<div id="big" @click="big_clicked()"></div>
			
		</div>
		
		<script>
		// 创建 Vue 根实例
		const app = Vue.createApp({
			data(){
				return {
					
				}
			},
			methods : {
				big_clicked(){
					console.log( "big 被点击了！" )
				}
			}
		})
		// Vue实例 挂载 视图
		app.mount( "#app" )
		</script>
		
	</body>
</html>
```



### 三：事件修饰符



#### 3.1 .stop修饰符



​		.stop 阻止事件传递（event.stopPropagation = true）



```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>Event事件</title>
		<script src="js/vue-3.0.11.prod.js"></script>
		<style>
		#big{
			width: 300px;
			height: 300px;
			border: 1px solid black;
			background-color: #fcc;
		}
		#mid{
			width: 200px;
			height: 200px;
			border: 1px solid black;
			background-color: #cfc;
		}
		#small{
			width: 100px;
			height: 100px;
			border: 1px solid black;
			background-color: #ccf;
		}
		</style>
	</head>
	<body>
		
		<div id="app">
			
			<div id="big" @click.stop="big_clicked()">
				<div id="mid" @click.stop="mid_clicked()">
					<div id="small" @click.stop="small_clicked()"></div>
				</div>
			</div>
			
		</div>
		
		<script>
		// 创建 Vue 根实例
		const app = Vue.createApp({
			data(){
				return {
					
				}
			},
			methods : {
				big_clicked(){
					console.log( "big 被点击了！" )
				},
				mid_clicked(){
					console.log( "mid 被点击了！" )
				},
				small_clicked(){
					console.log( "small 被点击了！" )
				}
			}
		})
		// Vue实例 挂载 视图
		app.mount( "#app" )
		</script>
		
	</body>
</html>
```



#### 3.2 .capture修饰符



​		.capture提升传递的优先级



```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>Event事件</title>
		<script src="js/vue-3.0.11.prod.js"></script>
		<style>
		#big{
			width: 300px;
			height: 300px;
			border: 1px solid black;
			background-color: #fcc;
		}
		#mid{
			width: 200px;
			height: 200px;
			border: 1px solid black;
			background-color: #cfc;
		}
		#small{
			width: 100px;
			height: 100px;
			border: 1px solid black;
			background-color: #ccf;
		}
		</style>
	</head>
	<body>
		
		<div id="app">
			
			<div id="big" @click.capture="big_clicked()">
				<div id="mid" @click="mid_clicked()">
					<div id="small" @click="small_clicked()"></div>
				</div>
			</div>
			
		</div>
		
		<script>
		// 创建 Vue 根实例
		const app = Vue.createApp({
			data(){
				return {
					
				}
			},
			methods : {
				big_clicked(){
					console.log( "big 被点击了！" )
				},
				mid_clicked(){
					console.log( "mid 被点击了！" )
				},
				small_clicked(){
					console.log( "small 被点击了！" )
				}
			}
		})
		// Vue实例 挂载 视图
		app.mount( "#app" )
		</script>
		
	</body>
</html>
```



#### 3.3 .self修饰符



​		.self事件只对当前Dom生效



```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>Event事件</title>
		<script src="js/vue-3.0.11.prod.js"></script>
		<style>
		#big{
			width: 300px;
			height: 300px;
			border: 1px solid black;
		}
		#mid{
			width: 200px;
			height: 200px;
			border: 1px solid black;
		}
		#small{
			width: 100px;
			height: 100px;
			border: 1px solid black;
		}
		.active{
			background-color: #fcc;
		}
		</style>
	</head>
	<body>
		
		<div id="app">
			<div id="big" @click.self="div_clicked()">
				<div id="mid">
					<div id="small"></div>
				</div>
			</div>
		</div>
		
		<script>
		// Vue根实例
		const app = Vue.createApp({
			data(){
				return {
					
				}
			},
			methods : {
				div_clicked(){
					console.log( event.srcElement )
					event.srcElement.className = "active"
				}
			}
		})
		app.mount( "#app" )
		</script>
		
	</body>
</html>
```



#### 3.4 .once修饰符



​		.once事件只会触发一次



```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>Event事件</title>
		<script src="js/vue-3.0.11.prod.js"></script>
	</head>
	<body>
		
		<div id="app">
			<button @click.once="button_clicked()"> 按钮 </button>
		</div>
		
		<script>
		// Vue 根实例
		const app = Vue.createApp({
			data(){
				return {
					
				}
			},
			methods : {
				button_clicked(){
					console.log( "按钮被点击了！" )
				}
			}
		})
		app.mount("#app")
		</script>
		
	</body>
</html>
```



#### 3.5 .prevent修饰符



​		.prevent阻止事件传播（event.preventDefault(true) ）



```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>Event事件</title>
		<script src="js/vue-3.0.11.prod.js"></script>
	</head>
	<body>
		
		<div id="app">
			<a href="http://www.atstudy.com" @click.prevent="link_clicked()">去学掌门官网</a>
		</div>
		
		<script>
		// Vue 根实例
		const app = Vue.createApp({
			data(){
				return {
					
				}
			},
			methods : {
				link_clicked(){
					alert("超链接被点击了！")
				}
			}
		})
		app.mount("#app")
		</script>
		
	</body>
</html>
```



#### 3.6 .passive修饰符



​		.passive告诉监听器不会调用event.preventDefault()方法，减少监听步骤提高性能



