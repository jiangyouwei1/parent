FROM java:8
#容器中应用程序的运行端口
EXPOSE 8081

ENV LOG_NAME  eureka

ENV NAME eureka-1.0.0.jar

ADD $NAME $NAME
#在此配置了cmd命令 docker run时可以不知道
CMD java -server -Xms512m -Xmx1024m  -Duser.timezone=GMT+8 -jar $NAME --spring.profiles.active=local
