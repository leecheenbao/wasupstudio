FROM eucm/maven

# Set the timezone
RUN apk --no-cache add tzdata \
    && ln -sf /usr/share/zoneinfo/Asia/Taipei /etc/localtime \
    && echo "Asia/Taipei" > /etc/timezone

# Set the locale to UTF-8
ENV LANG C.UTF-8

WORKDIR /app
COPY . .
RUN mvn package

CMD ["java", "-jar", "target/wasupstudio-0.0.1-SNAPSHOT.jar"]
