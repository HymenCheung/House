## Mysql

### 常用操作

登录数据库

```sql
# 打开mysql安装目录bin目录下的dos窗口
mysql -uroot -p
# 输入密码
```

查看所有数据库

```sql
SHOW DATABASES;
```

切换到指定数据库

```sql
USE `数据库名`;
```

查看一个数据库中有哪些表

```sql
SHOW TABLES;
```

查看其他数据库中的表

```sql
SHOW TABLES FROM `库名`;
```

查看表结构

```SQL
DESC `表名`;
```

查看MySQL版本

```sql
SELECT VERSION();
```

### 数据类型

#### 整型

**tinyint**

1个字节，取值范围-128~127

**smallint**

2个字节，-32768~32767

**mediumint**

3个字节

**int**

4个字节

**bigint**

8个字节

#### 浮点型

**float**

单精度浮点型，4个字节，8位精度

**double**

双精度浮点型，8个字节，16位精度

double(m,d)        m表示十进制的总个数，d代表小数点后面的个数，d不能大于m

#### 定点型

decimal，存放精确值

#### 文本

**char**

固定长度文本，char(n) n最多是255

**varchar**

动态长度文本，varchar(n)  n最多是65535

**tinytext**

动态长度文本，最多255字符

**text**

动态长度，最多65535字符

**mediumtext**

动态长度文本，最对2^24 -1 个字符

**longtext**

动态长度文本，最多2^32 - 1 个字符

#### 二进制型

**tinyblob**

二进制数据，最多存放255个字节

**blob**

二进制数据，最多存放65535个字节

**mediumblob**

二进制数据，最多存放2^24 - 1个字节

**longblob**

二进制数据，最多存放2^32 - 1个字节

#### 时间日期类型

**date**

日期类型  例如2022-10-17

**time**

时间类型 例如 16:32:001

**datetime**

时间日期类型 例如  2022-10-17 16:34:001

**timestamp**

时间戳类，从1970年1月1日 零时零分零秒到现在的毫秒数

### SQL分类

*sql语句分为四大类*

DDL:数据定义语言（数据库、表的创建、修改、删除）

<font color = #faa>DML:数据操作语言（表的增、删、改）</font>

<mark>DQL:数据查询语言（表的查询）</mark>

DCL:数据控制语言（用户访问权限、用户密码控制、用户创建、修改...）

#### DDL

**sql不区分大小写**

##### CREATE

创建数据库

```SQL
# 格式
CREATE DATABASE `数据库名` CHARSET `字符编码(一般是utf8)`;

# 例子
create database `atstudy` charset=`utf8`;
```

在 SQL 中，可以使用 `SOURCE` 命令来执行从外部文件加载并执行 SQL 脚本。`SOURCE` 命令通常用于在命令行界面或 SQL 客户端中导入和执行大型 SQL 脚本或批处理任务。

以下是使用 `SOURCE` 命令的一般语法：

```sql
#切换到指定数据库
SOURCE 文件路径   #后面不加分号；
#其中，`文件路径` 是指要执行的包含 SQL 脚本的文件的完整路径或相对路径。
SOURCE H:\sql\emp.sql   
```

创建数据表

```sql
# 格式
CREATE TABLE `表名`(
    `字段名1` 数据类型 约束,
    `字段名2` 数据类型,
    ......
);

create table `user`(
    `user_id` int primary key auto_increment,
    `user_name` varchar(55) not null,
    `user_age` smallint not null
);
```

##### ALTER

修改数据库

```sql
# 格式
ALTER DATABASE `数据库名` CHARSET `编码格式`;

# 例子
alter database `atstudy` charset `gbk`;
```

修改表

```sql
# 格式
ALTER TABLE `表名` ADD `列名（字段名）` 数据类型;
# 例子
alter table `user` add `addr` varchar(255);

# 重命名一个字段
ALTER TABLE CHANGE `旧字段名` `新字段名` 新的数据类型;

# 删除一列
ALTER TABLE `表名` DROP `列名`;
```

##### DROP

删除表

```SQL
DROP TABLE `表名`;
```

删除数据库

```SQL
DROP DATABASE `表名`;
```

#### 约束

是对表中数据的一些规定、规范

##### 约束分类

###### 非空约束( NOT NULL)

```sql
# 设置了非空约束的列数据不能为空
ALTER TABLE `表名` MODIFY `字段名` 数据类型 NOT NULL;
```

