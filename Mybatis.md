## MyBatis

MyBatis是一个半自动的ORM框架，半自动表示还是需要自己写sql语句，ORM是对象关系映射

对象指的Java对象，关系指的是数据库的关系模型，映射指的在Java对象和数据库的关系模型之间建立映射关系

例如数据库中有一张表student，现在使用一个Java的Student类去做一个映射，要求类的属性和表的字段一一对应，一个对象就对应表中的一条数据

### 基本使用

1.新建一个项目

2.导入相关依赖

```xml
<dependencies>
    <!--mybatis依赖-->
    <dependency>
        <groupId>org.mybatis</groupId>
        <artifactId>mybatis</artifactId>
        <version>3.5.13</version>
    </dependency>
    <!--连接数据库需要使用到mysql驱动-->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>8.0.31</version>
    </dependency>
    <!--lombok-->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <version>1.18.24</version>
    </dependency>
</dependencies>
```

3.新建一个实体类，和某张数据表对应

```java
/**
 * 员工表实体模型类
 */
@Data
public class Emp {
	private int empno;
	private String ename;
	private String job;
	private int mgr;
	private Date hiredate;
	private double sal;
	private double comm;
	private int deptno;
}
```

4.编写配置文件

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "https://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <!--default="development"使用的是开发环境-->
    <environments default="development">
        <!--一个环境-->
        <environment id="development">
            <!--使用的是JDBC的事务机制-->
            <transactionManager type="JDBC"/>
            <!--数据源 POOLED用到了池的概念-->
            <dataSource type="POOLED">
                <!--下面都是数据源的连接信息-->
                <property name="driver" value="com.mysql.cj.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://127.0.0.1:3306/atstudy_mall"/>
                <property name="username" value="root"/>
                <property name="password" value=""/>
            </dataSource>
        </environment>
    </environments>
    <!--这里指定了持久层接口对应的xml文件的路径-->
    <mappers>
        <mapper resource="org/mybatis/example/BlogMapper.xml"/>
    </mappers>
</configuration>
```

5.编写持久层接口

```java
/**
 * Emp持久层接口
 */
public interface EmpMapper {

	/**
	 * 查询所欲员工
	 * @return
	 */
	List<Emp> selectAll();
}
```



6.在resources目录下面新建这个持久层接口的同级**目录**(新建目录的时候，目录之间是用/分隔)

7.在这个同级目录下新建xml文件,这个xml文件的名字一般和接口名相同

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "https://mybatis.org/dtd/mybatis-3-mapper.dtd">
<!--namespace 表示这个xml文件对应哪个接口-->
<mapper namespace="com.atstudy.mapper.EmpMapper">
    <!--id是接口中的方法名 resultType表示此次查询返回什么类型的数据 -->
    <select id="selectAll" resultType="com.atstudy.domain.Emp">
        select * from emp
    </select>
</mapper>
```

8.在mybatis的配置文件中指定扫描这个持久层接口对应的xml配置文件

```xml
<!--这里指定了持久层接口对应的xml文件的路径-->
<mappers>
    <mapper resource="com/atstudy/mapper/EmpMapper.xml"/>
</mappers>
```

9.编写程序，读取mybatis的配置文件，创建SqlSession，再通过SqlSession创建这个持久层接口的实现类对象

```java
public static void main(String[] args) throws IOException {
	String resource = "mybatis.xml";
	InputStream inputStream = Resources.getResourceAsStream(resource);
	SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

	SqlSession sqlSession = sqlSessionFactory.openSession();

	EmpMapper mapper = sqlSession.getMapper(EmpMapper.class);
	List<Emp> emps = mapper.selectAll();
	System.out.println(emps);
}
```



### 参数传递

#### 传递一个参数

```java
/**
 * 持久层接口
 */
public interface EmpMapper {

	/**
	 * 查询所有员工
	 * @return
	 */
	List<Emp> selectAll();

	/**
	 * 根据员工编号查询一个员工信息
	 * @param empno	员工编号
	 * @return
	 */
	Emp selectOneById(int empno);

}
```



再对应的xml文件中如何接收到empno

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="com.atstudy.mapper.EmpMapper">
    <select id="selectAll" resultType="com.atstudy.domain.Emp">
        select * from emp
    </select>

    <select id="selectOneById" resultType="com.atstudy.domain.Emp">
        select * from emp where empno = #{empno}
    </select>
</mapper>
```

#### 传递多个参数

```java
/**
 * 根据部门编号和上级领导编号查询员工信息
 * @param deptno
 * @param mgr
 * @return
 */
Emp selectOneByEmpnoAndMgr(int deptno, int mgr);
```

再xml文件中尝试直接获取

```xml
 <select id="selectOneByEmpnoAndMgr" resultType="com.atstudy.domain.Emp">
     select * from emp where deptno = #{deptno} and mgr = #{mgr}
 </select>
```

现在调用这个方法会有异常，因为它底层使用的反射，而反射无法获取到方法中的参数名

```tex
Caused by: org.apache.ibatis.binding.BindingException: Parameter 'deptno' not found. Available parameters are [arg1, arg0, param1, param2]
```



mybatis建议我们使用arg0，arg1，arg2... 获取 param1，param2... 来顺序获取到方法中的参数

```xml
<select id="selectOneByEmpnoAndMgr" resultType="com.atstudy.domain.Emp">
    select * from emp where deptno = #{arg0} and mgr = #{arg1}
</select>
```

或者

```xml
<select id="selectOneByEmpnoAndMgr" resultType="com.atstudy.domain.Emp">
    select * from emp where deptno = #{param1} and mgr = #{param2}
</select>
```



如果我们想要通过指定的名字获取到参数怎么办，可以使用@Param注解给方法中的参数指定名字，再xml配置文件中通过名字获取数据

```java
Emp selectOneByEmpnoAndMgr(@Param("deptno") int deptno, @Param("mgr") int mgr);
```

```xml
 <select id="selectOneByEmpnoAndMgr" resultType="com.atstudy.domain.Emp">
     select * from emp where deptno = #{deptno} and mgr = #{mgr}
 </select>
