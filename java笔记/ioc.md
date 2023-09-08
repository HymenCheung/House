### Dom4j解析XML

1.导入dom4j依赖

2.编写xml配置文件，最好放在resources资源根目录

```xml
<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<beans>
    <bean id="student" class="com.atstudy.entity.pojo.Student"/>

    <bean id="user" class="com.atstudy.entity.pojo.User"/>

    <bean id="dept" class="com.atstudy.entity.pojo.Dept"/>

    <bean id="userDao" class="com.atstudy.dao.impl.UserDaoImpl"/>

    <bean id="userService" class="com.atstudy.service.impl.UserServiceImpl"/>

    <bean id="userController" class="com.atstudy.controller.UserController"/>

</beans>



```



3.编写程序使用SAXReader进行解析

```java
try {
    // 创建SAXReader对象
    SAXReader reader = new SAXReader();
    // 使用类加载器读取到这个xml配置文件的绝对性路径
    String file = Application.class.getClassLoader().getResource("spring.xml").getFile();
    // 需要传入一个xml文件的绝对路径
    // 读取到xml文件
    Document read = reader.read(file);
    // Document方法
        // 1.getRootElement()  获取到根元素
    // 获取到根元素
    Element rootElement = read.getRootElement();
    // 获取获取到所有的bean元素
    List<Element> bean = rootElement.elements("bean");
    for (Element element : bean) {
        // 获取到当前元素中指定属性的值
        System.out.println(element.attributeValue("id"));
        System.out.println(element.attributeValue("class"));
    }
} catch (Exception e) {
    e.printStackTrace();
}
```



### 设计ioc托管容器

```java
/**
 * ioc容器，用于存放需要托管的对象
 */
public class ApplicationContext {

    // 这个map集合是用于存储对象的容器
    private Map<String,Object> beans = new HashMap<>();


    public ApplicationContext() {
    }

    public ApplicationContext(String xmlFilePath) {
        init(xmlFilePath);
    }

    /**
     * 加载配置文件并初始化bean
     * @param xmlFilePath   配置文件的路径(需要写在资源根目录下)
     */
    private void init(String xmlFilePath){

        try {
            // 使用类加载器获取到类路径下(资源根路径)的资源
            ClassLoader classLoader = getClass().getClassLoader();
            InputStream in = classLoader.getResourceAsStream(xmlFilePath);

            // 创建SAXReader对象
            SAXReader reader = new SAXReader();
            // 读取文档
            Document document = reader.read(in);
            // 获取到根标签beans
            Element rootElement = document.getRootElement();
            // 获取到所有的bean标签
            List<Element> bean = rootElement.elements("bean");
            // 遍历这些bean标签，获取到class的值(全类名)
            for (Element element : bean) {
                // 获取到class属性的值
                String className = element.attributeValue("class");
                // 使用反射创建出这个类的实例
                Class<?> aClass = Class.forName(className);
                Object o = aClass.getDeclaredConstructor(null).newInstance();
                // 对象创建出来之后，将它放到容器中
                beans.put(aClass.getSimpleName(),o);
            }

        } catch (DocumentException | ClassNotFoundException | NoSuchMethodException | InstantiationException |
                 IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        // 遍历容器
        for (Map.Entry<String, Object> entry : beans.entrySet()) {
            System.out.println(entry.getKey() + "  =========  " + entry.getValue());
        }

    }

}
```



```java
/**
 * 从容器中获取到指定类型的对象
 * @param tClass
 * @return
 * @param <T>
 */
public <T> T getBean(Class<T> tClass){
    // 从容器中取值
    return (T) beans.get(tClass.getSimpleName());
}
```

还有以下几个问题：

1.现在需要托管的对象并不多，可以一个个写在配置文件中，但是数量多了之后怎么办

```xml
<beans>
    <!--这里将所有需要托管的包写进来，中间用,分隔-->
    <scan-packages  packages="controller,dao.impl,service.impl,entity.pojo"/>

</beans>

```



2.是所有对象都需要进行托管吗，有没有一种办法可以区分需要托管和不需要托管的对象呢