###### 唯一约束(UNIQUE)

社长唯一约束的列数据不能重复

```SQL
ALTER TABLE `表名` MODIFY `字段名` 数据类型 UNIQUE;
```

###### 默认约束(DEFAULT)

不给列赋值，会有一个默认值

```sql
ALTER TABLE `表名` MODIFY `字段名` 数据类型 DEFAULT 默认值;
```

###### 主键约束(PRIMARY KEY)

其实就是非空加上唯一，用来保证数据的唯一性，还可以设置自动增长的功能

```sql
ALTER TABLE `表名` MODIFY `字段名` 数据类型 PRIMARY KEY AUTO_INCREMENT;
```

###### 外键约束(foreign key)

```tex
tb_class

class_no       class_name         stu_no             stu_name        id
------------------------------------------------------------------------
101                一班                1                    张三
102                二班                1                    李四
101                一班                2                    王五
102                二班                2                    赵六




tb_class

class_no                class_name
------------------------------------
101                            一班
102                            二班



tb_stu

stu_id                stu_name            cno（班级编号）
-------------------------------------------------------
    1                张三                    101
    2                李四                    102
    3                王五                    101
```

```sql
# 主表  设置级联更新可以同步字表数据
create table `tb_class`(
    class_no smallint primary key auto_increment,
    class_name varchar(55)
);


# 从表   只能先删除从表在删除主表
create table `tb_stu`(
    stu_id int primary key auto_increment,
    stu_name varchar(55) not null,
    cno smallint,
    foreign key(cno) references tb_class(class_no)            # 外键，将cno与班级表的class_no关联起来
);
```

#### DML

##### INSERT

添加一条数据

now()函数可以获取到当前事件

```sql
# 格式
INSERT INTO `表名`
    (列名1,列名2) 
VALUES 
    (列名1的数据,列名2的数据);

insert into `tb_class`(class_no,class_name) 
values (101,'一年一班');
```

添加多条数据

```sql
# 格式
INSERT INTO `表名` 
    (列名1,列名2,...) 
VALUES
    (列名1的数据,列名2的数据),
    (列名1的数据,列名2的数据),
    ...


insert into `tb_class`
    (class_no,class_name)
values
    (102,'一年二班'),
    (201,'二年一班'),
    (202,'二年二班');
```

##### UPDATE

修改数据

```sql
# 格式
UPDATE `表名`
SET
列名=数据,
列名=数据,
...
where
修改条件


UPDATE `tb_class`
SET
    class_name='一班'
where
    class_no = 101;
```

##### DELETE

删除数据

```sql
# 格式
DELETE FROM `表名`;            # 删除了表中所有数据
DELETE FROM `表名` WHERE 删除条件;
```

#### DQL

##### SELECT

查询语句

查询所有信息

```sql
# 查询一张表中的所有信息
SELECT * FROM `表名`;
```

查询指定列

```sql
SELECT 列名1,列名2,列名3,... FROM `表名`;
```

查询指定列并起别名

```sql
SELECT 列名 as `别名` FROM `表名`;            # as可以省略
```

参与数学运算

```sql
SELECT class_no+200 FROM `表名`;
# 例子
select class_no+200 as class_no from tb_class;
```

去除重复数据

```sql
# 格式
SELECT distinct 想要去除重复数据的列 from `表名`;
```

##### WHERE

**=  等于**

```sql
# 查询名字是KING的员工
select * from emp where ename='KING';
# 查询薪资是800的员工
```

**<> 获取!=  不等于**

```sql
# 查询部门不是10的员工
select * from emp where deptno != 10;
```

**< 小于**

```sql
# 查询薪资小于3000 的员工
select * from emp where sal < 3000;
```

**> 大于 **

```sql
# 查询薪资大于3000 的员工
select * from emp where sal > 3000;
```

**>=  <=   大于等于、小于等于**

**between and   闭区间，可以取到两边的值**

```sql
# 使用格式  一定要保证小的数在左边
select * from `表名` where 字段名 between xx and xxx;

# 查询薪资在900到2100之间的员工
select * from emp where sal between 800 and 2100;
```

**is null 为空  is not null 不为空**

```sql
# 查询奖金不为空的员工
select * from emp where comm is not null;
```

**not 取反**

**and 并且**

```sql
# 查询部门是20并且薪资大于1200的员工
select * from emp where deptno = 20 and sal > 1200;
```

**or   或者**

