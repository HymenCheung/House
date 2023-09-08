## Spring

### 概述

Spring是一个开源的轻量级的Java框架，它的主要目的是为了解决开发中的依赖注入问题。它提供了一个简单而有效的方式来组织和管理Java应用程序的依赖关系，从而使得开发人员能够更加容易地开发和维护复杂的应用程序

Spring有两个核心组件: IOC、AOP

### IOC

ioc的核心思想:控制反转，不再自己new对象了，而是将对象的控制权交给容器(spring)

而控制反转又依赖于DI



首先，创建一个maven项目

引入核心依赖

```xml
<!--引入了spring核心依赖  最核心的依赖是spring-core 而spring-context包含了spring-core-->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-context</artifactId>
    <version>5.3.18</version>
</dependency>
```



在resources目录下创建spring配置文件

spring.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd">


</beans>
```



现在想要将一个对象托管到容器中怎么做

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd">


    <!--将学生托管到容器中-->
    <!--class 需要托管的类  id是这个类创建的对象的唯一标识-->
    <bean class="com.atstudy.domain.Student" id="student"></bean>

</beans>
```



如何获取到容器中的对象

```java
// 创建容器(因为我们是使用的配置文件进行托管,所以需要借组配置文件创建容器)
// 以读取配置文件的方式创建一个容器
ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
// 从容器中获取到对象
// 1.通过类获取
Student student1 = applicationContext.getBean(Student.class);
System.out.println(student1);
// 2.通过id获取
Object student2 = applicationContext.getBean("student");
System.out.println(student2);
```



#### 懒加载

spring容器中，默认在容器启动时就创建对象，我们可以设置懒加载(容器启动时不创建对象，只有在第一次获取的时候再创建)

```xml
<bean class="com.atstudy.domain.Student" id="student" lazy-init="true">		// 设置懒加载
    <!--name: 给哪个属性赋值  value: 要赋给这个属性的值-->
    <property name="name" value="张三"/>
    <property name="age" value="18"/>
</bean>
```



#### 初始化和销毁方法

```java
@Data
@AllArgsConstructor
//@NoArgsConstructor
public class Person {

	private String name;
	private int age;

	public Person() {
		System.out.println("再创建对象时执行某些方法");
	}

	public void init(){
		System.out.println("初始化方法");
	}

	public void destory(){
		System.out.println("销毁方法，再对象被销毁时调用");
	}
}
```



```java
<bean class="com.atstudy.domain.Person" id="person" init-method="init" destroy-method="destory">
    <property name="name" value="tom"/>
    <property name="age" value="16"/>
</bean>
```





#### 配置实例工厂创建对象

实例工厂类

```java
/**
 * 这是一个实例工厂，用于创建Student对象
 */
public class StudentFactory {

	public Student getStudent(){
		System.out.println("通过实例工厂获取对象");
		Student student = new Student();
		student.setName("jack");
		student.setAge(18);
		// 假设还有100行代码完成初始化
		return student;
	}
}
```

配置文件

```xml
<!--将实例工厂托管到容器中-->
<bean class="com.atstudy.factory.StudentFactory" id="studentFactory">
</bean>
<!--配置实例工厂创建对象-->
<!-- 使用实例工厂中的 getStudent 方法创建对象-->
<bean class="com.atstudy.domain.Student" id="student" factory-bean="studentFactory" factory-method="getStudent">
</bean>
```





#### 配置静态工厂创建对象

```java
/**
 * 静态工厂
 */
public class StudentStaticFactory {

	public static Student getStudent(){
		System.out.println("通过实例工厂获取对象");
		Student student = new Student();
		student.setName("jack");
		student.setAge(18);
		// 假设还有100行代码完成初始化
		return student;
	}
}

```

配置文件

```xml
<!--配置静态工厂-->
<bean class="com.atstudy.factory.StudentStaticFactory" id="student" factory-method="getStudent">
</bean>
```

