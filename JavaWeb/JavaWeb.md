## 创建项目

### 创建一个maven模块

如果使用的是idea社区版，需要使用骨架

![](Resource\Snipaste_2023-07-28_09-36-11.png)

选中你的项目，新建模块，或者直接新建一个工程

![](Resource\Snipaste_2023-07-28_09-37-17.png)



创建一个webapp

![](Resource\Snipaste_2023-07-28_09-38-54.png)



看到BUILD SUCCESS说明模块或者项目创建成功

![](Resource\Snipaste_2023-07-28_09-39-55.png)



### 引入依赖

![](Resource\Snipaste_2023-07-28_09-42-01.png)



将main转成类到的根目录

![](Resource\Snipaste_2023-07-28_09-44-43.png)



### 修改配置文件

使用骨架创建的webapp中的web.xml版本太低，需要重新创建(修改)

![](Resource\Snipaste_2023-07-28_09-45-55.png)

修改之后的内容

![](D:\Java36\Java\JavaWeb\Resource\Snipaste_2023-07-28_09-47-31.png)

如果上方的https那里报红，将光标移上去，alt + 回车 fetch 引入相关依赖

```XML
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee
         https://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         id="WebApp_ID" version="4.0">


</web-app>

```

### 创建模板

每次创建webapp都去重新修改这个web.xml太麻烦了，可以将这个web.xml创建为一个模板

![](Resource\Snipaste_2023-07-28_09-50-27.png)



这是模板的配置

![](Resource\Snipaste_2023-07-28_09-51-49.png)

点击应用之后点ok

下次新建文件时，就会多出一个web.xml选项，直接点击即可

![](Resource\Snipaste_2023-07-28_09-53-32.png)



### 编写servlet程序

![](Resource\Snipaste_2023-07-28_10-02-40.png)

你的类需要实现Servlet接口，再service方法中提供服务



### 编辑url映射

![](Resource\Snipaste_2023-07-28_10-16-36.png)



### 配置webapp

![](Resource\Snipaste_2023-07-28_10-04-40.png)

![](Resource\Snipaste_2023-07-28_10-05-30.png)

![](Resource\Snipaste_2023-07-28_10-08-19.png)



### 运行webapp

![](Resource\Snipaste_2023-07-28_10-18-32.png)



看到下面这个界面说明启动成功

![](Resource\Snipaste_2023-07-28_10-19-10.png)

点击蓝色路径或者将它粘贴到浏览器地址栏进行访问可以看到这个界面

![](Resource\Snipaste_2023-07-28_10-20-24.png)

现在可以访问/hello

![](Resource\Snipaste_2023-07-28_10-21-53.png)









## web.xml中的配置

```xml
    <servlet>
        <!-- 这个是servlet的名字 -->
        <servlet-name>test01</servlet-name>
        <!-- 这里是这个servlet会创建哪个对象，一定要写全类名 -->
        <servlet-class>com.atstudy.Test01</servlet-class>

        <!--初始化参数-->
        <init-param>
            <!--初始化参数的名称-->
            <param-name>country</param-name>
            <!--初始化参数的值-->
            <param-value>china</param-value>
        </init-param>

        <!--初始化参数-->
        <init-param>
            <!--初始化参数的名称-->
            <param-name>sex</param-name>
            <!--初始化参数的值-->
            <param-value>0</param-value>
        </init-param>

        <!-- 规定当前Servlet对象在什么时候创建创建出来
            如果不写或者参数小于0，这个Servlet对象会在第一次访问时被创建出来
            如果参数大于等于0，那么在tomcat启动时就会将这个对象创建
            数字越小优先级越高
        -->
        <load-on-startup>2</load-on-startup>
    </servlet>

    <!-- 这个是servlet和url的映射关系 -->
    <servlet-mapping>
        <!-- 这是servlet的名字 -->
        <servlet-name>test01</servlet-name>
        <!-- 这个是访问路径 -->
        <url-pattern>/test01</url-pattern>
    </servlet-mapping>
```

tomcat欢迎页

```xml
    <!-- 这是默认配置，可以在web.xml中进行配置，越上面的访问优先级越高
 		如果在项目中配置了欢迎页会覆盖默认配置
	-->
	<welcome-file-list>
        <welcome-file>index.html</welcome-file>
        <welcome-file>index.htm</welcome-file>
        <welcome-file>index.jsp</welcome-file>
    </welcome-file-list>
```







## Servlet

servlet是一个接口，只有实现了这个接口的Java程序才可以被tomcat调用



### Servlet的生命周期

一个servlet对象从创建到死亡的过程就是它的生命周期

- Servlet对象只创建一次
- Servlet对象的init方法在对象创建之后马上执行，并且只执行一次
- Servlet对象的service方法，只要用户访问一次就调用一次
- Servlet的destroy方法也只执行一次，再Servlet对象死亡之前执行



**注意： Servlet对象的生命周期是由Tomcat容器进行管理的，对象的创建、方法调用、对象的销毁都是由Tomcat进行管理的，程序员不能够干涉，程序员能做的事情只有编写类实现Servlet接口，将它配置到web.xml文件中**

**Servlet对象是单例的，并且在多线程环境下运行，可能存在线程安全问题**

**在生命周期方法中应该写生命程序**

- init
  - init方法是Sun公司为程序员提供的一个初始化时机，初始化操作尽可能在init方法中完成
  - Tomcat创建Servlet对象是借组它的无参构造方法，如果程序员在构造方法中进行初始化操作，就有可能漏掉无参构造
  - init方法一般很少使用
- service 是Servlet的核心业务方法，核心业务都在这里完成，service方法是提供服务的方法，完成业务处理(核心)，使用最多
- destroy
  - 和init类似，只不过调用时机不同，在Servlet对象销毁前使用(也很少使用)



### ServletConfig

​	ServletConfig是一个接口，提供了四个方法

​	ServletConfig和servlet的联系: 一个Sevlet对象对应一个ServletConfig对象

​	ServletConfig是什么：

