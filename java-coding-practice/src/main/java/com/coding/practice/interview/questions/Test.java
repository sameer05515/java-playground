package com.coding.practice.interview.questions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Arrays;

public class Test {
    public static void main(String[] args) {
        printNames("");
    }

    private static String baseSting = """
,
  {
    name:"<name>",
    projectType:"BACKEND",
    projectLocation:"<ROOT>/MyTestPrograms/<name>",
    isActiveDevelopmentGoingOn:false,
    purpose:"This project is created to learn java 8 - '<name>' ",
    requiredFeatures: "",
    techStacksUsed: [TSProps.SpringBoot],
    developmentHistory: "",
  } 
            """;

    private static String[] arr={"batch-processing-large-datasets-spring",
            "BCE-MicroServices",
            "BeerKayMultithreading",
            "boot-logging-file-example",
            "boot-primefaces-integration-example",
            "carousel-with-angularjs",
            "coding.ex",
            "CommandLineRunner",
            "consume.rest",
            "cron-expression",
            "CRUD Operation angular + eclipse App",
            "CRUD-Spring-Boot-JPA-MySQL",
            "download-using-streaming-response-body",
            "encryption-decryption",
            "EventLogging",
            "Exporter",
            "FileService",
            "GettringReadyWithForm",
            "GitRepoFetch",
            "Helper",
            "helpers",
            "hibTutorial",
            "JdbcDdl",
            "jdk8examples",
            "jpa-mappings",
            "JSF-Primeface",
            "JSON-Web-Token",
            "JSPFileManager",
            "JspJstlTutorial",
            "jspTut",
            "JSTL",
            "kafka-producer-consumer",
            "Log4JPackgSpecificLog",
            "Log4jRotation",
            "Log4jTest",
            "logback-with-springboot-config-master",
            "LoginSecurity",
            "ManualValidation",
            "methodfilteringinterceptor",
            "monitoring",
            "MultiplicationApp-With-Params",
            "MyAgentServices4.5",
            "MyTestPrograms",
            "NoticingApplication",
            "oneTouch",
            "Online Education System MCA Java Project",
            "paginationservlet",
            "parameterizinginterceptor",
            "ParentChildRelationsTopics",
            "ProjectManagementAndCurrentStatusTracker",
            "ReadExcel",
            "reports",
            "restapipool",
            "Resume",
            "resume-app",
            "resume-fullstack",
            "ShrimadBhagwatGeeta",
            "ShriRamCharitManas",
            "SimpleRMIExample",
            "spring-boot-crud-operation",
            "spring-boot-curd",
            "spring-boot-curd-mongo",
            "spring-boot-hibernate-crud-demo",
            "spring-boot-jsontodb",
            "spring-example",
            "spring-scheduler-with-shedlock-master",
            "spring-security-login-form-database",
            "spring-security-login-form-database-annotation",
            "SpringBatchDemo",
            "SpringBatchDemo1",
            "springboot-docker-compose",
            "Springboot-Microservice",
            "swing-projects",
            "TaskScheduler",
            "TaskScheduler1",
            "test-jar",
            "TestMavenProj",
            "vocab-khajana",
            "web-test"};

    private static void printNames(String s) {
        try {
            System.setOut(new PrintStream(new File("C:\\Users\\preme\\OneDrive\\Desktop\\tt.txt")));
            Arrays.stream(arr).forEach(s1 -> System.out.println(baseSting.replaceAll("<name>", s1)));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }


}