即使容器中并没有直接托管Student，也可以获取到Student对象，它会通过这个静态工厂的方法创建出来



### DI

依赖注入,就是给属性赋值

#### Set方法赋值

使用这种方式赋值前提是必须要有set方法，否则会报错

```xml
<bean class="com.atstudy.domain.Student" id="student">
    <!--name: 给哪个属性赋值  value: 要赋给这个属性的值-->
    <property name="name" value="张三"/>
    <property name="age" value="18"/>
</bean>
```



#### 有参构造赋值

前提是必须要有相应参数的有参构造

```xml
<bean class="com.atstudy.domain.School" id="school">
    <constructor-arg name="name" value="李四"/>
    <constructor-arg name="age" value="19"/>
    <constructor-arg name="address" value="上海"/>
</bean>
```



#### 复杂类型的注入

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
	
	private Student id;
	private Student name;
	private int age;
	private Student address;
	
	private int[] arr;
	
	private List<String> list;
	
	private Set<String> set;
	
	private Map<String,String> map;
	
	private School school;
	
}
```



在配置文件中进行赋值

```xml
<!--给User的属性赋值-->
<bean class="com.atstudy.domain.User" id="user">
    <property name="id" value="1001"/>
    <property name="name" value="王五"/>
    <property name="age" value="18"/>
    <property name="address" value="北京"/>

    <!--给数组赋值-->
    <property name="arr">
        <array>
            <value>1</value>
            <value>2</value>
        </array>
    </property>

    <!--给List赋值-->
    <property name="list">
        <list>
            <value>红色</value>
            <value>绿色</value>
            <value>蓝色</value>
        </list>
    </property>

    <!--给set赋值-->
    <property name="set">
        <set>
            <value>set1</value>
            <value>set2</value>
        </set>
    </property>

    <!--给map赋值-->
    <property name="map">
        <map>
            <entry key="brand" value="联想"/>
            <entry key="brand" value="华为"/>
        </map>
    </property>

    <!--给自定义的类型属性赋值-->
    <property name="school" ref="school"/>
</bean>
```





### 在Spring.xml中导入外部配置文件

自定义的DataSource

```java
@Component
@Data
public class DataSource {

	private String driver;
	private String username;
	private String password;
}
```

jdbc.properties

```properties
jdbc.driver=com.mysql.cj.jdbc.Driver
jdbc.username=root
jdbc.password=123456
```

想要在spring.xml配置文件中导入这个jdbc.properties

```xml
<!--假设需要在这里进行数据源的配置，而这个数据源写在jdbc.properties中的，如何将这个配置文件导入进来-->
<context:property-placeholder location="classpath:jdbc.properties"/>
<!--将自定义的DataSource类托管到容器中-->
<bean class="com.atstudy.domain.DataSource">
    <property name="driver" value="${jdbc.driver}"/>
    <property name="username" value="${jdbc.username}"/>
    <property name="password" value="${jdbc.password}"/>
</bean>
```









### 注解开发(熟练)

使用注解开发需要在spring.xml配置文件中开启组件扫描并引入相关的命名空间

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans 
       http://www.springframework.org/schema/beans/spring-beans.xsd 
       http://www.springframework.org/schema/context 
       https://www.springframework.org/schema/context/spring-context.xsd">

    <!--想要使用注解开发需要开启组件扫描-->
    <context:component-scan base-package="com.atstudy"/>
</beans>
```



#### IOC相关注解

##### @Component

这个注解作用和bean标签一样，可以将当前类托管到容器中，只要当前类处在组件扫描的范围内，加了这个注解，那么这个类就会被托管到spring容器

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component		// 这个注解意味着将当前类托管到了spring容器中
public class Student {
	