```sql
# 查询薪资大于等于3000或者部门是10的员工
select * from emp where sal >= 3000 or deptno = 10;
```

**not in    不在其中**

```sql
# 查询薪资不是3000或者5000的员工
select * from emp where sal not in(3000,5000);
```

**like   模糊查询**

%表示任意个数字符

_表示任意一个字符

```sql
# 查询名字中有K的员工
select * from emp where ename like '%K%';
# 查询名字以K开头的员工
select * from emp where ename like '_k%';
```

##### ORDER BY

排序,默认是升序

```sql
# 格式
SELECT * FROM `表名` ORDER BY 想要排序的字段;

# 查询所有员工，按照薪资排序
select * from emp order by sal,ename;            # 可以多个字段联合排序,第一个字段比较不出就比较第二个字段
# 查询所有员工，按照薪资降序排序
select * from emp order by sal desc;
```

##### LIMIT

分页

```sql
# 格式
SELECT * FROM `表名` LIMIT 起始索引,查询的记录数;
# 查询薪资前5的员工
select * from `emp` order by sal desc limit 0,5;
```

##### 单行处理函数

**lower   转小写**

```sql
select lower(ename) from `emp`;
```

**upper 转大写**

**length   取长度**

```sql
# 格式
LENGTH(字段)
select length(ename) from `emp` where ename = 'KING';
```

**substr   取子串**

```sql
# 格式  注意，这里的起始是从1开始
SUBSTR(被截取的字符串,起始下标,截取长度);
select substr(ename,1,3) from emp;
```

**trim 去除空格**

```sql
# 格式
TRIM(想要去除前后空格的字符串);
select * from emp where ename = trim(' KING ');
```

**date_format 日期格式化**

```sql
# 格式
DATE_FORMAT(字段,'日期格式');                        # %Y：年  %m: 月  %d: 日    %h: 时  %i: 分   %s：秒 
select DATE_FORMAT(hiredate,'%Y/%m/%d') as date from emp;
```

**round   四舍五入**

```sql
# 格式
ROUND(数字,想要保留的小数位)     四舍五入  0表示保留整数位，-1表示保留到十位，-2保留到百位
SELECT round(123.456,1) as tmp from emp;   #tem 为列
```

**rand   生成随机数**

```sql
select rand() from emp;
```

**ifnull**

```sql
# null参与运算的结果也是null
# 计算员工的每月薪资
select (sal + comm) as sal from emp;            # 有问题，奖金为null薪资全部变成了null
# ifnull 用法            ifnull(字段,如果数据为空，实际参与运算的值)
select (sal + ifull(comm,0)) as sal from emp;
```

##### 聚合函数

又被称作分组函数，使用的时候会自动忽略null

**sum  求和**

```sql
# 计算所有员工薪资的和
select sum(sal) as totalSal from emp;
```

**max  求最大值**

```sql
# 求出员工的最高薪资
select max(sal) as MaxSal from emp;
```

**min  最小值**

```sql
# 求出员工的最低薪资
select min(sal) as MinSal from emp;
```

**avg 平均数**

```sql
# 求出员工的平均薪资
select avg(sal) as AvgSal from emp;
```

**count 计数**

```sql
# 查询这张表有条张数据
select count(*) from emp;
# 查询有奖金的员工有几个
select count(comm) from emp;        # 只有四条，忽略null
```

```sql
# CASE WHEN 表达式分组
SELECT 
    c.id AS 课程号,
    c.name AS 课程名称,
    COUNT(CASE WHEN s.grade >= 85 AND s.grade <= 100 THEN s.grade END) AS `100-85`,
    COUNT(CASE WHEN s.grade >= 70 AND s.grade < 85 THEN s.grade END) AS `85-70`,
    COUNT(CASE WHEN s.grade >= 60 AND s.grade < 70 THEN s.grade END) AS `70-60`,
    COUNT(CASE WHEN s.grade < 60 THEN s.grade END) AS `<60`
FROM
    course c
LEFT JOIN
    score s ON c.id = s.course_id
GROUP BY
    c.id, c.name;
```



##### GROUP BY

分组函数，对数据分组操作

