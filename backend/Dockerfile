# 选择官方的 OpenJDK 17 运行时基础镜像
FROM eclipse-temurin:17-jdk-jammy

# 维护者信息（可选）
LABEL maintainer="yileen@example.com"

# 将jar包复制到镜像中
COPY target/Herbal_visualization-0.0.1-SNAPSHOT.jar /app/app.jar

# 设置时区（可选）
ENV TZ=Asia/Shanghai

# 容器启动时执行jar包
ENTRYPOINT ["java", "-jar", "/app/app.jar"]

# 容器监听端口（你Spring Boot用的是10010）
EXPOSE 10010