	private String name;
	private int age;
}
```

现在就可以从容器中获取到这个对象了



##### @Controller

这个注解作用和@Component是一样的，都可以将当前类托管到容器之中，只不过名字不同，为了区分层次

##### @Service

和@Controller作用一样，表示当前类是业务逻辑层

##### @Repository

和@Controller作用一样，表示当前类是数据持久层



上面这些注解和bean标签一样可以起名字(id)，如果只使用注解并没有起名字，会有一个默认名字( 类名首字母小写 )，但是如果这个类是以两个以上的大写字母开头，那么这个默认名就是类名

```java
@Component("myStudent")		// 这个注解意味着将当前类托管到了spring容器中，并且名字叫做student
public class Student {

	private String name;
	private int age;
}
```

```java
// 现在可以通过myStudent从容器中获取到这个对象
Object student = applicationContext.getBean("myStudent");
System.out.println(student);
```



如果使用这个注解的时候没有起名

```java
@Component	
public class Student {
	private String name;
	private int age;
}
```

那么这个对象默认的名字就是student



当某个类的类名以两个大写字母开头的时候，这个对象的名字就是这个类名

```
@Component
public class ABCtest {
}
```





#### DI相关注解

##### @Value

用于属性赋值

```java
@Data
@Component
public class School {

	@Value("人们小学")
	private String name;
	@Value("3")
	private int age;
}
```



##### @AutoWrite

这个注解的作用: 给添加了这个注解的属性注入相同类型的对象

```java
@Service
public class UserServiceImpl implements UserService {
}
```



```java
@Controller			// 这个注解的作用和@Component一样，只不过名字不同用于区分不同的层次
public class UserController {
	
	@Autowired		// 这个注解会从容器中获取到UserService类型的对象，赋值给这个userService
	private UserService userService;
}
```



##### @Qualifier

问题描述,现在在容器中存在两个相同类型的对象

```java
@Service
public class UserServiceImpl implements UserService {
}
```

```java
@Service
public class UserServiceCacheImpl implements UserService {
}
```

这两个对象都属于UserService类型

```java
@ToString
@Controller			// 这个注解的作用和@Component一样，只不过名字不同用于区分不同的层次
public class UserController {

	@Autowired		// 这个注解会从容器中获取到UserService类型的对象，赋值给这个userService
	private UserService userService;
}
```

这里需要从容器中获取到一个UserService类型的对象，但是根据UserService在容器中查询到了两个对象，无法确定到底该注入哪一个，执行程序会报错

```tex
No qualifying bean of type 'com.atstudy.service.UserService' available: expected single matching bean but found 2: userServiceCacheImpl,userServiceImpl
```



这个时候可以使用@Qualifier解决

我们在将这两个对象注入到容器中时，并没有起名字，那么会有默认名: 类名首字母小写(可能存在特殊情况：类名由两个大写字母开头)

```java
@ToString
@Controller			// 这个注解的作用和@Component一样，只不过名字不同用于区分不同的层次
public class UserController {

	@Autowired		// 这个注解会从容器中获取到UserService类型的对象，赋值给这个userService
	@Qualifier("userServiceImpl")			// 这里指定注入的是名字为userServiceImpl的UserService对象
	private UserService userService;
}
```



##### @Primary

添加了这个注解的对象会优先注入

```java
@Primary		// 这个注解表示优先注入
@Service
public class UserServiceImpl implements UserService {
}
```

**注意：@Primary 的优先级低于@Qualifier**



##### @Resource

@Resource是javaEE提供的注解，它默认是通过名字进行注入的，而@AutoWrite是默认通过类型进行注入，如果在使用@Resource的时候没有指定anme，那么通过类型进行注入



#### 配置文件相关的注解



##### @ComponentScan 

这个注解的作用和之前配置文件中的<context:component-scan base-package="com.atstudy"/> 是一样的，都是扫描指定包下的注解

```java
@ComponentScan(basePackages = "com.atstudy")
```

**注意：这个注解必须使用在创建容器所在的类的头顶上**

使用了这个注解之后，创建容器就不能用读取配置文件的方式了，而是应该读取注解

```java
@ComponentScan(basePackages = "com.atstudy")
public class Application {
	public static void main(String[] args) {

		// 现在全部都是使用注解开发，所以创建容器就不再使用读取配置文件的方式了，而是读取注解的方式创建容器
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Application.class);

