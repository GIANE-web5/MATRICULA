FROM eclipse-temurin:21-jdk

RUN apt-get update && \
    apt-get install -y tzdata fonts-dejavu-core && \
    rm -rf /var/lib/apt/lists/*

ENV TZ='America/Lima'
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

EXPOSE 8080

ADD target/ms-matricula-back.jar ms-matricula-back.jar

ENTRYPOINT ["java", "-jar", "ms-matricula-back.jar"]