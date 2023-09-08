## SpringBoot

Spring Boot 提供了一种构建应用程序的快速方法。它会查看您的类路径和您配置的 bean，对您缺少的内容做出合理的假设，并添加这些项。使用 Spring Boot，您可以更多地关注业务功能，而不是基础设施。

SpringBoot并不是什么新的东西，是基于Spring的再封装，在设计之初就是为了简化开发，它具有两个特点(**起步依赖、自动配置**)

所有Spring能做的事情，它都能做，并且可以做的更好，还提供了一些Spring没有的东西，它内嵌了一个tomcat



### 体验

1.创建一个项目

2.引入依赖

```xml
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.5.10</version>
    </parent>


    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
    </dependencies>
```



3.创建目录结构和启动类

```tex
在java目录下创建目录结构:com.atstudy.controller
在com.atstudy目录下创建启动类
```

```java
@SpringBootApplication
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class,args);
	}
}
```

4.编写测试用的controller

```java
@RestController
public class TestController {
	
	@GetMapping("/test")
	public String test(){
		return "hello world";
	}
}
```

5.启动项目并访问，测试是否有效

**注意：如果是社区版可以添加vm参数：-DSpring.output.ansi.enabled=ALWAYS**

### 起步依赖

#### 版本锁定

```tex
每个SpringBoot项目都会有一个父工程 spring-boot-starter-parent 在这个父工程中的spring-boot-dependencies 中对常用的依赖进行了版本的锁定  这样在SpringBoot项目中添加依赖时就可以不加版本号(如果父工程中也没有定义这个依赖的版本号那么还是需要自己写)
```



如果对锁定的版本不满意可以手动覆盖

```xml
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.16</version>
</dependency>
```



#### starter机制

```tex
在SpringBoot项目中如果想要使用某个功能，只需要引入相关的starter就可以，一个starter包含了这个功能所需要用到的所有的依赖

starter分为两种
官方: 一般都是以spring-boot-starter开头后面跟上场景名称
第三方: 一般是 项目名-spring-spring-boot-starter
```



### 自动配置

```tex
SpringBoot最重要的特性就是自动配置
SpringBoot遵循 `约定大于配置` 会自动进行配置
在引入某个starter想要使用某个功能的时候，会有默认的配置
如果这个自动配置满足不了需求可以自己手动更改
```

[SpringBoot配置网页]: https://springdoc.cn/spring-boot/application-properties.html#appendix.application-properties.web



### YML配置

SpringBoot的配置文件名必须是application

SpringBoot支持两种配置文件，一种是properties，另一种就是yml(完整的后缀是yaml)

```tex
properties 配置文件中用.来区分层级 用=来赋值
```



```tex
student:
  name: 张三
  age: 15
```



#### yaml语法

yml和yaml是一样的

- 用 k: v表示键值对关系，注意 ：后面有空格
- 使用空格表示缩进关系，一般是两个空格(实际上空格的数量无所谓)，只要是对齐的一列就表示同一个层级
- 缩进时最好不要使用tab键
- 区分大小写
- 遇到需要使用驼峰命名的单词可以使用-代替，例如Java中的userName,在yml中可以使用user-name进行映射
- yml中的注释 #

##### 键值对

V的取值

**字面值**

```tex
如果值是字母值可以直接书写
字符串不用使用'' 或者"" 可以直接写

如果有转义的需求要放在'' 或者""里面
’‘: 有转义的作用 ’张三 \n 18‘
"": 也有转义作用
```

**数组**

```yml
# 多行写法
arr:
  - 1
  - 2
  - 3
  - 4
  - 5
  
# 单行写法
arr: [1,2,3,4,5]
```

**对象**

```yml
student:
  name: 张三
  age: 18
```

**对象数组**

```yml
student:
	- name: 张三
	  age: 18
	- name: 李四
	  age: 20
	- name: 王五
	  age: 22
```

**时间日期**

```yml
time: 2023/9/5
```

**占位符**

```yml
server:
  port: ${app-port}
  
app-port: 8001
```



#### 读取yml配置文件中的值

##### @Value

```yml
person:
  name: 张三
  age: 18
```

```java
@Data
@Component
public class Person {

    @Value("${person.name}")
    private String name;
    @Value("${person.age}")
    private int age;
}
```

**注意:使用@Value的类必须托管到容器中**