		UserController bean = context.getBean(UserController.class);
		System.out.println(bean);
    }
}
```



##### @Configuration	

```java
@Configuration		//加了这个注解的类就是一个配置类
public class ApplicationConfig {
}

```



##### @Bean

这个注解一般是用于注入第三方的类，自己编写的类可以直接使用@Component注入

```java
@Configuration		//加了这个注解的类就是一个配置类
public class DataSourceConfig {

	/**
	 * @Bean 这个注解一般是用来注入第三方的类
	 * @return
	 */
	@Bean
	public DruidDataSource getDataSource(){
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setUsername("root");
		dataSource.setPassword("");
		dataSource.setUrl("jdbc:mysql://localhost:3306/atstudy_mall");
		// 其他配置
		return dataSource;
	}

}
```



##### @PropertySource

这个注解可以读取到外部的配置文件，作用和之前的<context:property-placeholder location="classpath:jdbc.properties"/>一样



jdbc.properties

```properties
jdbc.driver=com.mysql.cj.jdbc.Driver
jdbc.username=root
jdbc.password=123456
```



```java
package com.atstudy.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@PropertySource("jdbc.properties")
@Configuration		//加了这个注解的类就是一个配置类
public class DataSourceConfig {



	@Value("${jdbc.driver}")
	private String driver;

	@Value("${jdbc.username}")
	private String username;

	@Value("${jdbc.password}")
	private String password;

	/**
	 * @Bean 这个注解一般是用来注入第三方的类
	 * @return
	 */
	@Bean("db")
	public DruidDataSource getDataSource(){
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setDriverClassName(driver);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		// 其他配置
		return dataSource;
	}

}

```







### Spring整合Junit



#### JUNIT5

引入依赖

```xml
<!--junit相关依赖-->
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter-api</artifactId>
    <version>5.8.2</version>
    <scope>test</scope>
</dependency>
<!--spring-test-->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-test</artifactId>
    <version>5.3.18</version>
    <scope>test</scope>
</dependency>
```



接下来在test目录下的绿色的java目录下创建启动类的同级目录( 写了@ComponentScan 类所在的目录 )

例如上面蓝色的java目录下存在com.atstudy包，这个包下存在一个Application.java，并且这个类头顶上存在@ComponentScan注解

```java
@ComponentScan(basePackages = "com.atstudy")
public class Application {

	public static void main(String[] args) {

		// 以读取配置文件的方式查创建容器
//		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");


		// 读取注解创建容器
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Application.class);
	}
}
```





那么就要在绿色的java目录下创建同级目录： com.atstudy

接着在这个com.atstudy包下创建一个测试类，一般是在启动类前面或者后面加上Test，例如ApplicationTest

```java
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {Application.class})
public class ApplicationTest {

	
    // 将需要测试的类注入进来
	@Autowired
	private UserDao userDao;


    // 可以编写方法单独测试某个类中的某个方法
	@Test
	public void testUserDao(){
		userDao.doSome();
	}

}
```







#### JUNIT4

引入依赖

```xml
<!-- junit4 -->
<dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>4.13.2</version>
    <scope>test</scope>
</dependency>
<!--spring-test-->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-test</artifactId>
    <version>5.3.18</version>
    <scope>test</scope>
</dependency>
```



和上面的步骤一样，创建和启动类同级的目录

#### 配置文件开发

组件扫描写在配置文件中

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd">


    <!--开启组件扫描-->
    <context:component-scan base-package="com.atstudy"/>
</beans>
```




容器也是通过配置文件创建的

```java
public class Application {

	public static void main(String[] args) {

		// 以读取配置文件的方式查创建容器
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
	}
}
```



那么单元测试就必须是下面这种方式