​					ServletConfig是一个Servlet对象相关的配置信息对象，一个Servlet在web.xml中配置的信息会自动被封装					到ServletConfig对象中，通过ServletConfig提供的方法可以获取到当前Servlet的相关配置信息



​	ServletConfig中的方法：

- ​		getServletName  获取到Servlet的名字
- ​      getServletContext  可以获取到ServletContext 对象
- ​      getInitParameter 通过name获取到初始化参数的value值
- ​      getInitParameterNames 获取到所有初始化参数的name



getServletInfo()方法

​	返回有关 servlet 的信息，如作者、版本和版权

​	不管用它，意义不大



## GenericServlet

实现了Servlet接口，实现了其他四个方法，只留下了一个service方法，降低了Servlet接口的实现难度

**GenericServlet里面的方法**

```java
public GenericServlet() { }    // 无参构造

public void destroy() {}		// 生命周期中的最后一个方法，在Servlet销毁之前调用

public String getInitParameter(String name) {}		// 根据初始化参数的name获取到value

public Enumeration<String> getInitParameterNames(){}	// 获取到当前Servlet中所有的初始化参数名

public ServletConfig getServletConfig()		// 获取到ServletConfig对象
    
public ServletContext getServletContext()  // 获取到ServletContext对象
    
public String getServletInfo()		// 返回作者、版本、版权信息
    
public void init(ServletConfig config) throws ServletException	// 初始化方法，由Tomcat调用，Tomcat会传进一个封装了Servlet配置信息的ServletConfig对象，将这个对象赋值给成员变量config
    
public void init() throws ServletException	// 这个init方法是交给程序员进行初始化操作用的
    
public void log(String msg) // 记录日志 将指定信息写入到Servlet日志文件
 
public void log(String message, Throwable t)		// 将异常信息写入到Servlet日志文件
    
public abstract void service(ServletRequest req, ServletResponse res)	// 提供服务的核心方法
    
public String getServletName()  // 获取到web.xml中的servlet-name的值 获取到Servlet的名字
    

```



### ServletContext 

如何获取到ServletContext对象：通过ServletConfig对象获取

ServletContext是什么:	

- ServletContext在tomcat服务器启动时解析webapp里面的web.xml文件时创建
- 在tomcat服务器关闭时销毁
- 在一个webapp中，只有一个ServletContext对象
- ServletContext 表示Servlet`上下文`，所有的Servlet对象共享一个ServletContext对象，存储在ServletContext中的数据可以被所有的Servlet对象获取到。ServletContext对象可以完成多个Servlet之间的数据传递



 ServletContext 中的常用方法

```java
public void setAttribute(String name, Object object)  // 往上下文对象中存储数据
    
public Object getAttribute(String name) 	// 根据name从上下文对象中获取到数据
    
public void removeAttribute(String name)		// 从上下文对象中删除指定的数据
    
public String getInitParameter(String name)		// 从上下文中参数中获取到指定的值
    
public Enumeration<String> getInitParameterNames()	// 获取到上下文参数中所有的name
    
public String getContextPath()				// 获取到项目的根路径
    
public String getRealPath(String path)			// 获取到某个文件的真实路径(webapp下面的文件)
```



**总结：ServletContext和ServletConfig和Servlet的区别和联系**

一个Servlet对应一个ServletConfig，一个webapp中可能存在多个Servlet，但是一个webapp只有一个ServletContext，这个ServletContext对象被所有的Servlet共享





## GET和POST请求的区别

###　表层区别

1. GET请求通过URL提交数据，在URL中可以看到所传递的参数;而POST通过请求体提交数据，参数在URL中是看不见的
2. GET请求提交的数据长度是有限制的(1024或者2048)，注意不是GET请求有限制而是浏览器有限制；而POST请求没有限制(数据不再地址栏中)
3. GET请求返回的内容可以被浏览器缓存起来的，而浏览器不会缓存POST请求返回的内容

以上都是GET、POST的表现形式的区别，是浏览器对这两种请求的处理方式



### 什么时候用GET，什么时候用POST

**GET**

GET请求需要上传的数据往往比较小，并且`上传的参数往往不需要保密的`，一般直接放在URL中，比较高效,一般用于查询请求

**POST**

POST请求一般需要`提交大量数据或者参数的保密性要求较高`，所以需要将数据放到请求体中进行传递，一般用于增删改操作

**结论**

如果对参数的保密性要求较高用post，如果没有保密性要求可以用get

**POST和GET哪个请求更安全**

如果按照规范来，get请求只用于查询，post请求用于修改，get请求更加安全，因为get请求只用于查询，不会威胁到服务端，从这个角度来说GET请求更安全

如果只考虑保密性的话，POST请求更加安全，因为所有的数据全部在请求体中，数据不会直接暴露



**如何区分一个请求是GET还是POST**

1.如果在url中可以看见参数，一定是GET请求

2.如果表单的method没有写或者是get，那么就是GET请求

3.如果表单中的method是post就是POST请求



## Http协议



### 请求



HTTP协议，请求协议，基于Get方法

```text
GET /?tn=15007414_20_dg HTTP/1.1		// 请求行
Accept: 			// 请求报头	text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7
Accept-Encoding: gzip, deflate, br		// 表示客户端可以接收的内容压缩编码格式
Accept-Language: zh-CN,zh;q=0.9			// 客户端首选的语言
Cache-Control: max-age=0				
Connection: keep-alive					
Cookie: BIDUPSID=7CEDE2516926D81BEACF921FCAC71C45; PSTM=1689923592; BAIDUID=7CEDE2516926D81B9A6904CB86374673:FG=1; BD_UPN=12314753; BAIDUID_BFESS=7CEDE2516926D81B9A6904CB86374673:FG=1; ZFY=QmB6mzDlHXkPo2WFhA5idxPf7KpTIALEwj0Es:AdTXOs:C; BA_HECTOR=8hag2g840l208500818k800p1ice2h11p; BDRCVFR[bPTzwF-RsLY]=mk3SLVN4HKm; BD_HOME=1; H_PS_PSSID=
Host: www.baidu.com				
Sec-Fetch-Dest: document
Sec-Fetch-Mode: navigate
Sec-Fetch-Site: none
Sec-Fetch-User: ?1
Upgrade-Insecure-Requests: 1
User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36		// 客户端的代理身份
sec-ch-ua: "Not/A)Brand";v="99", "Google Chrome";v="115", "Chromium";v="115"
sec-ch-ua-mobile: ?0			// 身份为移动设置
sec-ch-ua-platform: "Windows"	// 操作系统的平台
```

 以上信息太丰富了，简化一下