```

#### 传递一个Map

```java
/**
 * 根据自定义查询条件查询数据
 * @param map
 * @return
 */
Emp selectOneByMap(Map<String, Object> map);
```

```xml
<select id="selectOneByMap" resultType="com.atstudy.domain.Emp">
    <!--如果传递进来的是一个Map集合 {里面直接写map的key即可}-->
    select * from emp where deptno = #{deptno} and mgr = #{mgr}
</select>
```

代码

```java
// 自定义查询条件
Map<String, Object> map = new HashMap<>();
map.put("deptno",10);
map.put("mgr",7839);
Emp emp = mapper.selectOneByMap(map);
System.out.println(emp);
```

#### 传递一个对象

```java
/**
 * 根据对象查询员工信息
 * @param emp
 * @return
 */
Emp selectByEmp(Emp emp);
```

```xml
<select id="selectByEmp" resultType="com.atstudy.domain.Emp">
    <!--如果传入进来的是一个对象，那么{} 里面可以直接写对象的属性名-->
    select * from emp where empno = #{empno} and ename = #{ename} and deptno = #{deptno}
</select>
```

代码

```java
// 创建员工对象
Emp emp = new Emp();
emp.setEmpno(7900);
emp.setEname("JAMES");
emp.setDeptno(30);
Emp emp1 = mapper.selectByEmp(emp);
System.out.println(emp1);
```







### 单表增删改操作

#### 插入

```java
/**
 * 新增一个员工
 * @param emp
 * @return  返回的受影响的记录条数
 */
int insertEmp(Emp emp);
```



xml文件

```xml
<insert id="insertEmp">
    insert into
        emp (empno,ename,job,mgr,hiredate,sal,comm,deptno)
    values
        (
         #{empno},
         #{ename},
         #{job},
         #{mgr},
         #{hiredate},
         #{sal},
         #{comm},
         #{deptno}
        )
</insert>
```

测试

```java
public static void main(String[] args) throws IOException {
	String source = "mybatis-config.xml";

	InputStream stream = Resources.getResourceAsStream(source);
	SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(stream);

	SqlSession sqlSession = sqlSessionFactory.openSession();

	EmpMapper mapper = sqlSession.getMapper(EmpMapper.class);
	// 创建一个员工对象
	Emp emp = new Emp();
	emp.setEmpno(3333);
	emp.setEname("王五");
	emp.setJob("清洁工");
	emp.setMgr(0001);
	emp.setHiredate(new Date(System.currentTimeMillis()));
	emp.setSal(3000);
	emp.setComm(300);
	emp.setDeptno(50);
	int i = mapper.insertEmp(emp);
	System.out.println( i == 1 ? "添加成功" : "添加失败" );
}
```



#### 事务

执行程序是成功的，但是数据库中并没有发送改变，我们可以通过SqlSession来管理事务

```java
sqlSession.commit();		// 提交事务
sqlSession.rollback();		// 回滚事务
```

如果不想每次都手动提交可以再获取SqlSession的时候设置为自动提交

```java
SqlSession sqlSession = sqlSessionFactory.openSession(true);
```



#### 修改

```java
/**
 * 修改员工信息
 * @param emp
 * @return	受影响的记录条数
 */
int updateByEmp(Emp emp);
```

XML

```XML
<update id="updateByEmp">
    update emp set
                   ename = #{ename},
                   job = #{job},
                   mgr = #{mgr},
                   hiredate = #{hiredate},
                   sal = #{sal},
                   comm = #{comm},
                   deptno = #{deptno}
            where
                empno = #{empno}
</update>
```

代码

```java
public static void main(String[] args) throws IOException {
	String source = "mybatis-config.xml";

	InputStream stream = Resources.getResourceAsStream(source);
	SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(stream);

	SqlSession sqlSession = sqlSessionFactory.openSession(true);

	EmpMapper mapper = sqlSession.getMapper(EmpMapper.class);
	// 创建一个员工对象
	Emp emp = new Emp();
	emp.setEmpno(5555);
	emp.setEname("TOM");
	emp.setJob("程序员");
	emp.setMgr(7369);
	emp.setHiredate(new Date(System.currentTimeMillis()));
	emp.setSal(9000);
	emp.setComm(900);
	emp.setDeptno(60);
	int i = mapper.updateByEmp(emp);
	System.out.println( i == 1 ? "修改成功" : "修改失败" );

}
```

#### 删除

```java
/**
 * 根据员工编号删除员工信息
 * @param empno
 * @return 受影响的记录条数
 */
int deleteByEmpno(@Param("empno") int empno);
```

XML

```XML
<delete id="deleteByEmpno">
    delete from emp where empno = #{empno}
</delete>
```

自己编写代码测试方法是否有效





### 使用properties配置文件解耦合

再此之前，数据的连接信息都是写死再mybatis的配置文件中的，不方便修改，可以将这个数据库的连接信息写道一个配置文件中

jdbc.properties

```properties
jdbc.driver=com.mysql.cj.jdbc.Driver
jdbc.url=jdbc:mysql://127.0.0.1:3306/atstudy_mall
jdbc.username=root
jdbc.password=
```

修改mybatis的配置文件

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "https://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>

    <!--再这里导入properties配置文件-->
    <properties resource="jdbc.properties"/>

    <environments default="development">
        <environment id="development">
            <!--JDBC的事务管理-->
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="${jdbc.driver}"/>
                <property name="url" value="${jdbc.url}"/>
                <property name="username" value="${jdbc.username}"/>
                <property name="password" value="${jdbc.password}"/>
            </dataSource>
        </environment>
    </environments>
    <mappers>
        <package name="com/atstudy/mapper"/>
    </mappers>
</configuration>
```



### mybatis常用配置

#### mappers

```xml
<mappers>
    <!--扫描指定的xml文件-->
    <mapper resource="com/atstudy/mapper/EmpMapper.xml"/>
</mappers>
```

```xml
<mappers>
    <!--扫描指定目录下的所有的xml文件用的最多-->
    <package name="com/atstudy/mapper"/>
</mappers>
```