```java
@RunWith(SpringJUnit4ClassRunner.class)
// 如果容器是通过读取配置文件的方式创建的，组件扫描也是在配置文件中开启的，那么需要使用这种方式
@ContextConfiguration(locations = "classpath:spring.xml")
public class ApplicationTest {


	// 现在我们需要测试UserMapper类中的方法是否有效
	@Autowired
	private UserMapper userMapper;

	@Test
	public void testUserMapper(){
		List<User> all = userMapper.findAll();
		System.out.println(Arrays.asList(all));
	}

}
```

```java
// 注意: 因为现在使用junit版本是4，所以这个@Test的包是
import org.junit.Test;

// 如果使用的junit版本是5，那么这个@Test的包是
import org.junit.jupiter.api.Test;
```



#### 全注解开发

全程没有用到配置文件

```java
@ComponentScan(basePackages = "com.atstudy")
public class Application {

	public static void main(String[] args) {

		// 以读取配置文件的方式查创建容器
//		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");


		// 读取注解创建容器
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Application.class);
	}
}
```

在使用单元测试时

```java
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Application.class})
public class ApplicationTest {


	// 现在我们需要测试UserMapper类中的方法是否有效
	@Autowired
	private UserMapper userMapper;

	@Test
	public void testUserMapper(){
		List<User> all = userMapper.findAll();
		System.out.println(Arrays.asList(all));
	}

}
```





### AOP

面向切面编程，它可以不修改源代码而进行功能增强



#### aop初体验

引入依赖

```xml
<!--spring的核心依赖-->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-context</artifactId>
    <version>5.3.18</version>
</dependency>

<!--lombok-->
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.24</version>
</dependency>

<!--下面两个是aop需要使用到的依赖-->
<dependency>
    <groupId>org.aspectj</groupId>
    <artifactId>aspectjweaver</artifactId>
    <version>1.9.7</version>
</dependency>

<dependency>
    <groupId>org.aspectj</groupId>
    <artifactId>aspectjrt</artifactId>
    <version>1.9.7</version>
</dependency>
```

开启aop支持

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
         http://www.springframework.org/schema/aop 
         https://www.springframework.org/schema/aop/spring-aop.xsd">

    <!--需要开启aop支持 需要导入额外的命名空间-->
    <aop:aspectj-autoproxy/>
</beans>
```

创建包com.atstudy,在这个包下创建controller\service\mapper包

```java
@Controller
public class UserController {

	public void doSome(){
		System.out.println("doSome");
	}

	public void doOther(){
		System.out.println("doOther");
	}
}
```

```java
@Service
public class UserServiceImpl implements UserService {
	@Override
	public void doSome() {
		System.out.println("这是业务逻辑层中的doSome方法");
	}

	@Override
	public void doOther() {
		System.out.println("这是业务逻辑层的doOther方法");
	}
}
```

引入单元测试测试这些类中的方法是否有效

```java
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring.xml")
public class TestApplication {

	@Autowired
	private UserController userController;

	@Autowired
	private UserService userService;

	@Test
	public void testUserController(){
		userController.doSome();
		userController.doOther();
	}

	@Test
	public void testUserService(){
		userService.doSome();
		userService.doOther();
	}
}
```

现在我们需要在com.atstudy.controller包中的所有的类中的所有方法 执行之前输出 功能增强了

**使用aop实现**

创建一个切面类

```java
@Component
@Aspect		// 这个注解表示这是一个切面类
public class MyAspect {


	// 定义一个切点
	@Pointcut("execution( * com.atstudy.controller.*.*(..))")
	public void pc(){
	}