##### @ConfigurationProperties

```yml
person:
  name: 张三
  age: 18
```

```java
@Data
@Component
@ConfigurationProperties(prefix = "person")
public class Person {
    
    private String name;
    private int age;
}

```

**注意：配置文件中的key必须和休要赋值的属性名相同**



### 热部署

之前每次更新代码都需要重新启动，SpringBoot提供了以dev-tools工具，可以实现热部署的效果: 更新代码之后，程序会自动重启

```text
idea设置  file-->settings-->Build,Execution,Deployment-->Compiler   勾选Build project automacailly

file-->settings-->Advanced Settings --> 勾选Compiler中所有的选项
```

引入依赖

```xml
 <!--热部署所需依赖-->
 <dependency>
     <groupId>org.springframework.boot</groupId>
     <artifactId>spring-boot-devtools</artifactId>
 </dependency>
```



### SpringBoot整合junit

#### Junit5

1.引入依赖

```xml
<!--junit所需依赖-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
</dependency>
```

2.在测试目录下(绿色的java目录)创建**启动类的同级目录**

3.创建测试类进行测试

```java
@SpringBootTest
public class ApplicationTest {


	@Autowired
	private PersonMapper personMapper;

	@Test
	public void testPersonMapper(){
		Person person = personMapper.getPerson();
		System.out.println(person);
	}

}
```

**注意：这个测试类要和启动类在同一级别的目录下**



#### Junit4

如果你的项目中同时使用到了junit4和junit5，需要引入额外依赖以保证程序的正常运行

```xml
<dependency>
    <groupId>org.junit.vintage</groupId>
    <artifactId>junit-vintage-engine</artifactId>
    <version>5.8.2</version>
    <scope>test</scope>
</dependency>
```

测试类代码

如果SpringBoot的版本低于2.2.0，默认使用的还是junit4

```java
@SpringBootTest
@RunWith(SpringRunner.class)
public class ApplicationTest {


	@Autowired
	private PersonMapper personMapper;

	@Test
	public void testPersonMapper(){
		Person person = personMapper.getPerson();
		System.out.println(person);
	}

}

// 这个@Test注解的包: import org.junit.Test;
```





### SpringBoot整合Mybatis和MP

#### 整合mybatis

1.引入依赖

```xml
<!--mybatis相关依赖-->
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>2.2.0</version>
</dependency>

<!--mysql驱动-->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
</dependency>
```

2.编写配置文件

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/atstudy_mall
    username: root
    password:
    driver-class-name: com.mysql.cj.jdbc.Driver

mybatis:
  mapper-locations: classpath:mapper/*Mapper.xml    # 扫描resources下mapper目录下所有以Mapper.xml结尾的文件
  configuration:
    # 开启驼峰命名
    map-underscore-to-camel-case: true
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
```



3.编写实体类和接口

```java
@Data
public class Dept {
	private Integer deptno;
	private String dname;
	private String loc;

}
```

```java
@Mapper
public interface DeptMapper {

	/**
	 * 查询所有部门
	 * @return
	 */
	List<Dept>	selectAll();
}

```

4.创建并编写xml文件

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="com.atstudy.mapper.DeptMapper">
    <select id="selectAll" resultType="com.atstudy.doamin.Dept">
        select * from dept
    </select>
</mapper>
```

5.在测试类中编写代码进行测试

```java
@SpringBootTest
public class ApplicationTest {

	@Autowired
	private DeptMapper deptMapper;

	@Test
	public void testDeptMapper(){
		List<Dept> depts = deptMapper.selectAll();
	}

}
```



#### 整合MybatisPlus

1.MybatisPlus只做增强不做改变，我们只需要改变依赖就可以

```xml
<!--MP相关依赖-->
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-boot-starter</artifactId>
    <version>3.5.3.1</version>
</dependency>
```

2.给实体类加上mp相关注解

```java
@Data
@TableName("dept")
public class Dept {
	@TableId
	private Integer deptno;
	private String dname;
	private String loc;

}
```

3.让持久层接口去继承BaseMapper

```java
@Mapper
public interface DeptMapper extends BaseMapper<Dept> {

	/**
	 * 查询所有部门
	 * @return
	 */
	List<Dept>	selectAll();
}

```

4.将配置文件中的mybatis改成mybatis-plus，其他配置不变

