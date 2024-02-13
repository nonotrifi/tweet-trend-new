FROM openjdk:8
ADD jarstaging/com/valaxy/demo-workshop/2.1.6/demo-workshop-2.1.6.jar ttrend.jar
ENTRYPOINT ["java", "-jar", "ttrend.jar"]