## SpringMVC

Spring MVC是Spring框架的一个模块，它是基于Java的Web应用程序开发框架。Spring MVC提供了一种基于模型-视图-控制器（Model-View-Controller，MVC）设计模式的方式来构建Web应用程序

### 基本使用

1.创建项目

2.引入依赖

```xml
<dependencies>
    <!--springmvc-->
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-webmvc</artifactId>
        <version>5.3.18</version>
    </dependency>

    <!--servlet-->
    <dependency>
        <groupId>javax.servlet</groupId>
        <artifactId>javax.servlet-api</artifactId>
        <version>4.0.1</version>
    </dependency>

    <!--jsp-->
    <dependency>
        <groupId>javax.servlet.jsp</groupId>
        <artifactId>jsp-api</artifactId>
        <version>2.1</version>
    </dependency>

    <!--jackson 帮助进行json转换-->
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-databind</artifactId>
        <version>2.13.4.2</version>
    </dependency>
</dependencies>
```



3.编写配置文件

web.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         id="WebApp_ID"
         version="4.0">

    <!-- 配置DispatcherServlet -->
    <!--通过这个dispatcher名字 找到一个 DispatcherServlet对象 这个DispatcherServlet做了很多处理-->
    <servlet>
        <servlet-name>dispatcher</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <!--
            下面是DispatcherServlet的初始化参数
            contextConfigLocation: 设置springmvc的配置文件的路径
        -->
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:spring-mvc.xml</param-value>
        </init-param>
        <!--指定项目启动时就初始化DispatcherServlet-->
        <load-on-startup>1</load-on-startup>
    </servlet>

    <!--下面这个配置表示 所有的请求(除了jsp)都会去找这个名字叫做dispatcher的Servlet-->
    <servlet-mapping>
        <servlet-name>dispatcher</servlet-name>
        <!--
            /       表示当前servlet映射除了jsp之外的所有请求(包含了静态资源)
            /*      表示当前servlet映射所有的请求(包含了静态资源和jsp)，一般不会使用这个配置DispatcherServlet
            *.do    表示映射到所有以.do结尾的请求路径(老项目会出现)
        -->
        <url-pattern>/</url-pattern>
    </servlet-mapping>



    <!--SpringMVC提供了一个乱码处理的过滤器-->
    <filter>
        <filter-name>encodingFilter</filter-name>
        <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
        <init-param>
            <!--通过初始化参数将字符编码设置为UTF-8-->
            <param-name>encoding</param-name>
            <param-value>UTF-8</param-value>
        </init-param>
    </filter>

    <filter-mapping>
        <filter-name>encodingFilter</filter-name>
        <!--所有的请求都设置为UTF-8-->
        <url-pattern>/*</url-pattern>
    </filter-mapping>


</web-app>

```



spring-mvc.xml

这个spring-mvc.xml是放在资源根路径下

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd
        http://www.springframework.org/schema/mvc http://www.springframework.org/schema/mvc/spring-mvc.xsd">

    <!-- 扫描包路径，自动注册注解的Bean -->
    <context:component-scan base-package="com.atstudy.controller" />

    <!-- 启用Spring MVC注解驱动 -->
    <mvc:annotation-driven />
    
    <!--解决静态资源的访问问题-->
    <mvc:default-servlet-handler/>
    
    <!--解决响应乱码-->
    <mvc:annotation-driven>
        <mvc:message-converters>
            <bean class="org.springframework.http.converter.StringHttpMessageConverter">
                <constructor-arg value="utf-8"/>
            </bean>
        </mvc:message-converters>
    </mvc:annotation-driven>

    <!-- 配置视图解析器 -->
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="prefix" value="/WEB-INF/views/" />
        <property name="suffix" value=".jsp" />
    </bean>

    <!-- 其他配置项 -->

</beans>

```



在被扫描的包路径下创建controller

```java
@Controller
public class TestController {

	@RequestMapping("/test")
	public void test(){
		System.out.println("hello world");
	}
}
```

访问localhost:8080/替换成自己的项目根路径/test



### 请求映射规则@RequestMapping

这个注解可以添加到方法上或者是类上

可以使用这个注解来设置它能够匹配的请求，只有符合要求的请求，才可以被加了这个注解的方法或者类处理



#### 指定请求的路径

例如

```java
@Controller
@RequestMapping("/api/v1")
public class TestController {
    
    @RequestMapping("/test")
    public void test(){
        System.out.println("hello world");
    }
}

```

这个代码的意思是，只有访问/api/v1/test才可以被这个test方法处理到，当前请求路径不要忘记IP地址、端口号、项目根路径



#### 指定请求的方式

method可以指定可处理的请求方式

例如：

​	让这个请求的资源路径为/api/v1/test 的POST请求可以被test方法处理

```java
    /**
     * 指定必须是post请求才可以访问到
     */
    @RequestMapping(value = "/test",method = RequestMethod.POST)
    public void test(){
        System.out.println("hello world");
    }
