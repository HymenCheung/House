### 三层目录结构和MVC

#### mvc

- Model 模型
  
  - 承载数据用的Bean，就是Java对象，例如实体类、Service、Dao层对象

- View 视图
  
  - 页面：JSP、为用户提供界面与用户进行交互

- Controller
  
  - 将用户的请求转发给对应的Model进行处理，将Model的计算结果响应给用户，常见的有Servlet、controller

#### 三层架构

- 视图层 View，又称之为Web层，Controller
  
  - 接收用户提交的请求的代码

- 服务层 Service
  
  - 系统的业务逻辑

- 持久层 Dao
  
  - 操作数据库
  
  ![](D:\Java32\笔记\Java\JavaWeb\img\mvc.png)

#### 开发中的目录规范

- java（或者src）
  
  - controller    存放控制器
  
  - service 存放业务接口
    
    - impl    存放业务接口的实现类
  
  - dao 存放数据访问层接口
    
    - impl    存放数据访问层接口实现类
  
  - domain 存放实体类
  
  - filter 存放过滤器
  
  - listener 存放监听器
  
  - config 存放配置类
  
  - util 存放工具类

- resource 资源根目录

#### 连接池

使用连接池可以避免频繁创建关闭数据库连接

常用的连接池：C3P0、Druid、Dbcp

### 使用DBUtil完成单表的增删改查

#### 导入依赖

```xml
<dependencies>
  <!--servlet依赖-->
  <dependency>
    <groupId>javax.servlet</groupId>
    <artifactId>javax.servlet-api</artifactId>
    <version>4.0.1</version>
  </dependency>
  <!--数据库依赖-->
  <dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.29</version>
  </dependency>
  <!--连接池依赖-->
  <dependency>
    <groupId>org.apache.commons</groupId>
    <artifactId>commons-dbcp2</artifactId>
    <version>2.9.0</version>
  </dependency>
  <!--apache旗下的数据库操作工具-->
  <dependency>
    <groupId>commons-dbutils</groupId>
    <artifactId>commons-dbutils</artifactId>
    <version>1.7</version>
  </dependency>

    <!--lombok-->
    <dependency>
      <groupId>org.projectlombok</groupId>
      <artifactId>lombok</artifactId>
      <version>1.18.24</version>
      <scope>provided</scope>
    </dependency>
</dependencies>
```

#### 编写配置

编写连接池的配置信息

```properties
driverClassName=com.mysql.cj.jdbc.Driver
url=jdbc:mysql://localhost:3306/atstudy_mall
username=root
password=123456
# 这个不上注释，自己自行删除，initialSize：连接池创建时的连接数量
initialSize=2       
# maxActive 连接池同一时间内最多能够分配的活动的连接数量,自行删除这个注释
maxActive=50
```

编写数据库工具类

```java
public class DataSourceUtil {
    private static DataSource dataSource;

    static {
        // 读取道Resource目录下的配置文件，以流的形式返回
        InputStream stream = DataSourceUtil.class.getClassLoader().getResourceAsStream("db.properties"); //修改配置文件名
        Properties properties = new Properties();
        try {
            properties.load(stream);
            dataSource = BasicDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("初始化dbcp失败");
        }
    }


    public static DataSource getDataSource(){
        return dataSource;
    }

}
```

#### commons-dbutils

是apache旗下的一个数据库操作工具，他有两个核心的类

##### QueryRunner

- 查询执行器，提供对sql进行操作的api

- update() 可执行增删改操作

- query(Connection, String, ResultSetHandler, Object...)可以执行查询操作

##### ResultSetHandler

- BeanHandler    将结果集的第一行数据封装到对应的JavaBean实例中

- BeanListHandler 将结果集中的每一行数据都封装到对应的JavaBean中，存到list里面 

- MapHandler 将结果集中的第一行数据封装道一个map对象中，key是列名，value是对应的值

- MapListHandler 将结果集的每一行数据都封装成一个map对象，放到list中

- ScalarHandler 结果集中第一行数据指定列的值

#### 开始增删改查操作

1.创建实体类

