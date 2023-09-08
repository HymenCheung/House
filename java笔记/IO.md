## File

##### 构造方法

```java
File(String pathname)                    // 根据传入的路径创建File对象
File(String parent, String child)        // 根据传入进来的父路径和子路径创建File对象
File(File parent, String child)            // 根据传入进来的File父抽象路径和子路径创建File对象
```

```java
// 根据传入的路径创建File对象
File file = new File("D:\\java27\\tmp");
// 根据传入进来的父路径和子路径创建File对象
File file1 = new File("D:\\java27\\tmp\\","Animal.java");
File file3 = new File("D:\\java27\\tmp\\","Dog.java");
// 根据传入进来的File父抽象路径和子路径创建File对象
File file2 = new File(file,"Cat.java");
```

##### 常用方法

```java
	File file = new File("D:\\env");				//创建文件对象
boolean canRead()                                    // 这个文件或者文件夹是否可读
    System.out.println(file.canRead());				// true
boolean canWrite()                                    // 这个文件或者文件夹是否可写
    System.out.println(file.canWrite());			// true    
boolean delete()           // 删除文件或目录 如果删除的对象是一个文件夹，必须是空文件夹才能删除
    System.out.println(file.delete());					//true
boolean createNewFile() throws IOException              // 将这个路径表示的文件创建出来
    File file1 = new File("D:\\bbc.txt");
    System.out.println(file1.createNewFile());			//true
boolean exists()									//判断当前文件或目录是否存在
    System.out.println(file.exists());				//true
String getAbsolutePath()                            // 返回此文件的绝对路径
    System.out.println(file1.getAbsolutePath());	//D:\bbc.txt
String getName()                                    // 返回文件或者文件夹的名字 
    System.out.println(file1.getName());			//bbc.txt
String getParent()                              // 返回此文件或者文件夹的上一级绝对路径
    System.out.println(file1.getParent());		//D:\
String getParentFile()                             // 返回上级绝对路径的file对象
    File parentFile = file1.getParentFile();		// debug中 parentFile="D:\" 
String getPath()									//获取file对象的字符串，建议使用绝对路径
    System.out.println(file1.getPath());			//D:\bbc.txt
boolean isDirectory()                                // 判断此File对象是否是目录或文件夹
    System.out.println(file1.isDirectory());		//false
boolean isFile()                                    // 判断此File对象是否是文件
    System.out.println(file1.isFile());				// true
long lastModified()                                    // 返回最后一次修改的时间
    System.out.println(file1.lastModified());		//1686225269782
long length()                                        // 返回文件的字节个数
    System.out.println(file1.length());				// 13
String[] list()					//返回当前File对象表示的目录下面所有的文件或者文件夹的名称
    File file2 = new File("D:\\");					//盘符需带有\\,不然显示的是当前文件
    String[] s = file2.list();						//[$RECYCLE.BIN, abc.txt, 		adc.txt, bbc.txt, Demo, edc.txt, env, idea, Markdown, MOCKplus, SunloginClient, 	System Volume Information, Typora, WeChat]
File[] listFiles() 								//返回的是当前File对象表示的目录下的所有文	件和目录的File对象（有别于字符串）
    File[] file3 = file2.listFiles();
	System.out.println(Arrays.toString(file3));		//[D:\$RECYCLE.BIN, D:\abc.txt, 	D:\adc.txt, D:\bbc.txt, D:\Demo, D:\edc.txt, D:\env, D:\idea, D:\Markdown, 		D:\MOCKplus, D:\SunloginClient, D:\System Volume Information, D:\Typora, 		D:\WeChat]
static File[] listRoots()                            // 获取系统的所有盘符
    File[] files = file1.listRoots();
    System.out.println(Arrays.toString(files));		//[C:\, D:\, E:\]
boolean setReadOnly()                                // 将文件设置为只读
    System.out.println(file1.setReadOnly());		//true
boolean setWritable(boolean writable)                 // 设置文件是否可写
    System.out.println(file1.setWritable(false));		//true
boolean mkdir()                                        // 创建文件夹
    File file4 = new File("D:\\AAA");
    System.out.println(file4.mkdir());				//true
boolean mkdirs()                                    // 创建文件夹,可以创建多级目录
    File file5 = new File("D:\\AAA\\bb\\cd");
    System.out.println(file5.mkdirs());				//true
```