```



**指定请求方式可以简写**

```JAVA
@GetMapping 	相等于 @RequestMapping(method = RequestMethod.GET)
@PostMapping	相等于 @RequestMapping(method = RequestMethod.POST)
@PutMapping  	相等于 @RequestMapping(method = RequestMethod.PUT)
... 
```

上面这个案例的简写方式

```java
@PostMapping("/test")
public void test(){
    System.out.println("hello world");
}
```



#### 指定请求参数

我们还可以指定这个请求需要携带哪些参数

例如： 要求 /api/user/v1/login **必须携带**一个username 的参数

```java
@Controller
@RequestMapping("/api/user/v1")
public class UserController {

    @PostMapping(value = "/login",params = "username")
    public void login(){
        System.out.println("用户登录");
    }
}
```



还可以要求此次请求不能够携带哪个参数

例如： 要求 /api/user/v1/login **不能携带**一个username 的参数

```java
@Controller
@RequestMapping("/api/user/v1")
public class UserController {

    @PostMapping(value = "/login",params = "!username")
    public void login(){
        System.out.println("用户登录");
    }
}
```



还可以指定必须携带哪个参数并且必须是哪个值

例如： 要求 /api/user/v1/login **必须携带**一个username 的参数，**要求它的值必须是admin**

```java
@Controller
@RequestMapping("/api/user/v1")
public class UserController {

    @PostMapping(value = "/login",params = "username=admin")
    public void login(){
        System.out.println("用户登录");
    }
}
```

还可以指定必须携带哪个参数并且不能是哪个值

例如： 要求 /api/user/v1/login **必须携带**一个username 的参数，**要求它的值不能是admin**

```java
@Controller
@RequestMapping("/api/user/v1")
public class UserController {

    @PostMapping(value = "/login",params = "username!=admin")
    public void login(){
        System.out.println("用户登录");
    }
}
```



#### 指定请求头

还可以在@RequestMapping注解中对请求头做一些限制



例如：必须在请求头中携带token

```java
@PostMapping(value = "/cart",headers = "token")
public void cart(){
    System.out.println("正在浏览购物车");
}
```



还可以指定这个请求头不能携带上面东西

```java
// 这里是要求请求头中不能带有token
@PostMapping(value = "/cart",headers = "!token")
public void cart(){
    System.out.println("正在浏览购物车");
}
```

结合上面的指定请求参数，可以自由组合



#### 指定请求头中的Content-Type的值

consumes

```java
@Controller
@RequestMapping("/api/v1")
public class TestController {

	/**
	 * consumes = "multipart/from-data" 作用是指定请求头中的Content-Type的值必须为multipart/from-data
	 */
	@RequestMapping(method = RequestMethod.POST,value = "/test",consumes = "multipart/form-data")
	public void test(){
		System.out.println("处理请求");
	}
}
```

在webapp目录下新建一个index.html

```html
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>Title</title>
</head>
<body>

<form action="/mvc-03/api/v1/test" method="post" enctype="multipart/form-data">
	<input type="file">
	<button>提交</button>
</form>

</body>
</html>
```

启动项目之后，访问index.html，选择文件发送请求就可以成功的被test方法处理



### 获取请求参数



#### 获取到路径参数

##### ResultFul

在resultful风格中，如果这个参数比较简单，可以直接写在url路径上

例如: /api/user/v1/1 GET		这个最后面的1就是我们传递过去的参数,因为是get请求，所以是获取到id为1的用户信息

例如: /api/user/v1/1 DELETE 因为是DELETE请求，所以是要删除id为1的用户数据

如果不适用resultful风格，这两个请求那么就是: /api/user/v1/findById?id=1			/api/user/v1/deleteById?id=1

​	

如果是比较复杂的参数，一般是写在请求体中，并且是用json进行传递



案例：

现在要求定义一个ResultFul风格的接口，这个接口可以用来查询用户数据，要求路径为/api/user/v1,并且要求是GET请求，参数要求写在url上面

```java
@Controller
public class UserController {
	
	@RequestMapping("/api/user/v1/{id}")
	public void findById(@PathVariable("id") Integer id){
		System.out.println(id);
	}
}

```

**请求的url**：(http://localhost:8080/mvc-03/api/user/v1/1)



如果在url中传递了多个参数

```java
@RequestMapping("/api/user/v1/{id}/{name}")
public void findUser(@PathVariable("id") Integer id,@PathVariable("name") String name){
	System.out.println(id);
	System.out.println(name);
}
```

**请求得URL**:(http://localhost:8080/mvc-03/api/user/v1/1/张三)



##### QueryString

如果不上ResultFul风格，那么可以使用@RequestParam注解获取到参数



现在要求从请求参数中获取到id贺name

```java
@Controller
@RequestMapping("/api/v1")
public class TestController {
	