```java
/**
 * Admin数据表对应的实体类
 */
public class Admin {
    private Integer adminId;
    private String adminName;
    private String adminPass;
    private String adminNickname;
    // 自行生成其他方法
}
```

##### BeanHandler

编写Dao层

```java
public class AdminDao {
    private QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDataSource());

    // 开启驼峰映射
    private BeanProcessor bean = new GenerousBeanProcessor();
    private RowProcessor processor = new BasicRowProcessor(bean);

    /**
     * 根据id查询用户
     * @param id 用户id
     * @return
     */
    public Admin findById(Integer id){
        // 准备sql
        String sql = "select * from admin where admin_id = ?";
        Admin admin = null;
        try {
            /*
            *   sql:要执行的sql语句
            *   new BeanHandler<>(Admin.class) 将结果及中的第一条数据封装成Admin对象
            *   id:需要查询的用户id
            * */
            admin = queryRunner.query(sql, new BeanHandler<>(Admin.class,processor), id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return admin;
    }
}
```

编写service接口和实现类

```java
public interface AdminService {
    Admin findById(Integer id);
}
```

```java
/**
 * Admin的业务逻辑层的实现类
 */
public class AdminServiceImpl implements AdminService {

    // 准备AdminDao对象方便调用
    private AdminDao adminDao = new AdminDao();

    @Override
    public Admin findById(Integer id) {
        return adminDao.findById(id);
    }
}
```

编写Controller层

```java
@WebServlet("/admin")
public class AdminServlet extends HttpServlet {


    // 注入service实现类对象方便调用
    AdminService adminService = new AdminServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 从请求参数中获取到调用哪一个方法
        String method = req.getParameter("method");
        if(method.equals("findById")){
            // 如果是调用的findById方法则从请求参数中获取到用户id
            String id = req.getParameter("id");
            // 调用service实现了的findById方法获取到admin对象
            Admin admin = adminService.findById(Integer.valueOf(id));
            System.out.println(admin);
        }


    }
}
```

##### BeanListHandler

编写dao层

```java
/**
 * 查询所有用户
 * @return
 */
public List<Admin> list(){
    String sql = "select * from admin";
    List<Admin> query = null;
    try {
        query = queryRunner.query(sql, new BeanListHandler<>(Admin.class, processor));
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return query;
}
```

编写service和实现类

```java
List<Admin> list();
```

```java
public List<Admin> list() {
    return adminDao.list();
}
```

编写controller

```java
if(method.equals("list")){
    List<Admin> list = adminService.list();
    for (Admin admin : list) {
        System.out.println(admin);
    }
}
```

##### MapHandler

编写dao层

```java
/**
 * 根据id查询用户
 * @param id 用户id
 * @return 返回一个map对象
 */
public Map<String,Object> findByIdWithMap(Integer id){
    String sql = "select * from admin where admin_id = ?";
    Map<String, Object> query = null;
    try {
        query = queryRunner.query(sql, new MapHandler(), id);
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return query;
}
```

编写service

```java
Map<String,Object> findByIdWithMap(Integer id);
```

```java
public Map<String, Object> findByIdWithMap(Integer id) {
    return adminDao.findByIdWithMap(id);
}
```

编写controller

```java
if(method.equals("findByIdWithMap")){
    String id = req.getParameter("id");
    Map<String, Object> byIdWithMap = adminService.findByIdWithMap(Integer.valueOf(id));
    System.out.println(byIdWithMap);
}
```

##### MapListHandler

编写dao层

```java
/**  MapListHandler
     * 查询所有数据，将所有数据放到一个个map集合中，最后全部放到一个list返回
     * @param num
     * @return
     */
    public List<Map<String,Object>> findListByDeptNo(double num){
        String sql = "select * from emp where deptno = ?";
        List<Map<String, Object>> list =null;
        try {
             list = queryRunner.query(sql, new MapListHandler(), num);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
```

编写service和实现类

```java
 /**  MapListHandler
     * 查询所有数据，将所有数据放到一个个map集合中，最后全部放到一个list返回
     * @param num
     * @return
     */
    public List<Map<String,Object>> findListByDeptNo(double num);
```