统计文件夹大小方法

```java
package com.atstudy;
import java.io.File;
public class Tess03 {
	public static void main(String[] args) {
		FileUtil fileUtil = new FileUtil();
		System.out.println(fileUtil.getSize("D:\\抖音"));
		System.out.println(fileUtil.getSize("D:\\LIFE"));
	}

}
class FileUtil{
	public static long getSize(String path){
		//1.创建这个路径对应的File对象
		File file = new File(path);
		//2.判断这个路径表示的是文件还是目录
		//2.1如果是文件，直接返回这个文件的大小
		if(file.isFile()){
			return  file.length();
		}
		//3.如果这个路径表示的是目录
		//3.1获取这个路径下的所有文件或目录的File数组
		File[] files = file.listFiles();
		//定义一个变量用于累计文件大小
		long size = 0;
		//遍历这个数组
		for (File file1 : files){
			//4.判断遍历到的是文件还是目录
			if (file1.isFile()){
				//4.1如果这个File对象是文件，则将这个文件的大小累加到某个变量中
				size += file1.length();
			}else{
				//4.2如果这个File对象是目录，则再次调用这个getSize方法
				size += getSize(file1.getAbsolutePath());
			}
		}
		return size;
		}
}
```



## IO

输入和输出都是从内存的角度出发

| 流向     | 操作的单位 | 顶级父类                |
| ------ | ----- | ------------------- |
| 输入流(读) | 字节    | InputStream(字节输入流)  |
| 输出流(写) | 字节    | OutputStream(字节输出流) |
| 输入流    | 字符    | Reader(字符输入流)       |
| 输出流    | 字符    | Writer(字符输出流)       |

### InputStream

只学FileInputStream，从文件中读取数据

##### 构造方法

```java
FileInputStream(String name) throws FileNotFoundException                // 创建一个字节输入流
```

##### 常用方法

```java
	FileInputStream fis = new FileInputStream("D:\\adc.txt");
int read() throws IOException                    // 一次读取一个字节，没有内容可以读取了，就返回-1.下面同理
    System.out.println(fis.read());				// 49
int read(byte b[]) throws IOException            // 一次读取一个byte数组，返回读取到的字节个数，并将读取到的内容放到byte数组中
    byte[] bytes = new byte[5];
    System.out.println(fis.read(bytes));		//5
    System.out.println(Arrays.toString(bytes)); //[49, 101, 49, 97, 98]
int read(byte b[], int off, int len)            // 一次读取一个byte数组,返回读取到的字节个数,可以指定从哪个索引位置开始放，可以指定读取几个字节    
    byte[] bytes1 = new byte[8];
    System.out.println(fis.read(bytes1,01,4));	//3
    System.out.println(Arrays.toString(bytes1));//[0, 99, 102, 103, 32, 0, 0, 0]
long skip(long n) throws IOException            // 跳过几个字节不去读取
    System.out.println(fis.skip(4));			// 4
    System.out.println(fis.read());				// 32    Unicode 空格  == 32
int available() throws IOException                // 返回还有几个字节没有被读取，只适合小文件
    System.out.println(fis.available());		// 2
```

##### 如何读取文件内容

```java
// jdk7的新特性
try(FileInputStream fis = new FileInputStream("D:\\test.txt")){
    // 读取文件
    // 建立一个byte数组用来读取文件
    byte[] bytes = new byte[1024];
    // 定义一个变量类接收每次读取到的字节个数
    int len;
    // 循环往byte数组中读取内容，判断读取到的字节个数是否为-1
    while ((len = fis.read(bytes)) != -1){
        System.out.println(new String(bytes,0,len));
    }
} catch (IOException e) {
    e.printStackTrace();
} 
```

### OutputStream

只学FileOutputStream，输入(向文件里面写数据)，单位是字节

##### 构造方法

```java
FileOutputStream(String name) throws FileNotFoundException
```

##### 常用方法