```tex

GET /?tn=15007414_20_dg HTTP/1.1		// 请求行
Accept: 			// 请求报头	text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7
Accept-Encoding: gzip, deflate, br		// 表示客户端可以接收的内容压缩编码格式
Accept-Language: zh-CN,zh;q=0.9			// 客户端首选的语言
User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36		// 客户端的代理身份
Host: www.baidu.com					// IP地址贺端口号

				// 空白行
username=aaaaa&password=abc123				// 请求体

```







一个标准的http请求分为四个部分: 请求行、请求头、空白行、请求体

#### 请求行

请求头 包含了请求方式、URI、协议

**请求方式**

GET    请求获取request-uri 标识的资源

POST	在request-uri所标识的资源添加新的数据

HEAD   请求获取request-uri标识的资源的响应消息头

PUT	存储(修改)request-uri标识的资源

DELETE  请求服务器删除request-uri标识的资源

TRACE	请求服务器回送收到的请求信息，一般用于测试或者诊断

CONECT	保留，供将来使用

OPTIONS	一般用于查询资源相关的选项或者需求

**URI**

统一资源标识符: 用于定位WenApp中的某个资源;注意：只有uri无法获取资源，只有协议、IP地址、端口号共同组成url才可以从网络中获取到资源( 定位到网络中的某个资源 )

**协议**

HTTP/1.1     使用的HTTP协议，版本号是1.1



#### 请求报头

请求报头中包含了非常多的信息

例如告诉了web服务器浏览器接收的语言版本

告诉web服务器的ip地址贺端口号

还包含了cookie等信息

#### 空白行   

用于分割请求报头和请求体

#### 请求体

GET请求，请求的参数都放在url中，所以请求体中不发送任何数据







### 响应

```tex
HTTP/1.1 200 OK				// 状态行
Connection: keep-alive		// 响应报头
Content-Encoding: gzip	
Server: BWS/1.1
...	

							// 空白行
							// 响应体
```

这个信息并没有补全，自己脑部

#### 状态行

HTTP/1.1 200 OK	

**HTTP/1.1**   协议和版本号(是HTTP协议，版本是1.1)

**200** 状态码   状态码由三位数字组成，第一个数字代表响应的类别，有5种

1xx: 表示请求被接收，继续处理

2xx: 表示成功-表示请求已被成功接收、处理

3xx: 重定向-要完成此次操作需要进一步操作

4xx: 错误，客户端错误-一般是请求方式错误或者语法错误(请求参数可能存在问题)

5xx: 错误，服务器错误-服务器未能处理这个请求

**常见的状态码**

200 OK  客户端请求成功

400 *BAD_REQUEST*  客户端请求存在语法错误，无法被服务器解析

401 *UNAUTHORIZED*   此次请求未授权，一般是没有登录

403 *FORBIDDEN*   服务器接收到了请求，但是你拒绝服务

404  *NOT_FOUND*  请求的资源不存在

405 *METHOD_NOT_ALLOWED*  此次请求方式不被运行(浏览器发送的请求方式和服务器预期的请求方式不一样)

500 *INTERNAL_SERVER_ERROR*  服务器发了在预期之外的错误

503 *SERVICE_UNAVAILABLE* 服务器暂时无法处理客户端请求，可能一段时间过后会恢复正常

**OK**

对此次响应结果的描述



#### 响应报头

web服务器的版本信息

响应内容的类型和字符编码

响应时间

Cookie

...

#### 空白行

用于分割响应报头和响应正文  

#### 响应正文

从web服务器响应回来的HTML代码





## HttpServletRequest

常用方法

```java
// 从请求行中获取到数据
getMethod()				// 获取到此次请求的请求方式

getProtocol()			// 返回请求使用的协议的名称和版本
    
getRequestURI()			// 获取uri
  
getRequestURL()			// 获取到url
  
getContextPath()		// 获取到项目的名称
    
getQueryString()		// 获取到参数的字符串信息
    
    
// 从请求头中获取到数据    
getCookies()            // 获取到所有cookie对象
    
getHeader()				// 从请求头中获取到指定名称的参数
    
 
// 从请求的正文获取到数据    
getParameter()			// 从请求参数中获取到指定的name的值
    
getParameterValues()	// 可以获取到多个相同name的值，获取到一个数组
    
getParameterMap()		//将所有的参数放到了map集合中,name作为key，value作为值

getParameterNames()		// 获取到所有参数的name
```



## HttpServletResponse

针对页面发送的请求做出数据响应，向页面返回信息（文本、图片、视频）

常用方法

```java
getWriter()					// 获取到输出流，可以在浏览器页面输出信息，但是无法输出中文，无法解析html
    
// 设置响应头信息
setHeader()					
// 例如
response.setHeader("Content-Type","text/html;charset=utf8");		// 此时就可以输出中文，还可以解析html，注意:一定要在获取到输出流之前设置

setContentType()					// 也是设置响应头中的ContentType
// 例子
response.setContentType("text/html;charset=utf8");

// 获取到字节输出流
getOutputStream()
```





## Cookie

### Cookie概述



Cookie  是由服务器端生成的并且存储在客户端上的数据，在服务端当作一个Java对象创建出来，由服务器发送给浏览器存储下来；例如登录某个网站之后，可以在服务器端生成带有用户信息的cookie对象，服务器将这个cookie返回给浏览器，在下一次访问呢这个页面时，浏览器会携带这个Cookie，服务器可以从cookie中获取到用户信息，就无需再次登录



Cookie由name和value组成，name和value都必须是字符串，服务器可以向一个客户端发送多个Cookie





