

# Java基础语法

### Java特性

Java是纯面向对象的语言（oop）

Java是跨平台语言（jvm）

### jvm、jre、jdk

- jvm：java虚拟机，是Java跨平台的保证，屏蔽了底层操作系统的区别

- jre：Java运行时环境，是jvm加上核心类库组（sun公司编写的代码）成的，想要运行Java程序，最起码要有jre

- jdk：Java开发工具包，是jre加上开发工具集组成的

### 常用dos命令

打开dos窗口： win + r 输入cmd 回车

列出当前目录文件：dir

切换盘符：D:     回车

切换路径： cd (相对路径或者绝对路径)

切换到上一级目录： cd ..

清理dos窗口：cls

### 配置环境变量

到编辑系统环境变量---> 新建一个JAVA_HOME  将jdk的安装目录粘贴过去

编辑Path--->新建   %JAVA_HOME%\bin

在dos窗口敲一个命令，系统会在Path中的目录查找是否有这个命令，有则执行，没有就报错

### 第一个Java程序

```java
public class Hello{
    public static void main(String[] args){
        System.out.println("Hello world");
    }    
}
```

到Java程序所在目录下，打开dos窗口

编译： javac  Hello.java

运行： java Hello

看到dos窗口输出了Hello World就成功了

**注意：一定要保证public修饰的类名和文件名保持一致**

### 注释

注释是对Java程序的解释和说明

**单行注释**

使用// 开头，一直到这一行末尾都是注释内容，注释不参与程序编译和运行

```java
// 这是注释，不参与编译运行
System.out.println("这是代码");   // 这里也可以写注释
```

**多行注释**

使用/*开头   以\*/结尾，在这个里面的内容都是注释

```java
/*
这里是注释
这也是注释
这里还是注释
*/


/*
* 这里前面加上*也是可以
*/
```

**文档注释**

以/**开头，以\*/结尾，在这里面无论写多少行都属于文档注释

文档注释的内容可以被javadoc提取出来作为帮助文档

```java
/**

这是文档注释，这里面的内容可以被提取成为帮助文档
*/
```

**TODO**

表示有事情没干

```java
// TODO
```

### Hello程序的说明

```java
/*
    public 表示公开   固定写法     
    class  表示一个类  固定写法
    Hello  类名（也是现阶段唯一可以改的地方）文件名是什么，这个类名就是什么（public修饰的class）
*/

public class Hello{
    /*
        static    表示静态的固定写法
        void      表示这个方法没有返回值
        main      方法名（不能改，这个方法是程序的入口） 
        String[]  表示一个String类型的数组
        args      参数名，可以改  
    */
    public static void main(String[] args){
        // 输出语句，卸载""里面的内容，就是程序运行之后输出的内容
        // println表示输出一行
        // 还有一个是print()  表示输出内容，但是不换行
        System.out.println("Hello world");
    }   

}
```

练习：编写一个Test.java  要求程序运行之后输出   这是测试程序

```java

```

### 计算机当中的存储单位

计算机只能识别0和1，一个1或者一个0我们称之为比特

```java
//  一个比特
1
0
```

**字节**

字节是计算机当中最基本的存储单位，一个字节是8个比特

```java
// 一个字节
0000 0000
1111 1111
```

**二进制**

逢二进一

```java
// 十进制
0 1 2 3 4 5 6 7 8 9 10 
//二进制
0 1       // 再往后就是  10
```

**原码**

二进制使用最高位表示符号位,1表示负数，0表示正数

```java
// 3
00000 0011
// -3
1000 0011

// 7
0000 0111


// 二进制对应十进制数字
1        1       1       1      1     1     1     1
128     64      32      16      8     4     2     1
```

**反码**

正数的反码就是原码，负数的反码是符号位不变，其余位取反

```java
// 3 
// 原码
0000 0011
// 反码
0000 0011


// -3
// 原码
1000 0011
// 反码
1111 1100   
```

为什么要有反码

```java
// 2 + (-2)   使用原码运算会得到什么
0000 0010     // 2 原码
1000 0010     // -2 原码
1000 0100     // 结果是 -4


// 使用反码进行计算
0000 0010    // 2  反码
1111 1101    // -2 反码
1111 1111    // 这是-2 和 2 的反码计算得到的结果，本身还是反码，需要转成原码才可以表示数字
// 将它转成原码
1000 0000   // 结果是 -0
```

**补码**

计算机底层存储的就是补码

正数的补码还是原码

负数的补码就是在反码的基础上加1

补码 1000 0000 表示-128   补码的最高位即是符号位，也是运算位

1111 11111   取值范围是 0-255

```java
// 一个字节中，最大的正数
0111 1111     // 127 
最小的负数  
1000 0000    // -128
```

### 数据类型

Java中有两大类数据类型 ，一种是基本数据类型，一种是引用数据类型

#### 基本数据类型

基本数据类型有四类八种

**整数型**

```java
数据类型                    占用字节                      取值范围
---------------------------------------------------------------------
byte                         1                         -128~127

short                        2                         -32768~32767

int                          4                         -2147483648~2147483647           

long                         8                         -2^63~2^63-1
```

**浮点型**

```java
数据类型                    占用字节                   取值范围
----------------------------------------------------------------
float                      4                        有效数字位是6-7位
double                     8                        有效数字位15-16位
```

