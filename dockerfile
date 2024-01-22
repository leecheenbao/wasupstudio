FROM eucm/maven

WORKDIR /app

# 将时区设置为Asia/Taipei
RUN ln -sf /usr/share/zoneinfo/Asia/Taipei /etc/localtime && echo "Asia/Taipei" > /etc/timezone

# 将整个项目拷贝到容器的/app目录下
COPY . .

# 构建Maven项目
RUN mvn package

# 启动应用程序
CMD ["java", "-jar", "target/wasupstudio-0.0.1-SNAPSHOT.jar"]