#### 驼峰映射

实体类和接口

```java
@Data
public class Brand {
	private String brand_id;
	private String brand_name;
	private String brand_introduction;
	private String brand_logourl;
	private int sortno;
	private Timestamp createtime;
	private Timestamp updatetime;
}
```



```java
/**
 * 品牌数据持久层接口
 */
public interface BrandMapper {

	List<Brand> selectAll();
}
```

xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="com.atstudy.mapper.BrandMapper">
    <select id="selectAll" resultType="com.atstudy.domain.Brand">
        select * from brand
    </select>
</mapper>
```

现在可以正常使用，因为实体类的属性名和brand表的字段名是完全对应的，但是违反了Java的命名规范



改动实体类以符合命名规范

```java
@Data
public class Brand {
	private String brandId;
	private String brandName;
	private String brandIntroduction;
	private String brandLogourl;
	private int sortno;
	private Timestamp createtime;
	private Timestamp updatetime;
}
```



这样虽然符合了命名规范，但是这些和数据表的字段对不上的属性就查询不到值了

mybatis提供了驼峰映射的设置

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "https://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>

    <!--再这里导入properties配置文件-->
    <properties resource="jdbc.properties"/>

    <settings>
        <!--开启驼峰映射-->
        <setting name="mapUnderscoreToCamelCase" value="true"/>
    </settings>


    <environments default="development">
        <environment id="development">
            <!--JDBC的事务管理-->
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="${jdbc.driver}"/>
                <property name="url" value="${jdbc.url}"/>
                <property name="username" value="${jdbc.username}"/>
                <property name="password" value="${jdbc.password}"/>
            </dataSource>
        </environment>
    </environments>
    <mappers>
        <!--扫描指定目录下的所有的xml文件-->
        <package name="com/atstudy/mapper"/>
    </mappers>
</configuration>
```



**注意：再mybatis配置文件中，每一个标签都是由顺序的，如果顺序乱了那么配置文件会爆红**



#### 日志

可以在settings标签内部开启查询日志

```xml
<settings>
    <!--开启驼峰映射-->
    <setting name="mapUnderscoreToCamelCase" value="true"/>
    <!--开启日志-->
    <setting name="logImpl" value="STDOUT_LOGGING"/>
</settings>
```



### #{}和${}的区别

使用#{}可以防止sql注入，它是占位,类似PreparedStatement，而${}是字符串拼接，是无法防止sql注入的，类似Statement



### 注解开发

在编写一些比较简单的curd操作时，可以使用注解简化开发，不需要生成xml文件，真实开发不建议使用

**@Insert**

```java
@Insert("insert into brand " +
		"(brand_id,brand_name,brand_introduction,brand_logourl,sortno,createtime,updatetime) " +
		"values (#{brandId},#{brandName},#{brandIntroduction},#{brandLogourl},#{sortno},#{createtime},#{updatetime})")
int insertBrand(Brand brand);
```



**@Delete**

```java
@Delete("delete from brand where brand_id = #{brandId}")
int deleteById(String brandId);
```



**@Update**

```java
@Update("update brand set " +
		"brand_name = #{brandName}," +
		"brand_introduction=#{brandIntroduction}," +
		"brand_logourl=#{brandLogourl}," +
		"sortno=#{sortno}," +
		"updatetime=#{updatetime} " +
		"where " +
		"brand_id = #{brandId}")
int updateBrand(Brand brand);
```



**@Select**

```java
@Select("select * from brand")
List<Brand> selectAll();
```



### 分页查询

可以使用第三方分页插件

1.引入依赖

```xml
<!--分页插件-->
<dependency>
    <groupId>com.github.pagehelper</groupId>
    <artifactId>pagehelper</artifactId>
    <version>5.3.3</version>
</dependency>
```



2.在mybatis的配置文件中配置插件

```java
<plugins>
        <!-- com.github.pagehelper为PageHelper类所在包名 -->
        <plugin interceptor="com.github.pagehelper.PageInterceptor">
            <property name="dialect " value="com.github.pagehelper.dialect.helper.MySqlDialect"/>
        </plugin>
</plugins>
```

3.使用分页插件

```java
BrandMapper mapper = sqlSession.getMapper(BrandMapper.class);
// 使用PageHelper进行分页
PageHelper.startPage(8,10);
List<Brand> brands = mapper.selectAll();
System.out.println(brands);
```



### 动态SQL

之前无论是使用xml还是注解编写sql语句，都只能够进行简单的查询，如果是比较复杂的查询就无能为力

动态sql可以进行判断，根据不同的查询条件拼接不同的查询语句

动态sql有这么几个常用的标签

#### if

只有这个if判断成立才会拼接这个标签中的sql语句

```java
List<Spu> selectByNameOrTitle(@Param("spuName") String spuName,@Param("spuTitle") String spuTitle);
```

```xml
<select id="selectByNameOrTitle" resultType="com.atstudy.domain.Spu">
    <!--传入进进来的商品Spu的名称获取标题有可能是空字符串或者是null，需要对这种情况做处理-->
    select * from spu  where 1 = 1
    <if test="spuName != null and spuName.length > 0">
        <!--如果传入进来的商品名字部位null并且长度大于0，说明需要根据名字查询  只有这个判断成立才会拼接里面的sql语句-->
        spu_name like '%${spuName}%'
    </if>
    <if test="spuTitle != null and spuTitle.length > 0">
        or spu_title like '%${spuTitle}%'
    </if>
</select>
```

这个if标签还是有局限性

#### choose (when, otherwise)

choose类似Java的switch when就像是case otherwise就像是default

如果我们想要在多个条件中选择一个的时候，可以使用choose，它类似Java中的switch

```java
	/**
	 * 根据id或者名字或者标题查询，id优先级最高，依次是名字、标题
	 * @return
	 */
	List<Spu> selectByIdOrNameOrTitle(@Param("spuId") Long spuId,
									  @Param("spuName") String spuName,
									  @Param("spuTitle") String spuTitle);
```