**布尔型**

```java
数据类型                   占用字节                    取值范围
-----------------------------------------------------------------
boolean                   1                        只有两个值:true(真)  false(假)
```

**字符型**

```java
数据类型                    占用字节                    取值范围
-------------------------------------------------------------------------
char                        2                        0-65535
```

#### 引用数据类型

现阶段只需要知道一种：String

String表示字符串

**如何区分字符和字符串**

在Java程序中，用单引号括起来的就是字符，用双引号括起来的就是字符串

### 标识符

在Java程序中可以自己命名的就是标识符

例如：类名、方法名、参数名、变量名、接口名...

**标识符的命名规则**

- 标识符只能够由数字、字母、下划线和美元符号组成

- 标识符不能以数字开头

- 标识符不能是Java中的关键字

```java
// 判断标识符是否合法
helloWorld
name
_123
_abc@
1abc
$1bc123
$_@123
```

**标识符的命名规范**

最好将它当成规则一样对待

命名规则原则上可以遵守也可以不遵守

- 标识符要做到见名知意（最好用英文）

- 类名、接口要遵循大驼峰（首字母大写，之后的每个单词首字母都要大写）

- 变量名、方法名、参数名要遵循小驼峰（首字母小写，之后的每个单词首字母大写）

**字面量**

就是字面意义上的值（就是一个值）

### 变量

在程序运行过程中，要有一个东西来存储这个程序不断变化的数据，这个东西就是变量

变量由三部分组成： 数据类型  标识符  字面值

#### 定义格式

**只定义，不赋值**

```java
// 定义格式： 数据类型 变量名;
int num;     // 定义了一个变量，但是这个变量还没有值，这时这个变量是不能使用的


// 给变量赋值
num = 500;         // 只有在赋值之后才能够使用这个变量
```

**定义变量并赋值**

```java
// 定义格式： 数据类型 变量名 = 字母值;
// 在定义变量的时候给它赋值
int num = 10;
```

**一次定义多个变量**

```java
// 定义多个变量并赋值
int num1 = 1,int num2 = 2;

// 定义多个变量但是不赋值
int num1,num2;            // 用的特别少
```

#### 变量的注意事项

1.变量是有使用范围的，这个范围是当前代码所在的{}

```java
public class VarTest02{
    public static void main(String[] args){


        // 这个东西叫局部代码块
        {
            String name = "张三";
            // 在这里面是可以使用的,出了这个{}就用不了了
            System.out.println(name);
        }
        // 这里就用不了
        // System.out.println(name);
    }
}
```

2.在同一个{}里面，变量名不能改重复定义

```java
public class VarTest02{
    public static void main(String[] args){


        // 这个东西叫局部代码块
        {
            String name = "张三";
            System.out.println(name);
        }

        // System.out.println(name);


        {
            String name = "李四";
            // 这里定义就有问题
            //String name = "王五";  
            System.out.println(name);
        }

        // 这里是没问题的
        String name = "王五";
        System.out.println(name);
    }

}
```

3.变量在使用之前，一定要先赋值

4.在定义变量的时候，整数型默认是int类型，浮点型默认是double类型

```java
// 如果想要定义long类型，需要在值后面加一个L（不分大小写）
long l = 123456789L;

// // 如果想要定义float类型，需要在值后面加一个F（不分大小写）
float f = 3.14F;
```

### 常量

在Java中使用final修饰的变量被称之为常量，常量一旦赋值就不可更改

注意：常量名一定是全部大写的，如果有多个单词，每个单词之间使用_隔开

```java
final double PI = 3.14;

// 全部大写，用_隔开
final int RESULT_CODE = 200;
```

```java
public class VarTest03{

    public static void main(String[] args){
        final int CODE = 200;
        // 常量一旦赋值不可更改
        // CODE = 300;

        final double PI;
        PI = 3.14;

        System.out.println(PI);
    }
}
```

### 类型转换

#### 强制类型转换(显示转换)

强制类型转换格式

```java
// 目标数据类型 变量名 = (目标数据类型) 值或者变量;

int a = 128;
byte b = (byte)a;
```

大的数据类型往小的数据类型转换会有风险，可能会丢失数据或者损失精度

```java
public static void main(String[] args){

    // 大转小
    int a = 65535;
    // byte b = a;
    // 强制类型转换
    byte b = (byte)a;
    System.out.println(b);
}
```

浮点型数据转成整数型数据，小数部分会丢失

#### 自动类型转换（隐式转换）

自动类型转换就是用大的数据类型接受小的数据类型的变量或者值

```java
byte b = 127;

// 这样没有任何问题，发生了自动类型转换
int i = b;


// 整数型转成浮点型
byte b1 = 127;
float f = b1;
double d = b1;
double d2 = f;
```

#### char和字符编码

char字符转成整数型、浮点型，会先转成Unicode编码表对应的数字再进行转换或者运算

```java
char c = 'a';
int i = c;
```

**字符编码**

ASCII码表：128个常用字符（大小写的英文字母和特殊字符），一个数字对应一个字符或者键位

Unicode编码表：使用两个字节存储数据，基本上能够表示世界上所有国家的字符

> 万国码>utf-8：由Unicode演变过来的，使用1个字节表示字母，3个字节表示汉字

gbk：国标码，使用1个字节存储字母，2个字节存储汉字

