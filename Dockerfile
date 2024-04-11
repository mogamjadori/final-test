FROM openjdk:8-alpine

# 작업 디렉토리 설정
WORKDIR /src

# 소스 코드 복사
COPY ./src /src

# 엔트리포인트 설정
ENTRYPOINT ["java", "-jar", "app-gymfit.jar"]

# 포트 노출
EXPOSE 80