```xml
<select id="selectByIdOrNameOrTitle" resultType="com.atstudy.domain.Spu">
    select * from spu where
    <choose>
        <when test="spuId != null">
            spu_id = #{spuId}
        </when>
        <when test="spuName != null and spuName.length > 0">
            spu_name = #{spuName}
        </when>
        <when test="spuTitle != null and spuTitle.length > 0">
            spu_title = #{spuTitle}
        </when>
        <otherwise>
            1=1
        </otherwise>
    </choose>
</select>
```



#### trim (where, set)

在数据持久层编写一个方法，要求传入商品名称和标题进行模糊查询，如果没有传入任何参数则查询出所有的数据

```java
List<Spu> selectByNameOrTitle(@Param("spuName") String spuName,@Param("spuTitle") String spuTitle);
```

```xml
<select id="selectByNameOrTitle" resultType="com.atstudy.domain.Spu">
    select * from spu where 
    <if test="spuName != null and spuName.length > 0">
        spu_name like '%${spuName}%'
    </if>
    <if test="spuTitle != null and spuTitle.length > 0">
       or spu_title like '%${spuTitle}%'
    </if>
</select>
```

存在问题:如果没有传入名称和标题，会拼接出 select * from spu where



**where**

where标签可以动态拼接where ，并且将紧跟着这个where后面的or、and去除

```java
<select id="selectByNameOrTitle" resultType="com.atstudy.domain.Spu">
    select * from spu 
    <where>
        <if test="spuName != null and spuName.length > 0">
            spu_name like '%${spuName}%'
        </if>
        <if test="spuTitle != null and spuTitle.length > 0">
            or spu_title like '%${spuTitle}%'
        </if>
    </where>
</select>
```

此时，如果传入null、手机，按照推测会拼接出  select * from spu where or spu_title like '%手机%'

但是where标签可以去除where后面紧跟着的or或者是and

所以真正拼接出的sql语句:select * from spu where spu_title like '%手机%'



**trim**

trim可以动态定制前缀后缀

```xml
<!--最简单的案例-->
<select id="selectById" resultType="com.atstudy.domain.Spu">
    select * from spu
    <trim prefix="where" prefixOverrides="and | or">
        and spu_id = #{spuId}
    </trim>
</select>
```

```tex
prefix  添加前缀
suffix  添加后缀
prefixOverrides 清除and、or的前缀
suffixOverrides 清除and、or的后缀
```

在这个select标签中拼接出的语句： select * from spu where spu_id = #{spuId}



**set**

set标签可以动态生成set,并将多余的,去除

```java
<update id="updateByDept">
    update dept set
    <if test="dname != null and dname.length > 0">
        dname = #{dname},
    </if>
    <if test="loc != null and loc.length > 0">
         loc = #{loc}
    </if>
    where deptno = #{deptno}
</update>
```

存在问题: 如果loc是null或者是空字符串，那么拼接出来的sql语句会多出一个,

```SQL
update dept set dname = ?, where deptno = ?
```



使用set标签改进

```xml
<update id="updateByDept">
    update dept
    <set>
        <if test="dname != null and dname.length > 0">
            dname = #{dname},
        </if>
        <if test="loc != null and loc.length > 0">
            loc = #{loc}
        </if>
    </set>
    where deptno = #{deptno}
</update>
```

传入一个带有部门编号和名称的dept对象,拼接出的sql语句

```sql
update dept SET dname = ? where deptno = ?
```





#### foreach

foreach标签的常用属性

```java
collection=""		// 要遍历哪个参数
open=""				// 使用foreach遍历一开始就要拼接的语句
close=""    		// 使用foreach遍历结束时要拼接的语句
item=""				// 给遍历到的元素起名字
separator=""		// foreach标签内部的sql的分隔符
```



foreach标签可以遍历我们传入的数组或者集合类型的参数，可以获取到其中的元素来动态拼接sql语句

```java
int deleteByIds(long[] idList);
```

```xml
<delete id="deleteByIds">
    delete from spu where spu_id in
    <foreach collection="idList" open="(" close=")" item="id" separator=",">
        #{id}
    </foreach>
</delete>
```

如果直接执行会报错:

```tex
Parameter 'idList' not found. Available parameters are [array, arg0]
```

因为在持久层接口方法中，传递的是一个数组，框架底层使用的反射，而我们在xml文件中直接使用idList代替方法中的参数，但是在反射中无法识别这个idList(反射是拿不到参数名的)

这个时候要么使用@Param注解给这个数组起别名，要么使用arg0 或者array来代替这个数组

```java
int deleteByIds(@Param("idList") long[] idList);
```



#### foreach练习

**批量添加**

```java
int insertBySkuList(@Param("skuList") List<Sku> skuList);
```

```xml
<insert id="insertBySkuList">
    insert into sku
        <!--需要往哪些字段中插入数据-->
        (sku_id,sku_spu_id,sku_name,sku_spuattr,createtime)
    values
        <foreach collection="skuList" item="sku" separator=",">
            (#{sku.skuId},#{sku.skuName},#{sku.sku_name},#{sku.sku_spuattr},#{sku.createtime})
        </foreach>
</insert>
```

```java
@Test
public void testSkuMapper(){
	LocalDateTime now = LocalDateTime.now();

	List<Sku> list = new ArrayList<>();
	for(int i = 1; i <= 10; i++){
		Sku sku = new Sku();
		sku.setSkuId((long) i);
		sku.setSkuName("sku === " + i);
		sku.setSkuSpuId((long) i);
		sku.setSkuSpuattr("这时第" + i + "个Sku");
		sku.setCreatetime(now);
		list.add(sku);
	}

	int i = skuMapper.insertBySkuList(list);
}
```

**批量修改**

```java
int updateBySkuList(@Param("skuList") List<Sku> skuList);
```

```xml
<update id="updateBySkuList">
    <foreach collection="skuList" item="sku">
        update sku set sku_name = #{sku.skuName},updatetime = #{sku.updatetime} where sku_id = #{sku.skuId};
    </foreach>
</update>
```

