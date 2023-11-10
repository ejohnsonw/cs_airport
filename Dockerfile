FROM gradle:jdk11-alpine AS BUILD_STAGE
COPY --chown=gradle:gradle . /home/gradle
RUN gradle assemble || return 1

FROM openjdk:11.0.11-jre
ENV ARTIFACT_NAME=airport-0.1-all.jar
ENV APP_HOME=/app
COPY --from=BUILD_STAGE /home/gradle/build/libs/$ARTIFACT_NAME $APP_HOME/
WORKDIR $APP_HOME
RUN groupadd -r -g 1000 user && useradd -r -g user -u 1000 user
RUN chown -R user:user $APP_HOME
RUN chgrp -R 0 $APP_HOME && \
chmod -R g=u $APP_HOME
USER user
ENTRYPOINT exec java -Xms2048M -Xmx2048M -jar ${ARTIFACT_NAME}