	// 表示在什么时机进行功能增强
	@Before("pc()")
	public void before(){
		System.out.println("功能增强");
	}

}
```

现在所有的controller包下的所有类的所有方法在执行之前都会输出 功能增强



#### 相关概念

连接点(JoinPoint)： 可以被增强的点，一般是方法，理论上所有的方法都可以被增强

切入点(PointCut):  就是被增强的点

通知/增强(Advice)： 增强的代码

切面(Aspect)：是切入点和通知的结合

目标对象(Target)：被增强的对象

代理(Proxy)：一个类被aop增强之后，会生成一个代理对象

#### 切点表达式

在上面的切面类中使用了@Pointcut,里面有一个字符串

```tex
execution  固定写法，不用过多关注
execution( * com.atstudy.controller.*.*(..))
execution( 修饰符 包名.类名.方法名(参数列表))

* 表示是任意的, ..表示任意参数或者表示当前包及其子包下面的类
```



#### 切点函数@annotation

可以使用注解的方式进行功能增强

1.定义一个注解

```java
@Target(ElementType.METHOD)
public @interface AAA {
}
```

2.定义切面类

```java
@Component
@Aspect		// 这个注解表示这是一个切面类
public class MyAspect {


	// 定义一个群切点
//	@Pointcut("execution( * com.atstudy.controller.*UserController.doOther(..) )")

	/*表示给所有加上了@AAA注解的方法进行功能增强*/
	@Pointcut("@annotation(com.atstudy.annonation.AAA)")
	public void pc(){
	}

	// 表示在什么时机进行功能增强
	@Before("pc()")
	public void before(){
		System.out.println("功能增强");
	}

}
```

3.在需要进行功能增强的方法头顶上加上@AAA即可

```java
@AAA
public void doOther(){
	System.out.println("doOther");
}
```

#### 通知分类

##### @Before 前置通知

在目标方法执行之前进行功能增强

##### @After 后置通知

在目标方法执行之后进行功能增强，即使出现了异常也可以正常执行

##### @AfterReturning 返回后通知

在目标方法执行之后进行功能增强，但是如果出现了异常就不会进行增强

##### @AfterThrowing 异常通知

在目标方法出现了异常之后才会进行增强



##### JoinPoint

除了环绕通知，都可以传入一个JoinPoint类型的对象，这个对象可以获取到除了返回值和异常之外的所以的信息

```java
getArgs()			//  获取方法参数列表。
getTarget()			// 获取目标对象。
getSignature()		// 获取方法签名信息，包括方法名、修饰符等。
getThis()			// 获取代理对象。
toLongString()		// 获取连接点的完整信息。
toShortString() 	// 获取连接点的简短信息。
getStaticPart() 	// 获取静态部分信息，通常是一个MethodSignature对象。
getKind()			//获取连接点的类型，如method-execution、constructor-execution等。
getSourceLocation()	// 获取连接点所在位置的信息。
getStaticPart()		// 获取连接点静态部分的信息。
proceed()			//执行原始连接点。
```

##### 获取返回值

除了环绕通知，就之后@AfterReturning可以获取到目标方法的返回值

```java
@AfterReturning(value = "pc()",returning = "result")
public void afterReturning(Object result){
	System.out.println("目标方法的返回值: " + result);
	System.out.println("进行功能增强");
}
```

##### 获取到异常对象

如果目标方法出现了异常，在通知方法中可以获取到这个异常对象，但是只有环绕通知和异常通知可以获取到异常对象

```java
// 异常通知，只有在目标方法出现异常的时候才会进行增强
@AfterThrowing(value = "pc()",throwing = "exception")
public void afterThrowing(Throwable exception ){
	System.out.println("这是增强的代码");
	System.out.println("这是异常对象: " + exception);
}
```



##### @Around 环绕通知

围绕着方法执行前后进行功能增强，是最强大的通知，上面四个通知都具有局限性，但是这个环绕通知没有，上面四个能做的它都能做，不能做的也能做

```java
@Around("pc()")
public void around(ProceedingJoinPoint pjp){
	// 获取到目标方法的参数
	System.out.println(Arrays.toString(pjp.getArgs()));
	// 获取到目标方法的签名信息
	System.out.println(pjp.getSignature());

	// 获取到目标方法的返回值
	try {
		// 这是前置通知，在目标方法执行之前执行
		System.out.println("前置通知");

		// 调用目标方法
		Object result = pjp.proceed();
		System.out.println("这是目标方法的返回值: " + result);

		// 这是返回后通知，在目标方法执行之后执行，但是目标方法出现异常之后就不会执行
		System.out.println("这是返回后通知");
	} catch (Throwable e) {
		// 这是异常通知，只有目标方法出现了异常之后才会执行
		System.out.println("这个就是异常对象: " + e);
	}finally {
		// 这是后置通知，即使方法出现了异常，也可以正常执行
		System.out.println("这是后置通知");
	}
}
```



#### 底层原理

**代理**

代理是一种常见的设计模式，为其他的对象提供一个代理以访问某个对象

aop底层使用的就是动态代理

动态代理又分为两种，一种是jdk动态代理，另一种是cglib动态代理

##### jdk动态代理

了解动态代理需要先属性几个类

```java
public interface InvocationHandler {
    public Object invoke(Object proxy, Method method, Object[] args)
        throws Throwable;
}
```

这个接口只有一个方法，在调用这个方法的时候，第一个参数是代理对象，第二个参数是被代理的方法，第三个参数是调用这个被代理的方法需要的参数

```java
public class Proxy implements {
	
   
    public static Object newProxyInstance(ClassLoader loader,
                                          Class<?>[] interfaces,
                                          InvocationHandler h) {
        Objects.requireNonNull(h);

        final Class<?> caller = System.getSecurityManager() == null
                                    ? null
                                    : Reflection.getCallerClass();

        /*
         * Look up or generate the designated proxy class and its constructor.
         */
        Constructor<?> cons = getProxyConstructor(caller, loader, interfaces);

        return newProxyInstance(caller, cons, h);
    }
}
```

Proxy： 这个类是动态代理类

静态方法newProxyInstance，它可以返回一个代理类的实例，这个实例可以被当做代理类使用



**动态代理的步骤**

- 1.创建被代理的类和接口
- 2.创建一个类实现InvocationHandler接口，它必须实现invoke方法
- 3.通过proxy的静态方法newProxyInstance创建代理对象
- 4.通过·代理对象调用方法



创建接口和实现类

```java
/**
 * 需要动态代理的接口
 */
