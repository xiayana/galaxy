FROM octahub.8lab.cn:5000/base/jdk-maven:17 as builder 
ENV version 0.0.1
ENV JAVA_HOME=/usr/local/jdk-17.0.6
ENV CLASSPATH=.:JAVA_HOME/lib
ENV PATH=.:JAVA_HOME/bin:$PATH
WORKDIR /usr/local/project/
ADD . . 
RUN mvn clean install

FROM octahub.8lab.cn:5000/base/jdk-maven:17 
ENV version 0.0.1
ENV JAVA_HOME=/usr/local/jdk-17.0.6
ENV CLASSPATH=.:JAVA_HOME/lib
ENV PATH=.:JAVA_HOME/bin:$PATH
WORKDIR /usr/local/project/
COPY --from=builder /usr/local/project/target/bitMatchOAServer-0.0.1-SNAPSHOT.jar  /usr/local/project/
ADD ./src/main/resources/application.properties  /usr/local/project/application.properties
#ADD docker/sources.list /etc/apt/sources.list
RUN apt update && apt install locales -y && locale-gen en_US.UTF-8 
ENV LANG en_US.UTF-8
ENV LC_ALL en_US.UTF-8
ADD docker/entrypoint.sh /usr/bin/
#CMD /usr/local/jdk-17.0.6/bin/java -jar tusimaTask-dsl-engine-$version-SNAPSHOT.jar 
ENTRYPOINT ["/usr/bin/entrypoint.sh"]