	@GetMapping(value = "/queryString")
	public void testQueryString(@RequestParam("id") Integer id, @RequestParam("name") String name){
		System.out.println("queryString");
		System.out.println(id);
		System.out.println(name);
	}
}
```

**请求得URL**:(http://localhost:8080/mvc-03/api/v1/queryString?id=1&name=张三)



**如果url中得参数名和方法中参数名相同，那么可以不用写@RequestParam注解**

```java
@Controller
@RequestMapping("/api/v1")
public class TestController {

	@GetMapping(value = "/queryString")
	public void testQueryString(Integer id, String name){
		System.out.println("queryString");
		System.out.println(id);
		System.out.println(name);
	}
}
```

如果方法得参数名贺url中得name对不上，就需要使用@RequestParam指定获取哪个参数







#### 获取到请求体中的参数

一些比较复杂的参数会转成**json**放在请求体中传递过来，可以通过@RequestBody获取到这些参数

##### 使用对象接收

例如：在请求体中传了一个用户数据，有id、name、address

下面是一个标准json

```json
{"id":"1001","name":"张三","address":"上海"}
```

这是controller

```java
@PostMapping("/api/user/v1")
public void insertUser(@RequestBody User user){
	System.out.println(user);
}
```

可以将这些参数封装到一个对象中，直接使用对象获取，这个对象的属性名和这个传递过来的json一定要完全对应

```java
public class User {
	private Integer id;
	private String name;
	private String address;

	// 自行生成get、set、toString方法
}
```



使用postman发送post请求，将上面的json对象放到请求体中



##### 使用Map集合接收数据

传递过来的json还是上面这个json，但是现在没有这个User对象了

```java
{"id":"1001","name":"张三","address":"上海"}
```



直接使用Map接收

```java
@PostMapping("/api/user/v1")
public void insertUser(@RequestBody Map map){
	Set set = map.entrySet();
	System.out.println(set);
}
```

##### 传递了一个json数组

```json
[
    {"id":"1001","name":"张三","address":"上海"},
    {"id":"1002","name":"李四","address":"北京"},
    {"id":"1004","name":"王五","address":"上海"}
]
```

在方法中使用List接收

```java
@PostMapping("/api/user/v1")
public void insertUser(@RequestBody List<User> userList){
	for (User user : userList) {
		System.out.println(user);
	}
}
```

##### @RequestBody注意事项

如果使用@RequestBody从请求体中接收数据，要求请求头中Content-Type 必须是 application/json



##### 传过来的不是json格式

可以直接使用@RequestParam接收

如果参数名和传过来的name一一对应，可以省略@RequestParam

还可以将这些name封装到对象中接收

```java
@RequestMapping("/test")
public void testData(User user){
	System.out.println(user);
}
```

##### @RequestParam其他属性

**required** 表示这个值是否是必须要有的，默认是true

```java
/**
 * required 表示这个值是否是必须的，默认是true，可以改成false
 * @param username
 * @param password
 */
@PostMapping("/login")
public void login(@RequestParam(value = "username",required = false) String username, @RequestParam(value = "password") String password){
	System.out.println("用户登录");
	System.out.println(username);
	System.out.println(password);
}
```

**defauleValue** 可以给这个参数设置默认值

```java
/**
 * defaultValue 表示这个值的默认值
 * @param username
 * @param password
 */
@PostMapping("/login")
public void login(@RequestParam(value = "username") String username, @RequestParam(value = "password",defaultValue = "admin") String password){
	System.out.println("用户登录");
	System.out.println(username);
	System.out.println(password);
}
```





#### 从请求头获取数据

使用@RequestHeader可以从请求头中获取数据

```java
@GetMapping("/header")
public void getHeader(@RequestHeader("User-Agent") String userAgent){

	System.out.println(userAgent);
}
```

通过HttpServletRequest也可以从请求头获取到数据





#### 从cookie获取数据

SpringMVC还提供了一个@CookieValue注解获取到指定的cookie的值

```java
@GetMapping("/getCookie")
public void getCookie(@CookieValue("Idea-ac70ea51") String jsessionid){
	System.out.println("获取到jsessionid的值");
}
```

案例没有效果，可以通过原生对象获取指定cookie的值





#### 类型转换

如果用户传过来的数据类型和方法的参数类型对不上，怎么处理？

SpringMVC提供了很多内置的类型转换器，例如字符串转时间日期、波尔

```java
@GetMapping("/test1")
public void test1(@RequestParam("isEnable") boolean isEnable){
	System.out.println(isEnable);
}
```

在apifox中的param传递isEnable，可以将字符串转成波尔类型

```java
@GetMapping("/test2")
// 要求传过来的时间满足这个yyyy-MM-dd格式
public void test2(@DateTimeFormat(pattern = "yyyy-MM-dd") Date date){
	System.out.println(date);
}
```



**自定义类型转换器**

如果SpringMVC内置的类型转换器不够用，可以自定义类型转换器

1.创建一个类，实现Converter<S,T> 接口，并实现里面的convert方法

```java
/**
  自定义的时间日期转换类，将字符串转成LocalDate类型
 	String 是接收到的类型
 	LocalDate是目标类型
 */
