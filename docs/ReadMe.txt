1、将 lombok.jar 拷贝到<eclipse_home>目录下，与eclipse.ini 相同目录
2、需要修改一下
	<eclipse_home>/eclipse.ini
   在末尾添加一行
   -javaagent:lombok.jar
   
3、项目启动，浏览器访问swagger方式很方便测试
   eg：http://localhost:7060/swagger-ui.html
   
   
说明：http://ip:port/v1/sms/send 发送短信rest接口

logback-spring.xml 直接放在jar中生效，如果放外部要生效必须再配置中指定文件路径。

测合并试1