big5：能够表示繁体字

### 运算符

#### 算术运算符

```java
符号                作用                            描述
----------------------------------------------------------------
+                   加                            数学加法运算
-                   减                            数学减法运算   
*                   乘                            数学乘法运算
/                   除                            数学除法运算（整数相除只能得到整数部位，不会四舍五入），如果想得到小数必须要有浮点型参与运算
%                   取余                          获取两数相除得到的余数
```

**注意事项**

1.两种不同类型参与运算，会发生**类型提升**（会议两个变量中数据类型大的为准）

```java
int i = 2;
long l = 3L;
int i1 = i + l;          // i + l 会发生类型提升，会变成long类型
// 上面这种方式是错误的，需要强制类型转换或者用大的数据类型接受
int i2 = (int)(i + l)                // 强制类型转换
long l1 = i + l;                     // 用大的类型接受
```

**2.Java再byte、short、char三者进行运算时，会将这三种类型先转成int类型再运算**

```java
public static void main(String[] args) {

    byte b1 = 2;
    byte b2 = 3;
    short s1 = 6;
    char c = 'a';  // 字符a对应的数字时97

    // 这里编译报错了，因为运算的时候已经转成int类型了，用byte接受会有问题
    // byte b3 = b1 + b2;
    byte b3 = (byte)(b1 + b2);
    // 用更大的数据类型接受也没有问题
    int i = b1 + b2;

    // 这里也不行，从int转成short需要强制类型转换
    // short s2 = b1 + s1;
    short s2 = (short)(b1 + s1);
    // int转成char会有问题，需要强转
    // char c2 = c + 1;
    char c2 = (char)(c + 1);

    System.out.println(c2);
}
```

3.字符串参与运算

字符串参与运算会直接拼接而不是进行算术运算（运算结果是字符串）

```java
public static void main(String[] args) {
    String name = "张三";
    // 张三1   拼接而不是运算
    System.out.println(name + 1);


    int i1 = 100;
    int i2 = 200;
    int i3 = 300;

    System.out.println(i1 + 1 + name + i2 + i3);
}
```

如果想将一个数字转成字符串可以加上一个空字符串<font color=#10f>（""）</font>

#### 赋值运算符

```java
符号                    作用                       描述
--------------------------------------------------------------------
=                       赋值                   a=10;将10赋值给        
+=                      加后赋值                a+=10; 等同于a = (a的数据类型)a + 10;
-=                      减后赋值                a-=10; 等同于a = (a的数据类型)a - 10;
*=                      乘后赋值                a*=10; 等同于a = (a的数据类型)a * 10;
/=                      除后赋值                a/=10; 等同于a = (a的数据类型)a / 10;
%=                      取余后再赋值            a%=10; 等同于a = (a的数据类型)a % 10;
```

除了=，其他赋值运算符都会进行强制类型转换

#### 自增自减运算符

```java
符号                作用                    描述
----------------------------------------------------------
++                自增                      a++;  等同于 a = (a的数据类型)a + 1;
--                自减                      a--;  等同于 a = (a的数据类型)
```

注意：自增自减运算符改变的是元数据，不会发生类型提升，也不需要强制类型转换

**自增自减运算符单独一行的时候++ -- 在前在后没区别，但是和其他运算符一起写那么**

++ 或者 -- 在前，先自增或者自减再参与运算

++ 或者-- 在后，先参与运算在自增自减

```java
public static void main(String[] args) {
    int i = 3;
    // ++i;

    int result = i++ + 3;
    System.out.println(i);

    int a = 1;            // a  1
    int b = a++;        // a  2
    // 2 + 3 + 3
    int c = a + ++a + a++;
    // a = 4

    System.out.println(c);
}
```

#### 比较运算符

比较运算符返回的结果是boolean类型，不是true就是false

```java
符号                       作用
----------------------------------------------------
>                        判断左边是否大于右边
<                        判断右边是否大于左边
==                       判断两边是否相等
    变量名.equals() 方法		  字符串的比较
>=                       判断左边是否大于等于右边
<=                       判断右边是否大于等于左边
!=                       判断两边是否不相等
```

#### 逻辑运算符

两边必须是布尔类型（true或者false）

```java
符号               描述              作用
--------------------------------------------------------
&                逻辑与              要求&左右两边都是true，结果才为true
|                逻辑或              要求|左右两边有一个true，结果就是true
!                逻辑非              取反,true变成false，false变成true
&&               短路与              要求&左右两边都是true，结果才为true，如果判断左边位false了，就不再判断右边，直接false
||               短路或              要求|左右两边有一个true，结果就是true，如果左边是true，不再判断右边，直接返回true 
^                逻辑异或            要求左右两边结果不一样才为true
```

```java
public static void main(String[] args) {
    int a = 3;
    int b = 5;


    System.out.println(3>5 & 3<5);
    System.out.println(3>5 | 3<5);
    System.out.println(!(a > b));

    // 3>5已经是false，不再判断右边结果，直接返回false
    System.out.println(3>5 && a < b);
    // 判断为true的条件和|一样，只不过有短路效果
    System.out.println(3>5 || a < b);

    System.out.println(3>5 ^ 3<5);

}
```

**注意：&& 和 || 有短路效果，如果只从左边结果就可以判断出返回什么，就不再判断右边了**

短路，最终结果是一样的，只是提高运算效率

