## Maven

如果不使用maven，项目中的依赖不仅数量多、并且版本问题也不好管理，并且依赖之间可能存在某些关系，例如需要使用到A，但是A有需要用到B，并且B又需要用到C，那么就需要手动下载这些依赖，太麻烦，所以我们使用maven



注意低版本的idea在使用较高版本的maven时，可能会构建失败，这个时候如何解决: 降低maven的版本



### mven仓库

maven仓库的分支结构

- 本地仓库
- 远程仓库
  - 中央仓库
  - 私服
  - 其他公共库

**本地仓库**

​	顾名思义就是Maven下载下来的依赖在本地存储的地方，使用idea创建一个项目，默认先从本地仓库中寻找依赖，如果找到了那么就不会去其他地方查找

**远程仓库**

如果在本地仓库中查找不到这个依赖，那么maven默认从中央长裤中去查找并下载这个依赖到本地仓库中

`中央仓库`: 是最权威的仓库，是maven的默认仓库，里面的依赖项最全面也最权威，但是他是外国网址，下载较慢

`私服`：自己公司搭建的仓库，一般只能内网访问

`其他公共库`：例如阿里云、腾讯云之类的，它是国内的仓库，一般会每隔一段时间和中央仓库中的依赖同步一次，访问速度也快，一般下载好maven之后都需要修改默认的下载源





安装好maven之后注意要修改配置文件(Setting)中的两个地方

```xml
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 http://maven.apache.org/xsd/settings-1.0.0.xsd">

  <!--这是本地仓库的地址，默认是在c盘，当前登录用户的.m2目录下，最好改成和当时settings中的设置一样-->
  <localRepository>D:\maven</localRepository>
```

```xml
  <mirrors>
    <mirror>
      <id>aliyun</id>
      <name>Aliyun Maven Repository</name>
      <url>https://maven.aliyun.com/repository/public</url>
      <mirrorOf>central</mirrorOf>
    </mirror>
  </mirrors>
```

新建一个maven项目，社区版要求我们必须选择一个骨架，我们选择

org.apache.maven.archetypes:maven-archetype-quickstart （快速开始）

### maven项目目录规范

- src
  - main
    - java 		-java源代码文件
    - resources   这里是资源根路径，一般用于存放配置文件
  - test
    - java       -测试源代码文件
    - resources  测试资源库，一般也是用于存放配置文件

​	

- target (编译后自动生成)存放的是项目构建之后的文件和目录，比如jar包，war包，class文件等



### pom文件的配置

什么是pom

- pom表示项目对象模型，它是maven中工作的基本单位，是一个xml文件，通常是pom.xml,总之，pom.xml文件中包含了各种配置信息，注意，每个项目都只有一个pom.xml
- 项目的配置信息
  - **project**  工程的根标签
  - **modelVersion** pom模型的版本，maven2和3要求这里必须是4.0.0
  - **groupId** 这个是工程组的标识，在一个项目中是唯一的
  - **artifactId** 这个是工程的标识，也是工程的名称，通常groupId和artifactId定义了工程在仓库中的位置
  - **version** 这个是工程的版本号，用来区分不同的版本
  - **packaging** 定义了maven项目的打包方式，一般有JAR、WAR、EAR三种方式
  - **properties** 这个是工程的配置
  - **dependencies** 当前工程的依赖项

- 最低配的pom文件(最低要求)

  - ```xml
    <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
      xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
      <modelVersion>4.0.0</modelVersion>
      <groupId>org.example</groupId>
      <artifactId>mavne-01</artifactId>
      <version>1.0-SNAPSHOT</version>
    
    
    </project>
    
    ```

- **properties**

​	用于定义pom常量

```xml
<properties>
  <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
</properties>

```

上面这个常量可以在pom文件的任意位置使用${project.build.sourceEncoding}来引用

一个mavne项目在启动时往往会报错jdk错误，可以在propertoes里面设置

```xml
<properties>
  <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
  <!--设置当前项目的jdk版本为11-->
  <maven.compiler.source>11</maven.compiler.source>
  <maven.compiler.target>11</maven.compiler.target>
</properties>
```



- **dependencies**

​	这里可以管理项目中的依赖

```xml
<dependencies>
  <dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>3.8.1</version>
    <scope>test</scope>
  </dependency>
</dependencies>
```

这里表示当前项目中引入了junit依赖，如果需要引入更多依赖，只需要添加这个依赖的dependency配置信息即可

例如再添加一个mysql驱动

```xml
<dependencies>
  <dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>3.8.1</version>
    <scope>test</scope>
  </dependency>
    
  <dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.31</version>
  </dependency>
</dependencies>
```

**注意：在新增了依赖之后，需要点击idea右上角的刷新按钮，这个依赖才可以被加到当前项目中**



### 依赖的作用范围

- **compile**
  - 这个是默认范围，对项目中所有阶段都可以使用
- **provided**
  - 这个选项表示该依赖我们只在编译和测试阶段使用，在运行时这个依赖可以由jdk或者容器提供
- **runtime**
  - 表示编译时不需要，只有再运行时才会用到
- **test**
  - 表示这个依赖只在测试阶段起作用
- **system**
  - 作用范围和provided非常相似，但是要求显示指定一个系统路径的jar

​	