```java
    /**
     * 加载配置文件并初始化bean
     * @param xmlFilePath   配置文件的路径(需要写在资源根目录下)
     */
    private void init(String xmlFilePath){

        try {
            // 使用类加载器获取到类路径下(资源根路径)的资源
            ClassLoader classLoader = getClass().getClassLoader();
            InputStream in = classLoader.getResourceAsStream(xmlFilePath);

            // 创建SAXReader对象
            SAXReader reader = new SAXReader();
            // 读取文档
            Document document = reader.read(in);
            // 获取到根标签beans
            Element rootElement = document.getRootElement();
            // 获取到根标签中的scan-packages
            List<Element> elements = rootElement.elements("scan-packages");
            // 因为相同名字的标签可能存在多个，所以获取到的是一个list，
            Element element = elements.get(0);
            String s = element.attributeValue("packages");
            // 先按照,分隔
            String[] split = s.split(",");      // 这个数组中就是一个个需要托管的包
            // 遍历这些包名
            for (String packageName : split) {
                // 将.替换成/
                String replace = packageName.replace(".", "/");
                // 替换之后就是我们需要扫描的包
                // 我们需要获取到当前包下所有.class结尾的文件

                //首先要获取到当前包的绝对路径
                // ApplicationContext.class.getResource("") 这样获取到的就是ApplicationContext类所在包的绝对路径
                File file = new File(ApplicationContext.class.getResource("/" + replace).getPath());

                // 代码执行到这里已经获取到了需要托管的包的绝对路径

                // 需要获取当前包名生成的文件路径 的所有的文件资源
                for (File f : file.listFiles()) {
//                    System.out.println(listFile.getAbsolutePath());
                    // 判断当前遍历到的文件是否是以.class结尾的文件
                    if(f.getName().endsWith(".class")){
//                        System.out.println(f.getName());
                        // 将当前文件的.class后缀去除
                        String className = f.getName().replace(".class", "");
                        // 在前面拼接上包名
                        className = packageName + "." + className;

                        // 这里获取到了当前包下.class的全类名
                        Class<?> aClass = Class.forName(className);

                        // 关键是每一个类都需要进行托管吗
                        // 判断这个类头顶上有没有@Conpnent注解，如果有，则创建对象并放入容器
                        if(aClass.getAnnotation(Component.class) != null){
                            Object o = aClass.getDeclaredConstructor(null).newInstance();
                            // 将对象添加到容器中
                            beans.put(aClass.getSimpleName(),o);
                        }


                    }
                }

            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        // 遍历容器
        for (Map.Entry<String, Object> entry : beans.entrySet()) {
            System.out.println(entry.getKey() + "  =========  " + entry.getValue());
        }

    }
```





3.对象确实交给容器管理了，但是相应的对象还没有赋值，例如UserServiceImpl中的UserDao，它仅仅是一个变量，并没有实际赋值

```java
/**
     * 加载配置文件并初始化bean
     * @param xmlFilePath   配置文件的路径(需要写在资源根目录下)
     */
    private void init(String xmlFilePath){

        try {
            // 使用类加载器获取到类路径下(资源根路径)的资源
            ClassLoader classLoader = getClass().getClassLoader();
            InputStream in = classLoader.getResourceAsStream(xmlFilePath);

            // 创建SAXReader对象
            SAXReader reader = new SAXReader();
            // 读取文档
            Document document = reader.read(in);
            // 获取到根标签beans
            Element rootElement = document.getRootElement();
            // 获取到根标签中的scan-packages
            List<Element> elements = rootElement.elements("scan-packages");
            // 因为相同名字的标签可能存在多个，所以获取到的是一个list，
            Element element = elements.get(0);
            String s = element.attributeValue("packages");
            // 先按照,分隔
            String[] split = s.split(",");      // 这个数组中就是一个个需要托管的包
            // 遍历这些包名
            for (String packageName : split) {
                // 将.替换成/
                String replace = packageName.replace(".", "/");
                // 替换之后就是我们需要扫描的包
                // 我们需要获取到当前包下所有.class结尾的文件

                //首先要获取到当前包的绝对路径
                // ApplicationContext.class.getResource("") 这样获取到的就是ApplicationContext类所在包的绝对路径
                File file = new File(ApplicationContext.class.getResource("/" + replace).getPath());

                // 代码执行到这里已经获取到了需要托管的包的绝对路径

                // 需要获取当前包名生成的文件路径 的所有的文件资源
                for (File f : file.listFiles()) {
//                    System.out.println(listFile.getAbsolutePath());
                    // 判断当前遍历到的文件是否是以.class结尾的文件
                    if(f.getName().endsWith(".class")){
//                        System.out.println(f.getName());
                        // 将当前文件的.class后缀去除
                        String className = f.getName().replace(".class", "");
                        // 在前面拼接上包名
                        className = packageName + "." + className;

                        // 这里获取到了当前包下.class的全类名
                        Class<?> aClass = Class.forName(className);

                        // 关键是每一个类都需要进行托管吗
                        // 判断这个类头顶上有没有@Conpnent注解，如果有，则创建对象并放入容器
                        if(aClass.getAnnotation(Component.class) != null){
                            Object o = aClass.getDeclaredConstructor(null).newInstance();
                            // 将对象添加到容器中
                            beans.put(aClass.getSimpleName(),o);
                        }


                    }
                }

            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        // 从这里开始完成这些成员属性的赋值

        // 遍历容器，获取到每一个需要托管的对象
        for (Map.Entry<String, Object> entry : beans.entrySet()) {
//            System.out.println("遍历到的对象 =========" +entry.getKey());
//            System.out.println("对象的类型 ====== " + entry.getValue());

            // 遍历每一个被托管对象的成员属性
            for (Field declaredField : entry.getValue().getClass().getDeclaredFields()) {
//                System.out.println("当前对象的成员属性的名字:  " + declaredField.getName());
                // 判断当前属性头顶上是否含有@AutoWrite注解,如果有，则想办法给他赋值
                if(declaredField.getAnnotation(AutoWrite.class) != null){

//                    System.out.println("需要赋值的成员属性名字====》" + declaredField.getName());
//                    System.out.println("需要赋值的成员属性的类型===>" + declaredField.getType().getSimpleName());

                    // 遍历容器，从容器中获取到当前属性类型的对象（但是容器中只有当前属性类型的实现类对象）
                    for (Map.Entry<String, Object> bean : beans.entrySet()) {

                        if(bean.getValue().getClass().getInterfaces().length > 0  &&      // 表示当前遍历到的对象存在接口
                            bean.getValue().getClass().getInterfaces()[0].equals(declaredField.getType())){  // 这个接口就是当前遍历到的属性的类型

                            // 想办法将这个对象赋值给这个属性
//                            System.out.println("需要将 " + bean.getValue());
//                            System.out.println("赋值给  " + declaredField.getName());

                            // 获取到当前属性的set方法
                            String setName = "set" + declaredField.getName().substring(0,1).toUpperCase() + declaredField.getName().substring(1);
//                            System.out.println("需要赋值的set方法的名字" + setName);

                            try {
                                // 通过set方法的名字获取到set方法
                                Method declaredMethod = entry.getValue().getClass().getDeclaredMethod(setName, new Class[]{declaredField.getType()});

                                // 调用这个set方法
                                declaredMethod.invoke(entry.getValue(),bean.getValue());

                                break;
                                // 找到目标对象进行赋值之后，结束这轮循环

                            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                                e.printStackTrace();
                            }

                        }

                    }


                }
            }
//            System.out.println("===========");



        }

    }

```



