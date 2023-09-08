## Vue 实例



### 一：Vue根实例



​		Vue.createApp( Object json )



### 二：Vue实例挂载视图



​		Vue.mount( String selector )



```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>Vue实例</title>
		
		<!--  导入 Vue 框架文件  -->
		<script src="js/vue-3.0.11.prod.js"></script>
		
	</head>
	<body>
		
		<!--  【View视图】  -->
		<div id="app">
			<div> 商品编号：{{prod_id}} </div>
			<div> 商品名称：{{prod_name}} </div>
			<div> 商品单价：￥{{prod_price}}元 </div>
			<div> 商品库存：{{prod_stock}} </div>
		</div>
		
		<script>
		
		// 一：创建 Vue 根实例
		const app = Vue.createApp( {
			// 【Model模型数据】
			data(){
				return {
					prod_id : 1001,
					prod_name : "超全栈开发课程",
					prod_price : 22800.00,
					prod_stock : 48
				}
			}
		} )
		
		// 二：Vue实例 挂载 视图
		app.mount( "#app" )		// 【ViewModel】视图模型挂载
		
		</script>
		
	</body>
</html>
```



### 三：MVVM开发模式



​		M：Model（模型数据）

​		V：View（视图）

​		VM：ViewModel（视图模型挂载）



> VueFramework 虽然不是标准的MVVM模式，但是在框架的设计上受到了MVVM模式的启发。