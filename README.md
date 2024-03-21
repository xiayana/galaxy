# galaxy
## 打包方式  此方式可以后端提供jar包只要控制版本
在deaven根目录下 打开cmd窗口  执行
mvn clean install -Dmaven.test.skip=true -Ppro
打包完成后在draven-dsl-engine下面的target下
将原项目kill进程kill掉
将draven-dsl-engine-0.0.1-SNAPSHOT.jar取出放到指定服务器文件夹下替换原来jar
执行
nohup java -jar draven-dsl-engine-0.0.1-SNAPSHOT.jar --spring.config.location=/home/lab/source_code/application-pro.properties --server.port=8097&
spring.config.location后面的路径为配置文件的路径到文件本身
重启
