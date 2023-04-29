FROM openjdk:11
ARG JAR_FILE=build/libs/sasca-api.jar
COPY ${JAR_FILE} ./sasca-api.jar
ENV TZ=Asia/Seoul
ENTRYPOINT ["java", "-jar", "./sasca-api.jar"]