public class MyConverter implements Converter<String, LocalDate> {
	@Override
	public LocalDate convert(String source) {
        // 在这里编写自己的转换逻辑
		LocalDate parse = LocalDate.parse(source);
		return parse;
	}
}
```

2.在SpringMVC的配置文件中进行配置，让它使用自定义的转换器

```xml
<!--配置自定义的类型转换器-->
<mvc:annotation-driven conversion-service="conversionService" />

<bean id="conversionService" class="org.springframework.format.support.FormattingConversionServiceFactoryBean">
    <property name="converters">
        <set>
            <bean class="com.atstudy.config.MyConverter" /> <!-- 注册自定义的类型转换器 -->
        </set>
    </property>
</bean>
```



### 将数据响应到响应体中

SpringMVC提供了一个注解可以将数据返回到响应体中@ResponseBody



#### 返回一个对象

```java
@Controller
@RequestMapping("/api/v1/user/")
public class UserController {
	
	@ResponseBody
	@GetMapping("/findById")
	public User findById(Integer id){
		System.out.println("查找用户");
		User user = new User();
		user.setId(1);
		user.setName("张三");
		user.setAddress("上海");
		return user;
	}
}
```



**注意：这里返回的是一个对象，但是SpringMVC会将它转成JSON，但是前提是引入饿了jsonson依赖并开启了mvc的注解驱动**

```xml
<!--jackson 帮助进行json转换-->
<dependency>
  <groupId>com.fasterxml.jackson.core</groupId>
  <artifactId>jackson-databind</artifactId>
  <version>2.13.4.2</version>
</dependency>
```

```xml
<!-- 启用Spring MVC注解驱动 -->
<mvc:annotation-driven/>
```



#### 返回一个List

```java
@ResponseBody		// 表示这个方法的数据返回到响应体中
@GetMapping("/findAll")
public List<User> test1(){
	User user1 = new User(1001,"张三","上海");
	User user2 = new User(1002,"李四","北京");
	User user3 = new User(1003,"王五","上海");

	ArrayList<User> users = new ArrayList<>();
	users.add(user1);
	users.add(user2);
	users.add(user3);
	return users;
}
```



#### 返回一个Map

```java
@ResponseBody
@GetMapping("/findMap")
public Map test2(){
	HashMap<String, Object> map = new HashMap<>();
	map.put("username","张三");
	map.put("age",18);
	map.put("address","上海");
	return map;
}
```



#### 返回普通类型

```java
@ResponseBody
@GetMapping("/getNum")
public int test3(){
	return 123456;
}

@ResponseBody
@GetMapping("/getString")
public String test4(){
	return "张三";
}

@ResponseBody
@GetMapping("/getBoolean")
public boolean test5(){
	return true;
}
```



#### @RestController	

@Controller和@ResponseBody的结合

如果一个Controller中所有方法的数据全部需要返回到响应体中，可以在类头顶上加@RestController注解

```java
@RestController		// 现在这个TestController中所有方法的返回值全部放到了响应体中
public class TestController {}
```



### 页面跳转

在SpringMVC中可以进行页面跳转,例如

```java
@GetMapping("/toIndex")
public String toIndex(){
	
	return "/index.html";
}

// 上面这个方法没有加@ResponseBody，意味着不会将返回的数据放到请求体中
// /index.html 表示跳转到webapp目录下面的index.html
```



这个跳转其实是转发，如果想要重定向到某个页面

```java

@GetMapping("/toIndex")
public String toIndex(){

	return "redirect:/index.html";
}
```



### 视图解析器

spring-mvc.xml

```xml
<!-- 配置视图解析器 -->
<bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
    <property name="prefix" value="/WEB-INF/views/"/>
    <property name="suffix" value=".jsp"/>