### Cookie在客户端的保存形式和时间

服务器端创建Cookie对象，发送给浏览器，浏览器将它保持在缓存中，当浏览器关闭之后Cookie消失

服务器创建Cookie之后，可以通过setMaxAge方法设置Cookie的有效时间

- 有效时间 >0,那么这个Cookie对象发送给浏览器之后，可以保存到硬盘文件
- 有效时间 <0, 那么这个Cookie对象发送给浏览器之后,浏览器关闭，Cookie消失
- 有效时间 =0,那么从服务器发送过来之后就已经是一个过时Cookie



### 浏览器禁用Cookie

当浏览器禁用Cookie之后，服务器还是会将Cookie对象发送给浏览器，只不过浏览器选择了不接收



## Session

### Session的概念

在JavaWeb中，session是一个存储在服务端的Java对象，这个对象表示用户和Web服务器的一次会话( 一次会话指的是 从进入网站到关闭浏览器的这一段时间，就是用户浏览网站的时间 )，session可以存储数据，一般在session中存储的都是用户相关的数据，一般session是和cookie联合起来使用

例子： 张三登录了某个网站，在服务端会创建出两个对象，一个Cookie，一个Session，在Session中存储用户相关的信息，返回Cookie到浏览器，下次访问这个网站，浏览器会携带cookie，在服务端可以通过这个cookie找到对应的session，从session中获取到用户信息，达到免登录的效果



### Session的工作原理



用户第一次访web服务器，web服务器会为这个用户分配一个session对象，并且会生成一个cookie对象，web服务器会将cookie对象的值和session对象以键值对的方式存储在web服务器的session列表，这个session是一个Map集合，服务器将cookie返回给浏览器，浏览器将cookie缓存起来，只要浏览器不关闭，用户第二次访问服务器会自动发送缓存中的cookie给服务器，在服务器中可以根据cookie的值获取到对应的session



**关于session的超时**

当浏览器关闭之后，缓存的cookie消失，这样再去访问服务器，就没有办法获取到之前的session了，意味着上一次会话已经结束了，但是这个session还存储在session列表中，如果长时间没有访问session，那么服务器会将这个session回收

什么情况下会话结束:

1.浏览器关闭，缓存的Cookie消失，但是会话并不一定结束了，在服务端的session并没有被销毁，还可以通过某些手段继续访问到这个session

2.浏览器一直没有关闭，但长时间没有访问这个session，服务器判定这个session过时了，将这个session对象销毁掉，这时浏览器虽然没有关闭，但是这次会话已经结束了





**注意：在http协议中规定，session关联的cookie对象，它的name必须是jessionid** 



### HttpSession的常用方法

```java
getSession()			// 获取session对象,没有则创建一个
    
getSession(false)		// 获取session对象，如果获取不到则返回null
  
setAttribute(key,value)	// 往session中存储数据
    
getAttribute(key)		// 从session中获取数据
    
removeAttribute(key)	// 从session中删除数据
    
invalidate()			// 让这个session失效
    
setMaxInactiveInterval()	// 设置session有效时间，单位是s
```



注意：一个会话对应一个session，这个session的数据只在当前会话有效



## ServletContext、HttpSession、HttpServletRequest的关系

**相同点**

​	都有存取、删除数据的方法，都可以用来传递数据

​	setAttribute( "name",Object value )

​	getAttribute("name")

​	removeAttribute("name")



**不同点**

**ServletContext (application)**，是Servlet上下文对象，在一个webapp中只有一个ServletContext，它被所有的Servlet共享，一旦创建，除非服务器关闭，否则不会被销毁，因为被所有的Servlet共享，所以尽可能不要在这里面存储大数据，在多线程环境下存在线程安全的问题； 一般存储在这里的数据是被所有用户共享的，不会被修改的少量数据； ServletContext对象传递数据可以跨越Servlet、跨请求、跨会话



**HttpSession (session)**,是会话对象，每一个用户都有一个HttpSession，是用户级别的对象，存储在这个对象中的数据是某个用户专享的，其他用户获取不到，使用它传递数据可以跨Servlet、跨请求( 要求这些请求必须是同一个会话 )



**HttpServletRequest**（request），是请求对象，一次请求就有一个对象每一次请求都会重新创建，存储在这里面的数据是请求级别的数据，使用这个对象传递数据无法跨请求，最多可以跨Servlet



从大到小 ServletContext > HttpSession > HttpServletRequest

如何选择:尽可能使用小范围的对象 从小到大考虑( request < session < application)

## 转发、重定向

转发: 本质上是之发送了一次请求，其他的请求是在服务器内部完成，**浏览器的url不会发生改变**，转发可以携带数据

重定向: 本质上是发送了多次请求，由浏览器发送不同的请求共同完成一个操作，**浏览器的url会发生改变**，重定向无法携带数据



## 注解开发

```java
@WebServlet("/01")
public class Servlet01 extends HttpServlet {}
```

上面的@WebServlet注解就相当于下面的配置

```xml
    <servlet>
        <servlet-name>01</servlet-name>
        <servlet-class>com.atstudy.jsp.Servlet01</servlet-class>
    </servlet>


    <servlet-mapping>
        <servlet-name>01</servlet-name>
        <url-pattern>/01</url-pattern>
    </servlet-mapping>
```



```java
@WebServlet(
        value = {"/01","/02","/03"},        // Servlet的映射路径，可以存在多个
        loadOnStartup = 1,                  // 这个数字大于等于0，那么在服务器启动时就会将这个Servlet创建出来
        initParams = {  @WebInitParam(name = "username",value = "张三"),  @WebInitParam(name = "age",value = "18")}      // 初始化参数
)
```





## JavaWeb项目常见问题

找不到驱动

如果找不到驱动，可以尝试以下方案

1.在WEB-INF 目录下新建一个lib目录，将jar包放到里面

2.如果上面这个操作没有起作用，直接将这个驱动放到tomcat的安装目录的lib目录中





##　关于Tomcat版本

