FROM openjdk:11-slim
ARG VERSION=0.0.0
ARG GIT_COMMIT_SHA

ADD build/distributions/spring-boot.tar /
WORKDIR /spring-boot
ADD https://dtdg.co/latest-java-tracer /dd-java-agent.jar
ENV JAVA_OPTS -javaagent:/dd-java-agent.jar
ENV DD_SERVICE=ci-integration
ENV DD_VERSION=$VERSION
ENV DD_TAGS=git.commit.sha:$GIT_COMMIT_SHA

CMD [ "bin/spring-boot" ]
