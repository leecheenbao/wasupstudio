FROM eclipse-temurin:8-jre

WORKDIR /app

# 將時區設為 Asia/Taipei
ENV TZ=Asia/Taipei
RUN ln -sf /usr/share/zoneinfo/Asia/Taipei /etc/localtime && echo "Asia/Taipei" > /etc/timezone

# CI 已執行 mvn package，直接使用產出的 fat jar（避免舊映像再打一次包導致 PKIX）
COPY target/wasupstudio-0.0.1-SNAPSHOT.jar app.jar

# 啟動應用程式
CMD ["java", "-jar", "app.jar"]