```java
@Test
public void testSkuMapper(){
	LocalDateTime now = LocalDateTime.now();

	List<Sku> list = new ArrayList<>();
	for(int i = 1; i <= 10; i++){
		Sku sku = new Sku();
		sku.setSkuId((long) i);
		sku.setSkuName("更新之后的Sku === " + i);
		sku.setUpdatetime(now);
		list.add(sku);
	}

	int i = skuMapper.updateBySkuList(list);
}
```



此时直接执行是会报错的，需要在数据库的url后面加上?allowMultiQueries=true

```properties
jdbc.driver=com.mysql.cj.jdbc.Driver
jdbc.url=jdbc:mysql://127.0.0.1:3306/atstudy_mall?allowMultiQueries=true
jdbc.username=root
jdbc.password=
```





### Spring整合Mybatis

1.导入依赖

```xml
<!--spring相关依赖-->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-context</artifactId>
    <version>5.3.23</version>
</dependency>

<!--mybatis依赖-->
<dependency>
    <groupId>org.mybatis</groupId>
    <artifactId>mybatis</artifactId>
    <version>3.5.13</version>
</dependency>

<!--mysql驱动-->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.31</version>
</dependency>

<!--lombok-->
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.24</version>
</dependency>

<!--spring整合mybatis需要用到的依赖-->
<dependency>
    <groupId>org.mybatis</groupId>
    <artifactId>mybatis-spring</artifactId>
    <version>2.0.6</version>
</dependency>

<!--引入spring事务相关依赖-->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-tx</artifactId>
    <version>5.3.23</version>
</dependency>

<!--引入spring-jdbc依赖-->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-jdbc</artifactId>
    <version>5.3.23</version>
</dependency>

<!--整合junit-->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-test</artifactId>
    <version>5.3.23</version>
</dependency>

<dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>4.13.2</version>
    <scope>test</scope>
</dependency>

<!--druid连接池-->
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid</artifactId>
    <version>1.2.18</version>
</dependency>
```



2.编写数据源的配置文件

jdbc.properties

```properties
jdbc.driver=com.mysql.cj.jdbc.Driver
jdbc.url=jdbc:mysql://127.0.0.1:3306/atstudy_mall
jdbc.username=root
jdbc.password=
```

3.编写myabtis的配置文件

mybatis-config.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "https://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
<settings>
    <!--开启驼峰映射-->
    <setting name="mapUnderscoreToCamelCase" value="true"/>
    <!--开启日志-->
    <setting name="logImpl" value="STDOUT_LOGGING"/>
</settings>
</configuration>
```

4.编写spring的配置文件整合数据源和mybatis

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd">


    <!--开启组件扫描-->
    <context:component-scan base-package="com.atstudy"/>

    <!--引入外部配置文件-->
    <context:property-placeholder location="classpath:jdbc.properties"/>


    <!--配置数据源-->
    <bean class="com.alibaba.druid.pool.DruidDataSource" id="dataSource">
        <property name="username" value="${jdbc.username}"/>
        <property name="password" value="${jdbc.password}"/>
        <property name="url" value="${jdbc.url}"/>
        <property name="driverClassName" value="${jdbc.driver}"/>
    </bean>

    <!--托管SqlSessionFactory到容器中-->
    <bean id="sqlSession" class="org.mybatis.spring.SqlSessionFactoryBean">
        <!--指定数据源-->
        <property name="dataSource" ref="dataSource"/>
        <!--整合mybatis的配置文件-->
        <property name="configLocation" value="classpath:mybatis-config.xml"/>
    </bean>

    <!--扫描指定位置的xml文件-->
    <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <!--要求再resources目录下创建持久层接口的同级目录-->
        <property name="basePackage" value="com.atstudy.mapper"/>
    </bean>
</beans>
```





### Spring整合MybatisPlus

1.新建项目

2.导入依赖

```xml
<!--spring相关依赖-->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-context</artifactId>
    <version>5.3.23</version>
</dependency>

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


<!--mysql驱动-->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.31</version>
</dependency>

<!--lombok-->
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.24</version>
</dependency>

<!--spring整合mybatis需要用到的依赖-->
<dependency>
    <groupId>org.mybatis</groupId>
    <artifactId>mybatis-spring</artifactId>
    <version>2.0.6</version>
</dependency>

<!--引入spring事务相关依赖-->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-tx</artifactId>
    <version>5.3.23</version>
</dependency>

<!--引入spring-jdbc依赖-->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-jdbc</artifactId>
    <version>5.3.23</version>
</dependency>

<!--整合junit-->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-test</artifactId>
    <version>5.3.23</version>
</dependency>

<dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>4.13.2</version>
    <scope>test</scope>
</dependency>

<!--druid连接池-->
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid</artifactId>
    <version>1.2.18</version>
</dependency>
```

3.编写配置文件

jdbc.properties

```properties
jdbc.driver=com.mysql.cj.jdbc.Driver
jdbc.url=jdbc:mysql://127.0.0.1:3306/atstudy_mall?allowMultiQueries=true
jdbc.username=root
jdbc.password=
```

mybats-config.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "https://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>

    <!--这里可以开启驼峰映射和日志-->
</configuration>
```

spring-config.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd">


    <!--开启组件扫描-->
    <context:component-scan base-package="com.atstudy"/>

    <!--引入外部配置文件-->
    <context:property-placeholder location="classpath:jdbc.properties"/>


    <!--配置数据源-->
    <bean class="com.alibaba.druid.pool.DruidDataSource" id="dataSource">
        <property name="username" value="${jdbc.username}"/>
        <property name="password" value="${jdbc.password}"/>
        <property name="url" value="${jdbc.url}"/>
        <property name="driverClassName" value="${jdbc.driver}"/>
    </bean>

    <!--托管SqlSessionFactory到容器中-->
    <bean id="sqlSession" class="com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean">
        <!--指定数据源-->
        <property name="dataSource" ref="dataSource"/>
        <!--整合mybatis的配置文件-->
        <property name="configLocation" value="classpath:mybatis-config.xml"/>
    </bean>

    <!--扫描指定位置的xml文件-->
    <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <!--要求再resources目录下创建持久层接口的同级目录-->
        <property name="basePackage" value="com.atstudy.mapper"/>
    </bean>
</beans>
```



