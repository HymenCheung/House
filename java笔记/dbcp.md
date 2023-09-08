连接池

获取到数据库的连接

每一次操作数据库都需要手动打开关闭资源，资源打开和释放很频繁

### 数据库连接池

进行数据库操作的时候，可以直接将这个连接池中的数据库连接拿来用

Druid

C3P0

DBCP

dbcp 是一个开源的数据库连接池 是别人写好的代码

先将dbcp依赖(别人的代码导入进来)

这个数据库连接是从数据库连接池中获取的，它在关闭之后会自动回到数据库连接池

### 下载依赖

在没有maven的情况下使用dbcp连接池需要很多依赖，自己手动下载

[Maven Repository: commons-logging » commons-logging » 1.2 (mvnrepository.com)](https://mvnrepository.com/artifact/commons-logging/commons-logging/1.2)

[Maven Repository: org.apache.commons » commons-pool2 » 2.9.0 (mvnrepository.com)](https://mvnrepository.com/artifact/org.apache.commons/commons-pool2/2.9.0)

[Maven Repository: org.apache.commons » commons-dbcp2 » 2.9.0 (mvnrepository.com)](https://mvnrepository.com/artifact/org.apache.commons/commons-dbcp2/2.9.0)

自己到maven 的中央仓库下载jar包



### 导入依赖

在idea中使用 ctrl+alt+shift+s 打开这个工程的工程结构 

点击libraries 

点击+

选择 Java

找到自己下载的jar包，依次导入(不要一次性选中三个，可能会有问题)



### 编写dbcp的配置文件

```properties
driver=com.mysql.cj.jdbc.Driver  # 驱动类名
url=jdbc:mysql://localhost:3306/atstudy  # 数据库连接 URL
username=root  # 用户名
password=123456  # 密码

initialSize=5  # 连接池初始化大小
maxTotal=50  # 最大连接数
maxIdle=10  # 最大空闲连接数
minIdle=3  # 最小空闲连接数

maxWaitMillis=5000  # 获取连接的超时时间
```

这些配置的意思并不复杂，不懂得直接问ai，**不要再这里面写注释**

### 编写DBUtil工具类

```java
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DBUtil {

    static String driver;
    static String url;
    static String username;
    static String password;
    static String initialSize;

    static BasicDataSource dataSource;

    static {
        // 在静态代码块中读取配置文件
        ResourceBundle bundle = ResourceBundle.getBundle("dbcp-config");

        driver = bundle.getString("driver");
        url = bundle.getString("url");
        username = bundle.getString("username");
        password = bundle.getString("password");
        initialSize =bundle.getString("initialSize");

        dataSource = new BasicDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setInitialSize(Integer.parseInt(initialSize));

    }


    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

}

```

### 使用字节码文件操作数据库

增加数据

```java 

```

删除数据

```java

```

修改数据

```java

```

查找数据

```java

```