逻辑

#### 三元运算符

```java
格式    布尔表达式 ? 值1(表达式1) : 值2(表达式2)

如果布尔表达式的结果是true，那么三元表达式的结构是值1，否则是值2
```

```java
public static void main(String[] args) {

    int a = 3 > 5 ? 0 : 1;
    System.out.println(a);
}
```

#### 位运算符

要求两边是<font face=黑体>数据类型</font>（一般都是整数型），使用两边的二进制参与运算

正数无论怎么位移都不会说负数，超出极限为0，负数位运算也是正常计算，只不过符号位是负数，但是向右位移超出界限，会变成-1

```java
符号             描述                    运算规则
------------------------------------------------------------
&                与                     两个位都是1，结果才为1
|                或                     两个位都为0，结果才为0
~                取反                   0变成1，1变成0   
^                异或                   相同为0，不同为1
<<               左位移                  将二进制向左位移x,就是原来数据乘x^2
>>               右位移                  将二进制向右边位移x位位,就是原来数据除x^2
```

```java
3&5

0000 0011            // 3的二进制
0000 0101            // 5的二进制
0000 0001            // 运算结果

3|5
0000 0011            // 3的二进制
0000 0101            // 5的二进制
0000 0111            // 运算结果

~5
0000 0101            // 5的二进制
// 结果是-6，以程序为准，不用手算

3^5
0000 0011            // 3的二进制
0000 0101            // 5的二进制
0000 0110            // 运算结果


2<<2
0000 0010          // 2的二进制
0000 1000          // 结果变成了8


8>>2
0000 1000           // 8的二进制
0000 0010           // 结果变成2
```

#### 运算符的优先级

当不确定优先级的时候，使用（）括起来，（）的优先级是最高的

```java
// 例如
(3&(5++))--
```

### Scanner键盘录入

可以接受用户输入的值

```java
// 引入了sun公司写好的一个功能
import java.util.Scanner;
```

```java
import java.util.Scanner;

public class Test02{
    public static void main(String[] args) {
        // 固定写法
        Scanner s = new Scanner(System.in);

        System.out.println("请输入一个整数：");
        // 从键盘接收一个int类型的值,只能接受整数，输入小数、字符、字符串、布尔都会报错
        int num = s.nextInt();        // next后面跟的是什么类型，就只能输入什么类型

        System.out.println(num);
    }
}
```

```java
// 想接收小数
double d = s.nextDouble();     // 只能接收小数或者整数，小数超出了会丢弃

// 接收一个byte类型的数据
byte b = s.nextByte();        // 只能接收byte取值范围内的整数

// 接收一个short类型的数据
short num = s.nextShort();    // 只能接收short取值范围内的整数

// int、long同上

// 接收字符串


String s1 = s.next();            // 遇到空格就不再接收

// 如果想将空格一起接收
String s2 = s.nextLine();        // 接收一行数据，空格和特殊字符也可以接受


// 接收布尔类型
boolean b = s.nextBoolean();    // 只能接收true或者false（不区分大小写）
```

```java
import java.util.Scanner;

public class Test02{
    public static void main(String[] args) {
        // 固定写法
        Scanner s = new Scanner(System.in);

        System.out.println("请输入一个值：");
        // 从键盘接收一个int类型的值
        // int num = s.nextInt();
        // double num = s.nextDouble();

        // byte num = s.nextByte();

        // short num = s.nextShort();

        // 接收字符串
        // String s1 = s.next();

        // 接收一行
        String s2 = s.nextLine();

        System.out.println(s2);
    }
}
```

### 流程控制语句

流程控制语句就是控制程序的走向

#### **分支控制语句**

#### if

```java
// 一个完整的if语句，结构
if(布尔表达式){
    语句体;
}else if(布尔表达式){
    语句体;
}else{
    语句体;
}
```

**if语句的执行流程**

- 自上而下判断布尔表达式

- 如果表达式结果为true，就进入到{}里面执行代码，并且if语句就结束了

- 如果表达式结果为false，就进入到下一个else if进行判断，重复第二步

- 如果if和else if都为false，那么就进入到else的{}执行代码，结束if语句

**注意：只要由一个判断成立，整个if语句就结束了**

if语句的几种情况

```java
int age = 18;


// 只有一个if判断
if(age >= 18){
    System.out.println("欢迎来到学掌门网吧");
}

// 由有一个if和else组成
if(age >= 18){
    // TODO
}else{
    // TODO
}


// 由一个if和一个或者多个else if组成
if(age >= 18){
    // TODO
}else if(age >= 80){
    // TODO
}else if(age < 0){
    // TODO
}


// 由一个if和一个或者多个else if组成并且由else
 if(age >= 18){
    // TODO
}else if(age >= 80){
    // TODO
}else if(age < 0){
    // TODO
}else{
    // TODO
}
```

```java
// 判断年龄是否满18，能否进网吧
import java.util.Scanner;

public class Test03{
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println("请输入年龄：");
        byte age = s.nextByte();

        String message;

        if(age >= 18){
            message = "欢迎来到学掌门网吧";
        }else {
            message = "年龄未满18，禁止进入";
        }

        System.out.println(message);
    }
}
```

```java
// 使用Scanner接收成绩，60分以下是不及格，60-70几个，70-80一般，80-90良好，90以上优秀，其他成绩是不合法的
```