</bean>
```

这个配置的意思是：返回一个index，那么会在这个index前面拼接上/WEB-INF/views/ 在后面拼接上.jsp

最终会去找到wenapp目录下的WEN-INF/views目录下的index.jsp



```java
@GetMapping("/toIndex")
public String toIndex(){
	return "index";
//		经过视图解析器拼接相当于: "/WEB-INF/views/index.jsp";
}
```



**如果不想走视图解析器怎么办**

手动声明转发或者重定向

```java
@GetMapping("/toImg")
public String toImg(){
	return "aaa.img";
}

// 因为配置了视图解析器，所以会拼接前缀和后缀，找到的应该是webapp目录下的 WEB-INF/views/aaa.img.jsp
```

但是不想走视图解析器,指向访问到这张图片怎么办？声明转发

```java
@GetMapping("/toImg")
public String toImg(){
	return "forward:/aaa.img";
}
```



### 获取原生对象

直接在方法参数中加上就行了，例如HttpServletRequest

```java
@GetMapping("/getRequest")
public void getHttpServletRequest(HttpServletRequest request, HttpServletResponse response, HttpSession session){
	System.out.println(request);
	System.out.println(response);
	System.out.println(session);
}
```







### 前后端不分离开发



#### Model

可以在Model中存放数据，配置视图解析器机械能跳转，并在jsp页面中获取到数据

```java
@Controller
public class TestController {
	
	@GetMapping("/test")
    // Model可以往reuqest域中存放数据
	public String test(Model model){
		model.addAttribute("name","张三");
		return "index";
	}
}
```



在index.jsp页面中获取到数据

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Index</title>
</head>
<body>
    <h1> ${name} </h1>
</body>
</html>
```



#### ModelAndView

##### 往request域存放数据

使用ModelAndView可以往域中存放数据和进行页面跳转

```java
@GetMapping("/toHello")
public ModelAndView toHello(ModelAndView modelAndView){

	// 往请求与存放数据
	modelAndView.addObject("name","张三");
	// 指定跳转的页面
	modelAndView.setViewName("index");
	return modelAndView;
}
```



##### 从request域获取数据

使用@RequestAtrribute注解可以获取到请求域的数据

```java
@GetMapping("/toRequest")
public String toSession(Model model){

	model.addAttribute("name","李四");
	model.addAttribute("age",18);
	model.addAttribute("address","上海");
	return "forward:showRequest";
}

@ResponseBody
@GetMapping("/showRequest")
public String showRequest(@RequestAttribute("name") String name){
	return name;
}
```





##### 往Session域存放数据

可以使用@SessionAttributes将指定的数据放到session域中

```java
@GetMapping("/toSession")
public String toSession(Model model){

    // 将这个参数放到请求域中
	model.addAttribute("name","李四");
	model.addAttribute("age",18);
	model.addAttribute("address","上海");
	return "session";
}
```

```java
@Controller
// 表示将address存放到session中
@SessionAttributes({"address"})
public class TestController {}
```





##### 从Session域中取出数据

可有借助@SessionAttribute 注解从session域获取到指定的数据

```java
@GetMapping("/toSession")
public String toSession(Model model){

	model.addAttribute("name","李四");
	model.addAttribute("age",18);
	model.addAttribute("address","上海");
	return "forward:showSession";
}

@ResponseBody
@GetMapping("/showSession")
public String showSession(@SessionAttribute("address") String address){
	return address;
}
```





## SSM整合

#### 1.导入依赖

```xml
  <properties>
    <maven.compiler.source>11</maven.compiler.source>
    <maven.compiler.target>11</maven.compiler.target>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
  </properties>

  <dependencies>
    <!--spring及springmvc核心依赖-->
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-webmvc</artifactId>
      <version>5.3.20</version>
    </dependency>

    <!--aop-->
    <dependency>
      <groupId>org.aspectj</groupId>
      <artifactId>aspectjweaver</artifactId>
      <version>1.9.7</version>
    </dependency>

    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-jdbc</artifactId>
      <version>5.3.20</version>
    </dependency>

    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-test</artifactId>
      <version>5.3.20</version>
    </dependency>

    <!--myabtis相关-->
    <dependency>
      <groupId>org.mybatis</groupId>
      <artifactId>mybatis</artifactId>
      <version>3.5.6</version>
    </dependency>

    <dependency>
      <groupId>org.mybatis</groupId>
      <artifactId>mybatis-spring</artifactId>
      <version>2.0.7</version>
    </dependency>

    <dependency>
      <groupId>mysql</groupId>
      <artifactId>mysql-connector-java</artifactId>
      <version>8.0.29</version>
    </dependency>

    <dependency>
      <groupId>com.alibaba</groupId>
      <artifactId>druid</artifactId>
      <version>1.2.8</version>
    </dependency>

    <dependency>
      <groupId>ch.qos.logback</groupId>
      <artifactId>logback-classic</artifactId>
      <version>1.4.4</version>
    </dependency>

    <dependency>
      <groupId>org.projectlombok</groupId>
      <artifactId>lombok</artifactId>
      <version>1.18.24</version>
    </dependency>

    <dependency>
      <groupId>com.github.pagehelper</groupId>
      <artifactId>pagehelper</artifactId>
      <version>5.3.2</version>
    </dependency>

    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>4.13.2</version>
      <scope>test</scope>
    </dependency>

    <dependency>
      <groupId>javax.servlet</groupId>
      <artifactId>javax.servlet-api</artifactId>
      <version>4.0.1</version>
    </dependency>

    <dependency>
      <groupId>javax.servlet.jsp</groupId>
      <artifactId>javax.servlet.jsp-api</artifactId>
      <version>2.3.3</version>
    </dependency>

    <dependency>
      <groupId>com.fasterxml.jackson.core</groupId>
      <artifactId>jackson-databind</artifactId>
      <version>2.13.4.2</version>
    </dependency>

    <dependency>
      <groupId>commons-fileupload</groupId>
      <artifactId>commons-fileupload</artifactId>
      <version>1.4</version>
    </dependency>
    

  </dependencies>
```