```yml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/atstudy_mall
    username: root
    password:
    driver-class-name: com.mysql.cj.jdbc.Driver

mybatis-plus:
  mapper-locations: classpath:mapper/*Mapper.xml    # 扫描resources下mapper目录下所有以Mapper.xml结尾的文件
  configuration:
    # 开启驼峰命名
    map-underscore-to-camel-case: true
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
```











### web开发

#### 静态资源

在SpringBoot中静态资源有默认的存放位置

详情见官方配置文档

```tex
[classpath:/META-INF/resources/, classpath:/resources/, classpath:/static/, classpath:/public/]
```

这个静态资源的存放位置可以自己覆盖

application.yml

```yml
spring:
  web:
    resources:
      static-locations: classpath:/aaa/ # 覆盖默认的静态资源存放目录
```



#### 指定请求映射规则

@RequestMapping 注解可以指定请求映射规则

##### 指定请求路径

```java
//可以使用value或者是path来指定请求路径
@RequestMapping(path = "/test")
public String test(){
	return "hello world";
}
```

##### 指定请求方式

可以使用method属性来指定这个路径的请求方式

```java
@RequestMapping(path = "/test",method = RequestMethod.GET)
public String test(){
	return "hello world";
}
```

还可以简写

```java
@GetMapping(path = "/test")
public String test(){
	return "hello world";
}
```

##### 指定请求参数

1.指定必须携带什么参数

```java
@GetMapping(path = "/test",params = "username")
public String test(){
	return "hello world";
}
```

2.指定不能带有什么参数

```java
@GetMapping(path = "/test",params = "!username")
public String test(){
	return "hello world";
}
```

3.指定必须携带什么参数并且参数必须是指定的值

```java
@GetMapping(path = "/test",params = "username=admin")
public String test(){
	return "hello world";
}
```

4.指定携带的参数不能是指定的值

```java
@GetMapping(path = "/test",params = "username!=admin")
public String test(){
	return "hello world";
}
```

##### 指定请求头

使用headers来指定请求头中的参数，规则和上面的指定请求参数是一样的 只不过 param变成了headers

```java
@GetMapping(path = "/test",headers = "token")
public String test(){
	return "hello world";
}
```

##### 指定Content-Type

要求请求头的Content-Type必须是multipart/form-data

```java
@GetMapping(path = "/test",consumes = "multipart/form-data")
public String test(){
	return "hello world";
}
```



### 获取请求参数

#### 请求参数放在url中

**resultful风格**

```tex
请求路径: localhost:8080/test/1
```

```java
@GetMapping(path = "/test/{id}")
public String test(@PathVariable("id") String id){
	return id;
}
```

**QueryString风格**

```tex
请求路径: localhost:8080/test?id=1
```

```java
@GetMapping(path = "/test")
public String test(@RequestParam("id") String id){
	return id;
}
```



#### 参数放在请求体中

**resultful风格**

resultful风格一般会将放到请求体中的参数转成json对象进行传输

```tex
请求路径: localhost:8080/test  POST
请求体中的参数:
{ "name":"张三","age":"18","address":"上海" }
```

```java
@PostMapping(path = "/test")
public String test(@RequestBody String student){
	System.out.println(student);
	return student;
}
```

在传输的时候，这个json对象被转成字符串了，所以可以直接被student接收

除此之外，还可以创建一个对象进行接收

```java
@Data
public class Student {
	private String name;
	private Integer age;
	private String address;
}
```

```java
@PostMapping(path = "/test")
public Student test(@RequestBody Student student){
	System.out.println(student);
	return student;
}
```



**QueryString**

如果这个数据是通过表单提交的

```tex
请求体中的数据:
NAME	VALUE
name	张三
age	18
address	上海
```

可以使用@RequestParam一个个接收

```java
@PostMapping(path = "/test")
public Student test(@RequestParam("name")String name,@RequestParam("age") Integer age, @RequestParam("address") String address){
	Student student = new Student();
	student.setName(name);
	student.setAge(age);
	student.setAddress(address);
	return student;
}
```



如果方法中的参数名和请求体中的key名字相同，这个@RequetParam注解可以省略

```java
@PostMapping(path = "/test")
public Student test(String name,Integer age, String address){
	Student student = new Student();
	student.setName(name);
	student.setAge(age);
	student.setAddress(address);
	return student;
}
```



