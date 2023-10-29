FROM amazoncorretto:17

COPY jungko-server-0.0.1-SNAPSHOT.jar jungko.jar

RUN ln -sf /usr/share/zoneinfo/Asia/Seoul /etc/localtime

CMD java -jar -Dspring.profiles.active=prod ${JAVA_OPTS} jungko.jar --spring.config.location=file:/resources/application-prod.yml