从Tomcat10开始，Servlet包不再叫做javax.Servlet ,这个Servlet包有了一个新的名字jakarta.servlet，因为在 Java EE 8 之后，Oracle 将 Java EE 规范的开发和管理交给了 Eclipse 基金会，那么在使用tomcat10的时候，引入的Servlet依赖就不再是javax.servlet而是jakarta.servlet

```xml
    <dependency>
      <groupId>jakarta.servlet</groupId>
      <artifactId>jakarta.servlet-api</artifactId>
      <version>6.0.0</version>
      <scope>provided</scope>
    </dependency>
```



## JSP

JSP 全称是Java Server Page(Java 服务器 页面)

它可以让我们在html页面动态拼接数据

jsp本质上还是一个Servlet，编写在jsp页面中内容会被翻译到_jspService方法体中，使用out.write()输出到页面中

### 注释

```jsp
<%-- 这个是jsp的注释 --%>
```



### 基本语法

```jsp
<%@ page contentType="text/html;charset=utf-8" language="java" %>
page: 指令
contentType: 表示响应格式是什么
charset=utf-8:编码格式
language: 表示这个jsp页面最终可以被翻译成什么语言

import: 导包

errorPage: 当这个jsp页面出现异常的时候，会跳转到指定的页面
isErrorPage: 将此jsp页面设置为错误页面，其他jsp页面出现错误可以跳转到此页面

session: 访问当前页面不会创建出HttpSession对象,默认就是true
```



### 直接写在jsp页面中

直接写在jsp页面中的代码或者文本会被放到out.write() 中输出到浏览器

```jsp
<html>
<body>
<h2>Hello World!</h2>
</body>
</html>
```

这些东西翻译过后放到了out.write()方法中输出

```java
out = pageContext.getOut();
out.write("<html>\r\n");
out.write("<body>\r\n");
out.write("<h2>Hello World!</h2>\r\n");
out.write("</body>\r\n");
out.write("</html>\r\n");
```



在jsp页面中直接添加你好 ，访问这个jsp页面会出现乱码，因为没有设置jsp页面的编码合适需要加上

```jsp
<%@ page contentType="text/html;charset=utf-8" language="java" %>
```



### JSP 脚本

```jsp
<% Java语句 %>
```

写在<% %> 中的java代码不会被翻译到out.write()中，写在这里面的代码是放到_jspService方法内部,作为普通JAVA代码存在

```jsp
<%  System.out.println("Hello world");  %>
```

这行jsp代码翻译过后放到了_jspService方法体内部，作为普通的java代码存在

```java
System.out.println("Hello world");
```



###　　声明脚本

```jsp
<%! java语句   %>
```

写在这里面的JAVA代码会放到jsp页面翻译过后生产的类的类体中

```jsp
<%! String country = "中国"; %>
```

这行代码翻译过后存放到了jsp翻译过后的class文件中的类体中

```java
public final class index_jsp extends HttpJspBase implements JspSourceDependent, JspSourceImports, JspSourceDirectives {
    String country = "中国";
}
```



### <%=表达式%>

表达式脚本

这个表达式脚本一般是用于将变量输出到浏览器

```jsp
<%! String name = "张三"; %>			<%-- 写在声明脚本中的内容其实是放到了jsp翻译过后的class的类体中 --%>
```

```jsp
<%=name%>		<%--  这个表达式脚本可以将变量直接输出到浏览器 --%>
```

写在<%= %> 里面的表达式，会被翻译到out.write() ; 这个表达式的值最终会被输出到浏览器，注意表达式脚本不能以;结尾



### 9大内置对象

| 内置对象名称 | 对应哪个Java类                  | 这个对象的有效范围                    |
| ------------ | ------------------------------- | ------------------------------------- |
| pageContext  | PageContext                     | 这个对象在当前jsp页面内有效(用的不多) |
| request      | HttpServletRequest              | 一次请求内有效                        |
| session      | HttpSession                     | 在一次会话内有效                      |
| application  | ServletContext                  | 整个web项目内都有效                   |
| response     | HttpServletResponse             |                                       |
| config       | ServletConfig                   |                                       |
| page         | 对应整个jsp对象 (基本不用)      |                                       |
| out          | JspWrite (可以向浏览器输出内容) |                                       |
| exception    | 异常对象                        |                                       |



**JavaWeb的四大作用域**

pageContext  只在当前jsp页面中有效

HttpServletRequest     一次请求内有效

HttpSession					一次会话内有效

ServletContext				在当前JavaWeb项目中有效



## EL表达式

如果没有el表达式，想要从request中获取到数据

```jsp
// 往请求域中存储数据
request.setAttribute("name","张三");
```

在jsp页面中获取到数据

```jsp
<%-- 第一步:定义变量，从这些作用域中获取到指存放到某个变量中 --%>
<% Object name = request.getAttribute("name"); %>

<%-- 使用表达式脚本将这个变量的值数到浏览器 --%>
<%= name %>
```

### el表达式如何使用

```jsp
${}			
```

el表达式可以直接获取到四大作用域中的值，范围从小到大获取，如果reqeust获取不到这个数据会尝试从session中获取，session也会去不到会尝试从application中获取

```java

// 王请求域、session域、上下文对象中设置数据

// 往请求域中存储数据
request.setAttribute("name","张三");

// 往session域中存储数据
HttpSession session = request.getSession();
session.setAttribute("session","这是session域中的数据");

// 往上下文对象存储数据
request.getServletContext().setAttribute("application","这是上下文对象中的数据");
```



### el表达式从四大作用域中获取数据

使用el表达式在jsp页面中直接获取数据

```jsp
<%-- 使用el表达式从四大作用域中获取到数据 --%>
${name}
${session}
${application}
```

el表达式获取到数据之后会直接输出到浏览器

### 使用el表达式从数组获取数据

```jsp
<%-- 使用el表达式从数组中获取数据 --%>
<%

        String[] names = {"张三","李四","王五"};
        request.setAttribute("names",names);
%>

<%--  通过索引获取到数组中的元素 --%>
${names[0]} <br/>
${names[1]} <br/>
${names[2]} <br/>

<%-- 在el表达式中不存在索引越界问题 --%>
${names[3]}
```