```java
    //FileOutputStream fos = new FileOutputStream("D:\\bbc.txt");
	FileOutputStream fos = new FileOutputStream("D:\\bbc.txt",true);//true表示连续写		入
void write(int b) throws IOException                            // 写入一个字节
    fos.write(99);												//写入c
void write(byte b[]) throws IOException                            // 写入一个字节写入一个字节数组
    byte[] bytes = {32,49,88,111};
	fos.write(bytes);											//在文件中写入 1Xo
void write(byte b[], int off, int len) throws IOException          // 写入一个字节写入一个字节数组,指定从哪里开始，写多长
    byte[] bytes1 = {52,80,90,60};
	fos.write(bytes1,1,2);										//输入PZ
```

### Reader

只学一个FileReader，是一个字符输入流

##### 构造方法

```java
FileReader(String fileName) throws FileNotFoundException        
```

##### 常用方法

```java
	FileReader fileReader = new FileReader("D:\\adc.txt");
int read() throws IOException                //一次读取一个字符，并将读取到的字符转成数字返回，没有内容可读取返回-1，下面同理
    System.out.println(fileReader.read());	// 返回65
int read(char cbuf[]) throws IOException    // 一次读取一个字符数组，并将读取到了几个字符返回
    char[] chars = new char[5];
	System.out.println(fileReader.read(chars));			//返回5
	System.out.println(Arrays.toString(chars));			//[张, b, c, e, g]
int read(char cbuf[], int off, int len) throws IOException            // // 一次读取一个字符数组，并将读取到了几个字符返回,可以指定从数组中的哪个位置开始存储，读取多长
    char[] chars1 = new char[6];
	System.out.println(fileReader.read(chars1,2,4));	//返回4
	System.out.println(Arrays.toString(chars1));		//[ ,  , 风, 中, 楼, 9]
```

##### 读取文件

```java
public static void main(String[] args) {
    try (FileReader reader = new FileReader("D:\\java27\\AAA.txt");){
        // 如何读取文件
        char[] chars = new char[3];
        int len;
        while ((len = reader.read(chars)) != -1){
            System.out.println(new String(chars,0,len));
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}
```

### Writer

只学一个FileReader，是一个字符输出流

##### 构造方法

```java
FileWriter(String fileName) throws IOException
FileWriter(String fileName, boolean append) throws IOException            // 追加写入
```

##### 常用方法

```java
	FileWriter fileWriter = null;
	//FileWriter fileWriter = new FileWriter("D:\\adc.txt");
	fileWriter = new FileWriter("D:\\edg.txt",true);		//追加输出
void write(int c) throws IOException                // 一次写入一个字符
    fileWriter.write("c");							// 写入c
void write(char cbuf[]) throws IOException            // 一次写入一个字符数组
    char[] chars = {'A','B','c','d'};
	fileWriter.write(chars);						//输出ABcd
void write(char cbuf[], int off, int len) throws IOException  // 一次写入一个字符数组，指定从哪里开始写，写多长
    char[] chars1 = {'张','三','里','S'};
	fileWriter.write(chars1,0,3);					//输出张三里
void flush() throws IOException                    // 刷新流，最好写完刷新一下
    fileWriter.flush();
```

### 高效缓冲流

高效缓冲流内部维护了一个8kb的缓冲区，数据先加载到缓冲区中，读写从缓冲区种读写，可以减少io次数

##### BufferedInputStream

字节输入缓冲流，使用起来和上面的字节输入流一样，但构造方法里需传入new  FileInputStream()

##### BufferedOutputStream

字节输出缓冲流，使用起来和上面的字节输入流一样,但构造方法里需传入new  FileIOutputStream()

##### BufferedReader

字符输入缓冲流，有一个特有方法，其他的使用和字符输入流一样

```java
String readLine() throws IOException            // 一次读取一行字符
```

##### BufferedWriter

字符输出缓冲流，有一个特有方法，其他的使用和字符输入流一样

```java
void newLine() throws IOException                // 写入一个换行符
void flush() throws IOException                    // 写入数据不要忘记调用这个方法，可以将缓冲区中的内容刷新到外存中
```

### 文件拷贝

使用字节流拷贝

```java
public static boolean copy(String src, String desc){
    // 先使用输入流读取文件内容，在使用输出流去将读取到的内容写到另一个文件中
    try (FileInputStream fis = new FileInputStream(src);            
         FileOutputStream fos = new FileOutputStream(desc);){
        // 定义一个byte数组用来接收读取到的内容
        byte[] bytes = new byte[1024];
        int len;
        // 边读边写
        while ((len = fis.read(bytes)) != -1){
            fos.write(bytes,0,len);
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    return true;
}
```