#### 2.编写配置文件

##### jdbc.properties

```properties
jdbc.driver=com.mysql.cj.jdbc.Driver
jdbc.url=jdbc:mysql://127.0.0.1:3306/atstudy_mall?allowMultiQueries=true
jdbc.username=root
jdbc.password=
```

##### mybatis-config.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "https://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>

    <!--这里可以开启驼峰映射和日志-->
    <settings>
        <setting name="mapUnderscoreToCamelCase" value="true"/>
        <setting name="logImpl" value="STDOUT_LOGGING"/>
    </settings>
</configuration>
```

##### spring-config.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns:tx="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
    https://www.springframework.org/schema/beans/spring-beans.xsd
    http://www.springframework.org/schema/context
    http://www.springframework.org/schema/context/spring-context.xsd
     http://www.springframework.org/schema/aop
      https://www.springframework.org/schema/aop/spring-aop.xsd">

    <context:component-scan base-package="com.atstudy">
        <!--因为再SpringMVC的配置文件中会扫描controller包，所以这个不用扫描，否则会报错-->
        <context:exclude-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
    </context:component-scan>

    <!--读取properties配置文件-->
    <context:property-placeholder location="classpath:jdbc.properties"/>

    <bean class="com.alibaba.druid.pool.DruidDataSource" id="dataSource">
        <property name="url" value="${jdbc.url}"/>
        <property name="username" value="${jdbc.username}"/>
        <property name="password" value="${jdbc.password}"/>
        <property name="driverClassName" value="${jdbc.driver}"/>
    </bean>


    <bean id="sqlSession" class="org.mybatis.spring.SqlSessionFactoryBean">
        <!--指定连接池-->
        <property name="dataSource" ref="dataSource"/>
        <!--整合mybatis配置文件-->
        <property name="configLocation" value="classpath:mybatis-config.xml"/>
    </bean>

    <!--扫描mapper.xml文件-->
    <bean id="mapperScannerConfig" class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <property name="basePackage" value="com.atstudy.mapper"/>
    </bean>

    <!--开启aop-->
    <aop:aspectj-autoproxy/>

</beans>
```

spring-mvc.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:tx="http://www.springframework.org/schema/context"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
    https://www.springframework.org/schema/beans/spring-beans.xsd
    http://www.springframework.org/schema/context
    http://www.springframework.org/schema/context/spring-context.xsd
       http://www.springframework.org/schema/mvc
        https://www.springframework.org/schema/mvc/spring-mvc.xsd">


    <context:component-scan base-package="com.atstudy.controller"/>
    <!-- 解决静态资源访问问题，如果不加会无法访问-->
    <mvc:default-servlet-handler/>
    <!--解决响应乱码-->
    <mvc:annotation-driven>
        <mvc:message-converters>
            <bean class="org.springframework.http.converter.StringHttpMessageConverter">
                <constructor-arg value="utf-8"/>
            </bean>
        </mvc:message-converters>
    </mvc:annotation-driven>
    