printf  格式化输出，%d 表示一个数字，%s表示一个字符

**if判断是可以嵌套的**

```java
public class Test05{
    public static void main(String[] args) {
        // 年龄必须大于18，并且金钱必须大于6才能上网

        int age = 19;
        int money = 7;

        // 先判断年龄，在判断金钱
        if(age >= 18){

            if(money >= 6){
                System.out.println("欢迎上网");
            }else {
                System.out.println("钱不够上网");
            }

        }else {
            System.out.println("年龄太小了");
        }

    }
}
```

查看以下代码有什么问题

```java
int a = 10;
int b;

if(a%2 == 0){
    b = 0;
}else if(a%2 == 1){
    b = 1;
}
System.out.println(b);
```

#### switch

```java
// 一个完整的switch语句的结构
switch(表达式/值){
    case 值1:
        语句体;      //todo
        break;
    case 值2:
        语句体;
        break;
    case 值3:
        语句体;
        break;
    ...
    default:
        语句体;
        break;            // 这里的break加不加都无所谓，switch已经结束了
}
```

**switch后面的（）中只能是以下的数据类型**

byte、short、int、char、String、枚举(enum)

**执行流程**

- 自上而下依次判断

- 如果case匹配成功，就进入到该case的语句体中执行，遇到break就结束整个switch语句

- 如果匹配不成功，就进入到下一个case继续匹配

- 如果没有case匹配成功，就进入到default内执行

**注意：如果没有break，会发生case穿透现象**

```java
import java.util.Scanner;
public class SwitchTest{
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        byte age = s.nextByte();
        // 判断年龄 30而立，40不惑，50知天命，60耳顺
        switch(age){
            case 30:
                System.out.println("而立之年了");
                // break;   
            case 40:
                System.out.println("不惑之年了");
                // break;
            case 50:
                System.out.println("知天命");
                // break;    
            case 60:
                System.out.println("耳顺之年了");
                // break;    
            default:
                System.out.println("我只是一个简单的程序，判断不了了");
                break;
        }
    }
}
```

```java
import java.util.Scanner;
public class MonthTest{
    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        System.out.println("请输入当前月份:");
        byte month = s.nextByte();

        // 利用switch的case穿透输出当前月份天数
        switch(month){
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                System.out.println(month + "月，有31天");
                break;
            case 4: 
            case 6: 
            case 9: 
            case 11:
                System.out.println(month + "月，有30天");
                break;
            case 2:
                System.out.println(month + "月，有28天");
        }
    }
}
```

**什么时候用if，什么时候用switch**

一般比较复杂的判断用if，如果是有限个等值判断，用switch，建议用一点

**循环**

需求：在控制台输出10行hello world，可以复制粘贴10便，比较简单

需求：输出100万行，复制粘贴做不到了，需要使用循环

当我们需要做一些重复有规律的事情，可以使用循环语句‘

#### for

```java
// 写法
for(初始化语句; 循环条件; 增量){        // {}里面叫做循环体
    循环语句;
}

// 例子
for(int i = 0; i < 10; i++){
    System.out.println(i);
}
```

**执行流程**

1. 先执行初始化语句
2. 判断循环条件
   
   1. 结果为true，进入到循环体，执行循环语句
   
   2. 结果为false，结束循环
3. 执行完循环语句之后，执行增量，执行第二步
   * for循环可以嵌套

```java
int i = 0;
true  
System.out.println(0);
i++;        // i = 1
true
System.out.println(1);
i++;        // i = 2
true
System.out.println(2);
...
i++;        // i = 10
false       // 循环结束了
```

```java
// 循环，里面的东西是可以缺省的（不写）



// 将初始化语句提到外面
int i = 1;
for( ; i <= 10; i++){
    System.out.println(i);
}

int i = 1;
// 缺省循环条件(默认就是true)，会一致循环下去
for( ; ; i++){
    System.out.println(i);
}


// 缺省增量,会无线循环，一直输出1
for(int i = 1; i <= 10; ){
    System.out.println(i);
}

// 将增量放到循环体中
for(int i = 1; i <= 10; ){
    System.out.println(i);
    i++;
}


// 死循环，会一直输出
for( ; ;){
    System.out.println("hello world");
}
```

**练习**

```java
// 1.输出100-1的所有整数

// 2.要求求出1-100的和

// 3.输出1-100之间的所有奇数，并求出1-100之间有几个奇数

// 4.将数字123456倒置过来变成654321输出(要求用for循环做)
        /*
         *      123456%10 = 6             123456/10 = 12345
         *      12345%10 = 5              12345/10 = 1234       6*10 + 5  = 65
         *      1234%10 = 4               1234/10 = 123         65*10 + 4 = 654
         *      123%10 = 3                123/10 = 12           654*10 + 3 = 6543
         *      12%10 = 2                 12/10 = 1             6543*10 + 2 = 65432
         *      1%10 = 1                                        654321
         */

        // 需要进行倒置的数字
        int num1 = 123456;
        // 定义一个变量用来存放倒置过后的数
        int num2 = 0;

        for(int i = num1; i > 0; ){
            num2 = num2 * 10 + i%10;
            i/=10;
        }
        System.out.printf("%d倒置过来是%d",num1,num2);
        System.out.println("");

// 5.要求从键盘接收今年的年份和月份以及今天是第几号，计算出今天是今年的第几天
```

