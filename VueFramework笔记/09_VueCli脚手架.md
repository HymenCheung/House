## VueCli 脚手架

### 一：VueCli 脚手架

​        Vue-cli是Vue.js的脚手架，用于vue项目工程的自动生成、打包。

### 二：NodeJS

​        NodeJS是一个基于Chrome V8引擎的Javascript运行环境。

​        官网：https://nodejs.org/zh-cn/

​        下载对应版本的NodeJS安装程序，进行安装

​        在【命令提示符】中，输入命令：node -v

​        出现对应的NodeJS的版本号

### 三：NPM和CNPM

#### 3.1 NPM

​        Node Package Manager（NPM）是一个NodeJS包管理和分发工具，已经成为了非官方的发布Node模块（包）的标准。

​        

​        查看NPM安装的组件的路径，在【命令提示符】中，输入命令：

​        npm config get prefix

​        查看NPM版本，在【命令提示符】中，输入命令：npm -v

​        出现对应的NodeJS的版本号

​        

​        npm升级到最新版本，在【命令提示符】中，输入命令：

​        npm install -g npm --registry=https://registry.npm.taobao.org

#### 3.2 CNPM

​        CNPM是NPM的国内镜像

​        安装CNPM，在【命令提示符】中，输入命令：

​        npm install -g cnpm --registry=https://registry.npm.taobao.org

​        

​        查看CNPM的版本号，在【命令提示符】中，输入命令：cnpm -v

1. 清除NPM缓存：运行以下命令以清除NPM缓存：

```
npm cache clean --force复制代码
```

1. 卸载并重新安装NPM：按照以下步骤卸载并重新安装NPM：

   a. 在终端中运行以下命令以卸载全局安装的NPM：

   ```
   npm uninstall -g npm复制代码
   ```

   b. 然后，重新安装NPM：

   ```
   npm install -g npm
   ```

### 四：安装VueCli

​        安装VueCli，在【命令提示符】中，输入命令：

​        cnpm install -g @vue/cli@4.5.0 --registry=https://registry.npm.taobao.org

​        查看VueCli版本，在【命令提示符】中，输入命令：vue -V

### 五：创建VueCli工程

​        在【命令提示符】中，进入到想要创建项目工程的路径。

​        输入命令：vue create 项目名称

vue create --registry=https://registry.npm.taobao.org vuecli-01

流程：

一：是否需要使用淘宝镜像服务器，创建vue-cli项目，输入 y

npm config set registry https://registry.npm.taobao.org/

二：技术选型，选择 Manually Select Feature（手动选择技术选型）

​    2.1：技术清单：

​        Choose Vue Version（后续会提供Vue版本选择）

​        Babel（ECMAScript 向下兼容）

​        TypeScript（Javascript语法糖）

​        Progreesive Web App（PWA）Support（PWA支持）

​        Router（Vue路由）

​        Vuex（状态管理）

​        CSS Pre-processors（CSS预处理器）

​        Linter / Formatter（代码检测）

​        Unit Testing（单元测试工具）

​        E2E Testing（测试工具）

​    2.2：选择vue版本

​        3.x

​    2.3：是否使用（历史记录模式）的VueRouter路由系统，选择 y

​    2.4：选择CSS预处理器的产品，选择：Sass/SCSS（with node-sass）

​    2.5：选择ESLint代码检测的预设方式，选择：ESLint with errors prevention only

​    2.6：选择ESLint检测代码的时机，选择：lint on save（保存文件时就进行一次检测）

​    2.7：选择单元测试工具，选择：Jest

​    2.8：各个组件的配置方式，选择：In dedicated config files（每个组件在自己独立的配置文件中，进行配置）

​    2.9：是否要保存当前项目工程配置方案？vuecli@4.5.0

### 六：使用IDE导入项目进行开发

​        在 HBuilderx中，选择【文件】->【导入】->【从本地目录导入】

​        选择刚才创建的vue-cli项目工程文件夹

### 七：运行Vue-cli项目

​        在【命令提示符】中，进入刚才新建的vue-cli项目路径。

​		cd vuecli-01

​        执行名称：cnpm run serve

​        浏览器访问：

​        127.0.0.1:8080        （127.0.0.1  本机ip）        只能本机使用

​        localhost:8080        （localhost  本机域名）    只能本机使用

​        172.31.95.194:8080    （172.31.95.194  本机在局域网中的ip，每个电脑不一样）    在同一个局域网中的所有设备都能访问

### 八：Vue-cli项目安装依赖模块

​        根据vue-cli项目中的依赖配置：package.json，安装所依赖的模块

​        在【命令提示符】中，进入刚才新建的vue-cli项目路径。

​        执行名称：cnpm install --registry=https://registry.npm.taobao.org

### 九：Vue-cli项目工程结构

/node_modules/            当前项目工程所依赖的组件包

/public/                            存放不需要压缩的资源

​        index.html              当前项目工程的唯一页面（Single Page）

/src/                                 Vue项目的源代码

​        assets/                    存放需要压缩的资源，反网络爬虫

​        components/        存放Vue组件，小组件，可以复用

​        router/                    存放VueRouter路由代码

​        store/                      存放Vuex状态管理代码

​        views/                     存放Vue组件，页面级组件，不可复用

​        App.vue                 Vue根组件

​        main.js                  Javascript指令的入口

.eslintrc.js                    ESLint代码检测的配置文件

.gitignore                    git版本管理工具的配置文件

babel.config.js            Babel的配置文件

package.json              当前项目工程依赖组件的配置文件

package-lock.json      版本锁定package.json配置文件

README.md                当前项目工程的自述文件