</beans>
```

##### web.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee
                      http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0"
         metadata-complete="false">

  <servlet>
    <servlet-name>DispatcherServlet</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
    <init-param>
      <!--这里使用配置spingmvc的处理映射器的-->
      <param-name>contextConfigLocation</param-name>
      <param-value>classpath:spring-mvc.xml</param-value>
    </init-param>

    <load-on-startup>1</load-on-startup>
  </servlet>

  <servlet-mapping>
    <servlet-name>DispatcherServlet</servlet-name>
    <!--
        *.do 表示以.do结尾的请求会走这个映射器
        / 所有请求全部交给DispatcherServlet解决，jsp不会被解析
        /* 不要这么写
    -->
    <url-pattern>/</url-pattern>
  </servlet-mapping>


  <!-- 处理post请求乱码 -->
  <filter>
    <filter-name>CharacterEncodingFilter</filter-name>
    <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
    <init-param>
      <param-name>encoding</param-name>
      <param-value>utf-8</param-value>
    </init-param>
  </filter>
  <filter-mapping>
    <filter-name>CharacterEncodingFilter</filter-name>
    <!-- 所有请求都经过这个过滤器将编码变为utf-8-->
    <url-pattern>/*</url-pattern>
  </filter-mapping>

  <!--在项目启动的时候同时也创建spring容器-->
  <context-param>
    <param-name>contextConfigLocation</param-name>
    <param-value>classpath:spring-config.xml</param-value>
  </context-param>
  <listener>
    <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
  </listener>

</web-app>
```



#### 编写controller测试

```java
@RestController
public class TestController {
	
	@GetMapping("/test")
	public String test(){
		return "test";
	}
}
```



#### 将mybatis替换成mybatisPlus

##### 1.替换依赖

将mybatis的依赖替换成下面这两个

```xml
<!--mybatis依赖-->
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus</artifactId>
    <version>3.5.1</version>
</dependency>


<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-extension</artifactId>
    <version>3.5.1</version>
</dependency>
```

##### 2.替换bean

将sqlSessionFactory替换成baomidu提供的MybatisSqlSessionFactoryBean

替换之前

```xml
<bean id="sqlSession" class="org.mybatis.spring.SqlSessionFactoryBean">
    <!--指定连接池-->
    <property name="dataSource" ref="dataSource"/>
    <!--整合mybatis配置文件-->
    <property name="configLocation" value="classpath:mybatis-config.xml"/>
</bean>
```

替换之后

```xml
    <!--托管SqlSessionFactory到容器中-->
    <bean id="sqlSession" class="com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean">
        <!--指定数据源-->
        <property name="dataSource" ref="dataSource"/>
        <!--整合mybatis的配置文件-->
        <property name="configLocation" value="classpath:mybatis-config.xml"/>
    </bean>
```



### 整合Thymelaf

#### 1.引入依赖

再之前的ssm的基础上引入依赖

```xml
<dependency>
  <groupId>org.thymeleaf</groupId>
  <artifactId>thymeleaf</artifactId>
  <version>3.0.15.RELEASE</version>
</dependency>
<dependency>
  <groupId>org.thymeleaf</groupId>
  <artifactId>thymeleaf-spring5</artifactId>
  <version>3.0.15.RELEASE</version>
</dependency>
```

#### 2.编写配置文件

spring-mvc.xml		在ssm的基础上新增配置

```xml
<!-- 配置Thymeleaf视图解析器 -->
<bean id="viewResolver" class="org.thymeleaf.spring5.view.ThymeleafViewResolver">
    <property name="order" value="1"/>
    <property name="characterEncoding" value="UTF-8"/>
    <property name="templateEngine">
        <bean class="org.thymeleaf.spring5.SpringTemplateEngine">
            <property name="templateResolver">
                <bean class="org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver">
                    <!-- 视图前缀 -->
                    <property name="prefix" value="/templates/"/>
                    <!-- 视图后缀 -->
                    <property name="suffix" value=".html"/>
                    <property name="templateMode" value="HTML5"/>
                    <property name="characterEncoding" value="UTF-8" />
                </bean>
            </property>
        </bean>
    </property>
</bean>
```

#### 案例

创建实体类

```java
/**
 * 品牌的实体模型对象
 */
@TableName("brand")
@Data
public class Brand {

	private String brandId;
	private String brandName;
	private String brandIntroduction;
	private String brandLogourl;
	private String sortno;
	private LocalDateTime createtime;
	private LocalDateTime updatetime;

}
```

编写持久层接口

```java
@Mapper
public interface BrandMapper extends BaseMapper<Brand> {
}
```

编写Controller

```java
@Controller
public class BrandController {

	@Autowired
	private BrandService brandService;

	@GetMapping("/brand")
	public String index(Model model){
		List<Brand> brands = brandService.selectAll();
		// 将查询到的数据放到请求域中
		model.addAttribute("brandList",brands);
		return "brand/index";
	}

}
```

自行补全service层接口和持久层接口



### Thymeleaf语法

想要在html页面中使用thymeleaf，需要在头部引入命名空间

```html
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">
```

引入这行代码之后就可以在html页面使用thymeleaf语法

#### th:text

从请求域获取并绑定指定文本

```java
@GetMapping("/text")
public String test(Model model) {
	model.addAttribute("age",18);
	model.addAttribute("font","<font color='red'>这是红色文字</font>");
	return "index";
}
```

