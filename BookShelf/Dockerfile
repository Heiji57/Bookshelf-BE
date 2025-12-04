FROM eclipse-temurin:17-jdk-jammy AS builder

WORKDIR /app

COPY . .

RUN ./gradlew clean build -x test


FROM eclipse-temurin:17-jre-jammy

COPY --from=builder /app/build/libs/BookShelf-0.0.1-SNAPSHOT.jar app.jar

# 컨테이너가 시작될 때 명령어
ENTRYPOINT ["java", "-jar", "/app.jar"]

# 포트 정보(명시용이고, 사실은 docker-compose에서 처리)
EXPOSE 8080