还可以直接使用一个对象进行接收

```java
@PostMapping(path = "/test")
public Student test(Student student){
	return student;
}
```



####  参数放在请求头中

```tex
请求头中的参数
token: 1234567890
```

```java
@PostMapping(path = "/test")
public String test(@RequestHeader("token") String token){

	return token;
}
```



### 获取到原生对象

#### 获取到HttpServletRequest

```java
@PostMapping(path = "/test")
public String test(@RequestHeader("token") String token, HttpServletRequest request){
	System.out.println(request);
	return token;
}
```



#### 获取到HttpServletResponse

```java
@PostMapping(path = "/test")
public String test(@RequestHeader("token") String token, HttpServletResponse response){
	System.out.println(response);
	return token;
}
```



#### 获取到HttpSession

```java
@PostMapping(path = "/test")
public String test(@RequestHeader("token") String token, HttpSession session){
	System.out.println(session);
	return token;
}
```



### @RequestParam的其他属性

#### required

表示这个参数是否是必须要传递了，默认是true，可以改成false

```java
@GetMapping(path = "/test")
public String test(@RequestParam(value = "name",required = false) String name){
	return name;
}
```

#### defaultValue

表示这个参数的默认值，如果没有传这个参数，那么有一个默认值

```java
@GetMapping(path = "/test")
public String test(@RequestParam(value = "name",required = true,defaultValue = "admin") String name){
	return name;
}
```



### 响应数据

#### 将数据响应到响应体中

SpringMVC提供了一个@ResponseBody注解将数据返回到响应体中

```java
@ResponseBody
@GetMapping(path = "/test")
public String test(String name){
	return name;
}
```

如果一个类中所有的方法都需要将数据返回到响应体中可以使用@ResyController注解

```java
@RestController
public class TestController {}
```



**在spring-boot-starter-web 中已经引入了jsckson依赖，并且是有默认的配置，可以直接将对象转成json**



### 整合thymeleaf

1.引入依赖

```xml
<!-- thymeleaf依赖 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
```



2.编写配置文件或者配置类

```java
// 默认的配置已经足够使用了
// 引入thymeleaf的starter之后，会自动配置视图解析器
// 默认的前缀:  classpath:/templates/
// 默认的后缀: .html
```



### MybatisPlus其他功能

#### 自动填充策略

再往数据库中新增数据时，可能会有一些通用的数据，例如创建贺更新时间，每次再程序中生成太麻烦，可以使用mybatisplus的自动填充策略

在项目整合SpringBoot之后，只要在想要自动填充的实体类属性上添加注解即可

```java
@TableField(fill = FieldFill.INSERT)
private LocalDateTime createtime;
@TableField(fill = FieldFill.INSERT_UPDATE)
private LocalDateTime updatetime;
```

下面是这些选项的意义

```java
public enum FieldFill {
    /**
     * 默认不处理
     */
    DEFAULT,
    /**
     * 插入填充字段
     */
    INSERT,
    /**
     * 更新填充字段
     */
    UPDATE,
    /**
     * 插入和更新填充字段
     */
    INSERT_UPDATE
}
```



加上这些注解还不够，还需要自定义填充策略

```java
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "createtime", LocalDateTime.class, LocalDateTime.now()); // 起始版本 3.3.0(推荐使用)

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "updatetime", LocalDateTime.class, LocalDateTime.now()); // 起始版本 3.3.0(推荐)

    }
}
```



新增案例

```java
@Test
public void testBrandMapper(){
	Brand brand = new Brand();
	brand.setBrandName("测试品牌12345");
	brand.setBrandLogourl("group1/M00/00/00/5d9686d4989ed2f8.png");
	int insert = brandMapper.insert(brand);
}
```

代码明明没有设置创建时间，但是在新增的时候可以看到新增sql中是由createtime的

更新案例

```java
@Test	
public void testBrandMapper(){
	Brand brand = new Brand();
	brand.setBrandId("1699658960531451905");
	brand.setBrandName("更新之后的测试品牌234567");
	
	int i = brandMapper.updateById(brand);
}
```

没有设置更新时间，但是在自动生成的sql中可以看到确实是自动加上了更新时间



#### ID自增策略

MybatisPlus内置了几种自增策略,MybatisPlus默认使用的雪花算法实现id自增，但是可以自己选择启用哪一种自增策略