### MybatisPlus基本使用

#### 编写实体类

```java
@Data
public class Spu {
    
    @TableId	// 表示这个字段是主键字段
	private Long pspuId;
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

#### 使用通用方法

如果只要要单表的crud，再引入了mybatisplus之后是不需要创建xml文件的

```java
@Autowired
private SpuMapper spuMapper;

@Test
public void testSpuMapper(){
	// 测试BaseMapper中的通用方法
	// 查询所有
	List<Spu> spus = spuMapper.selectList(null);
}
```



#### 通用方法

##### int insert(T entity)

插入一条数据，要求传入一个实体对象

```java
// 创建一个Spu用于插入
Spu spu = new Spu();
spu.setSpuId(1L);
spu.setSpuName("mp插入数据");
spu.setSpuTitle("MP Insert");
spu.setSpuStatus((byte) 1);
// 调用插入方法
int insert = spuMapper.insert(spu);
System.out.println(insert == 1 ? "插入成功" : "插入失败");
```

不需要自己编写sql语句



##### int deleteById(Serializable id)

删除一条数据，根据传入进来的数据删除(找到数据库中的主键字段进行删除)

```java
// 删除一条数据
int i = spuMapper.deleteById(1L);
System.out.println( i == 1 ? "删除成功" : "删除失败");
```



##### int deleteById(T entity)

删除一条数据，是根据传入进来的实体对象的加了@TableId对应的数据库中的字段进行删除

```java
// 删除一条数据
Spu spu = new Spu();
spu.setSpuId(2228939L);
int i = spuMapper.deleteById(spu);
System.out.println( i == 1 ? "删除成功" : "删除失败");
```



##### int deleteByMap(@Param("cm") Map<String, Object> columnMap)

根据自定义的删除条件进行删除,要求传入一个map，删除语句是key=value

例如想要删除一个spu名字为罗莱家纺 LUOLAI的Spu

```java
// 删除一条数据
Map<String, Object> map = new HashMap<>();
map.put("spu_name","罗莱家纺 LUOLAI");
int i = spuMapper.deleteByMap(map);
System.out.println( i == 1 ? "删除成功" : "删除失败");
```



##### int delete(@Param("ew") Wrapper<T> queryWrapper)

根据条件构造器删除，条件构造器提供的方法见官网

[MybatisPlis条件构造器]: https://baomidou.com/pages/10c804/#alleq



```java
// 根据条件构造器删除
// 创建一个条件构造器
QueryWrapper<Spu> wrapper = new QueryWrapper<>();
wrapper.eq("spu_id",3107472);
int row = spuMapper.delete(wrapper);
System.out.println(row == 1 ? "删除成功" : "删除失败");
```



例子:需要删除所有的得力和小米的商品数据

```java
QueryWrapper<Spu> wrapper = new QueryWrapper<>();
wrapper.like("spu_name","得力")
		.or(i -> i.like("spu_name", "小米"));
int row = spuMapper.delete(wrapper);
System.out.println(row > 0 ? "删除成功" : "删除失败");
```





##### int deleteBatchIds(@Param("coll") Collection<?> idList)

根据传入进去的idList批量删除数据(根据主键字段进行删除)

```java
ArrayList<Long> idList = new ArrayList<>();
idList.add(68390040690L);
idList.add(100006728065L);
idList.add(100009186319L);
int i = spuMapper.deleteBatchIds(idList);
System.out.println(i > 0 ? "批量删除成功" : "批量删除失败");
```



##### int update(@Param("et") T entity, @Param("ew") Wrapper<T> updateWrapper)

根据条件构造器进行更新

```java
// 创建一个更新条件构造器
UpdateWrapper<Spu> updateWrapper = new UpdateWrapper<>();
updateWrapper.set("spu_name","华为智慧屏SSS")
		.eq("spu_id",100017040962L);
int update = spuMapper.update(new Spu(),updateWrapper);
System.out.println(update > 0 ? "更新成功" : "更新失败");
```

set 用于设置需要更新的字段和和更新后的值 eq设置的是更新条件





##### int updateById(@Param("et") T entity)

根据传入进去的实体类进行更新 (根据加了@TableId的成员属性对应的字段进行更新)

```java
Spu spu = new Spu();
spu.setSpuId(100017040962L);
spu.setSpuName("华为智慧屏幕   AAAAAAAAAAAAAAAAA");
int i = spuMapper.updateById(spu);
System.out.println(i > 0 ? "更新成功" : "更新失败");
```





##### T selectById

根据主键进行查询

```java
Spu spu = spuMapper.selectById(557877);
```



##### List<T> selectBatchIds(@Param("coll") Collection<? extends Serializable> idList)

根据传入的id列表进行查询

```java
ArrayList<Long> idList = new ArrayList<>();
idList.add(2112125L);
idList.add(2338887L);
idList.add(2375700L);
List<Spu> spus = spuMapper.selectBatchIds(idList);
```



##### T selectOne(@Param("ew") Wrapper<T> queryWrapper)

根据条件构造器查询数据;  要求查询结果只能怪有一条数据

```java
QueryWrapper<Spu> wrapper = new QueryWrapper<>();
// wrapper.like("spu_name","海信");  这么查询是有问题的，期待返回一条数据但是结果返回了多条数据
wrapper.eq("spu_name","海信 (Hisense)");
Spu spu = spuMapper.selectOne(wrapper);
```



##### List<T> selectList(@Param("ew") Wrapper<T> queryWrapper)

根据条件构造器查询出一个集合

```java
QueryWrapper<Spu> wrapper = new QueryWrapper<>();
wrapper.like("spu_name","海信");
List<Spu> spus = spuMapper.selectList(wrapper);
```



##### List<T> selectByMap(@Param("cm") Map<String, Object> columnMap

将查询条件封装到Map中进行查询，key=value

```java
HashMap<String, Object> map = new HashMap<>();
map.put("spu_name","美的（Midea）JZY-Q216B 燃气灶");
List<Spu> spus = spuMapper.selectByMap(map);
```



##### List<Map<String, Object>> selectMaps(@Param("ew") Wrapper<T> queryWrapper)

根据条件构造器进行查询，将查询到的结果封装到一个个Map集合中，字段名=字段值的方式

```java
QueryWrapper<Spu> wrapper = new QueryWrapper<>();
wrapper.like("spu_name","美的");
List<Map<String, Object>> maps = spuMapper.selectMaps(wrapper);
for (Map<String, Object> map : maps) {
	System.out.println(map);
}
```



##### List<Object> selectObjs(@Param("ew") Wrapper<T> queryWrapper)

根据条件构造器进行查询，将所有查询到的数据的主键字段的值封装到Object中再放入到List里面

```java
QueryWrapper<Spu> wrapper = new QueryWrapper<>();
wrapper.like("spu_name","美的");
List<Object> objects = spuMapper.selectObjs(wrapper);
for (Object object : objects) {
	System.out.println(object);
}
```





##### Long selectCount(@Param("ew") Wrapper<T> queryWrapper)

查询出满足条件的数据一共有几条

```java
QueryWrapper<Spu> wrapper = new QueryWrapper<>();
wrapper.like("spu_name","美的");
Long l = spuMapper.selectCount(wrapper);
System.out.println(l);
```



##### boolean exists(Wrapper<T> queryWrapper)

判断是否存在数据满足这个查询条件

```java
QueryWrapper<Spu> wrapper = new QueryWrapper<>();
wrapper.like("spu_name", "美的")
		.and(i -> i.like("spu_title", "手机"));
