FROM eucm/maven

WORKDIR /app
COPY . .
RUN mvn package

CMD ["java", "-jar", "target/wasupstudio-0.0.1-SNAPSHOT.jar"]

# Tag and push the image to Docker Hub
#ARG TAG=latest
#ARG REPO=aibolee

#RUN docker images --format "{{.ID}}" --filter "reference=aibolee/wasupstudio"
#RUN docker tag <image-id> $REPO/wasupstudio:$TAG
#RUN docker push $REPO/wasupstudio:$TAG
