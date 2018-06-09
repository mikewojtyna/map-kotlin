FROM java:8

CMD java ${JAVA_OPTS} -Djava.security.egd=file:/dev/./urandom -jar /map-advanon-1.0.0.jar

EXPOSE 8080

ADD *.jar /map-advanon-1.0.0.jar