boolean exists = spuMapper.exists(wrapper);
System.out.println(exists);
```

#### 分页

可以直接使用PageHelper,和myabtis没有任何区别

1.引入依赖

```xml
<!--分页插件-->
<dependency>
    <groupId>com.github.pagehelper</groupId>
    <artifactId>pagehelper</artifactId>
    <version>5.3.3</version>
</dependency>
```

2.再mybatis的配置文件中启用分页插件

```xml
<plugins>
    <!-- com.github.pagehelper为PageHelper类所在包名 -->
    <plugin interceptor="com.github.pagehelper.PageInterceptor">
        <property name="dialect " value="com.github.pagehelper.dialect.helper.MySqlDialect"/>
    </plugin>
</plugins>
```

3.开启分页查询

```java
// 进行分页 从第几页开始查询，查询几条记录
PageHelper.startPage(2,10);
List<Spu> spus = spuMapper.selectList(null);
```



#### 雪花算法生成id

#### 自动填充



### ResultMap

#### 基本使用

在此之前我们进行的都是单表查询，并且实体类的属性和数据库的字段是可以对应上的，但如果实体类的属性名和数据表的字段对应不上怎么办

```java
@Data
@TableName("Emp")
public class Emp {

	@TableId
	private Integer empNo;
	private String eName;
	private String job;
	private Integer mgr;
	private LocalDate hireDate;
	private Double sal;
	private Double comm;
	private Integer deptNo;
}
```

这个实体类的属性名和数据库的字段名对不上，查询报错

可以再xml文件中编写ResultMap自定义映射关系

```xml
<select id="selectList" resultMap="emp">
    select * from emp
</select>
<!--自定义一个resultMap编写映射关系-->
<resultMap id="emp" type="com.atstudy.domain.Emp">
    <result property="empNo" column="empno"/>
    <result property="eName" column="ename"/>
    <result property="mgr" column="mgr"/>
    <result property="job" column="job"/>
    <result property="hireDate" column="hiredate"/>
    <result property="comm" column="comm"/>
    <result property="sal" column="sal"/>
    <result property="deptNo" column="deptno"/>
</resultMap>
```

调用selectList方法

```java
List<Emp> emps = empMapper.selectList();
```



#### extends

我们可以使用extends继承映射关系

```xml
<select id="selectList" resultMap="empRight">
    select * from emp
</select>

<!--自定义一个resultMap编写映射关系-->
<resultMap id="empLeft" type="com.atstudy.domain.Emp" >
    <result property="empNo" column="empno"/>
    <result property="eName" column="ename"/>
    <result property="mgr" column="mgr"/>
    <result property="job" column="job"/>
</resultMap>

<resultMap id="empRight" type="com.atstudy.domain.Emp" extends="empLeft">
    <result property="hireDate" column="hiredate"/>
    <result property="sal" column="sal"/>
    <result property="comm" column="comm"/>
    <result property="deptNo" column="deptno"/>
</resultMap>
```

如果存在多个实体类映射到同一张表中，但是这个实体类存在不同的属性(拓展)，可以编写一个基础的resultMap，映射共同的字段



#### 一对一

```java
@Data
@TableName("Emp")
public class Emp {

	private Integer empno;
	private String ename;
	private String job;
	private Integer mgr;
	private LocalDate localdate;
	private Double sal;
	private Double comm;
	private Integer deptno;

}
```

```java
@Data
@TableName("dept")
public class Dept {

	private Integer deptno;
	private String dname;
	private String loc;

}
```



之前都是进行单表的crud，接下来看一下如何使用resultMap进行多表查询

```java
@Data
public class EmpWithDeptVo {

	private Integer empno;
	private String ename;
	private String job;
	private Integer mgr;
	private LocalDate hiredate;
	private Double sal;
	private Double comm;
	private Integer deptno;

	private Dept dept;
}

```

现在又一个需求，根据员工编号查询到带有部门信息的员工信息

```java
@Mapper
public interface EmpMapper {
	
	/**
	 * 根据员工编号查询出带有部门信息的员工信息
	 * @param empno
	 * @return
	 */
	EmpWithDeptVo selectEmpWithDeptVoByEmpno(@Param("empno") int empno);
}
```

```xml
<select id="selectEmpWithDeptVoByEmpno" resultType="com.atstudy.domain.vo.EmpWithDeptVo">
    select * from emp where empno = #{empno}