```sql
# 查询所有员工，对部门进行分组
select * from emp group by deptno;

#  找出每个部门的最高薪资
select deptno,max(sal) from emp group by deptno;

# 聚合(分组)函数不能直接出现在where子句后面，只能在group by 后面执行
# 查询每个部门的员工的薪资，要求查询出大于部门的平均薪资
select sal,deptno from emp where sal > avg(sal) group by deptno;            # 因为where在分组之前执行,这个时候还没有分组


# 查询所有员工，对部门进行分组,根据工作岗位再次分组
select * from emp group by deptno,job;        # 有问题，真实查询不出所有数据，只是查询出参与分组的字段相关的数据

# 找出每个岗位的最高薪资
select job,max(sal) from emp group by job;

# 查询出每个部门中不同岗位的最高薪资
select deptno,job,max(sal) as maxSal from emp group by deptno,job;


#  select 后面只能跟参与分组的字段，和聚合（分组）函数
select ename,deptno,job,max(sal) as maxSal from emp group by deptno,job;
```

**sql 语句有执行顺序**

```sql
1.FROM
2.WHERE
3.GROUP BY
4.SELECT
5.ORDER BY
6.LIMIT
```

##### HAVING

having子句可在分组之后再次过滤数据

```sql
# 查询出每个部门不同岗位的最高薪资，将大于等于3000的输出
select deptno,job,max(sal) from emp group by deptno ,job having max(sal)>=3000;
# 先使用where过滤数据，在进行分组
select deptno,job,max(sal) from emp where sal >= 3000 group by deptno,job;

# 查询出每个部门的平均薪资，将平均薪资大于等于2000的输出 (使用where就有问题)
select deptno,avg(sal) as avgSal from emp  group by deptno having avgSal > 2000;
```

#### 多表查询

```sql
# 想查询员工名名字和部门名称
select ename,dname from emp,dept;            # 会出现笛卡尔积现象，得到一个乘积
# 加上筛选条件
select ename,dname from emp,dept where emp.deptno = dept.deptno;            # 笛卡尔积无法避免，只是将符合条件的显示了出来
# 可以给表起别名select ename,dname from emp,dept where emp.deptno = dept.deptno;
select e.ename,d.dname from emp e,dept d where e.deptno = d.deptno;

# 查询员工的薪资等级
select e.sal,s.grade from emp e,salgrade s where e.sal between s.losal and s.hisal;        # 这是sql92写法
```

##### 内连接

只有在满足匹配条件的情况下才会输出

```sql
# 格式   sql99写法
SELECT 
    ...
FROM
    `表名`
INNER JOIN 
    `表名`
ON
    查询条件
# 还有多张表
JOIN
    `表名`
ON
    查询条件
...



# 查询员工的薪资等级
select 
    e.sal,s.grade
from 
    emp e
inner join             # 这个inner可以省略
    salgrade s
on
    e.sal between s.losal and s.hisal;


# 查询员工的名字和他的领导名字
select 
    e1.ename as name,e2.ename as mgrName
from
    emp e1        # 将它视作员工表
join
    emp e2        # 将它视作领导表
on
    e1.mgr = e2.empno;            # 员工的领导编号是领导的员工编号
```

##### 外连接

```sql
# 查询所有部门下的所有员工
select 
    d.deptno,d.dname,e.ename
from
    emp e
join 
    dept d
on
    d.deptno = e.deptno;

# 还有一个部门没有被查询出来，但是它实际上是存在的，这也是内连接的不足，只有满足筛选条件会被查出来
```

###### 左外连接

```sql
# 查询所有部门下的所有员工
select 
    d.deptno,d.dname,e.ename
from
    dept d
left join         # 将左边的表作为主表，主表的数据会被全部查询出来，即使不满足筛选条件也会被查出来
    emp e
on
    d.deptno = e.deptno;
```

###### 右外连接

和左外连接反过来

```sql
# 查询所有部门下的所有员工
select 
    d.deptno,d.dname,e.ename
from
    emp e
right join         # 将右边的表作为主表，主表的数据会被全部查询出来，即使不满足筛选条件也会被查出来
    dept d
on
    d.deptno = e.deptno;
```

```sql
# 查询出所有员工的姓名、部门名称、薪资等级
select
    e.ename,d.dname,s.grade
from
    emp e
join
    dept d
on
    e.deptno = d.deptno
join
    salgrade s
on
    e.sal between s.losal and s.hisal;
```

##### 子查询

