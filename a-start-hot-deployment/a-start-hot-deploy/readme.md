# jboss docker
参考：https://github.com/jboss-dockerfiles/wildfly

# 热部署,动态加载,插件化
## ClassLoader 
1. 解决思路就是先获取当前类的Class，然后获取当前类的加载器，在自定义的加载器或者URLClassLoader加载器创建时指定为它们的父加载器，这样问题就会游刃而解了，可能平常我们测试写个简单的例子没遇到这个问题，因为我们那时的URLClassLoader或者自定义的加载器的父加载器都是JVM的第三次加载器即应用程序加载，它是专门加载classpath下边的或者指定的类或者jar的，依照双亲委托模型，肯定会找到引入路径的那个类或者jar的。
2. 使用Class.forName()的方式来动态加载指定的类，就不会存在这个问题，因为这种方式一方面是能初始化类的静态东西，再就是重要一点，就是采用的加载当前所在类的加载器来加载你指定的类，这样你在tomcat下那就是它的webApp加载器啊，肯定不再出现这个问题，可能直接就从缓存里找到了。
3. 核心代码
```
URL url = new URL(packageUrl);
// IDEA调试时与java运行时的ClassLoader是不同的,所以需要使用当前环境下的ClassLoader
ClassLoader loader = new URLClassLoader(new URL[]{url}, clazz.getClassLoader()) {
    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        try {
            String fileName = name.substring(name.lastIndexOf(".") + 1) + ".class";
            InputStream is = getClass().getResourceAsStream(fileName);
            if (is == null) {
                return super.loadClass(name);
            }
            byte[] b = new byte[is.available()];
            is.read(b);
            return defineClass(name, b, 0, b.length);

        } catch (IOException e) {
            e.printStackTrace();
            throw new ClassNotFoundException(name);
        }
    }
};
```
## 插件化核心思想
1. 面向接口
2. 从指定插件目录解析所有插件
3. 一个插件jar,一个类型,一个功能
4. 根据输入的插件名称去读取插件的实现