### 从List中获取数据

```jsp
<%
    List<String> list = new ArrayList<>();
    list.add("aaa");
    list.add("bbb");
    list.add("ccc");
    // 将list放到请求域中
    request.setAttribute("list",list);
%>

<%-- 使用el表达式从list中获取到数据 --%>
${ list[0] }
${ list[1] }
${ list[2] }
<%-- list同样不存在索引越界的问题 --%>
${ list[3] }

```

### 从map集合中获取到数据

```jsp
从map集合中获取数据
<%
    // 创建一个map集合，王集中添加数据，将list放到请求域中

    Map<String,String> map = new HashMap<>();
    map.put("name","张三");
    map.put("age","18");
    map.put("address","上海");
    map.put("jdbc.url","jdbc://mysql//localhost");

    request.setAttribute("map",map);
%>

<%-- 使用el表达式从map集合中获取到数据 --%>
${map.name}
${map.age}
${map.address}
<%-- map集合中的key部分如果存在. 无法直接获取 --%>
${map.jdbc.url}
<%-- 需要使用以下方式获取 --%>
${map["jdbc.url"]}

```



### 从自定义的数据类型中获取数据

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    private int id;
    private String stuName;
    private int stuAge;
    private String stuClass;
    private String stuSex;
}

```

```jsp
从自定义的类型中获取数据

<%

    // 创建学生对象，将这个对象存放到请求域
    Student stu = new Student();
    stu.setId(1001);
    stu.setStuName("张三");
    stu.setStuAge(10);
    stu.setStuClass("一年一班");
    stu.setStuSex("男");

    request.setAttribute("stu",stu);
%>

<%-- 使用el表达式获取数据 --%>
${stu.id}
${stu.stuName}
${stu.stuAge}
${stu.stuClass}
```



### el表达中的四大内置对象

**pageContext**

他说el表达式中一个内置对象，通过这个pageContext可以获取到request、response、session、servletContext、servletConfig

这个pageContext其实就是jsp中的9大内置对象中pageContext

```jsp
<%-- 直接输出获取不到 因为现在使用的el表达式，在el表达式中可以直接使用的只有四大内置对象--%>
${request}
<%-- 如果想要输出request，那么需要使用pageContext获取，这个pageContext是el表达式的内置对象，可以直接使用 --%>
${pageContext}          <br>
<%-- 可以借助pageContext内置对象获取到request、response、session、servletContext、servletConfig --%>
${pageContext.request}      <br>
${pageContext.response}     <br>
${pageContext.session}      <br>
${pageContext.servletContext}   <br>
${pageContext.servletConfig}    <br>

<%-- 可以获取到项目的根路径 --%>
${pageContext.request.contextPath}
```



**param**

```jsp
借助param可以获取到请求参数中的单个数据

<%-- 四大内置对象之二 param --%>
借助param可以获取到请求参数中的单个数据

<%--  这行代码尝试从请求参数中获取到name --%>
${param.name}
<%--  这行代码尝试从请求参数中获取到age --%>
${param.age}
```

如果请求参数中存在多个同名的name，那么使用param无法获取，它只能够获取单个参数



**paramValues**

```jsp
<%--
   使用param获取到多个参数存在问题
${param.hobby[0]} <br>
${param.hobby[1]} <br>
--%>



el表达式四大内置对象之三  paramValue   <br>

<%-- el表达式四大内置对象之三  paramValues --%>
${paramValues.hobby[0]} <br>
${paramValues.hobby[1]} <br>
${paramValues.hobby[2]} <br>
```

**initParam**

这个el表达的内置对象可以获取到上下文参数

```xml
<!--设置上下文参数-->
<context-param>
    <param-name>username</param-name>
    <param-value>tom</param-value>
</context-param>
<context-param>
    <param-name>password</param-name>
    <param-value>123456</param-value>
</context-param>
```



```jsp
el表达式四大内置对象之四 initParam<br>
${initParam.username}
${initParam.password}
```



### 运算符

在el表达式中支持运算符操作

```jsp
<%@ page contentType="text/html;charset=utf-8" language="java" %>



<%--

    运算符
    算术运算符
    + - * / %

    关系运算符
    > < >= <= == !=

    逻辑运算符
    && || ! not and or      就是短路与或非和逻辑与或非的区别

    条件运算符
    ? :

    empty 运算符
    判断结果是否为空 ， 为空结果是true 不为空 结果是false



--%>


算术运算符
${1+2}<br>
${5-3}<br>
${5*3}<br>
${5/3}<br>
${5%3}<br>

<%-- 在el表达式中 算术运算符只会进行算术运算，不会进行字符串拼接 --%>
${3 + "3"}      <%-- 在el表达式 在进行加法运算时 会将 数字字符串转成数字进行运算 --%>

<%--
这种写法是错误的，el表达式没有能力将字符串转成对应的数字时会报错
${3 + "a"}
--%>



关系运算符 <br>
${3 == 5}   <br>
${3 >= 5}   <br>
${3 <= 5}   <br>
${3 > 5}    <br>
${3 < 5}    <br>
${3 != 5}   <br>


<%
    // 这个name指向的堆里面的一块内存地址
    String name = new String("张三");
    // 将这个变量放到请求域中
    request.setAttribute("name",name);
%>

<%-- 使用el表达式判断字符串 "张三"是否和请求域中的name相等 --%>
${"张三" == name} <br>
${"张三" == "张三"}
<%-- 这个== 会调用equals方法 --%>


<br>
逻辑运算符
<br>
${1==1 && 2==2 } <br>
${1==1 && 2==3 } <br>
${1==1 || 2==3 } <br>
${ !(1==1) } <br>
${ not(1==1) }
${ 1==1 and 2==3 }
${ 1==1 or 2==3 }

<br>


条件运算符
${ 1==2 ? "1==2" : "1!=2" }
<br>



empty 运算符
<%-- 尝试从session域中获取数据 --%>
<%
    session.setAttribute("password","123456");