```java
@Override
    public List<Map<String, Object>> findListByDeptNo(double num) {
        return empDao.findListByDeptNo(num);
    }
```

编写controller

```java
public List<Map<String,Object>> findListByDeptNo(double num){
        return empService.findListByDeptNo(num);
    }
```



##### ScalarHandler

编写dao层

```java
/**  new ScalarHandler()
     * 根据id查询指定列的值
     * @param id
     * @return
     */
    public Object getNameByEmpNo(double id){
        String sql = " select ename from emp where empno = ?";
        Object value = null ;
        try {
            value = queryRunner.query(sql, new ScalarHandler(), id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return value;
    }
```

编写service和实现类

```java
/**  new ScalarHandler()
     * 根据id查询指定列的值
     * @param id
     * @return
     */
    public boolean getNameByEmpNo(double id);
```

```java
@Override
    public boolean getNameByEmpNo(double id) {
        return empDao.getNameByEmpNo(id) != null ? true : false;
    }
```

编写controller

```java
public String getNameByEmpNo(double id){
        return empService.getNameByEmpNo(id) ? "查找成功" : "查找失败";
    }
```



##### insert

编写dao层

```java
/**
     * 将一个员工对象插入到数据库中
     * @param emp
     * @return
     */
    @Override
    public int insert(Emp emp) {
        //准备一个sql
        String sql = "insert into emp (empno,ename,job,mgr,hiredate,sal,comm,deptno) values (?,?,?,?,?,?,?,?)";
        int execute = 0;
        try{
            execute = queryRunner.execute(sql,
                    emp.getEmpno(),
                    emp.getEname(),
                    emp.getJob(),
                    emp.getMgr(),
                    emp.getHiredate(),
                    emp.getSal(),
                    emp.getComm(),
                    emp.getDeptno());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return execute;
    }
```

编写service和实现类

```java
/**
     * 插入一个员工数据
     * @param emp
     * @return
     */
    boolean insert(Emp emp);
```

```java
@Override
    public boolean insert(Emp emp) {
        return empDao.insert(emp) > 0 ? true : false;
    }

```

编写controller

```java
public String insert(Emp emp){
        return empService.insert(emp) ? "添加成功" : "添加失败";
    }
```



##### update

编写dao层

```java
/**
     * 更新一条数据
     * @param emp
     * @return  受影响的记录条数
     */
    @Override
    public int update(Emp emp) {
        String sql = "update emp set ename = ?,job = ?,mgr = ?,hiredate = ? ,sal = ? , comm = ?, deptno = ?,where empno = ? ";
        int execute = 0;
        try {
            execute = queryRunner.execute(sql, emp.getEname(),
                    emp.getJob(),
                    emp.getMgr(),
                    emp.getHiredate(),
                    emp.getSal(),
                    emp.getComm(),
                    emp.getDeptno(),
                    emp.getEmpno());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return execute;
    }
```

编写service和实现类

```java
/**
     * 更新一条数据
     * @param emp
     * @return
     */
    boolean update(Emp emp);
```

```java
@Override
    public boolean update(Emp emp) {
        return empDao.update(emp) > 0 ? true : false;
    }
```

编写controller

```java
public String update(Emp emp){
        return empService.update(emp) ? "修改成功" : "修改失败";
    }
```



##### delete

编写dao层

```java
/**
     * 根据员工编号删除一条数据
     * @param id
     * @return  受影响的记录条数
     */
    @Override
    public int delete(double id) {
        String sql = "delete from emp where empno = ?";
        int execute = 0;
        try {
            execute = queryRunner.execute(sql, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return execute;
    }
```

编写service和实现类

```java
/**
     * 根据员工编号删除一条数据
     * @param id
     * @return
     */
    boolean delete(double id);
```

```java
@Override
    public boolean delete(double id) {
        return empDao.delete(id) > 0 ? true : false;
    }

```

编写controller

```java
public String delete(double id){
        return empService.delete(id) ? "删除成功" : "删除失败";
    }
```

