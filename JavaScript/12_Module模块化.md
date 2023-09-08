### Module模块化



​	历史上，JavaScript 一直没有模块（module）体系，无法将一个大程序拆分成互相依赖的小文件，再用简单的方法拼装起来。这对开发大型的、复杂的项目形成了巨大障碍。

​	在 ES6 之前，社区制定了一些模块加载方案，最主要的有 CommonJS 和 AMD 两种。前者用于服务器，后者用于浏览器。ES6 在语言标准的层面上，实现了模块功能，而且实现得相当简单，完全可以取代现有的 CommonJS 和 AMD 规范，成为浏览器和服务器通用的模块解决方案。



### Export 导出



```javascript
// 导出 String
export let name = '张三'
// 导出 Number
export let age = 20
// 导出 Array
export let hobby = ['美食','音乐','旅游']
// 导出 Function
export let say = () => '哈哈哈'
// 导出 Class
export let spu = class Spu{
	spu_id
	spu_name
	spu_price
}
// 导出 Object
export let product = {"spu_id":1001,"spu_name":"测试商品","spu_price":99.99}
// 导出 默认值
export default 'default默认值'
```



### Import 导入



#### 导入默认值



```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>Javascript</title>
	</head>
	<body>
		<script type="module">
			import data from './js/bwf.js'
			console.log(data)
		</script>
	</body>
</html>
```



#### 解构导入



```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>Javascript</title>
	</head>
	<body>
		<script type="module">
			import {name,age} from './js/bwf.js'
			console.log(name,age)
		</script>
	</body>
</html>
```



#### 全部导入



```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>Javascript</title>
	</head>
	<body>
		<script type="module">
			import * as data from './js/bwf.js'
			console.log(data)
		</script>
	</body>
</html>
```

