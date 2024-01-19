FROM eucm/maven

# 設置時區
RUN apk add --no-cache tzdata
ENV TZ=Asia/Taipei

ENV LANG C.UTF-8

WORKDIR /app
COPY . .
RUN mvn package

CMD ["java", "-jar", "target/wasupstudio-0.0.1-SNAPSHOT.jar"]