```sql
#  查询出比20部门的最高薪资还要高的员工  可以嵌套
select 
    ename,sal
from 
    emp
where
    sal > (select max(sal) from emp where deptno = 20);

# 找出每个岗位中比这个岗位的平均薪资还要高的员工   


# 找出每个部门中比这个部门的平均薪资还要高的员工 

select avg(sal),deptno from emp group by deptno;

select 
    e.ename,e.sal,e.deptno
from 
    emp e
join
    (select avg(sal) as avgSal,deptno from emp group by deptno) t
on
    e.sal > avgSal 
and
    e.deptno = t.deptno;
```

##### 一条完整sql的执行顺序

```sql
1.FROM
2.JOIN
3.ON
4.WHERE
5.GROUP BY
6.HAVING
7.SELECT
8.DISTINCT
9.ORDER BY
10.LIMIT
```

##### UNION

垂直连可以将查询出来的结果合并接

```sql
# 格式
SELECT ...
UNION
SELECT ...


# 例子
select ename,deptno,job from emp where deptno = 10
union all        # union all 显示所有结果
select ename,deptno,job from emp where job = 'MANAGER';
```

*注意： union拼接的结果集必须要有相同数量的列*

#### DCL

数据控制语言，对数据的访问权限进行控制，就是权限分配

##### 创建用户

```sql
# 格式
create user '用户名'@'ip地址' identified  whith mysql_native_password by '密码';

# 创建一个用户，名字是tom，密码是abc123
create user 'tom'@'localhost' identified  with mysql_native_password by 'abc123';

# 8.0之后
create user 'tom'@'localhost' identified  with caching_sha2_password by 'Abc@12345';
```

##### 删除用户

```sql
drop user '用户名'@'ip地址';
drop user 'tom'@'localhost';
```

##### 修改用户名

```sql
rename user '用户名'@'ip地址' to '新用户名'@'ip地址';

# 修改用户名
rename user 'tom'@'localhost' to 'jack'@'localhost';
```

##### 修改密码（先放着）

```sql
# 切换到mysql库
use mysql
# 更新密码
ALTER USER 'tom'@'localhost' IDENTIFIED BY '123456';

update user set password=password('新密码') where user='用户名' and host='ip地址';
update user set password=password('1234567') where user='jack' and host='localhost';
```

##### 权限管理

```sql
# 语法
grant 权限1,权限2,... on 数据库名.表名 to 用户名@ip地址;


# 权限： select、insert、update、delete

# mysql.*            可以操作mysql库中的任意表
# mysql.user         可以操作mysql库中的user表
#*.*                 可以操作任意库任意表


# '用户名'@'localhost'           表示只允许本机登录
# '用户名'@'%'                   表示可以在任意地址登录
# '用户名'@'xxx.xxx.xx.xx'        表示可以在指定ip地址登录

# 将mysql库中的user表的查找权限给jack
grant select on mysql.user to 'jack'@'localhost';
# 刷新权限
flush privileges;
```

##### 回收权限

```sql
# 语法
revoke 权限1,权限2,... on 数据库名.表名 from '用户名'@'ip地址';

revoke select on mysql.user from 'jack'@'localhost';
```

##### 查看权限

```sql
# 语法
show grants for '用户名'@'ip地址';

# 查看jack用户权限
show grants for 'jack'@'localhost';
```

##### 

#### 事务

事务是用来维护数据的完整性的，一个事务就是一个完整的业务逻辑

例如转账：A账户转500块钱到B账户，那么一个*完整的业务逻辑*就是由两个步骤完成的：A扣款，B增加余额，这两个步骤必须同时成功或者失败，这就是一个事务

注意：只有dml语句才有事务

##### 事务的实现

开启事务之后，对数据的操作会被记录到事务日志中，只有当提交了事务，这个操作才会被持久化到数据库并且清除日志，在提交事务之前，对数据的操作可以反悔(回滚)，因为还没有真正持久化。

一个事务可能由多条sql组成的，那么这些sql一定必须同时成功或者同时失败

Mysql开启了事务自动提交，每执行一条sql自动提交，不适合我们开发

```sql
# 开启事务就是关闭自动提交
START TRANSACTION

# 回滚(反悔)
ROLLBACK

# 提交(数据持久化)
COMMIT
```

##### 事务有四大特性

**A：原子性**

事务是最小工作单位，不可再分

**C：一致性**

一个事务中，所有操作要么同时成功，要么同时失败

**I：隔离性**

事务之间有一定的隔离，有四个隔离级别

**D：持久性**

事务提交之后，结果是永久的，事务结束之后，对数据的插入、删除、更新都会被持久化到数据库

##### 事务的隔离级别

