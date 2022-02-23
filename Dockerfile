FROM openjdk:11
RUN addgroup --system spring && adduser --system spring
USER spring:spring
COPY build/libs/ecommerce-0.0.1-SNAPSHOT.jar home/spring/application.jar
EXPOSE 9090
ENTRYPOINT ["java", "-jar", "/home/spring/application.jar"]