</select>
```

如果是这么实现，那么只能获取到基本的员工信息而无法获取带部门信息



但是员工信息中带有部门编号，现在编写一个方法：根据部门编号查询到部门信息

```java
@Mapper
public interface DeptMapper {

	/**
	 * 根据部门编号查询到部门信息
	 * @param deptno
	 * @return
	 */
	Dept selectByDeptno(@Param("deptno") int deptno);
}
```

```xml
<select id="selectByDeptno" resultType="com.atstudy.domain.Dept">
    select * from dept where deptno = #{deptno}
</select>
```



改进Emp接口中方法对应的xml文件

```xml
<select id="selectEmpWithDeptVoByEmpno" resultMap="empWithDeptVo">
    select * from emp where empno = #{empno}
</select>
<resultMap id="empWithDeptVo" type="com.atstudy.domain.vo.EmpWithDeptVo">
    <result property="empno" column="empno"/>
    <result property="ename" column="ename"/>
    <result property="mgr" column="mgr"/>
    <result property="job" column="job"/>
    <result property="hiredate" column="hiredate"/>
    <result property="comm" column="comm"/>
    <result property="sal" column="sal"/>
    <result property="deptno" column="deptno"/>
    <!--给dept属性进行映射  这个属性的类型是com.atstudy.domain.Dept
        column 根据上面查询到的deptno的值进行查询
        select 使用哪个方法进行查询
    -->
    <association property="dept" javaType="com.atstudy.domain.Dept"
                 column="deptno" select="com.atstudy.mapper.DeptMapper.selectByDeptno"/>
</resultMap>
```

**注意**: association标签中的select指定的方法可以是mybatisplus提供的通用方法

#### 一对多

现在要求：根据部门编号查询出这个部门的基本信息和这个部门下所有的员工信息



分析现有的实体类发现没有能够承载这些数据的实体类，创建vo对象

```java
/**
 * 带有员工列表的部门信息
 */
@Data
public class DeptWithEmpListVo {

	private Integer deptno;
	private String dname;
	private String loc;

	// 除了部门基本信息之外还存在员工列表
	private List<Emp> empList;
}
```

在持久层接口中编写方法

```java
/**
 * 根据部门编号查询到带有员工列表的部门信息
 * @param deptno
 * @return
 */
DeptWithEmpListVo selectDeptWithEmpListVoByDeptno(@Param("deptno") int deptno);
```

xml文件

```xml
<select id="selectDeptWithEmpListVoByDeptno" resultMap="deptWithEmpListVo">
    select * from dept where deptno = #{deptno}
</select>

<resultMap id="deptWithEmpListVo" type="com.atstudy.domain.vo.DeptWithEmpListVo">
    <result property="deptno" column="deptno"/>
    <result property="dname" column="dname"/>
    <result property="loc" column="loc"/>

    <!--
        property:给哪个属性赋值
        property: ofType是这个属性(集合)中的数据类型
        select: 使用哪个方法进行查询
        column: 根据上面的哪个字段的值进行查询
    -->
    <collection property="empList" ofType="com.atstudy.domain.po.Emp"
                select="com.atstudy.mapper.EmpMapper.selectEmpListByDeptno"
                column="deptno"/>
</resultMap>
```

注意这个select属性中用到的方法需要自己进行编写

#### 按需查询

Mybatis提供了按需查询，只有用到了关联的数才会进行查询，否则不查询

```xml
<resultMap id="deptWithEmpListVo" type="com.atstudy.domain.vo.DeptWithEmpListVo">
    <result property="deptno" column="deptno"/>
    <result property="dname" column="dname"/>
    <result property="loc" column="loc"/>

    <!--
        property:给哪个属性赋值
        property: ofType是这个属性(集合)中的数据类型
        select: 使用哪个方法进行查询
        column: 根据上面的哪个字段的值进行查询
    -->
    <collection property="empList" ofType="com.atstudy.domain.po.Emp"
                select="com.atstudy.mapper.EmpMapper.selectEmpListByDeptno"
                column="deptno"
                fetchType="lazy"/>
</resultMap>
```

当设置了fetchType为lazy时，只有真正使用到了empList，才会进行查询

```java
// 如果没有使用到这个Vo中的EmpList，那么这个EmpList是不会查询出来的
DeptWithEmpListVo vo = deptMapper.selectDeptWithEmpListVoByDeptno(10);
```

```java
// 只有真正使用到了这个empList，才会进行查询
DeptWithEmpListVo vo = deptMapper.selectDeptWithEmpListVoByDeptno(10);
System.out.println(vo.getEmpList());
```



**设置所有的关联查询为按需查询**

在mybatis配置文件中可以设置全局的延迟查询(按需查询)

```xml
<!--这里可以开启驼峰映射和日志-->
<settings>
    <setting name="mapUnderscoreToCamelCase" value="true"/>
    <setting name="logImpl" value="STDOUT_LOGGING"/>
    <!--开启全局 延迟查询-->
    <setting name="lazyLoadingEnabled" value="true"/>
</settings>
```





### 抽取公共的sql片段

之前在查询一张表中所有字段时一直使用的是select * ,但是不建议这么，最好是将所有需要查询的字段写出来

但是每次都将所有的字段写清楚太麻烦了，可以将这个需要重复使用的字段抽取到一个公共的sql片段中，需要使用的时候直接引入

```xml
<sql id="baseField">deptno,dname,loc</sql>
<select id="findAll" resultType="com.atstudy.domain.po.Dept">
    -- 在需要使用到这个公共片段中的sql语句时，可以使用include标签导入
    select <include refid="baseField"></include> from dept
</select>
```



### Mybatis缓存

mybatis缓存就是将之前查询到的信息放到了内存中，下次查询相同的东西就不会走MySQL而是直接

从缓存中拿到数据，从而提高效率

mybatis有一级缓存和二级缓存，默认开启了一级缓存(SqlSession级别)，二级缓存是Mapper级别的缓存(基本不用)



如何开启、关闭一级缓存

```xml
<!--关闭了一级缓存-->
<setting name="localCacheScope" value="STATEMENT"/>
```



#### 使用日志工具查看是否走了缓存

