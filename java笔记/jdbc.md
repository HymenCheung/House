### JDBC

#### 使用ResourceBundle解耦

准备一个配置文件jdbc.properties

```properties
driver=com.mysql.cj.jdbc.Driver
url=jdbc:mysql://localhost:3306/atstudy_mall
username=root
password=123456
```

注意:properties配置文件要放到src目录下(资源根路径)

*使用ResourceBundle读取到配置文件中的数据*

```java
// 先读取到配置文件中的数据库连接信息
ResourceBundle bundle = ResourceBundle.getBundle("jdbc");    // 如果是properties配置文件的话，只需要读取文件名
String driver = bundle.getString("driver");
String url = bundle.getString("url");
String username = bundle.getString("username");
String password = bundle.getString("password");
```

#### insert

*使用ResourceBundle操作jdbc代码*

```java
// 先读取到配置文件中的数据库连接信息
ResourceBundle bundle = ResourceBundle.getBundle("jdbc");           // 如果是properties配置文件的话，只需要读取文件名
String driver = bundle.getString("driver");
String url = bundle.getString("url");
String username = bundle.getString("username");
String password = bundle.getString("password");
// 编写jdbc代码
// 1.加载驱动
Class.forName(driver);
// 2.获取数据库连接
Connection connection = DriverManager.getConnection(url,username,password);
// 3.获取到statement操作对象
Statement statement = connection.createStatement();
// 4.准备并执行sql语句  往tb_user插入一条数据
String sql = "insert into tb_user values (2,'tom','123456')";
// 执行sql
boolean execute = statement.execute(sql);
```

#### update

```java
// 使用resourceBundle读取配置文件中的数据库连接信息
ResourceBundle bundle = ResourceBundle.getBundle("jdbc");
String driver = bundle.getString("driver");
String url = bundle.getString("url");
String username = bundle.getString("username");
String password = bundle.getString("password");
// 编写jdbc代码
Connection connection = null;
Statement statement = null;
try {
    // 1.加载驱动
    Class.forName(driver);
    // 2.获取数据库连接
    connection = DriverManager.getConnection(url,username,pass
    // 3.获取statement操作对象
    statement = connection.createStatement();
    // 4.准备更新语句
    String sql = "update tb_user set username='aaa' where id=2
    int i = statement.executeUpdate(sql);           // 这个i是受影响
    System.out.println(i == 1 ? "更新成功" : "更新失败");
} catch (ClassNotFoundException | SQLException e) {
    e.printStackTrace();
}finally {
    // 资源的关闭顺序是从小到大
    if(statement != null){
        try {
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    if(connection != null){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
```

#### delete

```java
// 1.加载驱动
Class.forName(driver);
// 2.获取到数据库连接
connection = DriverManager.getConnection(url,username,password);
// 3.获取到statement操作对象
statement = connection.createStatement();
// 4.准备并执行sql
String sql = "delete from tb_user where id = 2";
boolean execute = statement.execute(sql);
```

#### 事务

```java
Connection connection = null;
Statement statement = null;
try {
    Class.forName(driver);
    connection = DriverManager.getConnection(url, username, passwor
    statement = connection.createStatement();
    // 转账操作，张三给李四转5000
    String sql1 = "update account set balance = 5000 where username
    String sql2 = "update account set balance = 15000 where usernam
    // 开启事务
    connection.setAutoCommit(false);
    statement.execute(sql1);
    // 假设出现问题
    FileInputStream fis = new FileInputStream("");
    statement.execute(sql2);                // 这个格步骤是原子操作，需要同时成功或者失
    // 提交事务
    connection.commit();
} catch (ClassNotFoundException | SQLException | FileNotFoundExcept
    e.printStackTrace();
    // 回滚事务
    try {
        connection.rollback();
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
}finally {
    if(statement != null){
        try {
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    if (connection != null){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
```

#### query

```java
Class.forName(driver);
Connection connection = DriverManager.getConnection(url, username, password);
Statement statement = connection.createStatement();
String sql = "select * from account";
// 返回一个查询结果集对象
ResultSet resultSet = statement.executeQuery(sql);
while (resultSet.next()){
    System.out.println(resultSet.getString(1));
    System.out.println(resultSet.getString(2));
    System.out.println(resultSet.getString(3));

    /*
    while (resultSet.next()){
            // 除了可以获取到指定索引的字段值，还可以通过指定字段的名称来获取值
            System.out.println(resultSet.getString("id"));
            System.out.println(resultSet.getString("username"));
            System.out.println(resultSet.getString("balance"));
        }
    */

}
```

#### PreparedStatement

```java
// 1.加载驱动
Class.forName(driver);
// 2.获取到数据库连接
Connection connection = DriverManager.getConnection(url, username, password);
// 3.准备sql语句
String sql = "select * from admin where admin_name = ? and admin_pass = ?";         // 这里使用？做占位符
// 4.获取到预编译的statement对象
PreparedStatement ps = connection.prepareStatement(sql);             // 会对这条sql进行预编译
// 5.给？设置值
ps.setString(1,"tom");
ps.setString(2,"456");      
//ps.setString(2,"456 or '1' = '1'");         // 不会出问题，是安全的，不会发送sql注入      
ResultSet resultSet = ps.executeQuery();
while (resultSet.next()){
    System.out.println(resultSet.getString(1));
    System.out.println(resultSet.getString(2));
    System.out.println(resultSet.getString(3));
    System.out.println(resultSet.getString(4));
}
```

### ORM（Object Relational Mapping）

对象关系映射

**ORM是对象关系映射（Object-Relational Mapping）的缩写。它是一种数据库编程技术和设计模式，orm的主要目标是将面向对象的编程语言中的对象模型与关系数据库中的表结构进行映射，从而实现对象到关系数据的转换和交互。**

ORM的目标是解决关系型数据库和面向对象编程语言之间的障碍。它使开发人员能够通过面向对象的方式进行数据库操作，而不必关心底层的SQL语句。ORM工具会自动将对象的属性映射到数据库的表和字段上，并提供一系列的操作方法，如增删改查。

ORM有许多优点。首先，它提供了面向对象编程的便利性，使开发人员能够使用面向对象的思维和技术来操作数据库。其次，ORM可以提高开发效率，减少手写SQL语句的工作量。此外，ORM还可以屏蔽不同数据库之间的差异，并提供数据库迁移和数据持久化等功能。

常见的ORM框架包括Hibernate、Entity Framework、Django的ORM等。它们是在不同编程语言和开发平台上实现的ORM技术，为开发人员提供了方便、高效的数据库操作方式

