## IDEA新建SpringMVC项目后有三个xml文件，另一个不动，其他两个如下配置，然后应该就可以跑通并正常跳转了。

- web.xml

    ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
            version="4.0">

        <listener>
            <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
        </listener>

        <servlet>
            <servlet-name>dispatcher</servlet-name>
            <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
            <init-param>
                <param-name>contextConfigLocation</param-name>
                <param-value>/WEB-INF/applicationContext.xml</param-value>
            </init-param>
            <load-on-startup>1</load-on-startup>
        </servlet>
        <servlet-mapping>
            <servlet-name>dispatcher</servlet-name>
            <url-pattern>/</url-pattern>
        </servlet-mapping>
    </web-app>
    ```

- applicationContext.xml

    ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns:context="http://www.springframework.org/schema/context"
        xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">

        <context:component-scan base-package="cn.qujialin"/>

        <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
            <property name="prefix" value="/"></property>
            <property name="suffix" value=".jsp"></property>
        </bean>

    </beans>
    ```

**web.xml报错**

`'org.springframework.web.servlet.DispatcherServlet' is not assignable to 'javax.servlet.Servlet`

**解决方法**：Project Structure - Modules - 你的项目  - Dependencies,添加Tomcat library

![图片.png](https://i.loli.net/2020/01/08/TAaHgdupUnzjNMO.png)

![图片.png](https://i.loli.net/2020/01/08/NTtcAZsYOPKpwJD.png)

**问题解决参考博文：** [https://www.cnblogs.com/maximo/p/6971048.html](https://www.cnblogs.com/maximo/p/6971048.html)