#### while

```java
// 格式
while(布尔表达式){        // {}里面是循环体
    循环语句;
}

// 例子
while(true){
    System.out.println("hello world");
}
```

**执行流程**

1. 查看布尔表达式的结果
   
   1. 结果为true，执行循环体中的循环语句
   
   2. 结果为false，结束循环

2. 执行完循环语句之后，重复步骤1

```java
// 输出1-10
int i = 1;
while(i <= 10){
    System.out.println(i);
    i++;
}

// 相当于
int j = 1;
for( ; j <= 10; j++){
    System.out.println(j);
}

// 死循环，一直输出
while(true){
     System.out.println("hello world");
}

// 相当于
for( ; ; ){
     System.out.println("hello world");
}
```

for和while如何选择，一般来说，可以确定循环次数的，用for，无法确定次数的，用while

#### do...while

```java
// 格式
do{                //{}里面是循环体
    循环语句
}while(布尔表达式);


// 例子
int i = 1;
do{
    System.out,println(i);
}while(i > 1);
```

**执行流程**

1. 执行循环体中的代码

2. 判断布尔表达式
   
   1. 结果是true，继续步骤1
   
   2. 结果是false，结束循环

**while和do...while的区别**

while是先判断再执行

do...while是先执行，再判断

使用的时候，三种循环如何抉择，for循环用的最多，不确定循环次数用while，do...while使用的情况较少

#### 循环控制语句

**break**

再循环中，遇到break当前循环就结束了

```java
for(int i = 1; i <= 10; i++){
    if(i == 7){
        break;        // 当i循环到7的时候，for循环就结束了，不会继续往下执行
    }
    System.out.println(i);
}
```

**注意：break只能出现再循环或者switch语句中**

**continue**

跳过此次循环

```java
for(int i = 1; i <= 10; i++){
    if(i == 7){
        continue;
    }
    System.out.println(i);
}
```

*break跳出这个循环，*continue*跳过这次循环

输出1-100，遇到既是3又是5的倍数就不输出

```java

```

### Random

可以使用Random生产随机数

```java
import java.util.Random;        // 使用random需要先导包

public class RandomTest{
    public static void main(String[] args) {
        Random r = new Random();
        int i = r.nextInt(11);        // 会生成0-10的随机数
        System.out.println(i);
    }
}
```

int i = r.nextInt( bound：3)                     //生成一个范围在0-2的随机整数

### 数组



#### 栈

栈是一种后进先出的数据结构

往栈中存储数据叫做压栈、入栈、进栈、push

从栈里面拿数据叫做弹栈、出栈、pop

只有再栈顶的数据（元素）才具有活跃权

栈有指针，始终指向栈顶元素，如果空栈（栈里面没有元素），那么指向-1

满栈：栈放满了元素

数组的作用：可以存放多个同种数据类型的数据

#### 初始化

```java
// 数组的初始化，动态初始化
数据类型[] 数组名 = new 数据类型[数组长度];
// 例子
int[] arr01 = new int[3];

// 静态初始化
数据类型[] 数组名 = new 数据类型[]{数据1,数据2,...};
// 例子
int[] arr02 = new int[]{1,3,5,7,9};

// 静态初始化简写方式
数据类型[] 数组名 = {数据1,数据2,...};
// 例子
int[] arr03 = {2,4,6,8,10};
```

**如何选择数组的初始化方式**

- 如果数组中的元素确定好了，那么选择静态初始化，并且用简写方式

- 如果数组中存放的元素不确定，那么选择动态初始化

#### 特点

1. 数组的长度一旦确定不可更改

2. 数组中的元素（数据），他们的数据类型必须是一样的

3. 数组动态初始化，是有默认值的（数字类型默认是0，布尔类型false，字符串默认是null）

#### 概念

数据在内存中是东一块西一块的，数组在内存中是一块连续的空间，数组中只能存储某一种数据类型，相同的数据类型占用的字节数，数据里面每一个元素的大小是一样的。数组就是一个容器，用来存放多个数据 

**数组元素**

就是数组中的一个个数据

**数组长度**

初始化数组时给的长度，表示数组能够存放几个元素

可以使用数组名.length来获取数组长度

```java
int[] arr = {1,2,3,4,5};
System.out.println(arr.length);       //可以获取数组的长度
```

**索引值**

数组中的每一个元素都有一个索引，索引从0开始，到长度-1结束

#### 存取元素

**获取元素**

```java
数组名[索引值]      // 可以拿到对应索引处的元素
int[] arr01 = {1,2,3,4,5};
System.out.println(arr01[3]);            // 拿到4
```

**修改元素**

```java
数组名[索引] = 值;
int[] arr02 = {2,4,6,8,10};
arr02[2] = 100;   

int[] arr03 = new int[5];
arr03[2] = 20; 
```

#### 数组遍历

```java
int[] arr = {1,2,3,4,5};


for(int i = 0; i < arr.length; i++){
    System.out.println(arr[i]);
}
```

#### 数组的复制

```java
int[] arr1 = {1,2,3,4,5,6};


// 数组复制
int[] arr2 = new int[arr1.length];

for(int i = 0; i < arr1.length; i++){
    arr2[i] = arr1[i];
}


// 遍历数组arr2
for(int i = 0; i < arr2.length; i++){
    System.out.println(arr2[i]);
}
```

