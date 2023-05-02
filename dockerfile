FROM eucm/maven

WORKDIR /app
COPY . .
RUN mvn package

CMD ["java", "-jar", "target/wasupstudio-0.0.1-SNAPSHOT.jar"]