public interface PhoneFactory {

	// 生产手机的方法
	Phone create();
}
```

```java
/**
 * 需要代理的实现类
 */
public class HuaWeiPhone implements PhoneFactory{
	@Override
	public Phone create() {
		Phone phone = new Phone();
		phone.setPrice(4999);
		phone.setBrand("华为");
		return phone;
	}
}
```

创建一个类实现InvocationHandler接口

```java
public class InvocationHandlerImpl implements InvocationHandler {

	private HuaWeiPhone huaWeiPhone;

	// 通过有参构造将这个被代理的对象传入进来
	public InvocationHandlerImpl(HuaWeiPhone huaWeiPhone) {
		this.huaWeiPhone = huaWeiPhone;
	}

	public InvocationHandlerImpl() {
	}

	/**
	 *
	 * @param proxy 代理类的实例，通过有参构造拿到了
	 *
	 * @param method 被调用的方法对象
	 *
	 * @param args	调用目标方法需要的参数
	 *
	 * @return
	 * @throws Throwable
	 */
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

		// 调用目标方法得到返回值
		Phone invoke = (Phone) method.invoke(huaWeiPhone, args);
		// 进行功能增强
		invoke.setPrice( invoke.getPrice() + 200 );

		return invoke;
	}
}
```

创建代理对象并调用方法

```java
public static void main(String[] args) {
	// 创建实现类对象
	HuaWeiPhone huaWeiPhone = new HuaWeiPhone();
	InvocationHandlerImpl invocationHandler = new InvocationHandlerImpl(huaWeiPhone);
	// 获取到实现类的类加载器和实现接口对象数组
	ClassLoader classLoader = huaWeiPhone.getClass().getClassLoader();
	Class<?>[] interfaces = huaWeiPhone.getClass().getInterfaces();
	// 借助Proxy生成代理对象
	PhoneFactory o = (PhoneFactory) Proxy.newProxyInstance(classLoader, interfaces, invocationHandler);
	Phone phone = o.create();
	System.out.println(phone);
}
```



**jdk动态代理的实现过程**

调用Proxy.newProxyInstance会根据传入进去的需要代理的对象实现的接口对象数组创建一个接口的实现类，使用这个实现类(代理对象)调用目标方法，实际上调用的是InvocationHandler接口实现类中的invoke方法，而在invoke方法中我们编写了增强的逻辑

##### jdk动态代理和cglib动态代理的区别

jdk动态代理要求目标类必须实现了接口，而cglib不需要，spring默认使用jdk动态代理





### Bean的生命周期

就是容器中的对象从出生到死亡的过程，sprig提供了很多方法可以在特定时机调用

到目前位置接触过的bean的信息，已经接触到了5个生命周期

```tex
1.实例化,构造方法
2.给bean的属性赋值 set方法
3.初始化bean,我们指定的init方法
4.获取到bean正常使用
5.销毁bean,指定的destory方法
```

```java
public class Phone {
	private String brand;
	private int price;
	