看下面代码

```java
int[] arr1 = {2,4,6,8,10};
int[] arr2 = arr1;
arr2[2] = 200;
// 遍历arr1，会输出
```

#### 多维数组

**二维数组**

就是一个一维数组，只不过它的每一个元素都是一维数组

**定义**

```java
数据类型[][] 数组名 = new 数据类型[长度][长度];		//动态初始化


数据类型[][] 数组名 = {{1,2,3},{4,5,6}...};		//静态初始化



// 获取元素
数组名[索引][索引]

// 修改元素
数组名[索引][索引] = 值;


int[][] arr01 = {{1,2,3},{4,5,6}};
// 遍历二维数组
for(int i = 0; i < arr01.length; i++){

    // 遍历里面那层数组
    for(int j = 0; j < arr01[i].length; j++){		//arr01[i]数组元素，也是二维数组
        System.out.println(arr01[i][j]);
    }
}
```

### 简单算法

#### 顺序查询

就是遍历数组，查询是否是要查找的元素

int[] arr = {3,6,9,2,4,7,1,10};

        // 接收用户输入的数字,如果在arr中查找到了则输出这个数字对应的索引，如果查找不到输出没有这个数字


```java
 int[] arr = {3,6,9,2,4,7,1,10};

        // 接收用户输入的数字,如果在arr中查找到了则输出这个数字对应的索引，如果查找不到输出没有这个数字


        Scanner s = new Scanner(System.in);
        System.out.println("您要查找哪个数字");
        int num = s.nextInt();

        // 定义一个变量用于存储查找到的数字的索引
        int index = -1;

        for(int i = 0; i <= arr.length - 1; i++){
            // 比较遍历到的数字是否和要查找的数字相等
            if(arr[i] == num){
                // 如果找到了，就将这个数字的索引赋值给index
                index = i;
            }
        }

        // 遍历结束之后判断这个索引是否还是-1
        if(index == -1){
            System.out.println("没有这个数字");
        }else {
            System.out.printf("%d的索引是%d",num,index);
        }
```
#### 二分法查询

准备两个索引:最小索引和最大索引，开始直接查找中间索引，将目标数和中间索引的数进行比较，结果1：中间索引数比目标数大  说明目标数在中间索引的左边，重新获取最大索引和中间索引，最大索引=中间索引-1

结果2：中间索引数比目标数小    说明目标数在中间索引的右边，重新获取最小索引和中间索引，最小索引=中间索引+1

结果3：中间索引=目标索引，直接输出

```java
// 准备一个有序数组，从小到大
        int[] arr = {1,2,3,4,5,6,7,8,9};

        // 开始二分法查询

        /*
        *   思路:   准备两个索引:最小索引和最大索引 和中间索引         一开始最小索引就是0 最大索引就是数组长度-1 中间索引 = (最小索引+最大索引) / 2
        *
        *   开始直接查找中间索引,将目标数和中间索引的数进行比较
        *       结果1: 中间索引数比目标数大   说明目标数在中间索引的左边 ，重新获取最大索引和中间索引, 最大索引 = 中间索引-1
        *       结果2: 中间索引数比目标数小   说明目标数在中间索引的右边，重新获取最大索引和中间索引  最小索引 = 中间索引+1
        *       结果3: 中间索引数刚好就是要查找的数，直接输出
        *
        * */

        // 准备最小索引
        int min = 0;
        // 准备最大索引
        int max = arr.length - 1;
        // 准备中间索引
        int mid = (min + max)/2;

        // 准备一个目标数
        int num = 7;


        while (true){
            // 如果中间索引数大于目标数
            if(arr[mid] > num){
                if(min > max){
                    //说明在这个数组中根本没有这个目标数
                    System.out.println("没有这个数字");
                    // 退出循环
                    break;
                }
                // 重新获取最大索引和中间索引
                max = mid - 1;
                mid = (min + max) / 2;
            } else if (arr[mid] < num) {        // 如果中间索引数小于目标数
                if(max < min){
                    // 说明在这个数组中根本没有这个目标数
                    System.out.println("没有这个数字");
                    // 退出循环
                    break;
                }
                // 重新获取最小索引和中间索引
                min = mid + 1;
                mid = (min + max) / 2;
            }else {
                // 找到了这个数
                System.out.printf("要查找的数的索引是%d",mid);
                // 找到这个数之后要退出这个死循环
                break;
            }
        }
```

#### 冒泡排序

一轮交换之后得到一个最大值，冒泡排序的轮次=数组的长度-1

```java
System.out.println(Arrays.toString(arr));
```

```java
import java.util.Arrays;
public class Test01{
    public static void main(String[] args){
    int[] arr = {45,51,49,38,47,26,25};

        System.out.println(Arrays.toString(arr));
        boolean flag;
    for(int j = 1;j <= arr.length -1; j++){
        flag = false;
        for(int i =0; i <= arr.length-1-j; i++){
            if(arr[i] >arr[i+1]){
                int temp = arr[i];
                arr[i] =arr[i+1];
                arr[i+1] = temp;
                flag = true;
            }
        }
        if(flag == false){
            break;
        }
    }
    System.out.println(Arrays.toString(arr));
    }
}
```



#### 选择排序