现在只有一个@Component注解，太单一了，现在我们想要让这个注解更加多元化，达到看到注解就知道这个类属于哪个层级

再创建三个注解对应Controller、Service、Dao这三层

```java
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Component
public @interface Controller {          // 这个注解对应Controller层
}
```

```java
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Component
public @interface Service {     // 对应Service层
}
```

```java
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Component
public @interface Repository {          // 这个注解对应的是Dao从层
}

```

如果用if else if判断是否含有这些注解代码可读性太差，直接封装一个方法（在容器中）

```java
/**
     * 判断某个类头顶上是否含有某个注解
     * @param clazz     需要判断的类
     * @param annotation    需要判断的注解
     * @return
     */
    public boolean hasAnnotation(Class clazz,Class annotation){
        // 先直接判断这个类头顶上是否含有指定注解
        if(clazz.getDeclaredAnnotation(annotation) != null){
            // 这个类头顶上直接带有这个注解
            return true;
        }else {
            // 先获取到当前类头顶上所有的注解
            Annotation[] declaredAnnotations = clazz.getDeclaredAnnotations();
            // 遍历这些注解，判断遍历到的这个注解头顶上是否含有指定注解
            for (Annotation declaredAnnotation : declaredAnnotations) {
                if(declaredAnnotation.annotationType().getDeclaredAnnotation(annotation) != null){
                    return true;
                }
            }
        }
        return false;
    }
```

判断某个类头顶上是否包含@Component注解，可以调用这个方法

```java
// 关键是每一个类都需要进行托管吗
// 判断这个类头顶上有没有@Conpnent注解，如果有，则创建对象并放入容器
if( hasAnnotation(aClass, Component.class) ){
    Object o = aClass.getDeclaredConstructor(null).newInstance();
    // 将对象添加到容器中
    beans.put(aClass.getSimpleName(),o);
}
```



@AutoWrite是按照类型进行注入，如果想要按照名称进行注入怎么办

```java
                if(declaredField.getAnnotation(Resource.class) != null){


                    // 遍历容器，从容器中获取到当前属性名字对应的值
                    for (Map.Entry<String, Object> bean : beans.entrySet()) {
                        String name = declaredField.getName();
                        name = name.substring(0,1).toUpperCase() + name.substring(1) + "Impl";

                        if(bean.getKey().equals(name)){
                            // 获取到当前属性的set方法
                            String setName = "set" + declaredField.getName().substring(0,1).toUpperCase() + declaredField.getName().substring(1);
//                            System.out.println("需要赋值的set方法的名字" + setName);

                            try {
                                // 通过set方法的名字获取到set方法
                                Method declaredMethod = entry.getValue().getClass().getDeclaredMethod(setName, new Class[]{declaredField.getType()});

                                // 调用这个set方法
                                declaredMethod.invoke(entry.getValue(),bean.getValue());

                                break;
                                // 找到目标对象进行赋值之后，结束这轮循环

                            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                                e.printStackTrace();
                            }



                        }

                    }


                }
```