```java
@Data
@TableName("spu")
public class Spu {
	@TableId(type = IdType.AUTO)	// 现在用的数据库的自增id(要确保数据库确实设置了自增)
	private Long spuId;
	private String spuName;
	private String spuTitle;
	private String spuIntroduction;
	private String spuUnit;
	private Byte spuSketchtype;
	private String spuSketch;
	private String spuSpecs;
	private Byte spuSkutype;
	private Byte spuStatus;
	private LocalDateTime createtime;
	private LocalDateTime updatetime;
	private String spuBrandId;

}
```

下面是这个type的可选值(MybatisPlus提供的几种策略)

```java
@Getter
public enum IdType {
    /**
     * 数据库ID自增
     * <p>该类型请确保数据库设置了 ID自增 否则无效</p>
     */
    AUTO(0),
    /**
     * 该类型为未设置主键类型(注解里等于跟随全局,全局里约等于 INPUT)
     */
    NONE(1),
    /**
     * 用户输入ID
     * <p>该类型可以通过自己注册自动填充插件进行填充</p>
     */
    INPUT(2),

    /* 以下2种类型、只有当插入对象ID 为空，才自动填充。 */
    /**
     * 分配ID (主键类型为number或string）,
     * 默认实现类 {@link com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator}(雪花算法)
     *
     * @since 3.3.0
     */
    ASSIGN_ID(3),
    /**
     * 分配UUID (主键类型为 string)
     * 默认实现类 {@link com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator}(UUID.replace("-",""))
     */
    ASSIGN_UUID(4);

    private final int key;

    IdType(int key) {
        this.key = key;
    }
}
```

新增代码

```java
@Test
public void testSpuMapper(){
	Spu spu = new Spu();
	spu.setSpuName("测试商品Spu123456");
	spu.setSpuTitle("测试商品Spu标题123456");
	spu.setSpuStatus((byte) 0);
	spuMapper.insert(spu);
}
```

执行代码之后，在数据库中新增了一条数据，id是在上一条数据的id基础上加1



**UUID**

**注意：这个uuid要求数据库中的主键字段，类型必须是string类型**

使用mybatisplus提供的uuid自增策略，只需要改变@TableId的type属性的值即可

```JAVA
@Data
@TableName("brand")
public class Brand {

	@TableId(type = IdType.ASSIGN_UUID)	// 指定使用MybatisPlus提供的uuid自增策略
	private String brandId;
	private String brandName;
	private String brandIntroduction;
	private String brandLogourl;
	private Integer sortno;
	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime createtime;
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private LocalDateTime updatetime;

}
```

新增代码

```java
@Test
public void testBrandMapper(){
	Brand brand = new Brand();
	brand.setBrandName("UUID");
	brand.setBrandLogourl("group1/M00/00/00/33ffbca794e7b965.jpg");

	int insert = brandMapper.insert(brand);
}
```

**雪花算法**

不指定类型，默认使用的就是雪花算法,现在手动指定

```java
@TableId(type = IdType.ASSIGN_ID)	// 指定使用MybatisPlus提供的雪花算法
```

自行添加，不显示指定这个type默认使用的就是雪花算法



#### 代码生成器

1.创建项目

2.引入依赖

```xml
<properties>
    <maven.compiler.source>11</maven.compiler.source>
    <maven.compiler.target>11</maven.compiler.target>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
</properties>


<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.5.10</version>
</parent>

<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
    </dependency>
    <!-- https://mvnrepository.com/artifact/org.freemarker/freemarker -->
    <dependency>
        <groupId>org.freemarker</groupId>
        <artifactId>freemarker</artifactId>
        <version>2.3.32</version>
    </dependency>


    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
    </dependency>

    <dependency>
        <groupId>com.baomidou</groupId>
        <artifactId>mybatis-plus-boot-starter</artifactId>
        <version>3.5.3.1</version>
    </dependency>

    <dependency>
        <groupId>com.baomidou</groupId>
        <artifactId>mybatis-plus-generator</artifactId>
        <version>3.5.3.1</version>
    </dependency>
</dependencies>
```

3.创建启动类，在main方法中粘贴官方模板