使用字符流拷贝

```java
// 自己完成
```

### 文件夹拷贝

```java
/**
 * 拷贝文件夹
 * @param src           源文件夹
 * @param desc          目标路径
 * @return              是否拷贝成功
 */
public static boolean copyFolder(String src, String desc){
    File srcFile = new File(src);
    File descFile = new File(desc);
    if (!srcFile.isDirectory()){
        throw new RuntimeException("这个路径不是一个文件夹");
    }
    // 确定src是一个文件夹
    descFile.mkdirs();          // 将可能不存在的文件夹创建出来
    // 获取到源文件夹下的所有File对象
    File[] files = srcFile.listFiles();
    if (files != null){
        for (File file : files) {
            if(file.isFile()){
               new File(desc + "\\" + srcFile.getName()).mkdir();
                copy(file.getAbsolutePath(),desc+"\\"+srcFile.getName()+"\\"+file.getName());
            }else {
                new File(desc + "\\" + srcFile.getName()).mkdir();
                copyFolder(file.getAbsolutePath(),desc + "\\" + srcFile.getName());
            }
        }
    }
    return true;
}

/**
 * 拷贝文件
 * @param src              源文件绝对路径
 * @param desc              目标文件绝对路径
 * @return                  拷贝是否成功
 */
public static boolean copy(String src, String desc){
    // 先使用输入流读取文件内容，在使用输出流去将读取到的内容写到另一个文件中
    try (FileInputStream fis = new FileInputStream(src);
         FileOutputStream fos = new FileOutputStream(desc);){
        byte[] bytes = new byte[1024];
        int len;
        while ((len = fis.read(bytes)) != -1){
            fos.write(bytes,0,len);
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    return true;
}
```

### 序列化

序列化：就是将对象信息保存到文件的过程

反序列化：从文件中将对象读取出来的过程

#### ObjectOutputStream

```java
class Person{}


try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("D:\\person"));){
    // 创建Person对象
    Person person = new Person("张三",18);
    // 将这个对象写入到文件
    oos.writeObject(person);
} catch (IOException e) {
    e.printStackTrace();
}
```

#### ObjectInputStream

```java
try ( ObjectInputStream ois = new ObjectInputStream(new FileInputStream("D:\\person"));){
    Object o = ois.readObject();
    if (o instanceof Person){
        // 因为拿出来的是Object类型，没有Person特有方法，所以要强转
        Person person = (Person) o;
        System.out.println(person);
        person.doSome();
    }
} catch (IOException | ClassNotFoundException e) {
    throw new RuntimeException(e);
}
```

#### Serializable

*没有实现Serializable接口的类无法被序列化，这个接口只是一个标识接口(没有任何东西的接口)，给jvm看的，表示可以被序列化*

#### 序列化版本号

一个类如果没有显示声明序列化版本号，那么一旦改动，jvm就认为改动前后是不同的两个类，所以需要手动添加序列化版本号

```java
class Person{
    static final long serialVersionUID = 1L;        // 这里一般随便写一个数字就可以了，因为全类名相同并且序列化版本号也相同的情况太少了，不至于和别人的撞上
}
```

#### transient

游离的，被它修饰的成员不会参与序列化

### io结合properties

可以将一些重要的但是可能要经常修改的信息写入到一个文件中，到时候直接修改文件即可

application配置类

```properties
username=zhangsan
password=123456
```

```java
// 先创一个流（一般是字符流）用来读取配置文件
FileReader reader = new FileReader("application");            // 注意，直接写的文件必须放在项目根路径下，比如今天创建的day12
// 创properties集合对象
Properties properties = new Properties();
// 将流中的内容加载到集合中
properties.load(reader);
// 可以调用集合的方法以key-value形式获取
String username = properties.getProperty("username");
System.out.println(username);
String password = properties.getProperty("password");
System.out.println(password);
```

配置文件通常是以.properties结尾的

使用properties和ResourceBundle读取配置文件

```java
//使用ResouseBundle读取配置文件

```