```sql
# 查看隔离级别
SELECT @@transaction_isolation;

# 修改隔离级别
SET GLOBAL TRANSACTION ISOLATION LEVEL 隔离级别;

# 修改成读未提交
SET GLOBAL TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;
# 修改成读以提交
SET GLOBAL TRANSACTION ISOLATION LEVEL READ COMMITTED;
# 修改成可重复读
SET GLOBAL TRANSACTION ISOLATION LEVEL REPEATABLE READ;
# 修改成序列化
SET GLOBAL TRANSACTION ISOLATION LEVEL SERIALIZABLE;
```

**测试的时候，两个窗口都要开启事务**

**读未提交(READ UNCOMMITTED)**

事务A可以读取到事务B还没有提交的数据，会产生脏读现象

```sql
insert into test values(301,'北京');
# 另一个事务会看到未提交的数据
```

**读已提交(READ COMMITTED)**

事务A可以读取事务B已经提交的数据，未提交的数据读取不到，有不可重复读的现象

```sql
insert into test values(304,'北京');
```

**可重复读(REPEATABLE READ)**

是mysql默认的隔离级别

A，务B同时开启事务，B读取到A还未提交的数据，这时，A提交事务，B读取到的还是原来的数据，只有当B也提交事务，才能读取到最新的数据，会有幻读现象

**序列化(SERIALIZABLE)**

可以保证每一次读取到的都是最新的数据，不常用

#### 数据库设计

##### 一对一

就是一一对应，通常用一张表来存储

##### 一对多

通常两张表(例如学生，班级表)，设计的时候，在多的表里面添加外键和一的主键关联

```te
class_no            class_name                                        一
-------------------------------
101                    一年一班
102                    一年二班



stu_no                stu_name                cno                        多
----------------------------------------------------
001                    张三                        101
002                    李四                        102
003                    王五                        101
```

##### 多对多

使用中间表存放关联关系

```tes
stu_no                    stu_name    
-----------------------------------------
001                        张三
002                        李四
003                        王五


course_no                course_name            
--------------------------------------------
301                            计算机
302                            数学
303                            哲学


stu_no                course_no                    中间表
-------------------------------
001                        301
001                        303
002                        303
```

##### 数据库的三大范式

设计一张表的时候: 这张表一定要有主键、所有的非主键字段必须完全依赖主键

**第一范式**

- 要求任何一张表必须有主键，每一个字段原子性不可再分

```tes
stu_no            stu_name            stu_country    stu_city            stu_addr            
-------------------------------------------------------------------------------
001                张三                    中国            上海            xxxxx
```

**第二范式**

- 建立在第一范式之上，要求所有非主键字段完全依赖主键，不能产生部分依赖

```tex
(stu_no    college_no)    联合主键，有学生编号和学员编号组成            stu_name    college_name
--------------------------------------------------------------------------------------------
001            101                                            张三            计算机
002            101                                            李四            计算机
003            201                                            王五            法学院

需要改良，可以拆
```

第三范式

- 建立在第二范式之上，要求所有非主键字段直接依赖主键，不能产生传递依赖

```tex
stu_no            stu_name                class_tea                class_addr
----------------------------------------------------------------------------
001                张三                        零零1                    杭州
002                李四                        零零2                    上海
....


这个表有问题，要拆分

class_id                   class_addr           class_tea
-------------------------------------------------------


stu_no                    stu_name           class_id(fk)
------------------------------------------------------
```

#### 索引

是添加在表字段上的一个东西，相当于一本书的目录，可以提高数据的检索效率，降低数据库的io成本

记住:索引不是越多越好，索引有维护成本，不适合在频繁增删的字段上建立索引

##### 创建索引

```sql
# 格式
CREATE INDEX 索引名字 ON 表名(要创建索引的字段);

# 例子
create index index_emp on emp(ename);
```

##### 查看sql执行的索引情况

```sql
explain sql语句;
```

##### 删除索引

```sql
# 格式
DROP INDEX 索引名 ON 表名;

# 例子
drop index index_emp on emp;
```

##### 索引分类

```sql
# 单列索引：在一个字段上创建的索引
# 复合索引：由多个字段创建出来的索引
# ...
```

##### 索引失效

```sql
# 使用模糊查询以%开头的时候，索引失效

# 查询条件中有or，需要or两边的字段都有索引，否则失效

# 范围查询也会失效

# 遇到复合索引的时候，必须要以创建索引左边的列开始，独立查询索引右边会失效

# 在where中所有列参与运算，索引失效
```

