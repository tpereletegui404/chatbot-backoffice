FROM gradle:7.2.0-jdk16 as builder

USER root
ARG COMMIT_SHA

ADD . .

RUN printf %s ".${COMMIT_SHA}" >> VERSION

RUN gradle --parallel --build-cache -Dorg.gradle.console=plain -Dorg.gradle.daemon=false :shadowJar

###########################################################################
FROM openjdk:16-alpine

ENV PORT 6060

WORKDIR /app

COPY --from=builder /home/gradle/build/libs/chatbot-backoffice_api.jar finanz_api.jar

EXPOSE $PORT

CMD java -Xms256m -Xmx256m -Xss512k -jar chatbot-backoffice_api.jar
