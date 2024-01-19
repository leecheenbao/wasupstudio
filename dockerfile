FROM eucm/maven

WORKDIR /app
COPY . .

RUN ln -sf /usr/share/zoneinfo/Asia/Taipei /etc/localtime
RUN mvn package

CMD ["java", "-jar", "-Duser.timezone=UTC", "target/wasupstudio-0.0.1-SNAPSHOT.jar"]