默认第一个数就是最大值或者最小值，比较之后的数字是否比第一个要大还是小，如果比第一个数字大/小 交换位置

比较轮次和比较次数是反过来

```java
import java.util.Arrays;
import java.util.Random;

public class Demo02 {
    public static void main(String[] args) {

        // 选择排序

//        Random random = new Random();
//
//        int[] arr = new int[7];
//
//        for(int i = 0; i <= arr.length - 1; i++){
//            arr[i] = random.nextInt(100) + 1;
//        }
//
//        System.out.println(Arrays.toString(arr));


        int[] arr = {20, 73, 68, 95, 45, 13, 94};
        System.out.println(Arrays.toString(arr));


        int min;        // 定义一个变量用于存放最小值
        for(int j = 0; j <= arr.length - 1 - 1; j++){     // j: 0~数组长度-1-1   数组长度7   j:0-5
            // 第一轮比较
            for(int i = j; i <= arr.length - 1 - 1; i++){
                // 默认第一个数是最小值
                min = arr[j];
                // 将索引0之后的数字和这个min比较，如果得到的数比这个min还要小，则交换位置
                if(min > arr[i + 1]){
                    arr[j] = arr[i + 1];
                    arr[i + 1] = min;
                }
            }
        }


//        int min;        // 定义一个变量用于存放最小值
//        // 第一轮比较
//        for(int i = 0; i <= arr.length - 1 - 1; i++){
//            // 默认第一个数是最小值
//            min = arr[0];
//            // 将索引0之后的数字和这个min比较，如果得到的数比这个min还要小，则交换位置
//            if(min > arr[i + 1]){
//                arr[0] = arr[i + 1];
//                arr[i + 1] = min;
//            }
//        }
////
//        System.out.println(Arrays.toString(arr));
//
//
//        // 第二轮比较
//        for(int i = 1; i <= arr.length - 1 - 1; i++){
//            // 默认第二个数是最小值
//            min = arr[1];
//            // 将索引0之后的数字和这个min比较，如果得到的数比这个min还要小，则交换位置
//            if(min > arr[i + 1]){
//                arr[1] = arr[i + 1];
//                arr[i + 1] = min;
//            }
//        }
//        System.out.println(Arrays.toString(arr));
//
//        // 第三轮比较
//        for(int i = 2; i <= arr.length - 1 - 1; i++){
//            // 默认第三个数是最小值
//            min = arr[2];
//            // 将索引0之后的数字和这个min比较，如果得到的数比这个min还要小，则交换位置
//            if(min > arr[i + 1]){
//                arr[2] = arr[i + 1];
//                arr[i + 1] = min;
//            }
//        }
//        System.out.println(Arrays.toString(arr));
//
//        // 第四轮比较
//        for(int i = 3; i <= arr.length - 1 - 1; i++){
//            // 默认第四个数是最小值
//            min = arr[3];
//            // 将索引0之后的数字和这个min比较，如果得到的数比这个min还要小，则交换位置
//            if(min > arr[i + 1]){
//                arr[3] = arr[i + 1];
//                arr[i + 1] = min;
//            }
//        }
//        System.out.println(Arrays.toString(arr));
//
//
//        // 第五轮比较
//        for(int i = 4; i <= arr.length - 1 - 1; i++){
//            // 默认第五个数是最小值
//            min = arr[4];
//            // 将索引0之后的数字和这个min比较，如果得到的数比这个min还要小，则交换位置
//            if(min > arr[i + 1]){
//                arr[4] = arr[i + 1];
//                arr[i + 1] = min;
//            }
//        }
//        System.out.println(Arrays.toString(arr));
//
//
//        // 第六轮比较
//        for(int i = 5; i <= arr.length - 1 - 1; i++){
//            // 默认第六个数是最小值
//            min = arr[5];
//            // 将索引0之后的数字和这个min比较，如果得到的数比这个min还要小，则交换位置
//            if(min > arr[i + 1]){
//                arr[5] = arr[i + 1];
//                arr[i + 1] = min;
//            }
//        }
//        System.out.println(Arrays.toString(arr));
    }
}

```



#### 插入排序

将数组分为两个区间 一个有序区间 一个是无序区间

```

```

#### 快速排序

特点 快

JAVA提供了排序方法，这个sort排序方法默认用的是快排

```java
import java.util.Arrays;
public class Demo03{
    public static void main(String[] args){
        int[] arr = {20,73,68,95,45,13,94,};
        Arrays.sort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
```





## 快捷方式

1. ctrl+Y 快速删除一行
2. **Alt+enter 提示**
3. Alt+insert:新建工程
4. 在Java中，可以使用`\n`实现换行。`\n`是一个特殊字符，表示换行符。注意，在字符串中使用`\n`时，需要将它放在双引号中。在Java中，双引号用于表示字符串常量
5. ".var",例子：s.nextInt().var+回车  ->int  i = s.nextInt()<自动创建变量>。
6. 占位符%d(整型)，%f（浮点型），%b(布尔型)，%n(换行符)，%.2f表示保留两位小数。
7. Math.*pow*(2,3),对2取3次幂
8.   243/1%10=取个位数，
   243/10%10=十位数，    
   243/100%10=百位数
9. alt +左键选中多行，可以多行编辑
10. 双击+shift，查看源文档
11. 按住ctrl再按下删除。快速删除