%>

<%-- 数据不为空返回false 数据为空 返回true --%>
${ empty password}

```



## JSTL

JSTL被称为JSP标准标识库，这是一套标准的通用的标签库，方便javaWeb程序员开发jsp项目，可以使用jstl代替jsp页面中的java代码，从而提高程序的可读性，降低维护难度





### 环境准备

引入jstl相关依赖

```xml
     <dependency>
      <groupId>jakarta.servlet.jsp.jstl</groupId>
      <artifactId>jakarta.servlet.jsp.jstl-api</artifactId>
      <version>3.0.0</version>
    </dependency>
    <dependency>
      <groupId>org.glassfish.web</groupId>
      <artifactId>jakarta.servlet.jsp.jstl</artifactId>
      <version>3.0.0</version>
    </dependency>


    <dependency>
      <groupId>org.apache.taglibs</groupId>
      <artifactId>taglibs-standard-impl</artifactId>
      <version>1.2.5</version>
      <scope>runtime</scope>
    </dependency>

    <dependency>
      <groupId>org.apache.taglibs</groupId>
      <artifactId>taglibs-standard-spec</artifactId>
      <version>1.2.5</version>
    </dependency>
```

在jsp页面中添加taglib指令

```jsp
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
```

```tex
uri: 指向jar包的tld
prefix: 前缀，理论上可以自定义，但是一般在使用时都是c,被称之为c标签
```

如何使用jstl

```tex
<c:if xxxx >
<c:each xxxx>
```

jstl提供了很多标签供我们使用，只需要记住几个常用的就可以



### if

```tex
属性		类型		     是否可以使用el表达式    		说明
test	  boolean		是			决定是否处理标签内部的内容,起条件判断的作用
var		  String		否			指定变量名，保存test属性的判断结构
scope	  String		否			指定var的作用范围,默认是page，它的值有: page\request\session\application
```

```jsp
<%--
    test=${1!=2} 判断是成立的 所以if标签内部内容会被显示出来，同时将这个判断结构存储到了page域中，变量名叫做aaa
--%>
<c:if test="${1==2}" var="aaa" scope="page">
    如果这个判断成立，那么这个文字会显示出来
</c:if>

<%-- 下面就是尝试从四大作用域中获取到名字为aaa的值 --%>
${aaa}
```





### forEach

```tex
属性				类型				是否可以使用el表达式				说明
items			数组、字符串、集合	是							   获取需要迭代的集合或者数组
var				String			   否							  定义变量，接受循环到的数据
varStatus		String			   否							  获取循环的状态信息，有一些参数，常用的有index,count
```



```jsp
<%
    List<Student> list = new ArrayList<>();
    // 使用循环创建对象
    for(int i = 1; i <= 10; i++){
        Student stu = new Student();
        stu.setId(i*100);
        stu.setStuName( i*100 +  "号学生");
        stu.setStuAge(i);
        stu.setStuClass("一年" + i+"班");
        list.add(stu);
    }

    // 将list放到请求域中
    request.setAttribute("list",list);

%>

<%-- 准备一个表格遍历学生信息 --%>
<table border="1px" cellpadding="4px" cellspacing="4px" align="center">
    <thead>
        <tr>
            <th>学生的索引</th>
            <th>学生计数</th>
            <th>学号</th>
            <th>学生姓名</th>
            <th>学生班级</th>
            <th>学生年龄</th>
        </tr>
    </thead>
    <tbody>
    <%-- 使用forEach标签进行遍历 --%>
    <c:forEach var="stu" items="${list}" varStatus="status">
        <tr>
            <td>${status.index}</td>
            <td>${status.count}</td>
            <td>${stu.id}</td>
            <td>${stu.stuName}</td>
            <td>${stu.stuAge}</td>
            <td>${stu.stuClass}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>

```

### formatDate

这个标签并不在core中，需要额外导入指令

```jsp
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
```



事件日期格式化

```tex
属性       								解释说明
value						  			  需要进行格式化的值(目标)
pattern									  自定义的时间日期格式 yyyy-MM-dd HH:mm:ss
var										 存储格式化日期的变量名
type									 有三个可选值:time date both  只显示时间、只显示日期、都显示							
```

```jsp
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
<%
    // 创建一个时间日期对象，将这个对象添加到请求域中
    request.setAttribute("now",new Date());
%>

<%-- 使用formatDate标签进行事件日期格式化 --%>
<fmt:formatDate value="${now}" pattern="yyyy-MM-dd HH:mm:ss" />

```





## 过滤器

过滤器是JavaWeb的三大组件之一(Servlet、Filter、Listener)

过滤器是用来拦截请求、还可以过滤响应

### 如何使用

- 定义一个类实现Filter接口
- 实现里面的抽象方法
- 在注解上编写拦截路径(在web.xml进行配置也可以)

```java
@WebFilter("/*")            // /*表示拦截住了所有的请求
public class EncodingFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        System.out.println("init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        // 在这个拦截器中设置请求和响应的字符编码
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        request.setCharacterEncoding("UTF-8");
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setContentType("text/html;charset=utf-8");

        System.out.println("doFilter");

        // 放行
        filterChain.doFilter(request,response);
    }

    @Override
    public void destroy() {
        System.out.println("destroy");
    }
}
```



###  作用

- 动态拦截请求和响应，可以设置更改包含在请求或者响应中的信息
- 在客户端请求之后，访问后端资源之前，拦截请求
- 在服务端响应之后，发送会客户端之前，处理响应



### 生命周期



- ```java
  init(FilterConfig filterConfig)  // 在服务器启动时调用并且只调用一次
  ```

- ```java
  doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
  // 只要满足过滤规则，救可以在filte中根据条件决定是否调用filterChain.doFilter() 方法 来决定是否放行这次请求
  ```

- ```java
  destroy()		// 在服务器关闭时调用一次，只调用一次
  ```

  

### @WebFilter

```java
@WebFilter("/*") 
```

上面这个注解和下面这个配置是一样的效果

```xml
    <filter>
        <filter-name>encoding</filter-name>
        <filter-class>com.atstudy.filter.EncodingFilter</filter-class>
    </filter>

    <filter-mapping>
        <filter-name>encoding</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>