**注意：索引是要占用空间的，频繁增删的列不适合建立索引，索引不是越多越好，太多了反而会降低效率**

#### 视图

视图是一张虚拟的表，并没有真实存在，它的数据全部来自定义视图的表

##### 作用

- 简化操作，可以将经常使用的这个查询定义为视图，以后每次使用就不用写复杂的查询条件
- 安全：通过视图可以让用户只能够查询修改能够见到的数据

##### 创建视图

```sql
create view 视图名称 as select语句;
```

##### 视图查询

```sql
# 查询视图中的数据
select xxx,xxx from 视图名称;

# 查看视图的创建语句
show create view 视图名称;
```

##### 删除视图

```sql
drop view 视图名称;
```

##### 通过视图修改数据

```sql
# insert

# delete

# update
```

#### 存储过程

类似Java中的方法

```sql
create procedure name(in 参数名 数据类型，in 参数名 参数类型····)
需要执行的SQL语句
```

```sql
#创建了一个存储过程，名字叫做insertEmp,它需要传入三个数据，
create PROCEDURE insertEmp(IN empno decimal(4,0),in ename varchar(10),in insert into `emp`(empno,ename,sal)values(empno,ename,sal)
                           
#调用这个存储过程可以往emp表中插入一条数据
CALL insertEmp(1,'张三'，1000)
```

#### 窗口函数

1. row_number() 给每一行数据添加一个序号

```sql
# row_number() 给每一行数据加上一个序号
select row_number() over() `rk`,emp.* from `emp`;
# 按照薪资排序,给每一行数据前面加上了一个序号
select row_number() over(order by sal desc) ` no ` ,emp.* from `emp`;
```

2. rank()

```sql
#rank()给数据进行排序
# 查询出所有员工的信息，同时按照薪资进行排序，如果薪资相同，并列排名，后面的排名并不是连续的
select rank() over(order by sal desc) `no` ,emp.* from `emp`;
```

3. dense_rank()

```sql
#按照薪资进行排序，相同的薪资排名相同，后面的排名是连续的
select dence_rank() over(order by sal desc) `no` ,emp.* from `emp`;
```

下是一些常见的 SQL 日期函数：

1. `CURDATE()` - 返回当前日期。

2. `CURTIME()` - 返回当前时间。

3. `NOW()` - 返回当前日期和时间。

4. `DATE()` - 从日期时间表达式中提取日期部分。

5. `TIME()` - 从日期时间表达式中提取时间部分。

6. `YEAR()` - 从日期或日期时间表达式中提取年份。

   ```sql
   #### 1、2000年出生的学生名单
   SELECT  * FROM student WHERE YEAR(birthdate) = '2000';
   ```

   

7. `MONTH()` - 从日期或日期时间表达式中提取月份。

   ```sql
   ####查询本月过生日的学生
   SELECT * FROM student WHERE MONTH(birthdate)=  MONTH(CURDATE()) ;
   ```

   

8. `DAY()` - 从日期或日期时间表达式中提取天数。

9. `HOUR()` - 从时间或日期时间表达式中提取小时。

10. `MINUTE()` - 从时间或日期时间表达式中提取分钟。

11. `SECOND()` - 从时间或日期时间表达式中提取秒数。

12. `DATEDIFF(date1, date2)` - 计算两个日期之间的天数差。返回的是整数值

13. `DATE_ADD(date, INTERVAL value unit)` - 在日期上添加指定的值和单位。

14. `DATE_SUB(date, INTERVAL value unit)` - 从日期中减去指定的值和单位。

    ```sql
    #### 查询3个月前过生日的学生
    SELECT * FROM student WHERE MONTH(birthdate)=  MONTH(DATE_SUB(CURDATE(),INTERVAL 3 MONTH)) ;
    ```

15. `DATE_FORMAT(date, format)` - 将日期格式化为指定的格式。

16. `TIMESTAMPDIFF(MONTH, birthdate, CURDATE())`具体地，我们使用 `MONTh` 作为计算单位，将 `birth_date` 设为起始日期，`CURDATE()` 函数获取当前日期作为结束日期。

```sql
#### 查询各学生的年龄（精确到月份）
SELECT student.*, TIMESTAMPDIFF(MONTH, birthdate, CURDATE()) AS age_in_months FROM student;
```