	public Phone() {
		System.out.println("Phone的无参构造方法执行了");
	}

	public void init(){
		System.out.println("初始化方法");
	}

	public void destory(){
		System.out.println("销毁方法");
	}

	public void setBrand(String brand) {
		System.out.println("set方法被调用了");
		this.brand = brand;
	}


	public void setPrice(int price) {
		System.out.println("set方法被调用了");
		this.price = price;
	}
}
```

在配置文件中指定初始化和销毁方法

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean class="com.atstudy.domain.Phone" id="phone" init-method="init" destroy-method="destory">
        <property name="brand" value="华为"/>
    </bean>
</beans>
```

springbean的生命周期大致分成这几部分，但是可以通过接口拓展



定义一个类，实现BeanPostProcessor 接口，重写bean前置后置处理逻辑

```java
public class BeanPost implements BeanPostProcessor {


	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		beanName = "aaa";
		System.out.println("bean的前置处理逻辑");
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		System.out.println("bean的后置处理逻辑");
		return bean;
	}
	
}
```

在xml配置文件中配置处理器

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean class="com.atstudy.domain.Phone" id="phone" init-method="init" destroy-method="destory">
        <property name="brand" value="华为"/>
    </bean>

    <!--配置bean处理器,这个处理器会对当前配置文件中所有的bean起作用-->
    <bean class="com.atstudy.domain.BeanPost"></bean>
</beans>
```

到这里，Spring Bean的生命周期就从5个拓展到了7个

```tex
1.实例化,构造方法
2.给bean的属性赋值 set方法
如果配置了bean处理器，会执行postProcessBeforeInitialization方法
3.初始化bean,我们指定的init方法
如果配置bean处理器，会执行
4.获取到bean正常使用postProcessAfterInitialization方法
5.销毁bean,指定的destory方法
```



除此之外，bean还可以实现三个接口

```java
BeanNameAware
InitializingBean
DisposableBean
```

如果一个bean配置了bean处理器并且实现了这三个接口，那么这个beand的生命周期就拓展到了10个



**一个SpringBean完整的生命周期**

```java
// 1.实例化,构造方法
// 2.给bean的属性赋值 set方法
// 如果这个bean实现了BeanNameAware接口，那么会执行setBeanName方法
// 如果配置了bean处理器，则执行前置处理
// 如果实现了InitializingBean接口则执行afterPropertiesSet方法
// 3.初始化bean,我们指定的init方法
// 如果配置了bean处理器，则执行后置处理
// 4.获取到bean正常使用
// 如果实现了DisposableBean接口，则执行destory方法
// 5.销毁bean,执行指定的destory方法
```