```

**WebFilter的属性**

```java
    String description() default "";		// 这个拦截器的描述

    String displayName() default "";		// 拦截器显示的名称

    WebInitParam[] initParams() default {};	// 初始化参数

    String filterName() default "";		// 拦截器的名称

    String[] servletNames() default {};	// 过滤的Servlet，可以指定对个

    String[] value() default {};		// 拦截路径，也可以写多个

	// 指定Filter拦截的url，和servletNames相似，但是不可以在字母后加*,是按照模块划分/order/*
    String[] urlPatterns() default {};	

	// 指定Filte对dispatcher模式进行过滤，存在几个可选值：FORWARD、INCLUDE、REQUEST、ASYNC、ERROR
    DispatcherType[] dispatcherTypes() default {DispatcherType.REQUEST};

    boolean asyncSupported() default false;		// 表示是否支持异步操作模式
```



**注意:在JavaWeb项目中，放到WEB-INF下的资源是受到保护的，无法在外部直接访问**



```java
@WebFilter("/*")
public class LoginFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 在这里从session域中获取到用户数据
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 获取到用户访问的目标页面
        String requestURI = request.getRequestURI();
        String contextPath = request.getContextPath();
        // 用户访问的资源名
        String uri = requestURI.replace((contextPath +"/"),"");


        if(session.getAttribute("user") != null || uri.equals("login.jsp") ){
            // 如果从session中获取到了用户数据或者用户访问的login.jsp那么直接放行
            filterChain.doFilter(request,response);
        }else {
            // 重定向回到登录页面
            response.sendRedirect("login.jsp");
        }
    }
}
```



### FilterConfig

过滤器的配置类，可以通过这个类获取到过滤器的配置信息





## 监听器

它也是JavaWeb三大组件的一员，和过滤器很相似但是有区别，监听器可以监听某个动作的发送，当发生了某个动作时会调用特定的方法

主要用于监听某些事件的发生，并且根据这些事件做一些处理







| 监听器名字                      | 作用                                             |
| ------------------------------- | ------------------------------------------------ |
| ServletContextListener          | 监听ServletContext的创建和销毁                   |
| HttpSessionListener             | 监听HttpSession的创建和销毁                      |
| ServletRequestListener          | 监听ServletRequest的创建和销毁                   |
| ServletRequestAttributeListener | 监听往ServletRequest中添加、删除、修改数据的操作 |
| HttpSessionAttributeListener    | 监听往ServletSession中添加、删除、修改数据的操作 |
| ServletContextAttributeListener | 监听ServletContext添加、删除、修改操作           |
|                                 |                                                  |
|                                 |                                                  |





### 使用监听器统计当前的在线人数

1.ContextListener

```java
@WebListener        // 只有加上了这个注解的类才是一个监听器否则就是一个普通类
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // 监听上下文对象创建，创建时调用这个方法
        System.out.println("application create");
        // 获取到上下文对象
        ServletContext servletContext = sce.getServletContext();
        // 设置在线人数为0
        servletContext.setAttribute("onlineNum",0);

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("application del");
    }
}
```

2.SessionLiscener

```java
@WebListener
public class SessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        System.out.println("create session");



        // 获取到上下文对象
        ServletContext servletContext = se.getSession().getServletContext();
        // 获取在线人数
        Integer onlineNum = (Integer) servletContext.getAttribute("onlineNum");

        System.out.println(onlineNum);

        // 效果:每新增一个会话(一个用户使用浏览器访问这个网站)
        servletContext.setAttribute("onlineNum",++onlineNum);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        // 当一个会话关闭了，在线人数减一
        // 获取到上下文对象
        ServletContext servletContext = se.getSession().getServletContext();
        // 获取在线人数
        Integer onlineNum = (Integer) servletContext.getAttribute("onlineNum");

        // 效果:每有一个会话关闭，在线人数减1
        servletContext.setAttribute("onlineNum",--onlineNum);
    }
}
```

新增会话界面

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>新增会话</title>
</head>
<body>


当前在线人数: ${applicationScope.onlineNum}

</body>
</html>

```



关闭会话界面

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>销毁会话</title>
</head>
<body>
销毁session

<% request.getSession().invalidate(); %>
</body>
</html>
```





###  使用监听器统计访问总次数

contextLiscener

```java
        // 设置当前网站的总访问人数
        servletContext.setAttribute("total",0);
```



```java
@WebListener
public class ServletListener implements ServletRequestListener {



    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        // 每有一次请求过来，获取到上下文对象中的total，++
        ServletContext  servletContext = sre.getServletContext();
        // 获取到访问总数
        Integer total = (Integer) servletContext.getAttribute("total");
        // 访问总数++
        servletContext.setAttribute("total",++total);
    }

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        ServletRequestListener.super.requestDestroyed(sre);
    }


}
```



## 文件上传下载



准备文件上传的表单

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>文件上传</title>
</head>
<body>


<form action="<%=request.getContextPath()%>/upload" method="post" enctype="multipart/form-data">
    <input type="text" placeholder="请输入用户名" name="username"><br>
    <input type="password" placeholder="请输入用户密码" name="password"><br>
    请选择头像 <input type="file" name="img">
    <button>提交</button>

</form>

</body>
</html>

```



Servlet

```java
@MultipartConfig        // 在Servlet中要上传文件需要加上这个注解
@WebServlet("/upload")
public class FileUpLoad extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 文件上传

        // 设置字符编码
        req.setCharacterEncoding("UTF-8");

        // 获取到Part对象
        Part img = req.getPart("img");
        // 获取到文件名
        String imgName = img.getSubmittedFileName();

        // 拿到文件的存储路径  这个download是在webapp目录下，也是一个目录
        String rootPath = req.getServletContext().getRealPath("/download");
        rootPath = rootPath +"/" + imgName;
        
        // 将文件存储到指定位置
        img.write(rootPath);
    }
}

```









## 项目