在html页面中可以使用th:text绑定文本

```html
<h2 th:text="${age}"></h2>
<h2 th:text="${font}"></h2>
```

这个th:text无法解析HTML标签

#### th:utext

```html
<h2 th:utext="${font}"></h2>
```

#### th:if

判断，只有满足条件的标签才会被渲染出来

```java
@GetMapping("/if")
public String thif(Model model){
	model.addAttribute("sex","男");
	return "index";
}
```

```html
性别: <span th:if="${sex == '男'}">男</span>	<span th:if="${sex == '女'}">女</span>
```

#### th:switch

和Java中的switch类型 和case一起用

```java
@GetMapping("/switch")
public String thswitch(Model model){
	model.addAttribute("age","18");
	return "index";
}
```

```html
是否可以进入网吧
<div th:switch="${age}">
	<span th:case="18">成年了</span>
	<span th:case="60">退休了</span>
	<span th:case="0">出生了</span>
</div>
```



#### th:each

循环

```java
@GetMapping("/brand")
public String index(Model model){
	// 将查询到的所有的品牌放到请求域
	model.addAttribute("brandList",brandService.selectAll());

	return "brand/index";
}
```

```html
<table border="1px" cellspacing="4px" cellpadding="4px" align="center">
	<thead>
	<tr>
		<th>品牌id</th>
		<th>品牌名称</th>
		<th>品牌描述</th>
		<th>品牌logo</th>
		<th>品牌序号</th>
		<th>创建时间</th>
		<th>更新时间</th>
	</tr>
	</thead>
	<tbody>
	<tr th:each="brand:${brandList}">
		<td th:text="${brand.brandId}"></td>
		<td th:text="${brand.brandName}"></td>
		<td th:text="${brand.brandIntroduction}"></td>
		<td th:text="${brand.brandLogourl}">菊花</td>
		<td th:text="${brand.sortno}">1</td>
		<td th:text="${brand.createtime}">2023-8-31</td>
		<td th:text="${brand.updatetime}">2023-8-31</td>
	</tr>
	</tbody>
</table>
```



#### 时间日期格式化

**如果实体类的时间日期类型Date类型(jdk1.0提供的)**

```java
import java.util.Date;

private Date createtime;
private Date updatetime;
```

```html
<td th:text="${#dates.format(brand.createtime,'yyyy-MM-dd HH:mm:ss')}"></td>
<td th:text="${#dates.format(brand.updatetime,'yyyy-MM-dd HH:mm:ss')}"></td>
```



#### **@{...}**

使用链接表达式可以引入外部文件

在resources目录下新建static/js  static/css static/img

在spring-mvc.xml配置文件中新增配置

```xml
<!--在spring-mvc中放行静态资源，不交给DispatcherServlet处理-->
<mvc:resources mapping="/js/**" location="classpath:static/js/"/>
<mvc:resources mapping="/img/**" location="classpath:static/img/"/>
<mvc:resources mapping="/css/**" location="classpath:static/css/"/>
```

所有从项目根路径出发的/js/文件名  会去到resources目录下的static/js/目录下查找

现在在html页面中可以使用链接表达式来引入外部文件

```java
	<script th:src="@{/js/jquery.js}"></script>
	<link th:href="@{/css/bootstrap.min.css}" rel="stylesheet"/>
	<script th:src="@{/js/bootstrap.js}"></script>
```





**现在可以使用ssmhethymeleaf结合bootstrap编写案例**

```java
<table align="center" class="table table-striped table-bordered">
	<thead>
	<tr>
		<th><input type="checkbox"></th>
		<th>品牌id</th>
		<th>品牌名称</th>
		<th>品牌logo</th>
		<th>操作</th>
	</tr>
	</thead>
	<tbody>
	<tr th:each="brand:${brandList}">
		<td><input type="checkbox"></td>
		<td th:text="${brand.brandId}" style="font-size: 14px"></td>
		<td th:text="${brand.brandName}"></td>
		<td th:text="${brand.brandLogourl}">菊花</td>
		<td class="btn-group">
			<a th:href=" @{ 'brandInfo?brandId=' + ${brand.brandId} } " class="btn btn-info">查看</a>
			<button class="btn btn-warning">修改</button>
			<button class="btn btn-danger">删除</button>
		</td>
	</tr>
	</tbody>
</table>
```

```java
@GetMapping("/brandInfo")
public String brandInfo(String brandId,Model model){
	// 根据传过来的品牌id查询到数据并放入到请求域中转发到品牌信息页面
	Brand brand = brandService.findById(brandId);
	model.addAttribute("brand",brand);

	return "brand/brandInfo";
}
```

