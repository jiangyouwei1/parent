FROM java:8
#容器中应用程序的运行端口
EXPOSE 8092

ENV LOG_NAME  oauth

ENV NAME oauth-1.0-SNAPSHOT.jar

ADD $NAME $NAME
#在此配置了cmd命令 docker run时可以不知道
CMD java -server -Xms512m -Xmx1024m  -Duser.timezone=GMT+8 -jar $NAME --spring.profiles.active=uat
