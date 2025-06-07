FROM openjdk:17-jdk-slim

# 애플리케이션 위치 설정
WORKDIR /app

# JAR 파일 복사
COPY build/libs/*.jar app.jar

# 프로파일 환경 설정
ENV SPRING_PROFILES_ACTIVE=prod

# 포트 노출
EXPOSE 8080

# 실행 명령
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