```java
FastAutoGenerator.create("url", "username", "password")
    .globalConfig(builder -> {
        builder.author("baomidou") // 设置作者
            .enableSwagger() // 开启 swagger 模式
            .fileOverride() // 覆盖已生成文件
            .outputDir("D://"); // 指定输出目录
    })
    .dataSourceConfig(builder -> builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
        int typeCode = metaInfo.getJdbcType().TYPE_CODE;
        if (typeCode == Types.SMALLINT) {
            // 自定义类型转换
            return DbColumnType.INTEGER;
        }
        return typeRegistry.getColumnType(metaInfo);

    }))
    .packageConfig(builder -> {
        builder.parent("com.baomidou.mybatisplus.samples.generator") // 设置父包名
            .moduleName("system") // 设置父包模块名
            .pathInfo(Collections.singletonMap(OutputFile.xml, "D://")); // 设置mapperXml生成路径
    })
    .strategyConfig(builder -> {
        builder.addInclude("t_simple") // 设置需要生成的表名
            .addTablePrefix("t_", "c_"); // 设置过滤表前缀
    })
    .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
    .execute();
```

自行更改基础配置：url、username、password; 自行更改需要生成的表名 sku

```java
// 改动的部位
FastAutoGenerator.create("jdbc:mysql://127.0.0.1:3306/atstudy_mall", "root", "")
    
builder.addInclude("sku") // 设置需要生成的表名
```

启动main方法，查看生成的代码的目录结构和位置





##### 自定义代码的生成目录

在第一次生成之后可以自己猜测一下哪些地方决定了代码的存放位置和包名

改动代码，再次生成

```java
// 将这个启用swaggerui模式注释起来了
// .enableSwagger() // 开启 swagger 模式
// 更改文件输出目录 改成自己当前项目的蓝色java目录的绝对路径
.outputDir("C:\\demo\\SpringBoot\\SpringBoot-04\\src\\main\\java"); // 指定输出目录

// 更改父包名
builder.parent("com.atstudy") // 设置父包名
// 现在项目较小，不需要分模块 
// .moduleName("") // 设置父包模块名

// 改变xml文件的生成路径，改成自己当前项目下的resources中的mapper目录的绝对路径
.pathInfo(Collections.singletonMap(OutputFile.xml, "C:\\demo\\SpringBoot\\SpringBoot-04\\src\\main\\resources\\mapper"));

// 需要为哪些表生成代码，可以自己addInclude
builder.addInclude("sku") // 设置需要生成的表名
.addInclude("spu");
```

点击启动main方法再次生成，看代码生成的位置和结构







### 跨域

#### @CrossOrigin

什么是跨域：

```tex
当发送请求的文件所在路径和目标路径的 协议、ip地址、端口号不一样就会有跨域问题
```

之前是使用nginx解决，现在可以使用SpringBoot提供的注解解决

```java
@RestController
@CrossOrigin		// 可以在支持跨域的方法或者类上面加上这个注解
public class SpuController {

	@Autowired
	private SpuService spuService;

	@GetMapping("/spu/{spuId}")
	public Spu findById(@PathVariable("spuId") Long spuId){
		Spu spu = spuService.getById(spuId);

		return spu;
	}
}
```

html文件中的代码

```javascript
<script>
	// 创建一个新的 XMLHttpRequest 实例
	var xhr = new XMLHttpRequest();

	// 打开一个到服务器的连接
	xhr.open('GET', 'http://localhost:8080/spu/2228939', true);

	// 定义 onreadystatechange 回调函数，该函数在请求状态改变时触发
	xhr.onreadystatechange = function () {
		// 当请求成功完成时，readyState 的值将为 4
		if (xhr.readyState === 4 && xhr.status === 200) {
			// 这里处理响应数据，例如：
			console.log(xhr.responseText);
		}
	};

	// 发送请求
	xhr.send();
</script>
```





#### 使用WebMvcConfigurer定义跨域规则

```java
@Configuration
public class CorsConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		// 设置允许跨域的路径
		registry.addMapping("/**")
				// 设置允许跨域的域名
				.allowedOriginPatterns("*")
				// 是否允许cookie
				.allowCredentials(true)
				// 允许healder属性
				.allowedHeaders("*")
				// 设置允许的请求方式
				.allowedMethods("GET", "POST", "DELETE", "PUT")
				// 跨域允许的时间
				.maxAge(3600);
	}
}

```

